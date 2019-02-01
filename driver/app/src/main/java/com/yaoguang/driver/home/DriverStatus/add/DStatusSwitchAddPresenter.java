package com.yaoguang.driver.home.driverstatus.add;

import android.support.annotation.NonNull;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.interactor.driver.BasePresenterImplV2;

import io.reactivex.functions.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 韦理英
 * on 2017/6/21 0021.
 */

public class DStatusSwitchAddPresenter extends BasePresenterImplV2 implements DStatusSwitchAddContact.Presenter {
    DriverRepository mDriverRepository;
    DStatusSwitchAddContact.View view;

    public DStatusSwitchAddPresenter(DStatusSwitchAddContact.View view, @NonNull DriverRepository driverRepository) {
        this.view = view;
        mDriverRepository = checkNotNull(driverRepository);
    }

    @Override
    public void saveLeaveStatus(DriverOrderNodeDetail driverOrderNodeDetail, final UserDriverStatusDetail userDriverStatusDetail) {
        mCompositeDisposable.add(mDriverRepository.addDriverRestPlan(userDriverStatusDetail).subscribe(new Consumer<BaseResponse<String>>() {
            @Override
            public void accept(BaseResponse<String> stringBaseResponse) throws Exception {
                // 保存到本地
                LoginResult loginResult = mDriverRepository.getLoginResult();
                loginResult.getUserInfo().setStatus(userDriverStatusDetail.getStatus());
                mDriverRepository.saveOrUpdate(loginResult);
                view.setStatusSuccess(userDriverStatusDetail);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
    }
}
