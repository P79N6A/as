package com.yaoguang.datasource.api.driver;

import com.yaoguang.greendao.entity.DriverOrderPositionLog;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 有关地址的
 * Created by zhongjh on 2018/7/11.
 */
public interface PositionApi {

    String DRIVER_POSITION = "driver/position/";

    /**
     * 获取司机信息
     *
     * @param driverOrderPositionLog 坐标集合数据
     * @return json
     */
    @POST(DRIVER_POSITION + "add.do?")
    Observable<BaseResponse<String>> add(@Body List<Location> driverOrderPositionLog);

}
