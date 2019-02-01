package com.yaoguang.datasource.driver;

import android.text.TextUtils;

import com.yaoguang.datasource.api.driver.OrderFeedBackApi;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeFeedback;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * 意见反馈
 * Created by zhongjh on 2018/5/17.
 */
public class OrderFeedBackDataSource {

    private OrderFeedBackApi mOrderFeedBackApi = Api.getInstance().retrofit.create(OrderFeedBackApi.class);

    /**
     * 提交反馈
     *
     * @param orderId 订单id
     * @return 返回数据源
     */
    public Observable<BaseResponse<String>> feedbackAdd(String orderId, String nodeName, String dsc, DriverOrderNodeDetail driverOrderNodeDetail, String nodeId) {
        DriverOrderNodeFeedback driverOrderNodeFeedback = new DriverOrderNodeFeedback();
        driverOrderNodeFeedback.setLat(ObjectUtils.parseDouble(driverOrderNodeDetail.getLat()));
        driverOrderNodeFeedback.setLon(ObjectUtils.parseDouble(driverOrderNodeDetail.getLon()));
        driverOrderNodeFeedback.setAddress(driverOrderNodeDetail.getAddress());
        driverOrderNodeFeedback.setProvince(driverOrderNodeDetail.getProvince());
        driverOrderNodeFeedback.setCity(driverOrderNodeDetail.getCity());
        driverOrderNodeFeedback.setDistrict(driverOrderNodeDetail.getDistrict());
        driverOrderNodeFeedback.setStreet(driverOrderNodeDetail.getStreet());

        driverOrderNodeFeedback.setOrderSn(orderId);
        driverOrderNodeFeedback.setNodeId(nodeId);
        driverOrderNodeFeedback.setNodeName(nodeName);
        driverOrderNodeFeedback.setRemark(dsc);
        driverOrderNodeFeedback.setCreatedBy(DataStatic.getInstance().getId());

        if (!TextUtils.isEmpty(driverOrderNodeDetail.getPicture()))
            driverOrderNodeFeedback.setPicture(driverOrderNodeDetail.getPicture());
        if (!TextUtils.isEmpty(driverOrderNodeDetail.getVideoUrl()))
            driverOrderNodeFeedback.setVideoUrl(driverOrderNodeDetail.getVideoUrl());
        if (!TextUtils.isEmpty(driverOrderNodeDetail.getAudioUrl()))
            driverOrderNodeFeedback.setAudioUrl(driverOrderNodeDetail.getAudioUrl());

        return mOrderFeedBackApi.feedbackAdd(driverOrderNodeFeedback);
    }


    /**
     * 查看反馈
     *
     * @param orderId  订单id
     * @param driverId 用户id
     */
    public Observable<BaseResponse<List<DriverOrderNodeFeedback>>> feedbackList(String orderId, String driverId) {
        return mOrderFeedBackApi.feedbackList(orderId, DataStatic.getInstance().getId());
    }

}
