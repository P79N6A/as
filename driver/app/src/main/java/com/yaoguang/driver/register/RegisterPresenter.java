package com.yaoguang.driver.register;

import android.content.Context;
import android.text.TextUtils;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.driver.data.source.MessageRepository;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * 注册的逻辑分发层
 * Created by wly
 * on 2017 2017/3/31.9:54
 */
public class RegisterPresenter implements RegisterContact.RegisterPresenter {

    private final DriverRepository mDriverRepository;
    private final MessageRepository mMessageRepository;
    protected RegisterContact.RegisterView mView;
    protected CompositeDisposable mCompositeDisposable;
    private Context mContext;

    public RegisterPresenter(RegisterContact.RegisterView view, Context context, DriverRepository driverRepository, MessageRepository messageRepository) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        mContext = context;
        mDriverRepository = driverRepository;
        mMessageRepository = messageRepository;
    }


    @Override
    public void success() {
        mView.showToast("注册成功");
        mView.setSubmitProgressSuccess();
    }

    @Override
    public void fail(String strMessage) {
        mView.showToast(strMessage);
        mView.stopShowCountdown();
    }

    @Override
    public void getVerificationCode(String strMobile) {
        if (TextUtils.isEmpty(strMobile)) {
            fail("请输入手机号码");
            return;
        }
        mMessageRepository.getVerificationCode(strMobile, "1").subscribe(new Subscriber<BaseResponse<String>>() {
            @Override
            public void onSubscribe(Subscription s) {
                mView.startShowCountdown();
            }

            @Override
            public void onNext(BaseResponse<String> stringBaseResponse) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void fail(int intType, String strCode) {
        mView.showToast(strCode);
    }

    /**
     * 注册
     * @param mobile     手机号
     * @param pass       密码
     * @param auth       验证码
     * @param cbProtocol 是否勾选协议
     */
    @Override
    public void handleOneAuth(String mobile, String pass, String auth, boolean cbProtocol) {
        if (mobile.length() != 11) {
            fail(2, "手机号码为11位");
            return;
        }
        if (auth.length() <= 0) {
            fail(0, "验证码不能为空");
            return;
        }
        if (pass.length() < 6 || pass.length() > 12) {
            fail(0, "密码长度在6-12之间");
            return;
        }
        if (!cbProtocol) {
            fail(2, "请先同意协议，才可以注册");
            return;
        }
        mView.setSubmitProgressLoading();
        mDriverRepository.simpleRegister(mobile, pass, auth).subscribe(stringBaseResponse -> {
            if (stringBaseResponse.getState().equals("200")) {
                mView.setSubmitProgressSuccess();
            } else {
                mView.setSubmitProgressError();
                if (stringBaseResponse.getResult() != null)
                    mView.showToast(stringBaseResponse.getResult());
            }
        }, throwable -> mView.setSubmitProgressError());

    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
