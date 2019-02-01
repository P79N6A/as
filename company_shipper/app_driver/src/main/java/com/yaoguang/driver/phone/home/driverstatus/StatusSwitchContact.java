package com.yaoguang.driver.phone.home.driverstatus;

import android.support.annotation.NonNull;

import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.List;

/**
 * Created by 韦理英
 * on 2017/6/21 0021.
 */

public interface StatusSwitchContact {

    interface View extends BaseView {

        void setRefreshAdapter(@NonNull BaseResponse<DriverStatusSwitch> data);

        void updateSuccess();

        /**
         * 刷新adapter
         *
         * @param list  数据
         */
        void refreshAdapter(List list);

        /**
         * 赋值adapter
         * @param list 数据
         */
        void setAdapter(List list);

        void deleteLeavePlanSuccess();

    }

    interface Presenter extends BasePresenter {

        void getDriverLeaveStatus();

        void deleteLeavePlan(@NonNull String id);

        void updateLeavePlan(@NonNull String id, @NonNull String endDate, @NonNull String sendFlag);
    }

}
