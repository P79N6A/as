package com.yaoguang.interactor.company.pricetruck;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppPriceTruckWrapper;
import com.yaoguang.greendao.entity.PriceTruckCondition;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/6/12.
 */
public class PriceTruckInteractorImpl extends DCSBaseInteractorImpl implements PriceTruckContact.PriceTruckInteractor {

    @Override
    public Observable<BaseResponse<PageList<AppPriceTruckWrapper>>> initDatas(PriceTruckCondition condition, int pageIndex) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.getTruckFee(condition, getAppUserWrapper().getId(), pageIndex);
    }
}
