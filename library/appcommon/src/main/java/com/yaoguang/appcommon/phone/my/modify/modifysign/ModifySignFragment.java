package com.yaoguang.appcommon.phone.my.modify.modifysign;

import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.appcommon.common.view.EditTextFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * 修改签名
 * Created by zhongjh on 2017/8/21.
 */
public class ModifySignFragment extends EditTextFragment {

    public ModifySignFragment newInstance( String value) {
        return (ModifySignFragment) super.newInstance(false,  "签名", -1, value, true, true, 100, true, null, -1, false);
    }

    @Override
    protected BaseBackFragment getFragment() {
        return new ModifySignFragment();
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
