package com.yaoguang.driver.net.api;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Contact;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.greendao.entity.UserOffice;
import com.yaoguang.greendao.entity.driver.DriverAuthentication;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;
import com.yaoguang.lib.annotation.apt.ApiDriverAnnotation;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 司机api
 * Created by wly on 2016/3/23.
 */

public interface ApiDriver {



    /**
     * 认证资料提交/保存
     * @param driver 认证资料(json)
     * @param authType (lic,car,real)
     * @param operateType 0:保存 1:提交
     * @param driver 认证实体
     */
    @POST("driver/auth/submit.do?")
    Flowable<BaseResponse<String>> setAuthentication(@Body Driver driver, @Query("authType") String authType, @Query("operateType") String operateType);

    /**
     * 认证资料提交/保存
     * @param userDriverCar 认证资料(json)
     * @param authType (lic,car,real)
     * @param operateType 0:保存 1:提交
     */
    @POST("driver/auth/submit.do?")
    Flowable<BaseResponse<String>> setAuthentication(@Body UserDriverCar userDriverCar, @Query("authType") String authType, @Query("operateType") String operateType);

    /**
     * 认证资料提交/保存
     * @param userDriverLicence 认证资料(json)
     * @param authType (lic,car,real)
     * @param operateType 0:保存 1:提交
     */
    @POST("driver/auth/submit.do?")
    Flowable<BaseResponse<String>> setAuthentication(@Body UserDriverLicence userDriverLicence, @Query("authType") String authType, @Query("operateType") String operateType);

    /**
     * 获取认证信息
     * @param driverId 司机id
     */
    @POST("driver/getInfo.do?")
    Flowable<BaseResponse<DriverAuthentication>> getAuthentication(@Query("driverId") String driverId);

    /**
     * 获取司机工作状态
     * @param driverId 司机id
     */
    @POST("driver/restPlan/current.do?")
    Flowable<BaseResponse<DriverStatusSwitch>>  getDriverStatus(@Query("driverId") String driverId);

    /**
     * 删除休假计划
     *
     * @return 返回登录对象
     */
    @POST("driver/restPlan/delete.do?")
    Flowable<BaseResponse<String>>  deleteLeavePlan(@Query("id") String id);

    /**
     * 登录
     *
     * @param username 用户
     * @param password 密码
     */
    @GET("driver/login.do?")
    Flowable<BaseResponse<LoginResult>> login(@Query("username") String username, @Query("password") String password, @Query("deviceToken") String deviceToken
            , @Query("loginMachine") String loginMachine, @Query("loginMachineVersion") String loginMachineVersion, @Query("loginAppVersion") String loginAppVersion);


    /**
     * 注册
     *
     * @param authCode 验证码
     */
    @POST("register/driver.do?")
    Flowable<BaseResponse<Driver>> register(@Body Driver driver, @Query("authCode") String authCode);


    /**
     * 关联 – 待审核的公司信息列表
     *
     * @param userId    司机id
     * @param type      0司机 1物流 2货主
     * @param pageIndex 页码
     * @return 列表
     */
    @GET("contact/wait/list.do?")
    Flowable<BaseResponse<PageList<Contact>>> contactWaitList(@Query("userId") String userId, @Query("type") String type, @Query("pageIndex") int pageIndex);

    /**
     * 关联 – 审核不通过的公司信息列表
     *
     * @param userId    司机id
     * @param type      0司机 1物流 2货主
     * @param pageIndex 页码
     * @return 列表
     */
    @GET("contact/reject/list.do?")
    Flowable<BaseResponse<PageList<Contact>>> contactRejectList(@Query("userId") String userId, @Query("type") String type,  @Query("pageIndex") int pageIndex);

    /**
     * 关联 – 审核通过的公司信息列表
     *
     * @param userId    司机id
     * @param type      0司机 1物流 2货主
     * @param pageIndex 页码
     * @return 列表
     */
    @GET("contact/pass/list.do?")
    Flowable<BaseResponse<PageList<Contact>>> contactPassList(@Query("userId") String userId, @Query("type") String type, @Query("pageIndex") int pageIndex);

