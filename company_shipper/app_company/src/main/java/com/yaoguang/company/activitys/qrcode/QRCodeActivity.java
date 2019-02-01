package com.yaoguang.company.activitys.qrcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.ActivityQrCodeBinding;
import com.yaoguang.datasource.common.AppDataSource;
import com.yaoguang.greendao.entity.company.QRCode;
import com.yaoguang.greendao.entity.company.ScanCodeResult;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseActivityDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 需要授权登录的二维码界面
 * Created by zhongjh on 2018/11/27.
 */
public class QRCodeActivity extends BaseActivityDataBind<ActivityQrCodeBinding> {

    public static final int QR_CODE_CODE = 3;

    private AppDataSource appDataSource = new AppDataSource();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    DialogHelper mDialogHelper;



    public static void newInstance(Activity activity, String userId) {
        Intent intent = new Intent(activity, QRCodeActivity.class);
        intent.putExtra("userId", userId);
        activity.startActivityForResult(intent, QR_CODE_CODE);
    }

    @Override
    protected void initView() {
        initToolbarNav(findViewById(R.id.toolbar), "扫码授权设备", -1, null);
        initGetQrCode();
    }

    private void initGetQrCode() {
        appDataSource.getQrCode(getAppVersionName(getBaseContext()), getIntent().getStringExtra("userId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<QRCode>>(mCompositeDisposable, QRCodeActivity.this) {

                    @Override
                    public void onSuccess(BaseResponse<QRCode> response) {
                        byte[] imageByteArray = Base64.decode(response.getResult().getQrCode(), Base64.DEFAULT);
                        Glide.with(getBaseContext())
                                .asBitmap()
                                .load(imageByteArray)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.ic_refresh_oringe))
                                .listener(new RequestListener<Bitmap>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                        mDataBinding.imgImg.setImageBitmap(resource);
                                        // 长按事件
                                        mDataBinding.imgImg.setOnClickListener(v -> {
                                            if (mDialogHelper == null)
                                                mDialogHelper = new DialogHelper(QRCodeActivity.this, "提示", "是否保存二维码至相册?", new CommonDialog.Listener() {
                                                    @Override
                                                    public void ok(String msg) {
                                                        mDialogHelper.hideDialog();
                                                        saveImage(QRCodeActivity.this, resource);
                                                    }

                                                    @Override
                                                    public void cancel() {

                                                    }
                                                });
                                            mDialogHelper.show();
                                        });
                                        return false;
                                    }
                                }).submit();

                        // 开启轮循检查
                        start(response.getResult().getKey());
                    }

                    @Override
                    public void onFail(BaseResponse<QRCode> response) {
                        super.onFail(response);
                        Glide.with(getBaseContext())
                                .load(R.drawable.ic_refresh_oringe)
                                .into(mDataBinding.imgImg);
                        mDataBinding.imgImg.setOnClickListener(v -> initGetQrCode());
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initListener() {
    }

    /**
     * 轮循
     */
    private void start(String key) {
        //在当前线程（也即主线程中）开启一个消息处理器，并在3秒后在主线程中执行，从而来更新UI
        new Handler().postDelayed(() -> {
            //有关更新UI的代码
            //要执行的代码
            appDataSource.checkScanCode(key)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<ScanCodeResult>>(mCompositeDisposable) {

                        @Override
                        public void onSuccess(BaseResponse<ScanCodeResult> response) {
                            if (response.getResult().getCode() != 1001 && response.getResult().getCode() != 1004) {
                                start(key);
                            } else if (response.getResult().getCode() == 1001) {
                                // 再次跳转登录
                                setResult(RESULT_OK);
                                QRCodeActivity.this.finish();
                            }
                        }
                    });
        }, 3000);//3秒后发送
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    //获取当前版本号
    private String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception ignored) {
        }
        return versionName;
    }

    /**
     * 保存图片到系统相册
     */
    public static void saveImage(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "YGCompany");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }


}
