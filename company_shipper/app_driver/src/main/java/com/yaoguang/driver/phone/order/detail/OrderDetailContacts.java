package com.yaoguang.driver.phone.order.detail;

import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.driver.DriverOrderDetailVoSec;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

import java.util.List;

/**
 * 订单明细
 * Created by zhongjh on 2018/5/22.
 */
public class OrderDetailContacts {

    interface View extends BaseView {

        /**
         * 加载中动画
         */
        void showLoadingView();

        /**
         * 隐藏加载中的动画
         */
        void hideLoadingView();

        /**
         * 加载后异常
         */
        void showErrorView();

        /**
         * 更新状态之后 成功返回 相关信息
         *
         * @param result      文本
         * @param operateType 操作索引
         */
        void updateSuccess(String result, int operateType);

        /**
         * 赋值数据源
         *
         * @param response 数据源
         */
        void setData(DriverOrderDetailVoSec response);

        /**
         * 打开放单地址
         *
         * @param infoPutorderPlaces 放单地址
         * @param position 索引
         */
        void openPutOrderAddress(DriverOrderWrapper driverOrderWrapper, List<InfoPutorderPlace> infoPutorderPlaces, int position);
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化订单详情
         *
         * @param orderId 订单id
         */
        void refreshOrderDetail(String orderId);

        /**
         * 出车、接单、拒绝
         *
         * @param orderId     订单id
         * @param operateType 操作类型
         * @param order       订单数据
         * @param position    索引
         * @param remark      备注
         * @param placeId     放单id
         */
        void handleUpdate(String orderId, int operateType, DriverOrderWrapper order, int position, String remark, String placeId);

        /**
         * 出车
         *
         * @param orderId            订单id
         * @param driverOrderWrapper 订单实体类
         * @param lat                经
         * @param lon                纬
         * @param address            地址
         */
        void outCar(String orderId, DriverOrderWrapper driverOrderWrapper, double lat,
                    double lon, String address);

        /**
         * 获取放单点数据
         *
         * @param order    订单
         * @param position 列表位置
         */
        void acceptOrder(DriverOrderWrapper order, int position);
    }

}
