package com.yaoguang.driver.home;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.elvishew.xlog.XLog;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.appcommon.common.eventbus.HomeMessageOrderChildEvent;
import com.yaoguang.appcommon.common.eventbus.NotificationEvent;
import com.yaoguang.appcommon.databinding.ToolbarHomeBinding;
import com.yaoguang.appcommon.home.weather.WeatherFragment;
import com.yaoguang.common.appcommon.utils.AppUtils;
import com.yaoguang.common.appcommon.widget.head.PullHead;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.DateUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.activitys.LoginActivity;
import com.yaoguang.driver.base.basemap.BaseMapFragment;
import com.yaoguang.driver.databinding.FragmentHomeBinding;
import com.yaoguang.driver.databinding.ViewHomeHeadBinding;
import com.yaoguang.driver.databinding.ViewHomeMenuBinding;
import com.yaoguang.driver.databinding.ViewOrderProcessBinding;
import com.yaoguang.driver.home.driverstatus.DriverStatusSwitchFragment;
import com.yaoguang.driver.home.wiget.Banner;
import com.yaoguang.driver.home.wiget.PopHome;
import com.yaoguang.driver.main.MainFragment;
import com.yaoguang.driver.my.message.PlatformMessageFragment;
import com.yaoguang.driver.my.my.MyFragment;
import com.yaoguang.driver.order.OrderFragment;
import com.yaoguang.driver.order.detail.OrderDetailFragment;
import com.yaoguang.driver.order.home.HomeOrderMessageChildFragment;
import com.yaoguang.driver.order.map.MapSearchFragment;
import com.yaoguang.driver.order.map.OrderNodeMapFragment;
import com.yaoguang.driver.order.message.MessageOrderChildFragment;
import com.yaoguang.driver.util.HtmlSwipeFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.driver.util.WeatherUtils;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderProgressWrapper;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.lib.annotation.aspect.BackKey;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.yaoguang.common.common.Constants.FLAG_ORDER;
import static com.yaoguang.common.common.Constants.FLAG_ORDER_DETAIL;
import static com.yaoguang.common.common.Constants.FLAG_PERSONL_MESSAGE;
import static com.yaoguang.common.common.Constants.FLAG_PLATFORM_MESSAGE;
import static com.yaoguang.common.common.Constants.FLAG_REFRESH_PAGE;
import static com.yaoguang.common.common.Constants.FLAG_REFRESH_PROGRESS;
import static com.yaoguang.common.common.DateUtils.YYYY_MM_DD_HH_MM;
import static com.yaoguang.driver.home.driverstatus.DriverStatusSwitchFragment.DRIVER_WORK_STATUS;
import static com.yaoguang.driver.home.driverstatus.DriverStatusSwitchFragment.FLAG_UPDATE_DRIVER_STATUS;
import static com.yaoguang.driver.my.message.HomePlatformMessageFragment.LOOK_PLATFORM_MESSAGE;
import static com.yaoguang.driver.order.child.OrderChildFragment.UPLOAD_PROCESS;
import static com.yaoguang.driver.util.WeatherUtils.FLAG_WEATHER;


public class HomeFragment extends BaseMapFragment<HomePresenter, FragmentHomeBinding> implements HomeContacts.View, Toolbar.OnMenuItemClickListener {
    private Banner mBanner;
    private Timer mTimer;
    private ToolbarHomeBinding mToolbarHomeBinding;
    private ViewHomeMenuBinding mViewHomeMenuBinding;
    private ViewOrderProcessBinding mViewOrderProcessBinding;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 不要定位按钮
        setLocationButton(false);

        // 检测密码
//        checkPassword();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        App.locationHistoryManager.saveCache();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);

        mToolbarHomeBinding = DataBindingUtil.bind(view.findViewById(R.id.toolbarHomeCommon));
        ViewHomeHeadBinding mViewHomeHeadBinding = mDataBinding.includeViewHomeHead;
        mViewHomeMenuBinding = mViewHomeHeadBinding.includeViewHomeMenu;
        mViewOrderProcessBinding = mViewHomeHeadBinding.includeViewOrderProcess;

