package com.yaoguang.lib.qinui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.vinaygaba.rubberstamp.RubberStamp;
import com.vinaygaba.rubberstamp.RubberStampConfig;
import com.vinaygaba.rubberstamp.RubberStampPosition;
import com.yaoguang.lib.R;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.file.FileUtils;
import com.yaoguang.lib.net.ApiDefault;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 不适用全局的七牛，为了能传多个文件
 * Created by zhongjh on 2019/1/29.
 */
public class QiNiuManager2<T> {

    // 保存在服务器上的资源唯一标识，请参阅键值对
    // https://developer.qiniu.com/kodo/manual/1277/product-introduction#key-value
    // 	服务器分配的 token
    private final static String HOST = "http://img.huoyunji.com/";
    private final static String KEY = "key";
    private final static String TOKEN = "token";
    private final static int FZ_KB = 1024;

    // 保存在服务器上的资源唯一标识，请参阅键值对
    private static UploadManager mUploadManager;
    private static Handler mHandler = new Handler();

    private CompositeDisposable mCompositeDisposable;

    // 初始化、执行上传
    private volatile boolean isCancelled = false;
    private long uploadLastOffset = 0;
    private long uploadLastTimePoint;

    private T t;// 绑定值

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public QiNiuManager2(CompositeDisposable mCompositeDisposable) {
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
//                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(AutoZone.autoZone)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        mUploadManager = new UploadManager(config);
        this.mCompositeDisposable = mCompositeDisposable;
    }

    /**
     * 上传文件
     *
     * @param context        上下文
     * @param file           地址
     * @param comeBack       回调事件
     * @param isAddWaterMark 是否要添加水印
     */
    public void upload(Context context, String file, boolean isAddWaterMark, QiNiuManager.ComeBack comeBack) {
        String prefix = file.substring(file.lastIndexOf(".") + 1);
        if (prefix.equals("wav") || prefix.equals("mp3")) {
            uploadFile(context, file, comeBack);
        } else if (prefix.equals("mp4") || prefix.equals("3gp")) {
            if (prefix.equals("mp4"))
                uploadFile(context, file, comeBack);
            if (prefix.equals("3gp"))
                uploadFile(context, file, comeBack);
        } else if (prefix.equals("jpg") || prefix.equals("png")) {
            uploadImage(context, file, isAddWaterMark, comeBack);
        }
    }

    /**
     * 开始上传
     *
     * @param file     图片数据
     * @param token    token
     * @param comeBack 回调
     */
    private void startUpload(final String file, final String token, final QiNiuManager.ComeBack comeBack) {
        uploadLastTimePoint = System.currentTimeMillis();
        uploadLastOffset = 0;
        isCancelled = false;

        // 进度
        final UpProgressHandler progressHandler = (key, percent) -> mHandler.post(() -> {
            long now = System.currentTimeMillis();
            long deltaTime = now - uploadLastTimePoint;
            long currentOffset = (long) (percent * file.length());
            long deltaSize = currentOffset - uploadLastOffset;
            if (deltaTime <= 100) {
                return;
            }

            final String speed = formatSpeed(deltaSize, deltaTime);

            // update
            uploadLastTimePoint = now;
            uploadLastOffset = currentOffset;

            int progress = (int) (percent * 100);
            comeBack.progress(speed, progress);
        });

        // 结果
        final UpCompletionHandler completionHandler = (key, info, response) -> mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!info.isOK() || !response.has(KEY)) {
                    fail();
                    onDestroy();
                    return;
                }

                try {
                    String key = response.getString(KEY);
                    if (!TextUtils.isEmpty(key)) {
                        String url = HOST + key;
                        comeBack.result(info.isOK(), url);

                        ULog.i("upload image url=" + url);
                    } else {
                        fail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    fail();
                }

                onDestroy();
            }

