package com.yaoguang.driver.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.data.source.remote.MessageRemoteDataSource;
import com.yaoguang.driver.util.OrderAssistant;
import com.yaoguang.greendao.entity.MessageInfo;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.greendao.entity.UnreadNum;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/1/10
 * 描    述：消息仓库
 * =====================================
 */

public class MessageRepository implements MessageDataSource {
    private MessageRemoteDataSource mMessageRemoteDataSource;
    private DriverRepository mDriverRepository;

    private static MessageRepository INSTANCE;

    public static MessageRepository getInstance(@NotNull MessageRemoteDataSource messageRemoteDataSource, @NotNull DriverRepository driverRepository) {
        if (INSTANCE == null) {
            synchronized (MessageRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MessageRepository(messageRemoteDataSource, driverRepository);
                }
            }
        }
        return INSTANCE;
    }

    private MessageRepository(@NotNull MessageRemoteDataSource messageRemoteDataSource, @NotNull DriverRepository driverRepository) {
        this.mMessageRemoteDataSource = checkNotNull(messageRemoteDataSource);
        this.mDriverRepository = checkNotNull(driverRepository);
    }


    /**
     * 获取消息
     *
     * @param pageIndex  页码
     * @param isHomePage 是否首页
     */

    @NonNull
    @Override
    public Flowable<BaseResponse<PageList<Order>>> getMessageList(int pageIndex, boolean isHomePage) {
        String driverId = mDriverRepository.getDriver().getId();

        Flowable<BaseResponse<PageList<Order>>> homeMessageList;
        if (isHomePage) { // getHomeMessageList(getDriverId(), ObjectUtils.parseString(pageIndex), type, "0");
            homeMessageList = mMessageRemoteDataSource.getHomeMessageList(driverId, pageIndex, "1", "0")
                    .map(OrderAssistant.getInstance().deliveryRoutmapper)
                    .map(OrderAssistant.getInstance().orderCreateTimeMapper)
                    .map(OrderAssistant.getInstance().driverOrderMsgMapper)
                    .map(OrderAssistant.getInstance().orderMarkmapper);
        } else {
            homeMessageList = mMessageRemoteDataSource.getMessageOrderList(driverId, pageIndex)
                    .map(OrderAssistant.getInstance().deliveryRoutmapper)
                    .map(OrderAssistant.getInstance().orderCreateTimeMapper)
                    .map(OrderAssistant.getInstance().driverOrderMsgMapper)
                    .map(OrderAssistant.getInstance().orderMarkmapper);
        }
        return homeMessageList;
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<PageList<Order>>> getHomeMessageList(@Nullable String driverId, int pageIndex, @NonNull String type, @NonNull String msgType) {
        return null;
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<PageList<Order>>> getMessageOrderList(@NonNull String driverId, int pageIndex) {
        return null;
    }

    @NonNull
    @Override
    public Flowable<Boolean> readBatch(@NonNull String ids) {
        return mMessageRemoteDataSource.readBatch(checkNotNull(ids));
    }

    /**
     * 批量删除消息
     * @param selectIds 消息id
     */
    @NonNull
    @Override
    public Flowable<Boolean> orderMessageDeleted(@NonNull String selectIds) {
        return mMessageRemoteDataSource.orderMessageDeleted(checkNotNull(selectIds));
    }

    /**
     * 平台公告
     * @param driverId
     * @param pageIndex     页码
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<PageList<MessageInfo>>> getPlatformMessageData(@Nullable String driverId, int pageIndex) {
        if (driverId == null) driverId = mDriverRepository.getDriver().getId();
        return mMessageRemoteDataSource.getPlatformMessageData(driverId, pageIndex);
    }

    /**
     * 删除公告消息
     * @param driverId
     * @param msgId 消息id
     */
    @NonNull
    @Override
    public Flowable<Boolean> deletePlatformMessage(@Nullable String driverId, @NonNull String msgId) {
        if (driverId == null) driverId = mDriverRepository.getDriver().getId();
        return mMessageRemoteDataSource.deletePlatformMessage(driverId, msgId);
    }

    /**
     * 设置已读公告
     * @param driverId
     * @param msgId
     */
    @NonNull
    @Override
    public Flowable<Boolean> platformReadState(@Nullable String driverId, @NonNull String msgId) {
        if (driverId == null) driverId = mDriverRepository.getDriver().getId();
        return mMessageRemoteDataSource.platformReadState(driverId, msgId);
    }

    /**
     * 获取业务消息，平台公告未读数
     * @param driverId 司机id
     */
    @Override
    @NonNull
    public Flowable<UnreadNum> getUnreadNum(@Nullable String driverId) {
        if (driverId == null) driverId = mDriverRepository.getDriver().getId();
        return mMessageRemoteDataSource.getUnreadNum(driverId);
    }

    /**
     * 获取验证码
     * @param strMobile 手机号
     * @param codeType  APP端类型
     * @return
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<String>> getVerificationCode(@NonNull String strMobile, @NonNull String codeType) {
        return mMessageRemoteDataSource.getVerificationCode(checkNotNull(strMobile),checkNotNull(codeType));
    }
}
