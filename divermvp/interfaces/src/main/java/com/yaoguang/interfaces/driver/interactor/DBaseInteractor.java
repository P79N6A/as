package com.yaoguang.interfaces.driver.interactor;

import com.yaoguang.greendao.entity.Driver;

/**
 * 获取缓存中的司机
 * Created by zhongjh on 2017/4/10.
 */
public interface DBaseInteractor {
    Driver getDriver();

    String getDriverId();
}
