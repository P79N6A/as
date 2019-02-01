package com.yaoguang.driver.phone.my.myorder2.clientcondition;


import com.yaoguang.datasource.driver.OrderDataSource;
import com.yaoguang.greendao.entity.driver.DriverEntrustCompany;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.phone.my.myorder2.clientcondition
 * @Description: 委托人条件筛选 控制层
 * @date 2018/04/09
 */
public class ClientConditionPresenter extends BasePresenterListCondition<String, DriverEntrustCompany> implements ClientConditionContract.Presenter {

    private ClientConditionContract.View mView;
    private OrderDataSource mOrderDataSource = new OrderDataSource();

    ClientConditionPresenter(ClientConditionContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<DriverEntrustCompany>>> initDatas(String condition, int pageIndex) {
        return mOrderDataSource.companies();
    }
}
