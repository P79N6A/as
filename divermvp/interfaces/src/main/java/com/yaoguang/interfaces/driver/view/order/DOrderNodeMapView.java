package com.yaoguang.interfaces.driver.view.order;


import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.lib.base.interfaces.BaseView;

import java.util.List;

/**
 * Created by zhongjh on 2017/4/28.
 */

public interface DOrderNodeMapView extends BaseView {

    void setDriverOrderNodes(List<DriverOrderNode> driverOrderNodes);

    /**
     * 设置完成
     */
    void setIsValid();

    /**
     * 刷新数据
     */
    void refreshData();

    void clearOldData();

    void success(BaseResponse<String> value);

    void fail(String message);

    /**
     * 启动另一个Fragment
     * @param driverOrderNodeList
     */
    void startForResultOrderNodeRichTextFragment(String driverOrderNodeList);

    void switchToNode(String switchToNodeId);

    void switchToNodeSuccess();

    void orderFinish();
}
