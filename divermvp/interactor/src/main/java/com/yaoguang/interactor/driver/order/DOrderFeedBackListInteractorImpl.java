package com.yaoguang.interactor.driver.order;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeFeedback;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interfaces.driver.interactor.order.DOrderFeedBackListInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.OrderApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/5/17.
 */
public class DOrderFeedBackListInteractorImpl<T> extends DCSBaseInteractorImpl implements DOrderFeedBackListInteractor {

    @Override
    public Observable<BaseResponse<List<DriverOrderNodeFeedback>>> initList(final String mOrderId) {
        return Api.getInstance().retrofit.create(OrderApi.class).feedbackList(mOrderId, getDriver().getId());
    }

    @Override
    public Observable<BaseResponse<DriverOrderNodeFeedback>> initOrderNodeDetailList(final String mOrderId) {
        return Api.getInstance().retrofit.create(OrderApi.class).orderNodeDetail(mOrderId);
    }

    @Override
    public BaseResponse<PageList<DriverOrderNodeFeedback>> getPageList(BaseResponse<ArrayList<DriverOrderNodeFeedback>> data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setState(data.getState());
        if (baseResponse.getState().equals("200")) {
            baseResponse.setMessage(data.getMessage());
            PageList<DriverOrderNodeFeedback> pageList = new PageList();
            pageList.setPageNo(1);
            pageList.setResult(data.getResult());
            baseResponse.setResult(pageList);
        }
        return baseResponse;
    }
}
