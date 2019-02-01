package com.yaoguang.driver.phone.my.myorder2.clientcondition;


import com.yaoguang.greendao.entity.driver.DriverEntrustCompany;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.phone.my.myorder2.clientcondition
 * @Description: 委托人条件筛选 关联接口
 * @date 2018/04/09
 */
public interface ClientConditionContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<String> {

    }

    interface View extends BaseView, BaseListConditionView<DriverEntrustCompany, String> {


    }
}
