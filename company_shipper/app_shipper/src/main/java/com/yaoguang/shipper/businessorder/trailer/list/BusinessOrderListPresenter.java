package com.yaoguang.shipper.businessorder.trailer.list;

import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list.BaseBusinessOrderTrailerListContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list.BaseBusinessOrderTrailerListPresenter;
import com.yaoguang.datasource.shipper.OwnerTruckOrderDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrder;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/9/4.
 */
public class BusinessOrderListPresenter extends BaseBusinessOrderTrailerListPresenter<AppCompanyOrderCondition, AppOwnerTruckOrder> {

    private OwnerTruckOrderDataSource mOwnerTruckOrderDataSource;

    public BusinessOrderListPresenter(BaseBusinessOrderTrailerListContact.View view) {
        super(view);
        mOwnerTruckOrderDataSource = new OwnerTruckOrderDataSource();
    }

    @Override
    protected Observable<BaseResponse<PageList<AppOwnerTruckOrder>>> initDatas(AppCompanyOrderCondition condition, int pageIndex) {
        return mOwnerTruckOrderDataSource.getAppOrders(condition, pageIndex);
    }


    @Override
    public void addTemplate(AppOwnerTruckOrder item) {
        mOwnerTruckOrderDataSource.addTemplate(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        mView.setStartEnable(false);
                    }

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.showToast(response.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.setStartEnable(true);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mView.setStartEnable(true);
                    }
                });
    }


}
