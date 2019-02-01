package com.yaoguang.appcommon.address.editAddress;

import com.yaoguang.lib.base.impl.BasePresenter;

/**
 * Created by zhongjh on 2018/6/8.
 */
public class EditAddressPresenter extends BasePresenter implements EditAddressContact.Presenter {

    private EditAddressContact.View mView;

    public EditAddressPresenter(EditAddressContact.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

}
