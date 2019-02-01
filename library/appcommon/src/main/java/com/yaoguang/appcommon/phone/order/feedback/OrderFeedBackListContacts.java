package com.yaoguang.appcommon.phone.order.feedback;

import com.yaoguang.greendao.entity.driver.DriverOrderNodeFeedback;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.PageList;

/**
 * 查看故障反馈、查看动态记录
 * Created by zhongjh on 2018/3/25.
 */
public class OrderFeedBackListContacts {

    interface View extends BaseView {

        /**
         * 显示数据源
         * @param value
         */
        void show(PageList<DriverOrderNodeFeedback> value);
    }

    interface Presenter extends BasePresenter {

        /**
         * 获取故障反馈列表，和装卸动态
         *
         * @param mOrderId 订单
         * @param type     0 故障反馈 , 1 装卸动态
         */
        void getList(String mOrderId, int type);

    }

}
