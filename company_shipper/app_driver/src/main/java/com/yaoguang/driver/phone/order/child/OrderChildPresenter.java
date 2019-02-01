package com.yaoguang.driver.phone.order.child;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.datasource.driver.OrderDataSource;
import com.yaoguang.datasource.driver.OrderNodeDataSource;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeProgressWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wly
 * on 2017 2017/4/6
 */
public class OrderChildPresenter extends BasePresenterListCondition<Integer, DriverOrderWrapper> implements OrderChildContacts.Presenter {

    // 待确认
    public static final int WAIT = 0;
    // 已接单
    public static final int ACCEPT = 1;
    // 已完成
    public static final int FINISH = 2;
    // 拒绝
    public static final int REFUSE = 3;

    private OrderChildContacts.View mView;
    private OrderNodeDataSource mOrderNodeDataSource;
    private OrderDataSource mOrderDataSource;


    OrderChildPresenter(OrderChildContacts.View view) {
        mView = view;
        mOrderNodeDataSource = new OrderNodeDataSource();
        mOrderDataSource = new OrderDataSource();
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    protected Observable<BaseResponse<PageList<DriverOrderWrapper>>> initDatas(Integer tabType, int pageIndex) {
        return mOrderDataSource.getList(tabType, pageIndex, R.drawable.ic_dc_s02);
    }

    @Override
    public void handleUpdate(String orderId, final int operateType, final DriverOrderWrapper driverOrderWrapper, final int position, String remark, @Nullable String placeId) {
        mOrderDataSource.update(orderId, String.valueOf(operateType), remark, placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<String> value) {
                        switch (operateType) {
                            case 4:
                                //出车
                                if (value.getState().equals("200")) {
                                    driverOrderWrapper.setVehicleFlag("1");
                                    mView.showSuccess(value.getResult(), driverOrderWrapper, position);
                                } else {
                                    mView.showToast(value.getMessage());
                                }
                                break;
                            case ACCEPT:
                                //接单
                                if (value.getState().equals("200")) {
                                    driverOrderWrapper.setOrderStatus("1");
                                    mView.showAcceptSuccess(value.getResult(), driverOrderWrapper, position, WAIT, ACCEPT);
                                } else {
                                    mView.showToast(value.getMessage());
                                }
                                break;
                            case REFUSE:
                                // 拒绝
                                if (value.getState().equals("200")) {
                                    driverOrderWrapper.setOrderStatus("3");
                                    mView.showAcceptSuccess(value.getResult(), driverOrderWrapper, position, WAIT, REFUSE);
                                } else {
                                    mView.showToast(value.getMessage());
                                }
                                break;
                        }
                    }
                });

    }

    @Override
    public void outCar(String orderId, final DriverOrderWrapper driverOrderWrapper, double lat,
                       double lon, String address, final int position) {
        //出车
        Observable<BaseResponse<DriverOrderNodeProgressWrapper>> driverOrderNodeProgressWrapper = mOrderNodeDataSource.finish(null, lat, lon, address, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        driverOrderNodeProgressWrapper.subscribe(new BaseObserver<BaseResponse<DriverOrderNodeProgressWrapper>>(mCompositeDisposable) {
            @Override
            public void onSuccess(BaseResponse<DriverOrderNodeProgressWrapper> response) {
                driverOrderWrapper.setVehicleFlag("1");
                mView.showSuccess("", driverOrderWrapper, position);
            }
        });
    }

    @Override
    public void getPutOrderData(final DriverOrderWrapper driverOrderWrapper,
                                final int position) {
        // 获取数据并传到view层
        mOrderDataSource.places(driverOrderWrapper.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoPutorderPlace>>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<List<InfoPutorderPlace>> response) {
                        // 如果有数据就打开放单点fragment
                        if (response.getResult() != null && response.getResult().size() > 0)
                            mView.openPutOrderAddress(driverOrderWrapper, (ArrayList<InfoPutorderPlace>) response.getResult(), position);
                        else
                            // 否则接受订单
                            handleUpdate(driverOrderWrapper.getOrderId(), ACCEPT, driverOrderWrapper, position, "", null);
                        mView.finishRefreshing();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.recyclerViewShowError(null);
                        mView.finishRefreshing();
                    }

                    @Override
                    public void onFail(BaseResponse<List<InfoPutorderPlace>> response) {
                        super.onFail(response);
                        mView.recyclerViewShowError(null);
                        mView.finishRefreshing();
                    }
                });
    }


}
