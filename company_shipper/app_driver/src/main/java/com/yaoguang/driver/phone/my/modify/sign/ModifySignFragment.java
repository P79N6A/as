package com.yaoguang.driver.phone.my.modify.sign;

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
 * 修改个人简介
 * Created by wly on 2017/8/21.
 */
public class ModifySignFragment extends EditTextFragment {

    PersonalCenterContact.PersonalCenterInteractor mInteractor;

    public ModifySignFragment newInstance(String codtType, String value) {
        return (ModifySignFragment) super.newInstance(false, codtType, "个人简介", "简单介绍一下你自己吧", -1, value, null, null, true, true, 40, true, null, -1, false);
    }

    @Override
    protected BaseBackFragment getFragment() {
        return new ModifySignFragment();
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
        return mInteractor.modifySign(value);
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
