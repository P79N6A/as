package com.yaoguang.company.my.usermanagement.list;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.ManageUserDataSource;
import com.yaoguang.greendao.entity.company.user.UserCondition;
import com.yaoguang.greendao.entity.company.user.ViewUser;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/12/12.
 */

public class UserManagementPresenter extends BasePresenterListCondition<UserCondition, ViewUser> implements UserManagementContract.Presenter {

    private UserManagementContract.View mView;
    private ManageUserDataSource mManageUserDataSource = new ManageUserDataSource();

    UserManagementPresenter(UserManagementContract.View view) {
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
    protected Observable<BaseResponse<PageList<ViewUser>>> initDatas(UserCondition condition, int pageIndex) {
        return mManageUserDataSource.userPage(condition,pageIndex);
    }
}
