package com.yaoguang.shipper.my;

import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yaoguang.appcommon.phone.my.modify.modifymail.ModifyMailFragment;
import com.yaoguang.appcommon.phone.my.modify.modifymobile.ModifyMobileFragment;
import com.yaoguang.appcommon.phone.my.modify.modifyqq.ModifyQQFragment;
import com.yaoguang.appcommon.phone.my.modify.modifysign.ModifySignFragment;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.activitys.login.LoginActivity;
import com.yaoguang.shipper.my.companyinfo.CompanyInfoFragment;
import com.yaoguang.shipper.my.contact.ContactFragment;
import com.yaoguang.shipper.my.modify.modifypass.ModifyPassFragment;
import com.yaoguang.shipper.my.modify.modifyphone.ModifyPhoneFragment;
import com.yaoguang.shipper.my.setting.SettingFragment;

/**
 * 个人中心
 * Created by zhongjh on 2017/7/5.
 */
public class PersonalCenterFragment extends BasePersonalCenterFragment<UserOwner> {

    @Override
    protected String getCodeType() {
        return Constants.APP_SHIPPER;
    }

    @Override
    protected void customInit(InitialView initialView) {
        initialView.tvMerchantManagementTitle.setText("货主管理");
    }

    @Override
    public void toLoginActivity() {
        LoginActivity.newInstance(getActivity(), true, null, null);
        getActivity().finish();
    }

    @Override
    protected void customInitListener(InitialView initialView) {
        // 关联管理
        initialView.rlAssociationManagement.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(ContactFragment.newInstance());
            }
        });
        initialView.imgSetting.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(SettingFragment.newInstance());
            }
        });
        initialView.rlMerchantManagement.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(CompanyInfoFragment.newInstance(initialView.tvMerchantManagementTitle.getText().toString()));
            }
        });
        initialView.rlPassWord.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(ModifyPassFragment.newInstance());
            }
        });
        initialView.rlMobile.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(ModifyPhoneFragment.newInstance());
            }
        });

        initialView.rlPhone.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                startForResult(new ModifyMobileFragment().newInstance(initialView.tvValuePhone.getText().toString()), PersonalCenterFragment.REQUESTCODE_MOBILE);
            }
        });
        initialView.rlEmail.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                startForResult(new ModifyMailFragment().newInstance( initialView.tvValueEmail.getText().toString()), PersonalCenterFragment.REQUESTCODE_EMAIL);
            }
        });
        initialView.rlQQ.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                startForResult(new ModifyQQFragment().newInstance( initialView.tvValueQQ.getText().toString()), PersonalCenterFragment.REQUESTCODE_QQ);
            }
        });
        initialView.llAutograph.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                startForResult(new ModifySignFragment().newInstance( initialView.tvValueAutograph.getText().toString()), PersonalCenterFragment.REQUESTCODE_SIGN);
            }
        });
    }

    public static PersonalCenterFragment newInstance() {
        return new PersonalCenterFragment();
    }

    @Override
    public void showData(UserOwner info) {
        mInitialView.tvValueEmail.setText(info.getEmail());
        mInitialView.tvValueAutograph.setText(info.getSign());
        //手機就是phone,反了
        mInitialView.tvValueMobile.setText(info.getPhone());
        //固話就是mobile,反了
        mInitialView.tvValuePhone.setText(info.getTel());
        mInitialView.tvValueQQ.setText(info.getQq());

        mInitialView.tvName.setText(ObjectUtils.parseString(info.getName()));
        mInitialView.tvTel.setText(ObjectUtils.parseString(info.getCompanyName()));

        if (info.getHeadPortrait() != null) {
            String imageUrl = GlideManager.getImageUrl(info.getHeadPortrait(), true);
            Glide.with(PersonalCenterFragment.this)
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
                    .apply(new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_grbg)
                            .error(R.drawable.ic_grbg))
                    .into(mInitialView.imgTop);
            mInitialView.imgTop.setTag(R.id.tag_glide, info.getBackgroundPicture());
        }

    }


}
