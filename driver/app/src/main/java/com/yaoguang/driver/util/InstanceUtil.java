package com.yaoguang.driver.util;


import com.yaoguang.driver.net.factory.InstanceFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class InstanceUtil {

    public static <T> T getInstance(Class clazz) {
        checkNotNull(clazz);

        try {
            return (T) InstanceFactory.create(clazz);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
