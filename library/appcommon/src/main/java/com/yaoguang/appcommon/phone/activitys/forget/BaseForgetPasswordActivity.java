package com.yaoguang.appcommon.phone.activitys.forget;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.QRCodeBaseActivity;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.interactor.driver.login.forgetpassword.DForgotPasswordPresenterImpl;
import com.yaoguang.interfaces.driver.presenter.DForgotPasswordPresenter;
import com.yaoguang.interfaces.driver.view.DForgetPasswordView;
import com.yaoguang.widget.edittext.PasswordEditText;

/**
 * 忘记密码
 * Created by 韦理英 on 2017/3/31.
 */
public abstract class BaseForgetPasswordActivity extends QRCodeBaseActivity implements DForgetPasswordView {
    DForgotPasswordPresenter mForgotPasswordPresenter = new DForgotPasswordPresenterImpl(this);
    private InitialView mInitialView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        mInitialView = new InitialView();

    }

    @Override
    public void onModitySuccess() {
        // 清除token缓存
        SPUtils.getInstance().put(Constants.TOKEN_ID, "");
        SPUtils.getInstance().put(Constants.TOKEN_TOKEN, "");
        // 清除自动登录
        SPUtils.getInstance().put(Constants.PASSWORD, "");
        SPUtils.getInstance().put(Constants.AUTOLOGIN, false);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected TextView getGetCodeBtn() {
        return mInitialView.btnGetCode;
    }

    public class InitialView {
        private EditText etMobile;
        private EditText actvCode;
        private TextView btnGetCode;
        private PasswordEditText etPassWord;
        private ImageView pwdDisplay;
        private PasswordEditText etPassWord2;
        private ImageView pwdDisplay2;
        private LinearLayout llMobile;
        private AppCompatButton acbSubmit;
        private TextView toolbar_title;
        private ImageView imgReturn;

        public InitialView() {
            initUI();
            initListener();
        }

        public void initUI() {
            this.acbSubmit = (AppCompatButton) findViewById(R.id.acbSubmit);
            this.llMobile = (LinearLayout) findViewById(R.id.llMobile);
            this.etPassWord2 = (PasswordEditText) findViewById(R.id.etPassWord2);
            this.imgReturn = (ImageView) findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) findViewById(R.id.toolbar_title);
            this.etPassWord = (PasswordEditText) findViewById(R.id.etPassWord);
            this.btnGetCode = (TextView) findViewById(R.id.btnGetCode);
            this.actvCode = (EditText) findViewById(R.id.actvCode);
            this.etMobile = (EditText) findViewById(R.id.etMobile);
            toolbar_title.setText("密码重置");
        }


        private void initListener() {
            btnGetCode.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (AppClickUtil.isDuplicateClick()) return;
                    mForgotPasswordPresenter.getVerficationCode(etMobile.getText().toString().trim());
                }
            });
            acbSubmit.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (AppClickUtil.isDuplicateClick()) return;
                    mForgotPasswordPresenter.handleForgotPassword(etMobile.getText().toString().trim(), actvCode.getText().toString().trim()
                            , etPassWord.getText().toString().trim(), etPassWord2.getText().toString().trim());
                }
            });
            imgReturn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    finish();
                }
            });
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toLoginActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract void toLoginActivity();
}

