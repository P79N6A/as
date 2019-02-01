package com.yaoguang.appcommon.publicsearch;

import com.yaoguang.datasource.api.shipper.OwnerBaseInfoApi;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.PublicSearchDataDao;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.biz.company.PublicSearchDataBiz;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.PublicSearchData;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyBaseInfoApi;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/6/2.
 */
public class PublicSearchInteractorImpl extends DCSBaseInteractorImpl implements PublicSearchContact.CPublicSearchInteractor {

    PublicSearchDataBiz mPublicSearchDataBiz = Injections.getPublicSearchDataBiz();

    /**
     * 托运人
     */
    public static final int TYPE_INFOCLIENTMANAGER = 1;
    /**
     * 业务下单 - 物流端：托运人 货主端：托运人
     */
    public static final int TYPE_INFOCLIENTMANAGER111 = 111;
    /**
     * 起运地
     */
    public static final int TYPE_DEPARTURE = 2;
    /**
     * 业务下单 - 起运地
     */
    public static final int TYPE_DEPARTURE222 = 222;
    /**
     * 目的地
     */
    public static final int TYPE_DESTINATION = 3;
    /**
     * 业务下单 - 目的地
     */
    public static final int TYPE_DESTINATION333 = 333;
    /**
     * 操作条款
     */
    public static final int TYPE_OPERATIONCLAUSE = 4;
    /**
     * 起运港 - 货主端需要关联id的
     */
    public static final int TYPE_PORTOFSHIPMENT = 5;
    /**
     * 起运港 - 需要type参数的
     */
    public static final int TYPE_PORTOFSHIPMENT55 = 55;
    /**
     * 起运港 - 货主端不需要关联id的
     */
    public static final int TYPE_PORTOFSHIPMENT555 = 555;
    /**
     * 目的港 - 货主端需要关联id的
     */
    public static final int TYPE_PORTOFDESTINATION = 6;
    /**
     * 目的港 - 需要type参数的
     */
    public static final int TYPE_PORTOFDESTINATION66 = 66;
    /**
     * 目的港 - 货主端不需要关联id的
     */
    public static final int TYPE_PORTOFDESTINATION666 = 666;
    /**
     * 运输条款
     */
    public static final int TYPE_TRANSPORTATIONCLAUSE = 7;
    /**
     * 货物名称
     */
    public static final int TYPE_GOODSNAME = 8;
    /**
     * 装货信息
     */
    public static final int TYPE_LOADPLACES = 9;
    /**
     * 卸货信息
     */
    public static final int TYPE_UNLOADPLACES = 10;
    /**
     * 关注的公司
     */
    public static final int TYPE_CONTACT_COMPANY = 13;
    /**
     * 运单号
     */
    public static final int TYPE_ORDER_NUMBER = 14;
    /**
     * 柜号
     */
    public static final int TYPE_ORDER_SONS = 15;
    /**
     * 工作单号
     */
    public static final int TYPE_ORDER_SNS = 16;
    /**
     * 业务员
     */
    public static final int TYPE_EMPLOYEES = 17;
    /**
     * 码头
     */
    public static final int TYPE_INFODOCK = 18;
    /**
     * 拖车公司
     */
    public static final int TYPE_TRUCK = 11;
    /**
     * 船公司
     */
    public static final int TYPE_SHIP_COMPANY = 12;
    /**
     * 货代公司
     */
    public static final int TYPE_FORWARD = 19;
    /**
     * 保险公司
     */
    public static final int TYPE_INSURER_COMPANY = 20;
    /**
     * 干线船名
     */
    public static final int TYPE_USER_INFO_SHIP = 21;
    /**
     * 费用项目
     */
    public static final int TYPE_FEE_TYPES = 22;
    /**
     * 查找车牌号 remark1 车辆编号  车牌号  拖车公司 司机名称 司机电话 挂车号码 证件号码 0196| |粤AN0196| |中通物流| |孙登杰| |13533289847| |沙步停车场| |372926196802091115
     */
    public static final int SEARCH_INFO_TRUCKNOS = 23;
    /**
     * 选择司机 remark1 司机名称 电话 证件 拖车公司
     */
    public static final int SEARCH_INFO_HACKMANS = 24;

