package com.yaoguang.driver.phone.main;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yaoguang.appcommon.common.eventbus.TabSelectedEvent;
import com.yaoguang.driver.R;
import com.yaoguang.driver.phone.home.HomeFragment;
import com.yaoguang.driver.phone.home.wiget.BottomBar;
import com.yaoguang.driver.phone.home.wiget.BottomBarTab;
import com.yaoguang.driver.phone.my.my.MyFragment;
import com.yaoguang.driver.phone.order.OrderFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 首页支持3个不同的tab
 */
public class MainFragment extends SupportFragment {

    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private SupportFragment[] mFragments = new SupportFragment[3];

    private BottomBar mBottomBar;
    private RelativeLayout mRlMain;

    private int mSysMsgNumber; // 系统消息数量
    private int mOrderMsgNumber; // 订单消息数量
    private int mContactCount;// 关联消息数量


    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = OrderFragment.newInstance();
            mFragments[THIRD] = MyFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(OrderFragment.class);
            mFragments[THIRD] = findChildFragment(MyFragment.class);
        }
    }

    private void initView(View view) {
        mBottomBar = view.findViewById(R.id.bottomBar);
        mRlMain = view.findViewById(R.id.rlMain);

        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_home_normal, getString(R.string.home)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_dingdan_normal, getString(R.string.order)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_myself_normal, getString(R.string.my)));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);

//                // 解决首页滚动问题，有时会滚动到[最新消息处],重置到顶部
//                if (getFragmentManager() != null && getFragmentManager().getFragments() != null && getFragmentManager().getFragments().get(0) instanceof HomeFragment) {
//                    HomeFragment homeFragment = (HomeFragment) getFragmentManager().getFragments().get(0);
//                    homeFragment.toScrollHeadView();
//                }

                // 改变图标
                mBottomBar.getItem(position).getIcon().setImageResource(
                        (new int[]{R.drawable.ic_home_click, R.drawable.ic_dingdan_click, R.drawable.ic_myself_click})[position]);
                mBottomBar.getItem(prePosition).getIcon().setImageResource(
                        (new int[]{R.drawable.ic_home_normal, R.drawable.ic_dingdan_normal, R.drawable.ic_myself_normal})[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });

        // 初始化选择
        mBottomBar.getItem(0).getIcon().setImageResource(
                (new int[]{R.drawable.ic_home_click, R.drawable.ic_dingdan_click, R.drawable.ic_myself_click})[0]);
        mBottomBar.getItem(1).getIcon().setImageResource(
                (new int[]{R.drawable.ic_home_normal, R.drawable.ic_dingdan_normal, R.drawable.ic_myself_normal})[1]);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        // 切换竖屏
        _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        handleResult(requestCode, resultCode, data);
    }

    /**
     * 递归调用，对 子Fragement生效
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        数据源
     */
    private void handleResult(int requestCode, int resultCode,
                              Bundle data) {
        List<Fragment> frags = getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null) {
                    if (f instanceof ISupportFragment) {
                        ISupportFragment iSupportFragment = (ISupportFragment) f;
                        iSupportFragment.onFragmentResult(requestCode, resultCode, data);
                    }
                }
            }
        }
    }

    /**
     * 描述：显示未读数红点
     */
    private void showUnreadNumber() {
        BottomBarTab tab = mBottomBar.getItem(THIRD);
        tab.showUnreadCount();
    }

    /**
     * 描述：隐藏未读数红点
     */
    private void hideUnreadNumber() {
        BottomBarTab tab = mBottomBar.getItem(THIRD);
        tab.hideUnreadCount();
    }

    /**
     * 同时检查公告消息和关联，是否显示红点
     */
    private void isUnreadNumber() {
        if (mSysMsgNumber > 0 || mOrderMsgNumber > 0 || mContactCount > 0){
            showUnreadNumber();
        }else{
            hideUnreadNumber();
        }
    }

    /**
     * 赋值系统消息和订单业务消息，用于计算底部红点
     */
    public void setMessageCount(int sysMsgNumber, int orderMsgNumber) {
        mSysMsgNumber = sysMsgNumber;
        mOrderMsgNumber = orderMsgNumber;
        isUnreadNumber();
    }

    /**
     * 关联消息的数据量
     * @param contactCount 关联消息的数据量
     */
    public void setContactCount(int contactCount){
        mContactCount = contactCount;
        isUnreadNumber();
    }

    /**
     * 切换tab
     */
    public void changeTab(int tabId) {
        mBottomBar.setCurrentItem(tabId);
    }

    /**
     * 我的 - 上传对话框需要使用
     *
     * @return
     */
    public RelativeLayout getRlMain() {
        return mRlMain;
    }
}
