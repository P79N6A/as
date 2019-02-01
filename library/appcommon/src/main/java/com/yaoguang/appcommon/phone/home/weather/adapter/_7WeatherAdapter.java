package com.yaoguang.appcommon.phone.home.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.greendao.entity.AliWeatherWeek;

import java.util.List;

/**
 * 一周预报
 * Created by zhongjh on 2017/9/7.
 */
public class _7WeatherAdapter extends RecyclerView.Adapter<_7WeatherAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<AliWeatherWeek> mDatas;

    public _7WeatherAdapter(Context context, List<AliWeatherWeek> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView imgDayWeatherImage;
        TextView tvStrWeekDay;
        TextView tvDay;
        TextView tvDayWeather;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_7weather,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.imgDayWeatherImage = (ImageView) view
                .findViewById(R.id.imgDayWeatherImage);
        viewHolder.tvStrWeekDay = (TextView) view
                .findViewById(R.id.tvStrWeekDay);
        viewHolder.tvDay = (TextView) view
                .findViewById(R.id.tvDay);
        viewHolder.tvDayWeather = (TextView) view
                .findViewById(R.id.tvDayWeather);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.imgDayWeatherImage.setImageResource(mDatas.get(i).getDayWeatherImage());
        viewHolder.tvStrWeekDay.setText(mDatas.get(i).getStrWeekDay());
        viewHolder.tvDay.setText(mDatas.get(i).getDay());
        viewHolder.tvDayWeather.setText(mDatas.get(i).getDayWeather());
    }


}
