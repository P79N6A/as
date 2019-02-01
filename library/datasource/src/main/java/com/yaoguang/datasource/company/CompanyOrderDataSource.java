package com.yaoguang.datasource.company;

import com.yaoguang.datasource.common.OrderDataSource;
import com.yaoguang.greendao.entity.common.ViewForwardOrder;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.AccountFee;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.OldNewAccountFeeWrapper;
import com.yaoguang.greendao.entity.company.AppBusinessOrderListCondition;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppCompanyOrder;
import com.yaoguang.greendao.entity.company.AppCompanyOrderTemplate;
import com.yaoguang.greendao.entity.company.AppViewForwardOrderWrapper;
import com.yaoguang.greendao.entity.company.RecordUpdate;
import com.yaoguang.greendao.entity.company.UpdateBusinessOrderModel;
import com.yaoguang.greendao.entity.company.WebOrderTemplateWrapper;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.datasource.api.company.CompanyOrderApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * 订单
 * Created by zhongjh on 2018/1/8.
 */
public class CompanyOrderDataSource extends DCSBaseDataSource implements OrderDataSource<ViewForwardOrder, TruckBills> {

    /**
     * 货代工作单查询
     *
     * @param condition 条件实体
     * @param pageIndex 页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<FreightBills>>> getForwardOrders(SysConditionWrapper condition, int pageIndex) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.getForwardOrders(condition, getAppUserWrapper().getId(), pageIndex);
    }

    /**
     * 货代工作单查询
     *
     * @param condition 条件实体
     * @param pageIndex 页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<FreightBills>>> getForwardOrders2(SysConditionWrapper condition, int pageIndex) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.getForwardOrders2(condition, getAppUserWrapper().getId(), pageIndex);
    }

    /**
     * 拖车工作单查询
     *
     * @param condition 条件实体
     * @param pageIndex 页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<TruckBills>>> getTruckOrders(SysConditionWrapper condition, int pageIndex) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.getTruckOrders(condition, getAppUserWrapper().getId(), pageIndex);
    }

    /**
     * 拖车工作单查询（版本2）
     *
     * @param condition 条件实体
     * @param pageIndex 页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<TruckBills>>> truckList(SysConditionWrapper condition, int pageIndex) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.truckList(condition.getsysConditions(), getAppUserWrapper().getId(), pageIndex);
    }

    /**
     * 货代工作单明细
     *
     * @param billId 工作单主键id
     * @return 返回货代工作单明细
     */
    public Observable<BaseResponse<ViewForwardOrder>> getForwardOrderById(String billId, String clientId) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.getForwardOrderById(getAppUserWrapper().getId(), billId);
    }

    /**
     * 拖车工作单明细
     *
     * @param billId 工作单主键id
     * @return 返回拖车工作单明细
     */
    public Observable<BaseResponse<TruckBills>> getTruckOrderById(String billId, String clientId) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.getTruckOrderById(getAppUserWrapper().getId(), billId);
    }

    /**
     * 拖车工作单明细
     *
     * @param id 工作单主键id
     * @return 返回拖车工作单明细
     */
    public Observable<BaseResponse<TruckBills>> truckDetail(String id) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.truckDetail(getAppUserWrapper().getId(), id);
    }

    /**
     * 加载日期查询条件
     *
     * @param billType 0-货代，1-拖车
     * @return 日期集合
     */
    public Observable<BaseResponse<List<SysCondition>>> loadOrderCondition(int billType) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.loadOrderCondition(billType);
    }

    /**
     * 获取搜索条件
     *
     * @param billType 0-货代，1-拖车
     * @return 返回相关搜索数据
     */
    public Observable<BaseResponse<AppBusinessOrderListCondition>> loadOrderCondition2(String billType) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.loadOrderCondition2(billType, getAppUserWrapper().getId());
    }

    /**
     * 业务下单
     *
     * @param appCompanyOrder 下单模型
     * @return 返回的信息文本
     */
    public Observable<BaseResponse<String>> addCompanyOrder(AppCompanyOrder appCompanyOrder) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.addCompanyOrder(appCompanyOrder, getAppUserWrapper().getId());
    }

    /**
     * 业务下单第二版本(添加)
     *
     * @param viewForwardOrder 实体类
     * @param check            是否需要检查
     * @return 返回相关信息
     */
    public Observable<BaseResponse<UpdateBusinessOrderModel>> addFowardOrder(ViewForwardOrder viewForwardOrder, boolean check) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.addFowardOrder(viewForwardOrder, check);
    }

    /**
     * 业务下单第二版本(更新)
     *
     * @param appViewForwardOrderWrapper 实体类
     * @param check                      是否需要检查
     * @return 返回相关信息
     */
    public Observable<BaseResponse<UpdateBusinessOrderModel>> updateFowardOrder(AppViewForwardOrderWrapper appViewForwardOrderWrapper, boolean check) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.updateFowardOrder(appViewForwardOrderWrapper, check);
    }

    /**
     * 业务下单第二版本(拖车)
     *
     * @param truckBills 实体类
     * @param check      是否需要检查
     * @return 返回相关信息
     */
    public Observable<BaseResponse<UpdateBusinessOrderModel>> addTruck(TruckBills truckBills, boolean check) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.addTruck(truckBills, check);
    }

    /**
     * 业务下单第二版本(拖车)
     *
     * @param recordUpdate 实体类
     * @param check        是否需要检查
     * @return 返回相关信息
     */
    public Observable<BaseResponse<UpdateBusinessOrderModel>> updateTruck(RecordUpdate recordUpdate, boolean check) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.updateTruck(recordUpdate, check);
    }

    /**
     * 业务下单查询
     *
     * @param appCompanyOrderCondition 条件实体
     * @param pageIndex                页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppCompanyOrder>>> getAppOrders(AppCompanyOrderCondition appCompanyOrderCondition, int pageIndex) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.getAppOrders(appCompanyOrderCondition, getAppUserWrapper().getId(), pageIndex);
    }

    /**
     * 业务下单查询 (模版)
     *
     * @param appCompanyOrderCondition 条件实体
     * @param pageIndex                页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppCompanyOrderTemplate>>> getOrderTemplates(AppCompanyOrderCondition appCompanyOrderCondition, int pageIndex) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.getOrderTemplates(appCompanyOrderCondition, getAppUserWrapper().getId(), pageIndex);
    }

    /**
     * 业务下单查询 (模版)
     *
     * @param pageIndex 页码
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<AppCompanyOrderTemplate>>> getOrderTemplates(int pageIndex) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.getOrderTemplates(getAppUserWrapper().getId(), pageIndex);
    }

    /**
     * 业务下单查询（旧版明细）
     *
     * @param id 主键id
     * @return 返回数据源
     */
    public Observable<BaseResponse<AppCompanyOrder>> getAppOrder(String id) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.getAppOrder(id);
    }

    /**
     * 业务下单查询(货代明细)
     *
     * @param id 主键id
     * @return 返回数据源
     */
    public Observable<BaseResponse<ViewForwardOrder>> detail(String id) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.detail(id);
    }

    /**
     * 添加模版
     *
     * @param appCompanyOrder 实体类
     * @return 编辑信息
     */
    public Observable<BaseResponse<String>> addTemplate(AppCompanyOrder appCompanyOrder) {
        AppCompanyOrderTemplate appCompanyOrderTemplate = new AppCompanyOrderTemplate();
        appCompanyOrderTemplate.setShipper(appCompanyOrder.getShipper());
        appCompanyOrderTemplate.setShipperId(appCompanyOrder.getShipperId());
        appCompanyOrderTemplate.setGoodsName(appCompanyOrder.getGoodsName());
        appCompanyOrderTemplate.setOwner(appCompanyOrder.getOwner());
        appCompanyOrderTemplate.setCarriageItem(appCompanyOrder.getCarriageItem());
        appCompanyOrderTemplate.setCarriageWay(appCompanyOrder.getCarriageWay());
        appCompanyOrderTemplate.setIsInsurance(appCompanyOrder.getIsInsurance());
        appCompanyOrderTemplate.setFinalDestination(appCompanyOrder.getFinalDestination());
        appCompanyOrderTemplate.setDockOfLoading(appCompanyOrder.getDockOfLoading());
        appCompanyOrderTemplate.setDockOfLoading(appCompanyOrder.getDockOfLoading());
        appCompanyOrderTemplate.setPortDelivery(appCompanyOrder.getPortDelivery());
        appCompanyOrderTemplate.setPortLoading(appCompanyOrder.getPortLoading());
        appCompanyOrderTemplate.setConsigneeClientPlaces(appCompanyOrder.getConsigneeClientPlaces());
        appCompanyOrderTemplate.setLoadClientPlaces(appCompanyOrder.getLoadClientPlaces());
        return editTemplate(appCompanyOrderTemplate);
    }

    /**
     * 编辑模版
     *
     * @param appCompanyOrderTemplate 实体类
     * @return 编辑信息
     */
    public Observable<BaseResponse<String>> editTemplate(AppCompanyOrderTemplate appCompanyOrderTemplate) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.editTemplate(appCompanyOrderTemplate, getAppUserWrapper().getId());
    }

    /**
     * 获取搜索条件
     *
     * @param billsId     工作单id
     * @param serviceType 0-货代，1-拖车
     * @return 返回相关搜索数据
     */
    public Observable<BaseResponse<List<AccountFee>>> feeList(String billsId, String serviceType) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.feeList(billsId, serviceType);
    }

    /**
     * 保存/编辑模版
     *
     * @param oldNewAccountFeeWrapper 实体类
     * @return 编辑信息
     */
    public Observable<BaseResponse<String>> feeSave(OldNewAccountFeeWrapper oldNewAccountFeeWrapper) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.feeSave(oldNewAccountFeeWrapper);
    }

    /**
     * 删除费用
     * @param ids 多个id
     * @param billsId 订单id
     */
    public Observable<BaseResponse<String>> feeDelete(String ids,String billsId) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.feeDelete(ids,billsId);
    }

    /**
     * 查询模板列表
     *
     * @param type 0-货代，1-拖车
     */
    public Observable<BaseResponse<PageList<WebOrderTemplateWrapper>>> templateList(String type, int pageIndex) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.templateList(type, pageIndex);
    }

    /**
     * 删货柜（货代）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    public Observable<BaseResponse<String>> forwardSonoDelete(String id, String billsId) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.forwardSonoDelete(id,billsId);
    }

    /**
     * 删装货地址 （货代）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    public Observable<BaseResponse<String>> forwardLoadAddressDelete(String id, String billsId) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.forwardLoadAddressDelete(id,billsId);
    }

    /**
     * 删卸货地址 （货代）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    public Observable<BaseResponse<String>> forwardUnloadAddressDelete(String id, String billsId) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.forwardUnloadAddressDelete(id,billsId);
    }

    /**
     * 删货柜（拖车）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    public Observable<BaseResponse<String>> truckSonoDelete(String id, String billsId) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.truckSonoDelete(id,billsId);
    }

    /**
     * 删地址（装货或者卸货，货代或者拖车）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    public Observable<BaseResponse<String>> truckAddressDelete(String id, String billsId) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.truckAddressDelete(id,billsId);
    }

    /**
     * 拖车APP派单
     * @param id 订单id
     */
    public Observable<BaseResponse<String>> sendToDriver(String id) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.sendToDriver(id);
    }

    /**
     * 拖车APP取消派单
     * @param driverOrderId truckBills.driverOrderId
     */
    public Observable<BaseResponse<String>> cancelDriverOrder(String driverOrderId) {
        CompanyOrderApi companyOrderApi = Api.getInstance().retrofit.create(CompanyOrderApi.class);
        return companyOrderApi.cancelDriverOrder(driverOrderId);
    }


}
