package com.yaoguang.driver.my.setting.push;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.umeng.message.IUmengCallback;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseFragmentV2;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.SPUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.interactor.driver.my.setting.SettingFragmentView;
import com.yaoguang.interactor.driver.my.setting.SettingPresenter;
import com.yaoguang.interactor.driver.my.setting.impl.SettingPresenterImpl;
import com.yaoguang.taobao.push.impl.PushManager;

/**
 * 消息提醒设置
 * Created by zhongjh on 2017/9/5.
 */
public abstract class BasePushSettingFragment extends BaseFragmentV2 implements SettingFragmentView {

    protected ViewHolder mViewHolder;
    SettingPresenter mSettingPresenter = new SettingPresenterImpl(this);


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pushsetting, container, false);
        mViewHolder = new ViewHolder(view);

        initToolbarNav(mViewHolder.toolbar, "消息提醒设置", -1, null);

        mViewHolder.sPush.setChecked(getPushType());
        mViewHolder.sSound.setChecked(SPUtils.getInstance().getBoolean(Constants.Sound, true));
        mViewHolder.sVibrate.setChecked(SPUtils.getInstance().getBoolean(Constants.Vibrate, true));

        //设置铃声
        mViewHolder.sSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SPUtils.getInstance().put(Constants.Sound, isChecked);
            }
        });
        //设置震动
        mViewHolder.sVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SPUtils.getInstance().put(Constants.Vibrate, isChecked);
            }
        });
        customCreateView();


        return view;
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
        public CheckBox sPush;
        public CheckBox sSound;
        public CheckBox sVibrate;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.imgReturn = (ImageView) rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.sPush =  rootView.findViewById(R.id.sPush);
            this.sSound =  rootView.findViewById(R.id.sSound);
            this.sVibrate =  rootView.findViewById(R.id.sVibrate);
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

    @Deprecated
    protected void closePush(String codeType) {

    }

    protected void openPush(String codeType) {
        mSettingPresenter.pushSetting(true, codeType);
    }


}
