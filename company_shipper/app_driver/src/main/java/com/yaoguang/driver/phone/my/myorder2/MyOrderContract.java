package com.yaoguang.driver.phone.my.myorder2;


import com.yaoguang.greendao.entity.driver.DriverOrderCondition;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.myorder
 * @Description: 我的订单 关联接口
 * @date 2018/04/04
 */
public interface MyOrderContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<DriverOrderCondition> {

    }

    interface View extends BaseView, BaseListConditionView<DriverOrderWrapper, DriverOrderCondition> {

        /**
         * 赋值总金额
         *
         * @param response      数据源
         * @param mTotalFreight 金额
         * @param StartDate     初始时间
         * @param EndDate       结束时间
         */
        void setTotalFreight(BaseResponse<PageList<DriverOrderWrapper>> response, String mTotalFreight, String StartDate, String EndDate);
    }

}
