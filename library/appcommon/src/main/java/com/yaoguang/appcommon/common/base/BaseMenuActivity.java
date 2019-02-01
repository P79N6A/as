package com.yaoguang.appcommon.common.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yaoguang.lib.R;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

/**
 * Created by zhongjh on 2017/3/24.
 * 一个基类
 */

public abstract class BaseMenuActivity extends BaseActivity {
    boolean isMenu = true;

    protected abstract ImageView getMenu();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        initToolbar();

        super.onCreate(savedInstanceState);
    }

    private void initToolbar() {
        getMenu().setImageResource(isMenu ? R.drawable.ic_menu_white_24dp : R.drawable.ic_arrow_back_white_24dp);
        getMenu().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (isMenu) {

                } else {
                    finish();
                }
            }
        });
    }

    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
