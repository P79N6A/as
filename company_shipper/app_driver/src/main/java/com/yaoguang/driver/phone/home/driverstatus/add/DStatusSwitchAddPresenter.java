package com.yaoguang.driver.phone.home.driverstatus.add;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.RestPlanDataSource;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.interactor.driver.BasePresenterImplV2;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 韦理英
 * on 2017/6/21 0021.
 */
public class DStatusSwitchAddPresenter extends BasePresenterImplV2 implements DStatusSwitchAddContact.Presenter {
    RestPlanDataSource mRestPlanDataSource = new RestPlanDataSource();
    DStatusSwitchAddContact.View view;

    public DStatusSwitchAddPresenter(DStatusSwitchAddContact.View view) {
        this.view = view;
    }

    @Override
    public void saveLeaveStatus(DriverOrderNodeDetail driverOrderNodeDetail, final UserDriverStatusDetail userDriverStatusDetail) {
        mRestPlanDataSource.addDriverRestPlan(userDriverStatusDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        // 保存到本地
                        Driver driver = DataStatic.getInstance().getDriver();
                        driver.setStatus(userDriverStatusDetail.getStatus());
                        DataStatic.getInstance().setDriver(driver);

                        view.setStatusSuccess(userDriverStatusDetail);
                    }

                });
    }
}
