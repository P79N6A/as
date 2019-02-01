package com.yaoguang.shipper.activitys;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.yaoguang.appcommon.phone.activitys.welcome.BaseGuidanceActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.activitys.login.LoginActivity;

/**
 * Created by 韦理英
 * on 2017/6/8 0008.
 * Update by zhongjh：添加跳转广告的操作
 * on 2017/12/26
 */

public class GuidanceActivity extends BaseGuidanceActivity {

    protected static void newInstance(Activity activity, String url) {
        Intent intent = new Intent(activity, GuidanceActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected View[] getViewList() {
        return new View[]{
                getFullScreenView(this, R.drawable.ic_hz_ydyy),
                getFullScreenView(this, R.drawable.ic_hz_ydye),
                getFullScreenView(this, R.drawable.ic_hz_ydys)};
    }

    @Override
    protected void toLoginActivity() {
        LoginActivity.newInstance(this, true, null, getIntent().getStringExtra("url"));
        this.finish();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
