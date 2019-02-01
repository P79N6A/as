package com.yaoguang.driver.phone.order.nodeEdit.nodeEditAddress;

import com.yaoguang.lib.base.impl.BasePresenter;

/**
 * Created by zhongjh on 2018/6/8.
 */
public class NodeEditAddressPresenter extends BasePresenter implements NodeEditAddressContact.Presenter {

    private NodeEditAddressContact.View mView;

    public NodeEditAddressPresenter(NodeEditAddressContact.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

}
