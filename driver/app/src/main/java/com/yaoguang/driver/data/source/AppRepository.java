package com.yaoguang.driver.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.data.source.remote.AppRemoteDataSource;
import com.yaoguang.driver.data.source.remote.OrderRemoteDataSource;
import com.yaoguang.greendao.biz.driver.impl.LocationBizImpl;
import com.yaoguang.greendao.entity.BannerPic;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/1/10
 * 描    述：app仓库
 * =====================================
 */

public class AppRepository implements AppDataSource{
    private AppRemoteDataSource mAppRemoteDataSource;

    private static AppRepository INSTANCE;

    public static AppRepository getInstance(@NonNull AppRemoteDataSource appRemoteDataSource) {
        if (INSTANCE == null) {
            synchronized (OrderRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppRepository(appRemoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    private AppRepository(@NonNull AppRemoteDataSource appRemoteDataSource) {
        this.mAppRemoteDataSource = appRemoteDataSource;
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<PageList<BannerPic>>> getBannerPic() {
        return mAppRemoteDataSource.getBannerPic();
    }
}
