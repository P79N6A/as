package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business;

import android.content.Context;

import com.mingle.entity.MenuEntity;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrder;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2017/5/31.
 */
public abstract class BaseBusinessOrderPresenter<T> implements BaseBusinessOrderContact.Presenter<T> {

    protected BaseBusinessOrderContact.View mCBusinessOrderView;
    protected CompositeDisposable mCompositeDisposable;
    protected Context mContext;

    /**
     * 从列表传递过来的对象(用于进行修改或者查看)
     */
    protected String mID;
    /**
     * 从模版列表传递过来的对象(用于复用模版)
     */
    protected T mModel;

    @Override
    public void subscribe() {
        //解析柜型柜量数据,解析完后，判断id是否存在，存在则读取这个订单的详情
        analysisSonos();
    }

    public BaseBusinessOrderPresenter(BaseBusinessOrderContact.View cBusinessOrderView, Context context, String id, T t) {
        mCBusinessOrderView = cBusinessOrderView;
        mCompositeDisposable = new CompositeDisposable();
        mContext = context;
        mID = id;
        mModel = t;
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }



}
