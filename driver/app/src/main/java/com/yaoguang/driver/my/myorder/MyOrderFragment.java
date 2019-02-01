package com.yaoguang.driver.my.myorder;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.DateUtils;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.driver.databinding.ToolbarCommonBinding;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.baseview.BaseFragmentListV2;
import com.yaoguang.driver.databinding.FragmentMyOrderBinding;
import com.yaoguang.driver.order.adapter.OrderChildAdapter;
import com.yaoguang.driver.order.adapter.OrderChildAdapterComplete;
import com.yaoguang.driver.order.detail.OrderDetailFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.greendao.entity.UserOffice;

import java.util.HashSet;
import java.util.List;


/**
 * 我的订单
 */
public class MyOrderFragment extends BaseFragmentListV2<Order, MyOrderPresenter, FragmentMyOrderBinding> implements MyOrderContacts.View, Toolbar.OnMenuItemClickListener {

    private List<UserOffice> mUserOffice;


    public String mStartData;
    public String mEndData;
    public HashSet<String> mIds = new HashSet<>();
    //司机id
    private OrderChildAdapter orderChildAdapter;
    private MyOrderDialogFragment dialogFragment;

    private String mSelectNum = "1";
    String startDate = null;
    String endDate = null;

    public static MyOrderFragment newInstance() {
        return new MyOrderFragment();
    }

    @Override
    protected void initView(View view) {
        dialogFragment = MyOrderDialogFragment.newInstance();
        orderChildAdapter = new OrderChildAdapterComplete(getContext());

        mToolbarCommonBinding = DataBindingUtil.bind(view.findViewById(R.id.toolbarCommon));
        mToolbarCommonBinding.toolbar.inflateMenu(R.menu.order_shaixuan);
        mToolbarCommonBinding.toolbar.setOnMenuItemClickListener(this);
        mToolbarCommonBinding.toolbarTitle.setText(R.string.my_order);
        ((ImageView) view.findViewById(R.id.ivEmpty)).setImageResource(R.drawable.ic_zbdjg);
        mToolbarCommonBinding.toolbar.setBackgroundColor(UiUtils.getColor(R.color.transparent));
        initSwipeRecyclerView(view, orderChildAdapter);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        if (getContext() == null) return;
        mPresenter.setData(Injection.provideOrderRepository(getContext()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    @Override
    protected void initListener() {
        dialogFragment.setComeback((ids, selectNum, startData, endData) -> {
            mIds = ids;
            mStartData = startData;
            mEndData = endData;
            mSelectNum = selectNum;
            refreshData();
            dialogFragment.dismiss();
        });

        orderChildAdapter.setCallPhone(order -> PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{order.getMobile(), order.getPhone()}));

        //列表跳转详情事件
        orderChildAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (AppClickUtil.isDuplicateClick()) return;

            Order order = (Order) item;
            if (getTopFragment() instanceof MyOrderFragment) {
                MyOrderFragment topFragment = (MyOrderFragment) getTopFragment();
                topFragment.start(OrderDetailFragment.newInstance(order.getId()));
            }
        });
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void refreshData() {
        switch (mSelectNum) {
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":  // 自定义月
                startDate = DateUtils.dateToString(DateUtils.getMonthFirstDay(DateUtils.stringToDate(mStartData, DateUtils.YYYY_MM_DD)), DateUtils.YYYY_MM_DD_HH_MM);
                endDate = DateUtils.dateToString(DateUtils.getMonthLastDay(DateUtils.stringToDate(mStartData, DateUtils.YYYY_MM_DD)), DateUtils.YYYY_MM_DD_HH_MM);
                break;
            case "5": //自定义日
                mSelectNum = "4";
                startDate = mStartData;
                endDate = mEndData;
                break;
        }

        App.handler.postDelayed(() ->
                mPresenter.calculationData(mIds,
                        startDate,
                        endDate,
                        mSelectNum), 300);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showData(BaseResponse<FreightStatistic> value) {
        orderChildAdapter.getList().clear();

        if (value.getResult().getOrderList() != null && !value.getResult().getOrderList().isEmpty()) {
            setAdapter(value.getResult().getOrderList(), false);
        }
        mDataBinding.tvSelectDate.setText(value.getResult().getStartDate() + " 至 " + value.getResult().getEndDate());
        mDataBinding.tvOrderCount.setText("共" + value.getResult().getAmount() + "单");
        mDataBinding.tvMoney.setText("¥" + ObjectUtils.formatNumber2(value.getResult().getTotalFreight(), 0));
    }

    /**
     * 更新公司
     *
     * @param value bean
     */
    @Override
    public void updateCompanies(BaseResponse<FreightStatistic> value) {
        if (mUserOffice == null) {
            mUserOffice = value.getResult().getCompanies();
        }
        dialogFragment.setCompanies(mUserOffice);
    }

    public void toggleMenu() {
        if (!dialogFragment.isVisible()) {
            dialogFragment.show(getFragmentManager(), "MyOrderDialogFragment");
        } else {
            dialogFragment.dismiss();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (AppClickUtil.isDuplicateClick()) return false;
        toggleMenu();
        return false;
    }

}