        // 显示 主页业务消息
        HomeOrderMessageChildFragment homeOrderMessageChildFragment = HomeOrderMessageChildFragment.newInstance(4);
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().replace(R.id.frameLayout,
                    homeOrderMessageChildFragment, "homeOrderMessageChildFragment").show(homeOrderMessageChildFragment).commit();
        }

        mViewOrderProcessBinding.llOrderProcess.setVisibility(View.GONE);
        mDataBinding.nestedScrollView.setFillViewport(true);
        mToolbarHomeBinding.toolbarTitle.setText(AppUtils.getAppName());
        initSwipeRefresh();
        mBanner = new Banner(getContext(), mViewHomeHeadBinding.convenientBanner);

        // 天气ui
        WeatherUtils.init();
        // 弹窗
        PopHome.initDialog(this, getActivity());
        // 初始化刷新
//        mInitialView.trlView.startRefresh();
        EventBus.getDefault().post(new HomeEvent(FLAG_REFRESH_PAGE));

//        CrashReport.testJavaCrash();
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        if (getContext() == null) return;
        mPresenter.setData(Injection.provideDriverRepository(getContext()));
        mPresenter.setAppRepository(Injection.provideAppRepository(getContext()));
        mPresenter.setOrderRepository(Injection.provideOrderRepository(getContext()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    private void initSwipeRefresh() {
        mDataBinding.trlView.setNestedScrollingEnabled(false);
        //上拉刷新
        mDataBinding.trlView.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                // 刷新首页 如果是在首页tab下
                // 如果非tab下刷新，那么把刷新的改变成finishLoadMore
                if (getTopFragment() instanceof HomeFragment || getTopFragment() instanceof MyFragment ||
                        getTopFragment() instanceof OrderFragment || getTopFragment() instanceof HomeOrderMessageChildFragment)
                    EventBus.getDefault().post(new HomeEvent(FLAG_REFRESH_PAGE));
                else
                    refreshLayout.finishLoadmore();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                EventBus.getDefault().post(new HomeMessageOrderChildEvent("下一页"));
                XLog.i("正在加载订单数据，");
                refreshLayout.finishLoadmore();
            }

        });

        PullHead headerView = new PullHead(getContext());
        mDataBinding.trlView.setHeaderView(headerView);
        mDataBinding.trlView.setHeaderHeight(110);
        mDataBinding.trlView.setAutoLoadMore(true);
    }

    @Override
    protected void initListener() {
        // 查看所有订单消息
        mViewHomeMenuBinding.tvLookAllOrderMessage.setOnClickListener(v -> {
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).startBrotherFragment(MessageOrderChildFragment.newInstance(4));
            }
        });

        // 天气
        mToolbarHomeBinding.rlWeather.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            WeatherUtils.getWeather();
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).startBrotherFragment(WeatherFragment.newInstance());
            }
        });

        // 进入进度更新
        mViewOrderProcessBinding.llMap.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).startBrotherFragment(MapSearchFragment.newInstance(null));
            }
        });

        // 右侧菜单
        mToolbarHomeBinding.toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (AppClickUtil.isDuplicateClick()) return false;
        // 司机休假设置
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).startBrotherFourResultFragment(DriverStatusSwitchFragment.newInstance(), FLAG_UPDATE_DRIVER_STATUS);
        }
        return false;
    }

    private void updateDriverStatus(boolean driverWorkStatus) {
        if (mToolbarHomeBinding == null) return;

        mToolbarHomeBinding.toolbar.getMenu().clear();
        // 司机休息状态
        mToolbarHomeBinding.toolbar.inflateMenu(driverWorkStatus ? R.menu.driver_work : R.menu.driver_leave);
    }


    /**
     * 描    述：更新位置
     * 作    者：韦理英
     * 时    间：
     */
    @Override
    protected void updateLocation() {
        App.locationInit();
    }

    @Subscribe
    public void home(final HomeEvent event) {
        App.handler.postDelayed(() -> {
            if (HomeFragment.this.getActivity() == null) return;
            ULog.i("home event=" + event.getType());
            switch (event.getType()) {
                case FLAG_REFRESH_PROGRESS:
                    // 刷新进度
                    mPresenter.getCurrentProgress();
                    break;
                case FLAG_REFRESH_PAGE:
                    // 通知 订单刷新
                    HomeOrderMessageChildFragment homeOrder = HomeFragment.this.findFragment(HomeOrderMessageChildFragment.class);
                    if (homeOrder != null) {
                        homeOrder.refreshData();
                    }
                    // 刷新未读数
                    MyFragment myFragment = HomeFragment.this.findFragment(MyFragment.class);
                    if (myFragment != null) {
                        myFragment.refreshUnReadCount();
                    }
                    // 刷新首页
                    mPresenter.subscribe();
                    break;
                case FLAG_WEATHER:
                    // 天气
                    YoYo.with(Techniques.FadeIn).playOn(mToolbarHomeBinding.rlWeather);
                    WeatherUtils.showUi(WeatherUtils.getCacheWeather(), mToolbarHomeBinding.imgWeather,
                            mToolbarHomeBinding.tvWeather, mToolbarHomeBinding.tvCity);
                    break;
                case LOOK_PLATFORM_MESSAGE:
                    // 查看平台消息
                    if (event.getObject() == null) return;
                    if (HomeFragment.this.getParentFragment() != null) {
                        ((MainFragment) HomeFragment.this.getParentFragment()).startBrotherFragment(HtmlSwipeFragment.newInstance("平台公告消息", (String) event.getObject()));
                    }
                    break;
                default:
                    break;
            }
        }, 500);
    }


    /**
     * 描    述：显示Banner
     * 作    者：韦理英
     * 时    间：
     */
    @Override
    public void showBannerImage(@NonNull ArrayList<BannerPic> result) {
        mBanner.netLoad(result);
    }

    /**
     * 描    述：显示订单进度
     * 作    者：韦理英
     * 时    间：
     */
    @Override
    public void showOrderProgress(@Nullable final DriverOrderProgressWrapper value) {
        if (mViewOrderProcessBinding == null || mViewOrderProcessBinding.llOrderProcess == null)
            return;

        if (value != null) {
            // 如果有接单数据
            mViewOrderProcessBinding.llOrderProcess.setVisibility(View.VISIBLE);
            mViewOrderProcessBinding.map.setVisibility(View.GONE);
            mViewOrderProcessBinding.map.onPause(); // 暂时不用地图
            mViewOrderProcessBinding.llMap.setVisibility(View.GONE);
            mViewOrderProcessBinding.tvOrderId.setText(value.getOrderSn());
            mViewOrderProcessBinding.tvNodeName.setText(value.getNodeName());
            mViewOrderProcessBinding.tvOrderType.setText(value.getOrderType());
            mViewOrderProcessBinding.tvDeliveryRoute.setText(value.getSpannableStringBuilder());
            mViewOrderProcessBinding.llOrderProcess.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                App.handler.postDelayed(HomeFragment.this::toScrollHeadView, 300);
                mPresenter.gotoMap(value.getOrderSn(), value.getOrderId());
            });
            App.launchLocationHistory(value);
            mViewOrderProcessBinding.tvBannerInfo.setVisibility(View.VISIBLE);
            mViewOrderProcessBinding.tvBannerInfo.setText(getString(R.string.current_mission_process));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                mViewOrderProcessBinding.tvBannerInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(UiUtils.getDrawable(R.drawable.ic_jindu), null, null, null);
        } else {
            // 没有接单数据
            mViewOrderProcessBinding.llOrderProcess.setOnClickListener(null);
            mViewOrderProcessBinding.map.onResume(); // 恢复使用地图
            mViewOrderProcessBinding.llOrderProcess.setVisibility(View.GONE);
            mViewOrderProcessBinding.map.setVisibility(View.VISIBLE);
            mViewOrderProcessBinding.llMap.setVisibility(View.VISIBLE);
            // 设置当前地址
            if (Injections.getLocationBiz() != null && Injections.getLocationBiz().getLast() != null && Injections.getLocationBiz().getLast().getStreet() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mViewOrderProcessBinding.tvBannerInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(UiUtils.getDrawable(R.drawable.ic_wz_05), null, null, null);
                    mViewOrderProcessBinding.tvBannerInfo.setText(String.format("当前位置-%s", Injections.getLocationBiz().getLast().getStreet()));
                }
            } else mViewOrderProcessBinding.tvBannerInfo.setVisibility(View.GONE);

            App.locationHistoryManager.destroyLocation();
        }
    }


    /**
     * 描    述：刷动到顶部
     * （解决有时会跳到tab顶部的问题）
     * 作    者：韦理英
     * 时    间：
     */
    @Override
    public void toScrollHeadView() {
        if (mDataBinding.nestedScrollView != null) {
            mDataBinding.nestedScrollView.scrollTo(0, 0);
        }
    }

    /**
     * 描    述：去登录页
     * 作    者：韦理英
     * 时    间：
     */
    @Override
    public void toLoginActivity() {
        LoginActivity.newInstance(getActivity(), true, null, null);
        if (getActivity() != null) getActivity().finish();
    }

    /**
     * 描    述：hide 下拉
     * 作    者：韦理英
     * 时    间：
     */
    @Override
    public void hideSRLLoadingView() {
        mDataBinding.trlView.finishRefreshing();
    }

    /**
     * 描    述：打开订单地图
     * 作    者：韦理英
     * 时    间：
     *
     * @param result [节点数据]
     * @param id     [订单id]
     */
    @Override
    public void startOrderNodeMapFragment(@NonNull List<DriverOrderNode> result, @NonNull String id) {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).startBrotherFourResultFragment(OrderNodeMapFragment.newInstance(result, id), UPLOAD_PROCESS);
        }
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        /*
          通知主页状态设置
          1 休假 2 工作 3 启动定时器 4 取消定时器
         */
        if (FLAG_UPDATE_DRIVER_STATUS == requestCode && data != null) {
            // 更新司机状态
            int value = data.getInt(DRIVER_WORK_STATUS, 2);
            switch (value) {
                case 1:
                    updateDriverStatus(true);
                    break;
                case 2:
                    updateDriverStatus(false);
                    break;
                case 3:
                    // 获取休假状态
                    mPresenter.getDriverLeaveStatus();
                    break;
                case 4:
                    if (mTimer != null) {
                        ULog.i("取消定时器");
                        mTimer.cancel();
                    }
                    break;
            }
        } else if (UPLOAD_PROCESS == resultCode && data != null) {
            // 更新进度
            mPresenter.getCurrentProgress();
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Subscribe(sticky = true)
    public void onNotificationEvent(NotificationEvent event) {
        if (getParentFragment() == null || getParentFragment().getParentFragment() == null) return;
        final String mToPage = event.getToPage();
        final String mToPageId = event.getToPageId();
        if (mToPage == null) return;

        App.handler.postDelayed(() -> {
            switch (mToPage) {
                case FLAG_ORDER:
                    // 订单中心
                    ((MainFragment) HomeFragment.this.getParentFragment().getParentFragment()).changeTab(1);
                    break;
                case FLAG_ORDER_DETAIL:
                    // 订单详情
                    if (mToPageId != null) {
                        App.handler.postDelayed(() ->
                                ((MainFragment) HomeFragment.this.getParentFragment()).startBrotherFragment(OrderDetailFragment.newInstance(mToPageId)), 500);
                    }
                    break;
                case FLAG_PERSONL_MESSAGE:
                    //企业消息
                    break;
                case FLAG_PLATFORM_MESSAGE:
                    // 平台消息
                    App.handler.postDelayed(() ->
                            ((MainFragment) HomeFragment.this.getParentFragment()).startBrotherFragment(PlatformMessageFragment.newInstance()), 500);
                    break;
            }
        }, 500);
        EventBus.getDefault().removeStickyEvent(event);
    }

    /**
     * 显示司机休假状态，定时器
     */
    @Override
    public void showDriverLeaveStatus(@NonNull BaseResponse<DriverStatusSwitch> value) {
        if (mTimer != null) {
            ULog.i("取消上一个定时器");
            mTimer.cancel();
        }

        // 启动定时器
        if (value.getResult() != null && value.getResult().getDriverRestPlan() != null && !TextUtils.isEmpty(value.getResult().getDriverRestPlan().getStartDate())) {
            // 计算秒数
            String currentDate = value.getResult().getCurrentDate();
            Date serverDate = DateUtils.stringToDate(currentDate, YYYY_MM_DD_HH_MM);
            long serverTime = serverDate.getTime();
            Date leaveDate = DateUtils.stringToDate(value.getResult().getDriverRestPlan().getStartDate(), YYYY_MM_DD_HH_MM);
            long leaveTime = leaveDate.getTime();
//            long miao = (leaveTime - serverTime) / 1000;
            long miao = (leaveTime - serverTime) + 5000;
            ULog.i("多少秒后显示" + miao);
            // 如果大于0，就启动定时刷新，加5秒确保刷新安全
            if (miao > 0) {
                // 定时前，先是工作状态，定时结束后才是休假状态
                updateDriverStatus(true);

                mTimer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        App.handler.post(() ->
                                updateDriverStatus(false));
                    }
                };
                mTimer.schedule(task, miao);
            } else {
                // 如果小于0秒，就马上显示
                updateDriverStatus(false);
            }
        } else {
            updateDriverStatus(true);
        }
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
