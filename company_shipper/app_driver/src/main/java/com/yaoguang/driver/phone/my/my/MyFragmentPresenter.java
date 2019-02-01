package com.yaoguang.driver.phone.my.my;

import android.app.Activity;
import android.content.Context;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.datasource.driver.DriverIndexDataSource;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.driver.UnreadNum;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.qinui.QiNiuManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 韦理英
 * on 2017/4/25 0025.
 */

public class MyFragmentPresenter extends BasePresenter implements MyFragmentContacts.Presenter {

    private MyFragmentContacts.View mView;
    private DriverIndexDataSource mDriverIndexDataSource = new DriverIndexDataSource();
    private DriverDataSource mDriverDataSource = new DriverDataSource();

    MyFragmentPresenter(MyFragmentContacts.View view) {
        mView = view;
    }

    private static final int REQUESTCODE_MY = 30000;
    private static final int FLAG_AVATAR = REQUESTCODE_MY + 1;
    // 用户上传自定义图片
    private static final int EDIT_BACKGROUND_PHOTO = REQUESTCODE_MY + 2;

    @Override
    public void showLocalDriverInfo() {
        mView.showDriverInfo(DataStatic.getInstance().getDriver(), DataStatic.getInstance().getUserDriverCars(), DataStatic.getInstance().getUserDriverLicence());
    }

    @Override
    public void showRemoteDriverInfo() {
        mDriverDataSource.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginDriver>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<LoginDriver> driverAuthenticationBaseResponse) {
                        showLocalDriverInfo();
                    }

                });
    }


    @Override
    public void uploadImage(Activity activity, Context context, String file, final int select) {
        QiNiuManager.getInstance().upload(activity, context, file, true, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (result) {
                    ULog.i("上传成功");

                    Driver driver = new Driver();
                    if (select == FLAG_AVATAR) {
                        driver.setId(DataStatic.getInstance().getDriver().getId());
                        driver.setPhoto(url);
                    } else if (select == EDIT_BACKGROUND_PHOTO) {
                        driver.setId(DataStatic.getInstance().getDriver().getId());
                        driver.setBackgroundPicture(url);
                    }

                    mView.showProgressDialog("处理中，请稍候");

                    mDriverDataSource.update(driver)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                                @Override
                                public void onSuccess(BaseResponse<String> response) {
                                    if (select == FLAG_AVATAR) {
                                        Driver driver1 = DataStatic.getInstance().getDriver();
                                        driver1.setPhoto(url);
                                        DataStatic.getInstance().setDriver(driver1);
                                    } else if (select == EDIT_BACKGROUND_PHOTO) {
                                        Driver driver1 = DataStatic.getInstance().getDriver();
                                        driver1.setBackgroundPicture(url);
                                        DataStatic.getInstance().setDriver(driver1);
                                    }
                                    showLocalDriverInfo();
                                    mView.hideProgressDialog();
                                }
                            });
                } else {
                    ULog.i("上传头像失败");
                    mView.showToast("上传失败");
                }
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    @Override
    public void uploadAvatar(Activity activity, Context context, String avatarUrl) {
        uploadImage(activity, context, avatarUrl, FLAG_AVATAR);
    }

    @Override
    public void uploadBackgroundUI(Activity activity, Context context, String userBackgroundUrl) {
        uploadImage(activity, context, userBackgroundUrl, EDIT_BACKGROUND_PHOTO);
    }

    @Override
    public void subscribe() {
        showRemoteDriverInfo();
        // 获取未读数
        getUnreadNum();
    }


    @Override
    public void getUnreadNum() {
        mDriverIndexDataSource.msgNumber()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<UnreadNum>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<UnreadNum> response) {
                        if (response != null)
                            mView.setUnreadNum(response.getResult());
                    }
                });
    }

    @Override
    public void openDrivingLicenseFragment() {
        mDriverDataSource.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginDriver>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<LoginDriver> loginDriver) {
                        mView.openDrivingLicenseFragment(loginDriver.getResult());
                    }

                });
    }

    @Override
    public void openMotorTractorOrSemiTrailerFragment(int type) {
        mDriverDataSource.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginDriver>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<LoginDriver> loginDriver) {
                        if (type == 0) {
                            mView.openMotorTractorFragment(loginDriver.getResult());
                        } else {
                            mView.openSemiTrailerFragment(loginDriver.getResult());
                        }
                    }

                });
    }

}
  