package .shipschedulefragment;

import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;


import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.AppShipScheduleFragmentWrapper;
import com.yaoguang.greendao.entity.ShipScheduleFragmentCondition;



/**
 * @author zhongjh
 * @Package .shipschedulefragment
 * @Description: 船期查询 关联接口
 * @date 2018/01/15
 */
public interface ShipScheduleFragmentContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<ShipScheduleFragmentCondition> {

    }

    interface View extends BaseView, BaseListConditionView<AppShipScheduleFragmentWrapper, ShipScheduleFragmentCondition> {


    }
}
