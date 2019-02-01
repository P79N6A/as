package com.yaoguang.appcommon.phone.my.modifypass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.phone.activitys.login.BaseLoginActivity;
import com.yaoguang.appcommon.common.base.BaseSubmitProgressFragment;
import com.yaoguang.appcommon.phone.forget.ForgetPasswordContact;
import com.yaoguang.appcommon.phone.forget.ForgetPasswordPresenterImpl;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.CheckPasswordSecurityUtil;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterContact;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.CPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.DPersonalCenterInteractorImpl;
import com.yaoguang.appcommon.phone.my.presonalcenter.interactorl.SPersonalCenterInteractorImpl;
import com.yaoguang.widget.edittext.PasswordEditText;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 修改密码第二步
 * 目前分3种界面：
 * 0：在进入首页后，直接修改密码，并且提示确定更换密码并退出账户?
 * 1：在登录页面修改密码，提示是否确定更换密码
 * 2：在登录时，如果发现密码是默认的（目前只有物流用到），那么需要强制修改密码
 * Created by zhongjh on 2017/9/18.
 */
public abstract class BaseModifyPass2Fragment extends BaseSubmitProgressFragment implements ForgetPasswordContact.ForgetPasswordViewStep2 {

    public final static String BUNDLE_KEY_ID = "id";
    public final static String BUNDLE_KEY_PASSWORD = "password";
    public final static String BUNDLE_KEY_TYPE = "type";
    public final static String BUNDLE_KEY_MOBILE = "mobile";
    public final static String BUNDLE_KEY_AUTOCODE = "autoCode";

    String mId; // 看id
    int mType; // 看顶部的3种界面解释
    String mPassword;//密码
    String mNewPass;// 修改后的新密码
    String mMobile;//从上一个界面传递过来的手机
    String mAutoCode; //验证码

    ViewHolder mViewHolder;

    CompositeDisposable mCompositeDisposable;
    public CommonDialog mCommonDialog;

    /**
     * 获取类型
     */
    public abstract String getCodeType();

    PersonalCenterContact.PersonalCenterInteractor mInteractor;
    ForgetPasswordContact.ForgetPasswordPresenter mForgotPasswordPresenter;

