package com.yaoguang.interfaces.driver.interactor.order;

import android.support.annotation.NonNull;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.DriverOrderMsg;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.greendao.entity.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * 订单业务层
 * Created by zhongjh on 2017/4/18.
 */
public interface DOrderChildInteractor {

    /**
     * 初始化订单
     * 作者：zhongjh
     * 时间：2017-04-19
     *
     * @param intType 类型0"待接单", 1"已接单",2"进行中", 3"已完成", 4"已关闭"
     * @return 数据源
     */
    Observable<BaseResponse<PageList<Order>>> initOrders(int intType, int pageIndex);

    Observable<BaseResponse<PageList<DriverOrderMsg>>> initMessageOrder(int intType, int pageIndex, boolean isHomePage, String type);

    @NonNull
    ArrayList<Order> getOrders(BaseResponse<PageList<DriverOrderMsg>> value);

    /**
     * 更新工作单状态
     *
     * @param orderId     订单id
     * @param operateType 1:接单 2:完成 3:关闭 4:出车
     * @param remark
     * @param placeId
     * @return 数据源
     */
    Observable<BaseResponse<String>> update(String orderId, int operateType, final String remark, String placeId);

    /**
     * 设置订单已读未读
     * @param ids 要设置的id
     * @return
     */
    Observable<BaseResponse<String>> readBatch(String ids);

    Observable<BaseResponse<String>> outCar(String orderId, Location location);

    /**
     * 初始化节点数据
     * @param orderId 订单id
     * @return 节点列表
     */
    Observable<BaseResponse<List<DriverOrderNode>>> initNodes(String orderId);

    Observable<BaseResponse<String>> deleteIds(HashSet<String> ids);

    /**
     * 获取选择订单地址数据源
     * @param id  订单id
     */
    Flowable<BaseResponse<List<InfoPutorderPlace>>> getPutOrderAddressData(String id);
}
