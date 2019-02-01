package com.yaoguang.driver.net.factory;

import com.yaoguang.common.common.RxSchedulers;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.DriverOrderDetailVo;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.DriverOrderNodeDetailWrapper;
import com.yaoguang.greendao.entity.DriverOrderNodeFeedback;
import com.yaoguang.greendao.entity.DriverOrderProgressWrapper;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.LatLons;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.greendao.entity.OrderDetailNodeList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @ API工厂 此类由apt自动生成 */
public final class ApiOrderFactory {
  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<DriverOrderProgressWrapper>> getOrderCurrentProgress(String driverId) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.getOrderCurrentProgress(driverId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<List<DriverOrderNode>>> next(String orderId) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.next(orderId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<List<InfoPutorderPlace>>> getPutOrderAddressList(String id) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.getPutOrderAddressList(id).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<PageList<Order>>> getList(String driverId, int type, int pageIndex) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.getList(driverId,type,pageIndex).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<DriverOrderDetailVo>> getOrderDetail(String driverId, String orderId) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.getOrderDetail(driverId,orderId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> update(String driverId, String orderId, String operateType, String remark, String placeId) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.update(driverId,orderId,operateType,remark,placeId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> outCar(String driverId, DriverOrderNodeDetail driverOrderNodeDetail) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.outCar(driverId,driverOrderNodeDetail).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<FreightStatistic>> count(String driverId, String companyName, String startDate, String endDate, String dateScopeType) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.count(driverId,companyName,startDate,endDate,dateScopeType).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> add(DriverOrderNodeDetail driverOrderNodeDetail, String driverId, int controlSync) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.add(driverOrderNodeDetail,driverId,controlSync).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> feedbackAdd(DriverOrderNodeFeedback driverOrderNodeFeedback) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.feedbackAdd(driverOrderNodeFeedback).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<OrderDetailNodeList>> OrderDetailNote(String driverOrderId, String orderSn) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.OrderDetailNote(driverOrderId,orderSn).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<DriverOrderNodeDetailWrapper>> edit(String id) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.edit(id).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> switchToNode(String id, String switchToNodeId) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.switchToNode(id,switchToNodeId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<List<DriverOrderNodeFeedback>>> feedbackList(String orderId, String driverId) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.feedbackList(orderId,driverId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<DriverOrderNodeFeedback>> orderNodeDetail(String nodeId) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.orderNodeDetail(nodeId).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<List<LatLons>>> OrderLatLonHistory(String orderSn) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.OrderLatLonHistory(orderSn).compose(RxSchedulers.io_main());
  }

  /**
   * @此方法由apt自动生成 */
  public static Flowable<BaseResponse<String>> readBatch(String ids) {
    return com.yaoguang.driver.net.ApiOrderFactory.getInstance().service.readBatch(ids).compose(RxSchedulers.io_main());
  }
}
