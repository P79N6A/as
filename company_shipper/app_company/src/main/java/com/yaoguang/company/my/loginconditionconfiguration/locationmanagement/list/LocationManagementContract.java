package com.yaoguang.company.my.loginconditionconfiguration.locationmanagement.list;

import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2018/12/10.
 */

public class LocationManagementContract {


    interface Presenter extends BasePresenter, BasePresenterListCondition<String> {

        /**
         * 删除
         * @param ids id逗号分隔
         */
        void remove(String ids);

        /**
         * 添加
         * @param userLoginAllowLocation 实体
         */
        void add(UserLoginAllowLocation userLoginAllowLocation);

    }

    interface View extends BaseView, BaseListConditionView<UserLoginAllowLocation, String> {

    }
}
