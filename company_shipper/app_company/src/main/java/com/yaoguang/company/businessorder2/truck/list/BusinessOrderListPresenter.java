package com.yaoguang.company.businessorder2.truck.list;

import com.yaoguang.company.businessorder2.common.list.BaseBusinessOrderListContact;
import com.yaoguang.company.businessorder2.common.list.BaseBusinessOrderListPresenter;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * 拖车
 * Created by zhongjh on 2018/11/14.
 */
public class BusinessOrderListPresenter extends BaseBusinessOrderListPresenter<TruckBills> {

    public BusinessOrderListPresenter(BaseBusinessOrderListContact.View view, String mBillType) {
        super(view, mBillType);
    }

    @Override
    protected Observable<BaseResponse<PageList<TruckBills>>> initDatas(SysConditionWrapper condition, int pageIndex) {
        return mCompanyOrderDataSource.truckList(condition, pageIndex);
    }

}
