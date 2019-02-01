package com.yaoguang.driver.order.child;

import android.support.annotation.Nullable;

import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.base.presetner.BasePresenterImplV2;
import com.yaoguang.driver.data.source.MessageRepository;
import com.yaoguang.greendao.entity.Order;

import java.util.HashSet;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 企业挂靠管理的逻辑分发层
 * Created by wly
 * on 2017 2017/4/6
 */
public class OrderChildPresenter extends OrderChildContacts.Presenter {
    public static final int WAIT = 0;
    public static final int ACCEPT = 1;
    public static final int FINISH = 2;
    public static final int REFUSE = 3;

    private int pageIndex;//下一页
    private boolean hasNextPage = true;//是否下一页
    private MessageRepository mMessageRepository;

    @Override
    public void setMessageRepository(MessageRepository messageRepository) {
        this.mMessageRepository = messageRepository;
    }

    public OrderChildPresenter() {
    }

    /**
     * 刷新数据
     * @param tabType 选择tag
     */
    @Override
    public void refreshData(int tabType) {
        refreshData(tabType, false);
    }

    @Override
    public void loadMoreData(int tabType, int dataSize) {
        loadMoreData(tabType, dataSize, false);
    }


    /**
     * 刷新数据
     *
     * @param tabType    订单类型
     * @param isHomePage 是否首页
     */
    @Override
    public void refreshData(int tabType, boolean isHomePage) {
        pageIndex = 1;
        mView.setPreviousTotal(0);
        hasNextPage = true;
        if (tabType <= 3) {
            getData(tabType, 0, false);
        } else if (tabType == 4) {
            getMessageOrderData(0, false, isHomePage);
        }
    }

    /**
     * 下一页数据
     *
     * @param tabType    订单类型
     * @param dataSize   当前数据源长度
     * @param isHomePage 是否首页
     */
    @Override
    public void loadMoreData(int tabType, int dataSize, boolean isHomePage) {
        pageIndex++;
        if (tabType <= 3) {
            getData(tabType, dataSize, true);
        } else if (tabType == 4) {
            ULog.i("getMessageOrderData");
            getMessageOrderData(dataSize, true, isHomePage);
        }
    }

