package com.yaoguang.company.activitys.register;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.yaoguang.appcommon.phone.register.modifybrief.ModifyBriefActivity;
import com.yaoguang.appcommon.phone.register.BaseRegisterDetailFragment;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.greendao.entity.UserApply;
import com.yaoguang.taobao.push.impl.PushManager;

import static com.yaoguang.appcommon.phone.register.BaseRegisterActivity.REQUESTCODE_BRIEF;

/**
 * 详情的注册
 * Created by zhongjh on 2017/7/4.
 */
public class RegisterDetailFragment extends BaseRegisterDetailFragment<UserApply> {


    public static RegisterDetailFragment newInstance() {
        return new RegisterDetailFragment();
    }

    @Override
    public void startShowCountdown() {

    }

    @Override
    public void customSetting() {
        getRegisterActivity().setRegisterNode(1);
    }

    @Override
    public void nextPage() {

    }

    @Override
    public UserApply getModel() {
        return getRegisterActivity().getModel();
    }

    @Override
    public void stopShowCountdown() {

    }

    private RegisterActivity getRegisterActivity() {
        return (RegisterActivity) getActivity();
    }

    @Override
    public String getCode() {
        return getRegisterActivity().getCode();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void customInitUI(InitialView initialView) {
        initialView.viewHolder.rlShipperApplyStatus.setVisibility(View.GONE);
//        viewHolder.llRemark.setOnClickListener(v -> startForResult(new ModifyBriefFragment().newInstance(Constants.APP_COMPANY, viewHolder.tvValueRemark.getText().toString()), BaseCompanyInfoFragment.REQUESTCODE_REMARK));
//        initialView.viewHolder.rlShopDetail.setOnClickListener(v -> new ModifyBriefActivity().newInstance(getActivity(), REQUESTCODE_BRIEF, Constants.APP_COMPANY, "商户简介", mInitialView.viewHolder.tvShopDetailTitleValue.getText().toString()));
        initialView.viewHolder.rlShopDetail.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("value", mInitialView.viewHolder.tvShopDetailTitleValue.getText().toString());
            intent.setClass(getActivity(), ModifyBriefActivity.class);
            getActivity().startActivityForResult(intent, REQUESTCODE_BRIEF);
        });
    }

    @Override
    protected void saveModel(InitialView initialView) {
        //业务类型
        if (initialView.viewHolder.cbApplyType0.isChecked() && initialView.viewHolder.cbApplyType1.isChecked()) {
            getModel().setApplyType(2);
        } else if (initialView.viewHolder.cbApplyType1.isChecked()) {
            getModel().setApplyType(1);
        } else if (initialView.viewHolder.cbApplyType0.isChecked()) {
            getModel().setApplyType(0);
        }
        //商户名称
        getModel().setCompanyName(initialView.viewHolder.etValueDepartureTitle.getText().toString());
        //地址
        getModel().setProvince(initialView.provinces);
        getModel().setCity(initialView.citys);
        getModel().setDistrict(initialView.areas);
        getModel().setCompanyAddress(initialView.viewHolder.etValueCompanyAddress.getText().toString());
        getModel().setName(initialView.viewHolder.etNameValue.getText().toString());
        getModel().setMobile(initialView.viewHolder.etValueMobile.getText().toString());
        getModel().setShopLogo(initialView.shopLogo);
        getModel().setShopPhoto(initialView.shopPhoto);
        getModel().setLicensePhoto(initialView.licensePhoto);
        getModel().setCompanyCode(initialView.viewHolder.etCompanyCodeValue.getText().toString());
        getModel().setShopDetail(initialView.viewHolder.tvShopDetailTitleValue.getText().toString());
        String deviceToken;
        if (TextUtils.isEmpty(PushManager.deviceToken)) {
            deviceToken = "Android " + android.os.Build.MANUFACTURER + " SDKINT：" + android.os.Build.VERSION.SDK_INT + " " + android.os.Build.VERSION.RELEASE;
        } else {
            deviceToken = PushManager.deviceToken;
        }
        getModel().setDeviceToken(deviceToken);
    }

    @Override
    protected String getCodeType() {
        return Constants.APP_COMPANY;
    }

    @Override
    public void submitProgressSuccess() {
        getRegisterActivity().nextPage();
    }


}
