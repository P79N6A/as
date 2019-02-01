package com.yaoguang.datasource.api.shipper;

import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRate;
import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRateCondition;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/8/29.
 */
public interface OwnerPriceApi {

    String OWNERPRICE = "owner/price";

    /**
     * 报价查询
     *
     * @param priceShipperReceivableRateCondition 条件类
     * @param companyId 关联公司id
     * @param userId      登录id
     * @param pageIndex 页数
     */
    @POST(OWNERPRICE + "/getShipperFee.do?")
    Observable<BaseResponse<PageList<PriceShipperReceivableRate>>> getShipperFee(@Body PriceShipperReceivableRateCondition priceShipperReceivableRateCondition,
                                                                                 @Query("userId") String userId, @Query("companyId") String companyId, @Query("pageIndex") int pageIndex);



}
