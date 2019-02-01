package com.yaoguang.driver.net;

import com.yaoguang.common.net.Api;
import com.yaoguang.driver.net.api.ApiDriver;

/**
 * api工具
 * Created by wly on 2017/12/8 0008.
 */

public class ApiDriverFactory extends Api {
    private static ApiDriverFactory mApiFactory;
    public ApiDriver service;

    private ApiDriverFactory() {
        super();
        service = retrofit.create(ApiDriver.class);
    }

    public static ApiDriverFactory getInstance() {
        if (mApiFactory == null) {
            synchronized (ApiDriverFactory.class) {
                if (mApiFactory == null) {
                    mApiFactory = new ApiDriverFactory();
                }
            }
        }
        return mApiFactory;
    }
}
