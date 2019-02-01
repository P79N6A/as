package com.yaoguang.company.businessorder2.common.list;

import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.company.AppBusinessOrderListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 工作单父类
 * Created by zhongjh on 2018/11/14.
 */
public class BaseBusinessOrderListContact {

    public interface Presenter extends BasePresenterListCondition<SysConditionWrapper> {

        /**
         * 获取搜索条件
         */
        void loadOrderCondition2();

    }

    public interface View<T> extends BaseView, BaseListConditionView<T, SysConditionWrapper> {

        /**
         * 初始化搜索数据view
         * @param result 数据源
         */
        void initCondition(AppBusinessOrderListCondition result);
    }

}
