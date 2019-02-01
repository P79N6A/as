package com.yaoguang.driver.base.presetner;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.base.baseview.BaseListViewV2;
import com.yaoguang.interactor.driver.DBasePresenterImpl;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * 一些公共方法
 * Created by 韦理英 on 2017/7/24.
 */
public class BasePresenterImplV2<T> extends DBasePresenterImpl {
    public boolean hasNextPage = true;//是否下一页

    /**
     * 通用 list分页，可自定义next
     *
     * @param flowable     flowable
     * @param baseListView view
     * @param dataSize     列表数量
     * @param isNext       是否一下页
     * @param pageIndex    查询页
     * @param onCallback   回调
     */
    public void subscribeList(Flowable flowable, final BaseListViewV2 baseListView, final int dataSize, final boolean isNext, final int pageIndex, final OnCallback onCallback) {
        if (pageIndex == 1) {
            baseListView.startRefresh();
        }

        flowable.subscribe(new Consumer<BaseResponse<PageList<T>>>() {
            @Override
            public void accept(BaseResponse<PageList<T>> value) throws Exception {
                if (value.getState().equals("200")) {
                    if (onCallback != null) {   // 自定义next
                        if (onCallback.onNext(value)) {
                            return;
                        }
                    }
                    if (value.getResult().getPageNo() == 1 && (value.getResult().getResult() == null || value.getResult().getResult().size() <= 0)) {   // 空数据
                        baseListView.setShowAllDataLoadFinish(false);
                        baseListView.recyclerViewShowEmpty();
                        baseListView.finishRefreshing();
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
                baseListView.finishRefreshing();
            }
        }, throwable -> {
            baseListView.hideDialog();
            baseListView.recyclerViewShowError("网络出错");
            baseListView.finishRefreshing();
        });
    }

    public interface OnCallback<T> {

        //如果返回true，则后面的就不执行了
        boolean onNext(BaseResponse<T> listBaseResponse);
    }


}
