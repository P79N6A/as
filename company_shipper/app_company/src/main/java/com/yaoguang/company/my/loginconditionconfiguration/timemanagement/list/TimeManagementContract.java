package com.yaoguang.company.my.loginconditionconfiguration.timemanagement.list;

import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2018/12/6.
 */
public class TimeManagementContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<String> {
        /**
         * 删除
         * @param ids id逗号分隔
         */
        void remove(String ids);
    }

    interface View extends BaseView, BaseListConditionView<UserLoginTime, String> {

    }

}
