package com.yaoguang.driver.net.factory;

import com.yaoguang.common.common.RxSchedulers;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.DriverOrderMsg;
import com.yaoguang.greendao.entity.MessageInfo;
import com.yaoguang.greendao.entity.UnreadNum;

import io.reactivex.Flowable;

/**
 * @ API工厂 此类由apt自动生成 */
public final class ApiMessageFactory {
  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<PageList<MessageInfo>>> platformMessageList(String userId, int pageIndex, int platformType, int noticeType) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.platformMessageList(userId,pageIndex,platformType,noticeType).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> deletePlatformMessage(String userId, String sysMsgId) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.deletePlatformMessage(userId,sysMsgId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> setReadPlatformMessage(String userId, String sysMsgId) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.setReadPlatformMessage(userId,sysMsgId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<PageList<DriverOrderMsg>>> getMessageOrderList(String driverId, int pageIndex) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.getMessageOrderList(driverId,pageIndex).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<PageList<DriverOrderMsg>>> getHomeMessageList(String driverId, int pageIndex, String type, String noticeType) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.getHomeMessageList(driverId,pageIndex,type,noticeType).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<PageList<MessageInfo>>> getHomeMessageList_tmp(String driverId, String pageIndex, String type, String noticeType) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.getHomeMessageList_tmp(driverId,pageIndex,type,noticeType).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> orderMessageDeleted(String ids, int platformType) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.orderMessageDeleted(ids,platformType).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<UnreadNum>> getUnreadNum(String driverId, int platformType) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.getUnreadNum(driverId,platformType).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> ignoreAll(String driverId, String type, String ids) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.ignoreAll(driverId,type,ids).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> readBatch(String ids) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.readBatch(ids).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> getVerificationCode(String phone, String type) {
    return com.yaoguang.driver.net.ApiMessageFactory.getInstance().service.getVerificationCode(phone,type).compose(RxSchedulers.io_main());
  }
}
