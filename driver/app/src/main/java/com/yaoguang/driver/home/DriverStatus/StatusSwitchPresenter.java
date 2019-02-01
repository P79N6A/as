package com.yaoguang.driver.home.driverstatus;

import android.support.annotation.NonNull;

import com.yaoguang.lib.annotation.apt.InstanceFactory;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 韦理英
 * on 2017/6/21 0021.
 */

@InstanceFactory
public class StatusSwitchPresenter extends com.yaoguang.driver.home.driverstatus.DStatusSwitchContact.Presenter {

    /**
     * 获取休假计划
     */
    @Override
    public void getDriverLeaveStatus() {
        mView.startRefresh();
        mData.getDriverStatus().subscribe(driverStatusSwitchBaseResponse -> {
            mView.setRefreshAdapter(driverStatusSwitchBaseResponse);
            mView.finishRefreshing();
        }, throwable -> mView.finishRefreshing());
    }

    /**
     * 删除休假计划
     *
     * @param id 计划id
     */
    @Override
    public void deleteLeavePlan(@NonNull String id) {

        mView.showProgressDialog("请等待...");
        mData.deleteLeavePlan(checkNotNull(id)).subscribe(stringBaseResponse -> {
            List list = new ArrayList();
            mView.deleteLeavePlanSuccess();
            mView.refreshAdapter(list, false);
            mView.hideDialog();
        }, throwable -> mView.hideDialog());
    }

    /**
     * 更新休假计划
     *
     * @param id       计划id
     * @param endDate  结束时间
     * @param sendFlag 是否可向我派单
     * @return 成功失败标识
     */
    @Override
    public void updateLeavePlan(@NonNull String id, @NonNull String endDate, @NonNull String sendFlag) {

        mView.showProgressDialog("请等待...");
        mData.updateLeavePlan(checkNotNull(id), checkNotNull(endDate), checkNotNull(sendFlag)).subscribe(stringBaseResponse -> {
            mView.updateSuccess();
            getDriverLeaveStatus();
            mView.hideDialog();
        }, throwable -> mView.hideDialog());
    }

    @Override
    public void subscribe() {

    }
}
