package com.yaoguang.appcommon.phone.activitys.start;

import android.annotation.SuppressLint;

import com.yaoguang.greendao.entity.common.LoginPic;
import com.yaoguang.lib.appcommon.utils.AppUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.AppDataSource;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.yaoguang.lib.common.constants.Constants.LOCALVERIONCODE;

/**
 * 开始界面的控制器
 * Created by zhongjh on 2017/5/25.
 */
public class StartPresenter implements StartContact.Presenter {

    private DCSBaseInteractorImpl mDCSBaseInteractorImpl;
    private AppDataSource mAppDataSource;
    private StartContact.View mStartView;
    private String mCodeType;
    private CompositeDisposable mCompositeDisposable;

    public StartPresenter(StartContact.View startView, String codeType) {
        this.mStartView = startView;
        this.mCodeType = codeType;
        mCompositeDisposable = new CompositeDisposable();
        mDCSBaseInteractorImpl = new DCSBaseInteractorImpl();
        mAppDataSource = new AppDataSource();
    }

    @Override
    public void openActivity() {
        // 判断类型
        String type = "0";
        switch (BaseApplication.getAppType()) {
            case Constants.APP_DRIVER:
                type = "1";
                break;
            case Constants.APP_COMPANY:
                type = "2";
                break;
            case Constants.APP_SHIPPER:
                type = "3";
                break;
        }
        mAppDataSource.getPic(type)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<LoginPic>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<LoginPic> response) {
                        if (response.getResult() == null)
                            toActivity("");
                        // 判断时间是否在范围之内，如果在就获取页面图片
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date startTime = null;
                        Date endTime = null;
                        //将字符串形式的时间转化为Date类型的时间
                        try {
                            startTime = sdf.parse(response.getResult().getStartTime());
                            endTime = sdf.parse(response.getResult().getEndTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String url = "";
                        if (startTime != null && endTime != null) {
                            if (System.currentTimeMillis() >= startTime.getTime() && System.currentTimeMillis() <= endTime.getTime()) {
                                url = response.getResult().getPicAddr();
                            }
                        }
                        toActivity(url);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        // 直接跳转
                        toActivity("");
                    }

                    @Override
                    public void onFail(BaseResponse<LoginPic> response) {
                        super.onFail(response);
                        toActivity("");
                    }
                });
    }

    @Override
    public void getCodeKey() {
        openActivity();

//        mAppDataSource.getCodeKey()
//                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
//                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
//                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
//
//                    @Override
//                    public void onSuccess(BaseResponse<String> response) {
//                        // 缓存公钥
//
////                        // 获取是打开哪个Activity
//                        openActivity();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        // 公钥获取不了,再次请求
//                        getCodeKey();
//                    }
//
//                    @Override
//                    public void onFail(BaseResponse<String> response) {
//                        super.onFail(response);
//                        // 公钥获取不了,再次请求
//                        getCodeKey();
//                    }
//                });
    }

    /**
     * 检测是否是新版本
     * 如果是新版本就显示引导页，
     * 不是新版本不用显示引导页
     */
    private boolean checkFirstInstall() {
        int versionCode = SPUtils.getInstance().getInt(LOCALVERIONCODE, 0);
        if (versionCode < AppUtils.getAppVersionCode()) {
            SPUtils.getInstance().put(LOCALVERIONCODE, AppUtils.getAppVersionCode());
            return true;
        }
        return false;
    }

    /**
     * 跳转某个Activity
     *
     * @param url 地址
     */
    private void toActivity(String url) {
        if (checkFirstInstall()) {
            mStartView.startGuidanceActivity(url);
        } else {
            String id = null;
            if (mCodeType.equals(Constants.APP_DRIVER)) {
                if (mDCSBaseInteractorImpl.getDriver() != null)
                    id = DataStatic.getInstance().getDriver().getId();
            } else if (mCodeType.equals(Constants.APP_SHIPPER)) {
                if (DataStatic.getInstance().getUserOwner() != null)
                    id = DataStatic.getInstance().getUserOwner().getId();
            } else if (mCodeType.equals(Constants.APP_COMPANY)) {
                if (DataStatic.getInstance().getAppUserWrapper() != null)
                    id = DataStatic.getInstance().getAppUserWrapper().getId();
            }
            if (id != null && SPUtils.getInstance().getBoolean(Constants.AUTOLOGIN)) {
                mStartView.startMainActivity(url);
            } else {
                mStartView.startLoginActivity(url);
            }
        }
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
