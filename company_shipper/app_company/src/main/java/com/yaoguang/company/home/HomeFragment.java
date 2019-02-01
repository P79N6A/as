package com.yaoguang.company.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.DPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yaoguang.appcommon.html.HtmlFragment;
import com.yaoguang.appcommon.phone.home.BaseHomeFragment;
import com.yaoguang.appcommon.phone.home.message.MessageFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.analysis.Analysis2Fragment;
import com.yaoguang.company.businessorder2.forwarder.list.BusinessOrderListFragment;
import com.yaoguang.company.databinding.FragmentHomeBinding;
import com.yaoguang.company.home.authorization.AuthorizationFragment;
import com.yaoguang.company.home.backstagelogon.BackstageLogonFragment;
import com.yaoguang.company.home.event.HomeFragmentEvent;
import com.yaoguang.company.my.PersonalCenterFragment;
import com.yaoguang.company.operator.OperatorFragment;
import com.yaoguang.company.pricetruck.PriceTruckFragment;
import com.yaoguang.company.shipschedule.ShipScheduleFragment;
import com.yaoguang.datasource.common.AppDataSource;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.company.RecentlyDataSource;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.LoginRestrict;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.qrcodescan.CustomCaptureActivity;

import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.amap.api.fence.GeoFence.STATUS_IN;
import static com.amap.api.fence.GeoFence.STATUS_OUT;
import static com.amap.api.fence.GeoFence.STATUS_STAYED;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_IN;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_OUT;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_STAYED;

/**
 * 首页
 */
public class HomeFragment extends BaseHomeFragment<FragmentHomeBinding> implements Toolbar.OnMenuItemClickListener {

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    int mApplyType;

    AppDataSource mAppDataSource = new AppDataSource();

    // region 有关围栏的

    public static final String GEOFENCE_BROADCAST_ACTION = "com.yaoguang.company.location.apis.geofencedemo.broadcast"; // 定义接收广播的action字符串

    private GeoFenceClient mGeoFenceClient; // 围栏

    ArrayList<DPoint> mPoints = new ArrayList<>();// 围栏点

    private boolean isGeoFenceReceiver = false;// 是否正在广播中围栏

    private String type;

