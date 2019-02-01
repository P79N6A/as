package com.yaoguang.datasource.api.driver;

import com.yaoguang.greendao.entity.driver.DriverNodeAddrVo;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeStatusWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeList;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeProgressWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/4/27.
 */
public interface OrderNodeApi {

    String ORDER_NODE = "driverOrder/node";

//    /**
//     * 根据订单id
//     *
//     * @param orderId 订单
//     * @param userId  用戶
//     * @return 列表数据
//     */
//    @GET(ORDER_NODE + "/progress.do?")
//    Observable<BaseResponse<DriverOrderNodeProgressWrapper>> progress(@Query("orderId") String orderId, @Query("userId") String userId);

    /**
     * 完成节点
     *
     * @return 列表数据
     */
    @POST(ORDER_NODE + "/finish.do?")
    Observable<BaseResponse<DriverOrderNodeProgressWrapper>> finish(@Body DriverOrderNodeList driverOrderNodeList);

    /**
     * 获取节点的具体提交内容
     *
     * @param nodeId 节点id
     */
    @GET(ORDER_NODE + "/detail.do?")
    Observable<BaseResponse<ArrayList<DriverOrderNodeDetail>>> detail(@Query("nodeId") String nodeId);

    /**
     * 提交节点内容
     *
     * @param driverOrderNodeDetail 提交的实体类
     * @return 是否成功的相关信息
     */
    @POST(ORDER_NODE + "/detail/submit.do?")
    Observable<BaseResponse<String>> detailubmit(@Body DriverOrderNodeDetail driverOrderNodeDetail);

    /**
     * 获取节点列表 来编辑
     *
     * @param orderId 订单id
     * @return 返回数据源
     */
    @GET(ORDER_NODE + "/list/edit.do?")
    Observable<BaseResponse<ArrayList<DriverOrderNodeWrapper>>> listEdit(@Query("orderId") String orderId);

    /**
     * 显示所有节点(司机端)
     *
     * @param driverOrderId 订单id
     * @return 数据源
     */
    @GET(ORDER_NODE + "/merge/list.do?")
    Observable<BaseResponse<DriverOrderMergeNodeStatusWrapper>> mergeList(@Query("driverOrderId") String driverOrderId);

    /**
     * 显示所有节点(货主端)
     *
     * @param type 类型(4:装货 5:送货)
     * @param pcSonoId 平台货柜id
     * @return 数据源
     */
    @GET(ORDER_NODE + "/sono/news.do?")
    Observable<BaseResponse<ArrayList<DriverOrderMergeNodeWrapper>>> sonoNews(@Query("type") String type,@Query("pcSonoId") String pcSonoId);

    /**
     * 编辑节点的地址
     *
     * @param driverNodeAddrVo 地址
     * @return 返回数据源
     */
    @POST(ORDER_NODE + "/address/update.do?")
    Observable<BaseResponse<String>> update(@Body DriverNodeAddrVo driverNodeAddrVo);

    /**
     * 两个节点互换
     *
     * @param nodeId         当前节点
     * @param switchToNodeId 需要互换的节点
     * @return 是否成功等消息
     */
    @GET(ORDER_NODE + "/switch.do?")
    Observable<BaseResponse<String>> nodeSwitch(@Query("nodeId") String nodeId, @Query("switchToNodeId") String switchToNodeId);

    /**
     * 批量完成节点
     *
     * @return 提交后，重新查询节点数据
     */
    @POST(ORDER_NODE + "/finish/batch.do?")
    Observable<BaseResponse<ArrayList<DriverOrderMergeNodeWrapper>>> finishBatch(@Body ArrayList<DriverOrderNodeList> driverOrderNodeList);

}
