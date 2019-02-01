package com.yaoguang.driver.net.factory;

import com.yaoguang.common.common.RxSchedulers;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.BannerPic;

import io.reactivex.Flowable;

/**
 * @ API工厂 此类由apt自动生成 */
public final class ApiAppFactory {
  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<PageList<BannerPic>>> getBannerPic(String platformType, int pageIndex, int pageCount) {
    return com.yaoguang.driver.net.ApiAppFactory.getInstance().service.getBannerPic(platformType,pageIndex,pageCount).compose(RxSchedulers.io_main());
  }
}
