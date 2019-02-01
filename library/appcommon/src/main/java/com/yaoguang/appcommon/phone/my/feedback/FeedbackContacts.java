package com.yaoguang.appcommon.phone.my.feedback;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.driver.FeedbackApp;

/**
 * 意思反馈
 * Created by wly
 * on 2018/1/8 0008.
 */

public interface FeedbackContacts {
    interface View extends BaseView {
        String getContactWay();
    }
    interface Presenter extends BasePresenter {
        void submitFeedback(String msg, String contactWay, String codeType, String feedbackPic);
        void submitFeedback(FeedbackApp feedbackApp);
    }
}
