package com.yaoguang.driver.phone.my.message;

import android.os.Bundle;
import android.view.View;

import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.driver.R;
import com.yaoguang.driver.phone.my.message.adapter.MessageAdapter;

import org.greenrobot.eventbus.EventBus;

/**
 * 首页平台公告
 * Created by 韦理英
 * on 2017/7/6.
 */

public class HomePlatformMessageFragment extends PlatformMessageFragment {
    public static final String LOOK_PLATFORM_MESSAGE = "查看平台消息";

    public static HomePlatformMessageFragment newInstance() {
        HomePlatformMessageFragment homePlatformMessageFragment = new HomePlatformMessageFragment();
        Bundle bundle = new Bundle();
        homePlatformMessageFragment.setArguments(bundle);
        return homePlatformMessageFragment;
    }

    public void customSetting() {
        mViewDelete.llSelectDelete.setVisibility(View.GONE);
        // 首页订单，空白页解决，gone后，只有订单页的大小，不会出现空白页;
        recyclerView.setVisibility(View.GONE);
        mToolbarCommonBinding.toolbar.setVisibility(View.GONE);
        // TODO
//        mDataBinding.tvNoMessage.setVisibility(View.VISIBLE);

        // 是否首页
        ((MessageAdapter)mBaseLoadMoreRecyclerAdapter).setHomePage(true);
        mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();

        // TODO
//        setOpenLoadMore(true);
    }

    // TODO
//    @Override
//    public void setAdapter(List list, boolean isHas) {
//        mMessageAdapter.appendToList(list);
//        mMessageAdapter.notifyDataSetChanged();
//    }

    public void setHomePageSwipeRv(View view) {
        View view_noSwipe = View.inflate(getContext(), R.layout.view_recyclerview, null);
        recyclerView = view_noSwipe.findViewById(R.id.recyclerView);
        // TODO
//        mInitialView.recyclerView.setBackgroundResource(R.color.white);
//        mDataBinding.contentMain.addView(view_noSwipe);
        recyclerView.setNestedScrollingEnabled(false);
        // TODO
//        initSwipeRecyclerView(view, mMessageAdapter);
    }


    public void initRecyclerviewListener() {
    }

    /**
     * 打开平台消息详情
     * @param  id id
     */
    @Override
    public void openMessageInfo(String id) {
        EventBus.getDefault().post(new HomeEvent(LOOK_PLATFORM_MESSAGE, WEB_URL + id));
    }
}
