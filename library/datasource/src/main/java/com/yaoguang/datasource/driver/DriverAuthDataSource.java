package com.yaoguang.datasource.driver;

import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.api.driver.DriverApi;
import com.yaoguang.datasource.api.driver.DriverAuthApi;

import io.reactivex.Observable;

/**
 * 有关Auth信息的
 * Created by zhongjh on 2018/5/16.
 */

public class DriverAuthDataSource {

    private DriverAuthApi mDriverAuthApi = Api.getInstance().retrofit.create(DriverAuthApi.class);

    /**
     * 提交数据
     *
     * @param object Driver UserDriverLicence UserDriverCar 3种类型
     * @return 信息
     */
    public Observable<BaseResponse<String>> setAuthentication(Object object) {
        if (object instanceof Driver) {
            return mDriverAuthApi.setAuthentication((Driver) object, "real", "1");
        } else if (object instanceof UserDriverLicence) {
            return mDriverAuthApi.setAuthentication((UserDriverLicence) object, "lic", "1");
        } else if (object instanceof UserDriverCar) {
            return mDriverAuthApi.setAuthentication((UserDriverCar) object, "car", "1");
        }
        return null;
    }

    /**
     * 保存数据
     *
     * @param object Driver UserDriverLicence UserDriverCar 3种类型
     * @return 信息
     */
    public Observable<BaseResponse<String>> saveAuthentication(Object object) {
        if (object instanceof Driver) {
            return mDriverAuthApi.setAuthentication((Driver) object, "real", "0");
        } else if (object instanceof UserDriverLicence) {
            return mDriverAuthApi.setAuthentication((UserDriverLicence) object, "lic", "0");
        } else if (object instanceof UserDriverCar) {
            return mDriverAuthApi.setAuthentication((UserDriverCar) object, "car", "0");
        }
        return null;
    }
}
