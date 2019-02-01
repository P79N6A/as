package com.yaoguang.shipper.sonos;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppOwnerSonoCondition;
import com.yaoguang.greendao.entity.AppOwnerSonoWrapper;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.shipper.OwnerApi;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * 货柜查询业务层
 * Created by zhongjh on 2017/6/12.
 */
public class SonosInteractorImpl extends DCSBaseInteractorImpl implements SonosContact.SonosInteractor {

    @Override
    public Observable<BaseResponse<PageList<AppOwnerSonoWrapper>>> initDatas(AppOwnerSonoCondition condition, int pageIndex) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        // 处理数据，返回的是单个数据，需要把它添加到列表里面
        Observable<BaseResponse<ArrayList<AppOwnerSonoWrapper>>> data = ownerApi.getInfoVoyageTabless(condition, getUserOwner().getId(), pageIndex);
        return data.map(driverOrderNodeProgressWrapperBaseResponse -> {
            BaseResponse<PageList<AppOwnerSonoWrapper>> baseResponse = new BaseResponse<>();
            baseResponse.setState(driverOrderNodeProgressWrapperBaseResponse.getState());
            if (baseResponse.getState().equals("200")) {
                PageList<AppOwnerSonoWrapper> pageList = new PageList<>();
                pageList.setPageNo(1);
                pageList.setPageSize(1);
                if (driverOrderNodeProgressWrapperBaseResponse.getResult() == null)
                {
                    baseResponse.setTotalCount(0);
                    pageList.setTotalCount(0);
                    pageList.setResult(null);
                }else{
                    baseResponse.setTotalCount(1);
                    pageList.setTotalCount(1);
                    pageList.setResult(driverOrderNodeProgressWrapperBaseResponse.getResult());
                }
                baseResponse.setResult(pageList);
            }
            return baseResponse;
        });
    }

}
