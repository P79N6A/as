package com.yaoguang.datasource.company;

import com.yaoguang.datasource.api.company.CompanyApi;
import com.yaoguang.datasource.api.company.CompanyForwardApi;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.greendao.entity.company.ViewTransferCancel;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/11/18.
 */

public class CompanyForwardDataSource {

    /**
     * 派单
     *
     * @param ids          -    工作单id
     * @param templateId   -     模板id
     * @param transferType -      派单类型(0:货代派单 1:装货派单 2:送货派单)
     */
    public Observable<BaseResponse<String>> transfer(String ids, String templateId, String transferType) {
        CompanyForwardApi companyForwardApi = Api.getInstance().retrofit.create(CompanyForwardApi.class);
        return companyForwardApi.transfer(ids, templateId, transferType);
    }

    /**
     * 派单管理 - 取消预览列表
     *
     * @param ids -    工作单id
     */
    public Observable<BaseResponse<List<ViewTransferCancel>>> transferCancelView(String ids) {
        CompanyForwardApi companyForwardApi = Api.getInstance().retrofit.create(CompanyForwardApi.class);
        return companyForwardApi.transferCancelView(ids);
    }

    /**
     * 订单派单管理 - 取消派单
     *
     * @param viewTransferCancel 集合
     */
    public Observable<BaseResponse<String>> transferCancel(ArrayList<ViewTransferCancel> viewTransferCancel) {
        CompanyForwardApi companyForwardApi = Api.getInstance().retrofit.create(CompanyForwardApi.class);
        return companyForwardApi.transferCancel(viewTransferCancel);
    }

}
