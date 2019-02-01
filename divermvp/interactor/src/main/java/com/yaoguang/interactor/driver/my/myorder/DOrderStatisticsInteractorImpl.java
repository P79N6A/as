package com.yaoguang.interactor.driver.my.myorder;

import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interfaces.driver.interactor.orderstatistics.DOrderStatisticsInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.OrderApi;

import java.util.HashSet;

import io.reactivex.Observable;

/**
 * 订单统计业务层
 * Created by zhongjh on 2017/5/5.
 */
public class DOrderStatisticsInteractorImpl extends DCSBaseInteractorImpl implements DOrderStatisticsInteractor {

    @Override
    public Observable<BaseResponse<FreightStatistic>> calculationData(HashSet<String> companyIds, String startDate, String endDate, String type) {
        //司机id
        final String driverId = getDriver().getId();
        String ids = ObjectUtils.subString(companyIds);
        ULog.i("companyName" + ids + "startDate" + startDate + "endDate" + endDate + "type" + type);
        return Api.getInstance().retrofit.create(OrderApi.class).count(driverId, ids, startDate, endDate, type);
    }

    @Override
    public String getMonthFirst() {
        return DateUtils.getMonthFirst();
    }

    @Override
    public String getMonthLast() {
        return DateUtils.getMonthLast();
    }

}
