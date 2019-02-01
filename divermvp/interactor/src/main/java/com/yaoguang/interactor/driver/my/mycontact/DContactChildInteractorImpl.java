package com.yaoguang.interactor.driver.my.mycontact;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.Contact;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interactor.driver.contact.DContactChildInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.DriverApi;

import io.reactivex.Observable;

/**
 * 我的关联业务
 * Created by zhongjh on 2017/3/29.
 */
public class DContactChildInteractorImpl extends DCSBaseInteractorImpl implements DContactChildInteractor {

    @Override
    public Observable<BaseResponse<PageList<Contact>>> initContactCompanys(String companyName, String companyId, int intType, String type, int pageIndex) {
        //用户id
        String id = "";
        switch (type) {
            case "0":
                id = getDriver().getId();
                break;
            case "1":
                id = getAppUserWrapper().getId();
                break;
            case "2":
                id = getUserOwner().getId();
                break;
        }
        switch (intType) {
            case 0:   // 查询通过的
                return Api.getInstance().retrofit.create(DriverApi.class).contactPassList(id, type,pageIndex);
            case 1:  // 查询待审核的
                return Api.getInstance().retrofit.create(DriverApi.class).contactWaitList(id, type,pageIndex);
            default:  // 查询拒绝的
                return Api.getInstance().retrofit.create(DriverApi.class).contactRejectList(id, type,pageIndex);
        }
    }

    @Override
    public Observable<BaseResponse<String>> handleCancelContact(final String strContactId, final String type) {
        //用户id
        String id = "";
        switch (type) {
            case "0":
                id = getDriver().getId();
                break;
            case "1":
                id = getAppUserWrapper().getId();
                break;
            case "2":
                id = getUserOwner().getId();
                break;
        }

        return Api.getInstance().retrofit.create(DriverApi.class).contactDelete(id, strContactId, type);
    }

    @Override
    public Observable<BaseResponse<String>> handleDeleteContact(final String strContactId) {
        return Api.getInstance().retrofit.create(DriverApi.class).contactDeleteApply(strContactId);
    }
}
