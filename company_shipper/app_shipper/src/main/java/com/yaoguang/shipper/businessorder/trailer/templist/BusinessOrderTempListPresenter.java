package com.yaoguang.shipper.businessorder.trailer.templist;

import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.BaseBusinessOrderTempListContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.BaseBusinessOrderTempListPresenter;
import com.yaoguang.datasource.shipper.OwnerTruckOrderDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrderTemplate;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/9/10.
 */
public class BusinessOrderTempListPresenter extends BaseBusinessOrderTempListPresenter<AppOwnerTruckOrderTemplate,AppCompanyOrderCondition> {

    OwnerTruckOrderDataSource mOwnerTruckdOrderDataSource = new OwnerTruckOrderDataSource();

    public BusinessOrderTempListPresenter(BaseBusinessOrderTempListContact.View view) {
        super(view);
    }


    @Override
    protected Observable<BaseResponse<PageList<AppOwnerTruckOrderTemplate>>> initDatas(AppCompanyOrderCondition condition, int pageIndex) {
        return mOwnerTruckdOrderDataSource.getForwardOrderTemplates(condition,pageIndex);
    }

}
