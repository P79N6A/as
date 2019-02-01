package com.yaoguang.appcommon.phone.activitys.welcome;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class GuidanceAdapter extends PagerAdapter {
    private final View[] view;

    public GuidanceAdapter(View[] view) {
        this.view = view;
    }

    @Override
    public int getCount() {
        return view.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(view[position]);
        return view[position];
    }
}
