package com.yaoguang.driver.phone.my.modify.qq;

import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.driver.widget.EditTextFragment;
import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterContact;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.CPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.DPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.SPersonalCenterInteractorImpl;

import io.reactivex.Observable;

/**
 * 修改QQ
 * Created by wly on 2017/8/21.
 */
public class ModifyQQFragment extends EditTextFragment {

    PersonalCenterContact.PersonalCenterInteractor mInteractor;

    public ModifyQQFragment newInstance(String codtType, String value) {
        return (ModifyQQFragment) super.newInstance(false, codtType, "QQ", null, -1, value, null, "请输入你的QQ", true, true, 100, true, null, -1, false);
    }

    @Override
    protected BaseBackFragment getFragment() {
        return new ModifyQQFragment();
    }

    @Override
    protected Observable<BaseResponse<String>> getObservable(String value) {
        if (mCodeType.equals(Constants.APP_SHIPPER)) {
            mInteractor = new SPersonalCenterInteractorImpl();
        } else if (mCodeType.equals(Constants.APP_COMPANY)) {
            mInteractor = new CPersonalCenterInteractorImpl();
        } else if (mCodeType.equals(Constants.APP_DRIVER)) {
            mInteractor = new DPersonalCenterInteractorImpl();
        }
        return mInteractor.modifyQQ(value);
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
