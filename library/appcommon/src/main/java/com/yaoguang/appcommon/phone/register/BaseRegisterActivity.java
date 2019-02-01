package com.yaoguang.appcommon.phone.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;


import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 注册:这是个框架界面，里面包含几个步骤的view
 * Created by zhongjh
 * on 2017/7/3.
 */
public abstract class BaseRegisterActivity<T> extends BaseActivity {

    public static final int REQUESTCODE_BRIEF = 0;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public SupportFragment[] mFragments = new SupportFragment[3];

    public InitialView mInitialView;
    private String code;
    /**
     * 当前步骤,0代表入口 1代表详情 2代表完成
     */
    public int node;

    protected abstract SupportFragment getRegisterOneFragment();

    public abstract void toMainActivity();

    public abstract void toLoginActivity();

    public abstract void saveCache();

    public abstract T getCache();

    public abstract void nextPage();

    public abstract void onBack();

    public abstract void initUICustom(InitialView initialView);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_c_and_s);

        mInitialView = new InitialView();
        mInitialView.registerNode.setVisibility(View.GONE);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.down_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //触摸事件
    public interface MyOnActivityResult {
        boolean onActivityResult(Intent data);
    }

    private ArrayList<MyOnActivityResult> myOnActivityResults = new ArrayList<>();

    public void registerMyOnActivityResult(MyOnActivityResult myOnActivityResult) {
        myOnActivityResults.add(myOnActivityResult);
    }

    public void unregisterMyOnActivityResult(MyOnActivityResult myOnActivityResult) {
        myOnActivityResults.remove(myOnActivityResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_BRIEF) {
                for (MyOnActivityResult listener : myOnActivityResults) {
                    if (listener != null) {
                        listener.onActivityResult(data);
                    }
                }
            }
        }
    }

    public void setRegisterNode(int to) {
        node = to;
    }

    public abstract T getModel();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public class InitialView {
        public ImageView registerNode;
        public TextView tvTitle;
        ImageView imgReturn;

        public InitialView() {
            initUI();
            initListener();
            initUICustom(this);
        }

        private void initUI() {
            this.tvTitle = (TextView) findViewById(R.id.toolbar_title);
            this.registerNode = (ImageView) findViewById(R.id.registerNode);
            this.imgReturn = (ImageView) findViewById(R.id.imgReturn);

            tvTitle.setText(getString(R.string.user_register));
            imgReturn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    onBack();
                }
            });
        }

        private void initListener() {
        }

    }

}