            private void fail() {
                onDestroy();
                comeBack.result(false, null);
            }
        });

        // 是否取消上传
        final UpCancellationSignal cancellationSignal = () -> isCancelled;

        // 设置文件名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Random rnd = new Random();
        int num = rnd.nextInt(89999) + 10000;
        //判断后缀名
        String key = null;
        String prefix = file.substring(file.lastIndexOf(".") + 1);
        if (prefix.equals("wav") || prefix.equals("mp3")) {
            key = "audio_" + sdf.format(new Date()) + "_Android_" + num;
        } else if (prefix.equals("mp4") || prefix.equals("3gp")) {
            key = "video_" + sdf.format(new Date()) + "_Android_" + num;
        } else if (prefix.equals("jpg") || prefix.equals("png")) {
            key = "photo_" + sdf.format(new Date()) + "_Android_" + num;
        }

        mUploadManager.put(file, key, token, completionHandler, new UploadOptions(null, null, false, progressHandler, cancellationSignal));

    }

    /**
     * 上传任意文件
     *
     * @param context  上下文
     * @param filePath 地址
     * @param comeBack 回调事件
     */
    private void uploadFile(Context context, String filePath, QiNiuManager.ComeBack comeBack) {
        if (context == null) {
            throw new NullPointerException("context be not can null");
        }
        if (comeBack == null) {
            throw new NullPointerException("uploadImage be not can null");
        }
        if (TextUtils.isEmpty(filePath)) {
            throw new NullPointerException("filePath be not can null");
        }
        toGetToken(filePath, comeBack);
    }

    /**
     * 上传图片
     *
     * @param filePath       上传文件路径
     * @param comeBack       回调
     * @param isAddWaterMark 是否要添加水印
     */
    private void uploadImage(@Nullable final Context context, @NonNull final String filePath, final boolean isAddWaterMark, @NonNull final QiNiuManager.ComeBack comeBack) {
        if (context == null) {
            throw new NullPointerException("context be not can null");
        }
        if (TextUtils.isEmpty(filePath)) {
            throw new NullPointerException("filePath be not can null");
        }

        // luBan压缩图片
        OnCompressListener onCompressListener = new OnCompressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(File file) {
                if (isAddWaterMark) {
                    addWaterMark(context, file.getAbsolutePath(), comeBack);
                } else {
                    toGetToken(file.getAbsolutePath(), comeBack);
                }
            }

            @Override
            public void onError(Throwable e) {
                onDestroy();
                comeBack.result(false, null);
            }
        };

        File file = new File(filePath);
        luBan(context, file, onCompressListener);
    }

    /**
     * 获取token
     *
     * @param file     文件
     * @param comeBack 回调事件
     */
    private void toGetToken(String file, QiNiuManager.ComeBack comeBack) {
        //检测token是否过期
        QiNiuToken token = getCacheToken();
        if (token == null) {
            getTokenAndUpload(file, comeBack);
        } else {
            startUpload(file, token.getData(), comeBack);
        }
    }

    /**
     * 获取缓存token
     *
     * @return token实体
     */
    private QiNiuToken getCacheToken() {
        String str = SPUtils.getInstance().getString(TOKEN);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        QiNiuToken token = new Gson().fromJson(str, QiNiuToken.class);
        // 判断缓存是否过期
        if ((System.currentTimeMillis() - token.getTime()) / 1000 > 120) {
            SPUtils.getInstance().clearStringKey(TOKEN);
            return null;
        }
        return token;
    }

    /**
     * 获取token并上传
     *
     * @param filePath 上传文件路径
     * @param comeBack 回调
     */
    private void getTokenAndUpload(final String filePath, final QiNiuManager.ComeBack comeBack) {

        BucketName bucketName = new BucketName();
        bucketName.setBucketName("images");

        mCompositeDisposable.add(ApiDefault.getInstance().retrofit.create(QiNiuManager.Net.class).getCode(bucketName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> {
                    if (token.getData() != null) {
                        // 保存token
                        token.setTime(System.currentTimeMillis());
                        saveToken(token);
                        // 开始上传
                        startUpload(filePath, token.getData(), comeBack);
                    }
                }, throwable -> {
                    onDestroy();
                    comeBack.result(false, null);
                }));
    }

    /**
     * 上传速度格式化
     *
     * @param deltaSize   deltaSize
     * @param deltaMillis deltaMillis
     * @return 速度
     */
    private String formatSpeed(double deltaSize, double deltaMillis) {
        double speed = deltaSize * 1000 / deltaMillis / FZ_KB;
        String result = String.format(Locale.getDefault(), "%.2f KB/s", speed);
        if ((int) speed > FZ_KB) {
            result = String.format(Locale.getDefault(), "%.2f MB/s", speed
                    / FZ_KB);
        }
        return result;
    }

    /**
     * 这里做处理水印和鲁班压缩
     *
     * @param context  context
     * @param filePath 上传文件路径
     * @param comeBack 回调
     */
    private void addWaterMark(Context context, final String filePath, final QiNiuManager.ComeBack comeBack) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        mCompositeDisposable.add(addWaterMark(context, bitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bytes -> {

                    // 保存文件
                    File file = new File(getPath() + UUID.randomUUID() + ".jpg");
                    boolean isSave = FileUtils.writeFileFromBytes(file, bytes, false);
                    if (!isSave) {
                        onDestroy();
                        comeBack.result(false, null);
                    }

                    // 压缩
                    luBan(context, file, new OnCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(File file) {
                            toGetToken(file.getAbsolutePath(), comeBack);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
                }));
    }

    /**
     * 鲁班压缩
     *
     * @param context
     * @param file
     * @param onCompressListener
     */
    private void luBan(@Nullable Context context, @NonNull File file, OnCompressListener onCompressListener) {
        Luban.with(context)
                .load(file)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .setCompressListener(onCompressListener)
                .launch();
    }

    /**
     * 添加水印
     *
     * @param context context
     * @param bitmap  图片的bitmap
     */
    private Observable<byte[]> addWaterMark(Context context, Bitmap bitmap) {
        Bitmap stamp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_sy_white);
        final RubberStampConfig config = new RubberStampConfig.RubberStampConfigBuilder()
                .base(bitmap)
                .rubberStampPosition(RubberStampPosition.TILE)
                .rubberStamp(stamp)
                .margin(10, 10)
                .build();

        RubberStamp rubberStamp = new RubberStamp(context);
        return Observable.just(rubberStamp).map(rubberStamp1 -> {
            Bitmap localBitmap = rubberStamp1.addStamp(config);
            // bitmap转byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            // 销毁原本的bitmap
            if (!bitmap.isRecycled()) {
                bitmap.recycle();   //回收图片所占的内存
                System.gc();  //提醒系统及时回收
            }
            return baos.toByteArray();
        });

    }

    /**
     * @return 获取鲁班压缩后的文件夹
     */
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    /**
     * 保存缓存token
     *
     * @param token token实体
     */
    private void saveToken(QiNiuToken token) {
        if (token != null) {
            String json = new Gson().toJson(token);
            SPUtils.getInstance().put(TOKEN, json);
        }
    }



    /**
     * 销毁时的处理
     */
    public void onDestroy() {
        if (isCancelled)
            isCancelled = false;
    }


}
