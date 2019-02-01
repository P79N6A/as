package com.yaoguang.shipper.activitys.register;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.yaoguang.appcommon.phone.register.BaseRegisterDetailFragment;
import com.yaoguang.appcommon.phone.register.modifybrief.ModifyBriefActivity;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.taobao.push.impl.PushManager;

import static com.yaoguang.appcommon.phone.register.BaseRegisterActivity.REQUESTCODE_BRIEF;


/**
 * 详情的注册
 * Created by zhongjh on 2017/7/4.
 */
public class RegisterDetailFragment extends BaseRegisterDetailFragment<UserOwner> {


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
//        getRegisterActivity().nextPage();
    }

    @Override
    public UserOwner getModel() {
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

    @Override
    protected void customInitUI(InitialView initialView) {
        initialView.viewHolder.rlCommpanyApplyType.setVisibility(View.GONE);
        initialView.viewHolder.rlShopDetail.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("autoCode", Constants.APP_SHIPPER);
                intent.putExtra("value", mInitialView.viewHolder.tvShopDetailTitleValue.getText().toString());
                intent.setClass(getActivity(), ModifyBriefActivity.class);
                getActivity().startActivityForResult(intent, REQUESTCODE_BRIEF);
            }
        });
    }

    @Override
    protected void saveModel(InitialView initialView) {
        //业务类型
        if (initialView.viewHolder.rbApplyStatus0.isChecked()) {
            getModel().setType(0);
        } else if (initialView.viewHolder.rbApplyStatus1.isChecked()) {
            getModel().setType(1);
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

        //判断是否是身份证还是营业执照
        if (initialView.viewHolder.rbApplyStatus0.isChecked()) {
            getModel().setCompanyCode(initialView.viewHolder.etCompanyCodeValue.getText().toString());
            getModel().setShopLogo(initialView.shipperShopLogo);
            getModel().setShopPhoto(initialView.shipperShopPhoto);
            getModel().setLicensePhoto(initialView.licensePhoto);
        } else if (initialView.viewHolder.rbApplyStatus1.isChecked()) {
            getModel().setIdNumber(initialView.viewHolder.etCompanyCodeValue.getText().toString());
            getModel().setShopLogo(initialView.shipperShopLogo);
            getModel().setShopPhoto(initialView.shipperShopPhoto);
            getModel().setIdPhoto(initialView.strIdPhoto1 + "," + initialView.strIdPhoto2);
        }

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
        return Constants.APP_SHIPPER;
    }

    @Override
    public void submitProgressSuccess() {
        getRegisterActivity().nextPage();
    }
}
