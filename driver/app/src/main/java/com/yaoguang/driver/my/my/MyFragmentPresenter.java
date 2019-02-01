package com.yaoguang.driver.my.my;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.media.upload.UploadListener;
import com.alibaba.sdk.android.media.upload.UploadTask;
import com.alibaba.sdk.android.media.utils.FailReason;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.interactor.driver.my.DMyContacts;
import com.yaoguang.lib.annotation.apt.InstanceFactory;
import com.yaoguang.taobao.wantu.impl.WanTuManagerV2;

/**
 * Created by 韦理英
 * on 2017/4/25 0025.
 */

@InstanceFactory
public class MyFragmentPresenter extends MyFragmentContacts.Presenter implements DMyContacts.Presenter {
    private static final int REQUESTCODE_MY = 30000;
    private static final int FLAG_AVATAR = REQUESTCODE_MY + 1;
    // 用户上传自定义图片
    private static final int EDIT_BACKGROUND_PHOTO = REQUESTCODE_MY + 2;
    private DriverRepository mDriverRepository;

    void setDriverRepository(DriverRepository driverRepository) {
        this.mDriverRepository = driverRepository;
    }

    /**
     * 显示本地消息
     */
    @Override
    public void showLocalDriverInfo() {
        mView.showDriverInfo(mDriverRepository.getLoginResult());
    }

    /**
     * 显示远程消息
     */
    @Override
    void showRemoteDriverInfo() {
        mDriverRepository.getUserInfo(null).subscribe(driverAuthenticationBaseResponse -> {
                    ULog.i("AccountSecurityFragmentPresenter showRemoteDriverInfo 信息加载成功");
                    showLocalDriverInfo();
                },
                throwable -> {
                    ULog.i("AccountSecurityFragmentPresenter showRemoteDriverInfo 信息加载失败");
                    showLocalDriverInfo();
                });
    }


    /**
     * 上传
     * @param file          文件
     * @param select        选择
     * @param isCompress    是否压缩
     * @param context       context
     */
    @Override
    public void uploadWantu(String file, final int select, boolean isCompress, Context context) {
        new WanTuManagerV2(context).uploadFile(file, com.yaoguang.taobao.common.Constants.TAG_DRIVER_AVATAR, isCompress, new UploadListener.BaseUploadListener() {

            @Override
            public void onUploadComplete(final UploadTask uploadTask) {
                ULog.i("上传成功");

                Driver driver = new Driver();
                if (select == FLAG_AVATAR) {
                    driver.setId(Injection.provideDriverRepository(BaseApplication.getInstance()).getDriver().getId());
                    driver.setPhoto(uploadTask.getResult().url);
                } else if (select == EDIT_BACKGROUND_PHOTO) {
                    driver.setId(Injection.provideDriverRepository(BaseApplication.getInstance()).getDriver().getId());
                    driver.setBackgroundPicture(uploadTask.getResult().url);
                }

                mView.showProgressDialog("处理中，请稍候");
                mDriverRepository.update(driver).subscribe(value -> {
                    if (!value.getState().equals("200")) {
                        mView.showToast(value.getMessage());
                        return;
                    }

                    if (select == FLAG_AVATAR) {
                        LoginResult d = Injection.provideDriverRepository(BaseApplication.getInstance()).getLoginResult();
                        d.getUserInfo().setPhoto(uploadTask.getResult().url);
                        Injection.provideDriverRepository(BaseApplication.getInstance()).saveOrUpdate(d);
                    } else if (select == EDIT_BACKGROUND_PHOTO) {
                        LoginResult d = Injection.provideDriverRepository(BaseApplication.getInstance()).getLoginResult();
                        d.getUserInfo().setBackgroundPicture(uploadTask.getResult().url);
                        Injection.provideDriverRepository(BaseApplication.getInstance()).saveOrUpdate(d);
                    }
                    showLocalDriverInfo();
                    mView.hideDialog();
                }, throwable -> mView.hideDialog());

                super.onUploadComplete(uploadTask);
            }

            @Override
            public void onUploadFailed(UploadTask uploadTask, FailReason failReason) {
                ULog.i("上传头像失败" + failReason.getMessage());
                mView.showToast("上传失败");
                super.onUploadFailed(uploadTask, failReason);
            }

            @Override
            public void onUploading(UploadTask uploadTask) {
                super.onUploading(uploadTask);
            }
        });
    }

    /**
     * 上传头像
     */
    @Override
    public void uploadAvatar(String avatarUrl, Context context) {
        uploadWantu(avatarUrl, FLAG_AVATAR, false, context);
    }

    /**
     * 上传背景
     */
    @Override
    public void uploadBackgroundUI(String userBackgroundUrl, Context context) {
        uploadWantu(userBackgroundUrl, EDIT_BACKGROUND_PHOTO, false, context);
    }

    @Override
    public void subscribe() {
        showRemoteDriverInfo();
        // 获取未读数
        getUnreadNum();
    }

    /**
     * 获取未读数
     */
    @Override
    public void getUnreadNum() {
        mCompositeDisposable.add(mData.getUnreadNum(null).subscribe(unreadNum -> {
            if (unreadNum != null) mView.setUnreadNum(unreadNum);
        }, Throwable::printStackTrace));

    }

}
  