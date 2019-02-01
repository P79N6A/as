package com.yaoguang.company.home.backstagelogon;

import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.AppDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhongjh
 * @Package com.yaoguang.company.home.backstagelogon
 * @Description: 后台登录 控制层
 * @date 2018/01/30
 */
public class BackstageLogonPresenter extends BasePresenter implements BackstageLogonContract.Presenter {

    private BackstageLogonContract.View mView;
    AppDataSource appDataSource = new AppDataSource();

    BackstageLogonPresenter(BackstageLogonContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void backstageLogon(String url) {
        appDataSource.scanCodeLogin(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.showToast(response.getResult());
                        mView.signOut();
                    }

                });
    }


}
