package com.yaoguang.driver.home.driverstatus.add;

import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.common.base.interfaces.BaseView;

/**
 * Created by 韦理英
 * on 2017/6/21 0021.
 */

public class DStatusSwitchAddContact {
    public interface View extends BaseView {
        void setStatusSuccess(UserDriverStatusDetail userDriverStatusDetail);
    }
    public interface Presenter extends BasePresenter {
        void saveLeaveStatus(DriverOrderNodeDetail driverOrderNodeDetail, UserDriverStatusDetail userDriverStatusDetail);
    }
}
