package com.yaoguang.datasource.common;

import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.greendao.entity.common.DriverFollowCompany;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.api.common.ContactApi;

import io.reactivex.Observable;

/**
 * 关注
 * Created by zhongjh on 2018/4/12.
 */
public class ContactDataSource {

    /**
     * 已经关注成功的公司信息列表
     *
     * @param pageIndex 页码
     * @return 列表数据
     */
    public Observable<BaseResponse<PageList<DriverContactCompany>>> getPassListVersion2(int pageIndex) {
        //获取柜型
        ContactApi contactApi = Api.getInstance().retrofit.create(ContactApi.class);
        String userId = "";
        String type = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            userId = DataStatic.getInstance().getDriver().getId();
            type = "0";
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            userId = DataStatic.getInstance().getAppUserWrapper().getId();
            type = "1";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            userId = DataStatic.getInstance().getUserOwner().getId();
            type = "2";
        }
        return contactApi.getPassListVersion2(type, userId, pageIndex);
    }

    /**
     * 已经关注成功的公司信息列表,获取前1000条
     *
     * @param pageIndex 页码
     * @return 列表数据
     */
    public Observable<BaseResponse<PageList<DriverContactCompany>>> getPassListVersion2All(int pageIndex) {
        //获取柜型
        ContactApi contactApi = Api.getInstance().retrofit.create(ContactApi.class);
        String userId = "";
        String type = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            userId = DataStatic.getInstance().getDriver().getId();
            type = "0";
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            userId = DataStatic.getInstance().getAppUserWrapper().getId();
            type = "1";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            userId = DataStatic.getInstance().getUserOwner().getId();
            type = "2";
        }
        return contactApi.getPassListVersion2All(type, userId, pageIndex,1000);
    }

    /**
     * 关注
     *
     * @param companyId 企业id
     * @return 返回消息
     */
    public Observable<BaseResponse<String>> contact(String companyId) {
        //获取柜型
        ContactApi contactApi = Api.getInstance().retrofit.create(ContactApi.class);
        String userId = "";
        String type = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            userId = DataStatic.getInstance().getDriver().getId();
            type = "0";
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            userId = DataStatic.getInstance().getAppUserWrapper().getId();
            type = "1";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            userId = DataStatic.getInstance().getUserOwner().getId();
            type = "2";
        }
        return contactApi.contact(type, userId, companyId);
    }

    /**
     * 取消关联
     *
     * @param contactId 关注的id
     * @return 返回消息
     */
    public Observable<BaseResponse<String>> delete(String contactId) {
        //获取柜型
        ContactApi contactApi = Api.getInstance().retrofit.create(ContactApi.class);
        String userId = "";
        String type = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            userId = DataStatic.getInstance().getDriver().getId();
            type = "0";
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            userId = DataStatic.getInstance().getAppUserWrapper().getId();
            type = "1";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            userId = DataStatic.getInstance().getUserOwner().getId();
            type = "2";
        }
        return contactApi.delete(type, contactId, userId);
    }

    /**
     * 审核通过
     *
     * @param contactId 关注的id
     * @return 返回消息
     */
    public Observable<BaseResponse<String>> passAduit(String contactId) {
        //获取柜型
        ContactApi contactApi = Api.getInstance().retrofit.create(ContactApi.class);
        String userId = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            userId = DataStatic.getInstance().getDriver().getId();
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            userId = DataStatic.getInstance().getAppUserWrapper().getId();
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            userId = DataStatic.getInstance().getUserOwner().getId();
        }
        return contactApi.passAduit(contactId, userId);
    }

    /**
     * 忽略申请信息
     *
     * @param contactId 关注的id
     * @return 返回消息
     */
    public Observable<BaseResponse<String>> ignoreAduit(String contactId) {
        //获取柜型
        ContactApi contactApi = Api.getInstance().retrofit.create(ContactApi.class);
        String userId = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            userId = DataStatic.getInstance().getDriver().getId();
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            userId = DataStatic.getInstance().getAppUserWrapper().getId();
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            userId = DataStatic.getInstance().getUserOwner().getId();
        }
        return contactApi.ignoreAduit(contactId, userId);
    }

    /**
     * 新的关联-获取关联消息列表
     *
     * @param name      名称
     * @param auditFlag 状态（0-未同意，1-已关联，2-已拒绝）
     * @param pageIndex 页码
     * @return 数据源
     */
    public Observable<BaseResponse<PageList<DriverFollowCompany>>> getApplyInfo(String name, String auditFlag, int pageIndex) {
        //获取柜型
        ContactApi contactApi = Api.getInstance().retrofit.create(ContactApi.class);
        String userId = "";
        String type = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            userId = DataStatic.getInstance().getDriver().getId();
            type = "0";
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            userId = DataStatic.getInstance().getAppUserWrapper().getId();
            type = "1";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            userId = DataStatic.getInstance().getUserOwner().getId();
            type = "2";
        }
        return contactApi.getApplyInfo(type, userId, name, auditFlag, pageIndex);
    }

    /**
     * 获取公司信息
     *
     * @param companyId 公司id
     */
    public Observable<BaseResponse<UserOfficeWrapper>> getCompanyInfo(String companyId) {
        //获取柜型
        ContactApi contactApi = Api.getInstance().retrofit.create(ContactApi.class);
        String userId = "";
        String type = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            userId = DataStatic.getInstance().getDriver().getId();
            type = "0";
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            userId = DataStatic.getInstance().getAppUserWrapper().getId();
            type = "1";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            userId = DataStatic.getInstance().getUserOwner().getId();
            type = "2";
        }
        return contactApi.getCompanyInfo(userId, companyId, type);
    }


}
