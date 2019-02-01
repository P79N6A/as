package com.yaoguang.driver.my.feedback;


import com.yaoguang.common.common.Constants;

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
