package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist;

import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

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
