package com.yaoguang.interactor.driver.my.setting.impl;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interactor.driver.BasePresenterImplV2;
import com.yaoguang.interactor.driver.my.setting.SettingFragmentView;
import com.yaoguang.interactor.driver.my.setting.SettingInteractor;
import com.yaoguang.interactor.driver.my.setting.SettingPresenter;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class SettingPresenterImpl extends BasePresenterImplV2 implements SettingPresenter {
    private final SettingFragmentView mSettingFragmentView;
    SettingInteractor mSettingInteractor = new SettingInteractorImpl();

    public SettingPresenterImpl(SettingFragmentView settingFragmentView) {
        mSettingFragmentView = settingFragmentView;
    }

    @Override
    public void pushSetting(final boolean isOpen, final String codeType) {
        Observable<BaseResponse<String>> baseResponseObservable = mSettingInteractor.setPush(isOpen, codeType);
        subscribe(baseResponseObservable, mSettingFragmentView, ShowType.NO, new OnCallback() {
            @Override
            public void onNext200(BaseResponse listBaseResponse) {
                mSettingInteractor.setPushInfo(isOpen, codeType);
                super.onNext200(listBaseResponse);
            }
        });
    }

    @Override
    public void subscribe() {

    }
}
