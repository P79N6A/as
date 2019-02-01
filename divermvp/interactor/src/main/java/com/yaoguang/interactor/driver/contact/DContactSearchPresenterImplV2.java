package com.yaoguang.interactor.driver.contact;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.Company;
import com.yaoguang.interactor.driver.BasePresenterImplV2;
import com.yaoguang.interactor.driver.my.mycontact.DContactSearchInteractorImpl;
import com.yaoguang.interactor.driver.contact.view.DContactSearchView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 企业挂靠查询的逻辑分发层
 * Created by zhongjh
 * on 2017 2017/4/6
 */
public class DContactSearchPresenterImplV2 extends BasePresenterImplV2 implements DContactSearchPresenter {

    private DContactSearchView mDContactSearchView;
    private DContactSearchInteractor mDContactSearchInteractor;
    private String mType;

    private int pageIndex;//下一页
    private boolean hasNextPage = true;//是否下一页

    public DContactSearchPresenterImplV2(DContactSearchView dContactSearchView, String type) {
        this.mDContactSearchView = dContactSearchView;
        mDContactSearchInteractor = new DContactSearchInteractorImpl();
        mType = type;
    }

    @Override
    public void refreshData(String strCompanyName) {
        pageIndex = 1;
        hasNextPage = true;
        getData(strCompanyName, 0, false);
    }

    @Override
    public void loadMoreData(String strCompanyName, int dataSize) {
        pageIndex++;
        getData(strCompanyName, dataSize, true);
    }

    @Override
    public void getData(String strCompanyName, final int dataSize, final boolean isNext) {
//        Observable<BaseResponse<PageList<Company>>> baseResponseObservable = mDContactSearchInteractor.initContactCompanys(strCompanyName, pageIndex, mType)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//        subscribeList(baseResponseObservable, mDContactSearchView, dataSize, isNext, pageIndex, null);
    }


    @Override
    public void subscribe() {
        refreshData("");
    }
}
