package com.yaoguang.lib.common;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ShiXiuwen on 2017/1/14.
 * <p>
 * Description:将程序的Crash信息写进文件
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private volatile static CrashHandler instance;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private final static String CRASH_PATH = "YaoGuangApp/crash_path";

    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    public void init(Context ctx) {
        mContext = ctx.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            Throwable cause = e.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss", Locale.CHINA);
            String format = sdf.format(new Date());

            File file = new File(getDiskCacheDir(mContext, CRASH_PATH), "crash-" + format + ".txt");
            if (file.exists() && file.length() > 5 * 1024 * 1024) {     // <5M
                boolean delete = file.delete();
            }

            File folder = new File(getDiskCacheDir(mContext, CRASH_PATH));
            if (folder.exists() && folder.isDirectory()) {
                if (!file.exists()) {
                    boolean newFile = file.createNewFile();
                    if (!newFile) {
                        return;
                    }
                }
            } else {
                boolean mkDir = folder.mkdir();
                if (mkDir) {
                    boolean newFile = file.createNewFile();
                    if (!newFile) {
                        return;
                    }
                }
            }
            FileOutputStream fos = new FileOutputStream(file, true);
            byte[] bytes = (format +        //追加Crash时间
                    "\n" + result +   //Crash内容
                    "\n" + "######## ~~~ T_T ~~~ ########" +    //分割线
                    "\n").getBytes();
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        mDefaultHandler.uncaughtException(t, e);    //该代码不执行的话程序无法终止
    }

    /**
     * @param context context
     * @param dirName dirName
     * @return dir
     */
    private static String getDiskCacheDir(Context context, String dirName) {
        String cachePath = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            File externalCacheDir = Environment.getExternalStorageDirectory().getAbsoluteFile();
            if (externalCacheDir != null) {
                cachePath = externalCacheDir.getPath();
            }
        }
        if (cachePath == null) {
            File cacheDir = Environment.getExternalStorageDirectory().getAbsoluteFile();
            if ((cacheDir != null) && (cacheDir.exists())) {
                cachePath = cacheDir.getPath();
            }
        }
        //0/emulate/Android/data/com.***.***/crash/crash.log
        return cachePath + File.separator + dirName;
    }

    public void sendServer() {

    }
}