package com.yaoguang.driver.net;

import com.yaoguang.common.net.Api;
import com.yaoguang.driver.net.api.ApiOrder;

/**
 * 订单api
 * Created by wly on 2017/12/8 0008.
 */

public class ApiOrderFactory extends Api {
    private static ApiOrderFactory mApiFactory;
    public ApiOrder service;

    private ApiOrderFactory() {
        super();
        service = retrofit.create(ApiOrder.class);
    }

    public static ApiOrderFactory getInstance() {
        if (mApiFactory == null) {
            synchronized (ApiOrderFactory.class) {
                if (mApiFactory == null) {
                    mApiFactory = new ApiOrderFactory();
                }
            }
        }
        return mApiFactory;
    }
}
