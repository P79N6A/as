package com.yaoguang.appcommon.common.base;

import android.widget.TextView;

import com.yaoguang.lib.R;
import com.yaoguang.lib.appcommon.utils.CountDownButtonHelper;

/**
 * 验证码默认方法
 * Created by zhongjh on 2017/6/20 0020.
 */
public abstract class QRCodeBaseActivity extends BaseSubmitProgressActivity {

    protected CountDownButtonHelper mCountDownButtonHelper = new CountDownButtonHelper(); // 验证码工具

    protected abstract TextView getGetCodeBtn();

    public void startShowCountdown() {
        mCountDownButtonHelper.init(getGetCodeBtn(), getString(R.string.get_code), 90, 1);
        mCountDownButtonHelper.start();
    }

    public void stopShowCountdown() {
        if (mCountDownButtonHelper != null) {
            mCountDownButtonHelper.stop();
        }
    }


}
