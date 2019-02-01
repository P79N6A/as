package com.yaoguang.driver.order.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.yaoguang.appcommon.common.eventbus.HomeMessageOrderChildEvent;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentOrderChildBinding;
import com.yaoguang.driver.my.my.MyFragment;
import com.yaoguang.driver.order.adapter.HomeOrderChildAdapterWait;
import com.yaoguang.driver.order.child.OrderChildPresenter;
import com.yaoguang.driver.order.child.OrderChildFragment;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.lib.annotation.aspect.BackKey;

import org.greenrobot.eventbus.Subscribe;

import static com.yaoguang.common.common.Constants.FLAG_REFRESH_PAGE;
import static com.yaoguang.common.common.Constants.FLAG_REFRESH_TOOLBAR;

/**
 * 作    者：韦理英
 * 时    间：2017/9/19 0019.
 * 描    述：首页订单消息
 */
public class HomeOrderMessageChildFragment extends OrderChildFragment<Order, OrderChildPresenter, FragmentOrderChildBinding> {

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    public static HomeOrderMessageChildFragment newInstance(int type) {
        HomeOrderMessageChildFragment fragment = new HomeOrderMessageChildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void orderClick(View itemView, Object item, int position) {
        super.orderClick(itemView, item, position);
        // 设置已读
        Order order = (Order) item;
        if (order.getDriverOrderMsg().getFlag() == 0) {
            mPresenter.readBatch(order.getDriverOrderMsg().getId(), position);
        }
    }

    /**
     * 设置已读成功
     *
     * @param position 订单位置
     */
    @Override
    public void setReadSuccess(int position) {
        try {
            orderChildAdapter.removeItem(position);
            if (orderChildAdapter.getList().isEmpty()) {
                recyclerView.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
            }

            findFragment(MyFragment.class).refreshUnReadCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        orderChildAdapter = new HomeOrderChildAdapterWait(getContext());
        setOpenLoadMore(false);

        ((HomeOrderChildAdapterWait) orderChildAdapter).setOnRecyclerViewItemClickListener((itemView, item, position) -> App.sayTts(((Order) item).getDriverOrderMsg().getVoice()));
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void refreshUi() {
        toolbarCommonBinding.toolbar.setVisibility(View.GONE);
        mDataBinding.tvNoMessage.setVisibility(View.VISIBLE);
        nestedScrollingEnabled(false);
    }

    @Subscribe
    public void OrderEvent(HomeMessageOrderChildEvent orderChildEvent) {
        switch (orderChildEvent.getType()) {
            case "下一页":
                loadMoreData();
                break;
            case FLAG_REFRESH_TOOLBAR:
                refreshData();
                break;
            case FLAG_REFRESH_PAGE:
                refreshData();
                break;
        }
    }

    /**
     * 首页 无下拉刷新
     */
    public void setSwipeRv() {
        if (getContext() == null) return;
        viewList.setPadding(0, ConvertUtils.dp2px(5), 0, 0);
    }

    /**
     * 描述：获取view
     */

    public View getListView() {
        return View.inflate(getContext(), R.layout.view_recyclerview, null);
    }

    public void refreshData() {
        mPresenter.refreshData(mType, true);
    }

    protected void loadMoreData() {
        mPresenter.loadMoreData(mType, orderChildAdapter.getList().size(), true);
    }


    public void recyclerViewShowError(String strMessage) {
    }

    public void recyclerViewShowEmpty() {
    }

    public void initRecyclerviewListener() {
    }

    /**
     * 处理回退事件
     */
    @Override
    @BackKey
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            App.getInstance().finishAllActivity();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(App.getInstance(), R.string.keydownquitapp, Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
