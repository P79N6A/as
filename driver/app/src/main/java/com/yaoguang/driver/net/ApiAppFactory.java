package com.yaoguang.driver.net;

import com.yaoguang.common.net.Api;

/**
 * 订单api
 * Created by wly on 2017/12/8 0008.
 */

public class ApiAppFactory extends Api {
    private static ApiAppFactory mApiFactory;
    public com.yaoguang.driver.net.api.ApiApp service;

    private ApiAppFactory() {
        super();
        service = retrofit.create(com.yaoguang.driver.net.api.ApiApp.class);
    }

    public static ApiAppFactory getInstance() {
        if (mApiFactory == null) {
            synchronized (ApiAppFactory.class) {
                if (mApiFactory == null) {
                    mApiFactory = new ApiAppFactory();
                }
            }
        }
        return mApiFactory;
    }
}