    /**
     * 关联企业
     *
     * @param userId 司机id
     * @param type   0司机 1物流 2货主
     * @param reason 申请理由
     * @return 列表
     */
    @GET("contact/add.do?")
    Flowable<BaseResponse<String>> contactAdd(@Query("userId") String userId, @Query("companyId") String companyId, @Query("auditRemark") String reason, @Query("type") String type);

    /**
     * 取消关联企业
     *
     * @param userId    司机id
     * @param contactId 关联表id
     * @param type      0司机 1物流 2货主
     * @return 列表
     */
    @GET("contact/delete.do?")
    Flowable<BaseResponse<String>> contactDelete(@Query("userId") String userId, @Query("contactId") String contactId, @Query("type") String type);

    /**
     * 删除关联企业
     *
     * @param id 关联表Contact id
     * @return 列表
     */
    @GET("contact/deleteApply.do?")
    Flowable<BaseResponse<String>> contactDeleteApply(@Query("id") String id);

    /**
     * 获取司机信息
     *
     * @param driverId 司机id
     * @return json
     */
    @GET("driver/getInfo.do?")
    Flowable<BaseResponse<LoginResult>> getInfo(@Query("driverId") String driverId);

    /**
     * 修改司机信息
     */
    @POST("driver/update.do?")
    Flowable<BaseResponse<String>> update(@Body Driver driver);

    /**
     * 修改手机号
     *
     * @param driverId 司机id
     * @param mobile   新手机号
     * @param authCode 验证码
     * @return json
     */
    @GET("driver/updateMobile.do?")
    Flowable<BaseResponse<String>> updateMobile(@Query("driverId") String driverId, @Query("mobile") String mobile, @Query("authCode") String authCode, @Query("password") String password);

    /**
     * 修改密码
     *
     * @param userId      用户id
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @return json
     */
    @GET("driver/changeLoginPassword.do?")
    Flowable<BaseResponse<String>> changeLoginPassword(@Query("userId") String userId, @Query("newPassword") String newPassword, @Query("oldPassword") String oldPassword);

    /**
     * 登录-忘记密码修改-验证验证码
     * @param phone 手机号
     * @param authCode 验证码
     * @return 返回信息
     */
    @GET("driver/checkAuthCode.do?")
    Flowable<BaseResponse<String>> checkAuthCode(@Query("phone") String phone, @Query("authCode") String authCode);

    /**
     * 登录-忘记密码修改
     * @param userId 手机
     * @param password 密码
     * @param authCode 验证码
     * @return 返回信息
     */
    @GET("driver/modifyLoginPassword.do?")
    Flowable<BaseResponse<String>> changePassword(@Query("phone") String userId, @Query("password") String password, @Query("authCode") String authCode);

    /**
     * 更新司机工作状态
     *
     * @param userDriverStatusDetail 状态实体
     * @return json
     */
    @POST("driver/restPlan/add.do")
    Flowable<BaseResponse<String>> addDriverRestPlan(@Body UserDriverStatusDetail userDriverStatusDetail);


    /**
     * 推送开启与关闭
     *
     * @param driverId 司机id
     * @return json
     */
    @GET("driver/updateMessageFlag.do?")
    Flowable<BaseResponse<String>> updateMessageFlag(@Query("driverId") String driverId, @Query("messageFlag") int messageFlag);

    /**
     * 验证注册
     *
     * @return 返回登录对象
     */
    @GET("register/driver/simple.do")
    Flowable<BaseResponse<String>> simpleRegister(
            @Query("mobile") String mobile, @Query("password") String password,
            @Query("authCode") String authCode);

    /**
     * 获取公司信息
     *
     * @param userId 用户id
     * @param companyId 公司id
     * @param type 0 司机 1物流 2 货主
     * @return 返回登录对象
     */
    @GET("contact/getCompanyInfo.do?")
    Flowable<BaseResponse<UserOffice>> getCompanyInfo(@Query("userId") String userId, @Query("companyId") String companyId, @Query("type") String type);

    /**
     * 更新休假计划
     * @param id        计划id
     * @param endDate   结束时间
     * @param sendFlag  是否可向我派单
     * @return 成功失败标识
     */
    @POST("driver/restPlan/update.do?")
    Flowable<BaseResponse<String>> updateLeavePlan(@Query("id") String id, @Query("endDate")String endDate, @Query("sendFlag")String sendFlag);
        }
