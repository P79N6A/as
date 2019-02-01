package com.yaoguang.driver.my.bindphone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.appcommon.utils.CountDownButtonHelper;
import com.yaoguang.common.base.BaseFragment;
import com.yaoguang.common.base.interfaces.BasePresenter;

import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.Injections;
import com.yaoguang.interactor.common.my.modifyphone.ModifyPhoneContact;
import com.yaoguang.interactor.common.my.modifyphone.ModifyPhonePresenterImpl;
import com.yaoguang.widget.edittext.ClearEditText;

/**
 * 修改手机第二步
 * Created by zhongjh on 2017/9/18.
 */
public abstract class BaseModifyPhone2Fragment extends BaseFragment implements ModifyPhoneContact.ViewStep2 {

    ModifyPhoneContact.Presenter presenter = new ModifyPhonePresenterImpl(null, BaseModifyPhone2Fragment.this, getCodeType());
    CountDownButtonHelper mCountDownButtonHelper;
    ViewHolder mViewHolder;
    CommonDialog mCommonDialog;
    String password;
    private DialogHelper mDialogHelper;

    public abstract String getCodeType();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        password = getArguments().getString("password");
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moditymobile2, null);
        mCommonDialog = new CommonDialog(getContext(), "确定更换手机号码并退出账户?");
        mViewHolder = new ViewHolder(view);

        switch (getCodeType()) {
            case Constants.APP_COMPANY:
                mViewHolder.tvMobile.setText("当前手机号:" + DataStatic.getInstance().getAppUserWrapper().getMobile());
                break;
            case Constants.APP_SHIPPER:
                mViewHolder.tvMobile.setText("当前手机号:" + DataStatic.getInstance().getUserOwner().getPhone());
                break;
            case Constants.APP_DRIVER:
                mViewHolder.tvMobile.setText("当前手机号:" + Injection.provideDriverRepository(getContext()).getLoginResult().getUserInfo().getMobile());
                break;
        }

        mViewHolder.toolbar_title.setText("更换手机号码");
        mViewHolder.btnGetCode.setEnabled(false);

        mViewHolder.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pop();
            }
        });
        // 获取验证码
        mViewHolder.btnGetCode.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                presenter.getVerficationCode(getCodeType(), mViewHolder.etMobile.getText().toString(), "0");
            }
        });
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
                if (s.length() == 11) {
                    mViewHolder.btnGetCode.setBackgroundResource(R.drawable.background_selector_verification);
                    mViewHolder.btnGetCode.setEnabled(true);
                } else {
                    mViewHolder.btnGetCode.setBackgroundResource(R.drawable.ic_hqyz_w);
                    mViewHolder.btnGetCode.setEnabled(false);
                }
                checkSubmit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 提交
        mViewHolder.acbSubmit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //提交前验证
                mCommonDialog.show();
                mCommonDialog.setListener(new CommonDialog.Listener() {
                    @Override
                    public void ok() {
                        presenter.setNewMobile(getCodeType(), mViewHolder.etMobile.getText().toString(), mViewHolder.actvCode.getText().toString(), password);
                        mCommonDialog.dismiss();
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        });


        //获取验证码
        mCountDownButtonHelper = new CountDownButtonHelper(mViewHolder.btnGetCode, getString(R.string.get_code), 90, 1);


        return view;
    }

    private void checkSubmit() {
        //手机号码如果不是11位或者验证码为空时就不能使用按钮
        if (mViewHolder.etMobile.getText().toString().length() != 11 || TextUtils.isEmpty(mViewHolder.actvCode.getText().toString())) {
            mViewHolder.acbSubmit.setBackgroundResource(R.color.background_enable_false);
            mViewHolder.acbSubmit.setEnabled(false);
        } else {
            mViewHolder.acbSubmit.setBackgroundResource(R.drawable.background_selector_primary);
            mViewHolder.acbSubmit.setEnabled(true);
        }
    }

    @Override
    public void startCountDown() {
        mCountDownButtonHelper.start();
    }

    @Override
    public void moditySuccess() {
        mCommonDialog.dismiss();
        showToast("手机修改成功，请重新登录");
        toLoginActivity();
    }

    protected abstract void toLoginActivity();

    @Override
    public void stopCountDown() {
        mCountDownButtonHelper.stop();
    }

    @Override
    public void showBindPhoneTips() {
        // 弹出提示
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        mDialogHelper = new DialogHelper();
        mDialogHelper.showConfirmDialog(getContext(),
                "获取验证码失败", "该手机号已被其他帐号绑定。如果继续，原帐号将自动解绑。是否继续?", "确定", "取消", false, new CommonDialog.Listener() {
                    @Override
                    public void ok() {
                        mDialogHelper.hideDialog();
                        presenter.getVerficationCode(Constants.APP_COMPANY, mViewHolder.etMobile.getText().toString(), "1");
                    }

                    @Override
                    public void cancel() {

                    }
                });
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public TextView tvMobile;
        public ClearEditText etMobile;
        public EditText actvCode;
        public TextView btnGetCode;
        public TextView acbSubmit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.imgReturn = (ImageView) rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.tvMobile = (TextView) rootView.findViewById(R.id.tvMobile);
            this.etMobile = (ClearEditText) rootView.findViewById(R.id.etMobile);
            this.actvCode = (EditText) rootView.findViewById(R.id.actvCode);
            this.btnGetCode = (TextView) rootView.findViewById(R.id.btnGetCode);
            this.acbSubmit = (TextView) rootView.findViewById(R.id.acbSubmit);
        }

    }
}
