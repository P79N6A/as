package com.yaoguang.driver.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.common.base.BaseFragment;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.driver.activitys.LoginActivity;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.interfaces.driver.view.DRegisterView;

/**
 * Created by 韦理英
 * on 2017/6/15 0015.
 */

public class RegisterFourFragment extends BaseFragment implements DRegisterView {

    InitialView mInitialView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(com.yaoguang.appcommon.R.layout.view_register4, container, false);
        customSetting();
        mInitialView = new InitialView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public class InitialView {
        private TextView btnRegister;
        private final View view;

        public InitialView(View view) {
            this.view = view;
            initUI();
        }

        private void initUI() {
            this.btnRegister = view.findViewById(com.yaoguang.appcommon.R.id.btnRegister);
            btnRegister.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                toLoginActivity();
            });

        }

    }

    @Override
    @Deprecated
    public void startShowCountdown() {

    }

    @Override
    public void customSetting() {
    }

    @Override
    @Deprecated
    public void nextPage() {

    }


    public static RegisterFourFragment newInstance() {
        return new RegisterFourFragment();
    }

    @Override
    public Driver getDriver() {
        return null;
    }

    @Override
    @Deprecated
    public void stopShowCountdown() {

    }

    @Override
    @Deprecated
    public void uploadSuccess(String url, int type) {

    }

    @Override
    @Deprecated
    public void uploadFail(int type) {

    }

    private RegisterActivity getRegisterActivity() {
        return (RegisterActivity) getActivity();
    }

    protected void toLoginActivity() {
        LoginActivity.newInstance(getActivity(), true, null, null);
        if (getActivity() != null)
            getActivity().finish();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
