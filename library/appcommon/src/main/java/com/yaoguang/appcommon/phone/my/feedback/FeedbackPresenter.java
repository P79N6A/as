package com.yaoguang.appcommon.phone.my.feedback;

import android.text.TextUtils;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.driver.AppDataSource;
import com.yaoguang.greendao.entity.driver.FeedbackApp;
import com.yaoguang.interactor.driver.BasePresenterImplV2;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class FeedbackPresenter extends BasePresenterImplV2 implements FeedbackContacts.Presenter {
    CompositeDisposable mCompositeDisposable;
    FeedbackContacts.View view;

    public FeedbackPresenter(FeedbackContacts.View feedbackView) {
        view = feedbackView;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void submitFeedback(String msg, String contactWay, String codeType, String feedbackPic) {
        if (TextUtils.isEmpty(msg)) {
            view.showToast("您没有输入意见，请输入您的意见");
            return;
        }

        Observable<BaseResponse<String>> baseResponseObservable = AppDataSource.getInstance().sendFeedback(msg, contactWay, codeType, feedbackPic);
        subscribe(baseResponseObservable, view, ShowType.NO, new OnCallback() {
            @Override
            public void onNext200(BaseResponse listBaseResponse) {
                view.showToast("反馈提交成功");
                super.onNext200(listBaseResponse);
            }
        });
    }

    @Override
    public void submitFeedback(FeedbackApp feedbackApp) {
        if (TextUtils.isEmpty(feedbackApp.getAdvice())) {
            view.showToast("您没有输入意见，请输入您的意见");
            return;
        }


        Observable<BaseResponse<String>> baseResponseObservable = new AppDataSource().sendFeedbackNew(feedbackApp.getFeedbackPic(), feedbackApp.getPlatformType(), feedbackApp.getAppType(), feedbackApp.getFeedbackType(), feedbackApp.getAdvice(), feedbackApp.getUserId());
        subscribe(baseResponseObservable, view, ShowType.NO, new OnCallback() {
            @Override
            public void onNext200(BaseResponse listBaseResponse) {
                view.showToast("反馈提交成功");
                super.onNext200(listBaseResponse);
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
