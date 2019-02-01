package com.yaoguang.appcommon.phone.register.modifybrief;


import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.appcommon.common.view.EditTextFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * 依附在activity的商户简介
 * Created by zhongjh on 2017/8/21.
 */
public class ModifyBriefFragment extends EditTextFragment {

    public ModifyBriefFragment newInstance( String value) {
        return (ModifyBriefFragment) super.newInstance(true,  "商户简介", -1, value, true, false, 100, true, null, -1, false);
    }

    @Override
    protected BaseBackFragment getFragment() {
        return new ModifyBriefFragment();
    }

    @Override
    protected Observable<BaseResponse<String>> getObservable(String value) {
        return null;
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
