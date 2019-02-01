package com.yaoguang.company.businessorder2.truck.businessmain.business;

import com.yaoguang.company.businessorder2.common.businessmain.business.BaseBusinessOrderContact;
import com.yaoguang.company.businessorder2.common.businessmain.business.BaseBusinessOrderPresenter;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.greendao.entity.company.RecordUpdate;
import com.yaoguang.greendao.entity.company.UpdateBusinessOrderModel;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/11/15.
 */
public class BusinessOrderPresenter extends BaseBusinessOrderPresenter<TruckBills, RecordUpdate> {

    public BusinessOrderPresenter(BaseBusinessOrderContact.View view) {
        super(view);
    }

    @Override
    public void subscribe() {
        searchPaymentMethod();
        getConts();
    }

    @Override
    public void addOrder(TruckBills truckBills, boolean isCheck) {
        mCompanyOrderDataSource.addTruck(truckBills, isCheck)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<UpdateBusinessOrderModel>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<UpdateBusinessOrderModel> response) {
                        mView.showUpdateFowardOrder(response.getResult());
                    }

                });
    }

    @Override
    public void updateOrder(RecordUpdate recordUpdate, boolean b) {
        mCompanyOrderDataSource.updateTruck(recordUpdate, b)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<UpdateBusinessOrderModel>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<UpdateBusinessOrderModel> response) {
                        mView.showUpdateFowardOrder(response.getResult());
                    }

                });
    }

    @Override
    public void deleteLoadAddress(String id, String billsId) {
        mCompanyOrderDataSource.truckAddressDelete(id,billsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.deleteLoadAddress();
                        mView.deleteLoadAddressData(id,billsId);
                    }

                });
    }

    @Override
    public void deleteUnLoadAddress(String id, String billsId) {
        mCompanyOrderDataSource.truckAddressDelete(id,billsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.deleteUnLoadAddress();
                        mView.deleteUnLoadAddressData(id,billsId);
                    }

                });
    }
}
