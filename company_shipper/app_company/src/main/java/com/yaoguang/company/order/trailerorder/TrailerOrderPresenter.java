package com.yaoguang.company.order.trailerorder;

import com.yaoguang.appcommon.phone.order.OrderContract;
import com.yaoguang.appcommon.phone.order.OrderPresenter;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.common.TruckBills;

import io.reactivex.Observable;

/**
 * 拖车订单
 * Created by zhongjh on 2018/1/8.
 */
public class TrailerOrderPresenter extends OrderPresenter<TruckBills> {

    TrailerOrderPresenter(OrderContract.View view, int billType) {
        super(view, billType);
    }

    @Override
    protected Observable<BaseResponse<PageList<TruckBills>>> initDatas(SysConditionWrapper condition, int pageIndex) {
        return mCompanyOrderDataSource.getTruckOrders(condition, pageIndex);
    }

}
