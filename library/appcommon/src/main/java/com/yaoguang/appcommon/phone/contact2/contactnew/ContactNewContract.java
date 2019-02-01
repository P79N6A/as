package com.yaoguang.appcommon.phone.contact2.contactnew;


import com.yaoguang.greendao.entity.common.DriverFollowCompany;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.contactnew
 * @Description: 新的关联 关联接口
 * @date 2018/04/19
 */
public interface ContactNewContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<String> {

        /**
         * 忽略申请信息
         *
         * @param condition
         */
        void ignoreAduit(String condition);

        void passAduit(String condition);
    }

    interface View extends BaseView, BaseListConditionView<DriverFollowCompany, String> {


    }
}
