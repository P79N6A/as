package com.yaoguang.driver.phone.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.model.LatLng;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceLocation;
import com.umeng.analytics.MobclickAgent;
import com.yaoguang.appcommon.phone.contact2.contactnew.event.ContactNewRefreshEvent;
import com.yaoguang.appcommon.phone.my.my.event.MyEvent;
import com.yaoguang.datasource.api.RongCloudApi;
import com.yaoguang.datasource.api.driver.OrderNodeApi;
import com.yaoguang.datasource.api.driver.PositionApi;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.OrderNodeDataSource;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.ActivityMainBinding;
import com.yaoguang.driver.phone.activitys.FestivalActivity;
import com.yaoguang.appcommon.phone.node.BaseNodeFragment;
import com.yaoguang.appcommon.phone.node.event.NodeFragmentRefreshEvent;
import com.yaoguang.driver.phone.order.node.NodeFragment;
import com.yaoguang.driver.phone.test.record.Util;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.LocationDao;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeStatusWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeList;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeWrapper;
import com.yaoguang.greendao.entity.driver.Site;
import com.yaoguang.lib.base.BaseActivityDataBind;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.view.bar.ImmersionBar;
import com.yaoguang.lib.common.view.bar.OSUtils;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.map.common.tts.TTSController;
import com.yaoguang.map.location.impl.LocationManager;
import com.yaoguang.map.service.LocationService;
import com.yaoguang.map.service.LocationStatusManager;
import com.yaoguang.map.service.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static android.provider.ContactsContract.Intents.Insert.ACTION;
import static com.amap.api.fence.GeoFence.STATUS_IN;
import static com.amap.api.fence.GeoFence.STATUS_OUT;
import static com.amap.api.fence.GeoFence.STATUS_STAYED;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_IN;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_OUT;
import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.REFRESH_UNREAD_CONTACT_COUNT;
import static com.yaoguang.map.service.Constant.RECEIVER_ACTION;


/**
 * 仿微信交互方式Demo
 * Created by YoKeyword on 16/6/30.
 */
public class MainActivity extends BaseActivityDataBind<ActivityMainBinding> {

    private ImmersionBar mImmersionBar;
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";
    int getTokenI = -1;
    public String mOrderNo = "";// 正在运行的订单号

    OrderNodeDataSource mOrderNodeDataSource = new OrderNodeDataSource();
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public MainActivity() {
    }

    //region 有关顶部的 顶部的Sheet点击事件

    // 语音
    public TTSController mTtsManager;

    public interface OnTopSheets {
        void OnTopSheetsOKKey();
    }

    //    MaryPopup popup;
    protected View mVTop;
    protected TextView mTVMessage;
    protected TextView mTVOK;

    private boolean mIsGetInto;

    private ArrayList<String> mId = new ArrayList<>();
    private String mDriverOrderId;

    //endregion

    //region 有关围栏的

    // 围栏点
    ArrayList<DPoint> mPoints = new ArrayList<>();

    public static final String GEOFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast"; // 定义接收广播的action字符串
    private GeoFenceClient mGeoFenceClient; // 围栏


    private boolean isGeoFenceReceiver = false;
    /**
     * 围栏广播
     */
    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null)
                if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
