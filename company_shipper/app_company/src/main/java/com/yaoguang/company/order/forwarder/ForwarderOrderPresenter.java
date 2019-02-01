package com.yaoguang.company.order.forwarder;

import com.yaoguang.appcommon.phone.order.OrderContract;
import com.yaoguang.appcommon.phone.order.OrderPresenter;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.greendao.entity.SysConditionWrapper;

import io.reactivex.Observable;

/**
 * 货代订单
 * Created by zhongjh on 2018/1/8.
 */
public class ForwarderOrderPresenter extends OrderPresenter<FreightBills> {

    ForwarderOrderPresenter(OrderContract.View view, int billType) {
        super(view, billType);
    }

    @Override
    protected Observable<BaseResponse<PageList<FreightBills>>> initDatas(SysConditionWrapper condition, int pageIndex) {
        return mCompanyOrderDataSource.getForwardOrders(condition, pageIndex);
    }

}
