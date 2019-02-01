package com.yaoguang.lib.common.imagepicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.yaoguang.lib.R;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;

import org.jetbrains.annotations.NotNull;

import static com.yaoguang.lib.common.constants.Constants.APP_WANTU_SIZE;


/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class GlideManager {

    private static GlideManager instance;

    public static synchronized GlideManager getInstance() {
        if (instance == null) {
            instance = new GlideManager();
        }
        return instance;
    }

    public void withRounded(final Context context, String imageUrl, final ImageView imageView) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, true);
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(imageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public void withRounded(final Context context, String imageUrl, final ImageView imageView, int downFail) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, true);
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(downFail)
                        .error(downFail)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });

    }

    public void withRounded(final Context context, String imageUrl, int width, int height, final ImageView imageView) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, true);
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_loading_tp)
                        .error(R.drawable.ic_loading_tp)
                        .centerCrop()
                        .override(width, height)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public void withSquare(final Context context, String imageUrl, final ImageView imageView) {
        withSquare(context, imageUrl, imageView, R.drawable.ic_loading_tp);
    }

    public void withSquare(final Context context, String imageUrl, final ImageView imageView, int failImage) {
        withSquare(context, imageUrl, imageView, failImage, failImage, true);
    }

    /**
     * 图片下载
     *
     * @param context       上下文
     * @param imageUrl      图片url
     * @param imageView     imageView
     * @param loading       加载中图片
     * @param failImage     加载失败图片
     * @param isUrlCompress 是否使用url压缩图片 true是 false是否
     */
    public void withSquare(final Context context, String imageUrl, final ImageView imageView, int loading, int failImage, boolean isUrlCompress) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, isUrlCompress);
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(loading)
                        .error(failImage)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    /**
     * 图片下载
     *
     * @param context       上下文
     * @param imageUrl      图片url
     * @param imageView     imageView
     * @param loading       加载中图片
     * @param failImage     加载失败图片
     * @param isUrlCompress 是否使用url压缩图片 true是 false是否
     */
    public void withNoneCache(final Context context, String imageUrl, final ImageView imageView, int loading, int failImage, boolean isUrlCompress) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, isUrlCompress);
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(imageView);
    }

    public void withSquare(final Context context, String imageUrl, int width, int height, int placeHolder, int error, final ImageView imageView) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, true);
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(imageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(placeHolder)
                        .error(error)
                        .override(width, height)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {

                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public void withSquare(final Context context, String imageUrl, int width, int height, final ImageView imageView, int loading, int loadingFail) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, true);
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(imageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(loading == 0 ? R.drawable.ic_loading_tp : loading)
                        .error(loadingFail == 0 ? R.drawable.ic_loading_tp : loading)
                        .override(width, height)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public void withSquare(final Context context, String imageUrl, int width, int height, final ImageView imageView) {
        withSquare(context, imageUrl, width, height, imageView, R.drawable.ic_ic_tpjzz_b, R.drawable.ic_jzsb_01);
    }

    /**
     * 加载图片 压缩自定义
     */
    public void withCompress(final Context context, String imageUrl, int w, int h, int loading, final ImageView imageView) {
        if (imageUrl == null)
            return;
        //如果是本地的
        if (imageUrl.contains(Constants.SDK_PATH.toString())) {
            //如果存在http,就直接返回，如果不存在，就添加
            if (imageUrl.contains("http")) {
            } else {
                imageUrl = Constants.HEAD + imageUrl;
            }
        }

        if (imageUrl.startsWith("http://yga.image.alimmdn.com")) {
            imageUrl = imageUrl + "@" + w + "w_" + h + "h_90Q.jpg";
        }

        if (loading != -1) {
            Glide.with(context)
                    .asBitmap() // some .jpeg files are actually gif
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .placeholder(loading)
                            .error(loading)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {

                            imageView.setImageBitmap(resource);
                        }
                    });
        } else {
            Glide.with(context)
                    .asBitmap() // some .jpeg files are actually gif
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {

                            imageView.setImageBitmap(resource);
                        }
                    });
        }
    }

    /**
     * 加载原图 不要压缩
     *
     * @param context   上下文
     * @param imageUrl  url
     * @param imageView view
     */
    public void withArtwork(final Context context, String imageUrl, final ImageView imageView) {
        if (imageUrl == null)
            return;
        //如果是本地的
        if (imageUrl.contains(Constants.SDK_PATH.toString()))
            //如果存在http,就直接返回，如果不存在，就添加
            if (imageUrl.contains("http")) {
            } else {
                imageUrl = Constants.HEAD + imageUrl;
            }

        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_ic_tpjzz_b)
                        .error(R.drawable.ic_jzsb_01)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {

                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public void withRounded(final Fragment fragment, String imageUrl, final ImageView imageView) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, true);
        Glide.with(fragment)
                .asBitmap() // some .jpeg files are actually gif
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_loading_tp)
                        .error(R.drawable.ic_loading_tp)
                        .centerCrop())
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(fragment.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public void with(Context context, String imageUrl, ImageView imageView) {
        with(context, imageUrl, imageView, null);
    }

    public RequestManager withCanCancle(Context context, String imageUrl, ImageView imageView, int placeHolder) {
        if (imageUrl == null)
            return null;
        imageUrl = getImageUrl(imageUrl, true);

        RequestManager with = Glide.with(context);
        with.load(imageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(placeHolder)
                        .error(placeHolder))
                .into(imageView);
        return with;
    }

    public void with(Context context, String imageUrl, ImageView imageView, Drawable drawable) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, true);

        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .error(drawable)
                        .centerCrop())
                .into(imageView);
    }

    public void with(Context context, int imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }

    public void withRounded(final Fragment fragment, Bitmap bitmap, final ImageView imageView) {
        Glide.with(fragment)
                .load(bitmap)
                .apply(new RequestOptions()
                        .centerCrop())
                .into(imageView);
    }

    public void withNoneCache(Context context, String imageUrl, ImageView imageView) {
        if (imageUrl == null)
            return;
        imageUrl = getImageUrl(imageUrl, true);
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(imageView);
    }

    /**
     * @param imageUrl 图片地址
     * @return 处理后的图片地址
     */
    public static String getImageUrl(String imageUrl) {
        return getImageUrl(imageUrl, false);
    }

    /**
     * 返回地址
     */
    public static String getImageUrl(String imageUrl, boolean isUrlCompress) {
        if (imageUrl == null)
            return null;

        //如果是本地的
        if (imageUrl.startsWith(Constants.SDK_PATH.toString()) || imageUrl.startsWith(BaseApplication.getInstance().getCacheDir().toString()))
            return imageUrl;

        // 使用地址压缩图片
        if (isUrlCompress) {
            imageUrl = checkWantu(imageUrl);
        }
        //如果存在http,就直接返回，如果不存在，就添加
        if (imageUrl.contains("http")) {
            return imageUrl;
        } else {
            return Constants.HEAD + imageUrl;
        }
    }

    /**
     * 检测是否是wantu地址
     * 如果是，就加结尾地址
     * 如果不是就不加
     *
     * @param url 网址
     */
    public static String checkWantu(@NotNull String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }

        if (url.startsWith("http://yga.image.alimmdn.com") && !url.contains(APP_WANTU_SIZE)) {
            return url + APP_WANTU_SIZE;
        }
        return url;
    }
}