    /**
     * 跳转登录
     */
    protected abstract void toLoginActivity();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getString(BUNDLE_KEY_ID);
            mPassword = bundle.getString(BUNDLE_KEY_PASSWORD);
            mType = bundle.getInt(BUNDLE_KEY_TYPE);
            mMobile = bundle.getString(BUNDLE_KEY_MOBILE);
            mAutoCode = bundle.getString(BUNDLE_KEY_AUTOCODE);
        }
        mForgotPasswordPresenter = new ForgetPasswordPresenterImpl(null, BaseModifyPass2Fragment.this, getCodeType());
        switch (getCodeType()) {
            case Constants.APP_COMPANY:
                mInteractor = new CPersonalCenterInteractorImpl();
                break;
            case Constants.APP_SHIPPER:
                mInteractor = new SPersonalCenterInteractorImpl();
                break;
            case Constants.APP_DRIVER:
                mInteractor = new DPersonalCenterInteractorImpl();
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moditypassword2, null);
        mCompositeDisposable = new CompositeDisposable();

        mViewHolder = new ViewHolder(view);
        setCpbSubmit(mViewHolder.cpbSubmit);

        switch (mType) {
            case 0:
                mViewHolder.toolbar_title.setText("修改密码");
                mCommonDialog = new CommonDialog(getContext(), "确定更换密码并退出账户?");
                mViewHolder.llTips.setVisibility(View.VISIBLE);
                break;
            case 1:
                mViewHolder.toolbar_title.setText("密码重置");
                mCommonDialog = new CommonDialog(getContext(), "确定更换密码?");
                mViewHolder.llTips.setVisibility(View.GONE);
                break;
            case 2:
                mViewHolder.toolbar_title.setText("修改密码");
                mCommonDialog = new CommonDialog(getContext(), "确定更换密码?");
                mViewHolder.llTips.setVisibility(View.GONE);
                break;
        }


        mViewHolder.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                switch (mType) {
                    case 0:
                        pop();
                        break;
                    case 1:
                        _mActivity.finish();
                        break;
                    case 2:
                        _mActivity.finish();
                        break;
                }
            }
        });
        //密码1和2判断
        mViewHolder.etPassWord.addTextChangedListener(new TextWatcher() {
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
        mViewHolder.etPassWord2.addTextChangedListener(new TextWatcher() {
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

        // 提交
        mViewHolder.cpbSubmit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String newPass = mViewHolder.etPassWord.getText().toString();
                String oldPass = mPassword;
                if (newPass.length() < 6 || newPass.length() > 12 || mViewHolder.etPassWord2.getText().toString().length() < 6 || mViewHolder.etPassWord2.getText().toString().length() > 12) {
                    showToast("请输入6～12位字母、数字或符号");
                    return;
                } else if (!newPass.equals(mViewHolder.etPassWord2.getText().toString())) {
                    showToast("两次输入的密码不一致");
                    return;
                } else if (newPass.equals(oldPass)) {
                    showToast("新密码不能与旧密码相同");
                    return;
                }

                switch (mType) {
                    case 0:
                        mForgotPasswordPresenter.modifyLoginPasswordType0(newPass, oldPass);
                        break;
                    case 1:
                        mForgotPasswordPresenter.modifyLoginPasswordType1(mMobile, mViewHolder.etPassWord.getText().toString(), mAutoCode);
                        break;
                    case 2:
                        mForgotPasswordPresenter.modifyLoginPasswordType2(mId, mMobile, newPass, oldPass);
                        break;
                }
            }
        });

        return view;
    }

    private void checkSubmit() {
        //如果有其中一个密码为空就不能点击
        if (TextUtils.isEmpty(mViewHolder.etPassWord.getText().toString()) || TextUtils.isEmpty(mViewHolder.etPassWord2.getText().toString())) {
            mViewHolder.cpbSubmit.setEnabled(false);
        } else {
            mViewHolder.cpbSubmit.setEnabled(true);
        }
        //判断密码等级
        if (TextUtils.isEmpty(mViewHolder.etPassWord.getText().toString())) {
            mViewHolder.vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
            mViewHolder.vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
            mViewHolder.vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
        } else {
            int level = CheckPasswordSecurityUtil.checkSecurity(mViewHolder.etPassWord.getText().toString());
            switch (level) {
                case 0:
                    mViewHolder.vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mViewHolder.vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                    mViewHolder.vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                    break;
                case 1:
                    mViewHolder.vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mViewHolder.vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mViewHolder.vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_null));
                    break;
                case 2:
                    mViewHolder.vLevel1.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mViewHolder.vLevel2.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    mViewHolder.vLevel3.setBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.level_full));
                    break;
            }
        }
    }

    @Override
    public void onModitySuccessType0() {
        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(tast, 1200);
    }

    @Override
    public void onModitySuccessType1() {
        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(1);
            }
        };
        timer.schedule(tast, 1200);
    }

    @Override
    public void onModitySuccessType2(String newPass) {
        mNewPass = newPass;
        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(2);
            }
        };
        timer.schedule(tast, 1200);
    }

    @Override
    public void fail(String message) {
        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    MyHandlerType0 myHandler = new MyHandlerType0(this);

    static class MyHandlerType0 extends Handler {
        WeakReference<BaseModifyPass2Fragment> mBaseModifyPass2Fragment;

        MyHandlerType0(BaseModifyPass2Fragment baseModifyPass2Fragment) {
            mBaseModifyPass2Fragment = new WeakReference<>(baseModifyPass2Fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseModifyPass2Fragment baseModifyPass2Fragment = mBaseModifyPass2Fragment.get();
//            msg.arg1
            if (msg.what == 0) {
                baseModifyPass2Fragment.mCommonDialog.dismiss();
                baseModifyPass2Fragment.showToast("修改密码成功，需要重新登录");
                SPUtils.getInstance().put(Constants.PASSWORD, "");
                SPUtils.getInstance().put(Constants.AUTOLOGIN, false);
                baseModifyPass2Fragment.toLoginActivity();
            } else if (msg.what == 1) {
                // 忘记密码修改完成之后，所修改密码的手机号就复制到登录界面（前提是登录界面的帐号为空）
                Bundle bundle = new Bundle();
                bundle.putString(BUNDLE_KEY_MOBILE, baseModifyPass2Fragment.mMobile);
                baseModifyPass2Fragment.setFragmentResult(RESULT_OK, bundle);
                baseModifyPass2Fragment._mActivity.finish();
            } else if (msg.what == 2) {
                baseModifyPass2Fragment.mCommonDialog.dismiss();
                baseModifyPass2Fragment.showToast("修改密码成功");
                Intent intent = new Intent();
                intent.putExtra(BaseLoginActivity.BUNDLE_KEY_NEWPASS, baseModifyPass2Fragment.mNewPass);
                intent.putExtra(BaseLoginActivity.BUNDLE_KEY_MOBILE, baseModifyPass2Fragment.mMobile);
                baseModifyPass2Fragment._mActivity.setResult(RESULT_OK, intent);
                baseModifyPass2Fragment._mActivity.finish();
            }
        }
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public PasswordEditText etPassWord;
        public PasswordEditText etPassWord2;
        public View vLevel1;
        public View vLevel2;
        public View vLevel3;
        public CircularProgressButton cpbSubmit;
        public LinearLayout llTips;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.imgReturn = rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = rootView.findViewById(R.id.toolbar_title);
            this.toolbar = rootView.findViewById(R.id.toolbar);
            this.etPassWord = rootView.findViewById(R.id.etPassWord);
            this.etPassWord2 = rootView.findViewById(R.id.etPassWord2);
            this.vLevel1 = rootView.findViewById(R.id.vLevel1);
            this.vLevel2 = rootView.findViewById(R.id.vLevel2);
            this.vLevel3 = rootView.findViewById(R.id.vLevel3);
            this.cpbSubmit = rootView.findViewById(R.id.cpbSubmit);
            this.llTips = rootView.findViewById(R.id.llTips);
        }

    }
}
