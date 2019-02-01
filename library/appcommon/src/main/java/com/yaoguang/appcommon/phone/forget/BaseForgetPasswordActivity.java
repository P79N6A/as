//package com.yaoguang.appcommon.phone.forget;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.AppCompatButton;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yaoguang.appcommon.R;
//import com.yaoguang.lib.base.QRCodeBaseActivity;
//import com.yaoguang.appcommon.phone.my.modifypass.forgetpassword.ForgetPasswordContact;
//import com.yaoguang.appcommon.phone.my.modifypass.forgetpassword.ForgetPasswordPresenterImpl;
//import com.yaoguang.widget.edittext.PasswordEditText;
//
///**
// * 忘记密码
// * Created by 韦理英 on 2017/3/31.
// */
//public abstract class BaseForgetPasswordActivity<T> extends QRCodeBaseActivity implements ForgetPasswordContact.ForgetPasswordViewStep2<T> {
//
//    String mMobile;
//
//    /**
//     * 用户类型
//     */
//    protected abstract String getCodeType();
//
//    ForgetPasswordContact.ForgetPasswordPresenter<T> mForgotPasswordPresenter;
//    private InitialView mInitialView;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forgetpassword);
//
//        mMobile = getIntent().getStringExtra("mobile");
//
//        mForgotPasswordPresenter = new ForgetPasswordPresenterImpl<>(null,this, getCodeType());
//        mInitialView = new InitialView();
//    }
//
//    @Override
//    public void onModitySuccessType1() {
//        //忘记密码修改完成之后，所修改密码的手机号就复制到登录界面（前提是登录界面的帐号为空）
//        Intent intent = new Intent();
//        intent.putExtra("mobile",mInitialView.etMobile.getText());
//        setResult(RESULT_OK,intent);
//        finish();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    protected TextView getGetCodeBtn() {
//        return mInitialView.btnGetCode;
//    }
//
//    public class InitialView {
//        private EditText etMobile;
//        private EditText actvCode;
//        private TextView btnGetCode;
//        private PasswordEditText etPassWord;
//        private TextView toolbar_title;
//        private ImageView imgReturn;
//        private PasswordEditText etPassWord2;
//        private LinearLayout llMobile;
//        private TextView acbSubmit;
//
//        public InitialView() {
//            initUI();
//            initListener();
//        }
//
//        private void initListener() {
//            btnGetCode.setOnClickListener(v -> mForgotPasswordPresenter.getVerficationCode(getText(etMobile)));
//            acbSubmit.setOnClickListener(v -> mForgotPasswordPresenter.handleForgotPassword(getText(etMobile), getText(actvCode)
//                    , getText(etPassWord), getText(etPassWord2)));
//            imgReturn.setOnClickListener(v -> finish());
//        }
//
//
//        public void initUI() {
//            this.acbSubmit = (TextView) findViewById(R.id.acbSubmit);
//            this.llMobile = (LinearLayout) findViewById(R.id.llMobile);
//            this.etPassWord2 = (PasswordEditText) findViewById(R.id.etPassWord2);
//            this.imgReturn = (ImageView) findViewById(R.id.imgReturn);
//            this.toolbar_title = (TextView) findViewById(R.id.toolbar_title);
//            this.etPassWord = (PasswordEditText) findViewById(R.id.etPassWord);
//            this.btnGetCode = (TextView) findViewById(R.id.btnGetCode);
//            this.actvCode = (EditText) findViewById(R.id.actvCode);
//            this.etMobile = (EditText) findViewById(R.id.etMobile);
//            etMobile.setText(mMobile);
//            toolbar_title.setText("重置密码");
//        }
//    }
//
//
//}
