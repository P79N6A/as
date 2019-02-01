package com.yaoguang.company.my.usermanagement.list;

import com.yaoguang.greendao.entity.company.user.UserCondition;
import com.yaoguang.greendao.entity.company.user.ViewUser;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2018/12/11.
 */

public class UserManagementContract {

    interface Presenter extends BasePresenterListCondition<UserCondition> {

    }

    interface View extends BaseView, BaseListConditionView<ViewUser, UserCondition> {

    }

}
