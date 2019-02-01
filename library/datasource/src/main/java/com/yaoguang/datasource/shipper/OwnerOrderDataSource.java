package com.yaoguang.datasource.shipper;

import com.yaoguang.datasource.common.OrderDataSource;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.greendao.entity.common.ViewForwardOrder;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.greendao.entity.AppOrderWrapper;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.datasource.api.shipper.OwnerOrderApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/1/16.
 */

public class OwnerOrderDataSource extends DCSBaseDataSource implements OrderDataSource<ViewForwardOrder, TruckBills> {

    /**
     * 货代工作单查询
     *
     * @param condition 条件实体
     * @param pageIndex 页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppOrderWrapper>>> getForwardOrders(SysConditionWrapper condition, int pageIndex) {
        OwnerOrderApi ownerOrderApi = Api.getInstance().retrofit.create(OwnerOrderApi.class);
        return ownerOrderApi.getForwardOrders(condition, getUserOwner().getId(), pageIndex);
    }

    /**
     * 货代工作单明细
     *
     * @param billId 工作单主键id
     * @return 返回货代工作单明细
     */
    @Override
    public Observable<BaseResponse<ViewForwardOrder>> getForwardOrderById(String billId, String clientId) {
        OwnerOrderApi ownerOrderApi = Api.getInstance().retrofit.create(OwnerOrderApi.class);
        return ownerOrderApi.getForwardOrderById(clientId, billId);
    }

    /**
     * 拖车工作单明细
     *
     * @param billId 工作单主键id
     * @return 返回拖车工作单明细
     */
    @Override
    public Observable<BaseResponse<TruckBills>> getTruckOrderById(String billId, String clientId) {
        OwnerOrderApi ownerOrderApi = Api.getInstance().retrofit.create(OwnerOrderApi.class);
        return ownerOrderApi.getTruckOrderById(clientId, billId);
    }
}
