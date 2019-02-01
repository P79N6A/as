package com.yaoguang.appcommon.phone.home.weather;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.appcommon.phone.home.event.HomeWeatherEvent;
import com.yaoguang.appcommon.phone.home.weather.adapter._24WeatherAdapter;
import com.yaoguang.appcommon.phone.home.weather.adapter._7WeatherAdapter;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.LunarUtil;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.WeatherUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.appcommon.widget.weather.SunProgressBar;
import com.yaoguang.lib.appcommon.widget.weather.SunProgressBarBackground;
import com.yaoguang.greendao.entity.AliWeatherComplex;
import com.yaoguang.greendao.entity.AliWeatherWeek;
import com.yaoguang.greendao.entity.F1Bean;
import com.yaoguang.map.location.impl.LocationManager;
import com.yaoguang.lib.net.ApiDefault;
import com.yaoguang.datasource.api.WeatherApi;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 天气窗口
 * Created by zhongjh on 2017/9/4.
 */
public class WeatherFragment extends BaseBackFragment {

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    ViewHolder mViewHolder;

    boolean isCurrentCount = false;

    float sunsetF = 0.0f;
    boolean isAnimation = false;

    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mViewHolder = new ViewHolder(view);
        getWeather();
        mViewHolder.sunProgressBarBackground.startAnimator(0, 1f, 0);
        //刷新
        mViewHolder.refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                isCurrentCount = false;
                getWeather();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });


        mViewHolder.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                WeatherFragment.this.pop();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    /**
     * 设置顶部可以滑动
     */
    void setRefreshLayout() {
        SinaRefreshView headerView = new SinaRefreshView(WeatherFragment.this.getContext());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mViewHolder.refresh.setHeaderView(headerView);
    }

    public void getWeather() {
        final LocationManager locationManager = new LocationManager();
        locationManager.init(getContext());
        locationManager.setComeback(location -> {
            locationManager.destroyLocation();
            WeatherApi weatherApi = ApiDefault.getInstance().retrofit.create(WeatherApi.class);
            weatherApi.getWeather2("APPCODE ebbefba3b9224a5389e9f18298e954a0", "3", ObjectUtils.parseString(location.getLat()), ObjectUtils.parseString(location.getLon()), "1", "0", "0", "1", "1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<AliWeatherComplex>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(AliWeatherComplex aliWeather) {
                            if (aliWeather == null || aliWeather.getShowapi_res_body().getNow() == null
                                    || aliWeather.getShowapi_res_body() == null ||
                                    aliWeather.getShowapi_res_body().getNow().getWeather_pic() == null)
                                return;
                            initData(aliWeather);
                            mViewHolder.refresh.finishRefreshing();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });
    }

    private void initData(AliWeatherComplex aliWeatherComplex) {

        //每次初始化的时候，保存进缓存
        SPUtils.getInstance().put(Constants.Weather, aliWeatherComplex.getShowapi_res_body().getNow().getTemperature() + "℃");
        SPUtils.getInstance().put(Constants.WeatherCity, aliWeatherComplex.getShowapi_res_body().getCityInfo().getC5());
        SPUtils.getInstance().put(Constants.WeatherPic, aliWeatherComplex.getShowapi_res_body().getNow().getWeather_pic());
        //刷新首页的天气信息
        EventBus.getDefault().post(new HomeWeatherEvent());

        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);//设置日期格式
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);//设置日期格式
        Date date = new Date();

        mViewHolder.toolbar_title.setText(aliWeatherComplex.getShowapi_res_body().getCityInfo().getC5());

        mViewHolder.tvDate.setText(df.format(date));
        //新历转农历
        LunarUtil lunarUtil = new LunarUtil(Calendar.getInstance());
        mViewHolder.tvDegrees.setText(aliWeatherComplex.getShowapi_res_body().getNow().getTemperature());
        mViewHolder.tvWeather.setText(aliWeatherComplex.getShowapi_res_body().getNow().getWeather());
        mViewHolder.tvNowDate.setText(df2.format(date) + "  周" + WeatherUtil.parstChineseNumeralsN(aliWeatherComplex.getShowapi_res_body().getF1().getWeekday()) + "  " + lunarUtil.toString());

        mViewHolder.tvDegreesMinMax.setText(aliWeatherComplex.getShowapi_res_body().getF1().getDay_air_temperature() + "/" + aliWeatherComplex.getShowapi_res_body().getF1().getNight_air_temperature() + "℃");
        mViewHolder.tvWind.setText(aliWeatherComplex.getShowapi_res_body().getNow().getWind_direction() + aliWeatherComplex.getShowapi_res_body().getNow().getWind_power());
        mViewHolder.tvWet.setText(aliWeatherComplex.getShowapi_res_body().getNow().getSd());

        //初始化日出日落
        initSum(aliWeatherComplex);

        //初始化24小时天气
        init24Weather(aliWeatherComplex);

        //初始化一周天气
        init7Weather(aliWeatherComplex);

        //初始化生活指数
        initLivingIndex(aliWeatherComplex);

        //显示
        mViewHolder.toolbar.setVisibility(View.VISIBLE);
        mViewHolder.refresh.setVisibility(View.VISIBLE);


        final Rect scrollBounds = new Rect();
        mViewHolder.scrollView.getHitRect(scrollBounds);
        mViewHolder.scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (!isAnimation) {
                    if (!mViewHolder.sunBar.getLocalVisibleRect(scrollBounds)) {
                        //不可见就返回
                        return;
                    }
                    //计算日出日落,这个动画只执行一次
                    mViewHolder.sunBar.post(new Runnable() {
                        @Override
                        public void run() {
                            mViewHolder.sunBar.startAnimator(0, sunsetF, 1500);
                        }
                    });
                    isAnimation = true;
                }
            }
        });

    }

    /**
     * 初始化24小时天气
     *
     * @param aliWeatherComplex 天气实体
     */
    private void init24Weather(AliWeatherComplex aliWeatherComplex) {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WeatherFragment.this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewHolder.rv24Weather.setLayoutManager(linearLayoutManager);
        //循环数据源，将8时-11时改成08:00 11:00、11:00 14:00、14:00 17:00、17:00 20:00、20:00 23:00、23:00 02:00、02:00 05:00
        List<F1Bean._$3hourForcastBeanXX> _24list = new ArrayList<>();
        for (int i = 0; i < aliWeatherComplex.getShowapi_res_body().getF1().get_$3hourForcast().size() - 1; i++) {
            F1Bean._$3hourForcastBeanXX entity = aliWeatherComplex.getShowapi_res_body().getF1().get_$3hourForcast().get(i);

            int beginInt = ObjectUtils.parseInt(entity.getHour().substring(0, entity.getHour().indexOf("时")));
            String beginData = ObjectUtils.parseString(beginInt);
            if (beginData.length() == 1) {
                beginData = "0" + beginData;
            }

            F1Bean._$3hourForcastBeanXX entityBegin = new F1Bean._$3hourForcastBeanXX();
            // 判断是否晚上,如果大于日落或者小于日出
            if (ObjectUtils.parseInt(beginData) > ObjectUtils.parseInt(mViewHolder.tvSunset.getText().toString().split(":")[0]) ||
                    ObjectUtils.parseInt(beginData) < ObjectUtils.parseInt(mViewHolder.tvSunrise.getText().toString().split(":")[0])) {
                entityBegin.setNight(true);
            }
            beginData = beginData + ":00";
            entityBegin.setHour(beginData);
            entityBegin.setTemperature(entity.getTemperature_min());
            entityBegin.setWeather(entity.getWeather());


            entityBegin.setNight(false);

            beginInt = beginInt + 1;
            String beginData1 = ObjectUtils.parseString(beginInt);
            if (beginData1.length() == 1) {
                beginData1 = "0" + beginData1;
            }

            F1Bean._$3hourForcastBeanXX entityBegin1 = new F1Bean._$3hourForcastBeanXX();
            // 判断是否晚上,如果大于日落或者小于日出
            if (ObjectUtils.parseInt(beginData1) > ObjectUtils.parseInt(mViewHolder.tvSunset.getText().toString().split(":")[0]) ||
                    ObjectUtils.parseInt(beginData1) < ObjectUtils.parseInt(mViewHolder.tvSunrise.getText().toString().split(":")[0])) {
                entityBegin1.setNight(true);
            }
            beginData1 = beginData1 + ":00";
            entityBegin1.setHour(beginData1);
            entityBegin1.setTemperature(entity.getTemperature());
            entityBegin1.setWeather(entity.getWeather());

            beginInt = beginInt + 1;
            if (beginInt > 24) {
                beginInt = beginInt - 24;
            }
            String beginData2 = ObjectUtils.parseString(beginInt);
            if (beginData2.length() == 1) {
                beginData2 = "0" + beginData2;
            }
            F1Bean._$3hourForcastBeanXX entityBegin2 = new F1Bean._$3hourForcastBeanXX();
            // 判断是否晚上,如果大于日落或者小于日出
            if (ObjectUtils.parseInt(beginData2) > ObjectUtils.parseInt(mViewHolder.tvSunset.getText().toString().split(":")[0]) ||
                    ObjectUtils.parseInt(beginData2) < ObjectUtils.parseInt(mViewHolder.tvSunrise.getText().toString().split(":")[0])) {
                entityBegin2.setNight(true);
            }
            beginData2 = beginData2 + ":00";
            entityBegin2.setHour(beginData2);
            entityBegin2.setTemperature(entity.getTemperature());
            entityBegin2.setWeather(entity.getWeather());

//            String endData = entity.getHour().substring(entity.getHour().indexOf("-") + 1, entity.getHour().length() - 1);
//            if (endData.length() == 1) {
//                endData = "0" + endData;
//            }
//            endData = endData + ":00";
//            AliWeatherComplex.ShowapiResBodyBean.F1Bean._$3hourForcastBeanXX entityEnd = new AliWeatherComplex.ShowapiResBodyBean.F1Bean._$3hourForcastBeanXX();
//            entityEnd.setHour(endData);
//            entityEnd.setTemperature(entity.getTemperature_max());
//            entityEnd.setWeather(entity.initWeatherData());

            _24list.add(entityBegin);
            _24list.add(entityBegin1);
            _24list.add(entityBegin2);
        }
        //设置适配器
        _24WeatherAdapter mAdapter = new _24WeatherAdapter(getContext(), _24list);
        mViewHolder.rv24Weather.setAdapter(mAdapter);
    }

    /**
     * 初始化7天天气
     *
     * @param aliWeatherComplex 天气实体
     */
    private void init7Weather(AliWeatherComplex aliWeatherComplex) {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WeatherFragment.this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewHolder.rvWeekWeather.setLayoutManager(linearLayoutManager);
        //把从今天到第7天都加入，今天/明天/周几 8/29 图标 天气文字
        List<AliWeatherWeek> aliWeatherWeeks = new ArrayList<>();

        AliWeatherWeek aliWeatherWeek = new AliWeatherWeek();
        aliWeatherWeek.setStrWeekDay("今天");
        aliWeatherWeek.setDay(aliWeatherComplex.getShowapi_res_body().getF1().getDay());
        aliWeatherWeek.setDayWeather(aliWeatherComplex.getShowapi_res_body().getF1().getDay_weather());
        aliWeatherWeek.setDayWeatherImage(new WeatherUtils().getImage(aliWeatherComplex.getShowapi_res_body().getF1().getDay_weather(), false));

        AliWeatherWeek aliWeatherWeek2 = new AliWeatherWeek();
        aliWeatherWeek2.setStrWeekDay("明天");
        aliWeatherWeek2.setDay(aliWeatherComplex.getShowapi_res_body().getF2().getDay());
        aliWeatherWeek2.setDayWeather(aliWeatherComplex.getShowapi_res_body().getF2().getDay_weather());
        aliWeatherWeek2.setDayWeatherImage(new WeatherUtils().getImage(aliWeatherComplex.getShowapi_res_body().getF2().getDay_weather(), false));

        AliWeatherWeek aliWeatherWeek3 = new AliWeatherWeek();
        aliWeatherWeek3.setStrWeekDay("周" + WeatherUtil.parstChineseNumeralsN(aliWeatherComplex.getShowapi_res_body().getF3().getWeekday()));
        aliWeatherWeek3.setDay(aliWeatherComplex.getShowapi_res_body().getF3().getDay());
        aliWeatherWeek3.setDayWeather(aliWeatherComplex.getShowapi_res_body().getF3().getDay_weather());
        aliWeatherWeek3.setDayWeatherImage(new WeatherUtils().getImage(aliWeatherComplex.getShowapi_res_body().getF3().getDay_weather(), false));

        AliWeatherWeek aliWeatherWeek4 = new AliWeatherWeek();
        aliWeatherWeek4.setStrWeekDay("周" + WeatherUtil.parstChineseNumeralsN(aliWeatherComplex.getShowapi_res_body().getF4().getWeekday()));
        aliWeatherWeek4.setDay(aliWeatherComplex.getShowapi_res_body().getF4().getDay());
        aliWeatherWeek4.setDayWeather(aliWeatherComplex.getShowapi_res_body().getF4().getDay_weather());
        aliWeatherWeek4.setDayWeatherImage(new WeatherUtils().getImage(aliWeatherComplex.getShowapi_res_body().getF4().getDay_weather(), false));

        AliWeatherWeek aliWeatherWeek5 = new AliWeatherWeek();
        aliWeatherWeek5.setStrWeekDay("周" + WeatherUtil.parstChineseNumeralsN(aliWeatherComplex.getShowapi_res_body().getF5().getWeekday()));
        aliWeatherWeek5.setDay(aliWeatherComplex.getShowapi_res_body().getF5().getDay());
        aliWeatherWeek5.setDayWeather(aliWeatherComplex.getShowapi_res_body().getF5().getDay_weather());
        aliWeatherWeek5.setDayWeatherImage(new WeatherUtils().getImage(aliWeatherComplex.getShowapi_res_body().getF5().getDay_weather(), false));

        AliWeatherWeek aliWeatherWeek6 = new AliWeatherWeek();
        aliWeatherWeek6.setStrWeekDay("周" + WeatherUtil.parstChineseNumeralsN(aliWeatherComplex.getShowapi_res_body().getF6().getWeekday()));
        aliWeatherWeek6.setDay(aliWeatherComplex.getShowapi_res_body().getF6().getDay());
        aliWeatherWeek6.setDayWeather(aliWeatherComplex.getShowapi_res_body().getF6().getDay_weather());
        aliWeatherWeek6.setDayWeatherImage(new WeatherUtils().getImage(aliWeatherComplex.getShowapi_res_body().getF6().getDay_weather(), false));

        AliWeatherWeek aliWeatherWeek7 = new AliWeatherWeek();
        aliWeatherWeek7.setStrWeekDay("周" + WeatherUtil.parstChineseNumeralsN(aliWeatherComplex.getShowapi_res_body().getF7().getWeekday()));
        aliWeatherWeek7.setDay(aliWeatherComplex.getShowapi_res_body().getF7().getDay());
        aliWeatherWeek7.setDayWeather(aliWeatherComplex.getShowapi_res_body().getF7().getDay_weather());
        aliWeatherWeek7.setDayWeatherImage(new WeatherUtils().getImage(aliWeatherComplex.getShowapi_res_body().getF7().getDay_weather(), false));

        aliWeatherWeeks.add(aliWeatherWeek);
        aliWeatherWeeks.add(aliWeatherWeek2);
        aliWeatherWeeks.add(aliWeatherWeek3);
        aliWeatherWeeks.add(aliWeatherWeek4);
        aliWeatherWeeks.add(aliWeatherWeek5);
        aliWeatherWeeks.add(aliWeatherWeek6);
        aliWeatherWeeks.add(aliWeatherWeek7);

        //设置适配器
        _7WeatherAdapter mAdapter = new _7WeatherAdapter(WeatherFragment.this.getContext(), aliWeatherWeeks);
        mViewHolder.rvWeekWeather.setAdapter(mAdapter);
    }

    /**
     * 初始化生活指数
     */
    private void initLivingIndex(AliWeatherComplex aliWeatherComplex) {
        mViewHolder.tvGmzs.setText(aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getCold().getTitle() + "。" + aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getCold().getDesc());
        mViewHolder.tvCyzs.setText(aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getClothes().getTitle() + "。" + aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getClothes().getDesc());
        mViewHolder.tvKqzs.setText(aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getAqi().getTitle() + "。" + aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getAqi().getDesc());
        mViewHolder.tvXczs.setText(aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getWash_car().getTitle() + "。" + aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getWash_car().getDesc());
        mViewHolder.tvLyzs.setText(aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getTravel().getTitle() + "。" + aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getTravel().getDesc());
        mViewHolder.tvZwxzs.setText(aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getUv().getTitle() + "。" + aliWeatherComplex.getShowapi_res_body().getF1().getIndex().getUv().getDesc());

    }

    /**
     * 初始化日出日落
     */
    private void initSum(AliWeatherComplex aliWeatherComplex) {
        //3个时间
        long sunriseL = 0;//日出毫秒
        long sunsetL = 0;//日落毫秒
        long sunCurrentL = 0;//当前毫秒
        //总和毫秒，当前毫秒
        long sunSumL = 0;//总和毫秒
        long sunCurrentPL = 0;//当前进度毫秒
        //最终百分比
        double sunP = 0f;
        //获取日出和日落时间、根据日落时间和日出时间计算百分比
        String[] suns = aliWeatherComplex.getShowapi_res_body().getF1().getSun_begin_end().split("\\|");
        //赋值日出日落时间
        mViewHolder.tvSunrise.setText(suns[0]);
        mViewHolder.tvSunset.setText(suns[1]);
        //得出3个时间的毫秒
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);

            calendar.setTime(df.parse(calendar.get(GregorianCalendar.YEAR) + "-" + (calendar.get(GregorianCalendar.MONTH) + 1) + "-" + calendar.get(GregorianCalendar.DAY_OF_MONTH) + " " + suns[0]));
            sunriseL = calendar.getTimeInMillis();
            calendar.setTime(df.parse(calendar.get(GregorianCalendar.YEAR) + "-" + (calendar.get(GregorianCalendar.MONTH) + 1) + "-" + calendar.get(GregorianCalendar.DAY_OF_MONTH) + " " + suns[1]));
            sunsetL = calendar.getTimeInMillis();
            sunCurrentL = new Date().getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //日落减日出=总和
        sunSumL = sunsetL - sunriseL;
        //当前时间减日出=当前进度
        sunCurrentPL = sunCurrentL - sunriseL;
        //当前进度/总和=百分比
        sunP = (double) sunCurrentPL / (double) sunSumL;
        sunsetF = (float) sunP;
    }

    public static class ViewHolder {
        public TwinklingRefreshLayout refresh;
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public TextView tvDate;
        public TextView tvDegrees;
        public TextView tvWeather;
        public TextView tvNowDate;
        public TextView tvDegreesMinMax;
        public TextView tvWind;
        public TextView tvWet;
        public RecyclerView rv24Weather;
        public RecyclerView rvWeekWeather;
        public TextView tvGmzs;
        public TextView tvCyzs;
        public TextView tvKqzs;
        public TextView tvXczs;
        public TextView tvLyzs;
        public TextView tvZwxzs;
        public SunProgressBar sunBar;
        public SunProgressBarBackground sunProgressBarBackground;
        public ScrollView scrollView;
        public TextView tvSunrise;
        public TextView tvSunset;

        public ViewHolder(View rootView) {
            this.refresh = (TwinklingRefreshLayout) rootView.findViewById(R.id.refresh);
            this.rootView = rootView;
            this.imgReturn = (ImageView) rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.tvDate = (TextView) rootView.findViewById(R.id.tvDate);
            this.tvDegrees = (TextView) rootView.findViewById(R.id.tvDegrees);
            this.tvWeather = (TextView) rootView.findViewById(R.id.tvWeather);
            this.tvNowDate = (TextView) rootView.findViewById(R.id.tvNowDate);
            this.tvDegreesMinMax = (TextView) rootView.findViewById(R.id.tvDegreesMinMax);
            this.tvWind = (TextView) rootView.findViewById(R.id.tvWind);
            this.tvWet = (TextView) rootView.findViewById(R.id.tvWet);
            this.rv24Weather = (RecyclerView) rootView.findViewById(R.id.rv24Weather);
            this.rvWeekWeather = (RecyclerView) rootView.findViewById(R.id.rvWeekWeather);
            this.tvGmzs = (TextView) rootView.findViewById(R.id.tvGmzs);
            this.tvCyzs = (TextView) rootView.findViewById(R.id.tvCyzs);
            this.tvKqzs = (TextView) rootView.findViewById(R.id.tvKqzs);
            this.tvXczs = (TextView) rootView.findViewById(R.id.tvXczs);
            this.tvLyzs = (TextView) rootView.findViewById(R.id.tvLyzs);
            this.tvZwxzs = (TextView) rootView.findViewById(R.id.tvZwxzs);
            this.sunBar = (SunProgressBar) rootView.findViewById(R.id.sunBar);
            this.sunProgressBarBackground = (SunProgressBarBackground) rootView.findViewById(R.id.sunBarBackground);
            this.scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
            this.tvSunrise = (TextView) rootView.findViewById(R.id.tvSunrise);
            this.tvSunset = (TextView) rootView.findViewById(R.id.tvSunset);
        }

    }
}
