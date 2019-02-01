package com.yaoguang.interactor.driver.my.mycontact;

import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interactor.driver.contact.DContactSearchInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyApi;
import com.yaoguang.datasource.api.DriverApi;

import io.reactivex.Observable;

/**
 * 挂靠公司查询业务
 * Created by zhongjh on 2017/3/29.
 */
public class DContactSearchInteractorImpl extends DCSBaseInteractorImpl implements DContactSearchInteractor {


    @Override
    public Observable<BaseResponse<PageList<UserOfficeWrapper>>> initContactCompanys(final String companyName, final int pageIndex, final String type) {
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
        return Api.getInstance().retrofit.create(CompanyApi.class).list(companyName, id, pageIndex);
    }

    @Override
    public Observable<BaseResponse<String>> handleAddContact(final String companyId, final String reason, final String type) {
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

        return Api.getInstance().retrofit.create(DriverApi.class).contactAdd(id, companyId, reason, type);
    }
}
