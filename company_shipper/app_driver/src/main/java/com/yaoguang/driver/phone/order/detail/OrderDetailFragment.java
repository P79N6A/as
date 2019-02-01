package com.yaoguang.driver.phone.order.detail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.jaeger.library.SelectableTextHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yaoguang.appcommon.address.addressDetail.AddressDetailFragment;
import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.appcommon.common.eventbus.OrderChildEvent;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.appcommon.common.widget.InputCommonDialog;
import com.yaoguang.appcommon.phone.my.my.event.MyEvent;
import com.yaoguang.datasource.util.OrderAssistant;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentOrderDetail2Binding;
import com.yaoguang.driver.phone.order.chooseaddress.PutOrderAddressFragment;
import com.yaoguang.appcommon.phone.node.BaseNodeFragment;
import com.yaoguang.driver.phone.order.node.NodeFragment;
import com.yaoguang.driver.phone.order.nodeEdit.NodeEditFragment;
import com.yaoguang.greendao.entity.DriverGoodsAddr;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.driver.DriverOrderDetailVoSec;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.greendao.entity.driver.DriverSonoVoSec;
import com.yaoguang.greendao.entity.driver.SonoTaskVo;
import com.yaoguang.lib.appcommon.widget.head.Pull2Header;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.databinding.LayoutRecyclerviewBinding;
import com.yaoguang.map.common.AMapUtil;
import com.yaoguang.map.location.impl.LocationManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.REFRESH_UNREAD_COUNT;
import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_PAGE;
import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_PROGRESS;

/**
 * 订单明细
 * Created by zhongjh on 2018/5/22.
 */
public class OrderDetailFragment extends BaseFragmentDataBind<FragmentOrderDetail2Binding> implements OrderDetailContacts.View, Toolbar.OnMenuItemClickListener {

    public static final int REQUEST_PUT_ORDER_ADDRESS = 1;

    OrderDetailPresenter mPresenter = new OrderDetailPresenter(this);

    private static final String ORDER_ID = "orderId";
    public static final String ORDER_TYPE = "orderType";
    private static final String IS_SHOW_COMIT_BUTTON = "isShowComitButton";
    private static final String IS_REFRESH = "isRefresh";

    private String mOrderId = null; // 订单id
    private int mOrderType; // 订单类型：待确认、已确认……等
    private boolean mIsShowComitButton; // 是否显示提交按钮
    private boolean mIsRefresh;// 是否刷新
    private DriverOrderDetailVoSec mDriverOrderDetailVoSec = new DriverOrderDetailVoSec();

    AMapUtil aMapUtil = new AMapUtil(); // 地图工具类


//    /**
//     * 实例化
//     *
//     * @param orderId     订单id
//     * @param isShowComit 是否显示提交按钮
//     */
//    public static NodeEditAddressFragment newInstance(String orderId, boolean isShowComit) {
//        NodeEditAddressFragment fragment = new NodeEditAddressFragment();
//        Bundle args = new Bundle();
//        args.putString(ORDER_ID, orderId);
//        args.putBoolean(IS_SHOW_COMIT_BUTTON, isShowComit);
//        fragment.setArguments(args);
//        return fragment;
//    }

