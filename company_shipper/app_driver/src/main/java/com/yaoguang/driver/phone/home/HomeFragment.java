package com.yaoguang.driver.phone.home;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.yaoguang.appcommon.common.base.basemap.BaseMapDataBindFragment;
import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.appcommon.common.eventbus.NotificationEvent;
import com.yaoguang.appcommon.databinding.ToolbarHomeBinding;
import com.yaoguang.appcommon.phone.home.weathergaode.WeatherGaoDeFragment;
import com.yaoguang.appcommon.phone.home.weathergaode.WeatherGaoDeUtils;
import com.yaoguang.driver.phone.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.phone.activitys.login.LoginActivity;
import com.yaoguang.driver.databinding.FragmentHomeBinding;
import com.yaoguang.driver.phone.home.adapter.HomeOrderChildAdapterWait;
import com.yaoguang.driver.phone.home.driverstatus.StatusSwitchFragment;
import com.yaoguang.driver.phone.home.wiget.Banner;
import com.yaoguang.driver.phone.home.wiget.PopHome;
import com.yaoguang.driver.phone.main.MainActivity;
import com.yaoguang.driver.phone.main.MainFragment;
import com.yaoguang.driver.phone.my.message.PlatformMessageFragment;
import com.yaoguang.driver.phone.my.messageorder.MessageOrderFragment;
import com.yaoguang.driver.phone.my.my.MyFragment;
import com.yaoguang.driver.phone.order.OrderFragment;
import com.yaoguang.driver.phone.order.detail.OrderDetailFragment;
import com.yaoguang.appcommon.phone.node.BaseNodeFragment;
import com.yaoguang.driver.phone.order.node.NodeFragment;
import com.yaoguang.driver.util.HtmlSwipeFragment;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderProgressWrapper;
import com.yaoguang.lib.appcommon.utils.AppUtils;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.appcommon.widget.head.Pull2Header;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.map.common.AMapUtil;
import com.yaoguang.map.location.impl.LocationManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yaoguang.driver.phone.home.driverstatus.StatusSwitchFragment.DRIVER_WORK_STATUS;
import static com.yaoguang.driver.phone.home.driverstatus.StatusSwitchFragment.FLAG_UPDATE_DRIVER_STATUS;
import static com.yaoguang.driver.phone.my.message.HomePlatformMessageFragment.LOOK_PLATFORM_MESSAGE;
import static com.yaoguang.lib.common.constants.Constants.FLAG_ORDER;
import static com.yaoguang.lib.common.constants.Constants.FLAG_ORDER_DETAIL;
import static com.yaoguang.lib.common.constants.Constants.FLAG_PERSONL_MESSAGE;
import static com.yaoguang.lib.common.constants.Constants.FLAG_PLATFORM_MESSAGE;
import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_PAGE;
import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_PROGRESS;
import static com.yaoguang.lib.common.DateUtils.YYYY_MM_DD_HH_MM;

/**
 * Update zhongjh
 * Data 2018-03-13
 */
public class HomeFragment extends BaseMapDataBindFragment<FragmentHomeBinding> implements HomeContacts.View, Toolbar.OnMenuItemClickListener {

    HomeContacts.Presenter mPresenter;

    ViewHolder mHeader;

    private Banner mBanner;
    private Timer mTimer;
    private ToolbarHomeBinding mToolbarHomeBinding;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    HomeOrderChildAdapterWait mHomeOrderChildAdapterWait;

