package com.yaoguang.datasource.api.company;

import com.yaoguang.greendao.entity.company.ViewTransferCancel;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/11/22.
 */

public interface CompanyTruckApi {

    String COMPANY_TRUCK = "company/truck";

    /**
     * 货代派单
     *
     * @param ids          -    工作单id
     * @param templateId   -     模板id
     * @param transferType -      派单类型(3:拖车派单)
     */
    @POST(COMPANY_TRUCK + "/transfer.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<String>> transfer(@Query("ids") String ids, @Query("templateId") String templateId, @Query("transferType") String transferType);

    /**
     * 派单管理 - 取消预览列表
     *
     * @param ids -    工作单id
     */
    @POST(COMPANY_TRUCK + "/transfer/cancelView.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<List<ViewTransferCancel>>> transferCancelView(@Query("ids") String ids);

    /**
     * 订单派单管理 - 取消派单
     *
     * @param viewTransferCancel 集合
     */
    @POST(COMPANY_TRUCK + "/transfer/cancel.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<String>> transferCancel(@Body ArrayList<ViewTransferCancel> viewTransferCancel);

}
