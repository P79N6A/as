package com.yaoguang.driver.net;

import com.yaoguang.common.net.Api;
import com.yaoguang.driver.net.api.ApiMessage;

/**
 * 订单api
 * Created by wly on 2017/12/8 0008.
 */

public class ApiMessageFactory extends Api {
    private static ApiMessageFactory mApiFactory;
    public ApiMessage service;

    private ApiMessageFactory() {
        super();
        service = retrofit.create(ApiMessage.class);
    }

    public static ApiMessageFactory getInstance() {
        if (mApiFactory == null) {
            synchronized (ApiMessageFactory.class) {
                if (mApiFactory == null) {
                    mApiFactory = new ApiMessageFactory();
                }
            }
        }
        return mApiFactory;
    }
}
