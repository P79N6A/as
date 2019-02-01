package com.yaoguang.appcommon.phone.contact2.contactSearch;


import com.yaoguang.datasource.company.CompanyDataSource;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.contactsearchfragment
 * @Description: 关注查找 控制层
 * @date 2018/04/12
 */
public class ContactSearchPresenter extends BasePresenterListCondition<String, UserOfficeWrapper> implements ContactSearchContract.Presenter {

    private ContactSearchContract.View mView;
    private CompanyDataSource mCompanyDataSource = new CompanyDataSource();

    @Override
    public void subscribe() {

    }

    ContactSearchPresenter(ContactSearchContract.View view) {
        mView = view;
    }

    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    public Observable<BaseResponse<PageList<UserOfficeWrapper>>> initDatas(String condition, int pageIndex) {
        return mCompanyDataSource.getList(condition,pageIndex);
    }

    @Override
    public void customRefreshing(BaseResponse<PageList<UserOfficeWrapper>> response) {
        super.customRefreshing(response);
        mView.isOneStartFragment(response.getResult());
    }
}
