package com.yaoguang.company.my.feedback;

import com.yaoguang.appcommon.phone.my.feedback.BaseFeedbackFragment;
import com.yaoguang.lib.common.constants.Constants;


/**
 * Created by zhongjh on 2017/7/6.
 */
public class FeedbackFragment extends BaseFeedbackFragment {

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    protected String getCodeType() {
        return Constants.APP_COMPANY;
    }


    @Override
    public String getContactWay() {
        return null;
    }
}
