package com.yaoguang.company.my.usermanagement.mode.time;

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
    }

    interface View extends BaseView, BaseListConditionView<UserLoginTime, String> {

    }

}
