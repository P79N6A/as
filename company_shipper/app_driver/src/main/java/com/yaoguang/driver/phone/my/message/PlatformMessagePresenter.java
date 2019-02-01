package com.yaoguang.driver.phone.my.message;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.common.SysMsgDataSource;
import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Project Name:driver
 * Created by weiliying
 * on 2017 2017/4/19.16:20
 * <p>
 * update zhongjh
 * data 2018-03-19
 */
public class PlatformMessagePresenter extends BasePresenterListCondition<String, SysMsg> implements PlatformMessageContacts.Presenter {

    private PlatformMessageContacts.View mView;
    private SysMsgDataSource mSysMsgDataSource = new SysMsgDataSource();


    PlatformMessagePresenter(PlatformMessageContacts.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<SysMsg>>> initDatas(String condition, int pageIndex) {
        return mSysMsgDataSource.list(pageIndex, 0);
    }

    @Override
    public void deleteMessage(String msgId) {
        mSysMsgDataSource.delete(msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.refreshMessageDelete();
                    }
                });
    }

    @Override
    public void setMessageReadState(String msgId, final int position) {
        mSysMsgDataSource.readBatch(msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.refreshMessageRead(position);
                    }
                });
    }

}
