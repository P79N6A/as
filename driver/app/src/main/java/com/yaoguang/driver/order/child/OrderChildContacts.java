package com.yaoguang.driver.order.child;

import com.yaoguang.driver.base.baseview.BaseListViewV2;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.data.source.MessageRepository;
import com.yaoguang.driver.data.source.OrderRepository;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 子订单MVP
 * Created by wly
 * on 2018/1/8 0008.
 */

public interface OrderChildContacts {
    interface View extends BaseListViewV2<Order> {

        void setPreviousTotal(int i);

        void showSuccess(String strMessage, Order order, int position);

        void showAcceptSuccess(String strMessage, Order order, int position, int fromFragment, int toFragment);

        void showRefuseSuccess(String strMessage, Order order, int position);

        void startOrderNodeMapFragment(List<DriverOrderNode> value, String id);

        void deleteMessageSuccess(String message);

        void setReadSuccess(int position);

        void openPutOrderAddress(Order order, ArrayList<InfoPutorderPlace> infoPutorderPlaces, int position);
    }

    abstract class Presenter extends BasePresenter<OrderChildContacts.View, OrderRepository> {

        abstract void getPutOrderData(Order order, int position);

        abstract void getData(int intType, int dataSize, boolean isNext);

        public abstract void setMessageRepository(MessageRepository messageRepository);

        abstract void refreshData(int intType);

        public abstract void refreshData(int intType, boolean isHomePage);

        abstract void handleUpdate(String orderId, int operateType, Order order, int position, String remark, String placeId);

        abstract void loadMoreData(int intType, int dataSize);

        public abstract void loadMoreData(int intType, int dataSize, boolean isHomePage);

        abstract void getNodes(String orderId, String id);

        abstract void outCar(String orderId, Order order, int position);

        public abstract void submitDeleteMessages(HashSet<String> selectIds);

        public abstract void readBatch(String ids, int position);

    }
}
