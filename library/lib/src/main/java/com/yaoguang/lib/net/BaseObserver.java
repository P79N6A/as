package com.yaoguang.lib.net;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.BaseActivity2;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.yaoguang.lib.BuildConfig.DEBUG;


/**
 * 非背压
 * 一个基类的Observer
 * Created by zhongjh on 2017/12/22.
 */
public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {

    CompositeDisposable mCompositeDisposable;
    BaseView mBaseView;
    BaseSubmitProgressView mBaseSubmitProgressView;
    BaseFragment mBaseFragment;
    BaseFragment2 mBaseFragment2;// 这个是加入了google的注入的
    BaseActivity mBaseActivity;
    BaseActivity2 mBaseActivity2;
    boolean mIsSubmitProgress;// 提交按钮是否开启旋转动画

    Toast toast;

    public BaseObserver(CompositeDisposable compositeDisposable) {
        this.mCompositeDisposable = compositeDisposable;
    }

    public BaseObserver(CompositeDisposable compositeDisposable, BaseView baseView) {
        this.mCompositeDisposable = compositeDisposable;
        this.mBaseView = baseView;
    }

    public BaseObserver(CompositeDisposable compositeDisposable, BaseSubmitProgressView baseSubmitProgressView, boolean isSubmitProgress) {
        this.mCompositeDisposable = compositeDisposable;
        this.mIsSubmitProgress = isSubmitProgress;
        if (isSubmitProgress) {
            this.mBaseSubmitProgressView = baseSubmitProgressView;
        } else {
            this.mBaseView = baseSubmitProgressView;
        }
    }

    public BaseObserver(CompositeDisposable compositeDisposable, BaseFragment baseFragment) {
        this.mCompositeDisposable = compositeDisposable;
        this.mBaseFragment = baseFragment;
    }

    public BaseObserver(CompositeDisposable compositeDisposable, BaseFragment2 baseFragment2) {
        this.mCompositeDisposable = compositeDisposable;
        this.mBaseFragment2 = baseFragment2;
    }

    public BaseObserver(CompositeDisposable compositeDisposable, BaseActivity baseActivity) {
        this.mCompositeDisposable = compositeDisposable;
        this.mBaseActivity = baseActivity;
    }

    public BaseObserver(CompositeDisposable compositeDisposable, BaseActivity2 baseActivity2) {
        this.mCompositeDisposable = compositeDisposable;
        this.mBaseActivity2 = baseActivity2;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
        if (mBaseView != null)
            mBaseView.showProgressDialog();
        if (mBaseSubmitProgressView != null)
            mBaseSubmitProgressView.setSubmitProgressLoading();
        if (mBaseFragment != null)
            mBaseFragment.showProgressDialog();
        if (mBaseFragment2 != null)
            mBaseFragment2.showProgressDialog();
        if (mBaseActivity != null)
            mBaseActivity.showProgressDialog();
        if (mBaseActivity2 != null)
            mBaseActivity2.showProgressDialog();
    }

    @Override
    public void onNext(final T response) {
//        try {
        if (mBaseView != null)
            mBaseView.hideProgressDialog();
        if (mBaseFragment != null)
            mBaseFragment.hideProgressDialog();
        if (mBaseFragment2 != null)
            mBaseFragment2.hideProgressDialog();
        if (mBaseActivity != null)
            mBaseActivity.hideProgressDialog();
        if (mBaseActivity2 != null)
            mBaseActivity2.hideProgressDialog();
        if (response.getState().equals("200") || response.getState().equals("106")) {
            if (mBaseSubmitProgressView != null)
                mBaseSubmitProgressView.setSubmitProgressOK();

            // 执行成功的事件
            onSuccess(response);
        } else if (response.getState().equals("107")) {
            if (mBaseSubmitProgressView != null)
                mBaseSubmitProgressView.setSubmitProgressError();

            // 判断如果已经不是自动登录了，就不需要重启了
            if (!SPUtils.getInstance().getBoolean(Constants.AUTOLOGIN))
                return;

            // 打开登录
            BaseApplication.getInstance().startLogin(response.getMessage());
            // 全部关闭
            BaseApplication.getInstance().finishAllActivity();

            // 清除token缓存
            SPUtils.getInstance().put(Constants.TOKEN_ID, "");
            SPUtils.getInstance().put(Constants.TOKEN_TOKEN, "");
            // 自动清除自动登录
//                SPUtils.getInstance().put(Constants.USERNAME, "");
            SPUtils.getInstance().put(Constants.PASSWORD, "");
            SPUtils.getInstance().put(Constants.AUTOLOGIN, false);
            // 清空融云token

            if (mBaseSubmitProgressView != null)
                mBaseSubmitProgressView.setSubmitProgressInit();
        } else {
            if (mBaseSubmitProgressView != null)
                mBaseSubmitProgressView.setSubmitProgressError();
            onFail(response);
            if (mBaseSubmitProgressView != null)
                mBaseSubmitProgressView.setSubmitProgressInit();
        }
//        }catch (Exception ex){
//            XLog.e("error",ex);
//            Log.e("zhongjh", "error : ", ex);
//        }
    }

    @Override
    public void onError(Throwable e) {
        if (mBaseSubmitProgressView != null)
            mBaseSubmitProgressView.setSubmitProgressError();
        if (DEBUG) {
            if (e.toString().toUpperCase().contains("9X8Q7W6E5R4T3Y2U1I"))
                showToast("不在允许的范围内禁止使用，请联系管理员");
            else
                showToast(e.getMessage());
        } else {
            if (e.toString().toUpperCase().contains("9X8Q7W6E5R4T3Y2U1I"))
                showToast("不在允许的范围内禁止使用，请联系管理员");
            else
                showToast("当前网络不可用，请检查网络连接");
        }
        if (mBaseSubmitProgressView != null)
            mBaseSubmitProgressView.setSubmitProgressInit();
        if (mBaseView != null)
            mBaseView.hideProgressDialog();
        if (mBaseFragment != null)
            mBaseFragment.hideProgressDialog();
        if (mBaseFragment2 != null)
            mBaseFragment2.hideProgressDialog();
        if (mBaseActivity != null)
            mBaseActivity.hideProgressDialog();
        if (mBaseActivity2 != null)
            mBaseActivity2.hideProgressDialog();
//        if (e instanceof HttpException) {     //   HTTP错误
//            onException(ExceptionReason.BAD_NETWORK);
//        } else if (e instanceof ConnectException
//                || e instanceof UnknownHostException) {   //   连接错误
//            onException(ExceptionReason.CONNECT_ERROR);
//        } else if (e instanceof InterruptedIOException) {   //  连接超时
//            onException(ExceptionReason.CONNECT_TIMEOUT);
//        } else if (e instanceof JsonParseException
//                || e instanceof JSONException
//                || e instanceof ParseException) {   //  解析错误
//            onException(ExceptionReason.PARSE_ERROR);
//        } else {
//            onException(ExceptionReason.UNKNOWN_ERROR);
//        }
    }

    @Override
    public void onComplete() {
        if (mBaseView != null)
            mBaseView.hideProgressDialog();
        if (mBaseFragment != null)
            mBaseFragment.hideProgressDialog();
        if (mBaseFragment2 != null)
            mBaseFragment2.hideProgressDialog();
        if (mBaseActivity != null)
            mBaseActivity.hideProgressDialog();
        if (mBaseActivity2 != null)
            mBaseActivity2.hideProgressDialog();
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response) {
        String message = response.getMessage();
        if (TextUtils.isEmpty(message)) {
            showToast("服务器返回数据失败");
        } else {
            showToast(message);
        }
    }

    @SuppressLint("ShowToast")
    protected void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 请求异常
     *
     * @param reason 异常信息
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                Toast.makeText(BaseApplication.getInstance(), "网络连接失败,请检查网络", Toast.LENGTH_LONG).show();
                break;

            case CONNECT_TIMEOUT:
                Toast.makeText(BaseApplication.getInstance(), "连接超时,请稍后再试", Toast.LENGTH_LONG).show();
                break;

            case BAD_NETWORK:
                Toast.makeText(BaseApplication.getInstance(), "服务器异常", Toast.LENGTH_LONG).show();
                break;

            case PARSE_ERROR:
                Toast.makeText(BaseApplication.getInstance(), "解析服务器响应数据失败", Toast.LENGTH_LONG).show();
                break;

            case UNKNOWN_ERROR:
            default:
                Toast.makeText(BaseApplication.getInstance(), "未知错误", Toast.LENGTH_LONG).show();
                break;
        }
    }


    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,

        /**
         * 网络问题
         */
        BAD_NETWORK,

        /**
         * 连接错误
         */
        CONNECT_ERROR,

        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,

        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