    /**
     * 托运人
     */
    public static final String TYPE_NULL = "";
    /**
     * 拖车公司
     */
    public static final String TYPE_IS_TRUCK = "icm.IS_TRUCK";
    /**
     * 货代公司
     */
    public static final String TYPE_IS_FORWARD = "icm.IS_FORWARD";
    /**
     * 船公司\驳船公司
     */
    public static final String TYPE_IS_SHIP_COMPANY = "icm.IS_SHIP_COMPANY";
    /**
     * 保险公司
     */
    public static final String TYPE_IS_INSURER_COMPANY = "icm.IS_INSURER";


    @Override
    public Observable<List<PublicSearchData>> search(String query, int type) {
        QueryBuilder<PublicSearchData> qb = mPublicSearchDataBiz.queryBuilder();
        //查询第一页10条并且模糊查询query
        List<PublicSearchData> publicSearchDatas = qb.where(qb.and(PublicSearchDataDao.Properties.Type.eq(type), PublicSearchDataDao.Properties.Name.like("%" + query + "%"))).offset(0 * 10).limit(10).list();
        return Observable.just(publicSearchDatas);
    }

    @Override
    public Observable<Long> addQuery(String body, int type) {
        //先查出来再删除相同的
        QueryBuilder<PublicSearchData> qb = mPublicSearchDataBiz.queryBuilder();
        List<PublicSearchData> publicSearchDatas = qb.where(qb.and(PublicSearchDataDao.Properties.Type.eq(type), PublicSearchDataDao.Properties.Name.eq(body))).list();
        if (publicSearchDatas.size() > 0)
            mPublicSearchDataBiz.delete(publicSearchDatas);
        //再添加
        PublicSearchData publicSearchData = new PublicSearchData();
        publicSearchData.setName(body);
        publicSearchData.setType(type);
        return Observable.just(mPublicSearchDataBiz.save(publicSearchData));
    }