    /**
     * 围栏广播
     */
    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null)
                if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                    // 判断该围栏只有进入、离开、停留才触发，如果是未知和失败，都不触发
                    if (intent.getExtras() != null && (intent.getExtras().getInt(GeoFence.BUNDLE_KEY_FENCESTATUS) == STATUS_IN
                            || intent.getExtras().getInt(GeoFence.BUNDLE_KEY_FENCESTATUS) == STATUS_STAYED)) {
                        // 进入状态
                        BaseApplication.getInstance().setRequestAPI(true);
                    } else if (intent.getExtras() != null && intent.getExtras().getInt(GeoFence.BUNDLE_KEY_FENCESTATUS) == STATUS_OUT) {
                        // 离开状态
                        BaseApplication.getInstance().setRequestAPI(false);
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
        _mActivity.registerReceiver(mGeoFenceReceiver, fliter);
    };

    // endregion 有关围栏的

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
        ((TextView) mViewHolder.toolbar.findViewById(R.id.toolbar_title)).setText("货云集物流");
        initJurisdiction();
        initLoginRestrict();
        initWifi();
    }

    @Override
    public void initListener() {
        //货代报价查询
        mDataBinding.cvAllForwarderQuotationInquiry.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(Analysis2Fragment.newInstance(), SINGLETOP);
            }
        });
        mDataBinding.cvForwarderForwarderQuotationInquiry.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(Analysis2Fragment.newInstance(), SINGLETOP);
            }
        });

        // 拖车报价查询
        mDataBinding.cvAllTrailerQuoteInquiry.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(PriceTruckFragment.newInstance(), SINGLETOP);
            }
        });
        mDataBinding.cvTrailerTrailerQuotationInquiry.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(PriceTruckFragment.newInstance(), SINGLETOP);
            }
        });

        // 货代预订单管理
        mDataBinding.cvAllForwarderPreOrder.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(BusinessOrderListFragment.newInstance("0"), SINGLETOP);
            }
        });
        mDataBinding.cvForwarderPreOrder.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(BusinessOrderListFragment.newInstance("0"), SINGLETOP);
            }
        });

        // 拖车预订单管理
        mDataBinding.cvAllTrailerPreOrder.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(com.yaoguang.company.businessorder2.truck.list.BusinessOrderListFragment.newInstance("1"), SINGLETOP);
            }
        });
        mDataBinding.cvTrailerPreOrder.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(com.yaoguang.company.businessorder2.truck.list.BusinessOrderListFragment.newInstance("1"), SINGLETOP);
            }
        });

        // 船位查询
        mDataBinding.cvForwarderShipPositioning.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(HtmlFragment.newInstance("船位查询", "http://weixin.shipxy.com/shipxy/map/?sid=972325687EAC4B2C909F74CF9811A8B4"), SINGLETOP);
            }
        });
        mDataBinding.cvTrailerShipPositioning.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(HtmlFragment.newInstance("船位查询", "http://weixin.shipxy.com/shipxy/map/?sid=972325687EAC4B2C909F74CF9811A8B4"), SINGLETOP);
            }
        });
        mDataBinding.cvForwarderForwarderShipPositioning.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(HtmlFragment.newInstance("船位查询", "http://weixin.shipxy.com/shipxy/map/?sid=972325687EAC4B2C909F74CF9811A8B4"), SINGLETOP);
            }
        });


        // 我的
        mDataBinding.cvAllMy.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(PersonalCenterFragment.newInstance(), SINGLETOP);

            }
        });
        mDataBinding.cvForwarderMy.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(PersonalCenterFragment.newInstance(), SINGLETOP);
            }
        });
        mDataBinding.cvTrailerMy.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(PersonalCenterFragment.newInstance(), SINGLETOP);
            }
        });

        // 船期查询
        mDataBinding.cvAllShipSchedule.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(ShipScheduleFragment.newInstance(), SINGLETOP);
            }
        });
        mDataBinding.cvForwarderShipSchedule.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(ShipScheduleFragment.newInstance(), SINGLETOP);
            }
        });

        // 货代办单
        mDataBinding.cvAllForwarderOperator.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(OperatorFragment.newInstance("0"), SINGLETOP);
            }
        });
        mDataBinding.cvForwarderForwarderOperator.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(OperatorFragment.newInstance("0"), SINGLETOP);
            }
        });

        // 拖车办单
        mDataBinding.cvAllTrailerOperator.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(OperatorFragment.newInstance("1"), SINGLETOP);
            }
        });
        mDataBinding.cvTrailerTrailerOperator.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(OperatorFragment.newInstance("1"), SINGLETOP);
            }
        });
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(BaseApplication.getInstance(), "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                // 获取类型
                int type = ObjectUtils.parseInt(result.getContents().substring(result.getContents().indexOf("&type=") + 6, result.getContents().length()));
                // 获取服务器有效性
                mAppDataSource.getServerTime()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, HomeFragment.this) {

                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                // 判断时间来显示是需要确认登录的还是无效的（时间过期）二维码
                                // 获取扫描拿到的时间
                                String activeTime = result.getContents().substring(result.getContents().indexOf("activeTime") + 11, result.getContents().indexOf("&type"));

                                // 获取服务器的时间
                                String webserviceTime = response.getResult();

                                // 时间比较
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                Date activeDate = null;
                                Date webserviceDate = null;
                                try {
                                    activeDate = sdf.parse(activeTime);
                                    webserviceDate = sdf.parse(webserviceTime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                assert webserviceDate != null;
                                if (type == 0) {
                                    if (webserviceDate.before(activeDate)) {
                                        start(BackstageLogonFragment.newInstance(result.getContents(), true, activeTime, webserviceTime));
                                    } else {
                                        start(BackstageLogonFragment.newInstance(result.getContents(), false, activeTime, webserviceTime));
                                    }
                                } else if (type == 1) {
                                    if (webserviceDate.before(activeDate)) {
                                        startForResult(AuthorizationFragment.newInstance(result.getContents(), true, activeTime, webserviceTime), -1);
                                    } else {
                                        startForResult(AuthorizationFragment.newInstance(result.getContents(), false, activeTime, webserviceTime), -1);
                                    }
                                }

                            }

                        });


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == -1)
                switch (data.getInt("type")) {
                    case -1:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(_mActivity);
                        intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                        intentIntegrator.initiateScan();
                        break;
                }
        }
    }

    @Override
    protected void onRefreshListener() {
        initJurisdiction();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initLoginRestrict();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                start(MessageFragment.newInstance(Constants.APP_COMPANY, 0), SINGLETOP);
                break;
            case R.id.action_scanning:
                IntentIntegrator intentIntegrator = new IntentIntegrator(_mActivity);
                intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                intentIntegrator.initiateScan();
                break;
        }
        return false;
    }

    @Subscribe
    public void onBusinessHomeFragmentEvent(HomeFragmentEvent homeFragmentEvent) {
        if (homeFragmentEvent.getType() == 0) {
            initWifi();
        }
    }

    //判断角色,相应角色有相应的权限控制
    private void initJurisdiction() {
        mApplyType = DataStatic.getInstance().getAppUserWrapper().getCompanyType();
        initListener();
        if (mApplyType == 0) {
            // 只有货代
            mDataBinding.llAll.setVisibility(View.GONE);
            mDataBinding.clForwarder.setVisibility(View.VISIBLE);
            mDataBinding.llTrailer.setVisibility(View.GONE);
        } else if (mApplyType == 1) {
            //如果只是拖车
            mDataBinding.llAll.setVisibility(View.GONE);
            mDataBinding.clForwarder.setVisibility(View.GONE);
            mDataBinding.llTrailer.setVisibility(View.VISIBLE);
        } else if (mApplyType == 2) {
            //全部启用
            mDataBinding.llAll.setVisibility(View.VISIBLE);
            mDataBinding.clForwarder.setVisibility(View.GONE);
            mDataBinding.llTrailer.setVisibility(View.GONE);
        } else if (mApplyType == 3) {
            //全部启用
            mDataBinding.llAll.setVisibility(View.VISIBLE);
            mDataBinding.clForwarder.setVisibility(View.GONE);
            mDataBinding.llTrailer.setVisibility(View.GONE);
        }
//        mApplyType = -1;
//        CompanyInfoContact.CompanyInfoInteractor<UserApply> mInteractor = new CCompanyInfoInteractorImpl<>();
//        mInteractor.getInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseResponse<UserApply>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        mCompositeDisposable.add(d);
//                    }
//
//                    @Override
//                    public void onNext(BaseResponse<UserApply> value) {
//                        if (value.getState().equals("200")) {
//                            mApplyType = value.getResult().getApplyType();
//                            initListener();
//                            if (value.getResult().getApplyType() == 0) {
//                                // 只有货代
//                                mDataBinding.llAll.setVisibility(View.GONE);
//                                mDataBinding.clForwarder.setVisibility(View.VISIBLE);
//                                mDataBinding.llTrailer.setVisibility(View.GONE);
//                            } else if (value.getResult().getApplyType() == 1) {
//                                //如果只是拖车
//                                mDataBinding.llAll.setVisibility(View.GONE);
//                                mDataBinding.clForwarder.setVisibility(View.GONE);
//                                mDataBinding.llTrailer.setVisibility(View.VISIBLE);
//                            } else if (value.getResult().getApplyType() == 2) {
//                                //全部启用
//                                mDataBinding.llAll.setVisibility(View.VISIBLE);
//                                mDataBinding.clForwarder.setVisibility(View.GONE);
//                                mDataBinding.llTrailer.setVisibility(View.GONE);
//                            } else if (value.getResult().getApplyType() == 3) {
//                                //全部启用
//                                mDataBinding.llAll.setVisibility(View.VISIBLE);
//                                mDataBinding.clForwarder.setVisibility(View.GONE);
//                                mDataBinding.llTrailer.setVisibility(View.GONE);
//                            }
//                        }
//                        // 跟wifi地址权限冲突，弃用
////                        else {
////                            LoginActivity.newInstance(getActivity(), true, null, null);
////                            _mActivity.finish();
////                            Toast.makeText(BaseApplication.getInstance(), value.getMessage() + "退出重新登录", Toast.LENGTH_LONG).show();
////                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(BaseApplication.getInstance(), "网络错误,请退出重新登录", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    // 获取设置登陆的一些配置
    private void initLoginRestrict() {
        mAppDataSource.loginRestrict()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginRestrict>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<LoginRestrict> response) {
                        addGeoFence(response.getResult().getLocation());
                    }

                });
    }

    /**
     * 初始化wifi信息
     */
    private void initWifi() {
        WifiManager mWifi = (WifiManager) _mActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        BaseApplication.getInstance().setSSID(wifiInfo.getBSSID());
        RecentlyDataSource recentlyDataSource = new RecentlyDataSource();
        if (!TextUtils.isEmpty(wifiInfo.getBSSID()) && !TextUtils.isEmpty(wifiInfo.getSSID())) {
            recentlyDataSource.wlanLogSave(whetherToRemoveTheDoubleQuotationMarks(wifiInfo.getSSID()), wifiInfo.getBSSID())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                        }

                    });
        }
    }

    //根据Android的版本判断获取到的SSID是否有双引号
    public String whetherToRemoveTheDoubleQuotationMarks(String ssid) {

        //获取Android版本号
        int deviceVersion = Build.VERSION.SDK_INT;

        if (deviceVersion >= 17) {

            if (ssid.startsWith("\"") && ssid.endsWith("\"")) {

                ssid = ssid.substring(1, ssid.length() - 1);
            }

        }
        return ssid;
    }

    /**
     * 启用/禁用
     *
     * @param cardView      卡片块控件
     * @param cardViewColor 卡片颜色
     * @param tvTitle       文本控件
     * @param textViewColor 文本颜色
     * @param imgTitle      图片控件
     * @param drawable      图片资源
     * @param drawableColor 图片颜色
     */
    private void enableCardView(CardView cardView, int cardViewColor, TextView tvTitle, int textViewColor, ImageView imgTitle, int drawable, int drawableColor) {
        if (cardView != null)
            cardView.setBackgroundColor(ContextCompat.getColor(_mActivity, cardViewColor));
        if (tvTitle != null)
            tvTitle.setTextColor(ContextCompat.getColor(_mActivity, textViewColor));
        if (imgTitle != null) {
            Drawable upForwardingOrder = ContextCompat.getDrawable(_mActivity, drawable);
            assert upForwardingOrder != null;
            Drawable drawableForwardingOrder = DrawableCompat.wrap(upForwardingOrder);
            DrawableCompat.setTint(drawableForwardingOrder, ContextCompat.getColor(_mActivity, drawableColor));
            imgTitle.setImageDrawable(drawableForwardingOrder);
        }
    }

    /**
     * 增加地理围栏
     */
    public void addGeoFence(List<UserLoginAllowLocation> listLocation) {
        if (mGeoFenceClient == null) {
            mGeoFenceClient = new GeoFenceClient(getContext());
        }

        // 增加围栏之前，先删除之前的所有围栏
        mGeoFenceClient.removeGeoFence();
        // 创建一个中心点坐标
        mPoints.clear();
        // 取消广播
        if (isGeoFenceReceiver) {
            _mActivity.unregisterReceiver(mGeoFenceReceiver);
            isGeoFenceReceiver = false;
        }

        // 循环添加多个围栏和id等等
        for (UserLoginAllowLocation userLoginAllowLocation : listLocation) {
            // 创建一个中心点坐标
            DPoint centerPoint = new DPoint();
            //设置中心点纬度
            centerPoint.setLatitude(ObjectUtils.parseDouble(userLoginAllowLocation.getLat()));
            //设置中心点经度
            centerPoint.setLongitude(ObjectUtils.parseDouble(userLoginAllowLocation.getLon()));
            mPoints.add(centerPoint);
            mGeoFenceClient.addGeoFence(centerPoint, ObjectUtils.parseFloat(userLoginAllowLocation.getRadius()), userLoginAllowLocation.getId());
            mGeoFenceClient.setActivateAction(GEOFENCE_IN | GEOFENCE_OUT | GEOFENCE_STAYED);
            mGeoFenceClient.setGeoFenceListener(mGeoFenceListener);
            mGeoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
        }


    }

}
