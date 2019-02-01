package com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist;

import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.company.AppCompanyOrder;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.interfaces.BaseListView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

import io.reactivex.Observable;

/**
 * 预订单查询
 * Created by zhongjh on 2017/6/12.
 */
public class BaseBusinessOrderTempListContact {

    public interface Presenter<T,C> extends BasePresenterListCondition<C> {

    }

    public interface View<T, C> extends BaseView, BaseListConditionView<T, C> {

    }

}
