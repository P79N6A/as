package com.yaoguang.appcommon.phone.home;

import android.content.Context;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.AliWeatherComplex;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.SpecialMsg;
import com.yaoguang.greendao.entity.common.SysMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 首页的关联
 * Created by zhongjh on 2018/1/17.
 */
public class HomeContact {

    public interface Presenter extends BasePresenter {
        /**
         * 初始化需要循环Banner的数据源
         */
        void initBannerData();

        /**
         * 初始化需要循环Banner的图片数据源
         */
        void initMessageBannerData();

        /**
         * 初始化未读消息的数据源
         */
        void initMessageData();

//        /**
//         * 初始化天气的数据源
//         */
//        void initWeatherData(double lat, double lon);

        /**
         * 初始化窗口的数据源
         */
        void initDialogData();

        /**
         * 设置成已弹窗
         *
         * @param msgId
         */
        void updatePopUpData(String msgId);
    }

    public interface View extends BaseView {

        /**
         * Banner 动态高度
         */
        void dynamicHeight();

        /**
         * 初始化需要循环Banner的数据源
         */
        void initBannerData();

        /**
         * 初始化需要循环Banner的View
         *
         * @param bannerPics 数据源
         */
        void initBannerView(ArrayList<BannerPic> bannerPics);

        /**
         * 初始化需要循环Banner的图片数据源
         *
         * @param result 数据源
         */
        void initMessageBannerView(List<SpecialMsg> result);

        /**
         * 关闭刷新
         */
        void finishRefreshing();

        /**
         * 设置顶部可以滑动
         */
        void setRefreshLayout();

        /**
         * 初始化信息，如果有未读消息的话，就切换
         */
        void initMessageData();

        /**
         * 初始未读消息的view
         *
         * @param result 数据源
         */
        void initMessageView(HashMap<String, Integer> result);

//        /**
//         * 初始化天气view
//         *
//         * @param aliWeatherComplex 数据源
//         */
//        void initWeatherView(AliWeatherComplex aliWeatherComplex);

//        /**
//         * 初始化天气的数据源
//         *
//         * @param context
//         * @param currentData
//         */
//        void initWeatherData(Context context, Long currentData);

//        /**
//         * 天气文本赋值、比如加载中、加载失败
//         *
//         * @param text
//         */
//        void setWeatherText(String text);

        /**
         * 初始化弹窗的数据源
         */
        void initDialogData();

        /**
         * 初始化弹窗的界面
         *
         * @param result 数据源
         */
        void initDialogDataView(List<SysMsg> result);

        /**
         * 删除缓存消息的第一条
         */
        void removewSysMsg();
    }

}
