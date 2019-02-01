package .clientcondition;

import com.yaoguang.common.base.interfaces.BaseListConditionView;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.base.interfaces.BasePresenterListCondition;


import com.yaoguang.common.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.AppclientConditionWrapper;
import com.yaoguang.greendao.entity.clientConditionCondition;



/**
 * @author zhongjh
 * @Package .clientcondition
 * @Description: 委托人条件筛选 关联接口
 * @date 2018/04/09
 */
public interface clientConditionContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<clientConditionCondition> {

    }

    interface View extends BaseView, BaseListConditionView<AppclientConditionWrapper, clientConditionCondition> {


    }
}