    /**
     * 实例化
     *
     * @param orderId     订单id
     * @param isShowComit 是否显示提交按钮
     */
    public static OrderDetailFragment newInstance(String orderId, boolean isShowComit, boolean isRefresh) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putString(ORDER_ID, orderId);
        args.putBoolean(IS_SHOW_COMIT_BUTTON, isShowComit);
        args.putBoolean(IS_REFRESH, isRefresh);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrderId = getArguments().getString(ORDER_ID);
            mOrderType = getArguments().getInt(ORDER_TYPE);
            mIsShowComitButton = getArguments().getBoolean(IS_SHOW_COMIT_BUTTON);
            mIsRefresh = getArguments().getBoolean(IS_REFRESH);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ORDER_ID, mOrderId);
        outState.putInt(ORDER_TYPE, mOrderType);
        outState.putBoolean(IS_SHOW_COMIT_BUTTON, mIsShowComitButton);
        outState.putBoolean(IS_REFRESH, mIsRefresh);
    }

    @Override
    public void savedInstanceState(@Nullable Bundle savedInstanceState) {
        super.savedInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mOrderId = savedInstanceState.getString(ORDER_ID);
            mOrderType = savedInstanceState.getInt(ORDER_TYPE);
            mIsShowComitButton = savedInstanceState.getBoolean(IS_SHOW_COMIT_BUTTON);
            mIsRefresh = savedInstanceState.getBoolean(IS_REFRESH);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_detail2;
    }

    @Override
    public void init() {
        if (mIsRefresh) {
            // 通知刷新
            EventBus.getDefault().postSticky(new MyEvent(ObjectUtils.parseString(REFRESH_UNREAD_COUNT)));
            // 实时刷新数据
            EventBus.getDefault().post(new HomeEvent(FLAG_REFRESH_PAGE));
        }

        // 先设置没有右上角菜单的，后面获取到数据后，再重新设置
        initToolbarNav(mToolbarCommonBinding.toolbar, "派车单详情", -1, null);

        mDataBinding.refreshLayout.setRefreshHeader(new Pull2Header(getContext()));

        showLoadingView();
    }

    @Override
    public void initListener() {
        // 刷新
        mDataBinding.refreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.refreshOrderDetail(mOrderId));

        // 接受
        mDataBinding.btnAccept.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 获取放单点，如果有就跳转，没有就接单
                mPresenter.acceptOrder(mDriverOrderDetailVoSec.getOrder(), -1);
            }
        });

        // 拒绝
        mDataBinding.btnRefuse.setOnClickListener(v -> {
            // 拒绝对话框
            InputCommonDialog dialog = new InputCommonDialog(getContext());
            dialog.setTitle(UiUtils.getString(R.string.order_reason));
            dialog.setHit(UiUtils.getString(R.string.please_enter_reason));
            dialog.setToast(UiUtils.getString(R.string.please_enter_reason_hit));
            dialog.setMaxLength(50);
            dialog.setComeBack(value ->
                    mPresenter.handleUpdate(mDriverOrderDetailVoSec.getOrder().getOrderId(), 3, mDriverOrderDetailVoSec.getOrder(), -1, value, null));
            dialog.show();
        });

        // 出车
        mDataBinding.btnOperationCar.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                RxPermissions permissions = new RxPermissions(_mActivity);
                permissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                // 出车
                                showProgressDialog();
                                // (1) 获取当前地址
                                LocationManager locationManager = new LocationManager();
                                locationManager.init(BaseApplication.getInstance().getBaseContext());
                                locationManager.setComeback(location -> {
                                    hideProgressDialog();
                                    locationManager.destroyLocation();
                                    mPresenter.outCar(mDriverOrderDetailVoSec.getOrder().getOrderId(), mDriverOrderDetailVoSec.getOrder(), location.getLat(), location.getLon(), location.getAddress());
                                });
                            } else {
                                showToast("请允许APP定位权限，否则无法出车");
                            }
                        });
            }
        });

        // 进度更新
        mDataBinding.btnProgressUpdate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                startForResult(NodeFragment.newInstance(mDriverOrderDetailVoSec.getOrder().getOrderId()), -1);
            }
        });

        // 路线
        mDataBinding.rlRoute.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(NodeEditFragment.newInstance(mDriverOrderDetailVoSec.getOrder().getOrderId(), mDriverOrderDetailVoSec.getOrder().getClientId()), SINGLETOP);
            }
        });

    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PUT_ORDER_ADDRESS:
                    // 刷新
                    showLoadingView();
                    break;
            }
        }
        // 切换竖屏
        _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoadingView() {
        mDataBinding.refreshLayout.autoRefresh();
    }

    @Override
    public void hideLoadingView() {
        mDataBinding.refreshLayout.finishRefresh();
    }

    @Override
    public void showErrorView() {
        hideLoadingView();
        mDataBinding.loading.showError();
    }

    @Override
    public void updateSuccess(String message, int operateType) {
        // 显示成功的消息
        showToast(message);
        // 刷新
        showLoadingView();
        switch (operateType) {
            case -1:
                // 出车
                OrderChildEvent orderChildEvent = new OrderChildEvent(FLAG_REFRESH_PROGRESS);
                orderChildEvent.setOrderType(1);
                EventBus.getDefault().post(orderChildEvent);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(DriverOrderDetailVoSec driverOrderDetailVo) {
        if (driverOrderDetailVo == null) {
            mDataBinding.loading.showEmpty();
            return;
        } else {
            mDriverOrderDetailVoSec = driverOrderDetailVo;
            mDataBinding.loading.showContent();
        }
        mDataBinding.tvFreightValue.setText("¥" + ObjectUtils.decimalFormat(driverOrderDetailVo.getOrder().getVehiclePrice(), "#,##0.00"));// 运费
        mDataBinding.tvCabinetValue.setText(driverOrderDetailVo.getOrder().getContQty()); // 货柜货量
        mDataBinding.tvSingleCarNumberValue.setText(driverOrderDetailVo.getOrder().getOrderId());// 派车单号
        mDataBinding.tvPhotoValue.setText(driverOrderDetailVo.getOrder().getEntrustCompany());// 委托人
        mDataBinding.tvContactsValue.setText("联系人：" + driverOrderDetailVo.getOrder().getEntrustPeople());// 联系人
        mDataBinding.tvContacts.setText(driverOrderDetailVo.getOrder().getMobile());
        mDataBinding.tvContacts.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mDataBinding.tvContacts.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{driverOrderDetailVo.getOrder().getMobile()});
            }
        });

        // 循环实例化工作单
        mDataBinding.llContainer.removeAllViews();
        for (int i = 0; i < driverOrderDetailVo.getSonoList().size(); i++) {
            DriverSonoVoSec driverSonoVoSec = driverOrderDetailVo.getSonoList().get(i);

            if (driverSonoVoSec != null){
                // 添加货柜view
                View view = LayoutInflater.from(getContext()).inflate(R.layout.view_order_detail_container, null, false);
                ViewHolderContainer viewHolderContainer = new ViewHolderContainer(view);
                mDataBinding.llContainer.addView(viewHolderContainer.view);

                // 装拆箱任务
                viewHolderContainer.flContainer1.removeAllViews();
                viewHolderContainer.flContainer2.removeAllViews();
                for (int k = 0; k < driverSonoVoSec.getTaskList().size(); k++) {
                    // 判断装拆箱
                    SonoTaskVo sonoTaskVo = driverSonoVoSec.getTaskList().get(k);
                    if (sonoTaskVo.getOtherService() == 0) {
                        // 添加tab
                        viewHolderContainer.tabLayout.addTab(viewHolderContainer.tabLayout.newTab().setText("装箱任务"));
                    } else {
                        // 添加tab
                        viewHolderContainer.tabLayout.addTab(viewHolderContainer.tabLayout.newTab().setText("拆箱任务"));
                    }
                    initSonoTaskVo(sonoTaskVo, k, viewHolderContainer);
                }

                // 事件 选择拆箱还是装箱
                viewHolderContainer.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getText() != null)
                            if (tab.getPosition() == 0) {
                                viewHolderContainer.flContainer1.setVisibility(View.VISIBLE);
                                viewHolderContainer.flContainer2.setVisibility(View.GONE);
                            } else {
                                viewHolderContainer.flContainer1.setVisibility(View.GONE);
                                viewHolderContainer.flContainer2.setVisibility(View.VISIBLE);
                            }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                // 导航提柜、还柜点
                viewHolderContainer.rlCounterPoint.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (driverSonoVoSec.getCarryPortSite() != null) {
                            LatLng latLng = new LatLng(driverSonoVoSec.getCarryPortSite().getLat(), driverSonoVoSec.getCarryPortSite().getLon());
                            start(AddressDetailFragment.newInstance(latLng, viewHolderContainer.tvCounterPoint.getText().toString(), -1));
//                        AMapUtil.startAMapNavi(getContext(), latLng);
                        } else if (!TextUtils.isEmpty(driverSonoVoSec.getCarryPort())) {
//                        // 如果有address，就显示导航
                            start(AddressDetailFragment.newInstance(driverSonoVoSec.getCarryPort(), viewHolderContainer.tvCounterPoint.getText().toString(), -1));
//                        aMapUtil.startAMapNavi(_mActivity, getContext(), driverSonoVoSec.getCarryPortLocation());
                        }
                    }
                });
                viewHolderContainer.rlCounterPoint2.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (driverSonoVoSec.getGetPortSite() != null) {
                            LatLng latLng = new LatLng(driverSonoVoSec.getGetPortSite().getLat(), driverSonoVoSec.getGetPortSite().getLon());
                            start(AddressDetailFragment.newInstance(latLng, viewHolderContainer.tvCounterPoint2.getText().toString(), -1));
//                        AMapUtil.startAMapNavi(getContext(), latLng);
                        } else if (!TextUtils.isEmpty(driverSonoVoSec.getGetPort())) {
                            // 如果有address，就显示导航
                            start(AddressDetailFragment.newInstance(driverSonoVoSec.getGetPort(), viewHolderContainer.tvCounterPoint2.getText().toString(), -1));
//                        aMapUtil.startAMapNavi(_mActivity, getContext(), driverSonoVoSec.getGetPortLocation());
                        }
                    }
                });

                // 赋值数据 0：吉出重回 1：重出吉回 2：重出重回
                if (i == 0) {
                    switch (driverOrderDetailVo.getOrder().getTruckBillFirstType()) {
                        case "0":
                            viewHolderContainer.tvType.setText("吉出重回");
                            break;
                        case "1":
                            viewHolderContainer.tvType.setText("重出吉回");
                            break;
                        case "2":
                            viewHolderContainer.tvType.setText("重出重回");
                            break;
                    }
                } else {
                    switch (driverOrderDetailVo.getOrder().getTruckBillSecondType()) {
                        case "0":
                            viewHolderContainer.tvType.setText("吉出重回");
                            break;
                        case "1":
                            viewHolderContainer.tvType.setText("重出吉回");
                            break;
                        case "2":
                            viewHolderContainer.tvType.setText("重出重回");
                            break;
                    }
                }
                if (driverOrderDetailVo.getSonoList().size() == 1)
                    viewHolderContainer.tvContainerTitle.setText("货柜");
                else
                    viewHolderContainer.tvContainerTitle.setText("货柜" + (i + 1));
                viewHolderContainer.tvCabinetNumber.setText(driverSonoVoSec.getContNo());
                viewHolderContainer.tvShipCompany.setText(driverSonoVoSec.getShipCompany());
                viewHolderContainer.tvCounterPoint.setText(driverSonoVoSec.getCarryPort());
                viewHolderContainer.tvCounterPoint2.setText(driverSonoVoSec.getGetPort());
            }

        }

        // 判断底部的按钮
        mToolbarCommonBinding.toolbar.getMenu().clear();// 清除右上角的菜单
        switch (driverOrderDetailVo.getOrder().getOrderStatus()) {
            case "0":
                // 待接单
                mDataBinding.imgState.setImageResource(R.drawable.ic_djd_23);
                // 显示操作
                if (mIsShowComitButton) {
                    mDataBinding.llOperation.setVisibility(View.VISIBLE);
                    mDataBinding.flOperationCar.setVisibility(View.GONE);
                    mDataBinding.flProgressUpdate.setVisibility(View.GONE);
                }
                // 能否拒单（0:非可拒 1:可拒）
                if (driverOrderDetailVo.getOrder().getRefusable() == null || driverOrderDetailVo.getOrder().getRefusable().equals("0"))
                    mDataBinding.btnRefuse.setVisibility(View.GONE);
                else
                    mDataBinding.btnRefuse.setVisibility(View.VISIBLE);

                // 隐藏放单点
                mDataBinding.rlPlaceSinglePoint.setVisibility(View.GONE);
                mDataBinding.vLine5.setVisibility(View.GONE);
                mDataBinding.rlPlaceSinglePointDetail.setVisibility(View.GONE);
                mDataBinding.vLine6.setVisibility(View.GONE);
                // 路线不可编辑
                // 设置线路箭头
                SpannableStringBuilder spannableStringBuilder = OrderAssistant.getInstance(R.drawable.ic_dc_s02).handlerDeliverRoute(driverOrderDetailVo.getOrder().getDeliveryRoute());
                mDataBinding.tvRoute.setText(spannableStringBuilder);
                mDataBinding.rlRoute.setOnClickListener(null);
                mDataBinding.imgCounterPoint.setVisibility(View.GONE);

                break;
            case "1":
                // 已接单
                mDataBinding.imgState.setImageResource(R.drawable.ic_yjd_01);
                // 显示操作
                if (mIsShowComitButton) {
                    if (driverOrderDetailVo.getOrder().getVehicleFlag().equals("1")) {
                        // 进度更新才显示详情 右上角显示操作
                        initToolbarNav(mToolbarCommonBinding.toolbar, "派车单详情", R.menu.menu_order_detail, this);
                        // 进度更新
                        mDataBinding.llOperation.setVisibility(View.GONE);
                        mDataBinding.flOperationCar.setVisibility(View.GONE);
                        mDataBinding.flProgressUpdate.setVisibility(View.VISIBLE);
                    } else {
                        // 现在出车
                        mDataBinding.llOperation.setVisibility(View.GONE);
                        mDataBinding.flOperationCar.setVisibility(View.VISIBLE);
                        mDataBinding.flProgressUpdate.setVisibility(View.GONE);
                    }
                }

                // 显示放单点
                showPutAddress(driverOrderDetailVo);

                // 路线可编辑
                SpannableStringBuilder spannableStringBuilder2 = OrderAssistant.getInstance(R.drawable.ic_dc_s02).handlerDeliverRoute(driverOrderDetailVo.getOrder().getDeliveryRoute());
                mDataBinding.tvRoute.setText(spannableStringBuilder2);
                mDataBinding.rlRoute.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        start(NodeEditFragment.newInstance(mOrderId, mDriverOrderDetailVoSec.getOrder().getClientId()));
                    }
                });
                mDataBinding.imgCounterPoint.setVisibility(View.VISIBLE);

                break;
            case "2":
                // 已完成
                mDataBinding.imgState.setImageResource(R.drawable.ic_ywc_01);
                // 右上角显示操作
                initToolbarNav(mToolbarCommonBinding.toolbar, "派车单详情", R.menu.menu_order_detail, this);

                // 显示放单点
                showPutAddress(driverOrderDetailVo);

                // 路线不可编辑
                SpannableStringBuilder spannableStringBuilder3 = OrderAssistant.getInstance(R.drawable.ic_dc_s02).handlerDeliverRoute(driverOrderDetailVo.getOrder().getDeliveryRoute());
                mDataBinding.tvRoute.setText(spannableStringBuilder3);
                mDataBinding.rlRoute.setOnClickListener(null);
                mDataBinding.imgCounterPoint.setVisibility(View.GONE);
                break;
        }
        if (ObjectUtils.parseInt(driverOrderDetailVo.getOrder().getOrderStatus(), 0) > 2) {
            // 已关闭
            mDataBinding.imgState.setImageResource(R.drawable.ic_ygb_01);
            // 右上角显示操作
            initToolbarNav(mToolbarCommonBinding.toolbar, "派车单详情", R.menu.menu_order_detail, this);

            // 显示放单点
            showPutAddress(driverOrderDetailVo);

            // 路线不可编辑
            SpannableStringBuilder spannableStringBuilder4 = OrderAssistant.getInstance(R.drawable.ic_dc_s02).handlerDeliverRoute(driverOrderDetailVo.getOrder().getDeliveryRoute());
            mDataBinding.tvRoute.setText(spannableStringBuilder4);
            mDataBinding.rlRoute.setOnClickListener(null);
            mDataBinding.imgCounterPoint.setVisibility(View.GONE);
        }
    }

    /**
     * 显示放单点
     */
    private void showPutAddress(DriverOrderDetailVoSec driverOrderDetailVo) {

        // 判断如果id为空，则隐藏
        if (TextUtils.isEmpty(driverOrderDetailVo.getOrder().getPlace().getId())) {
            // 隐藏放单点
            mDataBinding.rlPlaceSinglePoint.setVisibility(View.GONE);
            mDataBinding.vLine5.setVisibility(View.GONE);
            mDataBinding.rlPlaceSinglePointDetail.setVisibility(View.GONE);
            mDataBinding.vLine6.setVisibility(View.GONE);
            return;
        }

        mDataBinding.rlPlaceSinglePoint.setVisibility(View.VISIBLE);
        mDataBinding.vLine5.setVisibility(View.VISIBLE);
        mDataBinding.rlPlaceSinglePointDetail.setVisibility(View.VISIBLE);
        mDataBinding.vLine6.setVisibility(View.VISIBLE);
        // 名称
        mDataBinding.tvPointTitle.setText(driverOrderDetailVo.getOrder().getPlace().getName());
        // 联系电话
        mDataBinding.tvPointMobileValue.setText(driverOrderDetailVo.getOrder().getPlace().getPhone());
        mDataBinding.tvPointMobileValue.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mDataBinding.tvPointMobileValue.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                PhoneUtils.nodeCallPhone(_mActivity, getFragmentManager(), new String[]{driverOrderDetailVo.getOrder().getPlace().getPhone()});
            }
        });

        // 导航
        mDataBinding.imgPointAddress.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (driverOrderDetailVo.getOrder().getPlace() != null && driverOrderDetailVo.getOrder().getPlace().getLongitude() != -1) {
                    LatLng latLng = new LatLng(driverOrderDetailVo.getOrder().getPlace().getLatitude(), driverOrderDetailVo.getOrder().getPlace().getLongitude());
                    AMapUtil.startAMapNavi(getContext(), latLng);
                } else if (!TextUtils.isEmpty(driverOrderDetailVo.getOrder().getPlace().getAddress())) {
                    // 如果有address，就显示导航
                    aMapUtil.startAMapNavi(_mActivity, getContext(), driverOrderDetailVo.getOrder().getPlace().getAddress());
                }
            }
        });
        // 地址
        mDataBinding.tvPointAddressValue.setText(driverOrderDetailVo.getOrder().getPlace().getAddress());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_node:
                // 任务进度
                startForResult(NodeFragment.newInstance(mOrderId), -1);
                break;
        }
        return false;
    }

    @Override
    public void openPutOrderAddress(DriverOrderWrapper driverOrderWrapper, List<InfoPutorderPlace> infoPutorderPlaces, int position) {
        DriverOrderWrapper driverOrderWrapper2 = new DriverOrderWrapper();
        driverOrderWrapper2.setId(driverOrderWrapper.getId());
        driverOrderWrapper2.setOrderId(driverOrderWrapper.getOrderId());

        startForResult(PutOrderAddressFragment.newInstance(driverOrderWrapper2, (ArrayList<InfoPutorderPlace>) infoPutorderPlaces, -1, mOrderType), REQUEST_PUT_ORDER_ADDRESS);
    }

    /**
     * 初始化装/拆 箱任务
     *
     * @param sonoTaskVo          装/拆 箱实体类
     * @param position            索引
     * @param viewHolderContainer 货柜的view
     */
    @SuppressLint("SetTextI18n")
    private void initSonoTaskVo(SonoTaskVo sonoTaskVo, int position, ViewHolderContainer viewHolderContainer) {
        // fl添加拆装箱
        ViewHolderBox viewHolderBox = new ViewHolderBox(LayoutInflater.from(getContext()).inflate(R.layout.view_order_detail_box, null, false));
        if (position == 0) {
            viewHolderContainer.flContainer1.addView(viewHolderBox.view);
        } else {
            viewHolderContainer.flContainer2.addView(viewHolderBox.view);
        }

        // 赋值
        viewHolderBox.tvTitle.setText(sonoTaskVo.getSealNo());
        viewHolderBox.tvWaybillNumber.setText(sonoTaskVo.getmBlNo());
        viewHolderBox.tvNameGoods.setText(sonoTaskVo.getGoodsName());
        if (sonoTaskVo.getOtherService() == 0) {
            viewHolderBox.tvTitleUnloadingTime.setText("装货时间");
        } else {
            viewHolderBox.tvTitleUnloadingTime.setText("卸货时间");
        }
        viewHolderBox.tvUnloadingTime.setText(sonoTaskVo.getLoadDate());

        // 添加 装卸货 门点
        viewHolderBox.llBox.removeAllViews();
        for (int i = 0; i < sonoTaskVo.getGoodsAddrs().size(); i++) {
            DriverGoodsAddr driverGoodsAddr = sonoTaskVo.getGoodsAddrs().get(i);
            ViewHolderDoorPoint viewHolderDoorPoint = new ViewHolderDoorPoint(LayoutInflater.from(getContext()).inflate(R.layout.view_order_detail_door, null, false));
            viewHolderBox.llBox.addView(viewHolderDoorPoint.view);
            // 判断装卸货
            if (sonoTaskVo.getOtherService() == 0) {
                viewHolderDoorPoint.tvPointTitle.setText("装货门点" + (sonoTaskVo.getGoodsAddrs().size() == 1 ? "" : (i + 1)));
                viewHolderDoorPoint.tvRemarkTitle.setText("装货说明:");
            } else {
                viewHolderDoorPoint.tvPointTitle.setText("卸货门点" + (sonoTaskVo.getGoodsAddrs().size() == 1 ? "" : (i + 1)));
                viewHolderDoorPoint.tvRemarkTitle.setText("卸货说明:");
            }

            viewHolderDoorPoint.tvCompany.setText(driverGoodsAddr.getGoodsUnit());
            viewHolderDoorPoint.tvAddress.setText(driverGoodsAddr.getAddress());
            new SelectableTextHelper.Builder(viewHolderDoorPoint.tvAddress)
                    .setSelectedColor(getResources().getColor(R.color.primary))
                    .setCursorHandleSizeInDp(20)
                    .setCursorHandleColor(getResources().getColor(R.color.orange500))
                    .build();
            viewHolderDoorPoint.tvContacts.setText(driverGoodsAddr.getContacts());
            viewHolderDoorPoint.tvContactsPhone.setText(driverGoodsAddr.getMobile());
            viewHolderDoorPoint.tvContactsPhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            viewHolderDoorPoint.tvContactsPhone.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{driverGoodsAddr.getMobile()});
                }
            });
            viewHolderDoorPoint.tvContactsPhone2.setText(driverGoodsAddr.getTel());
            viewHolderDoorPoint.tvContactsPhone2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            viewHolderDoorPoint.tvContactsPhone2.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{driverGoodsAddr.getTel()});
                }
            });
            viewHolderDoorPoint.tvRemark.setText(driverGoodsAddr.getRemarks());
            viewHolderDoorPoint.tvRemark.setTrimCollapsedText("查看更多");
            viewHolderDoorPoint.tvRemark.setTrimExpandedText("隐藏");


            viewHolderDoorPoint.imgAddress.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    aMapUtil.startAMapNavi(_mActivity, getContext(), driverGoodsAddr.getAddress());
                }
            });
        }
    }

    /**
     * 货柜
     */
    static class ViewHolderContainer {
        View view;

        @BindView(R.id.imgContainer)
        ImageView imgContainer;
        @BindView(R.id.tvContainerTitle)
        TextView tvContainerTitle;
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.rlContainer)
        RelativeLayout rlContainer;
        @BindView(R.id.tvCabinetNumber)
        TextView tvCabinetNumber;
        @BindView(R.id.tvShipCompany)
        TextView tvShipCompany;
        @BindView(R.id.tvCounterPoint)
        TextView tvCounterPoint;
        @BindView(R.id.imgCounterPoint)
        ImageView imgCounterPoint;
        @BindView(R.id.tvCounterPoint2)
        TextView tvCounterPoint2;
        @BindView(R.id.imgCounterPoint2)
        ImageView imgCounterPoint2;
        @BindView(R.id.tab_layout)
        TabLayout tabLayout;
        @BindView(R.id.flContainer1)
        FrameLayout flContainer1;
        @BindView(R.id.flContainer2)
        FrameLayout flContainer2;
        @BindView(R.id.rlCounterPoint)
        RelativeLayout rlCounterPoint;
        @BindView(R.id.rlCounterPoint2)
        RelativeLayout rlCounterPoint2;

        ViewHolderContainer(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 装拆箱
     */
    static class ViewHolderBox {

        View view;

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvWaybillNumber)
        TextView tvWaybillNumber;
        @BindView(R.id.tvTitleUnloadingTime)
        TextView tvTitleUnloadingTime;
        @BindView(R.id.tvNameGoods)
        TextView tvNameGoods;
        @BindView(R.id.tvUnloadingTime)
        TextView tvUnloadingTime;
        @BindView(R.id.llBox)
        LinearLayout llBox;

        ViewHolderBox(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 装卸货门点
     */
    static class ViewHolderDoorPoint {

        View view;

        @BindView(R.id.tvPointTitle)
        TextView tvPointTitle;
        @BindView(R.id.tvCompanyTitle)
        TextView tvCompanyTitle;
        @BindView(R.id.tvCompany)
        TextView tvCompany;
        @BindView(R.id.tvAddressTitle)
        TextView tvAddressTitle;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.imgAddress)
        ImageView imgAddress;
        @BindView(R.id.tvContactsTitle)
        TextView tvContactsTitle;
        @BindView(R.id.tvContacts)
        TextView tvContacts;
        @BindView(R.id.tvContactsPhone)
        TextView tvContactsPhone;
        @BindView(R.id.tvContactsPhone2)
        TextView tvContactsPhone2;
        @BindView(R.id.tvRemarkTitle)
        TextView tvRemarkTitle;
        @BindView(R.id.tvRemark)
        ReadMoreTextView tvRemark;

        ViewHolderDoorPoint(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }


}