//                    Toast.makeText(getApplicationContext(), "范围选项" + intent.getExtras().getInt(GeoFence.BUNDLE_KEY_FENCESTATUS), Toast.LENGTH_LONG).show();

                    // 判断该围栏只有进入、离开、停留才触发，如果是未知和失败，都不触发
                    if (intent.getExtras() != null && (intent.getExtras().getInt(GeoFence.BUNDLE_KEY_FENCESTATUS) == STATUS_IN
                            || intent.getExtras().getInt(GeoFence.BUNDLE_KEY_FENCESTATUS) == STATUS_OUT
                            || intent.getExtras().getInt(GeoFence.BUNDLE_KEY_FENCESTATUS) == STATUS_STAYED)) {
                        // 提交节点,获取当前地址
                        LocationManager locationManager = new LocationManager();
                        locationManager.init(BaseApplication.getInstance().getBaseContext());
                        locationManager.setComeback(location -> {
                            locationManager.destroyLocation();
                            ArrayList<DriverOrderNodeList> driverOrderNodeLists = new ArrayList<>();
                            for (String id : mId) {
                                DriverOrderNodeList driverOrderNodeList = new DriverOrderNodeList();
                                driverOrderNodeList.setId(id);
                                driverOrderNodeList.setLat(location.getLat());
                                driverOrderNodeList.setLon(location.getLon());
                                driverOrderNodeList.setAddress(location.getAddress());
                                driverOrderNodeList.setDriverOrderId(mDriverOrderId);
                                driverOrderNodeLists.add(driverOrderNodeList);
                            }
                            mOrderNodeDataSource.finishBatch(driverOrderNodeLists)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseObserver<BaseResponse<ArrayList<DriverOrderMergeNodeWrapper>>>(mCompositeDisposable) {
                                        @Override
                                        public void onSuccess(BaseResponse<ArrayList<DriverOrderMergeNodeWrapper>> response) {
                                            // 删除所有围栏
                                            mGeoFenceClient.removeGeoFence();
                                            // 取消广播
                                            if (isGeoFenceReceiver) {
                                                unregisterReceiver(mGeoFenceReceiver);
                                                isGeoFenceReceiver = false;
                                            }
                                            // 刷新 nodeFragment界面
                                            EventBus.getDefault().post(new NodeFragmentRefreshEvent(false));
                                        }
                                    });
                        });

                        // 触发围栏
                        String strGetInfo;
                        String strGetInfo2;
                        String message;
                        if (mIsGetInto) {
                            strGetInfo = "走进";
                            strGetInfo2 = "到达";
                            message = "您已" + strGetInfo + "指定范围之内,系统已帮您完成" + strGetInfo2 + "这里";
                        } else {
                            strGetInfo = "走出";
                            strGetInfo2 = "离开";
                            message = "您已" + strGetInfo + "指定范围之外,系统已帮您完成" + strGetInfo2 + "这里";
                        }

                        mTtsManager.onGetNavigationText(message);
                        showTopSheets(message, "好的", () -> mVTop.setVisibility(View.GONE));

                        //解析广播内容
//                    //获取Bundle
//                    Bundle bundle = intent.getExtras().getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
//                    //获取围栏行为：
//                    int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
//                    //获取自定义的围栏标识：
//                    String customId = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
//                    //获取围栏ID:
//                    String fenceId = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
//                    //获取当前有触发的围栏对象：
//                    GeoFence fence = bundle.getParcelable(GeoFence.BUNDLE_KEY_FENCE);

                    }
                }


        }
    };

    /**
     * 创建围栏后的事件
     */
    GeoFenceListener mGeoFenceListener = (list, errorCode, s) -> {
        if (errorCode == GeoFence.ADDGEOFENCE_SUCCESS) {//判断围栏是否创建成功
            Log.d("test", "添加围栏成功!!");
        } else {
            Log.d("test", "添加围栏失败!!");
        }

        // 初始化围栏接收
        IntentFilter fliter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        fliter.addAction(GEOFENCE_BROADCAST_ACTION);
        isGeoFenceReceiver = true;
        registerReceiver(mGeoFenceReceiver, fliter);
    };

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实例化语音引擎
        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();

        // 注册广播接收者
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION);
        registerReceiver(locationChangeBroadcastReceiver, intentFilter);

        // 启动服务
        startService(true);

    }


    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    NAVIGATIONBAR_IS_MIN, 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                mImmersionBar.transparentNavigationBar().init();
            } else {
                //导航键显示了
                mImmersionBar.navigationBarColor(android.R.color.black) //隐藏前导航栏的颜色
                        .fullScreen(false)
                        .init();
            }
        }
    };

    public static void newInstance(Activity activity, String url) {
        if (TextUtils.isEmpty(url)) {
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        } else {
            FestivalActivity.newInstance(activity, url, FestivalActivity.TYPE_MAIN);
        }
    }

    @Override
    protected void initView() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
        //解决华为emui3.0与3.1手机手动隐藏底部导航栏时，导航栏背景色未被隐藏的问题
        if (OSUtils.isEMUI3_1()) {
            //第一种
            getContentResolver().registerContentObserver(Settings.System.getUriFor
                    (NAVIGATIONBAR_IS_MIN), true, mNavigationStatusObserver);
            //第二种,禁止对导航栏的设置
            //mImmersionBar.navigationBarEnable(false).init();
        }
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }


    }


    public static class ActionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (!ACTION.equals(action)) {
                return;
            }
            int op = intent.getIntExtra("op", 0);
            Log.e("ezy", "result ==> " + op);
            Toast.makeText(BaseApplication.getInstance(), "result ==> " + op, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }


    // BaseActivity中统一调用MobclickAgent 类的 onResume/onPause 接口
    // 子类中无需再调用
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 基础指标统计，不能遗漏
//        loginRongCloud();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 基础指标统计，不能遗漏
        mTtsManager.stopSpeaking();
        RongIM.getInstance().disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 关闭地图广播接收者
        if (locationChangeBroadcastReceiver != null)
            unregisterReceiver(locationChangeBroadcastReceiver);

        mTtsManager.destroy();

        // 关闭沉侵状态栏
        if (mImmersionBar != null)
            mImmersionBar.destroy();

        mCompositeDisposable.clear();
    }


    //region 有关融云的

