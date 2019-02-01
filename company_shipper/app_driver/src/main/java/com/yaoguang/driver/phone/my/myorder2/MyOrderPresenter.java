package com.yaoguang.driver.phone.my.myorder2;


import com.yaoguang.datasource.driver.OrderDataSource;
import com.yaoguang.datasource.util.OrderAssistant;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.driver.DriverOrderCondition;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapperPage;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.myorder
 * @Description: 我的订单 控制层
 * @date 2018/04/04
 */
public class MyOrderPresenter extends BasePresenterListCondition<DriverOrderCondition, DriverOrderWrapper> implements MyOrderContract.Presenter {

    private MyOrderContract.View mView;
    private OrderDataSource mOrderDataSource = new OrderDataSource();
    private String mTotalFreight;
    private String StartDate;
    private String EndDate;

    MyOrderPresenter(MyOrderContract.View view) {
        mView = view;
        isIgnoreEmptyAndError = true;
    }

    @Override
    public void subscribe() {

    }

    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<DriverOrderWrapper>>> initDatas(DriverOrderCondition condition, int pageIndex) {
        return initDatas2(condition, pageIndex)
                .map(OrderAssistant.getInstance(R.drawable.ic_dc_s02).deliveryRoutmapper)
                .map(OrderAssistant.getInstance(R.drawable.ic_dc_s02).orderCreateTimeMapper);
    }

    /**
     * 重构服务器返回来的数据源
     *
     * @param condition 条件
     * @param pageIndex 页码
     * @return 数据源
     */
    private Observable<BaseResponse<PageList<DriverOrderWrapper>>> initDatas2(DriverOrderCondition condition, int pageIndex) {
        Observable<BaseResponse<DriverOrderWrapperPage<PageList<DriverOrderWrapper>>>> value = mOrderDataSource.countv2(condition, pageIndex);
        return value.map(driverOrderWrapperPageBaseResponse -> {
            BaseResponse<PageList<DriverOrderWrapper>> value1 = new BaseResponse<>();
            value1.setState(driverOrderWrapperPageBaseResponse.getState());
            if (value1.getState().equals("200")) {
                DriverOrderWrapperPage<PageList<DriverOrderWrapper>> pageList = driverOrderWrapperPageBaseResponse.getResult();
                value1.setResult(pageList.getOrderList());
                value1.setMessage(driverOrderWrapperPageBaseResponse.getMessage());
                value1.setTotalCount(driverOrderWrapperPageBaseResponse.getResult().getOrderList().getTotalCount());
                mTotalFreight = pageList.getTotalFreight();
                StartDate = pageList.getStartDate();
                EndDate = pageList.getEndDate();
            }
            return value1;
        });
    }

    @Override
    public void customRefreshing(BaseResponse<PageList<DriverOrderWrapper>> response) {
        super.customRefreshing(response);
        mView.setTotalFreight(response,mTotalFreight,StartDate,EndDate);
    }
}
