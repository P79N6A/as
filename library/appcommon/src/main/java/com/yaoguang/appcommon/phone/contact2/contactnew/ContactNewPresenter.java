package com.yaoguang.appcommon.phone.contact2.contactnew;

import com.yaoguang.datasource.common.ContactDataSource;
import com.yaoguang.greendao.entity.common.DriverFollowCompany;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.contactnew
 * @Description: 新关联 控制层
 * @date 2018/04/19
 */
public class ContactNewPresenter extends BasePresenterListCondition<String, DriverFollowCompany> implements ContactNewContract.Presenter {

    private ContactNewContract.View mView;
    private ContactDataSource mContactDataSource = new ContactDataSource();

    ContactNewPresenter(ContactNewContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<DriverFollowCompany>>> initDatas(String condition, int pageIndex) {
        return mContactDataSource.getApplyInfo("", "", pageIndex);
    }

    @Override
    public void ignoreAduit(String condition){
        mContactDataSource.ignoreAduit(condition)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.refreshDataAnimation();
                    }
                });
    }

    @Override
    public void passAduit(String condition){
        mContactDataSource.passAduit(condition)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.refreshDataAnimation();
                    }
                });
    }

}
