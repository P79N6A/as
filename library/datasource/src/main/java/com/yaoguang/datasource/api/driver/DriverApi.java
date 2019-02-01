package com.yaoguang.datasource.api.driver;

import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.common.InfoDock;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/5/16.
 */

public interface DriverApi {

    String DRIVER = "driver/";

    /**
     * 获取司机信息
     *
     * @param driverId 司机id
     * @return json
     */
    @GET(DRIVER + "getInfo.do?")
    Observable<BaseResponse<LoginDriver>> getInfo(@Query("driverId") String driverId);

    /**
     * 修改司机信息
     */
    @POST(DRIVER + "update.do?")
    Observable<BaseResponse<String>> update(@Body Driver driver);

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
     * 安全验证
     *
     * @param userId      用户id
     * @param oldPassword 密码
     * @return
     */
    @GET(DRIVER + "checkOldPassword.do?")
    Observable<BaseResponse<String>> checkOldPassword(@Query("userId") String userId, @Query("oldPassword") String oldPassword);

    /**
     * 码头查询
     *
     * @param clientId  公司id
     * @param name      名称
     * @param pageIndex 页码
     * @return 返回數據源
     */
    @GET(DRIVER + "/baseinfo/infoDocks.do?")
    Observable<BaseResponse<PageList<InfoDock>>> infoDocks(@Query("clientId") String clientId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 更新码头
     *
     * @param nodeId   节点id
     * @param portName 码头名称
     * @return 更新后的相关信息
     */
    @POST("driverOrder/node/port/update.do?")
    Observable<BaseResponse<String>> updatePort(@Query("nodeId") String nodeId, @Query("portName") String portName);

}
