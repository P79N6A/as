package com.yaoguang.appcommon.phone.contact2;


import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.contact
 * @Description: 我的关联 关联接口
 * @date 2018/04/11
 */
public interface ContactContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<String> {

    }

    interface View extends BaseView, BaseListConditionView<DriverContactCompany, String> {


    }
}
