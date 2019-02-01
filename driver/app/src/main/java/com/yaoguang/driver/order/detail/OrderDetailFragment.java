package com.yaoguang.driver.order.detail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yaoguang.appcommon.common.eventbus.OrderNodeMapEvent;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.appcommon.common.widget.InputCommonDialog;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.common.databinding.RecyclerviewEmptyErrorBinding;
import com.yaoguang.driver.databinding.ToolbarCommonBinding;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.DataBindingFragment;
import com.yaoguang.driver.databinding.FragmentOrderDetailBinding;
import com.yaoguang.driver.order.adapter.OrderDetailNumberAdapter;
import com.yaoguang.driver.order.chooseaddress.PutOrderAddressFragment;
import com.yaoguang.driver.order.chooseaddress.detail.PutOrderAddressDetailFragment;
import com.yaoguang.driver.order.detail.node.OrderDetailNodeFragment;
import com.yaoguang.driver.order.feedback.OrderFeedBackListFragment;
import com.yaoguang.driver.order.map.OrderNodeMapFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.entity.DriverBillsVo;
import com.yaoguang.greendao.entity.DriverOrderDetailVo;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderWrapper;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.widget.viewpager.WrapContentHeightViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.yaoguang.driver.order.child.OrderChildFragment.ORDER_DETAIL_REFRESH;
import static com.yaoguang.driver.order.chooseaddress.PutOrderAddressFragment.ORDER;
import static com.yaoguang.driver.order.chooseaddress.PutOrderAddressFragment.PLACE_ID;

/**
 * 订单详情
 * Created by wly on 2017/4/20.
 */
public class OrderDetailFragment extends DataBindingFragment<OrderDetailPresenter, FragmentOrderDetailBinding> implements OrderDetailContacts.View, Toolbar.OnMenuItemClickListener {
    public static final int ORDER_DETAIL_ORDER_ACCEPT = DriverRequest.ORDER_DETAIL_FRAGMENT + 2;
    private static final String ORDER_ID = "orderId";

    //订单明细
    DriverOrderDetailVo mOrderDetail = new DriverOrderDetailVo();
    RecyclerviewEmptyErrorBinding mRecyclerViewEmptyErrorBinding;
    SwipeRefreshLayout srlOrderDetail;

