package com.yaoguang.company.my.setting.pushsetting;

import com.yaoguang.appcommon.phone.my.setting.pushsetting.BasePushSettingFragment;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.datasource.common.DataStatic;


/**
 * 推送设置
 * Created by zhongjh on 2017/9/5.
 */
public class PushSettingFragment extends BasePushSettingFragment {

    @Override
    public boolean getPushType() {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        // 设置推送参数
        return appUserWrapper.getMessageFlag() == 1;
    }

    @Override
    public void customCreateView() {
        //设置是否推送
        mViewHolder.sPush.setOnCheckedChangeListener((buttonView, isChecked) -> setPush(isChecked, Constants.APP_COMPANY));
    }

    public static PushSettingFragment newInstance() {
        return new PushSettingFragment();
    }
}
