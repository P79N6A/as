package com.yaoguang.interactor.driver.contact;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.common.UserOffice;

import io.reactivex.Observable;

/**
 * 订单详情
 * Created by zhongjh on 2017/4/26.
 */
public interface DCompanyDetailInteractor {


    Observable<BaseResponse<UserOffice>> getCompanyInfo(String companyId, String type);
}
