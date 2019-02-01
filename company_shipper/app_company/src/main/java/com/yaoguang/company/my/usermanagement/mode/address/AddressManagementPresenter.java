package com.yaoguang.company.my.usermanagement.mode.address;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.MemberDataSource;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/12/6.
 */

public class AddressManagementPresenter extends BasePresenterListCondition<String, UserLoginAllowLocation> implements AddressManagementContract.Presenter {

    private AddressManagementContract.View mView;
    private MemberDataSource mMemberDataSource = new MemberDataSource();

    AddressManagementPresenter(AddressManagementContract.View view) {
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
}
