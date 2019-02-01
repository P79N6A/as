package com.yaoguang.appcommon.phone.order;

import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;


import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.SysConditionWrapper;

import java.util.List;


/**
 * @author zhongjh
 * @Package com.yaoguang.company.order
 * @Description: 订单跟踪 关联接口
 * @date 2018/01/05
 */
public interface OrderContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<SysConditionWrapper> {

        /**
         * 加载所有日期查询条件
         */
        void loadOrderConditionDate();

        /**
         * 获取关注的公司
         */
        void analysisContactCompany();

    }

    interface View<T> extends BaseView, BaseListConditionView<T, SysConditionWrapper> {

        /**
         * 赋值日期条件列表
         *
         * @param result 数据源
         */
        void setSysConditionDates(List<SysCondition> result);

        /**
         * 托运人按钮，货代跟拖车不一样
         */
        void setRlUserOnClick();

        /**
         * 显示时间类型选择窗体
         */
        void showSweetSheetDateType();

        /**
         * 起始日期
         */
        void showLoadingDate();

        /**
         * 结束日期
         */
        void showEndDate();

        /**
         * 起始日期 - 业务
         */
        void showBusinessDateStart();

        /**
         * 结束日期 - 业务
         */
        void showBusinessDateEnd();

        /**
         * 起始时间 - 装货
         */
        void showLoadDateStart();

        /**
         * 结束时间 - 装货
         */
        void showLoadDateEnd();

        /**
         * 赋值默认第一个关注的公司名称
         * @param appPublicInfoWrapper 第一个公司
         */
        void setContactCompany(AppPublicInfoWrapper appPublicInfoWrapper);
    }
}
