package com.yaoguang.company.my.loginconditionconfiguration.locationmanagement.list;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.MemberDataSource;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/12/10.
 */
public class LocationManagementPresenter extends BasePresenterListCondition<String, UserLoginAllowLocation> implements LocationManagementContract.Presenter {

    private LocationManagementContract.View mView;
    private MemberDataSource mMemberDataSource = new MemberDataSource();

    LocationManagementPresenter(LocationManagementContract.View view) {
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
    protected Observable<BaseResponse<PageList<UserLoginAllowLocation>>> initDatas(String condition, int pageIndex) {
        return mMemberDataSource.locationPage(pageIndex);
    }

    @Override
    public void remove(String id) {
        mMemberDataSource.locationDelete(id)
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

    @Override
    public void add(UserLoginAllowLocation userLoginAllowLocation) {
        mMemberDataSource.locationSave(userLoginAllowLocation)
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
