package com.yaoguang.driver.order.detail;

import com.yaoguang.common.common.ULog;
import com.yaoguang.greendao.entity.DriverOrderWrapper;
import com.yaoguang.lib.annotation.apt.InstanceFactory;

import static com.yaoguang.driver.order.child.OrderChildPresenter.ACCEPT;

/**
 * 订单详情
 * Created by wly on 2017/4/21.
 */
@InstanceFactory
public class OrderDetailPresenter extends OrderDetailContacts.Presenter {


    /**
     * 初始化订单详情
     *
     * @param orderId
     */
    @Override
    public void refreshOrderDetail(String orderId) {
        mView.showLoadingView();
        mCompositeDisposable.add(mData.getOrderDetail(null, orderId).subscribe(value -> {
            if (value.getResult() != null && value.getResult().getOrderWrapper() != null) {
                mView.setData(value);
            }
            mView.hideLoadingView();
        }, throwable -> {
            mView.hideLoadingView();
            mView.recyclerViewShowEmpty();
        }));
    }

    @Override
    public void handleUpdate(String orderId, final int operateType, final DriverOrderWrapper order, final int position, String remark, String placeId) {
        mCompositeDisposable.add(mData.handleUpdate(null, orderId, String.valueOf(operateType), remark, placeId).subscribe(value -> {
            switch (operateType) {
                case 4:
                    //出车
                    if (value.getState().equals("200")) {
                        order.setVehicleFlag("1");
                        mView.showSuccess(value.getResult(), operateType);
                    } else {
                        mView.showToast(value.getMessage());
                    }
                    break;
                case 1:
                    //接单
                    if (value.getState().equals("200")) {
                        order.setOrderStatus("1");
                        mView.showSuccess(value.getResult(), operateType);
                    } else {
                        mView.showToast(value.getMessage());
                    }
                    break;
                case 3:
                    //拒绝
                    if (value.getState().equals("200")) {
                        order.setOrderStatus("3");
                        mView.showSuccess(value.getResult(), operateType);
                    } else {
                        mView.showToast(value.getMessage());
                    }
                    break;

            }
        }, Throwable::printStackTrace));

    }

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

    @Override
    public void outCar(String orderId, final DriverOrderWrapper order) {
        mCompositeDisposable.add(mData.outCar(null, orderId, null).subscribe(value -> {
            //出车
            order.setVehicleFlag("1");
            mView.showSuccess(value.getResult(), -1);
        }, Throwable::printStackTrace));
    }

    @Override
    public void hideLoadingView() {
        mView.hideLoadingView();
    }

    /**
     * 获取放单点数据
     *
     * @param order    订单
     * @param position 列表位置
     */
    @Override
    public void getPutOrderData(final DriverOrderWrapper order, final int position) {
        // 获取数据并传到view层
        mCompositeDisposable.add(mData.getPutOrderAddressList(order.getId())
                .subscribe(list -> {
                    // 打开放单点fragment
                    if (list != null)
                        mView.openPutOrderAddress(order, list, position);
                    else
                        // 接受订单
                        handleUpdate(order.getOrderId(), ACCEPT, order, position, "", null);
                }, ULog::e));
    }

    @Override
    public void subscribe() {
    }

}
