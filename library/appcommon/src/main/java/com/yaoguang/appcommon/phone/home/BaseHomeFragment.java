package com.yaoguang.appcommon.phone.home;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseMainFragment;
import com.yaoguang.appcommon.phone.home.adapter.LocalImageHolderView;
import com.yaoguang.appcommon.phone.home.event.HomeMessageEvent;
import com.yaoguang.appcommon.phone.home.event.HomeWeatherEvent;
import com.yaoguang.appcommon.phone.home.message.MessageDetailFragment;
import com.yaoguang.appcommon.phone.home.message.MessageFragment;
import com.yaoguang.appcommon.phone.home.weathergaode.WeatherGaoDeFragment;
import com.yaoguang.appcommon.phone.home.weathergaode.WeatherGaoDeUtils;
import com.yaoguang.datasource.api.MessageApi;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.SpecialMsg;
import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.appcommon.widget.head.PullHead;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.displaymetrics.ProportionUtils;
import com.yaoguang.widget.viewflipper.UPMarqueeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 基类首页
 * Created by zhongjh on 2018/1/22.
 */
public abstract class BaseHomeFragment<B extends ViewDataBinding> extends BaseMainFragment<B>  implements HomeContact.View {

    HomeContact.Presenter mPresenter = new HomePresenter(this);

    public ViewHolder mViewHolder; // View
    List<SysMsg> mSysMsgs = new ArrayList<>();// 消息数据源
    MessageDialog mDialog;//为了确保每次只有一个弹窗

    // 子类的刷新
    protected abstract void onRefreshListener();

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        super.initDataBind(view, inflater);
        mViewHolder = new ViewHolder(view);
        // 设置菜单
        mViewHolder.toolbar.inflateMenu(R.menu.home);
        // Banner 动态高度
        dynamicHeight();
        initBannerData();
        setRefreshLayout();

        //刷新
        mViewHolder.refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                initMessageData();
                initBannerData();
                onRefreshListener();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });

        //点击打开窗体
        mViewHolder.llWeather.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                start(WeatherGaoDeFragment.newInstance(), SINGLETOP);
            }
        });

        //初始化未读消息的数量
        initMessageData();

        //初始化天气
