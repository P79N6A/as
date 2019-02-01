package com.yaoguang.appcommon.common.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.R;
import com.yaoguang.lib.base.BaseActivity2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.map.common.ToastUtil;

/**
 * Created by 韦理英
 * on 2017/6/13 0013.
 */

public class LookPhotoActivity extends BaseActivity2 {

    private static final String IMAGE_URL = "image_url";
    private static final String FLAG_TITLE = "flag_title";
    private static final String ISNONECACHE = "isNoneCache";
    private ImageView imgReturn;
    private Toolbar toolbar;
    private PhotoView photoView;
    private TextView tvLookArtwork;
    private TextView toolbarTitle;

    String value;
    boolean isNoneCache;

    public static void newInstance(Activity activity, String url) {
        newInstance(activity, false, null, url);
    }

    public static void newInstance(Activity activity, String url, boolean isNoneCache) {
        newInstance(activity, isNoneCache, null, url);
    }

    public static void newInstance(Activity activity, String title, String url) {
        newInstance(activity, false, title, url);
    }

    public static void newInstance(Activity activity, boolean isNoneCache, String title, String url) {
        if (TextUtils.isEmpty(url)) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "暂无图片");
            return;
        }
        Intent intent = new Intent(activity, LookPhotoActivity.class);
        intent.putExtra(IMAGE_URL, url);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(FLAG_TITLE, title);
        }
        intent.putExtra(ISNONECACHE, isNoneCache);
        activity.startActivity(intent);
    }

    public void initView() {

        imgReturn = findViewById(R.id.imgReturn);
        toolbar = findViewById(R.id.toolbar);
        photoView = findViewById(R.id.photoView);
        tvLookArtwork = findViewById(R.id.tvLookArtwork);
        toolbarTitle = findViewById(R.id.toolbar_title);

        value = getIntent().getStringExtra(IMAGE_URL);
        isNoneCache = getIntent().getBooleanExtra(ISNONECACHE, false);

        // 默认显示压缩图片
        if (isNoneCache) {
            GlideManager.getInstance().withNoneCache(this, value, photoView, R.drawable.ic_ic_tpjzz_b, R.drawable.ic_jzsb_01, true);
        } else {
            GlideManager.getInstance().withSquare(this, value, photoView, R.drawable.ic_ic_tpjzz_b, R.drawable.ic_jzsb_01, true);
        }

        // 标题
        toolbar.setBackgroundColor(UiUtils.getColor(R.color.transparent));
        toolbarTitle.setText("查看图片");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.photoview;
    }

    @Override
    protected void initListener() {

        //  下载原图
        tvLookArtwork.setOnClickListener(v -> {
            GlideManager.getInstance().withArtwork(LookPhotoActivity.this, value, photoView);
            tvLookArtwork.setVisibility(View.GONE);
        });

        //  返回
        imgReturn.setOnClickListener(v -> finish());
    }

    @Override
    public void initDataBind(int layoutResID) {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }


}
