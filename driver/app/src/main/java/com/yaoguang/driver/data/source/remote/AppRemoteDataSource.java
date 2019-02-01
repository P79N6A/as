package com.yaoguang.driver.data.source.remote;

import android.support.annotation.NonNull;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.data.source.AppDataSource;
import com.yaoguang.driver.net.factory.ApiAppFactory;
import com.yaoguang.greendao.entity.BannerPic;

import io.reactivex.Flowable;

/**
 * APP数据源
 * Created by wly on 2018/1/9 0009.
 */

public class AppRemoteDataSource implements AppDataSource {

    private static AppRemoteDataSource INSTANCE;
    public static AppRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (AppRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<PageList<BannerPic>>> getBannerPic() {
        return ApiAppFactory.getBannerPic("1", 1, 4);
    }
}
