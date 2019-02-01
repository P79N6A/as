package com.yaoguang.datasource.driver;

import com.yaoguang.greendao.entity.driver.DriverNodeAddrVo;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeStatusWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeList;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeProgressWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeWrapper;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.api.driver.OrderNodeApi;
import java.util.ArrayList;
import io.reactivex.Observable;

/**
 * 订单数据源
 * Created by zhongjh on 2018/4/27.
 */

public class OrderNodeDataSource {


//    public Observable<BaseResponse<DriverOrderNodeProgressWrapper>> progress(String orderId, String userId) {
//        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
//        return orderNodeApi.progress(orderId, userId);
//    }

    /**
     * 如果不传id就是出车
     * 完成节点(现在只有出车了)
     *
     * @param id            id
     * @param lat           经度
     * @param lon           纬度
     * @param address       地址
     * @param driverOrderId 订单id
     * @return 消息
     */
    public Observable<BaseResponse<DriverOrderNodeProgressWrapper>> finish(String id, double lat, double lon, String address, String driverOrderId) {
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        DriverOrderNodeList driverOrderNodeList = new DriverOrderNodeList();
        driverOrderNodeList.setId(id);
        driverOrderNodeList.setLat(lat);
        driverOrderNodeList.setLon(lon);
        driverOrderNodeList.setAddress(address);
        driverOrderNodeList.setDriverOrderId(driverOrderId);
        return orderNodeApi.finish(driverOrderNodeList);
    }

    /**
     * 节点具体内容
     *
     * @param nodeId 节点id
     */
    public Observable<BaseResponse<ArrayList<DriverOrderNodeDetail>>> detail(String nodeId) {
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        return orderNodeApi.detail(nodeId);
    }

    /**
     * 提交节点内容
     *
     * @param driverOrderNodeDetail 节点实体
     */
    public Observable<BaseResponse<String>> detailubmit(DriverOrderNodeDetail driverOrderNodeDetail) {
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        return orderNodeApi.detailubmit(driverOrderNodeDetail);
    }

    /**
     * 获取节点列表 来编辑
     *
     * @param orderId 订单id
     * @return DriverOrderNodeWrapper
     */
    public Observable<BaseResponse<ArrayList<DriverOrderNodeWrapper>>> listEdit(String orderId) {
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        return orderNodeApi.listEdit(orderId);
    }

    /**
     * 显示所有节点(司机端)
     *
     * @param driverOrderId 订单id
     * @return 数据源
     */
    public Observable<BaseResponse<DriverOrderMergeNodeStatusWrapper>> mergeList(String driverOrderId) {
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        return orderNodeApi.mergeList(driverOrderId);
    }

    /**
     * 显示所有节点(货主端)
     *
     * @param type 类型(4:装货 5:送货)
     * @param pcSonoId 平台货柜id
     * @return 数据源
     */
    public Observable<BaseResponse<ArrayList<DriverOrderMergeNodeWrapper>>> sonoNews(String type,String pcSonoId) {
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        return orderNodeApi.sonoNews(type,pcSonoId);
    }

    /**
     * 编辑节点的地址
     *
     * @param driverNodeAddrVo 节点实体的地址
     * @return 是否成功文本
     */
    public Observable<BaseResponse<String>> update(DriverNodeAddrVo driverNodeAddrVo) {
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        return orderNodeApi.update(driverNodeAddrVo);
    }

    /**
     * 两个节点互换
     *
     * @param nodeId         当前节点
     * @param switchToNodeId 需要互换的节点
     * @return 是否成功等消息
     */
    public Observable<BaseResponse<String>> nodeSwitch(String nodeId, String switchToNodeId) {
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        return orderNodeApi.nodeSwitch(nodeId, switchToNodeId);
    }

    /**
     * 批量完成节点
     *
     * @return 是否成功
     */
    public Observable<BaseResponse<ArrayList<DriverOrderMergeNodeWrapper>>> finishBatch(ArrayList<DriverOrderNodeList> driverOrderNodeList) {
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        return orderNodeApi.finishBatch(driverOrderNodeList);
    }

}
