package com.yaoguang.company.activitys.register2;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yaoguang.appcommon.phone.register2.detail.BaseRegisterDetailActivity;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.UserApply;
import com.yaoguang.taobao.push.impl.PushManager;

/**
 * 详情页的注册
 * Created by zhongjh on 2017/11/30.
 */
public class RegisterDetailActivity extends BaseRegisterDetailActivity<UserApply> {

    public static void newInstance(Activity activity, UserApply value, String phone, String password) {
        Intent intent = new Intent(activity, RegisterDetailActivity.class);
        intent.putExtra(DATA, value);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        activity.startActivity(intent);
    }

    @Override
    public UserApply getModel() {

        if (model == null)
            model = new UserApply();

        // 手机号
        model.setPhone(getIntent().getStringExtra("phone"));
        // 密码
        model.setPassword(getIntent().getStringExtra("password"));
        //业务类型
        if (mInitialView.tvCompanyForwarder.getTag().equals("1") && mInitialView.tvCompanyTrailer.getTag().equals("1")) {
            model.setApplyType(2);
        } else if (mInitialView.tvCompanyForwarder.getTag().equals("1")) {
            model.setApplyType(0);
        } else if (mInitialView.tvCompanyTrailer.getTag().equals("1")) {
            model.setApplyType(1);
        }
        model.setCompanyName(mInitialView.etValueDepartureTitle.getText().toString());//商户名称
        model.setProvince(provinces);// 省
        model.setCity(citys); // 市
        model.setDistrict(areas); // 区
        model.setCompanyAddress(mInitialView.etValueCompanyAddress.getText().toString()); // 道路门牌号
        model.setName(mInitialView.etNameValue.getText().toString()); // 联系人姓名
        model.setMobile(mInitialView.etValueMobile.getText().toString()); // 联系人电话
        model.setLicensePhoto(mLicensePhoto); // 营业制造图片
        model.setCompanyCode(mInitialView.etCompanyCodeValue.getText().toString()); // 营业执照号码

        String deviceToken;
        if (TextUtils.isEmpty(PushManager.deviceToken)) {
            deviceToken = "Android " + android.os.Build.MANUFACTURER + " SDKINT：" + android.os.Build.VERSION.SDK_INT + " " + android.os.Build.VERSION.RELEASE;
        } else {
            deviceToken = PushManager.deviceToken;
        }
        model.setDeviceToken(deviceToken);

        return model;
    }

    @Override
    protected String getCodeType() {
        return Constants.APP_COMPANY;
    }

    @Override
    protected void customInitUI(InitialView initialView) {
        // 隐藏相关view，显示相关view
        // (1) 选择类型
        initialView.llCompany.setVisibility(View.VISIBLE);
        initialView.llShipper.setVisibility(View.GONE);

        // (2) 明细资料 - 图片
        initialView.llPhotoCompany.setVisibility(View.VISIBLE);
        initialView.llPhotoShipper.setVisibility(View.GONE);

        // (3) 明细资料 - 数据
        initialView.llInformationCompany.setVisibility(View.VISIBLE);
        initialView.llInformationShipper.setVisibility(View.GONE);

    }

    @Override
    protected void initData(UserApply userApply) {
        // 请选择商户业务类型
        mInitialView.tvStep1.setText("请选择商户的业务类型（可多选）");

        if (userApply == null)
            return;
        //业务类型
        switch (userApply.getApplyType()) {
            case 2:
                mInitialView.tvCompanyForwarder.setTag("1");
                mInitialView.tvCompanyForwarder.setBackgroundResource(R.drawable.ic_hdyw_click);
                mInitialView.tvCompanyTrailer.setTag("1");
                mInitialView.tvCompanyTrailer.setBackgroundResource(R.drawable.ic_hdyw_click);
                break;
            case 0:
                mInitialView.tvCompanyForwarder.setTag("1");
                mInitialView.tvCompanyForwarder.setBackgroundResource(R.drawable.ic_hdyw_click);
                break;
            case 1:
                mInitialView.tvCompanyTrailer.setTag("1");
                mInitialView.tvCompanyTrailer.setBackgroundResource(R.drawable.ic_hdyw_click);
                break;
        }
        mInitialView.etValueDepartureTitle.setText(ObjectUtils.parseString(userApply.getCompanyName()));// 商户名称
        provinces = userApply.getProvince();// 省
        citys = userApply.getCity();// 市
        areas = userApply.getDistrict();// 区
        mInitialView.tvAddressValue.setText(provinces + citys + areas);
        mInitialView.etValueCompanyAddress.setText(userApply.getCompanyAddress());// 道路门牌号
        mInitialView.etNameValue.setText(userApply.getName());// 联系人姓名
        mInitialView.etValueMobile.setText(userApply.getMobile());// 联系人电话

        // 营业制造图片
        if (!TextUtils.isEmpty(userApply.getLicensePhoto())) {
            mLicensePhoto = userApply.getLicensePhoto();
            mInitialView.llLicensePhoto.setVisibility(View.GONE);
            mInitialView.flLicensePhoto.setBackgroundResource(0);
            mInitialView.imgLicensePhoto.setVisibility(View.VISIBLE);
            mInitialView.imgLicensePhoto.setTag(R.id.tag_glide, mLicensePhoto);
            mLicensePhoto = GlideManager.getImageUrl(mLicensePhoto, true);
            Glide.with(RegisterDetailActivity.this)
                    .load(mLicensePhoto)
                    .apply(new RequestOptions()
                            .centerCrop()
                            .skipMemoryCache(true)
                            .error(R.drawable.ic_jz_sb01)
                            .placeholder(R.drawable.ic_jz_tp01)
                            .diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(mInitialView.imgLicensePhoto);
        }
        mInitialView.etCompanyCodeValue.setText(userApply.getCompanyCode());// 营业执照号码
    }


}
