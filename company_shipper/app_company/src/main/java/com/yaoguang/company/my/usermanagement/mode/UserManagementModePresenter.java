package com.yaoguang.company.my.usermanagement.mode;

import com.yaoguang.datasource.company.ManageUserDataSource;
import com.yaoguang.datasource.company.MemberDataSource;
import com.yaoguang.greendao.entity.company.user.ViewUserSetting;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/12/13.
 */

public class UserManagementModePresenter extends BasePresenter implements UserManagementModeContract.Presenter {

    ManageUserDataSource mManageUserDataSource = new ManageUserDataSource();
    MemberDataSource mMemberDataSource = new MemberDataSource();
    UserManagementModeContract.View mView ;

    UserManagementModePresenter(UserManagementModeContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getInfo(String userId){
        mManageUserDataSource.getInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ViewUserSetting>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<ViewUserSetting> response) {
                        mView.setViewUserSetting(response.getResult());
                    }
                });
    }

    @Override
    public void authCancelAuthorization(String id, int position,int type) {
        mMemberDataSource.authCancel(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        switch (type){
                            case 0:
                                mView.authCancelWebAuthorization(position);
                                break;
                            case 1:
                                mView.authCancelCompanyAuthorization(position);
                                break;
                            case 2:
                                mView.authCancelBossAuthorization(position);
                                break;
                        }

                    }
                });
    }

    @Override
    public void comit(ViewUserSetting viewUserSetting) {
        mManageUserDataSource.save(viewUserSetting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.comit(response.getResult());
                    }
                });
    }

}
