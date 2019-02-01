package com.yaoguang.driver.my.myorder;

import com.yaoguang.common.common.DateUtil;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.lib.annotation.apt.InstanceFactory;

import java.util.HashSet;

import io.reactivex.functions.Consumer;

/**
 * 我的订单历史列表控制层 实现
 * Created by wly on 2017/5/5.
 */
@InstanceFactory
public class MyOrderPresenter extends MyOrderContacts.Presenter {

    public MyOrderPresenter() {
    }

    /**
     * 计算订单统计
     *
     * @param companyIds  查询公司id列表
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @param type        时间范围类型 0、全部，1、本月，2、上月，3、近三月
     */
    @Override
    public void calculationData(HashSet<String> companyIds, String startDate, String endDate, String type) {

        mCompositeDisposable.add(mData.calculationData(null, companyIds, startDate, endDate, type).subscribe(new Consumer<BaseResponse<FreightStatistic>>() {
            @Override
            public void accept(BaseResponse<FreightStatistic> value) throws Exception {
                if (value.getResult() != null && value.getResult().getCompanies() != null && !value.getResult().getCompanies().isEmpty()) {
                    mView.updateCompanies(value);
                }
                if (value.getResult() == null || value.getResult().getOrderList() == null || value.getResult().getOrderList().size() <= 0) {
                    mView.recyclerViewShowEmpty();
                    mView.finishRefreshing();
                    return;
                }

                mView.showData(value);
                mView.finishRefreshing();
            }
        }, throwable -> {
            mView.recyclerViewShowError(null);
            mView.finishRefreshing();
        }));
    }

    /**
     * @return 获取月初的第一天
     */

    @Override
    public String getMonthFirst() {
        return DateUtil.getMonthFirst();
    }


    /**
     * @return 获取月底的第一天
     */
    @Override
    public String getMonthLast() {
        return DateUtil.getMonthLast();
    }


    @Override
    public void subscribe() {
    }

}
