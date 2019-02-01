package com.yaoguang.driver.phone.order.detail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.driver.R;

/**
 * 装箱 跟 拆箱
 * Created by zhongjh on 2018/5/22.
 */
public class OrderDetailAdapter extends PagerAdapter {

    private Context mContext;
    private String[] mTitles = new String[]{"拆箱任务", "装箱任务"};

    public OrderDetailAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_order_detail_box, null, false);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }


}
