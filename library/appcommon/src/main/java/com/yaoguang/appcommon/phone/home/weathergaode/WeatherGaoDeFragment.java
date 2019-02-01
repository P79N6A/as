package com.yaoguang.appcommon.phone.home.weathergaode;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.databinding.FragmentWeatherGaoDeBinding;
import com.yaoguang.appcommon.common.widget.chartview.ChartItem;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.map.common.ToastUtil;
import com.yaoguang.map.location.impl.LocationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 高德天气
 * Created by zhongjh on 2018/4/20.
 */
public class WeatherGaoDeFragment extends BaseFragmentDataBind<FragmentWeatherGaoDeBinding> {

    public static WeatherGaoDeFragment newInstance() {
        return new WeatherGaoDeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_weather_gao_de;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void init() {
        initWeather();
    }

    /**
     * 初始化天气1
     */
    private void initWeather() {
        LocationManager locationManager = new LocationManager();
        locationManager.init(BaseApplication.getInstance().getBaseContext());
        locationManager.setComeback(location -> {
            locationManager.destroyLocation();
            WeatherSearch weathersearch = new WeatherSearch(WeatherGaoDeFragment.this.getContext());
            WeatherSearchQuery query2 = new WeatherSearchQuery(location.getCity(), WeatherSearchQuery.WEATHER_TYPE_FORECAST);


            weathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                @Override
                public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {


                }

                @Override
                public void onWeatherForecastSearched(LocalWeatherForecastResult result, int i) {
                    LocalWeatherForecast forecastResult = result.getForecastResult();
                    List<LocalDayWeatherForecast> weatherForecast = forecastResult.getWeatherForecast();
                    ArrayList<ChartItem> list = new ArrayList<>();
                    ArrayList<ChartItem> list2 = new ArrayList<>();

                    initWeek(weatherForecast.get(1).getWeek(), mDataBinding.tvOneDay);
                    initWeek(weatherForecast.get(2).getWeek(), mDataBinding.tvTwoDay);
                    initWeek(weatherForecast.get(3).getWeek(), mDataBinding.tvThreeDay);
                    initDate(weatherForecast.get(1).getDate(), mDataBinding.tvOneDay2);
                    initDate(weatherForecast.get(2).getDate(), mDataBinding.tvTwoDay2);
                    initDate(weatherForecast.get(3).getDate(), mDataBinding.tvThreeDay2);

                    for (i = 0; i < weatherForecast.size(); i++) {
                        if (i > 0) {
                            list.add(new ChartItem(weatherForecast.get(i).getDayWeather(), ObjectUtils.parseFloat(weatherForecast.get(i).getDayTemp())));
                            list2.add(new ChartItem(weatherForecast.get(i).getDayWeather(), ObjectUtils.parseFloat(weatherForecast.get(i).getNightTemp())));
                        }
                    }
                    mDataBinding.chartView.setView(list, list2, "");

                    initWeather2();
                }
            });

            weathersearch.setQuery(query2);
            weathersearch.searchWeatherAsyn();
        });
    }

    /**
     * 初始化天气2
     */
    private void initWeather2() {
        LocationManager locationManager = new LocationManager();
        locationManager.init(BaseApplication.getInstance().getBaseContext());
        locationManager.setComeback(location -> {
            locationManager.destroyLocation();
            WeatherSearch weathersearch = new WeatherSearch(WeatherGaoDeFragment.this.getContext());
            WeatherSearchQuery query = new WeatherSearchQuery(location.getCity(), WeatherSearchQuery.WEATHER_TYPE_LIVE);


            weathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                @Override
                public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                    if (rCode == 1000) {
                        if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                            LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                            mDataBinding.toolbarTitle.setText(location.getCity());
                            mDataBinding.tvDate.setText(weatherlive.getReportTime());
                            mDataBinding.tvWeather.setText(weatherlive.getWeather());
                            mDataBinding.tvDegrees.setText(weatherlive.getTemperature());
                            mDataBinding.tvWindPower.setText("风力" + weatherlive.getWindPower() + "级");
                            mDataBinding.tvWindDirection.setText(weatherlive.getWindDirection() + "风");
                            mDataBinding.tvHumidity.setText("湿度" + weatherlive.getHumidity() + "%");
                        } else {
                            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有数据");
                        }

                        mDataBinding.toolbar.setVisibility(View.VISIBLE);
                        mDataBinding.refresh.setVisibility(View.VISIBLE);
                        mDataBinding.refresh.finishRefreshing();
                    }

                }

                @Override
                public void onWeatherForecastSearched(LocalWeatherForecastResult result, int i) {
                }
            });

            weathersearch.setQuery(query);
            weathersearch.searchWeatherAsyn();


        });
    }


    @Override
    public void initListener() {
        mDataBinding.refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                initWeather();
            }
        });
        mDataBinding.imgReturn.setOnClickListener(v -> pop());
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    /**
     * 格式化周几
     *
     * @param week     周几
     * @param textView 文本框
     */
    private void initWeek(String week, TextView textView) {
        switch (week) {
            case "1":
                textView.setText("星期一");
                break;
            case "2":
                textView.setText("星期二");
                break;
            case "3":
                textView.setText("星期三");
                break;
            case "4":
                textView.setText("星期四");
                break;
            case "5":
                textView.setText("星期五");
                break;
            case "6":
                textView.setText("星期六");
                break;
            case "7":
                textView.setText("星期日");
                break;
        }
    }

    /**
     * 格式化时间
     *
     * @param date     时间
     * @param textView 文本框
     */
    @SuppressLint("SetTextI18n")
    private void initDate(String date, TextView textView) {
        String[] s = date.split("-");

        textView.setText(s[1] + "/" + s[0]);
    }

}
