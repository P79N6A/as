package com.yaoguang.company.my;

import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yaoguang.appcommon.phone.my.modify.modifymail.ModifyMailFragment;
import com.yaoguang.appcommon.phone.my.modify.modifymobile.ModifyMobileFragment;
import com.yaoguang.appcommon.phone.my.modify.modifyqq.ModifyQQFragment;
import com.yaoguang.appcommon.phone.my.modify.modifysign.ModifySignFragment;
import com.yaoguang.company.my.loginconditionconfiguration.LoginCoditionConfigurationFragment;
import com.yaoguang.company.my.usermanagement.list.UserManagementFragment;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.company.R;
import com.yaoguang.company.activitys.login.LoginActivity;
import com.yaoguang.company.my.companyinfo.CompanyInfoFragment;
import com.yaoguang.company.my.modity.moditypass.ModifyPassFragment;
import com.yaoguang.company.my.modity.modityphone.ModifyPhoneFragment;
import com.yaoguang.company.my.setting.SettingFragment;
import com.yaoguang.greendao.entity.AppUserWrapper;

/**
 * 个人中心
 * Created by zhongjh on 2017/7/5.
 */
public class PersonalCenterFragment extends BasePersonalCenterFragment<AppUserWrapper> {

    @Override
    protected String getCodeType() {
        return Constants.APP_COMPANY;
    }

    @Override
    protected void customInit(BasePersonalCenterFragment<AppUserWrapper>.InitialView initialView) {
        //        initialView.rlAssociationManagement.setVisibility(View.GONE);
//        initialView.vAssociationManagement.setVisibility(View.GONE);
    }

    @Override
    protected void customInitListener(BasePersonalCenterFragment<AppUserWrapper>.InitialView initialView) {
//        initialView.rlAssociationManagement.setOnClickListener(v -> start(ContactFragment.newInstance(), SINGLETOP));
        initialView.imgSetting.setOnClickListener(v -> start(SettingFragment.newInstance(), SINGLETOP));
        initialView.rlMerchantManagement.setOnClickListener(v -> start(CompanyInfoFragment.newInstance(initialView.tvMerchantManagementTitle.getText().toString()), SINGLETOP));

        initialView.rlPassWord.setOnClickListener(v -> start(ModifyPassFragment.newInstance(), SINGLETOP));
        initialView.rlMobile.setOnClickListener(v -> start(ModifyPhoneFragment.newInstance(), SINGLETOP));
        initialView.rlPhone.setOnClickListener(v -> startForResult(new ModifyMobileFragment().newInstance(initialView.tvValuePhone.getText().toString()), PersonalCenterFragment.REQUESTCODE_MOBILE));
        initialView.rlEmail.setOnClickListener(v -> startForResult(new ModifyMailFragment().newInstance(initialView.tvValueEmail.getText().toString()), PersonalCenterFragment.REQUESTCODE_EMAIL));
        initialView.rlQQ.setOnClickListener(v -> startForResult(new ModifyQQFragment().newInstance(initialView.tvValueQQ.getText().toString()), PersonalCenterFragment.REQUESTCODE_QQ));
        initialView.llAutograph.setOnClickListener(v -> startForResult(new ModifySignFragment().newInstance(initialView.tvValueAutograph.getText().toString()), PersonalCenterFragment.REQUESTCODE_SIGN));

        // 登录设置
        initialView.rlLoginConfiguration.setOnClickListener(v -> start(LoginCoditionConfigurationFragment.newInstance(), SINGLETOP));
        // 用户设置
        initialView.rlUserManagement.setOnClickListener(v -> start(UserManagementFragment.newInstance(), SINGLETOP));


    }

    @Override
    public void toLoginActivity() {
        LoginActivity.newInstance(getActivity(), true, null, null);
        getActivity().finish();
    }

    public static PersonalCenterFragment newInstance() {
        return new PersonalCenterFragment();
    }

    @Override
    public void showData(AppUserWrapper info) {
        if (info == null)
            return;

        // 判断如果是普通用户就隐藏登录设置和用户设置
        if (info.getUserType() == 3) {
            // 登录设置
            mInitialView.rlLoginConfiguration.setVisibility(View.GONE);
            // 用户设置
            mInitialView.rlUserManagement.setVisibility(View.GONE);
        } else {
            // 登录设置
            mInitialView.rlLoginConfiguration.setVisibility(View.VISIBLE);
            // 用户设置
            mInitialView.rlUserManagement.setVisibility(View.VISIBLE);
        }

        mInitialView.tvValueEmail.setText(ObjectUtils.parseString(info.getEmail()));
        mInitialView.tvValueAutograph.setText(ObjectUtils.parseString(info.getSign()));
        mInitialView.tvValueMobile.setText(ObjectUtils.parseString(info.getMobile()));
        mInitialView.tvValuePhone.setText(ObjectUtils.parseString(info.getPhone()));
        mInitialView.tvValueQQ.setText(ObjectUtils.parseString(info.getQq()));
        mInitialView.tvName.setText(ObjectUtils.parseString(info.getLoginName()));

//        if (TextUtils.isEmpty(info.getName())) {
//            mInitialView.tvName.setText(ObjectUtils.parseString(info.getLoginName()));
//        } else {
//            mInitialView.tvName.setText(ObjectUtils.parseString(info.getName()) + "  |  " + ObjectUtils.parseString(info.getLoginName()));
//        }

        mInitialView.tvTel.setText(ObjectUtils.parseString(info.getCompanyName()));

        if (info.getPhoto() != null) {
            String imageUrl = GlideManager.getImageUrl(info.getPhoto(), true);
            Glide.with(PersonalCenterFragment.this.getContext())
                    .load(imageUrl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())
                            .placeholder(R.drawable.ic_grtouxiang)
                            .error(R.drawable.ic_grtouxiang))
                    .into(mInitialView.ivAvatar);
            mInitialView.ivAvatar.setTag(R.id.tag_glide, imageUrl);
        }
        if (info.getBackgroundPicture() != null) {
            String imageUrl = GlideManager.getImageUrl(info.getBackgroundPicture(), true);
            Glide.with(PersonalCenterFragment.this)
                    .load(imageUrl)
                    .apply(new RequestOptions().centerCrop()
                            .placeholder(R.drawable.ic_grbg)
                            .error(R.drawable.ic_grbg))
                    .into(mInitialView.imgTop);
            mInitialView.imgTop.setTag(R.id.tag_glide, imageUrl);
        }
    }

    public class InitialView extends BasePersonalCenterFragment.InitialView {

        RelativeLayout rlLoginConfiguration;


        public InitialView(View rootView) {
            super(rootView);
        }


    }

}
