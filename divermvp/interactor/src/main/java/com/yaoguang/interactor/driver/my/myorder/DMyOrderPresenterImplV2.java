package com.yaoguang.interactor.driver.my.myorder;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.interactor.driver.BasePresenterImplV2;
import com.yaoguang.interfaces.driver.interactor.orderstatistics.DOrderStatisticsInteractor;
import com.yaoguang.interfaces.driver.presenter.orderstatistics.DMyOrderPresenter;
import com.yaoguang.interfaces.driver.view.orderstatistics.DOrderStatisticsView;

import java.util.HashSet;

import io.reactivex.Observable;

/**
 * 订单统计控制层 实现
 * Created by zhongjh on 2017/5/5.
 */
public class DMyOrderPresenterImplV2 extends BasePresenterImplV2 implements DMyOrderPresenter {

    private DOrderStatisticsInteractor mDOrderStatisticsInteractor;
    private DOrderStatisticsView mDOrderStatisticsView;

    public DMyOrderPresenterImplV2(DOrderStatisticsView dOrderStatisticsView) {
        this.mDOrderStatisticsView = dOrderStatisticsView;
        mDOrderStatisticsInteractor = new DOrderStatisticsInteractorImpl();
    }

    @Override
    public void calculationData(HashSet<String> companyIds, String startDate, String endDate, String type) {
        Observable<BaseResponse<FreightStatistic>> baseResponseObservable = mDOrderStatisticsInteractor.calculationData(companyIds, startDate, endDate, type);
        subscribeList(baseResponseObservable,mDOrderStatisticsView,ShowType.REFRESH,new OnCallback() {
            @Override
            public boolean onNext(BaseResponse listBaseResponse) {
                BaseResponse<FreightStatistic> value = listBaseResponse;

                if (value.getResult() != null && value.getResult().getCompanies() != null && !value.getResult().getCompanies().isEmpty()) {
                    mDOrderStatisticsView.updateCompanies(value);
                }
                if (value == null || value.getResult() == null || value.getResult().getOrderList() == null || value.getResult().getOrderList().size() <= 0) {
                    mDOrderStatisticsView.recyclerViewShowEmpty();
                    return super.onNext(listBaseResponse);
                }

                mDOrderStatisticsView.showData(value);
                return super.onNext(listBaseResponse);
            }
        });
    }

    @Override
    public void subscribe() {
    }

}
