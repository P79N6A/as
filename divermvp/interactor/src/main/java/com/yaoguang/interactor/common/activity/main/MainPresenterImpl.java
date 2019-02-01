package com.yaoguang.interactor.common.activity.main;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;

/**
 * Created by zhongjh on 2017/5/25.
 */
public class MainPresenterImpl implements MainContact.MainPresenter {

    private DCSBaseInteractorImpl mDCSBaseInteractorImpl;
    private MainContact.MainView mView;
    private String mCodeType;

    public MainPresenterImpl(MainContact.MainView mainView, String codeType) {
        this.mView = mainView;
        this.mCodeType = codeType;
        mDCSBaseInteractorImpl = new DCSBaseInteractorImpl();
    }

    @Override
    public void openActivity() {
        String id = null;

        if (mCodeType.equals(Constants.APP_DRIVER)) {
            if (mDCSBaseInteractorImpl.getDriver() != null)
                id = mDCSBaseInteractorImpl.getDriver().getId();
        } else if (mCodeType.equals(Constants.APP_SHIPPER)) {
            if (mDCSBaseInteractorImpl.getUserOwner() != null)
                id = mDCSBaseInteractorImpl.getUserOwner().getId();
        } else if (mCodeType.equals(Constants.APP_COMPANY)) {
            if (mDCSBaseInteractorImpl.getAppUserWrapper() != null)
                id = mDCSBaseInteractorImpl.getAppUserWrapper().getId();
        }
        if (id == null) {
            mView.startLoginActivity();
        }

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
