package com.yaoguang.appcommon.phone.register2.authentication;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.appcommon.phone.register2.LookMoreDialog;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

/**
 * 是否认证
 * Created by zhongjh on 2017/12/1.
 */
public abstract class BaseRegisterAuthenticationActivity extends BaseActivity implements View.OnClickListener {

    public View vTop;
    public ImageView imgIcon;
    public PercentRelativeLayout prlTop;
    public TextView tvTitle;
    public TextView tvContent;
    public TextView tvContentTitle;
    public TextView btnOK;
    public TextView btnClose;
    public PercentRelativeLayout prlMain;
    public TextView tvAll;

    /**
     * 跳转到明细
     */
    protected abstract void toRegisterDetail();

    /**
     * 子类的自定义view
     */
    protected abstract void customInitView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_authentication);
        initView();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private void initView() {
        vTop = findViewById(R.id.vTop);
        vTop.setOnClickListener(this);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        imgIcon.setOnClickListener(this);
        prlTop = (PercentRelativeLayout) findViewById(R.id.prlTop);
        prlTop.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setOnClickListener(this);
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent.setOnClickListener(this);
        tvContentTitle = (TextView) findViewById(R.id.tvContentTitle);
        btnOK = (TextView) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
        btnClose = (TextView) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);
        prlMain = (PercentRelativeLayout) findViewById(R.id.prlMain);
        prlMain.setOnClickListener(this);
        tvAll = (TextView) findViewById(R.id.tvAll);

        tvAll.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                LookMoreDialog dialog = new LookMoreDialog(BaseRegisterAuthenticationActivity.this,tvContent.getText().toString());
                dialog.show();
            }
        });

        tvContent.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        tvContent.getViewTreeObserver().removeOnPreDrawListener(this);

                        // 获取单行的高度
                        int lineHeight = tvContent.getLineHeight();
                        // 获取总高度
                        int allHeight = tvContent.getHeight();
                        // 取整数至少显示的行数，可能也显示半行,例如7.5，取小值
                        int lineCount = allHeight / lineHeight;
                        // 判断当前行数是否小于总行数，如果小于，就显示
                        if (lineCount < (tvContent.getLineCount() - 1)) {
                            tvAll.setVisibility(View.VISIBLE);
                        }

                        return true;
                    }
                }

        );


        customInitView();


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnOK) {
            // 直接跳转到详情
            toRegisterDetail();
            this.finish();
        } else if (i == R.id.btnClose) {
            this.finish();
        }
    }

    // 计算每一个字符的宽度
    public float getCharWidth(TextView textView, char c) {
        textView.setText(String.valueOf(c));
        textView.measure(0, 0);
        return textView.getMeasuredWidth();
    }

}
