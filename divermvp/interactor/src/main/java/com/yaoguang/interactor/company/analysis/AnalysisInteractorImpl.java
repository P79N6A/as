package com.yaoguang.interactor.company.analysis;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyBaseInfoApi;
import com.yaoguang.datasource.api.company.CompanyApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/6/12.
 */
public class AnalysisInteractorImpl extends DCSBaseInteractorImpl implements AnalysisContact.AnalysisInteractor {

    @Override
    public Observable<BaseResponse<PageList<AppPriceAnalysisWrapper>>> initDatas(PriceAnalysisCondition priceAnalysisCondition, int pageIndex) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.getAnalysis(priceAnalysisCondition, getAppUserWrapper().getId(), pageIndex);
    }

    @Override
    public Observable<BaseResponse<List<String>>> analysisSonos() {
        //获取柜型
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.getContForSearch();
    }
}
