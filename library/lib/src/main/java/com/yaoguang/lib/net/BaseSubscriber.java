package com.yaoguang.lib.net;//package com.yaoguang.lib.net;
//
//import com.yaoguang.lib.base.BaseActivity;
//import com.yaoguang.lib.base.BaseFragment;
//import com.yaoguang.lib.base.BaseFragment2;
//import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
//import com.yaoguang.lib.base.interfaces.BaseView;
//import com.yaoguang.lib.net.bean.BaseResponse;
//
//import org.reactivestreams.Subscriber;
//import org.reactivestreams.Subscription;
//
//import io.reactivex.disposables.CompositeDisposable;
//import io.reactivex.disposables.Subscription;
//
///**
// * 背压
// * 一个基类的Subscriber
// * Created by zhongjh on 2018/3/9.
// */
//public abstract class BaseSubscriber<T extends BaseResponse> implements Subscriber<T> {
//
//    CompositeDisposable mSubscription;
//    BaseView mBaseView;
//    BaseSubmitProgressView mBaseSubmitProgressView;
//    BaseFragment mBaseFragment;
//    BaseFragment2 mBaseFragment2;// 这个是加入了google的注入的
//    BaseActivity mBaseActivity;
//    boolean mIsSubmitProgress;// 提交按钮是否开启旋转动画
//
//    public BaseSubscriber(Subscription subscription) {
//        this.mSubscription = subscription;
//    }
//
//    public BaseSubscriber(Subscription subscription, BaseView baseView) {
//        this.mSubscription = subscription;
//        this.mBaseView = baseView;
//    }
//
//    public BaseSubscriber(Subscription subscription, BaseSubmitProgressView baseSubmitProgressView, boolean isSubmitProgress) {
//        this.mSubscription = subscription;
//        this.mIsSubmitProgress = isSubmitProgress;
//        if (isSubmitProgress) {
//            this.mBaseSubmitProgressView = baseSubmitProgressView;
//        } else {
//            this.mBaseView = baseSubmitProgressView;
//        }
//    }
//
//    public BaseSubscriber(Subscription subscription, BaseFragment baseFragment) {
//        this.mSubscription = subscription;
//        this.mBaseFragment = baseFragment;
//    }
//
//    public BaseSubscriber(Subscription subscription, BaseFragment2 baseFragment2) {
//        this.mSubscription = subscription;
//        this.mBaseFragment2 = baseFragment2;
//    }
//
//    public BaseSubscriber(Subscription subscription, BaseActivity baseActivity) {
//        this.mSubscription = subscription;
//        this.mBaseActivity = baseActivity;
//    }
//
//
//    @Override
//    public void onSubscribe(Subscription s) {
//        mSubscription.add(s);
//        if (mBaseView != null)
//            mBaseView.showProgressDialog();
//        if (mBaseSubmitProgressView != null)
//            mBaseSubmitProgressView.setSubmitProgressLoading();
//        if (mBaseFragment != null)
//            mBaseFragment.showProgressDialog();
//        if (mBaseFragment2 != null)
//            mBaseFragment2.showProgressDialog();
//        if (mBaseActivity != null)
//            mBaseActivity.showProgressDialog();
//    }
//
//    @Override
//    public void onNext(T t) {
//
//    }
//
//    @Override
//    public void onError(Throwable t) {
//
//    }
//
//    @Override
//    public void onComplete() {
//
//    }
//}
