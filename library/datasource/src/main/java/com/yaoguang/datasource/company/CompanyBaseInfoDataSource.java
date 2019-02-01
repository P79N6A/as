package com.yaoguang.datasource.company;


import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.InfoPackType;
import com.yaoguang.greendao.entity.company.InfoClientBookingconsignee;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.greendao.entity.company.InfoPaymentMethod;
import com.yaoguang.greendao.entity.company.InfoSendOrderTemp;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;
import com.yaoguang.datasource.api.company.CompanyBaseInfoApi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * 柜型类
 * Created by zhongjh on 2017/12/13.
 */
public class CompanyBaseInfoDataSource extends DCSBaseDataSource {

    /**
     * 查询柜型柜量，缓存一天：2*40GP,3*40HP
     */
    public Observable<BaseResponse<List<String>>> getContForSearch() {
        //获取柜型
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.getContForSearch();
    }

    /**
     * 查询柜型柜量，缓存一天：40GP,40HP
     */
    public Observable<BaseResponse<List<InfoContType>>> getConts() {
        //获取柜型
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.getConts();
    }

    /**
     * 包装类型
     */
    public Observable<BaseResponse<List<InfoPackType>>> packType() {
        //获取柜型
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.packType();
    }

    /**
     * 船期查询
     *
     * @param infoVoyageTableCondition 条件实体
     * @param pageIndex                页码
     * @return 返回數據源
     */
    public Observable<BaseResponse<PageList<InfoVoyageTable>>> getInfoVoyageTables(InfoVoyageTableCondition infoVoyageTableCondition, int pageIndex) {
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.getInfoVoyageTables(infoVoyageTableCondition, DataStatic.getInstance().getId(), pageIndex);
    }

    /**
     * 业务下单-选择操作、运输条款
     *
     * @return 数据源
     */
    public Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectTraffic() {
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.selectTraffic(1);
    }

    /**
     * 业务下单-收款方式
     */
    public Observable<BaseResponse<List<InfoPaymentMethod>>> searchPaymentMethod() {
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.searchPaymentMethod();
    }

    /**
     * 按托运人及起运地（目的地）获取装卸货地址
     *
     * @param codeId    托运人
     * @param areaName  起运地（目的地）
     * @param name 搜索名称
     * @param pageIndex 页数
     */
    public Observable<BaseResponse<PageList<InfoClientPlace>>> selectInfoPlaces(String codeId, String areaName,String name, int pageIndex) {
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.selectInfoPlaces(codeId, areaName,name, pageIndex);
    }

    /**
     * 批量删除装卸货地址
     *
     * @param ids    逗号拼接
     * @param codeId 托运人id
     * @return 返回的信息文本
     */
    public Observable<BaseResponse<String>> batchDeletePlace(String ids, String codeId) {
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.batchDeletePlace(ids, codeId, DataStatic.getInstance().getId());
    }

    /**
     * 添加/修改 装卸货地址
     *
     * @param infoClientPlace 装卸货地址
     * @return 返回的信息文本
     */
    public Observable<BaseResponse<String>> addLoadPlace(InfoClientPlace infoClientPlace){
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.addLoadPlace(infoClientPlace, DataStatic.getInstance().getId());
    }

    /**
     * 按目的拖车查询订舱收货人
     * @param destTruck 目的拖车
     * @return 返回列表
     */
    public Observable<BaseResponse<List<InfoClientBookingconsignee>>> searchInfoClientBookingconsignees(String destTruck) {
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.searchInfoClientBookingconsignees(destTruck);
    }

    /**
     * 派单模板列表
     * @param type 0：货代 1：装货 2：送货 3：拖车
     */
    public Observable<BaseResponse<List<InfoSendOrderTemp>>> transferTempList(String type) {
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.transferTempList(type);
    }

}
