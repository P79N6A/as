package com.yaoguang.datasource.shipper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yaoguang.datasource.api.shipper.OwnerTruckOrderApi;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrder;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrderTemplate;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 货代业务订单
 * Created by zhongjh on 2018/9/5.
 */
public class OwnerTruckOrderDataSource extends DCSBaseDataSource {

    /**
     * 业务下单
     *
     * @param appOwnerTruckOrder 下单模型
     * @return 返回的信息文本
     */
    public Observable<BaseResponse<String>> addCompanyOrder(AppOwnerTruckOrder appOwnerTruckOrder) {
        OwnerTruckOrderApi ownerTruckOrderApi = Api.getInstance().retrofit.create(OwnerTruckOrderApi.class);
        return ownerTruckOrderApi.editTruckOrder(appOwnerTruckOrder, getUserOwner().getId());
    }

    /**
     * 业务下单明细
     *
     *
     * @param id
     *            业务下单主键id
     */
    public Observable<BaseResponse<AppOwnerTruckOrder>> getAppOrder(String id) {
        OwnerTruckOrderApi ownerTruckOrderApi = Api.getInstance().retrofit.create(OwnerTruckOrderApi.class);
        return ownerTruckOrderApi.getAppOrder(id);
    }

    /**
     * 业务下单查询
     *
     * @param appCompanyOrderCondition 条件实体
     * @param pageIndex                页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppOwnerTruckOrder>>> getAppOrders(AppCompanyOrderCondition appCompanyOrderCondition, int pageIndex) {
        OwnerTruckOrderApi ownerTruckOrderApi = Api.getInstance().retrofit.create(OwnerTruckOrderApi.class);
        return ownerTruckOrderApi.getAppOrders(appCompanyOrderCondition, getUserOwner().getId(), pageIndex);
    }

    /**
     * 业务下单查询 (模版)
     *
     * @param appCompanyOrderCondition 条件实体
     * @param pageIndex                页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppOwnerTruckOrderTemplate>>> getForwardOrderTemplates(AppCompanyOrderCondition appCompanyOrderCondition, int pageIndex) {
        OwnerTruckOrderApi ownerTruckOrderApi = Api.getInstance().retrofit.create(OwnerTruckOrderApi.class);

        Observable<BaseResponse<PageList<AppOwnerTruckOrderTemplate>>> value = ownerTruckOrderApi.getOrderTemplates(appCompanyOrderCondition, getUserOwner().getId(), pageIndex);
        return value.map(appOwnerTruckOrderTemplates -> {
            // 将数据转换集合
            if (appOwnerTruckOrderTemplates.getState().equals("200")) {
                if (appOwnerTruckOrderTemplates.getResult() != null && appOwnerTruckOrderTemplates.getResult().getResult().size() > 0) {
                    Gson gson = new Gson();
                    for (AppOwnerTruckOrderTemplate appOwnerForwardOrderTemplate : appOwnerTruckOrderTemplates.getResult().getResult()) {
                        ArrayList<AppInfoClientPlace> dockInfoss = gson.fromJson(appOwnerForwardOrderTemplate.getDockInfos(), new TypeToken<List<AppInfoClientPlace>>() {
                        }.getType());
                        appOwnerForwardOrderTemplate.setAppInfoClientPlaces(dockInfoss);
                    }
                }
            }
            return appOwnerTruckOrderTemplates;
        });
    }

    /**
     * 添加模版
     *
     * @param appOwnerTruckOrder 实体类
     * @return 编辑信息
     */
    public Observable<BaseResponse<String>> addTemplate(AppOwnerTruckOrder appOwnerTruckOrder) {

        AppOwnerTruckOrderTemplate appOwnerTruckOrderTemplate = new AppOwnerTruckOrderTemplate();
        appOwnerTruckOrderTemplate.setClientId(appOwnerTruckOrder.getClientId());
        appOwnerTruckOrderTemplate.setOfficeName(appOwnerTruckOrder.getCompanyName());
        appOwnerTruckOrderTemplate.setAddress(appOwnerTruckOrder.getAddress());
        appOwnerTruckOrderTemplate.setUserId(appOwnerTruckOrder.getUserId());
//        appOwnerTruckOrderTemplate.setShipperId(appOwnerTruckOrder.getShipperId());
//        appOwnerTruckOrderTemplate.setShipper(appOwnerTruckOrder.getShipper());
        appOwnerTruckOrderTemplate.setGoodsName(appOwnerTruckOrder.getGoodsName());
        appOwnerTruckOrderTemplate.setOwner(appOwnerTruckOrder.getOwner());
        appOwnerTruckOrderTemplate.setPort(appOwnerTruckOrder.getPort());
        appOwnerTruckOrderTemplate.setAddress(appOwnerTruckOrder.getAddress());
        appOwnerTruckOrderTemplate.setShipCompany(appOwnerTruckOrder.getShipCompany());
        appOwnerTruckOrderTemplate.setDockInfos(appOwnerTruckOrder.getDockInfos());
//        appOwnerTruckOrderTemplate.setAppInfoClientPlaces(appOwnerTruckOrder.getAppInfoClientPlaces());
//        appOwnerTruckOrderTemplate.setRemark(appOwnerTruckOrder.getRemark());
//        appOwnerTruckOrderTemplate.setCreatedBy(appOwnerTruckOrder.getCreatedBy());
        appOwnerTruckOrderTemplate.setClientId(appOwnerTruckOrder.getClientId());
        appOwnerTruckOrderTemplate.setIsValid(appOwnerTruckOrder.getIsValid());

//        Gson gson = new Gson();
//        ArrayList<AppInfoClientPlace> loadClientPlaces = gson.fromJson(appOwnerTruckOrder.getLoadingId(), new TypeToken<List<AppInfoClientPlace>>() {
//        }.getType());
//        ArrayList<AppInfoClientPlace> consigneeClientPlaces = gson.fromJson(appOwnerTruckOrder.getConsigneeId(), new TypeToken<List<AppInfoClientPlace>>() {
//        }.getType());
//        appOwnerTruckOrderTemplate.setConsigneeClientPlaces(loadClientPlaces);
//        appOwnerTruckOrderTemplate.setLoadClientPlaces(consigneeClientPlaces);
//        appOwnerTruckOrderTemplate.setAppInfoClientPlaces(appOwnerTruckOrder.get);

        return editTemplate(appOwnerTruckOrderTemplate);
    }

    /**
     * 编辑模版
     *
     * @param appOwnerTruckOrderTemplate 实体类
     * @return 编辑信息
     */
    public Observable<BaseResponse<String>> editTemplate(AppOwnerTruckOrderTemplate appOwnerTruckOrderTemplate) {
        OwnerTruckOrderApi ownerTruckOrderApi = Api.getInstance().retrofit.create(OwnerTruckOrderApi.class);
        return ownerTruckOrderApi.editTemplate(appOwnerTruckOrderTemplate, getUserOwner().getId());
    }

    /**
     * 删除模板
     * @param id
     * @return
     */
    public Observable<BaseResponse<String>> cancelOrder(String id) {
        OwnerTruckOrderApi ownerTruckOrderApi = Api.getInstance().retrofit.create(OwnerTruckOrderApi.class);
        return ownerTruckOrderApi.cancelOrder(id);
    }

}
