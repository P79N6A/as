package com.yaoguang.shipper.my.aboutus;

import android.content.Context;

import com.yaoguang.appcommon.phone.my.aboutus.BaseAboutUsDialog;

/**
 * Created by zhongjh on 2017/7/12.
 */
public class AboutUsDialog extends BaseAboutUsDialog {

    public AboutUsDialog(Context context) {
        super(context);
    }

    @Override
    protected void customCreate() {
        viewHolder.tvContent.setText("1.修复细节同一部分bug");
    }
}