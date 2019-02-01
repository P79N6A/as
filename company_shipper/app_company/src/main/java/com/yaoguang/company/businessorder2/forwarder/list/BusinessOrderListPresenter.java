package com.yaoguang.company.businessorder2.forwarder.list;

import android.support.annotation.NonNull;

import com.yaoguang.company.businessorder2.common.list.BaseBusinessOrderListContact;
import com.yaoguang.company.businessorder2.common.list.BaseBusinessOrderListPresenter;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.company.AppBusinessOrderListCondition;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 工作单管理 FreightBills
 * Created by zhongjh on 2018/10/24.
 */
public class BusinessOrderListPresenter extends BaseBusinessOrderListPresenter<FreightBills> {

    public BusinessOrderListPresenter(BaseBusinessOrderListContact.View view, String mBillType) {
        super(view, mBillType);
    }

    @Override
    protected Observable<BaseResponse<PageList<FreightBills>>> initDatas(SysConditionWrapper condition, int pageIndex) {
        return mCompanyOrderDataSource.getForwardOrders2(condition, pageIndex);
    }

}


