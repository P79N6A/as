package com.yaoguang.datasource.api;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.DriverOrderPositionLog;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 文件名：
 * 描    述： 定位api
 * 作    者：韦理英
 * 时    间：2017/8/1 0001.
 * 版    权：
 */
public interface PositionApi {
    // 说明
    String POSITION = "driver/position/";

    /**
     * @param driverOrderPositionLogList 发送json
     * @return
     */
    @POST(POSITION + "add.do?")
    Observable<BaseResponse<String>> add(@Body List<DriverOrderPositionLog> driverOrderPositionLogList);
}
