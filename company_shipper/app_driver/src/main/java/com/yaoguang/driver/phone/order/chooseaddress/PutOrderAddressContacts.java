package com.yaoguang.driver.phone.order.chooseaddress;

import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 控制层与view层
 * Created by wly on 2017/12/28 0028.
 *
 * update zhongjh by 2018/06/07
 */
public interface PutOrderAddressContacts {

    interface View extends BaseView {

        /**
         * 接单成功后触发的事件
         */
        void acceptSuccess();

    }

    interface Presenter extends BasePresenter {

        /**
         * 同意订单
         *
         * @param orderId             订单id
         * @param mDriverOrderWrapper 订单数据
         * @param mPosition           索引
         * @param remark              备注
         * @param mPlaceId            存放点id
         */
        void accept(String orderId, DriverOrderWrapper mDriverOrderWrapper, int mPosition, String remark, String mPlaceId);
    }

}
