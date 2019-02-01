package com.yaoguang.driver.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.data.source.MessageDataSource;
import com.yaoguang.driver.net.factory.ApiMessageFactory;
import com.yaoguang.greendao.entity.DriverOrderMsg;
import com.yaoguang.greendao.entity.MessageInfo;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.greendao.entity.UnreadNum;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 消息远程数据源
 * Created by wly on 2018/1/9 0009.
 */

public class MessageRemoteDataSource implements MessageDataSource {

    private static MessageRemoteDataSource INSTANCE;

    public static MessageRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (MessageRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MessageRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<PageList<Order>>> getMessageList(int pageIndex, boolean isHomePage) {
        return null;
    }

    /**
     * 获取首页订单列表
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     * @param type      页码 type(0：业务消息 1：平台公告)
     * @param msgType   noticeType
     */
    @Override
    @NonNull
    public Flowable<BaseResponse<PageList<Order>>> getHomeMessageList(@Nullable String driverId, int pageIndex, @NonNull String type, @NonNull String msgType) {
        return ApiMessageFactory.getHomeMessageList(checkNotNull(driverId), checkNotNull(pageIndex), checkNotNull(type), checkNotNull(msgType)).map(handlerOrderMessage).subscribeOn(Schedulers.io());
    }

    /**
     * 获取消息订单列表
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     */
    @Override
    @NonNull
    public Flowable<BaseResponse<PageList<Order>>> getMessageOrderList(@NonNull String driverId, int pageIndex) {
        return ApiMessageFactory.getMessageOrderList(checkNotNull(driverId), pageIndex).map(handlerOrderMessage);
    }

    @NonNull
    private Function<BaseResponse<PageList<DriverOrderMsg>>, BaseResponse<PageList<Order>>> handlerOrderMessage = value -> {
        BaseResponse<PageList<Order>> newResult = new BaseResponse<>();
        newResult.setResult(new PageList<>());

        ArrayList<Order> list = new ArrayList<>();
        for (DriverOrderMsg driverOrderMsg : value.getResult().getResult()) {    // 设置订单数据
            Order driverOrderWrapper = driverOrderMsg.getDriverOrderWrapper();
            driverOrderWrapper.setDriverOrderMsg(driverOrderMsg);
            list.add(driverOrderWrapper);
        }
        newResult.setState(value.getState());
        newResult.setMessage(value.getMessage());
        newResult.getResult().setResult(list);
        return newResult;
    };


    /**
     * 批量设置已读
     *
     * @param ids id列表
     */
    @Override
    @NonNull
    public Flowable<Boolean> readBatch(@NonNull String ids) {
        return ApiMessageFactory.readBatch(checkNotNull(ids)).map(stringBaseResponse -> stringBaseResponse.getState().equals("200"));
    }

    @Override
    @NonNull
    public Flowable<Boolean> orderMessageDeleted(@NonNull String selectIds) {
        return ApiMessageFactory.orderMessageDeleted(checkNotNull(selectIds), 1).map(stringBaseResponse -> stringBaseResponse.getState().equals("200"));
    }


    @NonNull
    @Override
    public Flowable<BaseResponse<PageList<MessageInfo>>> getPlatformMessageData(@Nullable String driverId, int pageIndex) {
        return ApiMessageFactory.platformMessageList(checkNotNull(driverId), pageIndex, 1, 0);
    }

    @NonNull
    @Override
    public Flowable<Boolean> deletePlatformMessage(@Nullable String driverId, @NonNull String msgId) {
        return ApiMessageFactory.deletePlatformMessage(checkNotNull(driverId), checkNotNull(msgId)).map(stringBaseResponse -> stringBaseResponse.getState().equals("200"));
    }

    @NonNull
    @Override
    public Flowable<Boolean> platformReadState(@Nullable String driverId, @NonNull String msgId) {
        return ApiMessageFactory.setReadPlatformMessage(checkNotNull(driverId), checkNotNull(msgId)).map(stringBaseResponse -> stringBaseResponse.getState().equals("200"));
    }

    @NonNull
    @Override
    public Flowable<UnreadNum> getUnreadNum(@Nullable String driverId) {
        return ApiMessageFactory.getUnreadNum(checkNotNull(driverId), 1).map(BaseResponse::getResult);
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> getVerificationCode(@NonNull String strMobile, @NonNull String codeType) {
        return ApiMessageFactory.getVerificationCode(strMobile, codeType);
    }
}
