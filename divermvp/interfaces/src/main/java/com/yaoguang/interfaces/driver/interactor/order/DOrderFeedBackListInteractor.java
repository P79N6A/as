package com.yaoguang.interfaces.driver.interactor.order;


import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeFeedback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 订单反馈列表
 * Created by zhongjh on 2017/5/17.
 */
public interface DOrderFeedBackListInteractor {

    Observable<BaseResponse<List<DriverOrderNodeFeedback>>> initList(String mOrderId);

    Observable<BaseResponse<DriverOrderNodeFeedback>> initOrderNodeDetailList(String mOrderId);

    BaseResponse<PageList<DriverOrderNodeFeedback>> getPageList(BaseResponse<ArrayList<DriverOrderNodeFeedback>> data);
}
