package com.yaoguang.appcommon.common.base;

import android.widget.TextView;

import com.yaoguang.lib.R;
import com.yaoguang.lib.appcommon.utils.CountDownButtonHelper;

/**
 * zhongjh
 */
public abstract class QRCodeBaseFragment extends BaseSubmitProgressFragment {

    protected CountDownButtonHelper mCountDownButtonHelper = new CountDownButtonHelper();

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
