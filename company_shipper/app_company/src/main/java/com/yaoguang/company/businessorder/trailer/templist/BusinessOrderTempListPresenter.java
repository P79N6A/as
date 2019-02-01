package com.yaoguang.company.businessorder.trailer.templist;

import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.BaseBusinessOrderTempListContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.BaseBusinessOrderTempListPresenter;
import com.yaoguang.datasource.company.TruckOrderDataSource;
import com.yaoguang.datasource.shipper.OwnerTruckOrderDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppTruckOrderTemplate;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/9/10.
 */
public class BusinessOrderTempListPresenter extends BaseBusinessOrderTempListPresenter<AppTruckOrderTemplate,AppCompanyOrderCondition> {

    private TruckOrderDataSource mTruckOrderDataSource = new TruckOrderDataSource();

    public BusinessOrderTempListPresenter(BaseBusinessOrderTempListContact.View view) {
        super(view);
    }

    @Override
    protected Observable<BaseResponse<PageList<AppTruckOrderTemplate>>> initDatas(AppCompanyOrderCondition condition, int pageIndex) {
        return mTruckOrderDataSource.getOrderTemplates(condition,pageIndex);
    }

}
