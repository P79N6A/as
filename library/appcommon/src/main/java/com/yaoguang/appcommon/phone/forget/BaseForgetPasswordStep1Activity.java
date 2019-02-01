package com.yaoguang.appcommon.phone.forget;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.QRCodeBaseActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.widget.edittext.ClearEditText;

/**
 * 忘记密码第一步
 * Created by zhongjh on 2017/9/20.
 */
public abstract class BaseForgetPasswordStep1Activity<T> extends QRCodeBaseActivity implements ForgetPasswordContact.ForgetPasswordViewStep1<T> {

    /**
     * 用户类型
     */
    protected abstract String getCodeType();

    //从登录界面传递过来的手机号码
    protected String mMobile;

    ForgetPasswordContact.ForgetPasswordPresenter<T> mForgotPasswordPresenter;
    protected InitialView mInitialView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpassword_step1;
    }

    @Override
    protected void initView() {
        mMobile = getIntent().getStringExtra("mobile");

        mForgotPasswordPresenter = new ForgetPasswordPresenterImpl<>(this, null, getCodeType());
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
        return mForgotPasswordPresenter;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.down_out);
    }

    @Override
    protected TextView getGetCodeBtn() {
        return mInitialView.mViewHolder.btnGetCode;
    }

    @Override
    public void setSubmitProgressInit() {
        mInitialView.mViewHolder.cpbSubmit.setProgress(0);
    }

    @Override
    public void setSubmitProgressOK() {
        mInitialView.mViewHolder.cpbSubmit.setProgress(100);
    }

    @Override
    public void setSubmitProgressLoading() {
        mInitialView.mViewHolder.cpbSubmit.setProgress(50);
    }

    @Override
    public void setSubmitProgressError() {
        mInitialView.mViewHolder.cpbSubmit.setProgress(-1);
    }

    @Override
    public void fail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public class InitialView {

        public ViewHolder mViewHolder;

        public InitialView() {
            initUI();
            initListener();
        }

        public void initUI() {
            mViewHolder = new ViewHolder();

            mViewHolder.etMobile.setText(mMobile);
            // 当手机号符合时 并且 不是正在倒数中
            if (mMobile.length() == 11 && !mCountDownButtonHelper.isReciprocal()) {
                mViewHolder.btnGetCode.setEnabled(true);
            } else {
                mViewHolder.btnGetCode.setEnabled(false);
            }
            mViewHolder.toolbar_title.setText("重置密码");
            mViewHolder.cpbSubmit.setIndeterminateProgressMode(true);
        }

        private void initListener() {
            //验证码输入判断
            mViewHolder.actvCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    checkSubmit();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            //手机号码输入判断
            mViewHolder.etMobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 11 && !mCountDownButtonHelper.isReciprocal()) {
                        mViewHolder.btnGetCode.setEnabled(true);
                    } else {
                        mViewHolder.btnGetCode.setEnabled(false);
                    }
                    checkSubmit();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            mViewHolder.imgReturn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    finish();
                }
            });
            mViewHolder.btnGetCode.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    mForgotPasswordPresenter.getVerficationCode(mViewHolder.etMobile.getText().toString().trim());
                }
            });
            mViewHolder.cpbSubmit.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    mForgotPasswordPresenter.checkAuthCode(mViewHolder.etMobile.getText().toString(), mViewHolder.actvCode.getText().toString());
                }
            });
        }

    }

    private void checkSubmit() {
        //手机号码如果不是11位或者验证码为空时就不能使用按钮
        if (mInitialView.mViewHolder.etMobile.getText().toString().length() != 11 || TextUtils.isEmpty(mInitialView.mViewHolder.actvCode.getText().toString())) {
            mInitialView.mViewHolder.cpbSubmit.setEnabled(false);
        } else {
            mInitialView.mViewHolder.cpbSubmit.setEnabled(true);
        }
    }

    public class ViewHolder {
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public ClearEditText etMobile;
        public EditText actvCode;
        public TextView btnGetCode;
        public CircularProgressButton cpbSubmit;
        public LinearLayout llMobile;

        public ViewHolder() {
            this.imgReturn = findViewById(R.id.imgReturn);
            this.toolbar_title = findViewById(R.id.toolbar_title);
            this.toolbar = findViewById(R.id.toolbar);
            this.etMobile = findViewById(R.id.etMobile);
            this.actvCode = findViewById(R.id.actvCode);
            this.btnGetCode = findViewById(R.id.btnGetCode);
            this.cpbSubmit = findViewById(R.id.cpbSubmit);
            this.llMobile = findViewById(R.id.llMobile);
        }

    }
}
