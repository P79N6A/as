package com.yaoguang.datasource.shipper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yaoguang.datasource.api.shipper.OwnerForwardOrderApi;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrder;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrderTemplate;
import com.yaoguang.lib.base.BaseApplication;
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
public class OwnerForwardOrderDataSource extends DCSBaseDataSource {

    /**
     * 业务下单
     *
     * @param appOwnerForwardOrder 下单模型
     * @return 返回的信息文本
     */
    public Observable<BaseResponse<String>> addCompanyOrder(AppOwnerForwardOrder appOwnerForwardOrder) {
        OwnerForwardOrderApi ownerForwardOrderApi = Api.getInstance().retrofit.create(OwnerForwardOrderApi.class);
        return ownerForwardOrderApi.addCompanyOrder(appOwnerForwardOrder, getUserOwner().getId());
    }

    /**
     * 业务下单明细
     *
     * @param id 业务下单主键id
     */
    public Observable<BaseResponse<AppOwnerForwardOrder>> getAppOrder(String id) {
        OwnerForwardOrderApi ownerForwardOrderApi = Api.getInstance().retrofit.create(OwnerForwardOrderApi.class);
        return ownerForwardOrderApi.getAppOrder(id);
    }

    /**
     * 业务下单查询
     *
     * @param appCompanyOrderCondition 条件实体
     * @param pageIndex                页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppOwnerForwardOrder>>> getAppOrders(AppCompanyOrderCondition appCompanyOrderCondition, int pageIndex) {
        OwnerForwardOrderApi ownerForwardOrderApi = Api.getInstance().retrofit.create(OwnerForwardOrderApi.class);
        return ownerForwardOrderApi.getAppOrders(appCompanyOrderCondition, getUserOwner().getId(), pageIndex);
    }

    /**
     * 业务下单查询 (模版)
     *
     * @param appCompanyOrderCondition 条件实体
     * @param pageIndex                页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppOwnerForwardOrderTemplate>>> getForwardOrderTemplates(AppCompanyOrderCondition appCompanyOrderCondition, int pageIndex) {
        OwnerForwardOrderApi ownerForwardOrderApi = Api.getInstance().retrofit.create(OwnerForwardOrderApi.class);
        Observable<BaseResponse<PageList<AppOwnerForwardOrderTemplate>>> value = ownerForwardOrderApi.getForwardOrderTemplates(appCompanyOrderCondition, getUserOwner().getId(), pageIndex);
        return value.map(appOwnerForwardOrderTemplates -> {
            // 将数据转换集合
            if (appOwnerForwardOrderTemplates.getState().equals("200")) {
                if (appOwnerForwardOrderTemplates.getResult() != null && appOwnerForwardOrderTemplates.getResult().getResult().size() > 0) {
                    Gson gson = new Gson();
                    for (AppOwnerForwardOrderTemplate appOwnerForwardOrderTemplate : appOwnerForwardOrderTemplates.getResult().getResult()) {
                        ArrayList<AppInfoClientPlace> loadClientPlaces = gson.fromJson(appOwnerForwardOrderTemplate.getLoadInfos(), new TypeToken<List<AppInfoClientPlace>>() {
                        }.getType());
                        ArrayList<AppInfoClientPlace> consigneeClientPlaces = gson.fromJson(appOwnerForwardOrderTemplate.getConsigneeInfos(), new TypeToken<List<AppInfoClientPlace>>() {
                        }.getType());
                        appOwnerForwardOrderTemplate.setLoadClientPlaces(loadClientPlaces);
                        appOwnerForwardOrderTemplate.setConsigneeClientPlaces(consigneeClientPlaces);
                    }
                }
            }
            return appOwnerForwardOrderTemplates;
        });

    }

    /**
     * 添加模版
     *
     * @param appOwnerForwardOrder 实体类
     * @return 编辑信息
     */
    public Observable<BaseResponse<String>> addTemplate(AppOwnerForwardOrder appOwnerForwardOrder) {
        AppOwnerForwardOrderTemplate appOwnerForwardOrderTemplate = new AppOwnerForwardOrderTemplate();
        appOwnerForwardOrderTemplate.setClientId(appOwnerForwardOrder.getClientId());
        appOwnerForwardOrderTemplate.setOfficeName(appOwnerForwardOrder.getCompanyName());
        appOwnerForwardOrderTemplate.setGoodsName(appOwnerForwardOrder.getGoodsName());
        appOwnerForwardOrderTemplate.setOwner(appOwnerForwardOrder.getOwner());
        appOwnerForwardOrderTemplate.setDockOfLoading(appOwnerForwardOrder.getDockOfLoading());
        appOwnerForwardOrderTemplate.setFinalDestination(appOwnerForwardOrder.getFinalDestination());
        appOwnerForwardOrderTemplate.setCarriageItem(appOwnerForwardOrder.getCarriageItem());
        appOwnerForwardOrderTemplate.setCarriageWay(appOwnerForwardOrder.getCarriageWay());
        appOwnerForwardOrderTemplate.setPortLoading(appOwnerForwardOrder.getPortLoading());
        appOwnerForwardOrderTemplate.setPortDelivery(appOwnerForwardOrder.getPortDelivery());
        appOwnerForwardOrderTemplate.setIsInsurance(appOwnerForwardOrder.getIsInsurance());
        appOwnerForwardOrderTemplate.setLoadInfos(appOwnerForwardOrder.getLoadingId());
        appOwnerForwardOrderTemplate.setConsigneeInfos(appOwnerForwardOrder.getConsigneeId());
        return editTemplate(appOwnerForwardOrderTemplate);
    }

    /**
     * 编辑模版
     *
     * @param appOwnerForwardOrderTemplate 实体类
     * @return 编辑信息
     */
    public Observable<BaseResponse<String>> editTemplate(AppOwnerForwardOrderTemplate appOwnerForwardOrderTemplate) {
        OwnerForwardOrderApi ownerForwardOrderApi = Api.getInstance().retrofit.create(OwnerForwardOrderApi.class);
        appOwnerForwardOrderTemplate.setUserId(getUserOwner().getId());
        return ownerForwardOrderApi.editTemplate(appOwnerForwardOrderTemplate, getUserOwner().getId());
    }

    /**
     * 删除模板
     * @param id
     * @return
     */
    public Observable<BaseResponse<String>> cancelOrder(String id) {
        OwnerForwardOrderApi ownerForwardOrderApi = Api.getInstance().retrofit.create(OwnerForwardOrderApi.class);
        return ownerForwardOrderApi.cancelOrder(id);
    }
}
