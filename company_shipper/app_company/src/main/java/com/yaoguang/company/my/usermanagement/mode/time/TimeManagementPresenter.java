package com.yaoguang.company.my.usermanagement.mode.time;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.MemberDataSource;
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

public class TimeManagementPresenter extends BasePresenterListCondition<String, UserLoginTime> implements TimeManagementContract.Presenter {

    private TimeManagementContract.View mView;
    private MemberDataSource mMemberDataSource = new MemberDataSource();

    TimeManagementPresenter(TimeManagementContract.View view) {
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
    protected Observable<BaseResponse<PageList<UserLoginTime>>> initDatas(String condition, int pageIndex) {
        return mMemberDataSource.userLoginTimePage(condition,pageIndex);
    }
}
