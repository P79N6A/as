package com.yaoguang.interactor.driver.contact;

import com.yaoguang.interactor.driver.BasePresenterImplV2;
import com.yaoguang.interactor.driver.contact.view.DContactChildView;
import com.yaoguang.interactor.driver.my.mycontact.DContactChildInteractorImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 韦理英
 * on 2017/6/22 0022.
 */

public class DContactChildPresenterImplV2 extends BasePresenterImplV2 implements DContactChildPresenter {
    private DContactChildInteractor mDContactChildInteractor;
    private String mType;
    private boolean hasNextPage;
    private int pageIndex;
    private DContactChildView dContactChildView;

    public DContactChildPresenterImplV2(DContactChildView dContactChildView, String type) {
        this.dContactChildView = dContactChildView;
        mDContactChildInteractor = new DContactChildInteractorImpl();
        mType = type;
    }

    @Override
    public void refreshData(int mType) {
        pageIndex = 1;
        hasNextPage = true;
        initContactCompanys("", "", mType, 0, false);
    }

    @Override
    public void loadMoreData(int mType, int size) {
        if (hasNextPage) {
            pageIndex++;
            initContactCompanys("", "", mType, size, true);
        }
    }

    @Override
    public void initContactCompanys(String companyName, String companyId, int intType, final int dataSize, final boolean isNext) {
        subscribeList(mDContactChildInteractor.initContactCompanys(companyName, companyId, intType, mType, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()), dContactChildView, dataSize, isNext, pageIndex, null);
    }
}
