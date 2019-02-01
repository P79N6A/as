package com.yaoguang.shipper.activitys.register2;

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
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.shipper.R;
import com.yaoguang.taobao.push.impl.PushManager;

/**
 *
 * Created by zhongjh on 2017/11/30.
 */
public class RegisterDetailActivity extends BaseRegisterDetailActivity<UserOwner> {

    public static void newInstance(Activity activity, UserOwner value, String phone, String password) {
        Intent intent = new Intent(activity, RegisterDetailActivity.class);
        intent.putExtra(DATA, value);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        activity.startActivity(intent);
    }

    @Override
    public UserOwner getModel() {

        if (model == null)
            model = new UserOwner();

        // 手机号
        model.setPhone(getIntent().getStringExtra("phone"));
        // 密码
        model.setPassword(getIntent().getStringExtra("password"));
        //业务类型
        if (mInitialView.cbShipperCompany.isChecked()) {
            model.setType(0);
        } else if (mInitialView.cbShipperUser.isChecked()) {
            model.setType(1);
        }
        model.setName(mInitialView.etNameValue.getText().toString());
        model.setMobile(mInitialView.etValueMobile.getText().toString());

        //判断是否是身份证还是营业执照
        if (mInitialView.cbShipperCompany.isChecked()) {
            // 营业制造url
            model.setLicensePhoto(mLicensePhoto);

            //商户名称
            model.setCompanyName(mInitialView.etValueDepartureTitle.getText().toString());
            // 营业制造号码
            model.setCompanyCode(mInitialView.etCompanyCodeValue.getText().toString());
            //地址
            model.setProvince(provinces);
            model.setCity(citys);
            model.setDistrict(areas);
            model.setCompanyAddress(mInitialView.etValueCompanyAddress.getText().toString());
        } else if (mInitialView.cbShipperUser.isChecked()) {
            // 身份证url
            model.setIdPhoto(mStrIdPhoto1 + "," + mStrIdPhoto2 + "," + mStrIdPhoto3);

            // 身份证号
            model.setIdNumber(mInitialView.etCodeValue.getText().toString());
            // 姓名
            model.setCompanyName(mInitialView.etShipperNameValue.getText().toString());
        }

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
        return Constants.APP_SHIPPER;
    }

