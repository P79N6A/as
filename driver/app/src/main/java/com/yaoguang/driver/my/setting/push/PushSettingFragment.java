package com.yaoguang.driver.my.setting.push;

import android.widget.CompoundButton;

import com.yaoguang.common.common.Constants;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.entity.Driver;


/**
 * 推送设置
 * Created by wly on 2017/9/5.
 */
public class PushSettingFragment extends BasePushSettingFragment {

    @Override
    public boolean getPushType() {

        Driver driver = Injection.provideDriverRepository(getContext()).getLoginResult().getUserInfo();
        // 设置推送参数
        return driver.getMessageFlag() == 1;
    }

    @Override
    public void customCreateView() {
        //设置是否推送
        mViewHolder.sPush.setOnCheckedChangeListener((buttonView, isChecked) -> setPush(isChecked, Constants.APP_DRIVER));
    }

    public static PushSettingFragment newInstance() {
        return new PushSettingFragment();
    }
}
