package com.yaoguang.driver.phone.order.nodeEdit.nodeEditPort;

import com.yaoguang.greendao.entity.common.InfoDock;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

import java.util.HashMap;

/**
 * Created by zhongjh on 2018/7/9.
 */
public class NodeEditPortContract {

    interface Presenter extends BasePresenterListCondition<HashMap<String, String>> {


    }

    interface View extends BaseView, BaseListConditionView<InfoDock, HashMap<String, String>> {

    }

}
