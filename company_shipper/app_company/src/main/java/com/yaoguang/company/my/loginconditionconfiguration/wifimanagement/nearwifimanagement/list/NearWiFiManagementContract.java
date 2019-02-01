package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.nearwifimanagement.list;

import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlan;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2018/12/6.
 */
public class NearWiFiManagementContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<String> {

    }

    interface View extends BaseView, BaseListConditionView<UserRecentlyUsedWlan, String> {

    }

}
