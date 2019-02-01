package com.yaoguang.datasource.api.company;

import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.greendao.entity.company.QRCode;
import com.yaoguang.greendao.entity.company.ScanCodeResult;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppLoginUser;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.AppPriceTruckWrapper;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.greendao.entity.PriceTruckCondition;
import com.yaoguang.greendao.entity.UserApply;
import com.yaoguang.greendao.entity.UserDemo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 企业api
 * Created by zhongjh on 2017/4/6.
 */
public interface CompanyApi {
    String COMPANY = "company";

    /**
     * 登录
     *
     * @param username            用户名称
     * @param password            密码
     * @param deviceToken         经过推送加密的唯一码
     * @param loginMachine        手机信息
     * @param loginMachineVersion 手机信息
     * @param loginAppVersion     手机信息
     * @return 返回登录对象
     */
    @GET(COMPANY + "/login.do?")
    Observable<BaseResponse<AppUserWrapper>> login(
            @Query("username") String username, @Query("password") String password,
            @Query("deviceToken") String deviceToken, @Query("loginMachine") String loginMachine, @Query("loginMachineVersion") String loginMachineVersion, @Query("loginAppVersion") String loginAppVersion);

    /**
     * 登录版本2
     *
     * @param username            用户名称
     * @param password            密码
     * @param deviceToken         经过推送加密的唯一码
     * @param loginMachine        手机信息
     * @param loginMachineVersion 手机信息
     * @param loginAppVersion     手机信息
     * @return 返回登录对象
     */
    @GET(COMPANY + "/loginVersion2.do?")
    Observable<BaseResponse<AppLoginUser>> loginVersion2(
            @Query("username") String username, @Query("password") String password,
            @Query("deviceToken") String deviceToken, @Query("loginMachine") String loginMachine, @Query("loginMachineVersion") String loginMachineVersion, @Query("loginAppVersion") String loginAppVersion);

    /**
     * 安全验证
     *
     * @param userId      用户id
     * @param oldPassword 密码
     * @return
     */
    @GET(COMPANY + "/user/checkOldPassword.do?")
    Observable<BaseResponse<String>> checkOldPassword(@Query("userId") String userId, @Query("oldPassword") String oldPassword);

    /**
     * 验证注册
     *
     * @return 返回登录对象
     */
    @GET("register/auth.do?type=1")
    Observable<BaseResponse<String>> auth(
            @Query("mobile") String mobile, @Query("password") String password,
            @Query("authCode") String authCode);

    /**
     * 验证注册
     *
     * @return 返回登录对象
     */
    @GET("register/authVersion2.do?type=1")
    Observable<BaseResponse<String>> authVersion2(
            @Query("mobile") String mobile, @Query("password") String password,
            @Query("authCode") String authCode);

    /**
     * 注册
     *
     * @param authCode 验证码
     */
    @POST("register/company.do?")
    Observable<BaseResponse<String>> register(@Body UserApply userApply, @Query("authCode") String authCode);

    /**
     * 注册
     *
     * @param authCode 验证码
     */
    @POST("register/companyVersion2.do?")
    Observable<BaseResponse<String>> registerVersion2(@Body UserApply userApply, @Query("authCode") String authCode);

    /**
     * 忘记密码-核对验证码
     *
     * @param phone
     * @param authCode
     * @return
     */
    @GET(COMPANY + "/user/checkVerificationCode.do?")
    Observable<BaseResponse<String>> checkVerificationCode(@Query("phone") String phone, @Query("authCode") String authCode);

    /**
     * 忘记密码-修改密码
     *
     * @param phone
     * @param newPassword
     * @param authCode    验证码
     * @return
     */
    @GET(COMPANY + "/user/changeLoginPassword.do?")
    Observable<BaseResponse<String>> changeLoginPassword(@Query("phone") String phone, @Query("newPassword") String newPassword, @Query("authCode") String authCode);

    /**
     * 登录-忘记密码修改-验证验证码
     *
     * @param phone    手机号
     * @param authCode 验证码
     * @return 返回信息
     */
    @GET(COMPANY + "/user/checkAuthCode.do?")
    Observable<BaseResponse<String>> checkAuthCode(@Query("phone") String phone, @Query("authCode") String authCode);

