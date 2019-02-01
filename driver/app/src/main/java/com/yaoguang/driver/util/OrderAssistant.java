package com.yaoguang.driver.util;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.yaoguang.common.common.DateUtils;
import com.yaoguang.common.common.SpanUtils;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.R;
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

    public static OrderAssistant getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderAssistant.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderAssistant();
                }
            }
        }
        return INSTANCE;
    }

    /**
     *  处理 派，套，拼，告，改，关
     */
    public Function<BaseResponse<PageList<Order>>, BaseResponse<PageList<Order>>> orderMarkmapper = pageListBaseResponse -> {
        if (pageListBaseResponse.getResult() == null) return pageListBaseResponse;
        for (int i = 0; i < pageListBaseResponse.getResult().getResult().size(); i++) {
            Order order = pageListBaseResponse.getResult().getResult().get(i);
            String[] orderMark = order.getOrderMark().split(",");
            List<Integer> list = new ArrayList<>();
            for (String s : orderMark) {
                if (!TextUtils.isEmpty(s)) {
                    list.add(getRes(s));
                }
            }
            order.setOrderMarkList(list);
        }

        return pageListBaseResponse;
    };

    /**
     * 处理 派改关
     */
    public Function<BaseResponse<PageList<Order>>, BaseResponse<PageList<Order>>> driverOrderMsgMapper = pageListBaseResponse -> {
        if (pageListBaseResponse.getResult() == null) return pageListBaseResponse;

        for (int i = 0; i < pageListBaseResponse.getResult().getResult().size(); i++) {
            Order order = pageListBaseResponse.getResult().getResult().get(i);
            if (order.getDriverOrderMsg() != null && order.getDriverOrderMsg().getMsgType() != null) {
                switch (order.getDriverOrderMsg().getMsgType()) {
                    case 0: //:"派"
                        order.getDriverOrderMsg().setMsgTypeRes(getRes("派"));
                        break;
                    case 1: //:"改"
                        order.getDriverOrderMsg().setMsgTypeRes(getRes("改"));
                        break;
                    case 2: //:"关"
                        order.getDriverOrderMsg().setMsgTypeRes(getRes("关"));
                        break;

                }
            }
        }
        return pageListBaseResponse;
    };

    /**
     * 处理线路转换
     */
    public Function<BaseResponse<PageList<Order>>, BaseResponse<PageList<Order>>> deliveryRoutmapper = pageListBaseResponse -> {
        if (pageListBaseResponse.getResult() == null) return pageListBaseResponse;
        for (int i = 0; i < pageListBaseResponse.getResult().getResult().size(); i++) {
            Order order = pageListBaseResponse.getResult().getResult().get(i);
            if ((order.getDeliveryRoute() == null) || order.getDeliveryRoute().isEmpty())
                return pageListBaseResponse;
            // 设置线路箭头
            order.setSpannableStringBuilder(handlerDeliverRoute(order.getDeliveryRoute()));
        }

        // 处理驾车线路
        return pageListBaseResponse;
    };


    /**
     * 时间转换
     */
    public Function<BaseResponse<PageList<Order>>, BaseResponse<PageList<Order>>> orderCreateTimeMapper = pageListBaseResponse -> {
        if (pageListBaseResponse.getResult() == null) return pageListBaseResponse;
        for (int i = 0; i < pageListBaseResponse.getResult().getResult().size(); i++) {
            Order order = pageListBaseResponse.getResult().getResult().get(i);
            @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = format.parse(order.getCreateTime());
            order.setCreateTime(DateUtils.formatDateTime(date) + " 派单");
        }
        return pageListBaseResponse;
    };

    /**
     * 获取标志 派，套，拼，告，改，关
     *
     * @param value 标志
     * @return 文字标志
     */
    private int getRes(String value) {
        int res = 0;
        switch (value) {
            case "派":
                res = R.drawable.ic_pai;
                break;
            case "套":
                res = R.drawable.ic_tao;
                break;
            case "拼":
                res = R.drawable.ic_pin;
                break;
            case "告":
                res = R.drawable.ic_gao;
                break;
            case "改":
                res = R.drawable.ic_gai;
                break;
            case "关":
                res = R.drawable.ic_guan;
                break;
        }
        return res;
    }

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
                spanUtils.appendImage(R.drawable.ic_dc_s02, SpanUtils.ALIGN_CENTER);
            }
        }
        return spanUtils.create();
    }
}
