package com.yaoguang.appcommon.phone.my.presonalcenter;

import android.text.TextUtils;

import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.CPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.DPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.SPersonalCenterInteractorImpl;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.RegExpValidatorUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interactor.driver.verficationcode.impl.SupportVerficationCodePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人中心控制层
 * Created by zhongjh on 2017/7/5.
 */
public class PersonalCenterPresenterImpl<T> extends SupportVerficationCodePresenter implements PersonalCenterContact.PersonalCenterPresenter<T> {

    private String mCodeType;
    protected PersonalCenterContact.PersonalCenterView<T> mView;
    PersonalCenterContact.PersonalCenterInteractor mInteractor;
    protected CompositeDisposable mCompositeDisposable;


    public PersonalCenterPresenterImpl(PersonalCenterContact.PersonalCenterView<T> view, String codeType) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        mCodeType = codeType;
        switch (mCodeType) {
            case Constants.APP_COMPANY:
                mInteractor = new CPersonalCenterInteractorImpl();
                break;
            case Constants.APP_SHIPPER:
                mInteractor = new SPersonalCenterInteractorImpl();
                break;
            case Constants.APP_DRIVER:
                mInteractor = new DPersonalCenterInteractorImpl();
        }
    }

    @Override
    public void subscribe() {
        initData();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void initData() {
        mView.showData((T) mInteractor.getInfo());
    }

    @Override
    public boolean isRightPhone(String newMobile) {
        if (TextUtils.isEmpty(newMobile) || !RegExpValidatorUtils.isMobile(newMobile)) {
            mView.showToast("新的手机号输入错误，请重新输入");
            return false;
        }
        return true;
    }

    @Override
    public void modifyPhoto(final String s) {
        update(mInteractor.modifyPhoto(s), new Runnable() {
            @Override
            public void run() {
                mInteractor.modifySqlPhoto(s);
            }
        });
    }

    @Override
    public void modifyBackgroundPicture(final String s) {
        update(mInteractor.modifyBackgroundPicture(s), new Runnable() {
            @Override
            public void run() {
                mInteractor.modifyBackgroundPicture(s);
            }
        });
    }

    @Override
    public void modifyMobile(String s) {
        mInteractor.modifySqlMobile(s);
    }

    @Override
    public void modifyEmail(String s) {
        mInteractor.modifySqlEmail(s);
    }

    @Override
    public void modifyQQ(String s) {
        mInteractor.modifySqlQQ(s);
    }

    @Override
    public void modifySign(String s) {
        mInteractor.modifySqlSign(s);
    }

    private void update(Observable<BaseResponse<String>> value, final Runnable runnable) {

        value.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable,mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        initData();
                        runnable.run();
                    }

                });

    }

}
