package com.yaoguang.datasource.common;

import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * 注册的共用接口
 * Created by zhongjh on 2017/11/29.
 */
public interface RegisterDataSource<T> {

    /**
     * 第一步的验证
     *
     * @param mobile 手机
     * @param pass   密码
     * @param auth   验证码
     */
    Observable<BaseResponse<String>> handleOneAuth(String mobile, String pass, String auth);

    /**
     * 最后一步进行注册
     * T
     *
     * @param model 注册的实体类
     * @param code  验证码
     */
    Observable<BaseResponse<String>> handleRegister(T model, String code);

    /**
     * 修改密码前的验证
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @return 是否成功
     */
    Observable<BaseResponse<String>> checkOldPassword(String userId, String oldPassword);

}
