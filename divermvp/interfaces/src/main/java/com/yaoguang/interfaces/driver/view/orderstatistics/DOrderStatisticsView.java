package com.yaoguang.interfaces.driver.view.orderstatistics;


import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.interfaces.BaseListViewV2;

/**
 * 订单统计
 * Created by zhongjh on 2017/5/4.
 */
public interface DOrderStatisticsView extends BaseListViewV2 {

    void showData(BaseResponse<FreightStatistic> value);

    void updateCompanies(BaseResponse<FreightStatistic> value);
}
