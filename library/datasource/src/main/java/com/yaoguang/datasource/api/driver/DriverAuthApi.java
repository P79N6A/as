package com.yaoguang.datasource.api.driver;

import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/5/16.
 */

public interface DriverAuthApi {

    String DRIVER_AUTH = "driver/auth/";

    /**
     * 认证资料提交/保存
     *
     * @param driver      认证资料(json)
     * @param authType    (lic,car,real)
     * @param operateType 0:保存 1:提交
     * @param driver      认证实体
     */
    @POST(DRIVER_AUTH + "submit.do?")
    Observable<BaseResponse<String>> setAuthentication(@Body Driver driver, @Query("authType") String authType, @Query("operateType") String operateType);

    /**
     * 认证资料提交/保存
     *
     * @param userDriverCar 认证资料(json)
     * @param authType      (lic,car,real)
     * @param operateType   0:保存 1:提交
     */
    @POST(DRIVER_AUTH + "submit.do?")
    Observable<BaseResponse<String>> setAuthentication(@Body UserDriverCar userDriverCar, @Query("authType") String authType, @Query("operateType") String operateType);

    /**
     * 认证资料提交/保存
     *
     * @param userDriverLicence 认证资料(json)
     * @param authType          (lic,car,real)
     * @param operateType       0:保存 1:提交
     */
    @POST(DRIVER_AUTH + "submit.do?")
    Observable<BaseResponse<String>> setAuthentication(@Body UserDriverLicence userDriverLicence, @Query("authType") String authType, @Query("operateType") String operateType);


}
