package com.yaoguang.lib.common.file;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.yaoguang.lib.appcommon.utils.AppUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhongjh on 2018/6/26.
 */

public class MediaUtils {

    /**
     * 选择文件
     */
    public static void selectFile(Activity activity, int resultType) {
        Intent getFileIntent = new Intent();
        if (Build.VERSION.SDK_INT >= 19) {
            getFileIntent.setAction("android.intent.action.OPEN_DOCUMENT");
        } else {
            getFileIntent.setAction(Intent.ACTION_GET_CONTENT);
        }
        getFileIntent.setType("*/*");
        getFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            activity.startActivityForResult(getFileIntent, resultType);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BaseApplication.getInstance(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开网络和下载音频
     *
     * @param context 上下文
     * @param url     音频地址
     */
    public static void openAudio(final Context context, String url) {
        if (context == null) return;
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(BaseApplication.getInstance(), "暂无录音", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取后缀名
        String suffixName = ".mp3";
        if (!TextUtils.isEmpty(url) && url.contains(".mp3")) {
            suffixName = ".mp3";
        }

        // 获取文件名
        String fileName = url.hashCode() + suffixName;

        // 开始下载，如果已下载，就打开
        final String path = Constants.SDK_PATH_AUDIO + "/";
        if (FileUtils.isFileExists(path + fileName)) {
            MediaUtils.openAudio((Activity) context, path, fileName);
        } else {
            FileDownloaderUtils.download(context, url, path + fileName, false,
                    () -> MediaUtils.openAudio((Activity) context, path, fileName));
        }
    }

    /**
     * 打开网络和下载视频
     *
     * @param context 上下文
     * @param url     视频地址
     */
    public static void openVideo(final Context context, String url) {
        if (context == null) return;
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(BaseApplication.getInstance(), "暂无录像", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取后缀名
        String suffixName = ".mp4";
        if (!TextUtils.isEmpty(url) && url.contains(".3gp")) {
            suffixName = ".3gp";
        }
        // 获取文件名
        String fileName = url.hashCode() + suffixName;

        // 开始下载，如果已下载就打开
        final String path = Constants.SDK_PATH_VIDEO + "/";
        if (FileUtils.isFileExists(path + fileName)) {
            MediaUtils.openVideo((Activity) context, path, fileName);
        } else {
            FileDownloaderUtils.download(context, url, path + fileName, false,
                    () -> MediaUtils.openVideo((Activity) context, path, fileName));
        }
    }

    /**
     * 打开本地视频
     *
     * @param activity 上下文
     * @param path     地址
     * @param name     文件名
     */
    private static void openVideo(Activity activity, String path, String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = "video/mp4";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            File file = new File(path, name);
            Uri contentUri = FileProvider.getUriForFile(activity, AppUtils.getAppPackageName() + ".fileprovider", file);
            intent.setDataAndType(contentUri, type);
        } else {
            Uri uri = Uri.parse("file:///" + path + name);
            intent.setDataAndType(uri, type);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }

    /**
     * 打开本地音频
     *
     * @param activity 上下文
     * @param path     地址
     * @param fileName 文件名
     */
    private static void openAudio(Activity activity, String path, String fileName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = "audio/x-mpeg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            File file = new File(path, fileName);
            Uri contentUri = FileProvider.getUriForFile(activity, AppUtils.getAppPackageName() + ".fileprovider", file);
            intent.setDataAndType(contentUri, type);
        } else {
            Uri uri = Uri.parse("file:///" + path + fileName);
            intent.setDataAndType(uri, type);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }

    /**
     * 拍摄视频
     *
     * @param activity    窗体
     * @param requestCode 请求码
     * @return 文件路径
     */
    public static String actionVideo(Activity activity, int requestCode) {
        String filePath = Constants.SDK_PATH_VIDEO;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        // 判断存储卡是否可以用，可用进行存储
        File file = new File(filePath, "VID_" + timeStamp + ".3gp");
        FileUtils.createOrExistsDir(filePath);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.getUriForFile(activity, file));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra("filefilefile", file.getPath());
        activity.startActivityForResult(intent, requestCode);
        return file.getPath();
    }

}