    /**
     * 更新出车状态
     *
     * @param orderId     订单id
     * @param operateType 1:接单 2:完成 3:关闭 4:出车
     * @param order       用于后期更新ui
     * @param position    索引
     * @param remark      备注
     * @param placeId     存放点id
     */
    @Override
    public void handleUpdate(String orderId, final int operateType, final Order order, final int position, String remark, @Nullable String placeId) {
        Flowable<BaseResponse<String>> baseResponseObservable = mData.handleUpdate(null, orderId, String.valueOf(operateType), remark, placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        baseResponseObservable.subscribe(value -> {
            switch (operateType) {
                case 4:
                    //出车
                    if (value.getState().equals("200")) {
                        order.setVehicleFlag("1");
                        mView.showSuccess(value.getResult(), order, position);
                    } else {
                        mView.showToast(value.getMessage());
                    }
                    break;
                case ACCEPT:
                    //接单
                    if (value.getState().equals("200")) {
                        order.setOrderStatus("1");
                        mView.showAcceptSuccess(value.getResult(), order, position, WAIT, ACCEPT);
                    } else {
                        mView.showToast(value.getMessage());
                    }
                    break;
                case REFUSE:
                    // 拒绝
                    if (value.getState().equals("200")) {
                        order.setOrderStatus("3");
                        mView.showAcceptSuccess(value.getResult(), order, position, ACCEPT, REFUSE);
//                            mView.showRefuseSuccess(value.getResult(), order, position);
                    } else {
                        mView.showToast(value.getMessage());
                    }
                    break;

            }
        }, Throwable::printStackTrace);
    }

    /**
     * 出车
     * @param orderId   订单id
     * @param order     订单
     * @param position  列表位置
     */
    @Override
    public void outCar(String orderId, final Order order, final int position) {

        Flowable<BaseResponse<String>> baseResponseObservable = mData.outCar(null, orderId, null);
        baseResponseObservable.subscribe(value -> {
            //出车
            if (value.getState().equals("200")) {
                order.setVehicleFlag("1");
                mView.showSuccess(value.getResult(), order, position);
            } else {
                mView.showToast(value.getMessage());
            }
        }, Throwable::printStackTrace);

    }

    private void getMessageOrderData(final int dataSize, final boolean isNext, boolean isHomePage) {

        mCompositeDisposable.add(mMessageRepository.getMessageList(pageIndex, isHomePage).subscribe(value -> {

            if (value.getResult().getPageNo() == 0 && (value.getResult().getResult() == null || value.getResult().getResult().size() <= 0)) {
                mView.setShowAllDataLoadFinish(false);
                mView.recyclerViewShowEmpty();
                mView.finishRefreshing();
                return;
            }
            if (value.getResult().getTotalCount() > (dataSize + value.getResult().getResult().size())) {
                mView.setShowAllDataLoadFinish(false);
                hasNextPage = true;
            } else {
                if (dataSize != 0) {    // 没有数据时不要显示 已全部加载完所有数据
                    mView.setShowAllDataLoadFinish(true);
                }
                hasNextPage = false;
            }
            if (isNext) {
                mView.nextAdapter(value.getResult().getResult(), hasNextPage);
            } else {
                mView.refreshAdapter(value.getResult().getResult(), hasNextPage);
            }
            mView.finishRefreshing();
        }, throwable -> {
            mView.recyclerViewShowError(null);
            mView.finishRefreshing();
        }));
    }

    /**
     * 获取放单点数据
     *
     * @param order    订单
     * @param position 列表位置
     */
    @Override
    public void getPutOrderData(final Order order, final int position) {
        // 获取数据并传到view层
        mCompositeDisposable.add(mData.getPutOrderAddressList(order.getId())
                .subscribe(list -> {
                    // 打开放单点fragment
                    if (list != null)
                        mView.openPutOrderAddress(order, list, position);
                    else
                        // 接受订单
                        handleUpdate(order.getOrderId(), ACCEPT, order, position, "", null);
                    mView.finishRefreshing();
                }, throwable -> {
                    ULog.e(throwable);
                    mView.recyclerViewShowError(null);
                    mView.finishRefreshing();
                }));
    }


    /**
     * 获取数据源
     *
     * @param type     订单类型
     * @param dataSize 当前数据源长度
     * @param isNext   是否下一页
     */
    @Override
    public void getData(int type, final int dataSize, final boolean isNext) {
        Flowable<BaseResponse<PageList<Order>>> baseResponseObservable = mData.getOrderList(null, type, pageIndex);
        BasePresenterImplV2 basePresenterImplV2 = new BasePresenterImplV2();
        basePresenterImplV2.subscribeList(baseResponseObservable, mView, dataSize, isNext, pageIndex, null);
    }

    /**
     * 获取节点数据
     *
     * @param orderId 订单id
     * @param id      id
     */
    @Override
    public void getNodes(String orderId, final String id) {
        //形成一个节点数据
        mCompositeDisposable.add(mData.nextNode(orderId).subscribe(value -> {
            if (value.getState().equals("200") && value.getResult().isEmpty()) {
                mView.showToast("订单完成");
            } else if (value.getState().equals("200")) {
                //跳转
                mView.startOrderNodeMapFragment(value.getResult(), id);
            } else {
                mView.showToast(value.getMessage());
            }
        }, Throwable::printStackTrace));
    }


    /**
     * 批量设置已读
     *
     * @param ids      id列表
     * @param position 消息位置
     */
    @Override
    public void readBatch(String ids, final int position) {
        mCompositeDisposable.add(mMessageRepository.readBatch(ids).subscribe(aBoolean -> {
            if (aBoolean) mView.setReadSuccess(position);
        }, Throwable::printStackTrace));
    }

    /**
     * 提交删除信息
     *
     * @param selectIds 消息id列表
     */
    @Override
    public void submitDeleteMessages(HashSet<String> selectIds) {
        if (selectIds != null && selectIds.isEmpty()) {
            mView.showToast("没有可删除的消息");
            return;
        }
        mCompositeDisposable.add(mMessageRepository.orderMessageDeleted(ObjectUtils.subString(selectIds)).subscribe(b -> {
            if (b) mView.deleteMessageSuccess("删除成功");
        }, Throwable::printStackTrace));
    }

    @Override
    public void subscribe() {

    }
}
