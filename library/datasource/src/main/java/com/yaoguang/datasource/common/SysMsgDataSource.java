package com.yaoguang.datasource.common;

import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.api.common.SysMsgApi;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/5/16.
 */

public class SysMsgDataSource {

    private SysMsgApi mSysMsgApi = Api.getInstance().retrofit.create(SysMsgApi.class);

    /**
     * @param noticeType 0是公告 1是消息
     */
    public Observable<BaseResponse<PageList<SysMsg>>> list(int pageIndex, int noticeType) {
        String platformType = "";
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            platformType = "2";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            platformType = "3";
        } else if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            platformType = "1";
        }
        return mSysMsgApi.list(platformType, DataStatic.getInstance().getId(), pageIndex, noticeType);
    }

    /**
     * @param noticeType 0是公告 1是消息
     */
    public Observable<BaseResponse<List<SysMsg>>> indexList(int pageIndex,  int noticeType) {
        String platformType = "";
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            platformType = "2";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            platformType = "3";
        } else if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            platformType = "1";
        }
        return mSysMsgApi.indexList(platformType, pageIndex,  noticeType);
    }

    /**
     * 删除平台公告
     *
     * @param sysMsgId 消息id
     * @return 返回成功信息
     */
    public Observable<BaseResponse<String>> delete(String sysMsgId) {
        return mSysMsgApi.delete(DataStatic.getInstance().getId(), sysMsgId);
    }

    /**
     * 将平台消息设为已读  userId
     *
     * @param sysMsgId 消息id
     * @return 返回成功信息
     */
    public Observable<BaseResponse<String>> readBatch(String sysMsgId) {
        return mSysMsgApi.readBatch(DataStatic.getInstance().getId(), sysMsgId);
    }

    /**
     * 获取节日的图片，登录界面之前显示
     *
     * @return 返回数据源
     */
    public Observable<BaseResponse<HashMap<String, Integer>>> unReadNumber() {
        String id = DataStatic.getInstance().getId();
        String platformType = "";
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            platformType = "2";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            platformType = "3";
        }else if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            platformType = "1";
        }
        return mSysMsgApi.unReadNumber(id, platformType);
    }

    /**
     * 获取弹窗消息
     *
     * @return 返回数据源
     */
    public Observable<BaseResponse<List<SysMsg>>> getPopUpMsg() {
        String id = DataStatic.getInstance().getId();
        String platformType = "";
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            platformType = "2";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            platformType = "3";
        }else if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            platformType = "1";
        }
        return mSysMsgApi.getPopUpMsg(id, platformType);
    }

    /**
     * 设置成已弹窗
     *
     * @param msgId 消息id
     * @return 返回数据源
     */
    public Observable<BaseResponse<String>> updatePopUp(String msgId) {
        String id = DataStatic.getInstance().getId();
        return mSysMsgApi.updatePopUp(id, msgId);
    }


}
