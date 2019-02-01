package com.yaoguang.appcommon.phone.my.setting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.databinding.FragmentSettingBinding;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.interactor.driver.my.setting.SettingFragmentView;
import com.yaoguang.interactor.driver.my.setting.SettingPresenter;
import com.yaoguang.interactor.driver.my.setting.impl.SettingPresenterImpl;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.file.FileSizeUtil;
import com.yaoguang.lib.common.file.FileUtils;
import com.yaoguang.lib.common.GlideCacheUtil;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.databinding.ToolbarCommonBinding;
import com.yaoguang.taobao.push.impl.PushManager;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 作者：韦理英
 * 时间： 2017/5/18 0018.
 * <p>
 * update zhongjh
 * data 2018/03/20
 * 为什么这里不继承databind，因为library不能继承，否则报错
 */
public abstract class BaseSettingFragment extends BaseFragment2 implements SettingFragmentView {

    public CompositeDisposable mCompositeDisposable;
    public FragmentSettingBinding mDataBinding;
    public ToolbarCommonBinding mToolbarCommonBinding;

    private DialogHelper mDialogHelper;
    private DialogHelper mDialogHelperPop;
    /**
     * 控制层代码
     */
    SettingPresenter mSettingPresenter = new SettingPresenterImpl(this);

    /**
     * 账户安全
     */
    protected abstract void openAccountSecurityFragment();

    protected abstract void openUsingHelpFragment();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        initCacheData();
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.bind(view);
        if (mDataBinding.getRoot().findViewById(R.id.toolbar) != null) {
            mToolbarCommonBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.toolbarCommon));
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return mSettingPresenter;
    }

    @Override
    public void init() {
        mCompositeDisposable = new CompositeDisposable();
        initToolbarNav(mToolbarCommonBinding.toolbar, "系统设置", -1, null);
    }

    @Override
    public void initListener() {
        // 账户安全
        mDataBinding.rlAccountSecurity.setOnClickListener(v -> openAccountSecurityFragment());
        // 消息提醒设置
        mDataBinding.rlMessageReminderSettings.setOnClickListener(v -> openPushSettingFramgnet());
        // 清除缓存
        mDataBinding.rlScavengingCaching.setOnClickListener(v -> {
            if (mDialogHelper == null)
                mDialogHelper = new DialogHelper(getContext(),
                        "提示", "是否清除缓存", "确认", "我再想想", false, new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        GlideCacheUtil.getInstance().clearImageAllCache(getContext());
                        File cacheFile = new File(BaseApplication.getInstance().getBaseContext().getCacheDir(), "cache");
                        FileUtils.deleteDir(cacheFile);
                        Toast.makeText(BaseApplication.getInstance(), "清除缓存成功", Toast.LENGTH_LONG).show();
                        initCacheData();
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelper.show();
        });
        // 使用帮助
        mDataBinding.rlUseHelp.setOnClickListener(v -> openUsingHelpFragment());
        // 意见反馈
        mDataBinding.rlFeedbackFeedback.setOnClickListener(v -> openFeedbackFragment());
        // 关于
        mDataBinding.rlAbout.setOnClickListener(v -> openAboutUsFragment());

        // 退出
        mDataBinding.flQuit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mDialogHelperPop == null)
                    mDialogHelperPop = new DialogHelper(getContext(), "提示", "确认退出帐号", "确认", "我再想想", false, new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            // 取消Alias
                            switch (getCodeType()) {
                                case Constants.APP_DRIVER:
                                    PushManager.getInstance().deletAlias(DataStatic.getInstance().getDriver().getId(), Constants.APP_ALIAS);
                                    break;
                                case Constants.APP_COMPANY:
                                    PushManager.getInstance().deletAlias(DataStatic.getInstance().getAppUserWrapper().getId(), Constants.APP_ALIAS);
                                    break;
                                case Constants.APP_SHIPPER:
                                    PushManager.getInstance().deletAlias(DataStatic.getInstance().getUserOwner().getId(), Constants.APP_ALIAS);
                                    break;
                            }

                            // 清除token缓存
                            SPUtils.getInstance().put(Constants.TOKEN_ID, "");
                            SPUtils.getInstance().put(Constants.TOKEN_TOKEN, "");
                            // 清除自动登录
                            SPUtils.getInstance().put(Constants.PASSWORD, "");
                            SPUtils.getInstance().put(Constants.AUTOLOGIN, false);
                            // 清空用户数据
                            DataStatic.getInstance().setAppUserWrapper(new AppUserWrapper());
                            DataStatic.getInstance().setUserOwner(new UserOwner());
                            DataStatic.getInstance().setDriver(new Driver());
                            // 清空融云token
                            DataStatic.getInstance().setRongCloudToken(null);

                            setLoginFlag();

                            // 重置可以重新请求网络
                            BaseApplication.getInstance().setRequestAPI(true);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                mDialogHelperPop.show();
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        mSettingPresenter.unSubscribe();
        mCompositeDisposable.clear();
        super.onDestroy();
    }


    /**
     * 初始化缓存
     */
    public void initCacheData() {
        mCompositeDisposable.add(Observable.create((ObservableOnSubscribe<Long>) e -> {
            //获取glide文件大小
            long glideSize = GlideCacheUtil.getInstance().getFolderSize(getContext());
            //获取网络缓存文件大小
            File cacheFile = new File(BaseApplication.getInstance().getBaseContext().getCacheDir(), "cache");
            long retrofit2Size = 0;
            try {
                retrofit2Size = FileSizeUtil.getFileSizes(cacheFile);
            } catch (Exception ignored) {
            }
            //相加，然后转换
            long cacheSize = glideSize + retrofit2Size;

            e.onNext(cacheSize);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> mDataBinding.tvCaching.setText("可释放" + FileSizeUtil.FormetFileSize(value))));

    }

    /**
     * 关于我们
     */
    protected abstract void openAboutUsFragment();

    /**
     * 意见反馈
     */
    protected abstract void openFeedbackFragment();

    /**
     * 推送设置
     */
    protected abstract void openPushSettingFramgnet();

    /**
     * 三端类型
     *
     * @return app类型
     */
    protected abstract String getCodeType();

    /**
     * 设置是否登录状态，并跳转
     */
    protected abstract void setLoginFlag();

}
