package com.yaoguang.shipper.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.appcommon.phone.home.BaseHomeFragment;
import com.yaoguang.appcommon.phone.home.message.MessageFragment;
import com.yaoguang.appcommon.html.HtmlFragment;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.businessorder.forwarder.list.BusinessOrderListFragment;
import com.yaoguang.shipper.databinding.FragmentHomeBinding;
import com.yaoguang.shipper.my.PersonalCenterFragment;
import com.yaoguang.shipper.offer.logistics.LogisticsFragment;
import com.yaoguang.shipper.order.OrderFragment;
import com.yaoguang.shipper.shipschedule.ShipScheduleFragment;
import com.yaoguang.shipper.sonos.SonosFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页
 */
public class HomeFragment extends BaseHomeFragment<FragmentHomeBinding> implements Toolbar.OnMenuItemClickListener {

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void init() {
        mViewHolder.toolbar.setOnMenuItemClickListener(HomeFragment.this);
        ((TextView) mViewHolder.toolbar.findViewById(R.id.toolbar_title)).setText("货云集货主");
    }

    @Override
    public void initListener() {
        mDataBinding.cvSonos.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(SonosFragment.newInstance());
            }
        });
        mDataBinding.cvOrder.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(OrderFragment.newInstance());
            }
        });
        mDataBinding.cvShipPositioning.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //船位定位
                start(HtmlFragment.newInstance("船位查询", "http://weixin.shipxy.com/shipxy/map/?sid=972325687EAC4B2C909F74CF9811A8B4"));
            }
        });
        mDataBinding.cvMy.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //我
                start(PersonalCenterFragment.newInstance());
            }
        });
        mDataBinding.cvShipSchedule.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //船期查询
                start(ShipScheduleFragment.newInstance());
            }
        });
        mDataBinding.cvOffer.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(LogisticsFragment.newInstance());
            }
        });
        // 货代业务下单
        mDataBinding.cvForwardingOrder.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(BusinessOrderListFragment.newInstance());
            }
        });
        // 拖车业务下单
        mDataBinding.cvTrailerOrder.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(com.yaoguang.shipper.businessorder.trailer.list.BusinessOrderListFragment.newInstance());
            }
        });
    }

    @Override
    protected void onRefreshListener() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        start(MessageFragment.newInstance(Constants.APP_SHIPPER, 0));
        return false;
    }

}
