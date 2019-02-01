package com.yaoguang.appcommon.phone.home.message.messagechild;

import com.yaoguang.datasource.common.SysMsgDataSource;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.interactor.BasePresenterImpl;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 消息控制层
 * Created by zhongjh on 2017/7/18.
 */
public class MessagePresenter extends BasePresenterImpl<SysMsg> implements MessageChildContact.MessagePresenter {

    private MessageChildContact.MessageView mView;
    private int mType;
    private String mCodeType;
    private SysMsgDataSource mSysMsgDataSource = new SysMsgDataSource();

    public MessagePresenter(MessageChildContact.MessageView view, int type, String codeType) {
        this.mView = view;
        mCompositeDisposable = new CompositeDisposable();
        //ui上的0代表平台公告，1代表企业信息。但是服务器的是调转过来的，所以这边要调换
        switch (type) {
            case 0:
                mType = 1;
                break;
            case 1:
                mType = 0;
                break;
        }

        mCodeType = codeType;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void refreshData() {
        pageIndex = 1;
        hasNextPage = true;
        getData(0, false);
    }

    @Override
    public void loadMoreData(int dataSize) {
        if (hasNextPage) {
            pageIndex++;
            getData(dataSize, true);
        }
    }

    @Override
    public void getData(final int dataSize, final boolean isNext) {
        Observable observable = mSysMsgDataSource.list(pageIndex, mType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        subscribe(observable, mCompositeDisposable, mView, dataSize, isNext, pageIndex);
    }

    @Override
    public void deleteBatch(String codeType, String sysMsgIds) {
        mSysMsgDataSource.delete(sysMsgIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        // 刷新数据
                        mView.refreshData();
                    }

                });
    }


    @Override
    public void updateBath(String codeType, String sysMsgIds, final int position) {
        mSysMsgDataSource.readBatch(sysMsgIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.alreadyRead(position);
                    }

                });
    }


}
