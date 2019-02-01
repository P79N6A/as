package com.yaoguang.driver.my.modify.email;

import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.common.base.interfaces.BasePresenter;

import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.RegexValidator;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.widget.EditTextFragment;
import com.yaoguang.interactor.common.my.PersonalCenterContact;
import com.yaoguang.interactor.company.my.CPersonalCenterInteractorImpl;
import com.yaoguang.interactor.driver.my.setting.DPersonalCenterInteractorImpl;
import com.yaoguang.interactor.shipper.my.SPersonalCenterInteractorImpl;

import io.reactivex.Observable;

/**
 * 修改邮箱
 * Created by zhongjh on 2017/8/21.
 */
public class ModifyMailFragment extends EditTextFragment {

    PersonalCenterContact.PersonalCenterInteractor mInteractor;


    public ModifyMailFragment newInstance(String codtType, String value) {
        return (ModifyMailFragment) super.newInstance(false, codtType, "邮箱", null, -1, value, null, "请输入你的电子邮箱", false, true, 0, true, RegexValidator.REGEX_EMAIL, -1, false);
    }


    @Override
    protected BaseBackFragment getFragment() {
        return new ModifyMailFragment();
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

        return mInteractor.modifyEmail(value);
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
