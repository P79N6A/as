package com.yaoguang.lib.common.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class VideoUrl {

    public void ShowVideoThumbnail(final String url, final Handler handler, final Context context, final ImageView ivVideo) {
        // 显示视频缩略图
//        Bitmap mBitmapVideo = VideoUrl.createVideoThumbnail(url, MediaStore.Images.Thumbnails.MICRO_KIND);
//        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        mBitmapVideo.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                Glide.with(context)
//                        .load(stream.toByteArray())
//                        .into(ivVideo);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Bitmap mBitmapVideo = VideoUrl.createVideoThumbnail(url, MediaStore.Images.Thumbnails.MICRO_KIND);
                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (mBitmapVideo == null) return;
                mBitmapVideo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                final byte[] bytes = stream.toByteArray();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(context)
                                .load(bytes)
                                .into(ivVideo);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 500);
    }

    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null) return null;

        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    96,
                    96,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

}
