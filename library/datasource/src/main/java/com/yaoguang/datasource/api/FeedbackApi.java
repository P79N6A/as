package com.yaoguang.datasource.api;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.driver.FeedbackApp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public interface FeedbackApi {
    String SYSMSG = "sysMsg";

    /**
     * 添加 意见反馈
     * @param driverId
     * @param appType
     * @param advice
     * @param contactWay
     * @param platformType 0 android 1 ios
     * @return
     */
    @GET(SYSMSG + "/addFeedBack.do?")
    Observable<BaseResponse<String>> addFeedback(@Query("userId") String driverId, @Query("appType") String appType, @Query("advice") String advice, @Query("contactWay") String contactWay, @Query("platformType") String platformType, @Query("feedbackPic;") String feedbackPic);

    /**
     * 添加 意见反馈 新接口
     * @param feedbackPic   图片
     * @param platformType  平台类型(0:Android 1:iOS)
     * @param appType       应用类型（1：司机端 2：物流 3：货主）
     * @param feedbackType  反馈类型,多选 (功能故障或不可用1,产品建议2,其他4)
     * @param advice        反馈内容
     * @param userId        反馈人
     * @return
     */
    @POST("feedback/submit.do?")
    Observable<BaseResponse<String>> addFeedbackNew(@Query("imgUrl")String feedbackPic, @Query("platformType")int platformType, @Query("appType")int appType, @Query("feedbackType")int feedbackType, @Query("advice")String advice, @Query("userId")String userId);
}
