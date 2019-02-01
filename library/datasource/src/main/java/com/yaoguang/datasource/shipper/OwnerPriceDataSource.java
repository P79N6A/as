package com.yaoguang.datasource.shipper;

import com.yaoguang.datasource.api.shipper.OwnerPriceApi;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRate;
import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRateCondition;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/8/29.
 */
public class OwnerPriceDataSource extends DCSBaseDataSource {

    /**
     * 报价查询
     *
     * @param condition 条件实体
     * @param pageIndex 页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<PriceShipperReceivableRate>>> getShipperFee(PriceShipperReceivableRateCondition condition, int pageIndex) {
        OwnerPriceApi ownerPriceApi = Api.getInstance().retrofit.create(OwnerPriceApi.class);
        return ownerPriceApi.getShipperFee(condition, getUserOwner().getId(),condition.getCompanyId(), pageIndex);
    }

}
