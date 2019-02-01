package com.yaoguang.appcommon.search;

import android.support.annotation.NonNull;

import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.interactor.BasePresenterImpl;
import com.yaoguang.appcommon.publicsearch.PublicSearchContact;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by zhongjh on 2017/8/24.
 */
public class SearchPresenter extends BasePresenterListCondition<String, AppPublicInfoWrapper> implements SearchContact.SearchPresenter {

    private SearchContact.SearchView mView;
    private PublicSearchContact.CPublicSearchInteractor mCPublicSearchInteractor;
    //查询类型
    private int mType;
    // 关联公司Id,托运人Id，都可以传给这个codeId
    private String mCodeId;

    private PublishSubject<String> mPublishSubject;
    private CompositeDisposable mCompositeDisposable;

    public SearchPresenter(SearchContact.SearchView searchView, int type, String codeId) {
        this.mView = searchView;
        mCPublicSearchInteractor = new PublicSearchInteractorImpl();
        mType = type;
        mCodeId = codeId;
    }

    @Override
    public void subscribe() {
        // 创建publishSubject
        mPublishSubject = PublishSubject.create();
        // 使用debounce操作符，当输入框发生变化时，不会立刻将事件发送给下游，而是等待500ms，如果在这段事件内，输入框没有发生变化，那么才发送该事件；反之，则在收到新的关键词后，继续等待200ms。
        // 原理类似于我们在收到请求之后，发送一个延时消息给下游，如果在这段延时时间内没有收到新的请求，那么下游就会收到该消息；而如果在这段延时时间内收到来新的请求，那么就会取消之前的消息，并重新发送一个新的延时消息，以此类推。
        mPublishSubject.debounce(500, TimeUnit.MILLISECONDS)
//                // 使用filter操作符，只有关键词的长度大于0时才发送事件给下游。
//                .filter(s -> s.length() > 0)
                // 使用switchMap操作符，这样当发起了abc的请求之后，即使ab的结果返回了，也不会发送给下游，从而避免了出现前面介绍的搜索词和联想结果不匹配的问题。
                .switchMap(query -> getSearchObservable(query))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDisposableObserver);
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(mCompositeDisposable);
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    public Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> initDatas(String condition, int pageIndex) {
        return mCPublicSearchInteractor.initAppPublicInfoWrappers(condition, null,  mType, pageIndex, mCodeId);
    }

    @Override
    public Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getSearchObservable(String condition) {
        return mCPublicSearchInteractor.initAppPublicInfoWrappers(condition, null,  mType, pageIndex, mCodeId);
    }

    @Override
    public void startSearch(String query) {
        mPublishSubject.onNext(query);
    }

}
