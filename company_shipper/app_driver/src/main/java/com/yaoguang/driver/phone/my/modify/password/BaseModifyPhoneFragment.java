package com.yaoguang.driver.phone.my.modify.password;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.driver.R;
import com.yaoguang.appcommon.phone.my.modifyphone.ModifyPhoneContact;
import com.yaoguang.appcommon.phone.my.modifyphone.ModifyPhonePresenterImpl;
import com.yaoguang.widget.edittext.PasswordEditText;

/**
 * 文件名：
 * 描    述： 绑定新的手机号
 * 作    者：钟景华
 * 时    间：2017/9/18.
 * 版    权：
 */

public abstract class BaseModifyPhoneFragment extends BaseFragment2 implements ModifyPhoneContact.View {

    ModifyPhoneContact.Presenter presenter = new ModifyPhonePresenterImpl(BaseModifyPhoneFragment.this, null, getCodeType());
    public ViewHolder mViewHolder;

    public abstract String getCodeType();

    public abstract void onViewCreated();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_moditymobile;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mViewHolder = new ViewHolder(view);
    }

    @Override
    public void init() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated();
        //输入密码，超过6位的时候才可以输入
        mViewHolder.petPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mViewHolder.tvSubmit.setBackgroundResource(R.drawable.background_selector_primary);
                    mViewHolder.tvSubmit.setEnabled(true);
                } else {
                    mViewHolder.tvSubmit.setBackgroundResource(R.color.background_enable_false);
                    mViewHolder.tvSubmit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //跳转到下一个
        mViewHolder.tvSubmit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //检测密码
                presenter.login(mViewHolder.petPassWord.getText().toString(), getContext());
            }
        });
        // 返回
        mViewHolder.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pop();
            }
        });

    }

    public static class ViewHolder {
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public PasswordEditText petPassWord;
        public TextView tvSubmit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.imgReturn = rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = rootView.findViewById(R.id.toolbar_title);
            this.toolbar = rootView.findViewById(R.id.toolbar);
            this.petPassWord = rootView.findViewById(R.id.petPassWord);
            this.tvSubmit = rootView.findViewById(R.id.tvSubmit);
        }

    }
}
