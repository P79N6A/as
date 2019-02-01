package com.yaoguang.driver.phone.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.driver.DriverOrderProgressWrapper;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;

/**
 * 首页
 * Created by wly on 2017/7/6 0006.
 * <p>
 * Update zhongjh
 * Data 2018/03/14
 */

public interface HomeContacts {
    interface View extends BaseView {

        void showOrderProgress(@Nullable DriverOrderProgressWrapper value);

        /**
         * 天气ui
         */
        void initWeather();

        void showBannerImage(@NonNull ArrayList<BannerPic> result);

        void hideSRLLoadingView();

        void toLoginActivity();

        void showDriverLeaveStatus(@NonNull BaseResponse<DriverStatusSwitch> value);

        /**
         * 显示消息列表
         *
         * @param result    数据源
         * @param pageIndex 页码
         */
        void showMessageList(PageList<DriverOrderMsgWrapper> result, int pageIndex);

        /**
         * 刷新标题
         * @param driverContactCompany 最新关联的公司
         */
        void refreshTitle(DriverContactCompany driverContactCompany);
    }


    interface Presenter extends BasePresenter {

        void getBanner();

        void getCurrentProgress();

        void getDriverLeaveStatus();

        /**
         * 获取消息列表
         *
         * @param pageIndex 页码
         */
        void getMessageList(int pageIndex);

        /**
         * 设置已读
         *
         * @param ids
         */
        void readBatch(String ids);

        /**
         * 获取所有关联的公司
         */
        void getContactAll();
    }

}
