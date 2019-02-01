package com.yaoguang.shipper.businessorder.forwarder.templist;

import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.BaseBusinessOrderTempListContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.BaseBusinessOrderTempListPresenter;
import com.yaoguang.datasource.shipper.OwnerForwardOrderDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrderTemplate;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/9/7.
 */
public class BusinessOrderTempListPresenter extends BaseBusinessOrderTempListPresenter<AppOwnerForwardOrderTemplate,AppCompanyOrderCondition> {

    OwnerForwardOrderDataSource mOwnerForwardOrderDataSource = new OwnerForwardOrderDataSource();

    public BusinessOrderTempListPresenter(BaseBusinessOrderTempListContact.View view) {
        super(view);
    }


    @Override
    protected Observable<BaseResponse<PageList<AppOwnerForwardOrderTemplate>>> initDatas(AppCompanyOrderCondition condition, int pageIndex) {
        return mOwnerForwardOrderDataSource.getForwardOrderTemplates(condition,pageIndex);
    }

}
