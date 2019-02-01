package com.yaoguang.driver.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.base.baseview.BaseView;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderProgressWrapper;
import com.yaoguang.greendao.entity.DriverStatusSwitch;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页MVP
 * Created by wly on 2017/7/6 0006.
 */

public interface HomeContacts {
    interface View extends BaseView {

        void showOrderProgress(@Nullable DriverOrderProgressWrapper value);

        void showBannerImage(@NonNull ArrayList<BannerPic> result);

        void toScrollHeadView();

        void hideSRLLoadingView();

        void toLoginActivity();

        void startOrderNodeMapFragment(@NonNull List<DriverOrderNode> result,@NonNull  String id);

        void showDriverLeaveStatus(@NonNull BaseResponse<DriverStatusSwitch> value);
    }


    abstract class Presenter extends BasePresenter<View, DriverRepository> {

        abstract void getBanner();

        abstract void getCurrentProgress();

        abstract void getDriverLeaveStatus();

        abstract void gotoMap(@NonNull String orderId, @NonNull String id);
    }
}
