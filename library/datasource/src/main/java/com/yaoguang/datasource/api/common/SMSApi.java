package com.yaoguang.datasource.api.common;

import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 信息接口类
 * Created by zhongjh on 2017/4/1.
 */
public interface SMSApi {

    String SMS = "sms";

    @GET(SMS + "/driver/getCode.do?")
    Observable<BaseResponse<String>> getCode(@Query("phone") String phone, @Query("type") String type);

    @GET(SMS + "/driver/getCode.do?")
    Observable<BaseResponse<String>> getCodeDriver(@Query("phone") String phone, @Query("type") String type);

    /**
     * @param phone  手机号
     * @param type   类型 1注册验证码 2修改密码验证码 3修改手机号
     * @param userId 用户id
     * @param isBind 绑定手机号时，若手机号已绑过，是否确定解除原绑定帐号，重新绑定新帐号（1.是， 其他否）
     * @return
     */
    @GET(SMS + "/company/getCode.do?")
    Observable<BaseResponse<String>> getCodeCompany(@Query("phone") String phone, @Query("type") String type, @Query("userId") String userId, @Query("isBind") String isBind);

    /**
     * @param phone  手机号
     * @param type   类型 1注册验证码 2修改密码验证码 3修改手机号
     * @param userId 用户id
     * @param isBind 绑定手机号时，若手机号已绑过，是否确定解除原绑定帐号，重新绑定新帐号（1.是， 其他否）
     * @return
     */
    @GET(SMS + "/company/getCodeVersion2.do?")
    Observable<BaseResponse<String>> getCodeCompanyVersion2(@Query("phone") String phone, @Query("type") String type, @Query("userId") String userId, @Query("isBind") String isBind);

    /**
     * 获取验证码
     *
     * @param phone 新的手机号码
     * @param type  1注册验证码 2修改密码验证码 3修改手机号
     * @return 验证码
     */
    @GET(SMS + "/owner/getCode.do?")
    Observable<BaseResponse<String>> getCodeOwner(@Query("phone") String phone, @Query("type") String type);

    /**
     * 获取验证码
     *
     * @param phone 新的手机号码
     * @param type  1注册验证码 2修改密码验证码 3修改手机号
     * @return 验证码
     */
    @GET(SMS + "/owner/getCodeVersion2.do?")
    Observable<BaseResponse<String>> getCodeOwnerVersion2(@Query("phone") String phone, @Query("type") String type);

    /**
     * 司机端获取验证码
     *
     * @param phone 手机
     * @param type  类型
     */
    @GET(SMS + "/driver/getCode.do?")
    Observable<BaseResponse<String>> getVerificationCode(@Query("phone") String phone, @Query("type") String type);



}
