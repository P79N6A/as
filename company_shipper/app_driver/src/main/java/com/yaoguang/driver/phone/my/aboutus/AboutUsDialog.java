package com.yaoguang.driver.phone.my.aboutus;

import android.content.Context;

import com.yaoguang.appcommon.phone.my.aboutus.BaseAboutUsDialog;

/**
 * 关于我们弹窗
 * Created by wly on 2017/7/12.
 */
public class AboutUsDialog extends BaseAboutUsDialog {

    AboutUsDialog(Context context) {
        super(context);
    }

    @Override
    protected void customCreate() {
        viewHolder.tvContent.setText("1.修复细节同一部分bug\n2.添加更新app的提示");
    }
}