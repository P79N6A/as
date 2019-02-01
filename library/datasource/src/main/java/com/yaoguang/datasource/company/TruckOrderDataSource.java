package com.yaoguang.datasource.company;

import com.yaoguang.datasource.api.company.CompanyTruckOrderApi;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.greendao.entity.company.AppTruckOrderTemplate;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppTruckOrder;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/9/7.
 */
public class TruckOrderDataSource extends DCSBaseDataSource {

    /**
     * 业务下单
     *
     * @param appTruckOrder 下单模型
     * @return 返回的信息文本
     */
    public Observable<BaseResponse<String>> addCompanyOrder(AppTruckOrder appTruckOrder) {
        CompanyTruckOrderApi companyTruckOrderApi = Api.getInstance().retrofit.create(CompanyTruckOrderApi.class);
        return companyTruckOrderApi.editTruckOrder(appTruckOrder, getAppUserWrapper().getId());
    }

    /**
     * 业务下单明细
     *
     *
     * @param id
     *            业务下单主键id
     */
    public Observable<BaseResponse<AppTruckOrder>> getAppOrder(String id) {
        CompanyTruckOrderApi companyTruckOrderApi = Api.getInstance().retrofit.create(CompanyTruckOrderApi.class);
        return companyTruckOrderApi.getAppOrder(id);
    }

    /**
     * 业务下单查询
     *
     * @param appCompanyOrderCondition 条件实体
     * @param pageIndex                页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppTruckOrder>>> getAppOrders(AppCompanyOrderCondition appCompanyOrderCondition, int pageIndex) {
        CompanyTruckOrderApi companyTruckOrderApi = Api.getInstance().retrofit.create(CompanyTruckOrderApi.class);
        return companyTruckOrderApi.getAppOrders(appCompanyOrderCondition, getUserOwner().getId(), pageIndex);
    }

    /**
     * 业务下单查询 (模版)
     *
     * @param appCompanyOrderCondition 条件实体
     * @param pageIndex                页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppTruckOrderTemplate>>> getOrderTemplates(AppCompanyOrderCondition appCompanyOrderCondition, int pageIndex) {
        CompanyTruckOrderApi companyTruckOrderApi = Api.getInstance().retrofit.create(CompanyTruckOrderApi.class);
        return companyTruckOrderApi.getOrderTemplates(appCompanyOrderCondition, getUserOwner().getId(), pageIndex);
    }

    /**
     * 添加模版
     *
     * @param appTruckOrder 实体类
     * @return 编辑信息
     */
    public Observable<BaseResponse<String>> addTemplate(AppTruckOrder appTruckOrder) {
        AppTruckOrderTemplate appTruckOrderTemplate = new AppTruckOrderTemplate();
        appTruckOrderTemplate.setAddress(appTruckOrder.getAddress());
        appTruckOrderTemplate.setUserId(appTruckOrder.getUserId());
        appTruckOrderTemplate.setShipperId(appTruckOrder.getShipperId());
        appTruckOrderTemplate.setShipper(appTruckOrder.getShipper());
        appTruckOrderTemplate.setGoodsName(appTruckOrder.getGoodsName());
        appTruckOrderTemplate.setOwner(appTruckOrder.getOwner());
        appTruckOrderTemplate.setPort(appTruckOrder.getPort());
        appTruckOrderTemplate.setAddress(appTruckOrder.getAddress());
        appTruckOrderTemplate.setShipCompany(appTruckOrder.getShipCompany());
        appTruckOrderTemplate.setDockInfos(appTruckOrder.getDockInfos());
        appTruckOrderTemplate.setAppInfoClientPlaces(appTruckOrder.getAppInfoClientPlaces());
        appTruckOrderTemplate.setRemark(appTruckOrder.getRemark());
        appTruckOrderTemplate.setCreatedBy(appTruckOrder.getCreatedBy());
        appTruckOrderTemplate.setClientId(appTruckOrder.getClientId());
        appTruckOrderTemplate.setIsValid(appTruckOrder.getIsValid());
//        Gson gson = new Gson();
//        ArrayList<AppInfoClientPlace> loadClientPlaces = gson.fromJson(appTruckOrder.getLoadingId(), new TypeToken<List<AppInfoClientPlace>>() {
//        }.getType());
//        ArrayList<AppInfoClientPlace> consigneeClientPlaces = gson.fromJson(appTruckOrder.getConsigneeId(), new TypeToken<List<AppInfoClientPlace>>() {
//        }.getType());
//        appTruckOrderTemplate.setConsigneeClientPlaces(loadClientPlaces);
//        appTruckOrderTemplate.setLoadClientPlaces(consigneeClientPlaces);
        return editTemplate(appTruckOrderTemplate);
    }

    /**
     * 编辑模版
     *
     * @param appTruckOrderTemplate 实体类
     * @return 编辑信息
     */
    public Observable<BaseResponse<String>> editTemplate(AppTruckOrderTemplate appTruckOrderTemplate) {
        CompanyTruckOrderApi companyTruckOrderApi = Api.getInstance().retrofit.create(CompanyTruckOrderApi.class);
        return companyTruckOrderApi.editTemplate(appTruckOrderTemplate, getUserOwner().getId());
    }


}
