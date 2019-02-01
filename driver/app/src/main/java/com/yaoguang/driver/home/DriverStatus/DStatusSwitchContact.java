package com.yaoguang.driver.home.driverstatus;

import android.support.annotation.NonNull;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.base.baseview.BaseListViewV2;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.greendao.entity.DriverStatusSwitch;

/**
 * Created by 韦理英
 * on 2017/6/21 0021.
 */

public interface DStatusSwitchContact {

    interface View extends BaseListViewV2<DriverStatusSwitch> {

        /**
         * 设置adapter
         * @param data
         */
        void setRefreshAdapter(@NonNull BaseResponse<DriverStatusSwitch> data);

        /**
         * 更新adapter
         */
        void updateSuccess();

        /**
         * 删除休假计划
         */
        void deleteLeavePlanSuccess();
    }

    abstract class Presenter extends BasePresenter<View, DriverRepository> {

        /**
         * 获取休假计划状态
         */
        abstract void getDriverLeaveStatus();

        /**
         * 删除休假计划
         *
         * @param id 计划id
         */
        abstract void deleteLeavePlan(@NonNull String id);

        /**
         * 更新休假计划
         *
         * @param id       计划id
         * @param endDate  结束时间
         * @param sendFlag 是否可向我派单
         * @return 成功失败标识
         */
        abstract void updateLeavePlan(@NonNull String id, @NonNull String endDate, @NonNull String sendFlag);
    }

}
