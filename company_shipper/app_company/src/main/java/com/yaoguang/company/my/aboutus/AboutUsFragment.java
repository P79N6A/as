package com.yaoguang.company.my.aboutus;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.yaoguang.appcommon.phone.my.aboutus.BaseAboutUsFragment;
import com.yaoguang.company.BuildConfig;
import com.yaoguang.company.R;


/**
 * 关于货云集
 * Created by zhongjh on 2017/7/6.
 */
public class AboutUsFragment extends BaseAboutUsFragment {

    public static AboutUsFragment newInstance(boolean goSetting) {
        AboutUsFragment aboutUsFragment = new AboutUsFragment();
        Bundle bundle = new Bundle();
        aboutUsFragment.setArguments(bundle);
        return aboutUsFragment;
    }

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
    }


    @Override
    protected void customInitialView(InitialView initialView) {
        initialView.viewHolder.imgApp.setImageDrawable(ContextCompat.getDrawable(AboutUsFragment.this.getContext(), R.mipmap.ic_wl_qdtb));
        initialView.viewHolder.tvVer.setText("货云集物流" + initialView.viewHolder.tvVer.getText().toString());
        initialView.viewHolder.tvLog.setOnClickListener(v -> {
            AboutUsDialog dialog = new AboutUsDialog(AboutUsFragment.this.getContext());
            dialog.show();
        });
    }

    @Override
    protected void startAboutUsDialog() {
        AboutUsDialog dialog = new AboutUsDialog(AboutUsFragment.this.getContext());
        dialog.show();
    }

    @Override
    protected String getBeta() {
        return "Build" + BuildConfig.releaseTime;
    }


}
