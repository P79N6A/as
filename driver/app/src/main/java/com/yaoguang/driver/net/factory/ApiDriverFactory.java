package com.yaoguang.driver.net.factory;

import com.yaoguang.common.common.RxSchedulers;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Contact;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.greendao.entity.UserOffice;
import com.yaoguang.greendao.entity.driver.DriverAuthentication;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;

import io.reactivex.Flowable;

/**
 * @ API工厂 此类由apt自动生成 */
public final class ApiDriverFactory {
  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> setAuthentication(Driver driver, String authType, String operateType) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.setAuthentication(driver,authType,operateType).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> setAuthentication(UserDriverCar userDriverCar, String authType, String operateType) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.setAuthentication(userDriverCar,authType,operateType).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> setAuthentication(UserDriverLicence userDriverLicence, String authType, String operateType) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.setAuthentication(userDriverLicence,authType,operateType).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<DriverAuthentication>> getAuthentication(String driverId) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.getAuthentication(driverId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<DriverStatusSwitch>> getDriverStatus(String driverId) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.getDriverStatus(driverId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> deleteLeavePlan(String id) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.deleteLeavePlan(id).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<LoginResult>> login(String username, String password, String deviceToken, String loginMachine, String loginMachineVersion, String loginAppVersion) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.login(username,password,deviceToken,loginMachine,loginMachineVersion,loginAppVersion).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<Driver>> register(Driver driver, String authCode) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.register(driver,authCode).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<PageList<Contact>>> contactWaitList(String userId, String type, int pageIndex) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.contactWaitList(userId,type,pageIndex).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<PageList<Contact>>> contactRejectList(String userId, String type, int pageIndex) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.contactRejectList(userId,type,pageIndex).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<PageList<Contact>>> contactPassList(String userId, String type, int pageIndex) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.contactPassList(userId,type,pageIndex).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> contactAdd(String userId, String companyId, String reason, String type) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.contactAdd(userId,companyId,reason,type).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> contactDelete(String userId, String contactId, String type) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.contactDelete(userId,contactId,type).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> contactDeleteApply(String id) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.contactDeleteApply(id).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<LoginResult>> getInfo(String driverId) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.getInfo(driverId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> update(Driver driver) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.update(driver).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> updateMobile(String driverId, String mobile, String authCode, String password) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.updateMobile(driverId,mobile,authCode,password).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> changeLoginPassword(String userId, String newPassword, String oldPassword) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.changeLoginPassword(userId,newPassword,oldPassword).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> checkAuthCode(String phone, String authCode) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.checkAuthCode(phone,authCode).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> changePassword(String userId, String password, String authCode) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.changePassword(userId,password,authCode).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> addDriverRestPlan(UserDriverStatusDetail userDriverStatusDetail) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.addDriverRestPlan(userDriverStatusDetail).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> updateMessageFlag(String driverId, int messageFlag) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.updateMessageFlag(driverId,messageFlag).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> simpleRegister(String mobile, String password, String authCode) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.simpleRegister(mobile,password,authCode).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<UserOffice>> getCompanyInfo(String userId, String companyId, String type) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.getCompanyInfo(userId,companyId,type).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> updateLeavePlan(String id, String endDate, String sendFlag) {
    return com.yaoguang.driver.net.ApiDriverFactory.getInstance().service.updateLeavePlan(id,endDate,sendFlag).compose(RxSchedulers.io_main());
  }
}
