package com.yaoguang.appcommon.phone.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.greendao.entity.BannerPic;

/**
 * Created by zhongjh on 2017/6/8.
 */
public class LocalImageHolderView implements Holder<BannerPic> {

    private ImageView imageView;
    private Context mContext;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        mContext = context;

        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, BannerPic data) {
        Glide.with(context)
                .load(GlideManager.getImageUrl(data.getPicAddr() + "?imageView2/1/w/1080/h/510", true))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_jzsb)
                        .error(R.drawable.ic_jzsb))
                .into(imageView);
    }

}