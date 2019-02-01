package com.yaoguang.driver.my.modify.nickname;

import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.common.base.interfaces.BasePresenter;

import com.yaoguang.common.common.Constants;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.widget.EditTextFragment;
import com.yaoguang.interactor.common.my.PersonalCenterContact;
import com.yaoguang.interactor.company.my.CPersonalCenterInteractorImpl;
import com.yaoguang.interactor.driver.my.setting.DPersonalCenterInteractorImpl;
import com.yaoguang.interactor.shipper.my.SPersonalCenterInteractorImpl;

import io.reactivex.Observable;

/**
 * 修改昵称
 * Created by wly on 2017/8/21.
 */
public class ModifyNickNameFragment extends EditTextFragment {

    PersonalCenterContact.PersonalCenterInteractor mInteractor;

    public ModifyNickNameFragment newInstance(String codtType, String value) {
        return (ModifyNickNameFragment) super.newInstance(false, codtType, "修改昵称", "设置后，其他人将看到你的昵称", -1, value, "12个字符以内，仅可使用汉字、字母、数字或下划线", "", true, true, 12, true, null, -1, false);
    }

    @Override
    protected BaseBackFragment getFragment() {
        return new ModifyNickNameFragment();
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
        return mInteractor.modifyNickName(value);
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
