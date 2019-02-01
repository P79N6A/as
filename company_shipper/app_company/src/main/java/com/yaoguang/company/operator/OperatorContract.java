package com.yaoguang.company.operator;

import com.yaoguang.greendao.entity.company.AppCompanyBanDanCondition;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeList;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 *
 * Created by zhongjh on 2018/7/26.
 */
public class OperatorContract {

    interface Presenter extends BasePresenterListCondition<AppCompanyBanDanCondition> {

        /**
         * 拖车办单操作
         * @param sonoIds 货柜id，逗号分隔
         * @param operateType 操作类型 0:船到 1:办单 2:打单
         */
        void truckUpdate(String sonoIds, String operateType);

        /**
         * 货代办单操作
         * @param sonoIds 货柜id，逗号分隔
         * @param operateType 操作类型 0:船到 1:办单 2:打单
         */
        void freightUpdate(String sonoIds, String operateType);
    }

    interface View extends BaseView, BaseListConditionView<AppCompanyBanDanWrapper, AppCompanyBanDanCondition> {


    }

}
