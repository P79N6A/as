package com.yaoguang.driver.phone.order.chooseaddress;

import com.yaoguang.datasource.driver.OrderDataSource;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 选择订单地址控制实现层
 * Created by wly on 2017/12/28 0028.
 */
public class PutOrderAddressPresenter extends BasePresenter implements PutOrderAddressContacts.Presenter {

    private OrderDataSource mOrderDataSource = new OrderDataSource();
    private PutOrderAddressContacts.View mView;

    public PutOrderAddressPresenter(PutOrderAddressContacts.View view) {
        mView = view;
    }

    @Override
    public void accept(String orderId, DriverOrderWrapper mDriverOrderWrapper, int mPosition, String remark, String placeId) {
        mOrderDataSource.update(orderId, "1", remark, placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> value) {
                        //接单
                        if (value.getState().equals("200")) {
                            mView.showToast(value.getResult());
                            mView.acceptSuccess();
                        } else {
                            mView.showToast(value.getMessage());
                        }

                    }
                });
    }

    @Override
    public void subscribe() {

    }

}
