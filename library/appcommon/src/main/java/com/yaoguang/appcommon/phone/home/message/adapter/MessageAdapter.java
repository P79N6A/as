package com.yaoguang.appcommon.phone.home.message.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yaoguang.appcommon.phone.home.message.messagechild.MessageChildFragment;

import java.util.ArrayList;

/**
 * 关联查询适配器
 * Created by zhongjh on 2017/4/13.
 */
public class MessageAdapter extends FragmentPagerAdapter {
    String[] mTitles = new String[]{"企业消息", "平台公告"};
    public ArrayList<MessageChildFragment> mMessageChildFragment = new ArrayList<>();
    String mCodeType;

    public MessageAdapter(FragmentManager fm, String codeType) {
        super(fm);
        mCodeType = codeType;
    }

    @Override
    public Fragment getItem(int position) {
        MessageChildFragment messageChildFragment = MessageChildFragment.newInstance(position, mCodeType);
        mMessageChildFragment.add(messageChildFragment);
        return messageChildFragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }


}
