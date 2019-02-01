package com.yaoguang.company.businessorder2.forwarder.businesstemp;

import com.yaoguang.greendao.entity.company.AppCompanyOrder;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppCompanyOrderTemplate;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 选择模板新建
 * Created by zhongjh on 2018/10/29.
 */
public class BusinessOrderTempListContact {

    public interface Presenter extends BasePresenterListCondition<String> {

    }

    public interface View extends BaseView, BaseListConditionView<AppCompanyOrderTemplate, String> {

    }

}
