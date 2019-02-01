package com.yaoguang.company.activitys.bindphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.yaoguang.company.activitys.MainActivity;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.CountDownButtonHelper;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.company.R;
import com.yaoguang.lib.common.view.bar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 绑定手机号
 * Created by zhongjh on 2017/8/30.
 */
public class BindPhoneActivity extends BaseActivity implements BindNewPoneContact.View {

    public static final int BIND_PHONE_REQUEST_CODE = 1;

    BindNewPoneContact.Presenter presenter = new BindNewPonePresenter(this);
    CountDownButtonHelper mCountDownButtonHelper = new CountDownButtonHelper();
    InitialView mInitialView;

    private DialogHelper mDialogHelper;

    String mMobile;
    String mId;
    String mPassWord;
    String mUserName;

    public static void newInstance(Activity activity, String mobile, String id, String passWord, String userName) {
        Intent intent = new Intent(activity, BindPhoneActivity.class);
        intent.putExtra("mobile", mobile);
        intent.putExtra("id", id);
        intent.putExtra("passWord", passWord);
        intent.putExtra("userName", userName);
        activity.startActivityForResult(intent, 9999);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bindnewphone);


        // 计算toolbar高度，倾入式后高度变化
        View titleBar = findViewById(com.yaoguang.lib.R.id.toolbar);
        if (titleBar != null)
            ImmersionBar.setTitleBar(BindPhoneActivity.this, titleBar);

        mMobile = getIntent().getStringExtra("mobile");
        mId = getIntent().getStringExtra("id");
        mPassWord = getIntent().getStringExtra("passWord");
        mUserName = getIntent().getStringExtra("userName");
        unbinder = ButterKnife.bind(BindPhoneActivity.this);
        mInitialView = new InitialView();
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
    }

    @Override
    public void startCountDown() {
        mCountDownButtonHelper.start();
    }

    @Override
    public void stopCountDown() {
        mCountDownButtonHelper.stop();
    }

    @Override
    public void moditySuccess(String loginMobile, String strPassword) {
        // 进行登录
        presenter.login(loginMobile, strPassword, getBaseContext());
    }

    @Override
    public void loginSuccess() {
        BindPhoneActivity.this.finish();
        MainActivity.newInstance(BindPhoneActivity.this, null);
    }

    @Override
    public void showBindPhoneTips() {
        // 弹出提示
        if (mDialogHelper == null)
            mDialogHelper = new DialogHelper(BindPhoneActivity.this,
                    "获取验证码失败", "该手机号已被其他帐号绑定。如果继续，原帐号将自动解绑。是否继续?", "确定", "取消", new CommonDialog.Listener() {


                @Override
                public void ok(String content) {
                    presenter.getVerficationCode(Constants.APP_COMPANY, actvUserName.getText().toString(), mId, "1");
                }

                @Override
                public void cancel() {

                }
            });
        mDialogHelper.show();
    }

    @BindView(R.id.imgReturn)
    ImageView imgReturn;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.actvUserName)
    EditText actvUserName;
    @BindView(R.id.actvCode)
    EditText actvCode;
    @BindView(R.id.btnGetCode)
    TextView btnGetCode;
    @BindView(R.id.llMobile)
    LinearLayout llMobile;
    @BindView(R.id.acbSubmit)
    CircularProgressButton acbSubmit;
    Unbinder unbinder;


    public class InitialView {

        public InitialView() {
            initUI();
            initListener();
        }

        public void initUI() {
            initToolbarNav(toolbar, "首次登录身份验证", -1, null);

            if (mMobile.length() == 11 && !mCountDownButtonHelper.isReciprocal()) {
                btnGetCode.setEnabled(true);
            } else {
                btnGetCode.setEnabled(false);
            }


            mCountDownButtonHelper.init(btnGetCode, getString(R.string.get_code), 90, 1);
        }

        private void initListener() {

            //验证码输入判断
            actvCode.addTextChangedListener(new TextWatcher() {
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
            actvUserName.addTextChangedListener(new TextWatcher() {
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
                    checkSubmit();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            // 获取验证码 (逻辑：初次获取验证码的时候，传递参数除了1，如果是106，就是返回疑问信息，让用户确认是否继续，如果继续，就再次调用这个接口，并且传递参数1，意思就是强制修改手机号)
            btnGetCode.setOnClickListener(v -> presenter.getVerficationCode(Constants.APP_COMPANY, actvUserName.getText().toString(), mId, "0"));
            // 提交
            acbSubmit.setOnClickListener(v -> presenter.setNewMobile(Constants.APP_COMPANY, mId, actvUserName.getText().toString(), actvCode.getText().toString(), mPassWord, mUserName));
        }

        private void checkSubmit() {
            //手机号码如果不是11位或者验证码为空时就不能使用按钮
            if (actvUserName.getText().toString().length() != 11 || TextUtils.isEmpty(actvCode.getText().toString())) {
                acbSubmit.setEnabled(false);
            } else {
                acbSubmit.setEnabled(true);
            }
        }

    }


}
