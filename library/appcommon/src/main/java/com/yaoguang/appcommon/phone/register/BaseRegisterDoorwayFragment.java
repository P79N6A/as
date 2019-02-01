package com.yaoguang.appcommon.phone.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.html.HtmlActivity;
import com.yaoguang.appcommon.common.base.QRCodeBaseFragment;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.common.SpanUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.interactor.common.other.register.RegisterContact;
import com.yaoguang.interactor.common.other.register.RegisterPresenterImpl;
import com.yaoguang.widget.edittext.ClearEditText;
import com.yaoguang.widget.edittext.PasswordEditText;


/**
 * 第一步的注册界面
 * Created by zhongjh
 * on 2017/7/3 0015.
 */
public abstract class BaseRegisterDoorwayFragment<T> extends QRCodeBaseFragment implements RegisterContact.RegisterView<T>, BaseSubmitProgressView {
    protected RegisterContact.RegisterPresenter presenter;
    InitialView mInitialView;

    protected abstract void setCode(String code);

    protected abstract String getCodeType();

    /**
     * 点击注册后，实现对应的实体代码
     */
    protected abstract void clickBtnRegister(InitialView initialView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_register1, container, false);
        presenter = new RegisterPresenterImpl<>(this, null, getCodeType(), getContext());
        mInitialView = new InitialView(view);
        setCpbSubmit(mInitialView.cpbSubmit);
        customSetting();
        return view;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
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

    public class InitialView {
        public final View view;
        public ClearEditText etMobile;
        public PasswordEditText etPassWord;
        public EditText actvCode;
        public TextView btnGetCode;
        public LinearLayout llMobile;
        public CircularProgressButton cpbSubmit;
        public CheckBox cbProtocol;
        public TextView tvProtocol;
        public ImageView ivClearPhone;

        public InitialView(View view) {
            this.view = view;
            initUI();
        }

        private void initUI() {
            this.tvProtocol = (TextView) view.findViewById(R.id.tvProtocol);
            this.cbProtocol = (CheckBox) view.findViewById(R.id.cbProtocol);
            this.cpbSubmit = (CircularProgressButton) view.findViewById(R.id.cpbSubmit);
            this.llMobile = (LinearLayout) view.findViewById(R.id.llMobile);
            this.btnGetCode = (TextView) view.findViewById(R.id.btnGetCode);
            this.actvCode = (EditText) view.findViewById(R.id.actvCode);
            this.etPassWord = (PasswordEditText) view.findViewById(R.id.etPassWord);
            this.etMobile = (ClearEditText) view.findViewById(R.id.etMobile);
            this.btnGetCode.setEnabled(false);
            mCpbSubmitOnClick = new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    clickBtnRegister(InitialView.this);
                    //隐藏软键盘
                    hideKeyboard();
                }
            };
            btnGetCode.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    presenter.getVerficationCode(getMobile(), getCodeType());
                }
            });
            cpbSubmit.setOnClickListener(mCpbSubmitOnClick);
            tvProtocol.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    BaseRegisterDoorwayFragment.this.getParentFragment();
                    HtmlActivity.newInstance(getActivity(), HtmlActivity.class, 1, "用户协议", BuildConfig.ENDPOINT + "page/user_agreement.jsp");
                }
            });
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
                    .append("我已了解并同意 ").setForegroundColor(ContextCompat.getColor(getContext(),R.color.gray))
                    .append("《用户协议》").setForegroundColor(ContextCompat.getColor(getContext(),R.color.orange500))
                    .create());
        }

        public String getMobile() {
            return etMobile.getText().toString();
        }

    }


}
