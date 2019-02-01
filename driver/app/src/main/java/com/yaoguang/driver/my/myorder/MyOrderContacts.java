package com.yaoguang.driver.my.myorder;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.base.baseview.BaseListViewV2;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.data.source.OrderRepository;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.greendao.entity.Order;

import java.util.HashSet;

/**
 * 我的订单MVP
 * Created by wly on 2018/1/11 0011.
 */

public interface MyOrderContacts {

    interface View extends BaseListViewV2<Order> {
        void showData(BaseResponse<FreightStatistic> value);

        void updateCompanies(BaseResponse<FreightStatistic> value);
    }

    abstract class Presenter extends BasePresenter<View, OrderRepository> {

        public abstract void calculationData(HashSet<String> companyName, String startDate, String endDate, String type);

        public abstract String getMonthFirst();

        public abstract String getMonthLast();
    }
}
