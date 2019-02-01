package com.yaoguang.lib.common.file;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.yaoguang.lib.appcommon.dialog.helper.DialogUtil;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.permissions.impl.PermissionsManager;

import java.net.Proxy;

/**
 * Created by 韦理英
 * on 2017/7/5 0005.
 */

public class FileDownloaderUtils {

    public static void download(final Context context, String url, String sdpath, boolean isDir, final Runnable runnable) {
        if (PermissionsManager.getInstance().checkWriteExternalPermission((Activity) context, true) && PermissionsManager.getInstance().checkReadExternalPermission((Activity) context, true))
            FileDownloader.getImpl().create(url)
                    .setPath(sdpath, isDir)
                    .setCallbackProgressTimes(300)
                    .setMinIntervalUpdateSpeed(400)
                    .setListener(new FileDownloadSampleListener() {

                        @Override
                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            super.pending(task, soFarBytes, totalBytes);
                            DialogUtil.showProgressDialog(context, "下载中，请稍候");
                            ULog.i("pending");
                        }

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            super.progress(task, soFarBytes, totalBytes);

                            DialogUtil.setProgress(totalBytes, soFarBytes);
                            ULog.i("progress: soFarBytes" + soFarBytes + "totalBytes:" + totalBytes + "task.getSpeed()" + task.getSpeed());
                        }

                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                            super.error(task, e);
                            ULog.i("error:" + e.getMessage());
                            DialogUtil.hideDialog();
                        }

                        @Override
                        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                            ULog.i("connected");
                        }

                        @Override
                        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            super.paused(task, soFarBytes, totalBytes);
                            ULog.i("paused");
                        }

                        @Override
                        protected void completed(BaseDownloadTask task) {
                            super.completed(task);
                            DialogUtil.hideDialog();
                            runnable.run();
                            ULog.i("completed");
                        }

                        @Override
                        protected void warn(BaseDownloadTask task) {
                            super.warn(task);
                            ULog.i("warn");
                        }
                    }).start();
    }

    public static void init(Application app) {
        FileDownloader.setupOnApplicationOnCreate(app)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                        .proxy(Proxy.NO_PROXY) // set proxy
                ))
                .commit();
    }
}
