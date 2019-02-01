package com.yaoguang.datasource.company;

import com.yaoguang.datasource.api.company.CompanyTruckApi;
import com.yaoguang.greendao.entity.company.ViewTransferCancel;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/11/18.
 */

public class CompanyTruckDataSource {

    /**
     * 派单
     *
     * @param ids          -    工作单id
     * @param templateId   -     模板id
     * @param transferType -      派单类型(0:货代派单 1:装货派单 2:送货派单)
     */
    public Observable<BaseResponse<String>> transfer(String ids, String templateId, String transferType) {
        CompanyTruckApi companyTruckApi = Api.getInstance().retrofit.create(CompanyTruckApi.class);
        return companyTruckApi.transfer(ids, templateId, transferType);
    }

    /**
     * 派单管理 - 取消预览列表
     *
     * @param ids -    工作单id
     */
    public Observable<BaseResponse<List<ViewTransferCancel>>> transferCancelView(String ids) {
        CompanyTruckApi companyTruckApi = Api.getInstance().retrofit.create(CompanyTruckApi.class);
        return companyTruckApi.transferCancelView(ids);
    }

    /**
     * 订单派单管理 - 取消派单
     *
     * @param viewTransferCancel 集合
     */
    public Observable<BaseResponse<String>> transferCancel(ArrayList<ViewTransferCancel> viewTransferCancel) {
        CompanyTruckApi companyTruckApi = Api.getInstance().retrofit.create(CompanyTruckApi.class);
        return companyTruckApi.transferCancel(viewTransferCancel);
    }

}
