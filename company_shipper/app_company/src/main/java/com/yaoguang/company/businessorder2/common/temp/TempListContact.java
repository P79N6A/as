package com.yaoguang.company.businessorder2.common.temp;

import com.yaoguang.greendao.entity.company.WebOrderTemplateWrapper;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2018/11/14.
 */

public class TempListContact {

    public interface Presenter extends BasePresenterListCondition<String> {

    }

    public interface View extends BaseView, BaseListConditionView<WebOrderTemplateWrapper, String> {

    }

}
