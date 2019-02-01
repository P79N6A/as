package com.yaoguang.driver.my.message;

import com.yaoguang.driver.base.presetner.BasePresenterImplV2;
import com.yaoguang.lib.annotation.apt.InstanceFactory;

/**
 * Project Name:driver
 * Created by weiliying
 * on 2017 2017/4/19.16:20
 */

@InstanceFactory
public class PlatformMessagePresenter extends PlatformMessageContacts.Presenter {

    protected int pageIndex = 0;
    private boolean hasNextPage = true;

    @Override
    public void loadMessage(int pageIndex, String noticeType, int dataSize, boolean isNextPage) {
        BasePresenterImplV2 baseFragmentListV2 = new BasePresenterImplV2();
        baseFragmentListV2.subscribeList(mData.getPlatformMessageData(null, pageIndex), mView, dataSize, isNextPage, pageIndex, null);
    }

    @Override
    public void homePage(String noticeType) {
        pageIndex = 1;
        hasNextPage = true;
        mView.setPreviousTotal(0);
        loadMessage(pageIndex, noticeType, 0, false);
    }

    @Override
    public void nextPage(String noticeType, int dataSize) {
        pageIndex++;
        if (hasNextPage) {
            loadMessage(pageIndex, noticeType, dataSize, true);
        }
    }


    @Override
    public void deleteMessage(String msgId) {
        mCompositeDisposable.add(mData.deletePlatformMessage(null, msgId).subscribe(aBoolean -> {
            if (aBoolean == null) mView.refreshMessageDelete();
        }, Throwable::printStackTrace));
    }

    @Override
    public void setMessageReadState(String msgId, final int position) {
        mCompositeDisposable.add(mData.platformReadState(null, msgId).subscribe(aBoolean -> {
            if (aBoolean) mView.refreshMessageRead(position);
        }, Throwable::printStackTrace));
    }

    @Override
    public void subscribe() {

    }
}
