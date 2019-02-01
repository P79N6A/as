package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list;

import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppTruckOrder;
import com.yaoguang.interfaces.BaseListView;

import io.reactivex.Observable;

/**
 * 拖车预订单查询
 * Created by zhongjh on 2017/6/12.
 */
public abstract class BaseBusinessOrderTrailerListContact {

    public interface Presenter<T,C> extends BasePresenterListCondition<C> {

        /**
         * 利用当前订单数据添加入模版
         * @param item 当前订单数据
         */
        void addTemplate(T item);

    }

    public interface View<T, C> extends BaseView, BaseListConditionView<T, C> {

        /**
         * 星星按钮打开启用
         * @param b
         */
        void setStartEnable(boolean b);

    }

}