    @Override
    protected void customInitUI(InitialView initialView) {
        // 隐藏相关view，显示相关view
        // (1) 选择类型
        initialView.llCompany.setVisibility(View.GONE);
        initialView.llShipper.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData(UserOwner userOwner) {
        // 请选择商户业务类型
        mInitialView.tvStep1.setText("请选择商户的业务类型");

        if (userOwner == null)
            return;

        //业务类型
        if (userOwner.getType() == 0) {
            mInitialView.cbShipperCompany.setChecked(true);
            // 显示和隐藏相关的图片层
            mInitialView.llPhotoCompany.setVisibility(View.VISIBLE);
            mInitialView.llPhotoShipper.setVisibility(View.GONE);

            // 显示和隐藏相关的信息层
            mInitialView.llInformationCompany.setVisibility(View.VISIBLE);
            mInitialView.llInformationShipper.setVisibility(View.GONE);

            mInitialView.cbShipperUser.setChecked(false);
        } else if (userOwner.getType() == 1) {
            mInitialView.cbShipperUser.setChecked(true);
            // 显示和隐藏相关的图片层
            mInitialView.llPhotoCompany.setVisibility(View.GONE);
            mInitialView.llPhotoShipper.setVisibility(View.VISIBLE);

            // 显示和隐藏相关的信息层
            mInitialView.llInformationCompany.setVisibility(View.GONE);
            mInitialView.llInformationShipper.setVisibility(View.VISIBLE);

            mInitialView.cbShipperCompany.setChecked(false);
        }

        mInitialView.etNameValue.setText(userOwner.getName()); // 联系人姓名
        mInitialView.etValueMobile.setText(userOwner.getMobile()); // 联系人电话

        //判断是否是身份证还是营业执照
        if (mInitialView.cbShipperCompany.isChecked()) {
            // 营业制造url
            if (!TextUtils.isEmpty(userOwner.getLicensePhoto())) {
                mLicensePhoto = userOwner.getLicensePhoto();
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

            //商户名称
            mInitialView.etValueDepartureTitle.setText(userOwner.getCompanyName());
            // 营业制造号码
            mInitialView.etCompanyCodeValue.setText(userOwner.getCompanyCode());
            //地址
            provinces = userOwner.getProvince();// 省
            citys = userOwner.getCity();// 市
            areas = userOwner.getDistrict();// 区
            mInitialView.tvAddressValue.setText(provinces + citys + areas);
            mInitialView.etValueCompanyAddress.setText(userOwner.getCompanyAddress());// 道路门牌号
        } else if (mInitialView.cbShipperUser.isChecked()) {
            // 初始化3个身份证url
            if (!TextUtils.isEmpty(userOwner.getIdPhoto())) {
                String[] idPhotos = userOwner.getIdPhoto().split(",");
                if (!TextUtils.isEmpty(idPhotos[0])) {
                    mStrIdPhoto1 = idPhotos[0];
                    mInitialView.llIdPhoto1.setVisibility(View.GONE);
                    mInitialView.flIdPhoto1.setBackgroundResource(0);
                    mInitialView.imgIdPhoto1.setVisibility(View.VISIBLE);
                    mInitialView.imgIdPhoto1.setTag(R.id.tag_glide, mStrIdPhoto1);
                    mStrIdPhoto1 = GlideManager.getImageUrl(mStrIdPhoto1, true);
                    Glide.with(RegisterDetailActivity.this)
                            .load(mStrIdPhoto1)
                            .apply(new RequestOptions()
                                    .centerCrop()
                                    .skipMemoryCache(true)
                                    .error(R.drawable.ic_jz_sb02)
                                    .placeholder(R.drawable.ic_jz_tp02)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE))
                            .into(mInitialView.imgIdPhoto1);
                }
                if (!TextUtils.isEmpty(idPhotos[1])) {
                    mStrIdPhoto2 = idPhotos[1];
                    mInitialView.llIdPhoto2.setVisibility(View.GONE);
                    mInitialView.flIdPhoto2.setBackgroundResource(0);
                    mInitialView.imgIdPhoto2.setVisibility(View.VISIBLE);
                    mInitialView.imgIdPhoto2.setTag(R.id.tag_glide, mStrIdPhoto2);
                    mStrIdPhoto2 = GlideManager.getImageUrl(mStrIdPhoto2, true);
                    Glide.with(RegisterDetailActivity.this)
                            .load(mStrIdPhoto2)
                            .apply(new RequestOptions()
                                    .centerCrop()
                                    .skipMemoryCache(true)
                                    .error(R.drawable.ic_jz_sb02)
                                    .placeholder(R.drawable.ic_jz_tp02)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE))
                            .into(mInitialView.imgIdPhoto2);
                }
                if (!TextUtils.isEmpty(idPhotos[2])) {
                    mStrIdPhoto3 = idPhotos[2];
                    mInitialView.llIdPhoto3.setVisibility(View.GONE);
                    mInitialView.flIdPhoto3.setBackgroundResource(0);
                    mInitialView.imgIdPhoto3.setVisibility(View.VISIBLE);
                    mInitialView.imgIdPhoto3.setTag(R.id.tag_glide, mStrIdPhoto3);
                    mStrIdPhoto3 = GlideManager.getImageUrl(mStrIdPhoto3, true);
                    Glide.with(RegisterDetailActivity.this)
                            .load(mStrIdPhoto3)
                            .apply(new RequestOptions()
                                    .centerCrop()
                                    .skipMemoryCache(true)
                                    .error(R.drawable.ic_jz_sb02)
                                    .placeholder(R.drawable.ic_jz_tp02)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE))
                            .into(mInitialView.imgIdPhoto3);
                }

                // 身份证号
                mInitialView.etCodeValue.setText(userOwner.getIdNumber());
                // 姓名
                mInitialView.etShipperNameValue.setText(userOwner.getCompanyName());

            }


        }

    }


}
