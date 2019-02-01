package com.yaoguang.appcommon.phone.order.feedback;


import com.yaoguang.greendao.entity.driver.DriverOrderNodeFeedback;
import com.yaoguang.interactor.driver.order.DOrderFeedBackListInteractorImpl;
import com.yaoguang.interfaces.driver.interactor.order.DOrderFeedBackListInteractor;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 订单反馈列表
 * Created by zhongjh on 2017/5/17.
 */
public class OrderFeedBackListPresenter extends BasePresenter implements OrderFeedBackListContacts.Presenter {

    private DOrderFeedBackListInteractor mDOrderFeedBackListInteractor;
    private OrderFeedBackListContacts.View mDOrderFeedBackListView;

    public OrderFeedBackListPresenter(OrderFeedBackListContacts.View dOrderFeedBackListView) {
        this.mDOrderFeedBackListView = dOrderFeedBackListView;
        mDOrderFeedBackListInteractor = new DOrderFeedBackListInteractorImpl();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void getList(String orderId, int type) {
        Observable baseResponseObservable;
        if (type == 0) {
            baseResponseObservable = mDOrderFeedBackListInteractor.initList(orderId);
        } else {
            baseResponseObservable = mDOrderFeedBackListInteractor.initOrderNodeDetailList(orderId);
        }
        baseResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(mCompositeDisposable) {


                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (type == 1) {
                            ArrayList<DriverOrderNodeFeedback> arrayList = new ArrayList<>();
                            arrayList.add((DriverOrderNodeFeedback) response.getResult());
                            response.setResult(arrayList);
                        }
                        BaseResponse<PageList<DriverOrderNodeFeedback>> value = mDOrderFeedBackListInteractor.getPageList(response);
                        mDOrderFeedBackListView.show(value.getResult());

                    }
                });
    }

}
