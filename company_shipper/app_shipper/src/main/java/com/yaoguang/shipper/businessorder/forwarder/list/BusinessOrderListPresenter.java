package com.yaoguang.shipper.businessorder.forwarder.list;

import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.BaseBusinessOrderListContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.BaseBusinessOrderListPresenter;
import com.yaoguang.datasource.shipper.OwnerForwardOrderDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrder;
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
public class BusinessOrderListPresenter extends BaseBusinessOrderListPresenter<AppCompanyOrderCondition, AppOwnerForwardOrder> {

    private OwnerForwardOrderDataSource mOwnerForwardOrderDataSource;

    public BusinessOrderListPresenter(BaseBusinessOrderListContact.View view) {
        super(view);
        mOwnerForwardOrderDataSource = new OwnerForwardOrderDataSource();
    }

    @Override
    protected Observable<BaseResponse<PageList<AppOwnerForwardOrder>>> initDatas(AppCompanyOrderCondition condition, int pageIndex) {
        return mOwnerForwardOrderDataSource.getAppOrders(condition, pageIndex);
    }


    @Override
    public void addTemplate(AppOwnerForwardOrder item) {
        mOwnerForwardOrderDataSource.addTemplate(item)
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
