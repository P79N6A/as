package com.yaoguang.interactor.driver.my;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UnreadNum;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interfaces.driver.personalcenter.DPersonalCenterInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.MessageApi;

import io.reactivex.Observable;

/**
 * Created by 韦理英
 * on 2017/4/25 0025.
 */

public class DMyInteractorImpl extends DCSBaseInteractorImpl implements DPersonalCenterInteractor {

    @Override
    public Driver getLocalDriverInfo() {
        return DataStatic.getInstance().getDriver();
    }

    public Observable<BaseResponse<UnreadNum>> getUnreadNum() {
        return Api.getInstance().retrofit.create(MessageApi.class).getUnreadNum(getDriver().getId(), 1);
    }
}
