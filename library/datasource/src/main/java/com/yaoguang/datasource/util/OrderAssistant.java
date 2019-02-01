package com.yaoguang.datasource.util;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.common.SpanUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * 订单列表助手
 * Created by wly on 2018/1/11 0011.
 */

public class OrderAssistant {

    private static OrderAssistant INSTANCE;

    private static int mIc_dc_s02;

    public static OrderAssistant getInstance(int ic_dc_s02) {
        if (INSTANCE == null) {
            synchronized (OrderAssistant.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderAssistant();
                    mIc_dc_s02 = ic_dc_s02;
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 处理线路转换 消息类型：DriverOrderWrapper
     */
    public Function<BaseResponse<PageList<DriverOrderWrapper>>, BaseResponse<PageList<DriverOrderWrapper>>> deliveryRoutmapper = pageListBaseResponse -> {
        if (pageListBaseResponse.getResult() == null) return pageListBaseResponse;
        for (int i = 0; i < pageListBaseResponse.getResult().getResult().size(); i++) {
            DriverOrderWrapper driverOrderWrapper = pageListBaseResponse.getResult().getResult().get(i);
            if ((driverOrderWrapper.getDeliveryRoute() == null) || driverOrderWrapper.getDeliveryRoute().isEmpty())
                return pageListBaseResponse;
            // 设置线路箭头
            driverOrderWrapper.setSpannableStringBuilder(handlerDeliverRoute(driverOrderWrapper.getDeliveryRoute()));
        }

        // 处理驾车线路
        return pageListBaseResponse;
    };


    /**
     * 时间转换 消息类型：DriverOrderMsgWrapper
     */
    public Function<BaseResponse<PageList<DriverOrderWrapper>>, BaseResponse<PageList<DriverOrderWrapper>>> orderCreateTimeMapper = pageListBaseResponse -> {
        if (pageListBaseResponse.getResult() == null) return pageListBaseResponse;
        for (int i = 0; i < pageListBaseResponse.getResult().getResult().size(); i++) {
            DriverOrderWrapper driverOrderWrapper = pageListBaseResponse.getResult().getResult().get(i);
            @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = format.parse(driverOrderWrapper.getCreateTime());
            driverOrderWrapper.setCreateTime(DateUtils.formatDateTime(date) + " 派单");
        }
        return pageListBaseResponse;
    };

    /**
     * 处理线路转换 消息类型：DriverOrderMsgWrapper
     */
    public Function<BaseResponse<PageList<DriverOrderMsgWrapper>>, BaseResponse<PageList<DriverOrderMsgWrapper>>> deliveryRoutmapperMsg = pageListBaseResponse -> {
        if (pageListBaseResponse.getResult() == null) return pageListBaseResponse;
        for (int i = 0; i < pageListBaseResponse.getResult().getResult().size(); i++) {
            DriverOrderMsgWrapper driverOrderMsgWrapper = pageListBaseResponse.getResult().getResult().get(i);
            if ((driverOrderMsgWrapper.getDriverOrderWrapper().getDeliveryRoute() == null) || driverOrderMsgWrapper.getDriverOrderWrapper().getDeliveryRoute().isEmpty())
                return pageListBaseResponse;
            // 设置线路箭头
            driverOrderMsgWrapper.getDriverOrderWrapper().setSpannableStringBuilder(handlerDeliverRoute(driverOrderMsgWrapper.getDriverOrderWrapper().getDeliveryRoute()));
        }

        // 处理驾车线路
        return pageListBaseResponse;
    };


    /**
     * 时间转换 消息类型：DriverOrderMsgWrapper
     */
    public Function<BaseResponse<PageList<DriverOrderMsgWrapper>>, BaseResponse<PageList<DriverOrderMsgWrapper>>> orderCreateTimeMapperMsg = pageListBaseResponse -> {
        if (pageListBaseResponse.getResult() == null) return pageListBaseResponse;
        for (int i = 0; i < pageListBaseResponse.getResult().getResult().size(); i++) {
            DriverOrderMsgWrapper driverOrderMsgWrapper = pageListBaseResponse.getResult().getResult().get(i);
            @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = format.parse(driverOrderMsgWrapper.getDriverOrderWrapper().getCreateTime());
            driverOrderMsgWrapper.getDriverOrderWrapper().setCreateTime(DateUtils.formatDateTime(date) + " 派单");
        }
        return pageListBaseResponse;
    };

    /**
     * 处理驾车线路
     *
     * @param arrayList 路线列表
     */
    @NonNull
    public SpannableStringBuilder handlerDeliverRoute(List<String> arrayList) {
        SpanUtils spanUtils = new SpanUtils();
        // 去空数据
        List<String> list = new ArrayList<>();
        for (String s : arrayList) {
            if (!TextUtils.isEmpty(s)) {
                list.add(s);
            }
        }

        if (!list.isEmpty()) {
            int size = list.size();
            for (int j = 0; j < size; j++) {
                String value = list.get(j);
                spanUtils.append(value);
                // 如果是最后一组 就不要再加箭头
                if (j == size - 1) {
                    continue;
                }
                spanUtils.appendImage(mIc_dc_s02, SpanUtils.ALIGN_CENTER);
            }
        }
        return spanUtils.create();
    }
}
