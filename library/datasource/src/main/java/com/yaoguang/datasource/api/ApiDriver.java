package com.yaoguang.datasource.api;

import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.Contact;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.greendao.entity.driver.DriverAuthentication;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;

import io.reactivex.Observable;
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
     * 获取认证信息
     *
     * @param driverId 司机id
     */
    @POST("driver/getInfo.do?")
    Observable<BaseResponse<DriverAuthentication>> getAuthentication(@Query("driverId") String driverId);



    /**
     * 登录
     *
     * @param username 用户
     * @param password 密码
     */
    @GET("driver/login.do?")
    Observable<BaseResponse<LoginDriver>> login(@Query("username") String username, @Query("password") String password, @Query("deviceToken") String deviceToken
            , @Query("loginMachine") String loginMachine, @Query("loginMachineVersion") String loginMachineVersion, @Query("loginAppVersion") String loginAppVersion);


    /**
     * 注册
     *
     * @param authCode 验证码
     */
    @POST("register/driver.do?")
    Observable<BaseResponse<Driver>> register(@Body Driver driver, @Query("authCode") String authCode);


    /**
     * 关联 – 待审核的公司信息列表
     *
     * @param userId    司机id
     * @param type      0司机 1物流 2货主
     * @param pageIndex 页码
     * @return 列表
     */
    @GET("contact/wait/list.do?")
    Observable<BaseResponse<PageList<Contact>>> contactWaitList(@Query("userId") String userId, @Query("type") String type, @Query("pageIndex") int pageIndex);

    /**
     * 关联 – 审核不通过的公司信息列表
     *
     * @param userId    司机id
     * @param type      0司机 1物流 2货主
     * @param pageIndex 页码
     * @return 列表
     */
    @GET("contact/reject/list.do?")
    Observable<BaseResponse<PageList<Contact>>> contactRejectList(@Query("userId") String userId, @Query("type") String type, @Query("pageIndex") int pageIndex);

    /**
     * 关联 – 审核通过的公司信息列表
     *
     * @param userId    司机id
     * @param type      0司机 1物流 2货主
     * @param pageIndex 页码
     * @return 列表
     */
    @GET("contact/pass/list.do?")
    Observable<BaseResponse<PageList<Contact>>> contactPassList(@Query("userId") String userId, @Query("type") String type, @Query("pageIndex") int pageIndex);

    /**
     * 关联企业
     *
     * @param userId 司机id
     * @param type   0司机 1物流 2货主
     * @param reason 申请理由
     * @return 列表
     */
    @GET("contact/add.do?")
    Observable<BaseResponse<String>> contactAdd(@Query("userId") String userId, @Query("companyId") String companyId, @Query("auditRemark") String reason, @Query("type") String type);

    /**
     * 取消关联企业
     *
     * @param userId    司机id
     * @param contactId 关联表id
     * @param type      0司机 1物流 2货主
     * @return 列表
     */
    @GET("contact/delete.do?")
    Observable<BaseResponse<String>> contactDelete(@Query("userId") String userId, @Query("contactId") String contactId, @Query("type") String type);

    /**
     * 删除关联企业
     *
     * @param id 关联表Contact id
     * @return 列表
     */
    @GET("contact/deleteApply.do?")
    Observable<BaseResponse<String>> contactDeleteApply(@Query("id") String id);

    /**
     * 获取司机信息
     *
     * @param driverId 司机id
     * @return json
     */
    @GET("driver/getInfo.do?")
    Observable<BaseResponse<LoginDriver>> getInfo(@Query("driverId") String driverId);

    /**
     * 修改司机信息
     */
    @POST("driver/update.do?")
    Observable<BaseResponse<String>> update(@Body Driver driver);

    /**
     * 修改手机号
     *
     * @param driverId 司机id
     * @param mobile   新手机号
     * @param authCode 验证码
     * @return json
     */
    @GET("driver/updateMobile.do?")
    Observable<BaseResponse<String>> updateMobile(@Query("driverId") String driverId, @Query("mobile") String mobile, @Query("authCode") String authCode, @Query("password") String password);

    /**
     * 修改密码
     *
     * @param userId      用户id
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @return json
     */
    @GET("driver/changeLoginPassword.do?")
    Observable<BaseResponse<String>> changeLoginPassword(@Query("userId") String userId, @Query("newPassword") String newPassword, @Query("oldPassword") String oldPassword);

    /**
     * 登录-忘记密码修改-验证验证码
     *
     * @param phone    手机号
     * @param authCode 验证码
     * @return 返回信息
     */
    @GET("driver/checkAuthCode.do?")
    Observable<BaseResponse<String>> checkAuthCode(@Query("phone") String phone, @Query("authCode") String authCode);

    /**
     * 登录-忘记密码修改
     *
     * @param userId   手机
     * @param password 密码
     * @param authCode 验证码
     * @return 返回信息
     */
    @GET("driver/modifyLoginPassword.do?")
    Observable<BaseResponse<String>> changePassword(@Query("phone") String userId, @Query("password") String password, @Query("authCode") String authCode);




    /**
     * 推送开启与关闭
     *
     * @param driverId 司机id
     * @return json
     */
    @GET("driver/updateMessageFlag.do?")
    Observable<BaseResponse<String>> updateMessageFlag(@Query("driverId") String driverId, @Query("messageFlag") int messageFlag);

    /**
     * 验证注册
     *
     * @return 返回登录对象
     */
    @GET("register/driver/simple.do")
    Observable<BaseResponse<String>> simpleRegister(
            @Query("mobile") String mobile, @Query("password") String password,
            @Query("authCode") String authCode);



    /**
     * 获取首页的数据
     *
     * @param condition 司机id
     * @param pageIndex 页码
     * @param pageCount 页数
     * @return
     */
    @POST("driver/index/home.do?")
    Observable<BaseResponse<String>> home(@Query("driverId") String condition, @Query("pageIndex") String pageIndex, @Query("pageCount") String pageCount);
}
