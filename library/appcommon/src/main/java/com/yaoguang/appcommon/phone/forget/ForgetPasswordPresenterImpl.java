package com.yaoguang.appcommon.phone.forget;

import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterContact;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.CPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.DPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.SPersonalCenterInteractorImpl;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.appcommon.phone.forget.company.CForgetPasswordInteractorImpl;
import com.yaoguang.appcommon.phone.forget.driver.DForgetPasswordInteractorImpl;
import com.yaoguang.appcommon.phone.forget.shipper.SForgetPasswordInteractorImpl;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2017/7/4.
 */
public class ForgetPasswordPresenterImpl<T> implements ForgetPasswordContact.ForgetPasswordPresenter<T> {

    private String mCodeType;
    protected ForgetPasswordContact.ForgetPasswordViewStep1<T> mViewStep1;
    protected ForgetPasswordContact.ForgetPasswordViewStep2<T> mViewStep2;
    ForgetPasswordContact.ForgetPasswordInteractor mInteractor;
    PersonalCenterContact.PersonalCenterInteractor mPersonalCenterInteractor;
    protected CompositeDisposable mCompositeDisposable;


    public ForgetPasswordPresenterImpl(ForgetPasswordContact.ForgetPasswordViewStep1<T> viewStep1, ForgetPasswordContact.ForgetPasswordViewStep2<T> view, String codeType) {
        mViewStep1 = viewStep1;
        mViewStep2 = view;
        mCompositeDisposable = new CompositeDisposable();
        mCodeType = codeType;
        switch (mCodeType) {
            case Constants.APP_COMPANY:
                mInteractor = new CForgetPasswordInteractorImpl();
                mPersonalCenterInteractor = new CPersonalCenterInteractorImpl();
                break;
            case Constants.APP_SHIPPER:
                mInteractor = new SForgetPasswordInteractorImpl();
                mPersonalCenterInteractor = new SPersonalCenterInteractorImpl();
                break;
            case Constants.APP_DRIVER:
                mInteractor = new DForgetPasswordInteractorImpl();
                mPersonalCenterInteractor = new DPersonalCenterInteractorImpl();
                break;
        }
    }

    @Override
    public void checkAuthCode(String mobile, String autoCode) {
        Observable<BaseResponse<String>> observable = mInteractor.checkAuthCode(mobile, autoCode);
        observable.subscribeOn(Schedulers.io())
                .delay(1200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mViewStep1, true) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mViewStep1.success();
                    }


                });
    }

    @Override
    public void modifyLoginPasswordType0(String newPass, String oldPass) {
        Observable<BaseResponse<String>> observable = mPersonalCenterInteractor.changePassword(null, newPass, oldPass);
        observable.subscribeOn(Schedulers.io())
                .delay(1200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mViewStep2, true) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mViewStep2.onModitySuccessType0();
                    }

                });
    }

    @Override
    public void modifyLoginPasswordType1(String mobile, String password, String autoCode) {
        Observable<BaseResponse<String>> observable = mInteractor.handleForgotPassword(mobile, autoCode, password, password);
        observable.subscribeOn(Schedulers.io())
                .delay(1200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mViewStep2, true) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mViewStep2.onModitySuccessType1();
                    }

                });
    }

    @Override
    public void modifyLoginPasswordType2(String mId, String mobile, final String newPass, String oldPass) {
        Observable<BaseResponse<String>> observable = mPersonalCenterInteractor.changePassword(mId, newPass, oldPass);
        observable.subscribeOn(Schedulers.io())
                .delay(1200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())


                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mViewStep2, true) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mViewStep2.onModitySuccessType2(newPass);
                    }

                });
    }


    @Override
    public void getVerficationCode(String strMobile) {
        mInteractor.getVerficationCode(strMobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        mViewStep1.startShowCountdown();
                    }

                    @Override
                    public void onSuccess(BaseResponse<String> response) {

                    }

                    @Override
                    public void onFail(BaseResponse<String> response) {
                        super.onFail(response);
                        mViewStep1.stopShowCountdown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mViewStep1.stopShowCountdown();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mViewStep1.hideProgressDialog();
                    }

                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
