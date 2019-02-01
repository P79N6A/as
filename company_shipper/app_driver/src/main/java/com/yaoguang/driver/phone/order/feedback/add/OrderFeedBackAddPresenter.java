package com.yaoguang.driver.phone.order.feedback.add;

import android.text.TextUtils;

import com.yaoguang.datasource.driver.OrderFeedBackDataSource;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 故障反馈
 * Created by zhongjh on 2017/5/17.
 */
public class OrderFeedBackAddPresenter implements OrderFeedBackAddContacts.Presenter {

    private OrderFeedBackDataSource mOrderFeedBackDataSource = new OrderFeedBackDataSource();
    private OrderFeedBackAddContacts.View mOrderFeedBackAddView;
    private CompositeDisposable mCompositeDisposable;

    private String mOrderId;
    private String mNodeName;
    private String mNodeId;


    public OrderFeedBackAddPresenter(OrderFeedBackAddContacts.View OrderFeedBackAddView, String orderId, String orderName, String nodeId) {
        this.mOrderFeedBackAddView = OrderFeedBackAddView;
        mCompositeDisposable = new CompositeDisposable();
        this.mOrderId = orderId;
        mNodeName = orderName;
        mNodeId = nodeId;
    }


    @Override
    public void add(DriverOrderNodeDetail driverOrderNodeDetail, String dsc) {
        String checkCommit = checkCommit(driverOrderNodeDetail, dsc);
        if (checkCommit != null) {
            mOrderFeedBackAddView.showToast(checkCommit);
            return;
        }

        mOrderFeedBackDataSource.feedbackAdd(mOrderId, mNodeName, dsc, driverOrderNodeDetail, mNodeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<String>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mOrderFeedBackAddView.showProgressDialog("请稍候");
                    }

                    @Override
                    public void onNext(BaseResponse<String> value) {
                        if (value.getState().equals("200")) {
                            mOrderFeedBackAddView.success(value);
                        } else {
                            mOrderFeedBackAddView.fail(value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mOrderFeedBackAddView.hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        mOrderFeedBackAddView.hideProgressDialog();
                    }
                });
    }

    private String checkCommit(DriverOrderNodeDetail driverOrderNodeDetail, String dsc) {
        if (TextUtils.isEmpty(dsc)) {
            return "请填写故障描述";
        }
        if (TextUtils.isEmpty(driverOrderNodeDetail.getPicture()) && TextUtils.isEmpty(driverOrderNodeDetail.getVideoUrl())) {
            return "图片与视频必须上传一个";
        }
        return null;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

}
