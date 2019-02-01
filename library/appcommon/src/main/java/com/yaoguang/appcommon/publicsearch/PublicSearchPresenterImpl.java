package com.yaoguang.appcommon.publicsearch;

import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.PublicSearchData;
import com.yaoguang.interactor.BasePresenterImpl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2017/6/2.
 */
public class PublicSearchPresenterImpl extends BasePresenterImpl<AppPublicInfoWrapper> implements PublicSearchContact.CPublicSearchPresenter {

    private PublicSearchContact.CPublicSearchView mCPublicSearchView;
    private PublicSearchContact.CPublicSearchInteractor mCPublicSearchInteractor;
    //查询类型
    private int mType;
    //根据某个id查询的可以存放在这里,这是个CodeId
    private String mCodeId;
    //区域名称
    private String mAreaName;
    //已经勾选的
    private String mIds;


    public PublicSearchPresenterImpl(PublicSearchContact.CPublicSearchView cPublicSearchView, int type, String codeId, String areaName, String ids) {
        this.mCPublicSearchView = cPublicSearchView;
        mCPublicSearchInteractor = new PublicSearchInteractorImpl();
        mCompositeDisposable = new CompositeDisposable();
        mType = type;
        mCodeId = codeId;
        mAreaName = areaName;
        mIds = ids;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onSearchAction(String query) {
        mCPublicSearchInteractor.search(query, mType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PublicSearchData>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mCPublicSearchView.showProgress();
                    }

                    @Override
                    public void onNext(List<PublicSearchData> value) {
                        mCPublicSearchView.swapSuggestions(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCPublicSearchView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        mCPublicSearchView.hideProgress();
                    }
                });
    }

    @Override
    public void addQuery(String body) {
        if (body == null || body.equals("")) {
            mCPublicSearchView.startRefresh();
            return;
        }
        mCPublicSearchInteractor.addQuery(body, mType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mCPublicSearchView.showProgress();
                    }

                    @Override
                    public void onNext(Long value) {
                        if (value > 0) {
                            mCPublicSearchView.startRefresh();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCPublicSearchView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        mCPublicSearchView.hideProgress();
                    }
                });
    }

    @Override
    public void refreshData(String name) {
        pageIndex = 1;
        hasNextPage = true;
        getData(name, 0, false);
    }

    @Override
    public void getData(String name, final int dataSize, final boolean isNext) {
        Observable observable = mCPublicSearchInteractor.initAppPublicInfoWrappers(name, mAreaName, mType, pageIndex, mCodeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        subscribe(observable, mCompositeDisposable, mCPublicSearchView, dataSize, isNext, pageIndex);
    }

    @Override
    public void loadMoreData(String name, int size) {
        if (hasNextPage) {
            pageIndex++;
            getData(name, size, true);
        }
    }

}
