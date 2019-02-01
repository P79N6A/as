package com.yaoguang.interactor.driver;

import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.interactor.R;
import com.yaoguang.interfaces.BaseListViewV2;
import com.yaoguang.lib.base.interfaces.BaseView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 一些公共方法
 * Created by 韦理英 on 2017/7/24.
 */
public abstract class BasePresenterImplV2<T> extends DBasePresenterImpl {
    public int pageIndex;//下一页
    public boolean hasNextPage = true;//是否下一页

    /**
     * 获取数据
     * @param t         实体
     * @param dataSize  数据大小
     * @param isNext    是否请求下一页
     */
    public void getData(T t, int dataSize, boolean isNext) {

    }

    /**
     * 刷新数据
     * @param t 实体
     */
    public void refreshData(T t) {
        pageIndex = 1;
        hasNextPage = true;
        // getData(orderId, 0, false);
    }

    /**
     * 加载更多数据
     * @param t     实体
     * @param size  数据大小
     */
    public void loadMoreData(T t, int size) {
        if (hasNextPage) {
            pageIndex++;
            // getList(orderId,size, true);
        }
    }

    /**
     * 通用的 无刷新,无list，可自定义next
     *
     * @param observable   观察者
     * @param baseListView view
     * @param showType     创建观察者时的提示
     * @param onCallback   回调
     */
    public void subscribe(Observable observable, final BaseView baseListView, final ShowType showType, final OnCallback onCallback) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        switch (showType) {
                            case PROCESS:
                                baseListView.showProgressDialog("请等待...");
                                break;
                        }
                    }


                    @Override
                    public void onNext(BaseResponse<T> value) {
                        ULog.i("onNext State=" + value.getState());
                        if (onCallback.onNext(value)) {
                            return;
                        }

                        if (value.getState().equals("200")) {
                            onCallback.onNext200(value);
                        } else {
                            baseListView.showToast(value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        switch (showType) {
                            case REFRESH:
                                break;
                            case PROCESS:
                                baseListView.hideProgressDialog();
                            case TOAST:
                                baseListView.showToast(UiUtils.getString(R.string.net_error_please));
                                break;
                        }
                        baseListView.hideProgressDialog();
                        onCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        switch (showType) {
                            case PROCESS:
                                baseListView.hideProgressDialog();
                                break;
                        }
                        // 完成
                        if (onCallback != null) onCallback.onComplete();
                    }

                });
    }

    /**
     * 通用的 list，可自定义next
     *
     * @param observable   观察者
     * @param baseListView view
     * @param showType     创建观察者时的提示
     * @param onCallback   回调
     */
    public void subscribeList(Observable observable, final BaseListViewV2 baseListView, final ShowType showType, final OnCallback onCallback) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        switch (showType) {
                            case REFRESH:
                                baseListView.startRefresh();
                                break;
                            case PROCESS:
                                baseListView.showProgressDialog("请等待...");
                                break;
                        }
                    }


                    @Override
                    public void onNext(BaseResponse<T> value) {
                        ULog.i("onNext State=" + value.getState());
                        if (onCallback.onNext(value)) {
                            return;
                        }

                        if (value.getState().equals("200")) {
                            onCallback.onNext200(value);
                        } else {
                            baseListView.showToast(value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        switch (showType) {
                            case REFRESH:
                                baseListView.recyclerViewShowError("网络出错：" + e.getMessage());
                                baseListView.finishRefreshing();
                                break;
                            case PROCESS:
                                baseListView.showToast(UiUtils.getString(R.string.net_error_please));
                                baseListView.hideProgressDialog();
                                break;
                        }
                        if (onCallback != null) onCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        switch (showType) {
                            case REFRESH:
                                baseListView.finishRefreshing();
                                break;
                            case PROCESS:
                                baseListView.hideProgressDialog();
                                break;
                        }
                        // 完成
                        if (onCallback != null) onCallback.onComplete();
                    }

                });
    }

    /**
     * 通用 list分页，可自定义next
     *
     * @param observable   观察者
     * @param baseListView view
     * @param dataSize     列表数量
     * @param isNext       是否一下页
     * @param pageIndex    查询页
     * @param onCallback   回调
     */
    public void subscribeList(Observable observable, final BaseListViewV2 baseListView, final int dataSize, final boolean isNext, final int pageIndex, final OnCallback onCallback) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<PageList<T>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        if (pageIndex == 1) {
                            baseListView.startRefresh();
                        }
                    }


                    @Override
                    public void onNext(BaseResponse<PageList<T>> value) {
                        if (value.getState().equals("200")) {
                            if (onCallback != null) {   // 自定义next
                                if (onCallback.onNext(value))
                                    return;
                            }
                            if (value.getResult().getPageNo() == 1 && (value.getResult().getResult() == null || value.getResult().getResult().size() <= 0)) {   // 空数据
                                baseListView.setShowAllDataLoadFinish(false);
                                baseListView.recyclerViewShowEmpty();
                                return;
                            }
                            if (value.getResult().getTotalCount() > (dataSize + value.getResult().getResult().size())) {    // 有下一页
                                baseListView.setShowAllDataLoadFinish(false);
                                hasNextPage = true;
                            } else {    // 下一页无数据
                                if (dataSize != 0) {    // 没有数据时不要显示 已全部加载完所有数据
                                    baseListView.setShowAllDataLoadFinish(true);
                                }
                                hasNextPage = false;
                            }
                            if (isNext) {   // 显示adapter
                                baseListView.nextAdapter(value.getResult().getResult(), hasNextPage);
                            } else {
                                baseListView.refreshAdapter(value.getResult().getResult(), hasNextPage);
                            }
                        } else {
                            baseListView.recyclerViewShowError(value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseListView.hideProgressDialog();
                        baseListView.recyclerViewShowError("网络出错");
                        baseListView.finishRefreshing();
                    }

                    @Override
                    public void onComplete() {
                        baseListView.finishRefreshing();
                        // 完成
                        if (onCallback != null) onCallback.onComplete();
                    }

                });

    }

    /**
     * NO       无任何提示
     * REFRESH  swipe刷新
     * PROCESS  进度对话框提示
     * TOAST    toast
     */
    public enum ShowType {
        NO, REFRESH, PROCESS, TOAST
    }

    public class OnCallback<T> {
        //如果返回true，则后面的就不执行了
        public void onNext200(BaseResponse<T> listBaseResponse) {
        }

        //如果返回true，则后面的就不执行了
        public boolean onNext(BaseResponse<T> listBaseResponse) {
            return false;
        }

        // 出错处理
        public void onError(Throwable e) {}

        // 完成
        public void onComplete() { }
    }


}
