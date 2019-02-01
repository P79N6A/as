package com.yaoguang.company.businessorder.forwarder.templist;

import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.BaseBusinessOrderTempListContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.BaseBusinessOrderTempListPresenter;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.datasource.shipper.OwnerForwardOrderDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppCompanyOrderTemplate;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/9/7.
 */
public class BusinessOrderTempListPresenter extends BaseBusinessOrderTempListPresenter<AppCompanyOrderTemplate,AppCompanyOrderCondition> {

    private CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();

    public BusinessOrderTempListPresenter(BaseBusinessOrderTempListContact.View view) {
        super(view);
    }


    @Override
    protected Observable<BaseResponse<PageList<AppCompanyOrderTemplate>>> initDatas(AppCompanyOrderCondition condition, int pageIndex) {
        return mCompanyOrderDataSource.getOrderTemplates(condition,pageIndex);
    }

}
