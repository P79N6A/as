package com.yaoguang.driver.phone.my.setting.push;

import com.yaoguang.appcommon.phone.my.setting.pushsetting.BasePushSettingFragment;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.common.constants.Constants;


/**
 * 推送设置
 * Created by wly on 2017/9/5.
 */
public class PushSettingFragment extends BasePushSettingFragment {

    @Override
    public boolean getPushType() {
        // 设置推送参数
        return DataStatic.getInstance().getDriver().getMessageFlag() == 1;
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
