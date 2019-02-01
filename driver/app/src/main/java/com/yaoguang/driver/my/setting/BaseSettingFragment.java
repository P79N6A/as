package com.yaoguang.driver.my.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.beta.Beta;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseFragmentListV2;
import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.FileSizeUtil;
import com.yaoguang.common.common.FileUtils;
import com.yaoguang.common.common.GlideCacheUtil;
import com.yaoguang.common.common.SPUtils;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.driver.my.setting.accountsecurity.AccountSecurityFragment;
import com.yaoguang.driver.my.setting.usinghelp.UsingHelpFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.interactor.driver.my.setting.SettingFragmentView;
import com.yaoguang.interactor.driver.my.setting.SettingPresenter;
import com.yaoguang.interactor.driver.my.setting.impl.SettingPresenterImpl;
import com.yaoguang.taobao.push.impl.PushManager;

import java.io.File;
import java.util.Arrays;


/**
 * 作者：韦理英
 * 时间： 2017/5/18 0018.
 */

public abstract class BaseSettingFragment extends BaseFragmentListV2 implements SettingFragmentView, BaseLoadMoreRecyclerAdapter.OnRecyclerViewItemClickListener {
    /**
     * 列表数据，可自定义
     */
    protected String[] array = new String[]{"帐户安全", "消息提醒设置", "清除缓存", "使用帮助", "意见反馈", "关于货云集"};
    protected String[] arrayParam = {"", "", "", "", "", ""};

    InitialView mInitialView;

    private DialogHelper mDialogHelper;
    public SettingAdapter mSettingAdapter;
    /**
     * 控制层代码
     */
    SettingPresenter mSettingPresenter = new SettingPresenterImpl(this);

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        mInitialView = new InitialView(view);

        mSettingPresenter.subscribe();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (getView() == null) return;
        mSettingAdapter = new SettingAdapter(arrayParam);
        mSettingAdapter.setOnItemClickListener(this);
        initSwipeRecyclerView(getView(), mSettingAdapter);

        initCacheData();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        mSettingPresenter.unSubscribe();
        super.onDestroy();
    }

    @Override
    public BasePresenter getPresenter() {
        return mSettingPresenter;
    }

    /**
     * 初始化缓存
     */
    public void initCacheData() {
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
        //设置缓存大小的标题
        arrayParam[2] = FileSizeUtil.FormetFileSize(cacheSize);
    }

    @Override
    protected void refreshData() {
        mSettingAdapter.getList().clear();
        mSettingAdapter.appendToList(Arrays.asList(array));
        mSettingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View itemView, Object item, int position) {
        if (AppClickUtil.isDuplicateClick()) return;

        String value = ((TextView) itemView.findViewById(R.id.title)).getText().toString();
        switch (value) {
            case "帐户安全":
                start(AccountSecurityFragment.newInstance());
                break;
            case "消息提醒设置":
                openPushSettingFramgnet();
                break;
            case "清除缓存":
                if (mDialogHelper != null) {
                    mDialogHelper.hideDialog();
                }
                mDialogHelper = new DialogHelper();
                mDialogHelper.showConfirmDialog(getContext(),
                        "提示", "是否清除缓存", "确认", "我再想想", false, new CommonDialog.Listener() {
                            @Override
                            public void ok() {
                                GlideCacheUtil.getInstance().clearImageAllCache(getContext());
                                File cacheFile = new File(BaseApplication.getInstance().getBaseContext().getCacheDir(), "cache");
                                FileUtils.deleteDir(cacheFile);
                                Toast.makeText(_mActivity, "清除缓存成功", Toast.LENGTH_LONG).show();
                                initCacheData();
                                refreshData();
                                mDialogHelper.hideDialog();
                            }

                            @Override
                            public void cancel() {

                            }
                        });
                break;
            case "检查更新":
                // 使用腾讯的更新功能
                Beta.checkUpgrade();
                break;
            case "使用帮助 ":
                start(UsingHelpFragment.newInstance());
                break;
            case "意见反馈":
                openFeedbackFragment();
                break;
            case "关于货云集":
                openAboutUsFragment();
                break;
            case "退出帐号":
                if (mDialogHelper != null) {
                    mDialogHelper.hideDialog();
                }
                mDialogHelper = new DialogHelper();
                mDialogHelper.showConfirmDialog(getContext(), "提示", "确认退出帐号", "确认", "我再想想", false, new CommonDialog.Listener() {
                    @Override
                    public void ok() {
                        // 清除token缓存
                        SPUtils.getInstance().put(Constants.TOKEN_ID, "");
                        SPUtils.getInstance().put(Constants.TOKEN_TOKEN, "");
                        // 清除自动登录
                        SPUtils.getInstance().put(Constants.PASSWORD, "");
                        SPUtils.getInstance().put(Constants.AUTOLOGIN, false);
                        // 清空用户数据（目前只有物流和货主，还缺司机，已跟小韦提及）
                        DataStatic.getInstance().setAppUserWrapper(new AppUserWrapper());
                        DataStatic.getInstance().setUserOwner(new UserOwner());
                        mDialogHelper.hideDialog();

                        // 取消Alias
                        switch (getCodeType()) {
                            case Constants.APP_DRIVER:
                                PushManager.getInstance().deletAlias(Injection.provideDriverRepository(getContext()).getLoginResult().getUserInfo().getId(), Constants.APP_ALIAS);
                                break;
                            case Constants.APP_COMPANY:
                                PushManager.getInstance().deletAlias(DataStatic.getInstance().getAppUserWrapper().getId(), Constants.APP_ALIAS);
                                break;
                            case Constants.APP_SHIPPER:
                                PushManager.getInstance().deletAlias(DataStatic.getInstance().getUserOwner().getId(), Constants.APP_ALIAS);
                                break;
                        }
                        setLoginFlag();
                    }

                    @Override
                    public void cancel() {

                    }
                });
                break;
        }
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

    protected final class InitialView {
        private ImageView toolbar_left;
        private TextView toolbar_title;
        private RecyclerView recyclerView;
        private FrameLayout flQuit;

        public InitialView(View view) {
            flQuit = view.findViewById(R.id.flQuit);
            toolbar_left = view.findViewById(R.id.toolbar_left);
            toolbar_title = view.findViewById(R.id.toolbar_title);
            recyclerView = view.findViewById(R.id.recyclerView);

            toolbar_title.setText(getString(R.string.syssetting_title));

            // recyclerView 自动高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(params);

            // 返回
            toolbar_left.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    pop();
                }
            });
            // 退出
            flQuit.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    onItemClick(flQuit, null, 5);
                }
            });
        }
    }
}
