package com.yaoguang.driver.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yaoguang.driver.my.message.HomePlatformMessageFragment;
import com.yaoguang.driver.order.home.HomeOrderMessageChildFragment;

/**
 * Created by 韦理英
 * on 2017/7/12 0012.
 */

public class HomeMessageAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"业务消息","平台公告"};

    public HomeMessageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HomeOrderMessageChildFragment.newInstance(4);
        }
        return HomePlatformMessageFragment.newInstance();
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
