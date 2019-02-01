package com.yaoguang.datasource.driver;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.api.FeedbackApi;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
public class AppDataSource {

    private static AppDataSource instance;

    public static AppDataSource getInstance() {
        if (instance == null) {
            synchronized (AppDataSource.class) {
                if (instance == null) {
                    instance = new AppDataSource();
                }
            }
        }
        return instance;
    }

    /**
     * 意见反馈
     * @param msg           消息
     * @param contactWay    联系方式
     * @param codeType      app类型
     * @param feedbackPic   图片
     */
    public Observable<BaseResponse<String>> sendFeedback(final String msg, final String contactWay, final String codeType, final String feedbackPic) {
        String id = null;
        String appType = null;
        if (codeType.equals(Constants.APP_COMPANY)){
            id = DataStatic.getInstance().getAppUserWrapper().getId();
            appType = "2";
        }else if(codeType.equals(Constants.APP_SHIPPER)){
            id = DataStatic.getInstance().getUserOwner().getId();
            appType = "3";
        }else if(codeType.equals(Constants.APP_DRIVER)){
            id = DataStatic.getInstance().getDriver().getId();
            appType = "1";
        }

        return Api.getInstance().retrofit.create(FeedbackApi.class).addFeedback(id, appType, msg, contactWay, "0", feedbackPic);
    }

    public Observable<BaseResponse<String>> sendFeedbackNew(String feedbackPic, int platformType, int appType, int feedbackType, String advice, String userId) {
        return Api.getInstance().retrofit.create(FeedbackApi.class).addFeedbackNew(feedbackPic,platformType,appType,feedbackType,advice,userId);

    }

}
