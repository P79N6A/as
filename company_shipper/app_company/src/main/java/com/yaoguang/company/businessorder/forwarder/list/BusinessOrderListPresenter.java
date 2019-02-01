package com.yaoguang.company.businessorder.forwarder.list;

import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.BaseBusinessOrderListContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.BaseBusinessOrderListPresenter;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppCompanyOrder;
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
public class BusinessOrderListPresenter extends BaseBusinessOrderListPresenter<AppCompanyOrderCondition, AppCompanyOrder> {

    private CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();

    public BusinessOrderListPresenter(BaseBusinessOrderListContact.View view) {
        super(view);
    }

    @Override
    protected Observable<BaseResponse<PageList<AppCompanyOrder>>> initDatas(AppCompanyOrderCondition condition, int pageIndex) {
        return mCompanyOrderDataSource.getAppOrders(condition, pageIndex);
    }


    @Override
    public void addTemplate(AppCompanyOrder item) {
        mCompanyOrderDataSource.addTemplate(item)
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
