package com.yaoguang.driver.phone.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.yaoguang.driver.phone.activitys.login.LoginActivity;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.driver.R;
import com.yaoguang.driver.phone.main.MainActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 在打开LoginActivity或者MainActivity之前，判断是否需要打开这个节日Activity
 * Created by wly on 2017/12/26.
 */
public class FestivalActivity extends BaseActivity {

    public final static String TYPE_LOGIN = "login";
    public final static String TYPE_MAIN = "main";
    MyHandler myHandler = new MyHandler(this);
    private boolean isSkip = false;// 判断是否已经跳过

    public static void newInstance(Activity activity, String url, String type) {
        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.putExtra("type", type);
        intent.setClass(activity, FestivalActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.alpha, R.anim.alpha);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival);
        String url = getIntent().getStringExtra("url") + "?imageView2/0/h/" + DisplayMetricsSPUtils.getScreenHeight(getApplication()) + "/w/" + DisplayMetricsSPUtils.getScreenWidth(getApplication());
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(FestivalActivity.this)
                .load(url)
                .apply(new RequestOptions()
                        .centerCrop())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageView.setImageDrawable(resource);
                        // 显示
                        findViewById(R.id.imgBackground).setVisibility(View.GONE);
                        findViewById(R.id.flImageView).setVisibility(View.VISIBLE);
                        Timer timer = new Timer();
                        TimerTask tast = new TimerTask() {
                            @Override
                            public void run() {
                                // 跳转
                                myHandler.sendEmptyMessage(0);
                            }
                        };
                        timer.schedule(tast, 3000);
                        return false;
                    }
                }).submit();

        findViewById(R.id.imgSkip).setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                isSkip = true;
                // 判断是跳转到login还是跳转到main
                switch (getIntent().getStringExtra("type")) {
                    case TYPE_LOGIN:
                        LoginActivity.newInstance(FestivalActivity.this, true, null, null);
                        break;
                    case TYPE_MAIN:
                        MainActivity.newInstance(FestivalActivity.this, null);
                        break;
                }
                FestivalActivity.this.finish();
            }
        });


    }


    static class MyHandler extends Handler {
        WeakReference<FestivalActivity> mFestivalActivity;

        MyHandler(FestivalActivity festivalActivity) {
            mFestivalActivity = new WeakReference<>(festivalActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            FestivalActivity festivalActivity = mFestivalActivity.get();
            if (!festivalActivity.isSkip) {
                // 判断是跳转到login还是跳转到main
                switch (festivalActivity.getIntent().getStringExtra("type")) {
                    case TYPE_LOGIN:
                        LoginActivity.newInstance(festivalActivity, true, null, null);
                        break;
                    case TYPE_MAIN:
                        MainActivity.newInstance(festivalActivity, null);
                        break;
                }
                festivalActivity.finish();
            }
        }
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