    int mPageIndex;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 不要定位按钮
        setLocationButton(false);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        getPresenter().subscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        App.locationHistoryManager.saveCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void savedInstanceState(Bundle outState) {
        super.savedInstanceState(outState);
        // 实例化顶部view
        mHeader = new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.view_home_head, null, false));
    }

    @Override
    public void init() {
        // 新的一页
        mHomeOrderChildAdapterWait = new HomeOrderChildAdapterWait(getContext());
        // 显示主页业务消息
        RecyclerViewUtils.initPage(mDataBinding.rlMessage, mHomeOrderChildAdapterWait, null, getContext(), false);
        // 添加头部view
        mDataBinding.flHomeHead.addView(mHeader.view);
        // 让嵌套滑动更流畅
        LinearLayoutManager layoutManager = (LinearLayoutManager) mDataBinding.rlMessage.getLayoutManager();
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mDataBinding.rlMessage.setHasFixedSize(true);
        mDataBinding.rlMessage.setNestedScrollingEnabled(false);

        mPresenter = new HomePresenter(this);
        EventBus.getDefault().register(this);

        mToolbarHomeBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.toolbarHomeCommon));

        mPresenter.getMessageList(1);

        mHeader.llOrderProcess.setVisibility(View.GONE);

        mToolbarHomeBinding.toolbarTitle.setText(AppUtils.getAppName());
        initSwipeRefresh();
        mBanner = new Banner(getContext(), mHeader.convenientBanner);

        // 弹窗
        PopHome.initDialog(this, getActivity());
    }

    private void initSwipeRefresh() {
        mDataBinding.refreshLayout.setRefreshHeader(new Pull2Header(getContext()));
    }


    @Override
    public void initWeather() {
        WeatherGaoDeUtils weatherGaoDeUtils = new WeatherGaoDeUtils();
        weatherGaoDeUtils.getWeatherLiveSearched(HomeFragment.this.getContext(), (liveResult, i, location) -> {
            mDataBinding.toolbarHomeCommon.tvCity.setText(location.getCity());
            mDataBinding.toolbarHomeCommon.tvWeather.setText(liveResult.getLiveResult().getTemperature());
        });
    }

    @Override
    public void initListener() {
        // 刷新
        mDataBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新首页 如果是在首页tab下  如果非tab下刷新，那么把刷新的改变成finishLoadMore
            if (getTopFragment() instanceof HomeFragment || getTopFragment() instanceof MyFragment ||
                    getTopFragment() instanceof OrderFragment)
                mPresenter.subscribe();
            refreshLayout.finishRefresh();
        });

        // 加载
        mDataBinding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载更多
            mPresenter.getMessageList(mPageIndex + 1);
        });


        // 查看所有订单消息
        mHeader.tvLookAllOrderMessage.setOnClickListener(v -> {
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).start(MessageOrderFragment.newInstance());
            }
        });

        // 天气
        mToolbarHomeBinding.llWeather.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