//        initWeatherView(null);
        initWeather();

        //初始化弹窗
        initDialogData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 默认不改变
        return super.onCreateFragmentAnimator();
    }

    @Override
    public void onResume() {
        super.onResume();
//        initWeatherView(null);
        initWeather();
    }

    @Subscribe(sticky = true)
    public void onEvent(HomeMessageEvent event) {
        //刷新信息
        if (event.isInitMessage())
            initMessageData();

        //初始化弹窗
        if (event.isInitDialog())
            initDialogData();
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Subscribe
    public void onEvent(HomeWeatherEvent event) {
        //刷新天气
//        initWeatherView(null);
        initWeather();
    }

    @Override
    public void dynamicHeight() {
        //动态设置convenientBanner高度
        //获取屏幕的宽度
        int screenWidth = DisplayMetricsSPUtils.getScreenWidth(getContext());
        //计算BGABanner的应有高度
        int viewHeight = ProportionUtils.getHeight(170, 360, screenWidth);
        //设置BGABanner的宽高属性
        ViewGroup.LayoutParams banner_params = mViewHolder.convenientBanner.getLayoutParams();
        banner_params.width = screenWidth;
        banner_params.height = viewHeight;
        mViewHolder.convenientBanner.setLayoutParams(banner_params);
    }

    @Override
    public void initBannerData() {
        mPresenter.initBannerData();
    }

    @Override
    public void initBannerView(ArrayList<BannerPic> bannerPics) {
        //为了灵活的使用滚动的View，所以把滚动的内容让用户自定义,假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了
        mViewHolder.imgBanner.setVisibility(View.GONE);
        mViewHolder.convenientBanner.setVisibility(View.VISIBLE);
        //循环
        mViewHolder.convenientBanner.setPages(
                LocalImageHolderView::new, bannerPics)
                .startTurning(5000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @Override
    public void initMessageBannerView(List<SpecialMsg> result) {
        mViewHolder.llNotice.setVisibility(View.GONE);
        mViewHolder.upview1.setVisibility(View.VISIBLE);

        List<View> views = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(BaseHomeFragment.this.getContext()).inflate(R.layout.item_home_message, null);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.tvTitle);
            TextView tv2 = moreView.findViewById(R.id.tvContent);
            tv1.setText(result.get(i).getTitle());
            tv2.setText(result.get(i).getContent());
            //添加到循环滚动数组里面去
            views.add(moreView);
            final int finalI = i;
            tv1.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    //跳转详情
                    start(MessageDetailFragment.newInstance("消息详情", BuildConfig.ENDPOINT + MessageApi.MESSAGEDETAIL + result.get(finalI).getId() + "&type=1", null, ObjectUtils.parseString(BaseApplication.getAppType()), result.get(finalI).getId(), null), SINGLETOP);
                }
            });
            tv2.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    //跳转详情7
                    start(MessageDetailFragment.newInstance("消息详情", BuildConfig.ENDPOINT + MessageApi.MESSAGEDETAIL + result.get(finalI).getId() + "&type=1", null, ObjectUtils.parseString(BaseApplication.getAppType()), result.get(finalI).getId(), null), SINGLETOP);
                }
            });
        }
        mViewHolder.upview1.setViews(views);
    }

    @Override
    public void finishRefreshing() {
        mViewHolder.refresh.finishRefreshing();
    }

    @Override
    public void setRefreshLayout() {
        PullHead headerView = new PullHead(BaseHomeFragment.this.getContext());
        mViewHolder.refresh.setHeaderView(headerView);
        mViewHolder.refresh.setHeaderHeight(110);
    }

    @Override
    public void initMessageData() {
        mPresenter.initMessageData();
    }

    @Override
    public void initMessageView(HashMap<String, Integer> result) {
        int count = ObjectUtils.parseInt(result.get("unReadSystemNumber")) + ObjectUtils.parseInt(result.get("unReadCompanyNumber"));
        mViewHolder.toolbar.getMenu().clear();
        if (count > 0) {
            //替换红点
            mViewHolder.toolbar.inflateMenu(R.menu.home_red);
        } else {
            mViewHolder.toolbar.inflateMenu(R.menu.home);
        }
    }

//    @Override
//    public void initWeatherData(Context context, Long currentData) {
//        SPUtils.getInstance().put(Constants.WeatherData, currentData);
//        final LocationManager locationManager = new LocationManager();
//        locationManager.init(context);
//        locationManager.setComeback(location -> {
//            locationManager.destroyLocation();
//            mPresenter.initWeatherData(location.getLat(), location.getLon());
//        });
//    }

//    @Override
//    public void initWeatherView(AliWeatherComplex aliWeatherComplex) {
//
//        // 赋值数据源
//        if (aliWeatherComplex != null) {
//            SPUtils.getInstance().put(Constants.Weather, aliWeatherComplex.getShowapi_res_body().getNow().getTemperature() + "℃");
//            SPUtils.getInstance().put(Constants.WeatherCity, aliWeatherComplex.getShowapi_res_body().getCityInfo().getC5());
//            SPUtils.getInstance().put(Constants.WeatherPic, aliWeatherComplex.getShowapi_res_body().getNow().getWeather_pic());
//        }
//
//        Long data = SPUtils.getInstance().getLong(Constants.WeatherData);
//        Long currentData = System.currentTimeMillis();
//        //s为相隔的分钟数
//        Long s = (currentData - data) / (1000 * 60);
//        //如果大于3个小时或者是第一次加载
//        if (s > 180) {
//            initWeatherData(getContext(), currentData);
//        } else {
//            mViewHolder.tvWeather.setText(SPUtils.getInstance().getString(Constants.Weather));
//            mViewHolder.tvCity.setText(SPUtils.getInstance().getString(Constants.WeatherCity));
//            mViewHolder.imgWeather.setVisibility(View.VISIBLE);
//            Glide.with(getContext())
//                    .load(SPUtils.getInstance().getString(Constants.WeatherPic))
//                    .centerCrop()
//                    .into(new GlideDrawableImageViewTarget(mViewHolder.imgWeather) {
//                        @Override
//                        public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
//                            super.onResourceReady(drawable, anim);
//                            //在这里添加一些图片加载完成的操作
//                            mViewHolder.imgWeather.setVisibility(View.VISIBLE);
//                        }
//                    });
//        }
//    }

