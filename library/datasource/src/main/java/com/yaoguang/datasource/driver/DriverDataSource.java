package com.yaoguang.datasource.driver;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.common.InfoDock;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.RegisterDataSource;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.datasource.api.driver.DriverApi;
import com.yaoguang.lib.net.bean.PageList;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 司机数据源
 * Created by zhongjh on 2018/3/12.
 */
public class DriverDataSource implements RegisterDataSource<Driver> {


    private DriverApi driverApi = Api.getInstance().retrofit.create(DriverApi.class);

    @Override
    public Observable<BaseResponse<String>> handleOneAuth(String mobile, String pass, String auth) {
        return driverApi.simpleRegister(mobile, pass, auth);
    }

    @Override
    public Observable<BaseResponse<String>> handleRegister(Driver model, String code) {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> checkOldPassword(String userId, String oldPassword) {
        return driverApi.checkOldPassword(userId, oldPassword);
    }

    /**
     * 获取司机信息
     */
    public Observable<BaseResponse<LoginDriver>> getInfo() {
        return driverApi.getInfo(DataStatic.getInstance().getDriver().getId()).map(loginDriverBaseResponse -> {
            // 存储本地数据
            DataStatic.getInstance().setDriver(loginDriverBaseResponse.getResult().getUserInfo());
            DataStatic.getInstance().setUserDriverCars(loginDriverBaseResponse.getResult().getCarInfo());
            DataStatic.getInstance().setUserDriverLicence(loginDriverBaseResponse.getResult().getLicenceInfo());
            return loginDriverBaseResponse;
        });

    }

    /**
     * 修改司机信息
     */
    public Observable<BaseResponse<String>> update(Driver driver) {
        return driverApi.update(driver);
    }

    /**
     * 码头查询
     *
     * @param map clientId  公司id name      名称
     * @param pageIndex 页码
     * @return 返回數據源
     */
    public Observable<BaseResponse<PageList<InfoDock>>> infoDocks(HashMap<String, String> map, int pageIndex) {
        return driverApi.infoDocks(map.get("clientId"), map.get("name"), pageIndex);
    }

    /**
     * 更新码头
     */
    public Observable<BaseResponse<String>> updatePort(String nodeId, String portName) {
        return driverApi.updatePort(nodeId, portName);
    }


}
