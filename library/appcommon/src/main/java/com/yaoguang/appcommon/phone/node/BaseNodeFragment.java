package com.yaoguang.appcommon.phone.node;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.databinding.ViewDataBinding;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.appcommon.phone.node.adapter.NodeAdapter;
import com.yaoguang.appcommon.phone.node.event.NodeFragmentRefreshEvent;
import com.yaoguang.appcommon.phone.node.noderichtext.OrderNodeRichTextFragement;
import com.yaoguang.greendao.entity.driver.DriverNodeAddrVo;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeList;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.view.bar.ImmersionBar;
import com.yaoguang.map.common.AMapUtil;
import com.yaoguang.map.location.impl.LocationManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import ezy.ui.layout.LoadingLayout;

/**
 * 当前任务进度
 * Created by zhongjh on 2018/6/15.
 */
public abstract class BaseNodeFragment<B extends ViewDataBinding> extends BaseFragmentListConditionDataBind<DriverOrderMergeNodeWrapper, String[], NodeAdapter, B> implements NodeContract.View, Toolbar.OnMenuItemClickListener {

    protected FrameLayout mFrameLayout;
    protected boolean mIsEdit = true;

    protected String mOrderId;  //  司机端直接传订单id
    protected String mType;     //  货主端传递类型 4：装货 5：送货
    protected String mPcSonoId; //  平台货柜id
    protected DriverOrderMergeNodeWrapper mDriverOrderMergeNodeWrapper;
    protected String mClientId;
    protected boolean mIsTTs;
    // 当前状态
    protected int mOrderStatus;

    protected ViewHolder mViewHolder;

    protected AMapUtil aMapUtil = new AMapUtil(); // 地图工具类

    // region 重力感应

    protected boolean isLandscape = false;      // 默认是竖屏

    protected SensorManager sm;
    protected OrientationSensorListener listener;
    protected Sensor sensor;

    protected ContentObserver mSettingsContentObserver;    // 监控方向锁定

    // endregion


    protected NodeContract.Presenter mPresenter = new NodePresenter(this);

    protected DialogHelper dialogHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFrameLayout = new FrameLayout(_mActivity);
        View view = inflater.inflate(getLayoutId(), container, false);
        mViewHolder = new ViewHolder(view);
        mLayoutInflater = inflater;
        initView(view, getAdapter());
        initRecyclerviewListener();
        init();
        initView();
        initListener();
        mFrameLayout.addView(view);
        return mFrameLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mFrameLayout.removeAllViews();
        View view = null;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
            view = mLayoutInflater.inflate(R.layout.fragment_order_node2_land, null);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
            view = mLayoutInflater.inflate(R.layout.fragment_order_node2_port, null);
        }
        mFrameLayout.addView(view);
        mViewHolder = new ViewHolder(view);
        ImmersionBar.setTitleBar(getActivity(), mViewHolder.toolbar);
        // 适配器保留原样
        initView(view, mBaseLoadMoreRecyclerAdapter);
        initRecyclerviewListener();
        initView();
        initListener();

        // 动画结束则刷新
