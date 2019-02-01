package com.yaoguang.driver.phone.home.driverstatus;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yaoguang.datasource.driver.RestPlanDataSource;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 韦理英
 * on 2017/6/21 0021.
 */

public class StatusSwitchPresenter extends BasePresenter implements StatusSwitchContact.Presenter {

    private StatusSwitchContact.View mView;
    private RestPlanDataSource mRestPlanDataSource = new RestPlanDataSource();

    StatusSwitchPresenter(StatusSwitchContact.View view, Context context) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    /**
     * 获取休假计划
     */
    @Override
    public void getDriverLeaveStatus() {
        // 获取本地的休假计划
        mRestPlanDataSource.getDriverStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<DriverStatusSwitch>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<DriverStatusSwitch> response) {
                        mView.setRefreshAdapter(response);
                    }

                });
    }

    /**
     * 删除休假计划
     *
     * @param id 计划id
     */
    @Override
    public void deleteLeavePlan(@NonNull String id) {

        mRestPlanDataSource.deleteLeavePlan(checkNotNull(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        List list = new ArrayList();
                        mView.deleteLeavePlanSuccess();
                        mView.refreshAdapter(list);
                        mView.hideProgressDialog();
                    }

                });
    }

    /**
     * 更新休假计划
     *
     * @param id       计划id
     * @param endDate  结束时间
     * @param sendFlag 是否可向我派单
     */
    @Override
    public void updateLeavePlan(@NonNull String id, @NonNull String endDate, @NonNull String sendFlag) {

        mRestPlanDataSource.updateLeavePlan(checkNotNull(id), checkNotNull(endDate), checkNotNull(sendFlag))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
            @Override
            public void onSuccess(BaseResponse<String> response) {
                mView.updateSuccess();
                getDriverLeaveStatus();
                mView.hideProgressDialog();
            }
        });

    }


}