//    // 登录融云
//    private void loginRongCloud() {
//        if (TextUtils.isEmpty(DataStatic.getInstance().getRongCloudToken())) {
//            // 如果为空的，就重新获取
//            getRongCloudToken();
//            return;
//        }
//        RongIM.connect(DataStatic.getInstance().getRongCloudToken(), new RongIMClient.ConnectCallback() {
//            @Override
//            public void onTokenIncorrect() {
//                getTokenI++;
//                getRongCloudToken();
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                RongIMClient.setOnReceiveMessageListener((message, i) -> {
//                    // 刷新有关消息
//                    EventBus.getDefault().postSticky(new ContactNewRefreshEvent());
//                    EventBus.getDefault().postSticky(new MyEvent(ObjectUtils.parseString(REFRESH_UNREAD_CONTACT_COUNT)));
//                    return false;
//                });
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                if (RongIMClient.ErrorCode.RC_CONN_USER_OR_PASSWD_ERROR == errorCode) {
//                    getTokenI++;
//                    getRongCloudToken();
//                }
//
//            }
//        });
//    }
//
//    // 获取融云token
//    private void getRongCloudToken() {
//        // 获取token
//        if (getTokenI == 0) {
//            RongCloudApi rongCloudApi = Api.getInstance().retrofit.create(RongCloudApi.class);
//            rongCloudApi.getRongyunToken("0", DataStatic.getInstance().getDriver().getId())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<BaseResponse<String>>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            mCompositeDisposable.add(d);
//                        }
//
//                        @Override
//                        public void onNext(BaseResponse<String> String) {
//                            // 保存token
//                            DataStatic.getInstance().setRongCloudToken(String.getResult());
//                            if (String.getState().equals("200")) {
//                                // 重新登录融云
//                                loginRongCloud();
//                            } else {
//                                getRongCloudToken();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            getRongCloudToken();
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        } else {
//            RongCloudApi rongCloudApi = Api.getInstance().retrofit.create(RongCloudApi.class);
//            rongCloudApi.refreshRongyunToken("0", DataStatic.getInstance().getDriver().getId())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<BaseResponse<String>>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            mCompositeDisposable.add(d);
//                        }
//
//                        @Override
//                        public void onNext(BaseResponse<String> String) {
//                            // 保存token
//                            DataStatic.getInstance().setRongCloudToken(String.getResult());
//                            if (String.getState().equals("200") && !String.getResult().equals("")) {
//                                //重新登录融云
//                                loginRongCloud();
//                            } else {
//                                getRongCloudToken();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            getRongCloudToken();
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        }
//
//
//    }

    //endregion 有关融云的

    //region 有关围栏的、顶部sheet、围栏的订单节点

    /**
     * 根据当前订单获取所有节点
     *
     * @param orderNo 订单id
     */
    public void initOrder(String orderNo) {
        mOrderNo = orderNo;
        OrderNodeApi orderNodeApi = Api.getInstance().retrofit.create(OrderNodeApi.class);
        orderNodeApi.mergeList(orderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<DriverOrderMergeNodeStatusWrapper>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<DriverOrderMergeNodeStatusWrapper> response) {
                        if (response.getResult().getOrderStatus() == 1)
                            if (response.getResult() != null && response.getResult().getMergeNodes().size() > 0) {
                                // 判断 正在前往（1） 已到达，未离开（2） 获取当前正在执行的节点
                                for (DriverOrderMergeNodeWrapper driverOrderMergeNodeWrapper : response.getResult().getMergeNodes()) {
                                    if (driverOrderMergeNodeWrapper.getFinishStatus().equals("1") || driverOrderMergeNodeWrapper.getFinishStatus().equals("2")) {
                                        // 判断是到达还是离开
                                        boolean isGetInto = false;
                                        String strGetInfo = "";
                                        switch (driverOrderMergeNodeWrapper.getFinishStatus()) {
                                            case "1":
                                                isGetInto = true;
                                                strGetInfo = "前往";
                                                break;
                                            case "2":
                                                isGetInto = false;
                                                strGetInfo = "离开";
                                                break;
                                        }

                                        // 开启实时定位 // 如果是一个的，选择是否在围栏以内，如果在，就是您已进入XXX，否则是正在前往下一个地点
                                        if (driverOrderMergeNodeWrapper.getNodes() != null && driverOrderMergeNodeWrapper.getNodes().size() > 0)
                                            addGeoFence(driverOrderMergeNodeWrapper.getNodes(), isGetInto, orderNo);

                                        if (!driverOrderMergeNodeWrapper.getName().equals("")) {
                                            // 语音
                                            mTtsManager.onGetNavigationText("正在" + strGetInfo + driverOrderMergeNodeWrapper.getName());
                                            // 弹框
                                            showTopSheets("正在" + strGetInfo + driverOrderMergeNodeWrapper.getName(), "好的", () -> {
                                                // 跳转到
                                                MainFragment mainFragment = findFragment(MainFragment.class);
                                                if (mainFragment != null)
                                                    mainFragment.startForResult(NodeFragment.newInstance(orderNo), -1);
                                                mVTop.setVisibility(View.GONE);
                                            });
                                        }
                                    }
                                }
                            }
                    }
                });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 增加地理围栏
     *
     * @param nodes         订单数据
     * @param isGetInto     是否进入
     * @param driverOrderId 订单的id
     */
    public void addGeoFence(List<DriverOrderNodeWrapper> nodes, boolean isGetInto, String driverOrderId) {
        mIsGetInto = isGetInto;
        if (mGeoFenceClient == null) {
            mGeoFenceClient = new GeoFenceClient(getApplicationContext());
        }

        // 增加围栏之前，先删除之前的所有围栏
        mGeoFenceClient.removeGeoFence();
        // 创建一个中心点坐标
        mPoints.clear();
        // 删除所有节点id
        mId.clear();
        // 取消广播
        if (isGeoFenceReceiver) {
            unregisterReceiver(mGeoFenceReceiver);
            isGeoFenceReceiver = false;
        }

        mDriverOrderId = driverOrderId;

        // 循环添加多个围栏和id等等
        for (DriverOrderNodeWrapper driverOrderNodeWrapper : nodes) {
            // 如果没有经纬度，就直接返回，不进行围栏处理
            if (driverOrderNodeWrapper.getTruckGoodsAddr() == null || driverOrderNodeWrapper.getTruckGoodsAddr().getArea() == null || driverOrderNodeWrapper.getTruckGoodsAddr().getArea().size() <= 0) {
                continue;
            }

            // 判断，如果只有一个经纬度值，则以圆点方式做围栏，如果是多个，就是多边形
            if (driverOrderNodeWrapper.getTruckGoodsAddr().getArea().size() > 1) {
                for (Site site : driverOrderNodeWrapper.getTruckGoodsAddr().getArea()) {
                    DPoint dPoint = new DPoint();
                    dPoint.setLatitude(site.getLat());
                    dPoint.setLongitude(site.getLon());
                    mPoints.add(dPoint);
                    //执行添加围栏的操作
                    mGeoFenceClient.addGeoFence(mPoints, "自有ID");
                }
            } else if (driverOrderNodeWrapper.getTruckGoodsAddr().getArea().size() == 1) {
                // 创建一个中心点坐标
                DPoint centerPoint = new DPoint();
                //设置中心点纬度
                centerPoint.setLatitude(driverOrderNodeWrapper.getTruckGoodsAddr().getArea().get(0).getLat());
                //设置中心点经度
                centerPoint.setLongitude(driverOrderNodeWrapper.getTruckGoodsAddr().getArea().get(0).getLon());
                mPoints.add(centerPoint);
                mGeoFenceClient.addGeoFence(centerPoint, 600F, "自有ID");
            }

            // 添加围栏
            //设置希望侦测的围栏触发行为，默认只侦测用户进入围栏的行为
            //public static final int GEOFENCE_IN 进入地理围栏
            //public static final int GEOFENCE_OUT 退出地理围栏
            //public static final int GEOFENCE_STAYED 停留在地理围栏内10分钟
            if (isGetInto) {
                mGeoFenceClient.setActivateAction(GEOFENCE_IN);
            } else {
                mGeoFenceClient.setActivateAction(GEOFENCE_OUT);
            }

            mGeoFenceClient.setGeoFenceListener(mGeoFenceListener);

            mGeoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);

            // 添加id
            for (DriverOrderNodeList driverOrderNodeList : driverOrderNodeWrapper.getDriverOrderNodeList()) {
                if (isGetInto) {
                    // 正在前往就取number=0
                    if (driverOrderNodeList.getNumber() == 0) {
                        mId.add(driverOrderNodeList.getId());
                    }
                } else {
                    if (driverOrderNodeList.getNumber() == 2) {
                        mId.add(driverOrderNodeList.getId());
                    }
                }
            }
        }


    }

    /**
     * 顶部显示弹出框
     *
     * @param message     文本
     * @param ok          ok的文本
     * @param onTopSheets 事件
     */
    protected void showTopSheets(String message, String ok, OnTopSheets onTopSheets) {
        if (mVTop == null)
            mVTop = findViewById(R.id.vTop);
        if (mTVMessage == null)
            mTVMessage = mVTop.findViewById(R.id.tvMessage);
        if (mTVOK == null)
            mTVOK = mVTop.findViewById(R.id.tvOK);
        // 关闭控件
        if (mVTop.findViewById(R.id.tvCancel) != null)
            mVTop.findViewById(R.id.tvCancel).setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    mVTop.setVisibility(View.GONE);
                }
            });

        Observable.just("Amit")
                //延时两秒，第一个参数是数值，第二个参数是事件单位
                .delay(10, TimeUnit.SECONDS)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        // 事件
                        mTVOK.setOnClickListener(v -> onTopSheets.OnTopSheetsOKKey());
                        mTVMessage.setText(message);
                        mTVOK.setText(ok);
                        mVTop.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(String s) {
                        mVTop.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mVTop.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        mVTop.setVisibility(View.GONE);
                    }
                });
    }

    //endregion 有关围栏的

    /**
     * 开启测试围栏的MainActivity
     */
    public void startTestActivity() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.yaoguang.driver.phone.test.record.RecordShowActivity.class);
        intent.putParcelableArrayListExtra("points", mPoints);
        startActivity(intent);
    }

    // region 有关地图，后台记录轨迹的

    private Calendar mOldUploadCalendar;   // 记录上次提交的时间，相隔3分钟就提交一次

    // 广播接收者
    private BroadcastReceiver locationChangeBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(RECEIVER_ACTION)) {
                if (!TextUtils.isEmpty(DataStatic.getInstance().getDriver().getId())) {
                    // 获取到的东西存到数据库
                    Location location = new Location();
                    location.setLat(intent.getDoubleExtra("latitude", 0));
                    location.setLon(intent.getDoubleExtra("longitude", 0));
                    location.setPositionTime(intent.getStringExtra("time"));
                    location.setUserDriverId(DataStatic.getInstance().getDriver().getId());
                    location.setAzimuth(intent.getStringExtra("azimuth"));
                    location.setSpeed(intent.getStringExtra("speed"));
                    Injections.getLocationBiz().save(location);
                }

                boolean isDateUpload = false;// 是否达到时间上限进行上传
                if (mOldUploadCalendar == null) {
                    mOldUploadCalendar = Calendar.getInstance();

                    // 第一次拿到数据的，直接提交
                    // 传输数据
                    List<Location> locations = Injections.getLocationBiz().queryAll();
                    if (locations.size() > 0) {
                        // 一次性提交
                        PositionApi positionApi = Api.getInstance().retrofit.create(PositionApi.class);
                        positionApi.add(locations)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                                    @Override
                                    public void onSuccess(BaseResponse<String> response) {
                                        mOldUploadCalendar = Calendar.getInstance();
                                        // 删除所有
                                        Injections.getLocationBiz().deleteAll();
                                    }
                                });
                    }
                } else {
                    Calendar newCalendar = Calendar.getInstance();
                    long i = newCalendar.getTimeInMillis() - mOldUploadCalendar.getTimeInMillis();
                    if (i / (1000 * 60) >= 3) {
                        // 时间大于等于3分钟
                        isDateUpload = true;
                    }
                }

                if (intent.getIntExtra("count", 0) == 100 || isDateUpload) {
                    // 传输数据
                    List<Location> locations = Injections.getLocationBiz().queryAll();
                    // 循环判断相邻的数据是否一样
                    if (locations.size() > 2) {
                        for (int i = locations.size() - 1; i == 0; i--) {
                            Location locationNew = locations.get(i + 1);
                            Location locationOld = locations.get(i - 1);
                            if (locationNew != null && locations.get(i).getLat() == locationNew.getLat()
                                                    && locations.get(i).getLon() == locationNew.getLon()) {
                                locations.remove(i);
                                continue;
                            }
                            if (locationOld != null && locations.get(i).getLat() == locationOld.getLat()
                                                    && locations.get(i).getLon() == locationOld.getLon()) {
                                locations.remove(i);
                            }
                        }
                    }
                    if (locations.size() > 0) {
                        // 删除所有
                        Injections.getLocationBiz().deleteAll();
                        // 一次性提交
                        PositionApi positionApi = Api.getInstance().retrofit.create(PositionApi.class);
                        positionApi.add(locations)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                                    @Override
                                    public void onSuccess(BaseResponse<String> response) {
                                        mOldUploadCalendar = Calendar.getInstance();
                                    }
                                });
                    }
                }
            }
        }
    };

    /**
     * 启动或者关闭定位服务
     */
    public void startService(boolean isStart) {
        if (isStart) {
            startLocationService();
        } else {
            stopLocationService();
        }
        LocationStatusManager.getInstance().resetToInit(getApplicationContext());
    }

    /**
     * 开始定位服务
     */
    private void startLocationService() {
        getApplicationContext().startService(new Intent(this, LocationService.class));
    }

    /**
     * 关闭服务
     * 先关闭守护进程，再关闭定位服务
     */
    private void stopLocationService() {
        sendBroadcast(Utils.getCloseBrodecastIntent());
    }

    /**
     * 轨迹纠偏并且上传数据
     */
    private void setupRecord() {
        // 轨迹纠偏初始化
        LBSTraceClient mTraceClient = new LBSTraceClient(
                getApplicationContext());
        // 获取当前登录的司机id的数据
        List<Location> locations = Injections.getLocationBiz().queryRaw(" WHERE " + LocationDao.Properties.UserDriverId + " = ?", DataStatic.getInstance().getDriver().getId());

        if (locations != null) {

            List<TraceLocation> mGraspTraceLocationList = Util
                    .parseTraceLocationList2(locations);
            // 调用轨迹纠偏，将mGraspTraceLocationList进行轨迹纠偏处理
            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList,
                    LBSTraceClient.TYPE_AMAP, new TraceListener() {
                        @Override
                        public void onRequestFailed(int i, String s) {
                            // 轨迹纠偏失败

                        }

                        @Override
                        public void onTraceProcessing(int i, int i1, List<LatLng> list) {

                        }

                        @Override
                        public void onFinished(int i, List<LatLng> list, int i1, int i2) {
                            // 轨迹纠偏成功
//                            if (locations.size() > 0){
//                                // 一次性提交
//                                PositionApi positionApi = Api.getInstance().retrofit.create(PositionApi.class);
//                                positionApi.add(locations)
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
//                                            @Override
//                                            public void onSuccess(BaseResponse<String> response) {
//                                                // 删除所有
//                                                Injections.getLocationBiz().deleteAll();
//                                            }
//                                        });
//                            }
                        }
                    });
        }

    }

    // endregion

}
