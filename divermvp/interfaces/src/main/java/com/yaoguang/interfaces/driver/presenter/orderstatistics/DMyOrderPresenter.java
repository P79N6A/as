package com.yaoguang.interfaces.driver.presenter.orderstatistics;

import com.yaoguang.lib.base.interfaces.BasePresenter;

import java.util.HashSet;

/**
 * 订单统计控制层
 * Created by zhongjh on 2017/5/4.
 */
public interface DMyOrderPresenter extends BasePresenter {

    /**
     * @param companyIds   公司列表
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param type         时间范围类型 0、全部，1、本月，2、上月，3、近三月
     */
    void calculationData(HashSet<String> companyIds, String startDate, String endDate, String type);
}
