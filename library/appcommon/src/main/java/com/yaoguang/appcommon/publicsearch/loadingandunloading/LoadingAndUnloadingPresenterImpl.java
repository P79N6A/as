package com.yaoguang.appcommon.publicsearch.loadingandunloading;

import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2017/8/11.
 */

public class LoadingAndUnloadingPresenterImpl implements LoadingAndUnloadingContact.LoadingAndUnloadingPresenter {

    private LoadingAndUnloadingContact.LoadingAndUnloadingView mLoadingAndUnloadingView;
    private CompositeDisposable mCompositeDisposable;
    private LoadingAndUnloadingContact.LoadingAndUnloadingInteractor mLoadingAndUnloadingInteractor;

    public LoadingAndUnloadingPresenterImpl(LoadingAndUnloadingContact.LoadingAndUnloadingView loadingAndUnloadingView) {
        this.mLoadingAndUnloadingView = loadingAndUnloadingView;
        mLoadingAndUnloadingInteractor = new LoadingAndUnloadingInteractorImpl();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void deleteAddress(String ids, String codeId) {
        mLoadingAndUnloadingInteractor.deleteAddress(ids,codeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        mLoadingAndUnloadingView.showProgress();
                    }

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mLoadingAndUnloadingView.startRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mLoadingAndUnloadingView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mLoadingAndUnloadingView.hideProgress();
                    }


                });


    }



}
