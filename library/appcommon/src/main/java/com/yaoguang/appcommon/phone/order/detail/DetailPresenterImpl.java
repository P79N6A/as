package com.yaoguang.appcommon.phone.order.detail;

import com.yaoguang.datasource.common.OrderDataSource;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.datasource.shipper.OwnerOrderDataSource;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interactor.driver.BasePresenterImplV2;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 订单明细控制层
 * Created by zhongjh on 2017/6/21.
 */
public class DetailPresenterImpl<T> extends BasePresenterImplV2 implements DetailContact.DetailPresenter {

    protected DetailContact.DetailView<T> mView;
    protected CompositeDisposable mCompositeDisposable;
    protected OrderDataSource mOrderDataSource;

    /**
     * 订单id
     */
    String billId;

    /**
     * 订单类型0-货代，1-拖车
     */
    int billType;

    /**
     * 公司id
     */
    String clientId;

    public DetailPresenterImpl(DetailContact.DetailView<T> view, int billType, String billId,String clientId) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        this.billType = billType;
        this.billId = billId;
        this.clientId = clientId;
        // 判断是物流还是货主
        switch (BaseApplication.getAppType()){
            case Constants.APP_DRIVER:
                break;
            case Constants.APP_COMPANY:
                mOrderDataSource = new CompanyOrderDataSource();
                break;
            case Constants.APP_SHIPPER:
                mOrderDataSource = new OwnerOrderDataSource();
                break;
        }
    }


    @Override
    public void subscribe() {
        loadData();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadData() {
        Observable<BaseResponse<T>> observable;
        // 判断是货代还是拖车
        if (billType == 0) {
            observable = mOrderDataSource.getForwardOrderById(billId,clientId);
        } else {
            observable = mOrderDataSource.getTruckOrderById(billId,clientId);
        }
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<T>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<T> response) {
                        mView.initDataView(response.getResult());
                    }

                });
    }

}
