//package com.yaoguang.appcommon.phone.forget;
//
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.dd.CircularProgressButton;
//import com.yaoguang.appcommon.R;
//import com.yaoguang.lib.base.BaseActivity;
//import com.yaoguang.appcommon.phone.my.modifypass.forgetpassword.ForgetPasswordContact;
//import com.yaoguang.appcommon.phone.my.modifypass.forgetpassword.ForgetPasswordPresenterImpl;
//import com.yaoguang.widget.edittext.ClearEditText;
//
///**
// * 忘记密码第一步
// * Created by zhongjh on 2017/9/20.
// */
//public abstract class BaseForgetPasswordStep2Activity<T> extends BaseActivity implements ForgetPasswordContact.ForgetPasswordViewStep1<T> {
//
//    //从登录界面传递过来的手机号码
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
//        setContentView(R.layout.activity_forgetpassword_step1);
//
//        mMobile = getIntent().getStringExtra("mobile");
//
//        mForgotPasswordPresenter = new ForgetPasswordPresenterImpl<>(this, null, getCodeType());
//        mInitialView = new InitialView();
//    }
//
//}
