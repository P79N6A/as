package com.yaoguang.interactor.common.my.companyoinfo;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interactor.company.my.CCompanyInfoInteractorImpl;
import com.yaoguang.interactor.shipper.my.SCompanyInfoInteractorImpl;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2017/7/13.
 */
public class CompanyInfoPresenterImpl<T> implements CompanyInfoContact.CompanyInfoPresenter {

    private String mCodeType;
    protected CompanyInfoContact.CompanyInfoView<T> mView;
    CompanyInfoContact.CompanyInfoInteractor<T> mInteractor;
    protected CompositeDisposable mCompositeDisposable;

    protected T model;

    public CompanyInfoPresenterImpl(CompanyInfoContact.CompanyInfoView<T> view, String codeType) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        mCodeType = codeType;
        switch (mCodeType) {
            case Constants.APP_COMPANY:
                mInteractor = new CCompanyInfoInteractorImpl();
                break;
            case Constants.APP_SHIPPER:
                mInteractor = new SCompanyInfoInteractorImpl();
                break;
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
        mInteractor.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<T>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<T> response) {
                        model = response.getResult();
                        mView.showData(response.getResult());
                    }

                });
    }

    @Override
    public void modifyPhoto(String url) {
        mInteractor.modifyPhoto(model, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<BaseResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mView.showProgressDialog("处理中，请稍候");
                    }

                    @Override
                    public void onNext(BaseResponse<String> value) {
                        if (!value.getState().equals("200")) {
                            mView.showToast(value.getMessage());
                            return;
                        }
                        initData();
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showToast("出错啦" + e.getMessage());
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideProgressDialog();
                    }
                });

    }

    @Override
    public void modifyLog(String url) {
        mInteractor.modifyLog(model, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<BaseResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mView.showProgressDialog("处理中，请稍候");
                    }

                    @Override
                    public void onNext(BaseResponse<String> value) {
                        if (!value.getState().equals("200")) {
                            mView.showToast(value.getMessage());
                            return;
                        }
                        initData();
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showToast("出错啦" + e.getMessage());
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void modifyRemark(String value) {
        mInteractor.modifyShopDetail(model, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<BaseResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mView.showProgressDialog("处理中，请稍候");
                    }

                    @Override
                    public void onNext(BaseResponse<String> value) {
                        if (!value.getState().equals("200")) {
                            mView.showToast(value.getMessage());
                            return;
                        }
                        initData();
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showToast("出错啦" + e.getMessage());
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideProgressDialog();
                    }
                });
    }


}
