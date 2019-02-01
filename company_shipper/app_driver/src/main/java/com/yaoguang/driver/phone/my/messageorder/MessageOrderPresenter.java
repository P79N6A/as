package com.yaoguang.driver.phone.my.messageorder;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yaoguang.datasource.driver.OrderMsgDataSource;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.HashSet;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/5/14.
 */
public class MessageOrderPresenter extends BasePresenterListCondition<Integer, DriverOrderMsgWrapper> implements MessageOrderContact.Presenter {

    private MessageOrderContact.View mView;
    private OrderMsgDataSource mOrderMsgDataSource;

    MessageOrderPresenter(MessageOrderContact.View view, Context context) {
        mView = view;
        mOrderMsgDataSource = new OrderMsgDataSource();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void submitDeleteMessages(HashSet<String> selectIds) {
        if (selectIds != null && selectIds.isEmpty()) {
            mView.showToast("没有可删除的消息");
            return;
        }

        mOrderMsgDataSource.orderMessageDeleted(ObjectUtils.subString(selectIds))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.deleteMessageSuccess("删除成功");
                    }
                });
    }

    @Override
    public void readBatch(String ids, int position) {
        mOrderMsgDataSource.readBatch(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.setReadSuccess(position);
                    }
                });
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<DriverOrderMsgWrapper>>> initDatas(Integer condition, int pageIndex) {
        return mOrderMsgDataSource.getMessageList(pageIndex, false, R.drawable.ic_dc_s02);
    }
}