//    @Override
//    public void setWeatherText(String text) {
//        mViewHolder.tvWeather.setText(text);
//        mViewHolder.tvCity.setText(text);
//    }

    public void initWeather() {
        WeatherGaoDeUtils weatherGaoDeUtils = new WeatherGaoDeUtils();
        weatherGaoDeUtils.getWeatherLiveSearched(this.getContext(), (liveResult, i, location) -> {
            mViewHolder.tvWeather.setText(liveResult.getLiveResult().getTemperature());
            mViewHolder.tvCity.setText(location.getCity());
//            mDataBinding.toolbarHomeCommon.tvCity.setText(location.getCity());
//            mDataBinding.toolbarHomeCommon.tvWeather.setText(liveResult.getLiveResult().getTemperature());
        });
    }

    @Override
    public void initDialogData() {
        if (mDialog == null || !mDialog.isShowing()) {
            mPresenter.initDialogData();
        }
    }

    @Override
    public void initDialogDataView(List<SysMsg> result) {
        //缓存列表
        mSysMsgs = result;
        if (mSysMsgs.size() > 0) {
            //显示弹窗
            mDialog = new MessageDialog(getContext(), mSysMsgs);
            mDialog.setDialogListener(new MessageDialog.DialogListener() {
                @Override
                public void onLock(SysMsg sysMsg) {
                    start(MessageDetailFragment.newInstance("消息详情", BuildConfig.ENDPOINT + MessageApi.MESSAGEDETAIL + sysMsg.getId(), null, BaseApplication.getAppType(), sysMsg.getId(), null), SINGLETOP);
                }

                @Override
                public void onLock() {
                    start(MessageFragment.newInstance(Constants.APP_COMPANY, 0));
                }
            });
            mDialog.show();
            //设置已弹窗
            StringBuilder ids = new StringBuilder();
            for (SysMsg sysMsg : mSysMsgs) {
                ids.append(sysMsg.getId()).append(",");
            }
            mPresenter.updatePopUpData(ids.toString());
        }
    }

    @Override
    public void removewSysMsg() {
        // 全部读完后，就清空
        mSysMsgs.clear();
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView imgWeather;
        public TextView tvWeather;
        public TextView tvCity;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public ConvenientBanner convenientBanner;
        public ImageView imgNotice;
        public TextView tvTitle;
        public TextView tvContent;
        public LinearLayout llNotice;
        public UPMarqueeView upview1;
        public TwinklingRefreshLayout refresh;
        public ImageView imgBanner;
        public LinearLayout llWeather;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.imgWeather = rootView.findViewById(R.id.imgWeather);
            this.tvWeather = rootView.findViewById(R.id.tvWeather);
            this.tvCity = rootView.findViewById(R.id.tvCity);
            this.toolbar_title = rootView.findViewById(R.id.toolbar_title);
            this.toolbar = rootView.findViewById(R.id.toolbar);
            this.convenientBanner = rootView.findViewById(R.id.convenientBanner);
            this.imgNotice = rootView.findViewById(R.id.imgNotice);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvContent = rootView.findViewById(R.id.tvContent);
            this.llNotice = rootView.findViewById(R.id.llNotice);
            this.upview1 = rootView.findViewById(R.id.upview1);
            this.refresh = rootView.findViewById(R.id.refresh);
            this.imgBanner = rootView.findViewById(R.id.imgBanner);
            this.llWeather = rootView.findViewById(R.id.llWeather);
        }
    }


}
