package com.yaoguang.appcommon.phone.my.setting.pushsetting;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.umeng.message.IUmengCallback;
import com.yaoguang.appcommon.R;
import com.yaoguang.interactor.driver.my.setting.SettingFragmentView;
import com.yaoguang.interactor.driver.my.setting.SettingPresenter;
import com.yaoguang.interactor.driver.my.setting.impl.SettingPresenterImpl;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.taobao.push.impl.PushManager;

/**
 * 消息提醒设置
 * Created by zhongjh on 2017/9/5.
 * <p>
 * 为什么这里不继承databind，因为library不能继承，否则报错
 */
public abstract class BasePushSettingFragment extends BaseFragment2 implements SettingFragmentView {

    protected ViewHolder mViewHolder;
    SettingPresenter mSettingPresenter = new SettingPresenterImpl(this);

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pushsetting;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mViewHolder = new ViewHolder(view);
    }

    @Override
    public void init() {
        initToolbarNav(mViewHolder.toolbar, "消息提醒设置", -1, null);

        mViewHolder.sPush.setChecked(getPushType());
        mViewHolder.sSound.setChecked(SPUtils.getInstance().getBoolean(Constants.Sound, true));
        mViewHolder.sVibrate.setChecked(SPUtils.getInstance().getBoolean(Constants.Vibrate, true));
    }

    @Override
    public void initListener() {
        //设置铃声
        mViewHolder.sSound.setOnCheckedChangeListener((compoundButton, isChecked) -> SPUtils.getInstance().put(Constants.Sound, isChecked));
        //设置震动
        mViewHolder.sVibrate.setOnCheckedChangeListener((compoundButton, isChecked) -> SPUtils.getInstance().put(Constants.Vibrate, isChecked));
        customCreateView();
    }

    public abstract boolean getPushType();

    public abstract void customCreateView();

    @Override
    public void showPushStatus(boolean value) {
        mViewHolder.sPush.setChecked(value);
    }

    public BasePresenter getPresenter() {
        return mSettingPresenter;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public Switch sPush;
        public Switch sSound;
        public Switch sVibrate;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.imgReturn = rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = rootView.findViewById(R.id.toolbar_title);
            this.toolbar = rootView.findViewById(R.id.toolbar);
            this.sPush = rootView.findViewById(R.id.sPush);
            this.sSound = rootView.findViewById(R.id.sSound);
            this.sVibrate = rootView.findViewById(R.id.sVibrate);
        }

    }

    /**
     * @param value    设置是否推送打开
     * @param codeType Constants.APP_COMPANY
     */
    public void setPush(boolean value, final String codeType) {
        if (value) {
            PushManager.getInstance().openPush(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    ULog.i("推送打开成功");
                    openPush(codeType);
                }

                @Override
                public void onFailure(String s, String s1) {
                    showToast("推送打开失败");
                    showPushStatus(false);
                }
            }, getContext());
        } else {
            PushManager.getInstance().closePush(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    ULog.i("推送关闭成功");
                    mSettingPresenter.pushSetting(false, codeType);
                }

                @Override
                public void onFailure(String s, String s1) {
                    showToast("推送关闭失败");
                    showPushStatus(true);
                }
            }, getContext());
        }
        showPushStatus(value);
    }

    protected void openPush(String codeType) {
        mSettingPresenter.pushSetting(true, codeType);
    }


}
