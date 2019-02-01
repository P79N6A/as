package com.yaoguang.driver.phone.home.wiget;

import android.content.Context;
import android.graphics.Point;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.yaoguang.appcommon.phone.home.adapter.LocalImageHolderView;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.BannerPic;

import java.util.ArrayList;

/**
 * Created by 韦理英
 * on 2017/6/17.
 */

public class Banner {

    private final ConvenientBanner convenientBanner;

    public Banner(Context context, ConvenientBanner convenientBanner) {
        this.convenientBanner = convenientBanner;

        //动态设置convenientBanner高度
        //设置图片宽高比
        float scale = (float) 360 / (float) 170;
        //获取屏幕的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getSize(size);
        }
        int screenWidth = size.x;
        //计算BGABanner的应有高度
        int viewHeight = Math.round(screenWidth / scale);
        //设置BGABanner的宽高属性
        ViewGroup.LayoutParams banner_params = convenientBanner.getLayoutParams();
        banner_params.width = screenWidth;
        banner_params.height = viewHeight;
        convenientBanner.setLayoutParams(banner_params);

    }

    public void netLoad(ArrayList<BannerPic> result) {
        convenientBanner.setPages(
                LocalImageHolderView::new, result).startTurning(5000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

}
