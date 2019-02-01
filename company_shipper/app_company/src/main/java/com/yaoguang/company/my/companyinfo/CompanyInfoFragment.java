package com.yaoguang.company.my.companyinfo;

import android.annotation.SuppressLint;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yaoguang.appcommon.phone.my.companyinfo.BaseCompanyInfoFragment;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.UserApply;

/**
 * 商户管理
 * Created by zhongjh on 2017/7/13.
 */
@SuppressLint("ValidFragment")
public class CompanyInfoFragment extends BaseCompanyInfoFragment<UserApply> {

    public CompanyInfoFragment(String title) {
        super(title);
    }

    public static CompanyInfoFragment newInstance(String title) {
        return new CompanyInfoFragment(title);
    }

    @Override
    protected String getCodeType() {
        return Constants.APP_COMPANY;
    }

    @Override
    protected void initListener(ViewHolder viewHolder) {

    }

    @Override
    public void showData(UserApply info) {
        mInitialView.viewHolder.tvValueAddress.setText(info.getProvince() + info.getCity() + info.getDistrict() + info.getCompanyAddress());
        mInitialView.viewHolder.tvValueUser.setText(info.getName());
        mInitialView.viewHolder.tvValuePhone.setText(info.getMobile());
        mInitialView.viewHolder.tvValueRemark.setText(info.getShopDetail());
        mInitialView.viewHolder.tvCompanyName.setText(info.getCompanyName());

        //        mInitialView.viewHolder.rlCodePhoto.setTag(info.getLicensePhoto());
        mInitialView.viewHolder.rlCodePhoto.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                // 跳转商户认证信息
                start(CompanyInfoDetailFragment.newInstance(info));
            }
        });

        String imageUrl = GlideManager.getImageUrl(info.getShopPhoto(), true);
        Glide.with(CompanyInfoFragment.this.getContext())
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_mrbanner)
                        .error(R.drawable.ic_mrbanner))
                .into(mInitialView.viewHolder.imgPhoto);
        mInitialView.viewHolder.imgPhoto.setTag(R.id.tag_glide, imageUrl);

        imageUrl = GlideManager.getImageUrl(info.getShopLogo(), true);
        Glide.with(CompanyInfoFragment.this.getContext())
                .load(imageUrl)
                .apply(RequestOptions
                        .bitmapTransform(new CircleCrop())
                        .placeholder(R.drawable.ic_qymrtx)
                        .error(R.drawable.ic_qymrtx))
         .into(mInitialView.viewHolder.imgLog);
        mInitialView.viewHolder.imgLog.setTag(R.id.tag_glide, imageUrl);

        //判断类型
        if (info.getApplyType() == 0) {
            mInitialView.viewHolder.btnType0.setVisibility(View.VISIBLE);
        } else if (info.getApplyType() == 1) {
            mInitialView.viewHolder.btnType1.setVisibility(View.VISIBLE);
        } else if (info.getApplyType() == 2) {
            mInitialView.viewHolder.btnType0.setVisibility(View.VISIBLE);
            mInitialView.viewHolder.btnType1.setVisibility(View.VISIBLE);
        } else if (info.getApplyType() == 3) {
            mInitialView.viewHolder.btnType0.setVisibility(View.GONE);
            mInitialView.viewHolder.btnType1.setVisibility(View.GONE);
        }
    }


}