//        mViewHolder.trlView.refreshDataAnimation();
        // 不需要每次转屏刷新数据，
        // 直接赋值
        mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
        refreshingFootView(mOrderStatus, mClientId);
        // 因为转屏原因创建新的view，重新设置底部不能加载下一页
        mViewHolder.refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_node2_port;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new NodeAdapter(getContext());
    }

    @Override
    public String[] getCondition(boolean isRegain) {
        if (!TextUtils.isEmpty(mOrderId)) {
            return new String[]{mOrderId};
        } else {
            return new String[]{mType, mPcSonoId};
        }
    }

    @Override
    public void setConditionView(String[] condition) {

    }


    @Override
    public void init() {
        EventBus.getDefault().register(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mOrderId = bundle.getString("orderId");
            mType = bundle.getString("type");
            mPcSonoId = bundle.getString("pcSonoId");
        }

        // 初始化重力感应器
        sm = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        listener = new OrientationSensorListener(mHandler, _mActivity);

    }

    protected void initView() {
        mPresenter.subscribe();

        // 不显示加载完毕的脚步
        mBaseLoadMoreRecyclerAdapter.setHasFooterPriority(false);
        mViewHolder.refreshLayout.finishLoadMore();

        getCurrentAddress();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 启动重力感应
        if (Settings.System.getInt(_mActivity.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            // 1为自动旋转模式，0为锁定竖屏模式
            sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        }

    }

    @Override
    public void onRequestedOrientation() {

    }

    @Override
    public void onResume() {
        super.onResume();
        // 重新开启重力感应
        if (Settings.System.getInt(_mActivity.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        // 重新开启重力感应
        if (Settings.System.getInt(_mActivity.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        // 关闭重力感应
        sm.unregisterListener(listener);
        // 关闭监控手机方向锁定
        _mActivity.getContentResolver().unregisterContentObserver(mSettingsContentObserver);
    }

    @Override
    public void initListener() {
        mViewHolder.imgRefreshAddress.setOnClickListener(v -> getCurrentAddress());

        // 下一步
        mViewHolder.btnNext.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mViewHolder.btnNext.getText().equals("完成")) {
                    if (dialogHelper == null)
                        // 循环列表是否有不符合规定的动态记录，如果有，就提示
                        dialogHelper = new DialogHelper(getContext(), "提示", "尚有柜号、封号未录入，是否提交完成", "确定", "取消", false, new CommonDialog.Listener() {
                            @Override
                            public void ok(String content) {
                                finishBatch();
                            }

                            @Override
                            public void cancel() {

                            }
                        });
                    dialogHelper.show();
                } else {
                    finishBatch();
                }
            }
        });

        // 进度更新的一系列事件
        mBaseLoadMoreRecyclerAdapter.setmCallBack(new NodeAdapter.CallBack() {

            @Override
            public void startOrderNodeRichTextFragement(DriverOrderNodeWrapper driverOrderNodeWrapper) {
                startForResult(OrderNodeRichTextFragement.newInstance(driverOrderNodeWrapper.getId(), mIsEdit), OrderNodeRichTextFragement.REQUEST);
                // 关闭重力感应
                sm.unregisterListener(listener);
            }

            @Override
            public void startCallPhone(String mobile) {
                PhoneUtils.nodeCallPhone(_mActivity, getFragmentManager(), new String[]{mobile});
            }

            @Override
            public void startMap(DriverNodeAddrVo driverNodeAddrVo) {
                if (driverNodeAddrVo.getSite() != null && driverNodeAddrVo.getSite().getLat() != null && driverNodeAddrVo.getSite().getLat() != -1) {
                    LatLng latLng = new LatLng(driverNodeAddrVo.getSite().getLat(), driverNodeAddrVo.getSite().getLon());
                    AMapUtil.startAMapNavi(getContext(), latLng);
                } else if (!TextUtils.isEmpty(driverNodeAddrVo.getAddress())) {
                    // 如果有address，就显示导航
                    aMapUtil.startAMapNavi(_mActivity, getContext(), driverNodeAddrVo.getAddress());
                }
            }

        });


        // 设置事件
        mSettingsContentObserver = new ContentObserver(new Handler()) {
            @Override
            public boolean deliverSelfNotifications() {
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                // 启动重力感应
                if (Settings.System.getInt(_mActivity.getContentResolver(),
                        Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
                    // 1为自动旋转模式，0为锁定竖屏模式
                    sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                } else {
                    sm.unregisterListener(listener);
                }
            }

        };
        _mActivity.getContentResolver().registerContentObserver(
                Settings.System.CONTENT_URI, true,
                mSettingsContentObserver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void NodeFragmentRefreshEvent(NodeFragmentRefreshEvent nodeFragmentRefreshEvent) {
        mIsTTs = nodeFragmentRefreshEvent.isTTs();
        this.refreshDataAnimation();
    }


    @Override
    public void getCurrentAddress() {
        // 获取当前地址
        LocationManager locationManager = new LocationManager();
        locationManager.init(BaseApplication.getInstance().getBaseContext());
        locationManager.setComeback(location -> {
            locationManager.destroyLocation();
            mViewHolder.tvNextAddress.setText(location.getAddress());
        });
    }

    @Override
    public void finishBatch() {
        if (mDriverOrderMergeNodeWrapper == null)
            return;
        ArrayList<DriverOrderNodeList> driverOrderNodeLists = new ArrayList<>();

        // 判断是到达还是离开,true 正在前往 false 正在离开
        boolean isGetInto = false;
        switch (mDriverOrderMergeNodeWrapper.getFinishStatus()) {
            case "1":
                isGetInto = true;
                break;
            case "2":
                isGetInto = false;
                break;
        }
        for (DriverOrderNodeWrapper driverOrderNodeWrapper : mDriverOrderMergeNodeWrapper.getNodes()) {
            for (DriverOrderNodeList driverOrderNodeList : driverOrderNodeWrapper.getDriverOrderNodeList()) {
                if (isGetInto) {
                    // 正在前往就取number=0
                    if (driverOrderNodeList.getNumber() == 0)
                        driverOrderNodeLists.add(driverOrderNodeList);
                } else {
                    // 否则离开就取number=2
                    if (driverOrderNodeList.getNumber() == 2)
                        driverOrderNodeLists.add(driverOrderNodeList);
                }

            }
        }
        mPresenter.finish(driverOrderNodeLists);
    }

    // region 重力感应

    /**
     * 接收重力感应监听的结果，来改变屏幕朝向
     */
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {

            if (msg.what == 888) {
                int orientation = msg.arg1;

                /**
                 * 根据手机屏幕的朝向角度，来设置内容的横竖屏，并且记录状态
                 */
                if (orientation > 45 && orientation < 135) {
                    _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    isLandscape = true;
                } else if (orientation > 135 && orientation < 225) {
                    _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    isLandscape = false;
                } else if (orientation > 225 && orientation < 315) {
                    _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    isLandscape = true;
                } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {
                    _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    isLandscape = false;
                }
            }
        }
    };

    /**
     * 重力感应监听者
     */
    public class OrientationSensorListener implements SensorEventListener {

        private boolean isClickFullScreen;        // 记录全屏按钮的状态，默认false
        private boolean isEffetSysSetting = false;   // 手机系统的重力感应设置是否生效，默认无效，想要生效改成true就好了
        private boolean isOpenSensor = true;      // 是否打开传输，默认打开
        private boolean isLandscape = false;      // 默认是竖屏
        private boolean isChangeOrientation = true;  // 记录点击全屏后屏幕朝向是否改变，默认会自动切换

        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        public static final int ORIENTATION_UNKNOWN = -1;

        private Handler rotateHandler;
        private Activity activity;

        public OrientationSensorListener(Handler handler, Activity activity) {
            rotateHandler = handler;
            this.activity = activity;
        }

        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y
            // value
            if (magnitude * 4 >= Z * Z) {
                // 屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }


            /**
             * 获取手机系统的重力感应开关设置，这段代码看需求，不要就删除
             * screenchange = 1 表示开启，screenchange = 0 表示禁用
             * 要是禁用了就直接返回
             */
            if (isEffetSysSetting) {
                try {
                    int isRotate = Settings.System.getInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);

                    // 如果用户禁用掉了重力感应就直接return
                    if (isRotate == 0) return;
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            }


            // 只有点了按钮时才需要根据当前的状态来更新状态
            if (isClickFullScreen) {
                if (isLandscape && screenIsPortrait(orientation)) {           // 之前是横屏，并且当前是竖屏的状态
                    updateState(false, false, true, true);
                } else if (!isLandscape && screenIsLandscape(orientation)) {  // 之前是竖屏，并且当前是横屏的状态
                    updateState(true, false, true, true);
                } else if (isLandscape && screenIsLandscape(orientation)) {    // 之前是横屏，现在还是横屏的状态
                    isChangeOrientation = false;
                } else if (!isLandscape && screenIsPortrait(orientation)) {  // 之前是竖屏，现在还是竖屏的状态
                    isChangeOrientation = false;
                }
            }

            // 判断是否要进行中断信息传递
            if (!isOpenSensor) {
                return;
            }

            if (rotateHandler != null) {
                rotateHandler.obtainMessage(888, orientation, 0).sendToTarget();
            }
        }

        /**
         * 当前屏幕朝向是否竖屏
         *
         * @param orientation
         * @return
         */
        private boolean screenIsPortrait(int orientation) {
            return (((orientation > 315 && orientation <= 360) || (orientation >= 0 && orientation <= 45))
                    || (orientation > 135 && orientation <= 225));
        }

        /**
         * 当前屏幕朝向是否横屏
         *
         * @param orientation
         * @return
         */
        private boolean screenIsLandscape(int orientation) {
            return ((orientation > 45 && orientation <= 135) || (orientation > 225 && orientation <= 315));
        }


        /**
         * 更新状态
         *
         * @param isLandscape         横屏
         * @param isClickFullScreen   全屏点击
         * @param isOpenSensor        打开传输
         * @param isChangeOrientation 朝向改变
         */
        private void updateState(boolean isLandscape, boolean isClickFullScreen, boolean isOpenSensor, boolean isChangeOrientation) {
            this.isLandscape = isLandscape;
            this.isClickFullScreen = isClickFullScreen;
            this.isOpenSensor = isOpenSensor;
            this.isChangeOrientation = isChangeOrientation;
        }

    }

    // endregion


    public static class ViewHolder {
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public AppBarLayout app_bar;
        public RecyclerView rlView;
        public LoadingLayout loading;
        public SmartRefreshLayout refreshLayout;
        public FrameLayout flRecyclerView;
        public TextView tvTitle;
        public TextView tvProblemFeedback;
        public Button btnAdjustRoute;
        public Button btnNext;
        public TextView tvNextAddress;
        public ImageView imgRefreshAddress;
        public Guideline guideline50;
        public ConstraintLayout activity_description_content;
        public FrameLayout flMain;
        public CardView activity_cardview;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.imgReturn = (ImageView) rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.app_bar = (AppBarLayout) rootView.findViewById(R.id.appBar);
            this.rlView = (RecyclerView) rootView.findViewById(R.id.rlView);
            this.loading = (LoadingLayout) rootView.findViewById(R.id.loading);
            this.refreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refreshLayout);
            this.flRecyclerView = (FrameLayout) rootView.findViewById(R.id.flRecyclerView);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvProblemFeedback = (TextView) rootView.findViewById(R.id.tvProblemFeedback);
            this.btnAdjustRoute = (Button) rootView.findViewById(R.id.btnAdjustRoute);
            this.btnNext = (Button) rootView.findViewById(R.id.btnNext);
            this.tvNextAddress = (TextView) rootView.findViewById(R.id.tvNextAddress);
            this.imgRefreshAddress = (ImageView) rootView.findViewById(R.id.imgRefreshAddress);
            this.guideline50 = (Guideline) rootView.findViewById(R.id.guideline50);
            this.activity_description_content = (ConstraintLayout) rootView.findViewById(R.id.activity_description_content);
            this.flMain = (FrameLayout) rootView.findViewById(R.id.flMain);
            this.activity_cardview = rootView.findViewById(R.id.activity_cardview);
        }

    }


}
