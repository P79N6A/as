package com.yaoguang.interfaces.driver.interactor.orderstatistics;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.FreightStatistic;

import java.util.HashSet;

import io.reactivex.Observable;


/**
 * 订单业务层
 * Created by zhongjh on 2017/5/4.
 */
public interface DOrderStatisticsInteractor {

    /**
     * 计算订单统计
     *  @param companyName         公司名称
     * @param startDate    开始时间
     * @param endDate        结束时间
     * @param type                时间范围类型 0、全部，1、本月，2、上月，3、近三月
     */
    Observable<BaseResponse<FreightStatistic>>  calculationData(HashSet<String> companyName, String startDate, String endDate, String type);

    /**
     *
     * @return 获取月初的第一天
     */
    String getMonthFirst();

    /**
     *
     * @return 获取月底的第一天
     */
    String getMonthLast();

}
