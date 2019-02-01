package com.yaoguang.datasource.common;

import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * 目前是 企业端和货主端的共同实现类,F代表ForwardOrder货代，T代表TruckBills拖车
 * Created by zhongjh on 2018/8/31.
 */
public interface OrderDataSource<F, T> {

    /**
     * 货代工作单明细
     *
     * @param billId 工作单主键id
     * @param clientId 公司id
     * @return 返回货代工作单明细
     */
    Observable<BaseResponse<F>> getForwardOrderById(String billId,String clientId);

    /**
     * 拖车工作单明细
     *
     * @param billId 工作单主键id
     * @param clientId 公司id
     * @return 返回拖车工作单明细
     */
    Observable<BaseResponse<T>> getTruckOrderById(String billId,String clientId);

}
