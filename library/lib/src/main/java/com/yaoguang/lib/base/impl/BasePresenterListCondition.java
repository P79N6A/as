package com.yaoguang.lib.base.impl;

import android.os.Debug;
import android.support.annotation.NonNull;

import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 一些公共方法
 * Created by zhongjh on 2017/7/24.
 */
public abstract class BasePresenterListCondition<C, T> {

    public CompositeDisposable mCompositeDisposable;
    public int pageIndex = 1;// 当前第几页
    public boolean hasNextPage = true;//是否下一页
    public boolean isIgnoreEmptyAndError = false; // 忽略空和异常,当返回的列表是空或者异常的时候，依然执行next200等事件
    private int dataSize;
    private boolean isNext;

    /**
     * mDisposableObserver 对象
     */
    protected BaseObserver<BaseResponse<PageList<T>>> mDisposableObserver;

    /**
     * 可以让子类自定义实现ui
     *
     * @param response 数据源
     */
    public void customRefreshing(BaseResponse<PageList<T>> response) {

    }

    //回调接口
    protected OnRecyclerViewCallback mOnRecyclerViewCallback;

    //回调接口
    protected interface OnRecyclerViewCallback<T> {

        /**
         * 自定义其他view事件
         *
         * @param listBaseResponse 数据源
         */
        void onNext200(BaseResponse<PageList<T>> listBaseResponse);

    }

    // 列表view
    @NonNull
    protected abstract BaseListConditionView getBaseListView();

    /**
     * 给子类实现获取业务方法的
     *
     * @param condition 条件实体
     * @param pageIndex 页码
     * @return 数据源
     */
    protected abstract Observable<BaseResponse<PageList<T>>> initDatas(C condition, int pageIndex);

    /**
     * 构造函数
     */
    protected BasePresenterListCondition() {
        mCompositeDisposable = new CompositeDisposable();
        mDisposableObserver = new BaseObserver<BaseResponse<PageList<T>>>(mCompositeDisposable) {

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                if (pageIndex == 1) {
//                    mBaseListView.refreshDataAnimation();
                    getBaseListView().setEnableLoadmore(true);
                }
//                else if(isNext){
//                    mBaseListView.startLoadMore();
//                }
            }

            @Override
            public void onSuccess(BaseResponse<PageList<T>> response) {
                if (response.getResult().getPageNo() <= 1 && (response.getResult().getResult() == null || response.getResult().getResult().size() <= 0)) {
                    getBaseListView().recyclerViewShowEmpty(false);
                    if (!isIgnoreEmptyAndError)
                        return;
                }
                if (response.getResult().getTotalCount() > (dataSize + response.getResult().getResult().size())) {
                    hasNextPage = true;
                    getBaseListView().setEnableLoadmore(true);
                } else {
                    hasNextPage = false;
                    getBaseListView().setEnableLoadmore(false);
                }
                customRefreshing(response);
                if (isNext) {
                    getBaseListView().nextAdapter(response.getResult().getResult(), hasNextPage);
                } else {
                    getBaseListView().refreshAdapter(response.getResult().getResult(), hasNextPage);
                }
                if (mOnRecyclerViewCallback != null) {
                    mOnRecyclerViewCallback.onNext200(response);
                }
                getBaseListView().finishRefreshing();
            }

            @Override
            public void onFail(BaseResponse<PageList<T>> response) {
                getBaseListView().recyclerViewShowError(response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                if (BuildConfig.DEBUG) {
                    getBaseListView().recyclerViewShowError(e.getMessage());
                } else {
                    getBaseListView().recyclerViewShowError("网络出错");
                }
                if (isNext) {
                    getBaseListView().finishLoadmore();
                } else {
                    getBaseListView().finishRefreshing();
                }
            }

            @Override
            public void onComplete() {
                if (isNext) {
                    getBaseListView().finishLoadmore();
                } else {
                    getBaseListView().finishRefreshing();
                }
            }


        };
    }

    /**
     * 清除
     */
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    /**
     * 刷新数据
     *
     * @param condition 条件
     */
    public void refreshData(C condition) {
        pageIndex = 1;
        hasNextPage = true;
        getData(condition, 0, false);
    }

    /**
     * 刷新列表
     *
     * @param condition 条件
     * @param dataSize  列表数据源长度
     */
    public void loadMoreData(C condition, int dataSize) {
        if (hasNextPage) {
            pageIndex++;
            getData(condition, dataSize, true);
        }
    }

    /**
     * 刷新列表
     *
     * @param condition 条件
     * @param dataSize  列表数据源长度
     * @param isNext    是否加载下一页的
     */
    private void getData(C condition, final int dataSize, final boolean isNext) {
        Observable observable = initDatas(condition, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        showData(observable, dataSize, isNext);
    }

    /**
     * 刷新列表（调用ui）
     *
     * @param observable retrofit包含的数据源
     * @param dataSize   数据源长度
     * @param isNext     是否加载下一页
     */
    public void showData(Observable observable, final int dataSize, final boolean isNext) {
        this.dataSize = dataSize;
        this.isNext = isNext;
        observable.subscribe(mDisposableObserver);
    }


}
