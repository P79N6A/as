package com.yaoguang.interactor;

import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.interfaces.BaseListView;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 一些公共方法
 * Created by zhongjh on 2017/7/24.
 */
public class BasePresenterImpl<T> {

    public CompositeDisposable mCompositeDisposable;
    public int pageIndex;//下一页
    public boolean hasNextPage = true;//是否下一页

    //回调接口
    protected OnRecyclerViewCallback mOnRecyclerViewCallback;

    public void unSubscribe() {
        mCompositeDisposable.clear();
    }


    public void subscribe(Observable observable, final CompositeDisposable compositeDisposable, final BaseListView<T> baseListView, final int dataSize, final boolean isNext, final int pageIndex) {

        observable.subscribe(new BaseObserver<BaseResponse<PageList<T>>>(compositeDisposable) {
            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                if (pageIndex == 1) {
//                    mBaseListView.refreshDataAnimation();
                    baseListView.setEnableLoadmore(true);
                }
//                else if(isNext){
//                    mBaseListView.startLoadMore();
//                }
            }

            @Override
            public void onSuccess(BaseResponse<PageList<T>> response) {
                if (mOnRecyclerViewCallback != null) {
                    if (mOnRecyclerViewCallback.onNext200(response))
                        return;
                }
                if (response.getResult().getPageNo() == 1 && (response.getResult().getResult() == null || response.getResult().getResult().size() <= 0)) {
                    baseListView.recyclerViewShowEmpty();
                    return;
                }
                if (response.getResult().getTotalCount() > (dataSize + response.getResult().getResult().size())) {
                    hasNextPage = true;
                    baseListView.setEnableLoadmore(true);
                } else {
                    hasNextPage = false;
                    baseListView.setEnableLoadmore(false);
                }
                if (isNext) {
                    baseListView.nextAdapter(response.getResult().getResult(), hasNextPage);
                } else {
                    baseListView.refreshAdapter(response.getResult().getResult(), hasNextPage);
                }
            }

            @Override
            public void onFail(BaseResponse<PageList<T>> response) {
                super.onFail(response);
                baseListView.recyclerViewShowError(response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                baseListView.recyclerViewShowError("网络出错：" + e.getMessage());
                if (isNext) {
                    baseListView.finishLoadmore();
                } else {
                    baseListView.finishRefreshing();
                }
            }

            @Override
            public void onComplete() {
                if (isNext) {
                    baseListView.finishLoadmore();
                } else {
                    baseListView.finishRefreshing();
                }
            }

        });

    }

    protected interface OnRecyclerViewCallback<T> {
        //如果返回true，则后面的就不执行了
        boolean onNext200(BaseResponse<PageList<T>> listBaseResponse);
    }

}
