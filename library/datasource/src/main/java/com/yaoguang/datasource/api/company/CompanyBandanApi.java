package com.yaoguang.datasource.api.company;

import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanCondition;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 办单的api
 * Created by zhongjh on 2018/7/30.
 */
public interface CompanyBandanApi {

    String COMPANY_BANDAN = "company/bandan";

    /**
     * 船期查询
     *
     * @param appCompanyBanDanCondition 条件实体
     * @param userId                    用户id
     * @param pageIndex                 页码
     * @return 返回數據源
     */
    @POST(COMPANY_BANDAN + "/list.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppCompanyBanDanWrapper>>> list(@Body AppCompanyBanDanCondition appCompanyBanDanCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 拖车办单操作
     * @param sonoIds 货柜id，逗号分隔
     * @param operateType 操作类型 0:船到 1:办单 2:打单
     * @return 是否操作成功
     */
    @POST(COMPANY_BANDAN + "/truck/update.do?")
    Observable<BaseResponse<String>> truckUpdate(@Query("sonoIds") String sonoIds, @Query("operateType") String operateType);

    /**
     * 货代办单操作
     * @param sonoIds 货柜id，逗号分隔
     * @param operateType 操作类型 0:船到 1:办单 2:打单
     * @return 是否操作成功
     */
    @POST(COMPANY_BANDAN + "/freight/update.do?")
    Observable<BaseResponse<String>> freightUpdate(@Query("sonoIds") String sonoIds, @Query("operateType") String operateType);

}
