package com.yaoguang.appcommon.phone.home.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.phone.home.message.adapter.MessageAdapter;
import com.yaoguang.appcommon.phone.home.message.eventbus.child.IsEditEvent;
import com.yaoguang.appcommon.phone.home.message.eventbus.child.RefreshDataEvent;
import com.yaoguang.appcommon.phone.home.message.eventbus.main.UpdateMenuEvent;
import com.yaoguang.lib.appcommon.widget.viewpager.ControlSlipViewPager;
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.view.TabLayoutUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 消息列表
 * Created by zhongjh on 2017/4/13.
 */
public class MessageFragment extends BaseFragment implements Toolbar.OnMenuItemClickListener {

    private static final String ARG_CODETYPE = "arg_codeType";
    private static final String ARG_TYPE = "arg_Type";
    public static final int REQUEST_HTMLFRAGMENT = 1; // 这是打开信息详情的请求码


    String mCodeType;
    /**
     * 判断类型,0企业消息 1平台公告
     */
    int mType;
    boolean mIsEdit;//是否编辑中

    InitialView mInitialView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册成为订阅者
        EventBus.getDefault().register(this);
        Bundle args = getArguments();
        if (args != null) {
            mCodeType = args.getString(ARG_CODETYPE);
            mType = args.getInt(ARG_TYPE, 0);
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    /**
     * 实例化
     *
     * @param codeType Constants.APP_COMPANY
     * @param type     0代表滑动到第一个，1代表滑动到第二个
     * @return 实例化
     */
    public static MessageFragment newInstance(String codeType, int type) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CODETYPE, codeType);
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mInitialView = new InitialView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        // 解除注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case (REQUEST_HTMLFRAGMENT):
                    // 通知child刷新某个item
                    String codeType = data.getString("codeType");
                    String id = data.getString("id");
                    String position = data.getString("position");
                    String type = data.getString("type");
                    EventBus.getDefault().post(new RefreshDataEvent(codeType, id, position, type));
                    break;
            }
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mIsEdit) {
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }

    // 获取选择的
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateMenuEvent(UpdateMenuEvent updateMenuEvent) {
        if (updateMenuEvent.getType() == mType) {
            isShowEditMenu(mType);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_edit) {
            //通知child开启编辑
            EventBus.getDefault().post(new IsEditEvent(true));
            mInitialView.mToolbar.getMenu().clear();
            initToolbarNav(mInitialView.mToolbar, mInitialView.toolbar_title.getText().toString(), R.menu.message_cancel, MessageFragment.this);
            //禁止返回
            mIsEdit = true;
            //禁止滑动
            mInitialView.mViewPager.setPagingEnabled(false);
            TabLayoutUtils.setEnable(mInitialView.mTabLayout, false);
            //隐藏返回按钮
            mInitialView.mImgReturn.setVisibility(View.GONE);
        } else if (i == R.id.action_cancel) {
            //通知child关闭编辑模式
            EventBus.getDefault().post(new IsEditEvent(false));
            mInitialView.mToolbar.getMenu().clear();
            initToolbarNav(mInitialView.mToolbar, mInitialView.toolbar_title.getText().toString(), R.menu.message_edit, MessageFragment.this);
            //允许返回
            mIsEdit = false;
            //允许滑动
            mInitialView.mViewPager.setPagingEnabled(true);
            TabLayoutUtils.setEnable(mInitialView.mTabLayout, true);
            //显示返回按钮
            mInitialView.mImgReturn.setVisibility(View.VISIBLE);
        }
        return true;
    }

    /**
     * 判断是否显示编辑按钮
     *
     * @param i 索引
     */
    private void isShowEditMenu(int i) {
        mInitialView.mToolbar.getMenu().clear();
        //判断是否编辑中
        if (mIsEdit) {
            mInitialView.mToolbar.inflateMenu(R.menu.message_cancel);
        } else {
            //判断有没有数据,有才显示编辑
            if (mInitialView.mMessageAdapter.mMessageChildFragment.get(i).mMessageChildAdapter.getList().size() > 0)
                mInitialView.mToolbar.inflateMenu(R.menu.message_edit);
        }
        mInitialView.mToolbar.setOnMenuItemClickListener(MessageFragment.this);
        mInitialView.mImgReturn.setVisibility(View.VISIBLE);
    }

    public class InitialView {
        View mView;
        TextView toolbar_title;
        MessageAdapter mMessageAdapter;

        Toolbar mToolbar;
        TabLayout mTabLayout;
        ImageView mImgReturn;
        com.yaoguang.lib.appcommon.widget.viewpager.ControlSlipViewPager mViewPager;

        InitialView(View view) {
            mView = view;
            mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
            toolbar_title = (TextView) view.findViewById(R.id.toolbar_title);
            mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
            mViewPager = (ControlSlipViewPager) view.findViewById(R.id.viewPager);
            mImgReturn = (ImageView) view.findViewById(R.id.imgReturn);
            initUISetting();
            initListener();
        }

        private void initUISetting() {
            initToolbarNav(mToolbar, "消息中心", R.menu.message_edit, MessageFragment.this);

            mTabLayout.addTab(mTabLayout.newTab().setText("企业消息"));
            mTabLayout.addTab(mTabLayout.newTab().setText("平台公告"));
            mViewPager.setOffscreenPageLimit(1);
            mMessageAdapter = new MessageAdapter(getChildFragmentManager(), mCodeType);
            mViewPager.setAdapter(mMessageAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }

        private void initListener() {
            mViewPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (mType == 1)
                        mInitialView.mViewPager.setCurrentItem(1);
                    return true;
                }
            });

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }


                @Override
                public void onPageSelected(int i) {
                    isShowEditMenu(i);
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

}
