package com.yaoguang.appcommon.phone.order.orderdetailnode;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.LatLons;
import com.yaoguang.greendao.entity.OrderDetailNodeList;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.OrderApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 韦理英
 * on 2017/6/26 0026.
 */

public class OrderDetailNodeInteractor implements OrderDetailNodeContract.Interactor{


    @Override
    public Observable<BaseResponse<List<LatLons>>> requestLatLonHistory(String orderId) {
        return Api.getInstance().retrofit.create(OrderApi.class).OrderLatLonHistory(orderId);
    }

    @Override
    public Observable<BaseResponse<OrderDetailNodeList>> requestData(String driverOrderId,String orderSn) {
        return Api.getInstance().retrofit.create(OrderApi.class).OrderDetailNote(driverOrderId,orderSn);
    }
}
