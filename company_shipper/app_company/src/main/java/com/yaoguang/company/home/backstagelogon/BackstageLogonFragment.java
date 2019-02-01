package com.yaoguang.company.home.backstagelogon;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentBackstageLogonBinding;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhongjh
 * @Package com.yaoguang.company.home.backstagelogon
 * @Description: 后台登录 窗体
 * @date 2018/01/30
 */
public class BackstageLogonFragment extends BaseFragmentDataBind<FragmentBackstageLogonBinding> implements BackstageLogonContract.View {

    String mUrl;// 扫描地址
    boolean mIsValid; // 是否有效
    String mActiveTime = null;
    String mWebserviceTime = null;
    Date mWebserviceDate = null;
    Calendar mWebserviceCalendar;
    BackstageLogonContract.Presenter mPresenter = new BackstageLogonPresenter(this);
    Timer timer = new Timer(); // 循环1秒计时
    MyHandler myHandler = new MyHandler(this); // ui 呈现

    /**
     * @param url 需要登录的链接
     * @return 窗体
     */
    public static BackstageLogonFragment newInstance(String url, boolean isValid, String activeTime, String webserviceTime) {
        Bundle args = new Bundle();
        BackstageLogonFragment fragment = new BackstageLogonFragment();
        args.putString("url", url);
        args.putBoolean("isValid", isValid);
        args.putString("activeTime", activeTime);
        args.putString("webserviceTime", webserviceTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_backstage_logon;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        task.cancel();
        timer.cancel();
    }

    @Override
    public void init() {
        if (getArguments() != null) {
            mUrl = getArguments().getString("url");
            mIsValid = getArguments().getBoolean("isValid");
            mActiveTime = getArguments().getString("activeTime");
            mWebserviceTime = getArguments().getString("webserviceTime");
            mWebserviceCalendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
            try {
                mWebserviceDate = sdf.parse(mWebserviceTime);
                mWebserviceCalendar.setTime(mWebserviceDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 设置该界面是否有效
        if (!mIsValid) {
            mDataBinding.llNoValid.setVisibility(View.VISIBLE);
            mDataBinding.llValid.setVisibility(View.GONE);
        } else {
            mDataBinding.llValid.setVisibility(View.VISIBLE);
            mDataBinding.llNoValid.setVisibility(View.GONE);
        }

        // 开始每隔1秒计算是否超出扫描码的时间
        timer.schedule(task, 1000, 1000);       // timeTask
    }

    @Override
    public void initListener() {
        // 确认调用接口登录
        mDataBinding.btnOK.setOnClickListener(view -> mPresenter.backstageLogon(mUrl));
        // 关闭
        mDataBinding.btnClose.setOnClickListener(view -> pop());
        // 关闭
        mDataBinding.btnAgain.setOnClickListener(view -> pop());
        mDataBinding.tvClose.setOnClickListener(view -> pop());
        mDataBinding.btnValidAgain.setOnClickListener(view -> pop());
    }

    @Override
    public void signOut() {
        pop();
    }

    /**
     * 计时控件
     */
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            mWebserviceCalendar.add(Calendar.SECOND, 1);
            myHandler.sendEmptyMessage(0);
        }
    };

    /**
     * ui
     */
    static class MyHandler extends Handler {
        WeakReference<BackstageLogonFragment> mBackstageLogonFragment;

        MyHandler(BackstageLogonFragment backstageLogonFragment) {
            mBackstageLogonFragment = new WeakReference<>(backstageLogonFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            BackstageLogonFragment backstageLogonFragment = mBackstageLogonFragment.get();

            if (backstageLogonFragment == null)
                return;

            // 时间比较
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
            Date activeDate = null;
            Calendar activeCalendar = Calendar.getInstance();
            try {
                activeDate = sdf.parse(backstageLogonFragment.mActiveTime);
                activeCalendar.setTime(activeDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (activeCalendar.before(backstageLogonFragment.mWebserviceCalendar)) {
                // 过期
                backstageLogonFragment.mDataBinding.tvNoValid.setVisibility(View.VISIBLE);
                backstageLogonFragment.mDataBinding.btnOK.setVisibility(View.GONE);
                backstageLogonFragment.mDataBinding.btnClose.setVisibility(View.GONE);
                backstageLogonFragment.mDataBinding.btnValidAgain.setVisibility(View.VISIBLE);
            }
            super.handleMessage(msg);
        }
    }

}
