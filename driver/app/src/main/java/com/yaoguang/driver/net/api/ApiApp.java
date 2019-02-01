package com.yaoguang.driver.net.api;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.lib.annotation.apt.ApiAppAnnotation;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * APP api
 * Created by Administrator on 2017/12/8 0008.
 */

public interface ApiApp {

    /**
     * @param platformType 0pc 1司机 2供应链 3货主
     */
    @GET("app/bannerPic/list.do?")
    Flowable<BaseResponse<PageList<BannerPic>>> getBannerPic(@Query("platformType") String platformType, @Query("pageIndex") int pageIndex, @Query("pageCount") int pageCount);
}