//            WeatherUtils.getWeather();
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).start(WeatherGaoDeFragment.newInstance());
            }
        });

        // 进入导航
        mHeader.llMap.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            if (getParentFragment() != null) {
                LocationManager locationManager = new LocationManager();
                locationManager.init(BaseApplication.getInstance().getBaseContext());
                locationManager.setComeback(location -> {
                    locationManager.destroyLocation();
                    LatLng latLng = new LatLng(location.getLat(), location.getLon());
                    AMapUtil.startAMapNavi(getContext(), latLng);
                });
            }
        });

        // 右侧菜单
        mToolbarHomeBinding.toolbar.setOnMenuItemClickListener(this);

        // 显示当前位置和围栏
        mToolbarHomeBinding.toolbar.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                ((MainActivity) _mActivity).startTestActivity();
            }
        });

        // 消息列表点击
        mHomeOrderChildAdapterWait.setOnItemClickListener((itemView, item, position) -> {
            // 跳转详情的
            ((MainFragment) getParentFragment()).start(OrderDetailFragment.newInstance(((DriverOrderMsgWrapper) item).getDriverOrderWrapper().getOrderId(), true, true));
            // 设置已读
            mPresenter.readBatch(((DriverOrderMsgWrapper) item).getId());
        });

        // 消息列表语音的
        mHomeOrderChildAdapterWait.setOnRecyclerViewItemClickListener((itemView, item, position) -> ((MainActivity) _mActivity).mTtsManager.onGetNavigationText(item.getVoice()));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (AppClickUtil.isDuplicateClick()) return false;
        // 司机休假设置
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).startForResult(StatusSwitchFragment.newInstance(), FLAG_UPDATE_DRIVER_STATUS);
        }
        return false;
    }

    private void updateDriverStatus(boolean driverWorkStatus) {
        // 该功能暂时隐藏
//        if (mToolbarHomeBinding == null) return;
//
//        mToolbarHomeBinding.toolbar.getMenu().clear();
//        // 司机休息状态
//        mToolbarHomeBinding.toolbar.inflateMenu(driverWorkStatus ? R.menu.driver_work : R.menu.driver_leave);
    }

    @Subscribe
    public void home(final HomeEvent event) {
        if (HomeFragment.this.getActivity() == null) return;
        ULog.i("home event=" + event.getType());
        switch (event.getType()) {
            case FLAG_REFRESH_PROGRESS:
                // 刷新进度
                mPresenter.getCurrentProgress();
                break;
            case FLAG_REFRESH_PAGE:
                // 通知 业务订单消息刷新
                mPresenter.getMessageList(1);

                // 刷新未读数
                MyFragment myFragment = HomeFragment.this.findFragment(MyFragment.class);
                if (myFragment != null) {
                    myFragment.refreshUnReadCount();
                }
                break;
            case LOOK_PLATFORM_MESSAGE:
                // 查看平台消息
                if (event.getObject() == null) return;
                if (HomeFragment.this.getParentFragment() != null) {
                    ((MainFragment) HomeFragment.this.getParentFragment()).start(HtmlSwipeFragment.newInstance("平台公告消息", (String) event.getObject()));
                }
                break;
            default:
                break;
        }
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
        if (mHeader == null)
            return;

        if (value != null) {
            // 如果有接单数据

            // 根据当前订单id加载所有节点
            ((MainActivity) _mActivity).initOrder(value.getOrderSn());

            // 显示当前单的ui
            mHeader.llOrderProcess.setVisibility(View.VISIBLE);
            mHeader.map.setVisibility(View.GONE);
            mHeader.map.onPause(); // 暂时不用地图
            mHeader.llMap.setVisibility(View.GONE);
            mHeader.tvOrderId.setText(value.getOrderSn());
            mHeader.tvNodeName.setText(value.getNodeName());
            mHeader.tvOrderType.setText(value.getOrderType());
            mHeader.tvDeliveryRoute.setText(value.getSpannableStringBuilder());
            mHeader.llOrderProcess.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;
                // 打开节点的
                if (getParentFragment() != null)
                    ((MainFragment) getParentFragment()).startForResult(NodeFragment.newInstance(value.getOrderSn()), -1);
            });
//            App.launchLocationHistory(value);
            mHeader.tvBannerInfo.setVisibility(View.VISIBLE);
            mHeader.tvBannerInfo.setText(getString(R.string.current_mission_process));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                mHeader.tvBannerInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(UiUtils.getDrawable(R.drawable.ic_jindu), null, null, null);

            // 语音事件
            mHeader.ivOverTime.setOnClickListener(v -> ((MainActivity) _mActivity).mTtsManager.onGetNavigationText(value.getVoice()));

        } else {
            // 没有接单数据
            mHeader.llOrderProcess.setOnClickListener(null);
            mHeader.map.onResume(); // 恢复使用地图
            mHeader.llOrderProcess.setVisibility(View.GONE);
            mHeader.map.setVisibility(View.VISIBLE);
            mHeader.llMap.setVisibility(View.VISIBLE);
            // 设置当前地址
            LocationManager locationManager = new LocationManager();
            locationManager.init(BaseApplication.getInstance().getBaseContext());
            locationManager.setComeback(location -> {
                locationManager.destroyLocation();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mHeader.tvBannerInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(UiUtils.getDrawable(R.drawable.ic_wz_05), null, null, null);
                }
                mHeader.tvBannerInfo.setText(String.format("当前位置-%s", location.getStreet()));

            });
