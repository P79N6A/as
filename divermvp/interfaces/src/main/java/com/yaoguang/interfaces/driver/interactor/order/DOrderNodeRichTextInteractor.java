package com.yaoguang.interfaces.driver.interactor.order;

import android.content.Context;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.DriverOrderNodeDetailWrapper;
import com.yaoguang.interfaces.driver.presenter.order.DOrderNodeRichTextPresenter;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/5/16.
 */

public interface DOrderNodeRichTextInteractor {

    void updateDriverImage(final String filepath, boolean compress, final DOrderNodeRichTextPresenter.uploadListener listener, Context context);

    /**
     * 检查实体类是否能提交
     *  @param nodesBean 节点数据，存储各种数据
     *
     */
    String checkCommit(DriverOrderNodeDetailWrapper nodesBean);

    Observable<BaseResponse<DriverOrderNodeDetailWrapper>> getEditData(String driverOrderId);

    String isAllowCont(DriverOrderNodeDetailWrapper detailWrapper);
}
