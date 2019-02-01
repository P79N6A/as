package com.yaoguang.driver.data.source;

import android.support.annotation.NonNull;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.BannerPic;

import io.reactivex.Flowable;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/01/17
 * 描    述：app仓库
 * =====================================
 */

public interface AppDataSource {
    @NonNull
    Flowable<BaseResponse<PageList<BannerPic>>> getBannerPic();
}
