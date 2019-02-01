package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.nearwifimanagement.list;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.MemberDataSource;
import com.yaoguang.datasource.company.RecentlyDataSource;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlan;
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

public class NearWiFiManagementPresenter extends BasePresenterListCondition<String, UserRecentlyUsedWlan> implements NearWiFiManagementContract.Presenter {

    private NearWiFiManagementContract.View mView;
    private RecentlyDataSource mRecentlyDataSource = new RecentlyDataSource();

    NearWiFiManagementPresenter(NearWiFiManagementContract.View view) {
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
    protected Observable<BaseResponse<PageList<UserRecentlyUsedWlan>>> initDatas(String condition, int pageIndex) {
        return mRecentlyDataSource.wlanLogPage(pageIndex, condition);
    }
}
