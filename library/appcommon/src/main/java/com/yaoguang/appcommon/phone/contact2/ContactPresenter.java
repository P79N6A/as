package com.yaoguang.appcommon.phone.contact2;

import com.yaoguang.datasource.common.ContactDataSource;
import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.contact
 * @Description: 我的关联 控制层
 * @date 2018/04/11
 */
public class ContactPresenter extends BasePresenterListCondition<String, DriverContactCompany> implements ContactContract.Presenter {

    private ContactContract.View mView;
    private ContactDataSource mContactDataSource = new ContactDataSource();

    ContactPresenter(ContactContract.View view) {
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
    protected Observable<BaseResponse<PageList<DriverContactCompany>>> initDatas(String condition, int pageIndex) {
        return mContactDataSource.getPassListVersion2(pageIndex);
    }
}
