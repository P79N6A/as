package com.yaoguang.driver.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseSubmitProgressActivity;
import com.yaoguang.appcommon.html.HtmlActivity;
import com.yaoguang.common.BuildConfig;
import com.yaoguang.common.appcommon.utils.CountDownButtonHelper;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.common.common.CheckPasswordSecurityUtil;
import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.SpanUtils;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.driver.activitys.LoginActivity;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.widget.edittext.ClearEditText;
import com.yaoguang.widget.edittext.PasswordEditText;


/**
 * 第一步的注册界面
 * Created by wly
 * on 2017/7/3 0015.
 */
public class RegisterActivity<T> extends BaseSubmitProgressActivity implements RegisterContact.RegisterView<T>, BaseSubmitProgressView {
    protected RegisterContact.RegisterPresenter presenter;
    InitialView mInitialView;

    protected String getCodeType() {
        return Constants.APP_DRIVER;
    }

    protected void clickBtnRegister() {
        presenter.handleOneAuth(mInitialView.getMobile(),
                UiUtils.getText(mInitialView.etPassWord),
                mInitialView.actvCode.getText().toString(),
                mInitialView.cbProtocol.isChecked());
    }

    @Override
    public void customSetting() {
        mInitialView.cbProtocol.setChecked(false);
        mInitialView.cpbSubmit.setText("注册");
    }


    @Override
    public void submitProgressSuccess() {
        showToast("注册成功");
        LoginActivity.newInstance(this, true, null, null);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register1);
        presenter = new RegisterPresenter(this, getApplicationContext()
                , Injection.provideDriverRepository(getApplicationContext())
                , Injection.provideMessageRepository(getApplicationContext()));
        mInitialView = new InitialView();
        setCpbSubmit(mInitialView.cpbSubmit);
        customSetting();
    }


    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    protected TextView getGetCodeBtn() {
        return mInitialView.btnGetCode;
    }

    /**
     * 检查提交按钮
     */
    private void checkCpbSubmit() {
        //文本是否为空，勾选是否未钩
        if (!TextUtils.isEmpty(mInitialView.etMobile.getText().toString()) && !TextUtils.isEmpty(mInitialView.actvCode.getText().toString())
                && !TextUtils.isEmpty(mInitialView.etPassWord.getText().toString()) && mInitialView.cbProtocol.isChecked()) {
            mInitialView.cpbSubmit.setEnabled(true);
        } else {
            mInitialView.cpbSubmit.setEnabled(false);
        }
    }

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, RegisterActivity.class));
    }

    public class InitialView {
        ClearEditText etMobile;
        PasswordEditText etPassWord;
        EditText actvCode;
        TextView btnGetCode;
        LinearLayout llMobile;
        CircularProgressButton cpbSubmit;
        CheckBox cbProtocol;
        TextView tvProtocol;
        TextView toolbarTitle;
        View vLevel1;
        View vLevel2;
        View vLevel3;

        public InitialView() {
            initUI();
        }

        private void initUI() {
            etMobile = findViewById(R.id.etMobile);
            toolbarTitle = findViewById(R.id.toolbar_title);
            tvProtocol = findViewById(R.id.tvProtocol);
            cbProtocol = findViewById(R.id.cbProtocol);
            cpbSubmit = findViewById(R.id.cpbSubmit);
            llMobile = findViewById(R.id.llMobile);
            btnGetCode = findViewById(R.id.btnGetCode);
            actvCode = findViewById(R.id.actvCode);
            etPassWord = findViewById(R.id.etPassWord);
            vLevel1 = findViewById(R.id.vLevel1);
            vLevel2 = findViewById(R.id.vLevel2);
            vLevel3 = findViewById(R.id.vLevel3);
            btnGetCode.setEnabled(false);

            toolbarTitle.setText("注册");
            findViewById(R.id.imgReturn).setOnClickListener(v -> {
                LoginActivity.newInstance(RegisterActivity.this, true, null, null);
                finish();
            });
            mCpbSubmitOnClick = new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    clickBtnRegister();
                    //隐藏软键盘
                    hideKeyboard();
                }
            };
            btnGetCode.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    presenter.getVerificationCode(getMobile());
                }
            });
            cpbSubmit.setOnClickListener(mCpbSubmitOnClick);
            tvProtocol.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    HtmlActivity.newInstance(RegisterActivity.this, HtmlActivity.class, 1, "用户协议", BuildConfig.ENDPOINT + "page/user_agreement.jsp");
                }
            });
            etMobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 11) {
                        btnGetCode.setEnabled(true);
                    } else {
                        btnGetCode.setEnabled(false);
                    }
                    checkCpbSubmit();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            actvCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    checkCpbSubmit();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            etPassWord.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    checkCpbSubmit();
                    checkPasswordStrength();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            cbProtocol.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    checkCpbSubmit();
                }
            });

//            tvProtocol.setOnClickListener(v -> start(HtmlFragment.newInstance("用户协议", Api.ENDPOINT + "page/user_agreement.jsp")));

            tvProtocol.setText(new SpanUtils()
                    .append("我已了解并同意 ").setForegroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray))
                    .append("《用户协议》").setForegroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange500))
                    .create());
        }

        public String getMobile() {
            return etMobile.getText().toString();
        }


        /**
         * 检测密码强度
         */
        void checkPasswordStrength() {
            //判断密码等级
            if (TextUtils.isEmpty(etPassWord.getText().toString())) {
                vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
            } else {
                int level = CheckPasswordSecurityUtil.checkSecurity(etPassWord.getText().toString());
                switch (level) {
                    case 0:
                        vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                        vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                        vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                        break;
                    case 1:
                        vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                        vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                        vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                        break;
                    case 2:
                        vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                        vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                        vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                        break;
                }
            }
        }
    }

    private CountDownButtonHelper mCountDownButtonHelper;

    public void startShowCountdown() {
        mCountDownButtonHelper = new CountDownButtonHelper(getGetCodeBtn(), getString(com.yaoguang.common.R.string.get_code), 90, 1);
        mCountDownButtonHelper.start();
    }

    public void stopShowCountdown() {
        if (mCountDownButtonHelper != null) {
            mCountDownButtonHelper.stop();
        }
    }
}
