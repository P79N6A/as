package com.yaoguang.appcommon.publicsearch.loadingandunloading;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyBaseInfoApi;

import io.reactivex.Observable;

/**
 * 装卸货管理
 * Created by zhongjh on 2017/8/11.
 */
public class LoadingAndUnloadingInteractorImpl extends DCSBaseInteractorImpl implements LoadingAndUnloadingContact.LoadingAndUnloadingInteractor {


    @Override
    public Observable<BaseResponse<String>> deleteAddress(String ids, String codeId) {
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.batchDeletePlace(ids,codeId, getAppUserWrapper().getId());
    }


}
