package com.yaoguang.shipper.order;

import com.yaoguang.appcommon.phone.order.OrderContract;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppOrderWrapper;
import com.yaoguang.greendao.entity.SysConditionWrapper;

import io.reactivex.Observable;

/**
 * 订单跟踪
 * Created by zhongjh on 2018/1/8.
 */
public class OrderPresenter extends com.yaoguang.appcommon.phone.order.OrderPresenter<AppOrderWrapper> {

    OrderPresenter(OrderContract.View view, int billType) {
        super(view, billType);
    }

    @Override
    protected Observable<BaseResponse<PageList<AppOrderWrapper>>> initDatas(SysConditionWrapper condition, int pageIndex) {
        return mOwnerOrderDataSource.getForwardOrders(condition, pageIndex);
    }

}
