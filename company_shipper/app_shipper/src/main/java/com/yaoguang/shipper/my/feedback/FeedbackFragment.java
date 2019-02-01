package com.yaoguang.shipper.my.feedback;

import android.os.Bundle;

import com.yaoguang.appcommon.phone.my.feedback.BaseFeedbackFragment;
import com.yaoguang.lib.common.constants.Constants;


/**
 * Created by zhongjh on 2017/7/6.
 */
public class FeedbackFragment extends BaseFeedbackFragment {

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    public static FeedbackFragment newInstance(boolean goSetting) {
        FeedbackFragment feedbackFragment = new FeedbackFragment();
        Bundle bundle = new Bundle();
        feedbackFragment.setArguments(bundle);
        return feedbackFragment;
    }

    @Override
    protected String getCodeType() {
        return Constants.APP_SHIPPER;
    }

    @Override
    public String getContactWay() {
        return null;
    }
}
