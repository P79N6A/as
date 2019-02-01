package com.yaoguang.driver.phone.my.setting.feedback;


import com.yaoguang.appcommon.phone.my.feedback.BaseFeedbackFragment;
import com.yaoguang.lib.common.constants.Constants;

/**
 * 意见反馈
 * Created by wly on 2017/7/6.
 */

public class FeedbackFragment extends BaseFeedbackFragment {

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    protected String getCodeType() {
        return Constants.APP_DRIVER;
    }

    @Override
    public String getContactWay() {
        return null;
    }


}
