package com.yaoguang.datasource.company;

import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.AppPriceTruckWrapper;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.greendao.entity.PriceTruckCondition;
import com.yaoguang.datasource.api.company.CompanyApi;

import io.reactivex.Observable;

/**
 * 报价分析
 * Created by zhongjh on 2017/12/13.
 */
public class CompanyPriceDataSource extends DCSBaseDataSource {

    /**
     * 货代报价查询
     *
     * @param priceAnalysisCondition 查询条件
     * @param pageIndex              页数
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppPriceAnalysisWrapper>>> getAnalysis(PriceAnalysisCondition priceAnalysisCondition, int pageIndex) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.getAnalysis(priceAnalysisCondition, getAppUserWrapper().getId(), pageIndex);
    }

    /**
     * 拖车报价查询
     *
     * @param condition 查询条件
     * @param pageIndex              页数
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppPriceTruckWrapper>>> getTruckFee(PriceTruckCondition condition, int pageIndex) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.getTruckFee(condition, getAppUserWrapper().getId(), pageIndex);
    }

}
