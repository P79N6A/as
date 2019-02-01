package com.yaoguang.appcommon.phone.order;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.shipper.OwnerBaseInfoDataSource;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.datasource.shipper.OwnerOrderDataSource;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.lib.net.bean.PageList;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhongjh
 * @Package com.yaoguang.company.order
 * @Description: 订单跟踪 控制层
 * @date 2018/01/05
 */
public abstract class OrderPresenter<T> extends BasePresenterListCondition<SysConditionWrapper, T> implements OrderContract.Presenter {

    private OrderContract.View mView;
    protected CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();
    protected OwnerOrderDataSource mOwnerOrderDataSource = new OwnerOrderDataSource();
    private OwnerBaseInfoDataSource mOwnerBaseInfoDataSource = new OwnerBaseInfoDataSource();
    int billType; // 订单类型0-货代，1-拖车


    protected OrderPresenter(OrderContract.View view, int billType) {
        mView = view;
        this.billType = billType;
    }

    @Override
    public void subscribe() {
        loadOrderConditionDate();
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }


    @Override
    public void loadOrderConditionDate() {
        //加载所有日期查询条件
        mCompanyOrderDataSource.loadOrderCondition(billType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<SysCondition>>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<List<SysCondition>> response) {
                        mView.setSysConditionDates(response.getResult());
                    }

                });
    }

    @Override
    public void analysisContactCompany() {
        mOwnerBaseInfoDataSource.getContactCompany("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PageList<AppPublicInfoWrapper>>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<PageList<AppPublicInfoWrapper>> response) {
                        if (response.getResult() != null && response.getResult().getResult() != null && response.getResult().getResult().size() > 0) {
                            mView.setContactCompany(response.getResult().getResult().get(0));
                        }
                    }

                });
    }

}
