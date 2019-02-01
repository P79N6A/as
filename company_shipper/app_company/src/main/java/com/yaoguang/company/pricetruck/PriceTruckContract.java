package com.yaoguang.company.pricetruck;

import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;


import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.AppPriceTruckWrapper;
import com.yaoguang.greendao.entity.PriceTruckCondition;

import java.util.List;


/**
 * @author zhongjh
 * @Package com.yaoguang.company.pricetruck
 * @Description: 拖车报价查询 关联接口
 * @date 2018/01/05
 */
public interface PriceTruckContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<PriceTruckCondition> {
        /**
         * 解析柜子数据
         */
        void analysisSonos();
    }

    interface View extends BaseView, BaseListConditionView<AppPriceTruckWrapper, PriceTruckCondition> {

        /**
         * 赋值柜子信息
         *
         * @param values 值
         */
        void setSonos(List<String> values);

    }
}
