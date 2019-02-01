package com.yaoguang.datasource.driver;

import android.text.SpannableStringBuilder;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.util.OrderAssistant;
import com.yaoguang.greendao.entity.driver.DriverOrderProgressWrapper;
import com.yaoguang.greendao.entity.driver.UnreadNum;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.api.driver.DriverApi;
import com.yaoguang.datasource.api.driver.DriverIndexApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**.
 * 首页的
 * Created by zhongjh on 2018/5/16.
 */

public class DriverIndexDataSource {

    private DriverIndexApi mDriverIndexApi = Api.getInstance().retrofit.create(DriverIndexApi.class);

    /**
     * 获取当前订单进度
     */
    public Observable<BaseResponse<DriverOrderProgressWrapper>> getOrderCurrentProgress(int ic_dc_s02) {
        return mDriverIndexApi.getOrderCurrentProgress(DataStatic.getInstance().getDriver().getId())
                .observeOn(Schedulers.io())
                .map(driverOrderProgressWrapperBaseResponse -> {
                    if (driverOrderProgressWrapperBaseResponse.getResult() != null && driverOrderProgressWrapperBaseResponse.getResult().getDeliveryRoute() != null) {
                        // 设置线路箭头
                        SpannableStringBuilder spannableStringBuilder = OrderAssistant.getInstance(ic_dc_s02).handlerDeliverRoute(driverOrderProgressWrapperBaseResponse.getResult().getDeliveryRoute());
                        driverOrderProgressWrapperBaseResponse.getResult().setSpannableStringBuilder(spannableStringBuilder);
                    }
                    return driverOrderProgressWrapperBaseResponse;
                }).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取司机的未读数量
     */
    public Observable<BaseResponse<UnreadNum>> msgNumber() {
        return mDriverIndexApi.msgNumber(DataStatic.getInstance().getId(),1);
    }


}
