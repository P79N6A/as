package com.yaoguang.appcommon.phone.register2.doorway;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.QRCodeBaseActivity;
import com.yaoguang.appcommon.html.HtmlActivity;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.common.CheckPasswordSecurityUtil;
import com.yaoguang.lib.common.RegexValidator;
import com.yaoguang.lib.common.SpanUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.widget.edittext.ClearEditText;
import com.yaoguang.widget.edittext.PasswordEditText;

/**
 * 注册入口界面
 * Created by zhongjh on 2017/11/28.
 */
public abstract class BaseRegisterDoorwayActivity<T> extends QRCodeBaseActivity implements RegisterDoorwayContact.View<T>, BaseSubmitProgressView {

    protected RegisterDoorwayContact.Presenter presenter;
    protected InitialView mInitialView;

    public final static int REQUEST_REGISTERDOORWAY = 1;

    /**
     * 让子类根据自身情况改变view
     */
    public abstract void initUICustom(InitialView initialView);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_doorway;
    }

    @Override
    protected void initView() {
        presenter = new RegisterDoorwayPresenter<>(this);
        mInitialView = new InitialView();
    }

    @Override
    public void initDataBind(int layoutResID) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected TextView getGetCodeBtn() {
        return mInitialView.btnGetCode;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.down_out);
    }

    /**
     * 检查提交按钮
     */
    private void checkCpbSubmit() {
        //文本是否为空，勾选是否未钩
        if (!TextUtils.isEmpty(mInitialView.etMobile.getText().toString()) && !TextUtils.isEmpty(mInitialView.actvCode.getText().toString())
                && !TextUtils.isEmpty(mInitialView.etPassWord.getText().toString())) {
            mInitialView.cpbSubmit.setEnabled(true);
        } else {
            mInitialView.cpbSubmit.setEnabled(false);
        }
    }

    /**
     * 检测密码强度
     */
    void checkPasswordStrength() {
        //判断密码等级
        if (TextUtils.isEmpty(mInitialView.etPassWord.getText().toString())) {
            mInitialView.vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
            mInitialView.vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
            mInitialView.vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
        } else {
            int level = CheckPasswordSecurityUtil.checkSecurity(mInitialView.etPassWord.getText().toString());
            switch (level) {
                case 0:
                    mInitialView.vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mInitialView.vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                    mInitialView.vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                    break;
                case 1:
                    mInitialView.vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mInitialView.vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mInitialView.vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                    break;
                case 2:
                    mInitialView.vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mInitialView.vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mInitialView.vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    break;
            }
        }
    }

    public class InitialView {
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public ClearEditText etMobile;
        public EditText actvCode;
        public TextView btnGetCode;
        public PasswordEditText etPassWord;
        public LinearLayout llMobile;
        public CircularProgressButton cpbSubmit;
        public TextView tvProtocol;
        View vLevel1;
        View vLevel2;
        View vLevel3;

        public InitialView() {
            initUI();
            initListener();
            initUICustom(this);
        }

        private void initUI() {
            this.imgReturn = findViewById(R.id.imgReturn);
            this.toolbar_title = findViewById(R.id.toolbar_title);
            this.toolbar = findViewById(R.id.toolbar);
            this.etMobile = findViewById(R.id.etMobile);
            this.actvCode = findViewById(R.id.actvCode);
            this.btnGetCode = findViewById(R.id.btnGetCode);
            this.etPassWord = findViewById(R.id.etPassWord);
            this.llMobile = findViewById(R.id.llMobile);
            this.cpbSubmit = findViewById(R.id.cpbSubmit);
            this.tvProtocol = findViewById(R.id.tvProtocol);
            this.vLevel1 = findViewById(R.id.vLevel1);
            this.vLevel2 = findViewById(R.id.vLevel2);
            this.vLevel3 = findViewById(R.id.vLevel3);

            this.btnGetCode.setEnabled(false);
            mCpbSubmit = cpbSubmit;
            mCpbSubmit.setIndeterminateProgressMode(true);
            tvProtocol.setText(new SpanUtils()
                    .append("注册代表已了解并同意 ").setForegroundColor(ContextCompat.getColor(getApplication(), R.color.gray))
                    .append("《用户协议》").setForegroundColor(ContextCompat.getColor(getApplication(), R.color.orange500))
                    .create());
        }

        private void initListener() {
            toolbar_title.setText(getString(R.string.user_register));
            imgReturn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    finish();
                }
            });
            // 点击注册
            mCpbSubmitOnClick = new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    // 进行注册第一步
                    presenter.handleOneAuth(mInitialView.etMobile.getText().toString().trim(),
                            mInitialView.etPassWord.getText().toString().trim(),
                            mInitialView.actvCode.getText().toString(),
                            true);
                    //隐藏软键盘
                    hideKeyboard();
                }
            };
            cpbSubmit.setOnClickListener(mCpbSubmitOnClick);

            // 获取验证码
            btnGetCode.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (TextUtils.isEmpty(etMobile.getText().toString())) {
                        showToast("请输入手机号码");
                        stopShowCountdown();
                        return;
                    }
                    if (!RegexValidator.isMobile(etMobile.getText().toString())) {
                        showToast("手机格式错误");
                        stopShowCountdown();
                        return;
                    }
                    presenter.getVerficationCode(etMobile.getText().toString());
                }
            });
            // 用户协议
            tvProtocol.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    HtmlActivity.newInstance(BaseRegisterDoorwayActivity.this, HtmlActivity.class, 1, "用户协议", BuildConfig.ENDPOINT + "page/user_agreement.jsp");
                }
            });

            // 手机改变值时
            etMobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 11 && !mCountDownButtonHelper.isReciprocal()) {
                        btnGetCode.setEnabled(true);
                    } else {
                        btnGetCode.setEnabled(false);
                    }
                    checkCpbSubmit();
                    checkPasswordStrength();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            // 验证码改变值时
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

            // 密码改变值时
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

            // 选择用户协议时
//            cbProtocol.setOnCheckedChangeListener((compoundButton, isChecked) -> {
//                if (isChecked) {
//                    checkCpbSubmit();
//                }
//            });
        }
    }

}
