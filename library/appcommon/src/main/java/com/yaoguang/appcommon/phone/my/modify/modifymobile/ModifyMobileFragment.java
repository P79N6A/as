package com.yaoguang.appcommon.phone.my.modify.modifymobile;

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
 * 修改固话
 * Created by zhongjh on 2017/8/21.
 */
public class ModifyMobileFragment extends EditTextFragment {

    PersonalCenterContact.PersonalCenterInteractor mInteractor;


    public ModifyMobileFragment newInstance( String value) {
        return (ModifyMobileFragment) super.newInstance(false,  "电话", value);
    }

    @Override
    protected BaseBackFragment getFragment() {
        return new ModifyMobileFragment();
    }

    @Override
    protected Observable<BaseResponse<String>> getObservable(String value) {
        if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            mInteractor = new SPersonalCenterInteractorImpl();
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            mInteractor = new CPersonalCenterInteractorImpl();
        }
        return mInteractor.modifyMobile(value);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
