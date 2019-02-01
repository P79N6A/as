package com.yaoguang.driver.phone.activitys;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.yaoguang.appcommon.phone.activitys.welcome.BaseGuidanceActivity;
import com.yaoguang.driver.phone.activitys.login.LoginActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.driver.R;

/**
 * Created by 韦理英
 * on 2017/6/8 0008.
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
                getFullScreenView(this, R.drawable.guidance_one),
                getFullScreenView(this, R.drawable.guidance_two),
                getFullScreenView(this, R.drawable.guidance_three)};
    }

    @Override
    protected void toLoginActivity() {
        LoginActivity.newInstance(this, true, null, getIntent().getStringExtra("url"));
        finish();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
