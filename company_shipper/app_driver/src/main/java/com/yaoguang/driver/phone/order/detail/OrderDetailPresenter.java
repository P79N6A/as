package com.yaoguang.driver.phone.order.detail;

import com.yaoguang.datasource.driver.OrderDataSource;
import com.yaoguang.datasource.driver.OrderNodeDataSource;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.driver.DriverOrderDetailVoSec;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeProgressWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.ACCEPT;

/**
 * 订单明细
 * Created by zhongjh on 2018/5/30.
 */
public class OrderDetailPresenter extends BasePresenter implements OrderDetailContacts.Presenter {

    private OrderDetailContacts.View mView;
    private OrderDataSource mOrderDataSource;
    private OrderNodeDataSource mOrderNodeDataSource;

    OrderDetailPresenter(OrderDetailContacts.View view) {
        mView = view;
        mOrderDataSource = new OrderDataSource();
        mOrderNodeDataSource = new OrderNodeDataSource();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void refreshOrderDetail(String orderId) {
        mOrderDataSource.detailnew(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<DriverOrderDetailVoSec>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<DriverOrderDetailVoSec> response) {
                        if (response.getResult() != null && response.getResult().getOrder() != null) {
                            mView.setData(response.getResult());
                        }
                        mView.hideLoadingView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showErrorView();
                    }
                });
    }

    @Override
    public void handleUpdate(String orderId, int operateType, DriverOrderWrapper order, int position, String remark, String placeId) {
        mOrderDataSource.update(orderId, String.valueOf(operateType), remark, placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable,mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> value) {
                        switch (operateType) {
                            case 4:
                                //出车
                                if (value.getState().equals("200")) {
                                    order.setVehicleFlag("1");
                                    mView.updateSuccess(value.getResult(), operateType);
                                } else {
                                    mView.showToast(value.getMessage());
                                }
                                break;
                            case 1:
                                //接单
                                if (value.getState().equals("200")) {
                                    order.setOrderStatus("1");
                                    mView.updateSuccess(value.getResult(), operateType);
                                } else {
                                    mView.showToast(value.getMessage());
                                }
                                break;
                            case 3:
                                //拒绝
                                if (value.getState().equals("200")) {
                                    order.setOrderStatus("3");
                                    mView.updateSuccess(value.getResult(), operateType);
                                } else {
                                    mView.showToast(value.getMessage());
                                }
                                break;
                        }
                    }
                });
    }

    @Override
    public void outCar(String orderId, DriverOrderWrapper driverOrderWrapper, double lat, double lon, String address) {
        //出车
        Observable<BaseResponse<DriverOrderNodeProgressWrapper>> driverOrderNodeProgressWrapper = mOrderNodeDataSource.finish(null, lat, lon, address, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        driverOrderNodeProgressWrapper.subscribe(new BaseObserver<BaseResponse<DriverOrderNodeProgressWrapper>>(mCompositeDisposable,mView) {
            @Override
            public void onSuccess(BaseResponse<DriverOrderNodeProgressWrapper> response) {
                //出车
                driverOrderWrapper.setVehicleFlag("1");
                mView.updateSuccess("出车成功", -1);
            }
        });
    }

    @Override
    public void acceptOrder(DriverOrderWrapper order, int position) {
        // 获取数据并传到view层
        mOrderDataSource.places(order.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoPutorderPlace>>>(mCompositeDisposable,mView) {


                    @Override
                    public void onSuccess(BaseResponse<List<InfoPutorderPlace>> response) {
                        // 打开放单点fragment
                        if (response.getResult() != null && response.getResult().size() > 0)
                            mView.openPutOrderAddress(order, response.getResult(), position);
                        else
                            // 接受订单
                            handleUpdate(order.getOrderId(), ACCEPT, order, position, "", null);
                    }


                });
    }
}