    @Override
    public Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> initAppPublicInfoWrappers(String body, String areaName, int type, int pageIndex, String codeId) {
        String id = "";
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            id = getAppUserWrapper().getId();
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            id = getUserOwner().getId();
        }
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        OwnerBaseInfoApi ownerBaseInfoApi = Api.getInstance().retrofit.create(OwnerBaseInfoApi.class);
        switch (type) {
            case TYPE_INFOCLIENTMANAGER:
                return companyBaseInfoApi.getAllCompany(id, body, pageIndex, TYPE_NULL);
            case TYPE_INFOCLIENTMANAGER111:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.getCompany(id, body, pageIndex, TYPE_NULL);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.getCompanyShipper(id, codeId, body, pageIndex, TYPE_NULL);
                }
            case TYPE_DEPARTURE:
                // 起运地
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.selectAllDock(id, body, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.selectAllCity(id, body, pageIndex);
                }
            case TYPE_DESTINATION:
                // 目的地
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.selectAllDock(id, body, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.selectAllCity(id, body, pageIndex);
                }
            case TYPE_DEPARTURE222:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.selectDock(id, body, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.selectDock(codeId, body, pageIndex);
                }
            case TYPE_DESTINATION333:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.selectDock(id, body, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.selectDock(codeId, body, pageIndex);
                }
            case TYPE_OPERATIONCLAUSE:
                return companyBaseInfoApi.selectTraffic(pageIndex);
            case TYPE_PORTOFSHIPMENT:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.selectPort(id, body, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.selectPortShipper(codeId, body, pageIndex);
                }
                return companyBaseInfoApi.selectPort(id, body, pageIndex);
            case TYPE_PORTOFSHIPMENT55:
                return companyBaseInfoApi.selectPort(id, body, pageIndex, "workbills_portLoading");
            case TYPE_PORTOFSHIPMENT555:
                return ownerBaseInfoApi.selectAllPort(id, body, pageIndex);
            case TYPE_PORTOFDESTINATION:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.selectPort(id, body, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.selectPortShipper(codeId, body, pageIndex);
                }
                return companyBaseInfoApi.selectPort(id, body, pageIndex);
            case TYPE_PORTOFDESTINATION66:
                return companyBaseInfoApi.selectPort(id, body, pageIndex, "workbills_portDelivery");
            case TYPE_PORTOFDESTINATION666:
                return ownerBaseInfoApi.selectAllPort(id, body, pageIndex);
            case TYPE_TRANSPORTATIONCLAUSE:
                return companyBaseInfoApi.selectTraffic(pageIndex);
            case TYPE_GOODSNAME:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.selectGood(id, body, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.selectGood(codeId, body, pageIndex);
                }
            case TYPE_LOADPLACES:
                return companyBaseInfoApi.selectLoadPlaces(codeId, body, areaName, pageIndex);
            case TYPE_UNLOADPLACES:
                return companyBaseInfoApi.selectLoadPlaces(codeId, body, areaName, pageIndex);
            case TYPE_TRUCK:
                return companyBaseInfoApi.getAllCompany(id, body, pageIndex, TYPE_IS_TRUCK);
            case TYPE_FORWARD:
                return companyBaseInfoApi.getAllCompany(id, body, pageIndex, TYPE_IS_FORWARD);
            case TYPE_INSURER_COMPANY:
                return companyBaseInfoApi.getAllCompany(id, body, pageIndex, TYPE_IS_INSURER_COMPANY);
            case TYPE_SHIP_COMPANY:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.getAllCompany(id, body, pageIndex, TYPE_IS_SHIP_COMPANY);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.getCompanyShipper(id, codeId, body, pageIndex, TYPE_IS_SHIP_COMPANY);
                }
                return companyBaseInfoApi.getAllCompany(id, body, pageIndex, TYPE_IS_SHIP_COMPANY);
            case TYPE_CONTACT_COMPANY:
                return ownerBaseInfoApi.getContactCompany(id, body, pageIndex);
            case TYPE_ORDER_NUMBER:
                int orderMBlNosType = 0;
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.getMBlNos(body, orderMBlNosType, id, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.getSonoMBlNos(body, pageIndex);
                }
                return companyBaseInfoApi.getMBlNos(body, orderMBlNosType, id, pageIndex);
            case TYPE_ORDER_SONS:
                int orderContNosType = 0;
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.getContNos(body, orderContNosType, id, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.getSonoContNos(body, pageIndex);
                }
                return companyBaseInfoApi.getContNos(body, orderContNosType, id, pageIndex);
            case TYPE_ORDER_SNS:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.getOrderSns(body, id, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    return ownerBaseInfoApi.getOrderSns(body, codeId, pageIndex);
                }
                return companyBaseInfoApi.getOrderSns(body, id, pageIndex);
            case TYPE_EMPLOYEES:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.getEmployees(id, body, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    // 暂时没有

                }
                return companyBaseInfoApi.getEmployees(id, body, pageIndex);
            case TYPE_INFODOCK:
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    return companyBaseInfoApi.selectInfoDock(id, body, pageIndex);
                } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                    // 暂时没有
                    return ownerBaseInfoApi.selectInfoDock(codeId, body, pageIndex);
                }
                return companyBaseInfoApi.selectInfoDock(id, body, pageIndex);
            case TYPE_USER_INFO_SHIP:
                return companyBaseInfoApi.searchUserInfoShips(body, pageIndex);
            case TYPE_FEE_TYPES:
                return companyBaseInfoApi.feeTypes(body, pageIndex);
            case SEARCH_INFO_TRUCKNOS:
                return companyBaseInfoApi.searchInfoTruckNos(body, pageIndex);
            case SEARCH_INFO_HACKMANS:
                return companyBaseInfoApi.searchInfoHackmans(body, pageIndex);
        }
        return null;
    }

}
