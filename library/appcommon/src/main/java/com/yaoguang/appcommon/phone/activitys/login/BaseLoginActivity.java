package com.yaoguang.appcommon.phone.activitys.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.CircularProgressButton;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yaoguang.appcommon.R;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.widget.edittext.PasswordEditText;

import static com.yaoguang.appcommon.phone.register2.doorway.BaseRegisterDoorwayActivity.REQUEST_REGISTERDOORWAY;

/**
 * Created by zhongjh
 * on 2017/7/6.
 * 登录界面
 */
public abstract class BaseLoginActivity<T, T2> extends BaseLogin implements LoginContact.LoginView<T, T2>, BaseSubmitProgressView {

    public final static String BUNDLE_KEY_MOBILE = "mobile";
    public final static String BUNDLE_KEY_NEWPASS = "newPass";

    protected LoginContact.LoginPresenter mLoginPresenter;

    protected InitialView mInitialView;

    DialogHelper mDialogHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initDataBind(int layoutResID) {

    }

    @Override
    protected void initView() {

        // 清除融云的token
        DataStatic.getInstance().setRongCloudToken("");

        mInitialView = new InitialView();

        // 设置一些参数
        customSetting();
        // 请求权限
        checkPermissions();

        String message = getIntent().getStringExtra("message");
        if (!TextUtils.isEmpty(message)) {
            // 提示重启
            if (mDialogHelper == null)
                mDialogHelper = new DialogHelper(BaseLoginActivity.this, "提示", message, true, new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {

                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelper.show();
        }

//        // 到了登录界面就关闭推送
//        PushManager.getInstance().closePush(new IUmengCallback() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//
//            }
//        }, getApplicationContext());

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        getPresenter().subscribe();
        super.onResume();
    }

    @Override
    protected void onPause() {
        getPresenter().unSubscribe();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //按钮变成默认状态
        setSubmitProgressInit();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 50:
                    //忘记密码修改完成之后，所修改密码的手机号就复制到登录界面（前提是登录界面的帐号为空）
                    if (TextUtils.isEmpty(mInitialView.mACTVUserName.getText()))
                        mInitialView.mACTVUserName.setText(data.getStringExtra("mobile"));
                    break;
                case REQUEST_REGISTERDOORWAY:
                    // 自动登录
                    mInitialView.mACTVUserName.setText(data.getStringExtra("mobile"));
                    mInitialView.mETPassWord.setText(data.getStringExtra("pass"));
                    mInitialView.cpbSubmit.performClick();
                    break;
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void checkPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rxPermissions.request(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE
            ).subscribe(aBoolean -> {
                if (aBoolean) {
                    ULog.i("获取权限成功");
                } else {
                    ULog.i("获取权限失败");
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public String getPhone() {
        return mInitialView.mACTVUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return mInitialView.mETPassWord.getText().toString();
    }

    @Override
    public void showToastUserName(String alert) {
        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), alert, Toast.LENGTH_LONG).show();
//        mInitialView.mACTVUserName.setError(alert);
        mInitialView.mACTVUserName.requestFocus();

        YoYo.with(Techniques.Bounce)
                .duration(700)
                .playOn(mInitialView.mACTVUserName);
    }

    @Override
    public void showToastPassWord(String alert) {
        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), alert, Toast.LENGTH_LONG).show();
//        mInitialView.mETPassWord.setError(alert);
        mInitialView.mETPassWord.requestFocus();
        YoYo.with(Techniques.Bounce)
                .duration(700)
                .playOn(mInitialView.mETPassWord);
    }

    @Override
    public void rememberAccount() {
        String username = SPUtils.getInstance().getString(Constants.USERNAME, "");
        String password = SPUtils.getInstance().getString(Constants.PASSWORD, "");

        if (!TextUtils.isEmpty(username)) {
            mInitialView.mACTVUserName.setText(username);
        }

        if (!TextUtils.isEmpty(password)) {
            mInitialView.mETPassWord.setText(password);
        }

        if (SPUtils.getInstance().getBoolean(Constants.AUTOLOGIN, false)) {
            mInitialView.cpbSubmit.performClick();
        }
    }

    @Override
    public void clearAccount() {
        SPUtils.getInstance().put(Constants.PASSWORD, "");
        SPUtils.getInstance().put(Constants.USERNAME, "");
    }

    @Override
    public void submitProgressSuccess(Bundle bundle) {
        // 跳转首页
        toMainActivityCustom();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public class InitialView {
        //UIx
        public EditText mACTVUserName;
        public PasswordEditText mETPassWord;
        TextView mTVRegister;
        TextView mTVForgetPassword;
        public CircularProgressButton cpbSubmit = (CircularProgressButton) findViewById(R.id.cpbSubmit);

        public InitialView() {
            initUI();
            initListener();
        }

        public void initUI() {
            mACTVUserName = findViewById(R.id.actvUserName);
            mETPassWord = findViewById(R.id.etPassWord);
            mTVRegister = findViewById(R.id.tvRegister);
            mTVForgetPassword = findViewById(R.id.tvForgetPassword);
            mCpbSubmit = cpbSubmit;
            cpbSubmit.setIndeterminateProgressMode(true);
            mCpbSubmitOnClick = view -> clickLoginButton(view);
        }

        private void initListener() {
            cpbSubmit.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    clickLoginButton(v);
                }
            });
            mTVRegister.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    toRegisterActivityCustom();
                }
            });
            mTVForgetPassword.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    toForgetPasswordActivityCustom();
                }
            });
            //登录页上的账户（即手机号码或登录名）输入框被清空后，退出app后重新进入app，账户输入框应被清空
            mACTVUserName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    checkSubmit();
                    if (s.toString().equals("")) {
                        SPUtils.getInstance().put(Constants.USERNAME, "");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            mETPassWord.addTextChangedListener(new TextWatcher() {
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
        }

        private void clickLoginButton(View view) {
            ((LoginContact.LoginPresenter) getPresenter()).loginAuthentication(getPhone(), getPassword(), BaseLoginActivity.this);
        }

    }

    private void checkSubmit() {
        //手机号码如果不是11位或者验证码为空时就不能使用按钮
        if (TextUtils.isEmpty(mInitialView.mACTVUserName.getText().toString()) || TextUtils.isEmpty(mInitialView.mETPassWord.getText().toString())) {
            mInitialView.cpbSubmit.setEnabled(false);
        } else {
            mInitialView.cpbSubmit.setEnabled(true);
        }
    }
}
