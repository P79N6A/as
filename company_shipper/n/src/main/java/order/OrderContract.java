package .order;

import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;


import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.AppOrderWrapper;
import com.yaoguang.greendao.entity.OrderCondition;



/**
 * @author zhongjh
 * @Package .order
 * @Description:  关联接口
 * @date 2018/01/05
 */
public interface OrderContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<OrderCondition> {

    }

    interface View extends BaseView, BaseListConditionView<AppOrderWrapper, OrderCondition> {


    }
}
