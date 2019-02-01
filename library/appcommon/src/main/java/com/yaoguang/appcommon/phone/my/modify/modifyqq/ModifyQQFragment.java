package com.yaoguang.appcommon.phone.my.modify.modifyqq;

import android.text.InputType;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.appcommon.common.view.EditTextFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterContact;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.CPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.SPersonalCenterInteractorImpl;

import io.reactivex.Observable;

/**
 * 修改qq
 * Created by zhongjh on 2017/8/21.
 */
public class ModifyQQFragment extends EditTextFragment {

    PersonalCenterContact.PersonalCenterInteractor mInteractor;


    public ModifyQQFragment newInstance(String value) {
        return (ModifyQQFragment) super.newInstance(false, "QQ", R.drawable.ic_qq_j, value, false, false, 0, true, null, InputType.TYPE_CLASS_NUMBER, false);
    }


    @Override
    protected BaseBackFragment getFragment() {
        return new ModifyQQFragment();
    }

    @Override
    protected Observable<BaseResponse<String>> getObservable(String value) {
        if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            mInteractor = new SPersonalCenterInteractorImpl();
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            mInteractor = new CPersonalCenterInteractorImpl();
        }
        return mInteractor.modifyQQ(value);
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
