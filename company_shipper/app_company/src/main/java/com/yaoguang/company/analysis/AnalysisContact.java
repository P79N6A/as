package com.yaoguang.company.analysis;

import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

import java.util.List;

/**
 * 货代报价查询
 * Created by zhongjh on 2017/6/12.
 */
public class AnalysisContact {

    public interface Presenter extends BasePresenterListCondition<PriceAnalysisCondition> {

        /**
         * 解析柜子数据
         */
        void analysisSonos();
    }

    public interface View extends BaseView, BaseListConditionView<AppPriceAnalysisWrapper, PriceAnalysisCondition> {

        /**
         * 赋值柜子信息
         *
         * @param values 值
         */
        void setSonos(List<String> values);
    }

}
