package com.yaoguang.datasource.shipper;

import com.yaoguang.datasource.api.shipper.OwnerBaseInfoApi;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/1/16.
 */
public class OwnerBaseInfoDataSource extends DCSBaseDataSource {

    /**
     * 查询柜型柜量，缓存一天：40GP,40HP
     */
    public Observable<BaseResponse<List<InfoContType>>> getConts() {
        //获取柜型
        OwnerBaseInfoApi ownerBaseInfoApi = Api.getInstance().retrofit.create(OwnerBaseInfoApi.class);
        return ownerBaseInfoApi.getConts();
    }

    /**
     * 业务下单-选择操作、运输条款
     *
     * @return 数据源
     */
    public Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectTraffic() {
        OwnerBaseInfoApi ownerBaseInfoApi = Api.getInstance().retrofit.create(OwnerBaseInfoApi.class);
        return ownerBaseInfoApi.selectTraffic("",1);
    }

    /**
     * 船期查询
     *
     * @param infoVoyageTableCondition 条件实体
     * @param pageIndex                页码
     * @return 返回數據源
     */
    public Observable<BaseResponse<PageList<InfoVoyageTable>>> getInfoVoyageTables(InfoVoyageTableCondition infoVoyageTableCondition, int pageIndex) {
        OwnerBaseInfoApi ownerBaseInfoApi = Api.getInstance().retrofit.create(OwnerBaseInfoApi.class);
        return ownerBaseInfoApi.getInfoVoyageTables(infoVoyageTableCondition, getUserOwner().getId(), pageIndex);
    }

    /**
     * 关注的公司
     */
    public Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getContactCompany(String name) {
        OwnerBaseInfoApi businessonOrderApi = Api.getInstance().retrofit.create(OwnerBaseInfoApi.class);
        return businessonOrderApi.getContactCompany(getUserOwner().getId(), name, 1);
    }

}
