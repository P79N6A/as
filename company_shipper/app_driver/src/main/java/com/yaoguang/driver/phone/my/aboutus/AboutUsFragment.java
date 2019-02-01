package com.yaoguang.driver.phone.my.aboutus;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;

import com.yaoguang.driver.BuildConfig;
import com.yaoguang.driver.R;

/**
 * 关于我们
 * Created by wly on 2017/7/6.
 */

public class AboutUsFragment extends BaseAboutUsFragment {

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void customInitialView(InitialView initialView) {
        if (getContext() == null) return;
        initialView.viewHolder.imgApp.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_android_sj));
        initialView.viewHolder.tvAppName.setText("扫码下载货云集司机端");
        initialView.viewHolder.tvVer.setText(initialView.viewHolder.tvVer.getText().toString());
        initialView.viewHolder.tvLog.setOnClickListener(v -> {
            AboutUsDialog dialog = new AboutUsDialog(AboutUsFragment.this.getContext());
            dialog.show();
        });
    }

    @Override
    protected String getBeta() {
        return "Build" + BuildConfig.releaseTime;
    }

    @Override
    protected void startAboutUsDialog() {
        AboutUsDialog dialog = new AboutUsDialog(AboutUsFragment.this.getContext());
        dialog.show();
    }


}