//            App.locationHistoryManager.destroyLocation();
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
        mDataBinding.refreshLayout.finishRefresh();
        mDataBinding.refreshLayout.finishLoadMore();
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
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Subscribe(sticky = true)
    public void onNotificationEvent(NotificationEvent event) {
        if (getParentFragment() == null || getParentFragment().getParentFragment() == null) return;
        final String mToPage = event.getToPage();
        final String mToPageId = event.getToPageId();
        if (mToPage == null) return;

        switch (mToPage) {
            case FLAG_ORDER:
                // 订单中心
                ((MainFragment) HomeFragment.this.getParentFragment().getParentFragment()).changeTab(1);
                break;
            case FLAG_ORDER_DETAIL:
                // 订单详情
                if (mToPageId != null) {
                    ((MainFragment) HomeFragment.this.getParentFragment()).start(OrderDetailFragment.newInstance(mToPageId, true, true));
                }
                break;
            case FLAG_PERSONL_MESSAGE:
                //企业消息
                break;
            case FLAG_PLATFORM_MESSAGE:
                // 平台消息
                ((MainFragment) HomeFragment.this.getParentFragment()).start(PlatformMessageFragment.newInstance());
                break;
        }
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
                        updateDriverStatus(false);
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

    @Override
    public void showMessageList(PageList<DriverOrderMsgWrapper> result, int pageIndex) {
        mPageIndex = pageIndex;
        if (pageIndex == 1) {
            // 判断第一页是否有数据，如果没数据，就显示 亲,木有消息了~
            if (result.getResult().size() == 0) {
                // 显示消息
                mHeader.tvMessage.setVisibility(View.VISIBLE);
                mDataBinding.refreshLayout.setEnableLoadMore(false);

                // 隐藏recyclerview
                mDataBinding.rlMessage.setVisibility(View.VISIBLE);

                // 清空数据
                mHomeOrderChildAdapterWait.clear();
                mHomeOrderChildAdapterWait.appendToList(result.getResult());
            } else {
                // 隐藏没有消息的
                mHeader.tvMessage.setVisibility(View.GONE);
                mDataBinding.refreshLayout.setEnableLoadMore(true);

                // 显示recyclerview
                mDataBinding.rlMessage.setVisibility(View.VISIBLE);

                // 设置数据源
                mHomeOrderChildAdapterWait.clear();
                mHomeOrderChildAdapterWait.appendToList(result.getResult());

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                mDataBinding.rlMessage.setLayoutManager(layoutManager);
            }
        } else if (pageIndex > 1) {
            // 加载一页
            int size = mHomeOrderChildAdapterWait.getItemCount();
            mHomeOrderChildAdapterWait.appendToList(result.getResult());
            mHomeOrderChildAdapterWait.notifyItemRangeInserted(size, result.getResult().size());
            mDataBinding.refreshLayout.finishLoadMore();
        }

        // 通知刷新我的页面的未读消息

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshTitle(DriverContactCompany driverContactCompany) {
        if (driverContactCompany != null && driverContactCompany.getUserOffice() != null && !TextUtils.isEmpty(driverContactCompany.getUserOffice().getShortName())) {
            mDataBinding.toolbarHomeCommon.toolbarTitle.setText("货云集司机-" + driverContactCompany.getUserOffice().getShortName());
        } else {
            mDataBinding.toolbarHomeCommon.toolbarTitle.setText("货云集司机");
        }
    }

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            App.getInstance().finishAllActivity();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(BaseApplication.getInstance(), R.string.keydownquitapp, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initMapFinish() {

    }

    @Override
    public void onCameraChangeFinish(LatLng latLng) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result) {

    }

    static class ViewHolder {

        View view;

        @BindView(R.id.convenientBanner)
        ConvenientBanner convenientBanner;
        @BindView(R.id.tvBannerInfo)
        TextView tvBannerInfo;
        @BindView(R.id.map)
        TextureMapView map;
        @BindView(R.id.llMap)
        LinearLayout llMap;
        @BindView(R.id.tvOrderId)
        TextView tvOrderId;
        @BindView(R.id.tvOrderType)
        TextView tvOrderType;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.tvNodeName)
        TextView tvNodeName;
        @BindView(R.id.ivOverTime)
        ImageView ivOverTime;
        @BindView(R.id.tvDeliveryRoute)
        TextView tvDeliveryRoute;
        @BindView(R.id.llOrderProcess)
        LinearLayout llOrderProcess;
        @BindView(R.id.llProcess)
        LinearLayout llProcess;
        @BindView(R.id.tvNewsList)
        TextView tvNewsList;
        @BindView(R.id.tvLookAllOrderMessage)
        TextView tvLookAllOrderMessage;
        @BindView(R.id.tvMessage)
        TextView tvMessage;

        ViewHolder(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }

    }


}
