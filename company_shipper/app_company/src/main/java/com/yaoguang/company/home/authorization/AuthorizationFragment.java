package com.yaoguang.company.home.authorization;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentAuthorizationBinding;
import com.yaoguang.datasource.common.AppDataSource;
import com.yaoguang.greendao.entity.company.ScanCodeResult;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.yaoguang.greendao.entity.company.ScanCodeResult.CODE_FAILED;
import static com.yaoguang.greendao.entity.company.ScanCodeResult.CODE_SUCCESS;
import static com.yaoguang.greendao.entity.company.ScanCodeResult.CODE_TIMEOUT;

/**
 * Created by zhongjh on 2018/11/30.
 */

public class AuthorizationFragment extends BaseFragmentDataBind<FragmentAuthorizationBinding> {

    AppDataSource appDataSource = new AppDataSource();
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    String mUrl;// 扫描地址
    boolean mIsValid; // 是否有效
    String mActiveTime = null;
    String mWebserviceTime = null;
    Date mWebserviceDate = null;
    Calendar mWebserviceCalendar;
    Timer timer = new Timer(); // 循环1秒计时
    MyHandler myHandler = new MyHandler(this); // ui 呈现

    /**
     * @param url 需要登录的链接
     * @return 窗体
     */
    public static AuthorizationFragment newInstance(String url, boolean isValid, String activeTime, String webserviceTime) {
        Bundle args = new Bundle();
        AuthorizationFragment fragment = new AuthorizationFragment();
        args.putString("url", url);
        args.putBoolean("isValid", isValid);
        args.putString("activeTime", activeTime);
        args.putString("webserviceTime", webserviceTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_authorization;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        task.cancel();
        timer.cancel();
        mCompositeDisposable.clear();
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
        mDataBinding.btnOK.setOnClickListener(v -> {
            // 授权登录
            appDataSource.scanCodeAuth(mUrl)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<ScanCodeResult>>(mCompositeDisposable, AuthorizationFragment.this) {

                        @Override
                        public void onSuccess(BaseResponse<ScanCodeResult> response) {
                            initView(response.getResult());
                        }

                    });
        });
        // 关闭
        mDataBinding.btnClose.setOnClickListener(view -> pop());
        // 关闭
        mDataBinding.btnAgain.setOnClickListener(v -> {
            pop();
            Bundle bundle = new Bundle();
            bundle.putInt("type", -1);
            setFragmentResult(RESULT_OK, bundle);
        });
        mDataBinding.tvClose.setOnClickListener(view -> pop());
        mDataBinding.btnValidAgain.setOnClickListener(v -> {
            pop();
            Bundle bundle = new Bundle();
            bundle.putInt("type", -1);
            setFragmentResult(RESULT_OK, bundle);
        });
    }

    /**
     * 授权登录后进行相应改变
     */
    private void initView(ScanCodeResult scanCodeResult){
        switch (scanCodeResult.getCode()) {
            case CODE_SUCCESS:
                // 扫码后确认，不进行相应改变
                mDataBinding.llValid.setVisibility(View.VISIBLE);
                mDataBinding.llNoValid.setVisibility(View.GONE);
                mDataBinding.tvNoValid.setText(scanCodeResult.getInfo());
                mDataBinding.tvNoValid.setVisibility(View.VISIBLE);
                mDataBinding.btnOK.setVisibility(View.VISIBLE);
                mDataBinding.btnClose.setVisibility(View.VISIBLE);
                mDataBinding.btnValidAgain.setVisibility(View.GONE);
                // 显示消息，退出
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), scanCodeResult.getInfo(), Toast.LENGTH_SHORT).show();
                pop();
                break;
            case CODE_FAILED:
                // 授权确认失效，重新扫码
                mDataBinding.llValid.setVisibility(View.VISIBLE);
                mDataBinding.llNoValid.setVisibility(View.GONE);
                mDataBinding.tvNoValid.setText(scanCodeResult.getInfo());
                mDataBinding.tvNoValid.setVisibility(View.VISIBLE);
                // 隐藏相关按钮
                mDataBinding.btnOK.setVisibility(View.GONE);
                mDataBinding.btnClose.setVisibility(View.GONE);
                mDataBinding.btnValidAgain.setVisibility(View.VISIBLE);
                break;
            case CODE_TIMEOUT:
                // 二维码失效
                mDataBinding.llValid.setVisibility(View.GONE);
                mDataBinding.tvNoValid2.setText(scanCodeResult.getInfo());
                mDataBinding.llNoValid.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    /**
     * ui
     */
    static class MyHandler extends Handler {
        WeakReference<AuthorizationFragment> mAuthorizationFragment;

        MyHandler(AuthorizationFragment authorizationFragment) {
            mAuthorizationFragment = new WeakReference<>(authorizationFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            AuthorizationFragment authorizationFragment = mAuthorizationFragment.get();

            if (authorizationFragment == null)
                return;

            // 时间比较
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
            Date activeDate;
            Calendar activeCalendar = Calendar.getInstance();
            try {
                activeDate = sdf.parse(authorizationFragment.mActiveTime);
                activeCalendar.setTime(activeDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (activeCalendar.before(authorizationFragment.mWebserviceCalendar)) {
                // 过期
                authorizationFragment.mDataBinding.tvNoValid.setVisibility(View.VISIBLE);
                authorizationFragment.mDataBinding.btnOK.setVisibility(View.GONE);
                authorizationFragment.mDataBinding.btnClose.setVisibility(View.GONE);
                authorizationFragment.mDataBinding.btnValidAgain.setVisibility(View.VISIBLE);
            }
            super.handleMessage(msg);
        }
    }

}
