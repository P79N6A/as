package com.yaoguang.driver.phone.order.child;

import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

import java.util.ArrayList;

/**
 * 子订单MVP
 * Created by wly
 * on 2018/1/8 0008.
 */

public interface OrderChildContacts {

    interface View extends BaseView, BaseListConditionView<DriverOrderWrapper, Integer> {

        /**
         * 更新状态成功
         *
         * @param strMessage         成功信息
         * @param driverOrderWrapper 后期更新ui
         * @param position           更新的索引
         */
        void showSuccess(String strMessage, DriverOrderWrapper driverOrderWrapper, int position);

        /**
         * 接单成功
         *
         * @param strMessage         成功信息
         * @param driverOrderWrapper 后期更新ui
         * @param position           删除的索引
         * @param fromFragment
         * @param toFragment
         */
        void showAcceptSuccess(String strMessage, DriverOrderWrapper driverOrderWrapper, int position, int fromFragment, int toFragment);

        /**
         * 打开放单地点
         *
         * @param driverOrderWrapper 订单item
         * @param infoPutorderPlaces 放单数据源
         * @param position           索引
         */
        void openPutOrderAddress(DriverOrderWrapper driverOrderWrapper, ArrayList<InfoPutorderPlace> infoPutorderPlaces, int position);
    }

    interface Presenter extends BasePresenterListCondition<Integer> {


        /**
         * 获取放单点数据
         *
         * @param driverOrderWrapper 订单
         * @param position           列表位置
         */
        void getPutOrderData(DriverOrderWrapper driverOrderWrapper, int position);

        /**
         * 更新出车状态
         *
         * @param orderId            订单id
         * @param operateType        1:接单 2:完成 3:关闭 4:出车
         * @param driverOrderWrapper 用于后期更新ui
         * @param position           索引
         * @param remark             备注
         * @param placeId            存放点id
         */
        void handleUpdate(String orderId, int operateType, DriverOrderWrapper driverOrderWrapper, int position, String remark, String placeId);

        /**
         * 出车
         *
         * @param orderId            订单id
         * @param driverOrderWrapper 订单数据
         * @param lat                地图坐标lat
         * @param lon                地图坐标lon
         * @param address            地址
         * @param position           订单的索引
         */
        void outCar(String orderId, DriverOrderWrapper driverOrderWrapper, double lat, double lon, String address, int position);

    }
}
