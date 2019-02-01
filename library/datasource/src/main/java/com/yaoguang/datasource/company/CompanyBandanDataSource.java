package com.yaoguang.datasource.company;

import com.yaoguang.datasource.api.company.CompanyBandanApi;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanCondition;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanWrapper;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * 企业办单
 * Created by zhongjh on 2018/7/30.
 */
public class CompanyBandanDataSource {

    /**
     * 获取企业列表
     *
     * @param appCompanyBanDanCondition      搜索条件
     *
     * @param pageIndex 页码
     * @return 列表
     */
    public Observable<BaseResponse<PageList<AppCompanyBanDanWrapper>>> getList(AppCompanyBanDanCondition appCompanyBanDanCondition, int pageIndex) {
        // 获取公司
        CompanyBandanApi companyApi = Api.getInstance().retrofit.create(CompanyBandanApi.class);
        return companyApi.list(appCompanyBanDanCondition, DataStatic.getInstance().getId(), pageIndex);
    }

    /**
     * 拖车办单操作
     * @param sonoIds 货柜id，逗号分隔
     * @param operateType 操作类型 0:船到 1:办单 2:打单
     * @return 是否操作成功
     */
    public Observable<BaseResponse<String>> truckUpdate(String sonoIds,String operateType){
        // 获取公司
        CompanyBandanApi companyApi = Api.getInstance().retrofit.create(CompanyBandanApi.class);
        return companyApi.truckUpdate(sonoIds, operateType);
    }

    /**
     * 货代办单操作
     * @param sonoIds 货柜id，逗号分隔
     * @param operateType 操作类型 0:船到 1:办单 2:打单
     * @return 是否操作成功
     */
    public Observable<BaseResponse<String>> freightUpdate(String sonoIds,String operateType){
        // 获取公司
        CompanyBandanApi companyApi = Api.getInstance().retrofit.create(CompanyBandanApi.class);
        return companyApi.freightUpdate(sonoIds, operateType);
    }

}
