package com.yaoguang.appcommon.phone.register;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.interactor.common.other.register.RegisterContact;

import java.lang.ref.WeakReference;


/**
 * Created by zhongjh
 * on 2017/6/15 0015.
 */
public abstract class BaseRegisterEndFragment<T> extends BaseFragment implements RegisterContact.RegisterView<T> {
    InitialView mInitialView;

    //是否计时中
    boolean runningThree;
    //倒计时5秒
    CountDownTimer downTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            Message msg = new Message();
            msg.what = 1;
            Bundle bundle = new Bundle();
            bundle.putLong("L", l);
            msg.setData(bundle);
            myHandler.sendMessage(msg);
        }

        @Override
        public void onFinish() {
            Message msg = new Message();
            msg.what = 0;
            Bundle bundle = new Bundle();
            msg.setData(bundle);
            myHandler.sendMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_register_end, container, false);
        customSetting();
        mInitialView = new InitialView(view);
        return view;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //当可见时，就自动计时
        downTimer.start();
    }

    MyHandler myHandler = new MyHandler(this);

    static class MyHandler extends Handler {
        WeakReference<BaseRegisterEndFragment> mBaseRegisterEndFragment;

        MyHandler(BaseRegisterEndFragment baseRegisterEndFragment) {
            mBaseRegisterEndFragment = new WeakReference<>(baseRegisterEndFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseRegisterEndFragment baseRegisterEndFragment = mBaseRegisterEndFragment.get();
            Long l = msg.getData().getLong("L");
            if (msg.what == 1) {
                baseRegisterEndFragment.runningThree = true;
                baseRegisterEndFragment.mInitialView.btnRegister.setText("回到登录页" + ((l / 1000) + "秒"));
            } else {
                baseRegisterEndFragment.mInitialView.btnRegister.setText("回到登录页");
                baseRegisterEndFragment.runningThree = false;
                baseRegisterEndFragment.getActivity().finish();
            }

        }
    }


    public class InitialView {
        private TextView title;
        private TextView context;
        private TextView btnRegister;
        private final View view;

        public InitialView(View view) {
            this.view = view;
            initUI();
        }

        private void initUI() {
            this.btnRegister = (TextView) view.findViewById(R.id.btnRegister);
            this.context = (TextView) view.findViewById(R.id.context);
            this.title = (TextView) view.findViewById(R.id.title);
            btnRegister.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    toLoginActivity();
                }
            });
        }

    }

    protected abstract void toLoginActivity();

}
