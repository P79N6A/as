/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yaoguang.driver.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yaoguang.driver.data.source.AppRepository;
import com.yaoguang.driver.data.source.DriverDataSource;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.driver.data.source.MessageRepository;
import com.yaoguang.driver.data.source.OrderRepository;
import com.yaoguang.driver.data.source.local.DriverLocalDataSource;
import com.yaoguang.driver.data.source.remote.AppRemoteDataSource;
import com.yaoguang.driver.data.source.remote.DriverRemoteDataSource;
import com.yaoguang.driver.data.source.remote.MessageRemoteDataSource;
import com.yaoguang.driver.data.source.remote.OrderRemoteDataSource;
import com.yaoguang.greendao.Injections;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for
 * {@link DriverDataSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static DriverRepository provideDriverRepository(@NonNull Context context) {
        checkNotNull(context);
        return DriverRepository.getInstance(DriverRemoteDataSource.getInstance(),
                DriverLocalDataSource.getInstance(context));
    }

    public static OrderRepository provideOrderRepository(@NonNull Context context) {
        checkNotNull(context);
        return OrderRepository.getInstance(OrderRemoteDataSource.getInstance(),provideDriverRepository(context), Injections.getLocationBiz());
    }

    public static MessageRepository provideMessageRepository(@NonNull Context context) {
        checkNotNull(context);
        return MessageRepository.getInstance(MessageRemoteDataSource.getInstance(),provideDriverRepository(context));
    }

    public static AppRepository provideAppRepository(@NonNull Context context) {
        checkNotNull(context);
        return AppRepository.getInstance(AppRemoteDataSource.getInstance());
    }

}
