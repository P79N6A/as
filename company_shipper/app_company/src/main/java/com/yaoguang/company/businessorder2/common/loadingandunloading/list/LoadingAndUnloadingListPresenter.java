package com.yaoguang.company.businessorder2.common.loadingandunloading.list;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.greendao.entity.company.LoadingAndUnloadingCondition;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/10/24.
 */
public class LoadingAndUnloadingListPresenter extends BasePresenterListCondition<LoadingAndUnloadingCondition, InfoClientPlace> implements LoadingAndUnloadingListContact.Presenter {

    private LoadingAndUnloadingListContact.View mView;
    private CompanyBaseInfoDataSource mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();

    public LoadingAndUnloadingListPresenter(LoadingAndUnloadingListContact.View view) {
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
    protected Observable<BaseResponse<PageList<InfoClientPlace>>> initDatas(LoadingAndUnloadingCondition condition, int pageIndex) {
        return mCompanyBaseInfoDataSource.selectInfoPlaces(condition.getCodeId(), condition.getAreaName(), condition.getName(), pageIndex);
    }

    @Override
    public void batchDeletePlace(String id, String codeId) {
        mCompanyBaseInfoDataSource.batchDeletePlace(id, codeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> value) {
                        mView.showToast(value.getResult());
                        mView.refreshDataAnimation();
                    }
                });
    }


}
