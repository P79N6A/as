package com.yaoguang.appcommon.phone.my.companyinfo.modifybrief;

import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.appcommon.common.view.EditTextFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterContact;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.CPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.DPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.SPersonalCenterInteractorImpl;

import io.reactivex.Observable;

/**
 * 商户简介
 * Created by zhongjh on 2017/8/21.
 */
public class ModifyBriefFragment extends EditTextFragment {

    PersonalCenterContact.PersonalCenterInteractor mInteractor;


    public ModifyBriefFragment newInstance(String value) {
        return (ModifyBriefFragment) super.newInstance(false, "商户简介", -1, value, true, false, 100, true, null, -1, false);
    }


    @Override
    protected BaseBackFragment getFragment() {
        return new ModifyBriefFragment();
    }

    @Override
    protected Observable<BaseResponse<String>> getObservable(String value) {
        if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            mInteractor = new SPersonalCenterInteractorImpl();
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            mInteractor = new CPersonalCenterInteractorImpl();
        } else if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            mInteractor = new DPersonalCenterInteractorImpl();
        }
        return mInteractor.modifySign(value);
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