    private String mOrderId = null;
    private OrderDetailNumberAdapter orderDetailNumberOneAdapter;
    private OrderDetailNumberAdapter orderDetailNumberTwoAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(ORDER_ID))) {
            mOrderId = getArguments().getString(ORDER_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ORDER_ID, mOrderId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mOrderId = savedInstanceState.getString(ORDER_ID);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        mRecyclerViewEmptyErrorBinding = DataBindingUtil.bind(view.findViewById(R.id.includeRecyclerViewEmptyError));
        mToolbarCommonBinding = DataBindingUtil.bind(view.findViewById(R.id.toolbarCommon));
        mToolbarCommonBinding.toolbarTitle.setText(R.string.order_detail);
        mToolbarCommonBinding.toolbar.inflateMenu(R.menu.driver_dongtai);
        //初始化上拉更新颜色
        mDataBinding.includeDetail.srlOrderDetail.setColorSchemeResources(
                R.color.blue_primary,
                R.color.green_primary,
                R.color.red_primary,
                R.color.yellow_primary
        );
        srlOrderDetail = mDataBinding.includeDetail.srlOrderDetail;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_detail;
    }


    @Override
    protected void initListener() {
        //可以初始刷新
        srlOrderDetail.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    srlOrderDetail.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                refreshData();
            }
        });

        //上拉刷新
        srlOrderDetail.setOnRefreshListener(this::refreshData);

        mDataBinding.includeDetail.btnFeedback.setOnClickListener(v ->
                start(OrderFeedBackListFragment.newInstance(mOrderDetail.getOrderWrapper().getOrderId(), 0, true)));

        // 接受
        mDataBinding.btnAccept.setOnClickListener(v -> {

            // 获取放单点，如果有就跳转，没有就接单
            mPresenter.getPutOrderData(mOrderDetail.getOrderWrapper(), -1);
        });
        // 拒绝
        mDataBinding.btnRefuse.setVisibility(View.GONE);
        mDataBinding.btnRefuse.setOnClickListener(v -> {
            // 拒绝对话框
            InputCommonDialog dialog = new InputCommonDialog(getContext());
            dialog.setTitle(UiUtils.getString(R.string.order_reason));
            dialog.setHit(UiUtils.getString(R.string.please_enter_reason));
            dialog.setToast(UiUtils.getString(R.string.please_enter_reason_hit));
            dialog.setMaxLength(50);
            dialog.setComeBack(value ->
                    mPresenter.handleUpdate(mOrderDetail.getOrderWrapper().getId(), 3, mOrderDetail.getOrderWrapper(), -1, value, null));
            dialog.show();
        });
        // 出车 或 进度 更新
        mDataBinding.btnRegister.setOnClickListener(v -> {
            if (mOrderDetail.getOrderWrapper().getVehicleFlag() == 1) {
                //如果是进度更新的就直接跳转
                if (getTopFragment() instanceof OrderDetailFragment) {
                    mPresenter.getNodes(mOrderDetail.getOrderWrapper().getOrderId(), mOrderDetail.getOrderWrapper().getId());
                }
            } else {
                RxPermissions permissions = new RxPermissions(getActivity());
                permissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                //如果是现在出车就请求网络
                                mPresenter.outCar(mOrderDetail.getOrderWrapper().getOrderId(), mOrderDetail.getOrderWrapper());
                            } else {
                                showToast("请允许APP定位权限，否则无法出车");
                            }
                        });
            }
        });
        // 返回
        mToolbarCommonBinding.toolbar.setOnClickListener(v -> {
            hideKeyboard();
            pop();
        });
        mToolbarCommonBinding.toolbar.setOnMenuItemClickListener(this);

    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter.setData(Injection.provideOrderRepository(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 实例化
     *
     * @return 实例化
     */
    public static OrderDetailFragment newInstance(String orderId) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putString(ORDER_ID, orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showLoadingView() {
        if (srlOrderDetail != null)
            srlOrderDetail.setRefreshing(true);
    }

    @Override
    public void hideLoadingView() {
        if (srlOrderDetail != null)
            srlOrderDetail.setRefreshing(false);
    }

    /**
     * 显示操作成功
     *
     * @param result
     * @param operateType
     */
    @Override
    public void showSuccess(String result, int operateType) {
        mPresenter.refreshOrderDetail(mOrderId);
        switch (operateType) {
            case 4: // 出车
                break;
            case 1: // 接单
                setFragmentResult(ORDER_DETAIL_REFRESH, null);
                break;
            case 2: // 拒绝
                setFragmentResult(ORDER_DETAIL_REFRESH, null);
                break;
        }
    }

    /**
     * 初始加载成功后的呈现
     *
     * @param orderDetail 数据源
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void setData(BaseResponse<DriverOrderDetailVo> orderDetail) {
        if (mDataBinding.includeDetail.llParent == null
                || orderDetail.getResult() == null) return;

        mDataBinding.includeDetail.llParent.setVisibility(View.VISIBLE);

        //顶部属性加载
        mDataBinding.includeDetail.tvOrderId.setText(" " + ObjectUtils.parseString(orderDetail.getResult().getOrderWrapper().getOrderId()));
        // 订单类型
        String orderType = ObjectUtils.parseString(orderDetail.getResult().getOrderWrapper().getStrOrderStatus());
        mDataBinding.includeDetail.tvContQty.setText(" " + ObjectUtils.parseString(orderDetail.getResult().getOrderWrapper().getContQty()));
        mDataBinding.includeDetail.tvVehiclePrice.setText(" " + "¥" + ObjectUtils.formatNumber2(orderDetail.getResult().getOrderWrapper().getVehiclePrice(), 0));
        mDataBinding.includeDetail.tvWeiTuoRen.setText(" " + ObjectUtils.parseString(orderDetail.getResult().getOrderWrapper().getEntrustCompany()));
        if (!TextUtils.isEmpty(orderDetail.getResult().getOrderWrapper().getPhone())) {
            mDataBinding.includeDetail.tvWeiTuoRenPhone.setText("" + ObjectUtils.parseString(orderDetail.getResult().getOrderWrapper().getPhone()));
            mDataBinding.includeDetail.tvWeiTuoRenPhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
        mDataBinding.includeDetail.tvLianXiRen.setText(" " + ObjectUtils.parseString(orderDetail.getResult().getOrderWrapper().getEntrustPeople()));
        mDataBinding.includeDetail.tvLianXiRenPhone.setText("" + ObjectUtils.parseString(orderDetail.getResult().getOrderWrapper().getMobile()));
        mDataBinding.includeDetail.tvLianXiRenPhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        // 存放点订单信息
        InfoPutorderPlace place = orderDetail.getResult().getOrderWrapper().getPlace();
        if (place != null && !TextUtils.isEmpty(place.getId())) {
            // 存放点名称
            if (!TextUtils.isEmpty(place.getName())) {
                mDataBinding.includeDetail.tvPutOrderName.setText(place.getName());
            }
            // 存放点电话
            if (!TextUtils.isEmpty(place.getPhone())) {
                mDataBinding.includeDetail.tvPutOrderMobile.setText(place.getPhone());
                mDataBinding.includeDetail.tvPutOrderMobile.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            }
            // 存放点地址
            if (!TextUtils.isEmpty(place.getAddress())) {
                mDataBinding.includeDetail.tvPutOrderAddress.setText(place.getAddress());
            }

            mDataBinding.includeDetail.llPutOrder.setVisibility(View.VISIBLE);
            mDataBinding.includeDetail.llPutOrderLine.setVisibility(View.VISIBLE);
            mDataBinding.includeDetail.btnPutOrderDetail.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.includeDetail.llPutOrder.setVisibility(View.GONE);
            mDataBinding.includeDetail.llPutOrderLine.setVisibility(View.GONE);
            mDataBinding.includeDetail.btnPutOrderDetail.setVisibility(View.GONE);
        }
        // 查看存放点订单详情
        mDataBinding.includeDetail.btnPutOrderDetail.setOnClickListener(v -> {
            if (place != null) {
                start(PutOrderAddressDetailFragment.newInstance(place));
            }
        });
        mDataBinding.includeDetail.tvWeiTuoRenPhone.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{mDataBinding.includeDetail.tvWeiTuoRenPhone.getText().toString()});
        });
        mDataBinding.includeDetail.tvPutOrderMobile.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick() || place == null || TextUtils.isEmpty(place.getPhone()))
                return;

            PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{place.getPhone()});
        });
        mDataBinding.includeDetail.tvLianXiRenPhone.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{mDataBinding.includeDetail.tvLianXiRenPhone.getText().toString()});
        });


        mDataBinding.llWait.setVisibility(View.GONE);
        mDataBinding.btnRegister.setVisibility(View.GONE);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (orderType) {
            case "待接单":
                params.setMargins(0, ConvertUtils.dp2px(48), 0, ConvertUtils.dp2px(40));
                mDataBinding.llDetail.setLayoutParams(params);
                mDataBinding.includeDetail.ivOrderType.setImageResource(R.drawable.ic_djd_23);
                mDataBinding.llWait.setVisibility(View.VISIBLE);
                mDataBinding.includeDetail.btnFeedback.setVisibility(View.GONE);
                // 能否拒单（0:非可拒 1:可拒）
                if (orderDetail.getResult().getOrderWrapper().getRefusable() == null || orderDetail.getResult().getOrderWrapper().getRefusable() == 0)
                    mDataBinding.btnRefuse.setVisibility(View.GONE);
                else mDataBinding.btnRefuse.setVisibility(View.VISIBLE);
                break;
            case "已接单":
                params.setMargins(0, ConvertUtils.dp2px(48), 0, ConvertUtils.dp2px(40));
                mDataBinding.llDetail.setLayoutParams(params);
                mDataBinding.includeDetail.ivOrderType.setImageResource(R.drawable.ic_yjd_01);
                //判断状态
                DriverOrderWrapper orderWrapper = orderDetail.getResult().getOrderWrapper();
                mDataBinding.btnRegister.setVisibility(View.VISIBLE);
                if (orderWrapper.getVehicleFlag() == 1) {
                    mDataBinding.btnRegister.setBackgroundColor(getResources().getColor(R.color.refuse));
                    mDataBinding.includeDetail.btnFeedback.setVisibility(View.VISIBLE);
                    mDataBinding.btnRegister.setText("进度更新");
                } else {
                    mDataBinding.btnRegister.setBackgroundColor(getResources().getColor(R.color.received));
                    mDataBinding.includeDetail.btnFeedback.setVisibility(View.GONE);
                    mDataBinding.btnRegister.setText("现在出车");
                }
                break;
            case "已完成":
                params.setMargins(0, ConvertUtils.dp2px(48), 0, 0);
                mDataBinding.llDetail.setLayoutParams(params);
                mDataBinding.includeDetail.ivOrderType.setImageResource(R.drawable.ic_ywc_01);
                mDataBinding.includeDetail.btnFeedback.setVisibility(View.VISIBLE);
                break;
            case "已关闭":
                params.setMargins(0, ConvertUtils.dp2px(48), 0, 0);
                mDataBinding.llDetail.setLayoutParams(params);
                mDataBinding.includeDetail.btnFeedback.setVisibility(View.VISIBLE);
                mDataBinding.includeDetail.ivOrderType.setImageResource(R.drawable.ic_ygb_01);
                break;
        }
        //根据状态来判断显示相关时间
        switch (orderDetail.getResult().getOrderWrapper().getOrderStatus()) {
            case 0:
                //待接单
                mDataBinding.includeDetail.tvCreatedTime.setText("" + orderDetail.getResult().getOrderWrapper().getCreateTime());
                mDataBinding.includeDetail.llReceiveTime.setVisibility(View.GONE);
                mDataBinding.includeDetail.llFinishTime.setVisibility(View.GONE);
                break;
            case 1:
                //已接单
                mDataBinding.includeDetail.tvCreatedTime.setText(" " + orderDetail.getResult().getOrderWrapper().getCreateTime());
                mDataBinding.includeDetail.tvReceiveTime.setText(" " + orderDetail.getResult().getOrderWrapper().getReceiveTime());
                mDataBinding.includeDetail.llFinishTime.setVisibility(View.GONE);
                break;
            case 2:
                // 已完成
                mDataBinding.includeDetail.tvCreatedTime.setText(" " + orderDetail.getResult().getOrderWrapper().getCreateTime());
                mDataBinding.includeDetail.tvReceiveTime.setText(" " + orderDetail.getResult().getOrderWrapper().getReceiveTime());
                mDataBinding.includeDetail.tvFinishTime.setText(" " + orderDetail.getResult().getOrderWrapper().getFinishTime());
                break;
            case 3:
                //  已关闭
                mDataBinding.includeDetail.tvCreatedTime.setText(" " + orderDetail.getResult().getOrderWrapper().getCreateTime());
                mDataBinding.includeDetail.tvFinishTime.setText(" " + orderDetail.getResult().getOrderWrapper().getFinishTime());
                mDataBinding.includeDetail.tvFinishTimeKey.setText("关闭时间: ");
                mDataBinding.includeDetail.llReceiveTime.setVisibility(View.GONE);
            case 4:
                //  企业关闭
                mDataBinding.includeDetail.tvCreatedTime.setText(" " + orderDetail.getResult().getOrderWrapper().getCreateTime());
                mDataBinding.includeDetail.tvFinishTime.setText(" " + orderDetail.getResult().getOrderWrapper().getFinishTime());
                mDataBinding.includeDetail.tvFinishTimeKey.setText("关闭时间: ");
                mDataBinding.includeDetail.llReceiveTime.setVisibility(View.GONE);
                break;
        }


        //底部数据加载，循环工作单
        int dan1 = orderDetail.getResult().getDriverBillsOne().size();
        int dan2 = 0;

        // 吉出重回 装  重出吉回 拆 重出重回 拆装

        if (orderDetail.getResult().getDriverBillsTwo() != null) {
            dan2 = orderDetail.getResult().getDriverBillsTwo().size();
        }

        // 一张单情况下
        if (dan1 == 1) {
            mDataBinding.includeDetail.ivRemark1.setVisibility(View.GONE);
            mDataBinding.includeDetail.ivRemarkTao1.setVisibility(View.GONE);
        } else {
            mDataBinding.includeDetail.ivRemarkTao1.setVisibility(View.VISIBLE);
        }

        // 二张单情况下
        if (dan2 == 1) {
            mDataBinding.includeDetail.ivRemark2.setVisibility(View.GONE);
            mDataBinding.includeDetail.ivRemarkTao2.setVisibility(View.GONE);
        } else {
            mDataBinding.includeDetail.ivRemarkTao2.setVisibility(View.VISIBLE);
        }

        // 拼单第二张单，是空的
        if (dan2 == 0) {
            mDataBinding.includeDetail.ivTabOrderStatus2.setVisibility(View.GONE);
            mDataBinding.includeDetail.flTabTwo.setVisibility(View.GONE);
            mDataBinding.includeDetail.rlTabTwo.setVisibility(View.GONE);
        }

        // 各有一张单
        if (dan1 > 1 && dan1 > 1) {
            mDataBinding.includeDetail.ivRemark1.setVisibility(View.VISIBLE);
            mDataBinding.includeDetail.ivRemark2.setVisibility(View.VISIBLE);
        }
        mOrderDetail = orderDetail.getResult();


        if (orderDetailNumberOneAdapter == null) { // 初始化tab
            // 设置订单1类型
            mDataBinding.includeDetail.ivTabOrderStatus.setImageResource(getOrderType(orderDetail.getResult().getOrderWrapper().getTruckBillFirstType()));
            // 设置tab Ui
            initTabOne(orderDetail.getResult().getDriverBillsOne(), orderDetail.getResult(), mDataBinding.includeDetail.tabLayout1, mDataBinding.includeDetail.viewPager1);
            if (orderDetail.getResult().getDriverBillsTwo() != null && !orderDetail.getResult().getDriverBillsTwo().isEmpty()) {
                mDataBinding.includeDetail.flTabTwo.setVisibility(View.VISIBLE);
                mDataBinding.includeDetail.rlTabTwo.setVisibility(View.VISIBLE);
                mDataBinding.includeDetail.ivTabOrderStatus2.setVisibility(View.VISIBLE);
                // 设置订单2类型
                mDataBinding.includeDetail.ivTabOrderStatus2.setImageResource(getOrderType(orderDetail.getResult().getOrderWrapper().getTruckBillFirstType()));
                // 设置tab2 Ui
                initTabTwo(orderDetail.getResult().getDriverBillsTwo(), orderDetail.getResult(), mDataBinding.includeDetail.tabLayout2, mDataBinding.includeDetail.viewPager2);
            }
        } else { // 否则更新数据
            orderDetailNumberOneAdapter.setmDiverOrderAddrWrappers(orderDetail.getResult().getDriverBillsOne());
            orderDetailNumberOneAdapter.setmOrderDetailResult(orderDetail.getResult());
            orderDetailNumberOneAdapter.update();

            if (orderDetailNumberTwoAdapter != null) {
                orderDetailNumberTwoAdapter.setmDiverOrderAddrWrappers(orderDetail.getResult().getDriverBillsTwo());
                orderDetailNumberTwoAdapter.setmOrderDetailResult(orderDetail.getResult());
                orderDetailNumberOneAdapter.update();
            }
        }

        mDataBinding.llDetail.setVisibility(View.VISIBLE);
        mRecyclerViewEmptyErrorBinding.rlError.setVisibility(View.GONE);
        mRecyclerViewEmptyErrorBinding.ivError.setVisibility(View.GONE);
        mDataBinding.flBottom.setVisibility(View.VISIBLE);
    }

    private void initTabOne(List<DriverBillsVo> driverBillsVos, DriverOrderDetailVo orderDetailResult, TabLayout tabLayout, WrapContentHeightViewPager viewPager) {
        int size = driverBillsVos.size();
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String buillsid = getBillsidString(driverBillsVos, i);
            tabLayout.addTab(tabLayout.newTab().setText(driverBillsVos.get(i).getServiceType() + "\n" + buillsid));
            titles.add(driverBillsVos.get(i).getServiceType() + "\n" + buillsid);
        }
        orderDetailNumberOneAdapter = new OrderDetailNumberAdapter(getChildFragmentManager(), titles, driverBillsVos, orderDetailResult);
        viewPager.setAdapter(orderDetailNumberOneAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initTabTwo(List<DriverBillsVo> driverBillsVos, DriverOrderDetailVo orderDetailResult, TabLayout tabLayout, WrapContentHeightViewPager viewPager) {
        int size = driverBillsVos.size();
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String buillsid = getBillsidString(driverBillsVos, i);
            tabLayout.addTab(tabLayout.newTab().setText(driverBillsVos.get(i).getServiceType() + "\n" + buillsid));
            titles.add(driverBillsVos.get(i).getServiceType() + "\n" + buillsid);
        }
        orderDetailNumberTwoAdapter = new OrderDetailNumberAdapter(getChildFragmentManager(), titles, driverBillsVos, orderDetailResult);
        viewPager.setAdapter(orderDetailNumberTwoAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @NonNull
    private String getBillsidString(List<DriverBillsVo> list, int i) {
        String billsid = list.get(i).getTruckBillsId();
        if (billsid.contains("_")) {
            billsid = billsid.substring(0, billsid.indexOf("_"));
        }
        return billsid;
    }


    /**
     * 事件订阅者自定义的接收方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderNodeMapEvent event) {

        if (event.isRefresh()) {
            //设置刷新
            refreshData();
        }
    }

    /**
     * 订单类型对应图标
     *
     * @param orderType 0:吉出重回 1:重出吉回 2:重出重回
     */
    public int getOrderType(int orderType) {
        switch (orderType) {
            case 0://吉出重回
                return R.drawable.ic_jczh_01;
            case 1://重出吉回
                return R.drawable.ic_zcjh_01;
            case 2://重出重回
                return R.drawable.ic_zczh_01;
        }
        return orderType;
    }

    /**
     * 刷新数据
     */
    @Override
    public void refreshData() {
        App.handler.postDelayed(() -> mPresenter.refreshOrderDetail(mOrderId), 200);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (AppClickUtil.isDuplicateClick()) return false;
        if (mOrderDetail.getOrderWrapper() != null && !TextUtils.isEmpty(mOrderDetail.getOrderWrapper().getOrderId())) {
            start(OrderDetailNodeFragment.newInstance(mOrderDetail.getOrderWrapper().getOrderId()));
        } else {
            showToast("该车还没有派车");
        }
        return false;
    }

    /**
     * 接受订单
     *
     * @param order
     */
    @Override
    public void acceptOrder(Order order) {
        // 接受订单
        mPresenter.handleUpdate(order.getOrderId(), 1, mOrderDetail.getOrderWrapper(), -1, "", null);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        // 如果从详情进入地图，返回来时，要刷新一下订单详情
        int ENTER_ORDER_DETAIL = DriverRequest.ORDER_DETAIL_FRAGMENT + 1;
        if (requestCode == RESULT_OK || requestCode == ENTER_ORDER_DETAIL) {
            refreshData();
        } else if (requestCode == ORDER_DETAIL_ORDER_ACCEPT && data != null && data.getParcelable(ORDER) != null) {
            // 接受订单
            Order order = data.getParcelable(ORDER);
            String placeId = data.getString(PLACE_ID);
            if (order != null)
                mPresenter.handleUpdate(order.getOrderId(), 1, mOrderDetail.getOrderWrapper(), -1, "", placeId);
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void startOrderNodeMapFragment(List<DriverOrderNode> result, String id) {
        // 如果最后一个都是完成的
        if (getTopFragment() instanceof OrderDetailFragment) {
            ((OrderDetailFragment) getTopFragment()).startForResult(OrderNodeMapFragment.newInstance(result, id), RESULT_OK);
        }
    }

    @Override
    public void recyclerViewShowEmpty() {
        mDataBinding.includeDetail.llParent.setVisibility(View.GONE);
        mRecyclerViewEmptyErrorBinding.rlError.setVisibility(View.VISIBLE);
        mRecyclerViewEmptyErrorBinding.ivError.setVisibility(View.VISIBLE);
        mDataBinding.flBottom.setVisibility(View.GONE);
    }


    /**
     * 打开放单地址
     *
     * @param order
     * @param infoPutorderPlaces
     * @param position
     */
    @Override
    public void openPutOrderAddress(DriverOrderWrapper driverOrderWrapper, ArrayList<InfoPutorderPlace> infoPutorderPlaces, int position) {
        Order order = new Order();
        order.setId(driverOrderWrapper.getId());
        order.setOrderId(driverOrderWrapper.getOrderId());

        startForResult(PutOrderAddressFragment.newInstance(order, infoPutorderPlaces, -1), ORDER_DETAIL_ORDER_ACCEPT);
    }
}
