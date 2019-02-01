package com.yaoguang.shipper.offer.logistics;

import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 报价查询 - 物流公司
 * Created by zhongjh on 2018/8/29.
 */
public class LogisticsContact {

    public interface Presenter extends BasePresenterListCondition<String> {

    }

    public interface View extends BaseView, BaseListConditionView<AppPublicInfoWrapper, String> {

    }

}
