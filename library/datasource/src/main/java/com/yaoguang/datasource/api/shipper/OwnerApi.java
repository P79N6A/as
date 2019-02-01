package com.yaoguang.datasource.api.shipper;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppOwnerSonoCondition;
import com.yaoguang.greendao.entity.AppOwnerSonoWrapper;
import com.yaoguang.greendao.entity.AppUserOwner;
import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;
import com.yaoguang.greendao.entity.UserOwner;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2017/6/22.
 */
public interface OwnerApi {

    String OWNER = "owner";

    /**
     * 登录
     *
     * @param phone               用户名称
     * @param password            密码
     * @param deviceToken         经过推送加密的唯一码
     * @param loginMachine        手机信息
     * @param loginMachineVersion 手机信息
     * @param loginAppVersion     手机信息
     * @return 返回登录对象
     */
    @GET(OWNER + "/login.do?")
    Observable<BaseResponse<UserOwner>> login(
            @Query("phone") String phone, @Query("password") String password,
            @Query("deviceToken") String deviceToken, @Query("loginMachine") String loginMachine, @Query("loginMachineVersion") String loginMachineVersion, @Query("loginAppVersion") String loginAppVersion);

    /**
     * 登录
     *
     * @param phone               用户名称
     * @param password            密码
     * @param deviceToken         经过推送加密的唯一码
     * @param loginMachine        手机信息
     * @param loginMachineVersion 手机信息
     * @param loginAppVersion     手机信息
     * @return 返回登录对象
     */
    @GET(OWNER + "/loginVersion2.do?")
    Observable<BaseResponse<AppUserOwner>> loginVersion2(
            @Query("phone") String phone, @Query("password") String password,
            @Query("deviceToken") String deviceToken, @Query("loginMachine") String loginMachine, @Query("loginMachineVersion") String loginMachineVersion, @Query("loginAppVersion") String loginAppVersion);

    /**
     * 安全验证
     *
     * @param userId      用户id
     * @param oldPassword 密码
     * @return
     */
    @GET(OWNER + "/user/checkOldPassword.do?")
    Observable<BaseResponse<String>> checkOldPassword(@Query("userId") String userId, @Query("oldPassword") String oldPassword);


    /**
     * 验证注册
     *
     * @return 返回登录对象
     */
    @GET("register/auth.do?type=2")
    Observable<BaseResponse<String>> auth(
            @Query("mobile") String mobile, @Query("password") String password,
            @Query("authCode") String authCode);

    /**
     * 验证注册
     *
     * @return 返回登录对象
     */
    @GET("register/authVersion2.do?type=2")
    Observable<BaseResponse<String>> authVersion2(
            @Query("mobile") String mobile, @Query("password") String password,
            @Query("authCode") String authCode);

    /**
     * 注册
     *
     * @param authCode 验证码
     */
    @POST("register/owner.do?")
    Observable<BaseResponse<String>> register(@Body UserOwner userOwner, @Query("phone") String phone, @Query("password") String password, @Query("authCode") String authCode);

    /**
     * 注册
     *
     * @param authCode 验证码
     */
    @POST("register/ownerVersion2.do?")
    Observable<BaseResponse<String>> registerVersion2(@Body UserOwner userOwner, @Query("phone") String phone, @Query("password") String password, @Query("authCode") String authCode);


    /**
     * 忘记密码-核对验证码
     *
     * @param phone
     * @param authCode
     * @return
     */
    @GET(OWNER + "/user/checkVerificationCode.do?")
    Observable<BaseResponse<String>> checkVerificationCode(@Query("phone") String phone, @Query("authCode") String authCode);

    /**
     * 忘记密码-修改密码
     *
     * @param phone
     * @param newPassword
     * @return
     */
    @GET(OWNER + "/user/changeLoginPassword.do?")
    Observable<BaseResponse<String>> changeLoginPassword(@Query("phone") String phone, @Query("newPassword") String newPassword, @Query("authCode") String authCode);

    /**
     * 登录-忘记密码修改-验证验证码
     *
     * @param phone    手机号
     * @param authCode 验证码
     * @return 返回信息
     */
    @GET(OWNER + "/user/checkAuthCode.do?")
    Observable<BaseResponse<String>> checkAuthCode(@Query("phone") String phone, @Query("authCode") String authCode);

    /**
     * 修改登录密码
     *
     * @param newPassword
     * @return
     */
    @GET(OWNER + "/user/changePassword.do?")
    Observable<BaseResponse<String>> changePassword(@Query("userId") String userId, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

    /**
     * 修改手机号码
     *
     * @param userId
     * @param phone
     * @param authCode
     * @return
     */
    @GET(OWNER + "/user/updatePhone.do?")
    Observable<BaseResponse<String>> updatePhone(@Query("userId") String userId, @Query("phone") String phone, @Query("authCode") String authCode, @Query("password") String password);


    /**
     * 获取用户
     *
     * @param userId 用户id
     */
    @GET(OWNER + "/user/getInfo.do?")
    Observable<BaseResponse<UserOwner>> getInfo(@Query("userId") String userId);

    /**
     * 更新用户
     *
     * @param userOwner 用户数据
     */
    @POST(OWNER + "/user/updateInfo.do?")
    Observable<BaseResponse<String>> updateInfo(@Body UserOwner userOwner);


    /**
     * 货柜查询
     *
     * @param condition 条件
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 返回列表
     */
    @POST(OWNER + "/order/getSonos.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<ArrayList<AppOwnerSonoWrapper>>> getInfoVoyageTabless(@Body AppOwnerSonoCondition condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);


    /**
     * 根据柜号查询运单号列表
     *
     * @param contNo 柜号
     * @return
     */
    @POST(OWNER + "/baseInfo/getMBlNosByContNo.do?")
    Observable<BaseResponse<List<String>>> getMBlNosByContNo(@Query("contNo") String contNo);

    /**
     * 根据运单号查询柜号
     *
     * @param mblNo 运单号
     * @return
     */
    @POST(OWNER + "/baseInfo/getContNosByMBlNo.do?")
    Observable<BaseResponse<List<String>>> getContNosByMBlNo(@Query("mblNo") String mblNo);
}
