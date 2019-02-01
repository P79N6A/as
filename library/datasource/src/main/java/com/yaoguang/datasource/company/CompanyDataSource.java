package com.yaoguang.datasource.company;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.greendao.entity.company.QRCode;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.api.company.CompanyApi;


import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/4/12.
 */
public class CompanyDataSource {

    /**
     * 获取企业列表
     *
     * @param name      搜索条件(可空)
     * @param pageIndex 页码
     * @return 列表
     */
    public Observable<BaseResponse<PageList<UserOfficeWrapper>>> getList(String name, int pageIndex) {
        // 获取公司
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        String userId = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            userId = DataStatic.getInstance().getDriver().getId();
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            userId = DataStatic.getInstance().getAppUserWrapper().getId();
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            userId = DataStatic.getInstance().getUserOwner().getId();
        }
        return companyApi.list(name, userId, pageIndex);
    }






}
