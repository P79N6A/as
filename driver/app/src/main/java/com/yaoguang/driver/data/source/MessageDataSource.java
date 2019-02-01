package com.yaoguang.driver.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.MessageInfo;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.greendao.entity.UnreadNum;

import io.reactivex.Flowable;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/1/10
 * 描    述：消息仓库数据源接口
 * =====================================
 */

public interface MessageDataSource {

    @NonNull
    Flowable<BaseResponse<PageList<Order>>> getMessageList(int pageIndex, boolean isHomePage);

    @NonNull
    Flowable<BaseResponse<PageList<Order>>> getHomeMessageList(@Nullable String driverId, int pageIndex, @NonNull String type, @NonNull String msgType);

    @NonNull
    Flowable<BaseResponse<PageList<Order>>> getMessageOrderList(@NonNull String driverId, int pageIndex);

    @NonNull
    Flowable<Boolean> readBatch(@NonNull String ids);

    @NonNull
    Flowable<Boolean> orderMessageDeleted(@NonNull String selectIds);

    @NonNull
    Flowable<BaseResponse<PageList<MessageInfo>>> getPlatformMessageData(@Nullable String driverId, int pageIndex);

    @NonNull
    Flowable<Boolean> deletePlatformMessage(@Nullable String driverId, @NonNull String msgId);

    @NonNull
    Flowable<Boolean> platformReadState(String driverId, @NonNull String msgId);

    @NonNull
    Flowable<UnreadNum> getUnreadNum(@Nullable String driverId);

    @NonNull
    Flowable<BaseResponse<String>> getVerificationCode(@NonNull  String strMobile, @NonNull String codeType);
}