    /**
     * 获取用户
     *
     * @param userId 用户id
     */
    @GET(COMPANY + "/user/getInfo.do?")
    Observable<BaseResponse<AppUserWrapper>> getCompanyUserInfo(@Query("userId") String userId);

    /**
     * 获取商户
     *
     * @param userId 用户id
     */
    @GET(COMPANY + "/user/getCompanyInfo.do?")
    Observable<BaseResponse<UserApply>> getCompanyInfo(@Query("userId") String userId);

    /**
     * 修改登录密码
     *
     * @param newPassword
     * @return
     */
    @GET(COMPANY + "/user/changePassword.do?")
    Observable<BaseResponse<String>> changePassword(@Query("userId") String userId,@Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

    /**
     * 修改手机号码
     *
     * @param userId
     * @param mobile
     * @param authCode
     * @return
     */
    @GET(COMPANY + "/user/updateMobile.do?")
    Observable<BaseResponse<String>> updatePhone(@Query("userId") String userId, @Query("mobile") String mobile, @Query("authCode") String authCode, @Query("password") String password);

    /**
     * 修改手机号码
     *
     * @param userId   用户id
     * @param mobile   手机号
     * @param authCode
     * @return
     */
    @POST(COMPANY + "/user/bindMobile.do?")
    Observable<BaseResponse<String>> bindMobile(@Query("userId") String userId, @Query("mobile") String mobile, @Query("authCode") String authCode);

    /**
     * 更新用户
     *
     * @param appUserWrapper 用户数据
     */
    @POST(COMPANY + "/user/update.do?")
    Observable<BaseResponse<String>> updateInfo(@Body AppUserWrapper appUserWrapper);

    /**
     * 更新商户用户
     *
     * @param userApply 用户数据
     */
    @POST(COMPANY + "/user/updateCompanyInfo.do?")
    Observable<BaseResponse<String>> updateCompanyInfo(@Body UserApply userApply);


    /**
     * 获取企业列表
     *
     * @param name      搜索条件(可空)
     * @param pageIndex 页码
     * @return 列表
     */
    @GET(COMPANY + "/list.do?")
    Observable<BaseResponse<PageList<UserOfficeWrapper>>> list(@Query("name") String name, @Query("driverId") String driverId, @Query("pageIndex") int pageIndex);

    /**
     * 查看企业信息
     *
     * @param companyId 企业id
     */
    @GET(COMPANY + "/getInfo.do?")
    Call<ResponseBody> getInfo(@Query("companyId") String companyId);

    /**
     * 查看企业信息
     *
     * @param driverId    司机id
     * @param companyName 企业名称，可以模糊查询
     * @param companyId   企业的id，可以模糊查询
     */
    @GET(COMPANY + "/getAllCompany.do?")
    Call<ResponseBody> getCompany(@Query("driverId") String driverId, @Query("companyName") String companyName, @Query("companyId") String companyId);

    /**
     * 关联/取消 企业
     *
     * @param driverId  司机的id
     * @param type      关联/取消（1为发起关联，0为取消关联）
     * @param companyId 企业的id
     * @return
     */
    @GET(COMPANY + "/followCompany.do?")
    Call<ResponseBody> followCompany(@Query("driverId") String driverId, @Query("type") String type, @Query("companyId") String companyId);


    /**
     * 测试实体类传输的
     *
     * @param userDemo
     * @return
     */
    @POST("getUser1.do?")
    Observable<BaseResponse<String>> getUser1(@Body UserDemo userDemo, @Query("id") String id);



    /**
     * 货代报价查询
     *
     * @param priceAnalysisCondition 条件实体
     * @param userId                 用户id
     * @param pageIndex              页码
     * @return 返回数据源
     */
    @POST(COMPANY + "/price/getAnalysis.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPriceAnalysisWrapper>>> getAnalysis(@Body PriceAnalysisCondition priceAnalysisCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 拖车报价查询
     *
     * @param condition 条件实体
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 返回数据源
     */
    @POST(COMPANY + "/price/getTruckFee.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPriceTruckWrapper>>> getTruckFee(@Body PriceTruckCondition condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);



}
