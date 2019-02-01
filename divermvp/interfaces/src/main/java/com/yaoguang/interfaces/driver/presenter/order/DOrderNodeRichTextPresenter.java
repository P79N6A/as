package com.yaoguang.interfaces.driver.presenter.order;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.greendao.entity.DriverOrderNodeDetailWrapper;

/**
 * Created by zhongjh on 2017/5/16.
 */
public interface DOrderNodeRichTextPresenter extends BasePresenter {


    /**
     * 设置下一步
     * @param driverOrderNodeDetail 当前节点实体
     * @param tmpContFirst
     * @param tmpSealFirst
     * @param tmpContTwo
     * @param tmpSealTwo
     */
    void setNodeNext(DriverOrderNodeDetailWrapper driverOrderNodeDetail, String tmpContFirst,String  tmpSealFirst,String  tmpContTwo,String tmpSealTwo);

    void commitNode(DriverOrderNodeDetailWrapper driverOrderNodeDetail, String tmpContFirst, String tmpSealFirst, String tmpContTwo, String tmpSealTwo);

    void initEditData(String id);

    interface uploadListener {
        void success(String url);
        void fail();
        void onUploading(long max, long i);
    }
}
