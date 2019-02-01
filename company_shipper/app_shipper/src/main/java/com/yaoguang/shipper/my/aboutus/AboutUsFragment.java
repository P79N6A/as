package com.yaoguang.shipper.my.aboutus;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.yaoguang.appcommon.phone.my.aboutus.BaseAboutUsFragment;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.shipper.BuildConfig;
import com.yaoguang.shipper.R;


/**
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
        initialView.viewHolder.imgApp.setImageDrawable(ContextCompat.getDrawable(AboutUsFragment.this.getContext(), R.mipmap.ic_hz_qdtb));
        initialView.viewHolder.tvVer.setText("货云集货主" + initialView.viewHolder.tvVer.getText().toString());
        initialView.viewHolder.tvLog.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                AboutUsDialog dialog = new AboutUsDialog(AboutUsFragment.this.getContext());
                dialog.show();
            }
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
