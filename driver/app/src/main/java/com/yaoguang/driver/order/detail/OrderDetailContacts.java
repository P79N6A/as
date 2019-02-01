package com.yaoguang.driver.order.detail;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.base.baseview.BaseView;
import com.yaoguang.driver.data.source.OrderRepository;
import com.yaoguang.greendao.entity.DriverOrderDetailVo;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderWrapper;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情MVP
 * Created by wly on 2018/1/15 0015.
 */

public interface OrderDetailContacts {
    interface View extends BaseView {

        void showLoadingView();

        void hideLoadingView();

        void setData(BaseResponse<DriverOrderDetailVo> orderDetail);

        void showSuccess(String result, int operateType);

        void refreshData();

        void acceptOrder(Order order);

        void startOrderNodeMapFragment(List<DriverOrderNode> result, String id);

        void recyclerViewShowEmpty();

        void openPutOrderAddress(DriverOrderWrapper order, ArrayList<InfoPutorderPlace> infoPutorderPlaces, int position);
    }

    abstract class Presenter extends BasePresenter<View, OrderRepository> {

        abstract void refreshOrderDetail(String orderId);

        abstract void handleUpdate(String orderId, int operateType, DriverOrderWrapper order, int position, String remark, String placeId);

        abstract void getNodes(String orderId, String id);

        abstract void outCar(String orderId, DriverOrderWrapper order);

        abstract void hideLoadingView();

        abstract void getPutOrderData(DriverOrderWrapper order, int position);
    }
}
