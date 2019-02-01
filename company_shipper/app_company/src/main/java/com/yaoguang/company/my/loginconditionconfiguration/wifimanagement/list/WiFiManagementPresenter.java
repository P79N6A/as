package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.list;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.MemberDataSource;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowWlan;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/12/6.
 */

public class WiFiManagementPresenter extends BasePresenterListCondition<String, UserLoginAllowWlan> implements WiFiManagementContract.Presenter {

    private WiFiManagementContract.View mView;
    private MemberDataSource mMemberDataSource = new MemberDataSource();

    WiFiManagementPresenter(WiFiManagementContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<UserLoginAllowWlan>>> initDatas(String condition, int pageIndex) {
        return mMemberDataSource.loginWlanPage(pageIndex);
    }

    @Override
    public void remove(String ids) {
        mMemberDataSource.wlanDelete(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.showToast(response.getResult());
                        mView.refreshDataAnimation();
                    }
                });
    }
}
