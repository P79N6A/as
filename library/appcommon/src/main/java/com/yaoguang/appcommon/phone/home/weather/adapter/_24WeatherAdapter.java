package com.yaoguang.appcommon.phone.home.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.phone.home.weather.WeatherUtils;
import com.yaoguang.greendao.entity.F1Bean;

import java.util.List;

/**
 * Created by zhongjh on 2017/9/7.
 */
public class _24WeatherAdapter extends RecyclerView.Adapter<_24WeatherAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<F1Bean._$3hourForcastBeanXX> mDatas;

    public _24WeatherAdapter(Context context, List<F1Bean._$3hourForcastBeanXX> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView imgWeather;
        TextView tvHour;
        TextView tvTemperature;
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
        View view = mInflater.inflate(R.layout.item_24weather,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.imgWeather = (ImageView) view
                .findViewById(R.id.imgWeather);
        viewHolder.tvHour = (TextView) view
                .findViewById(R.id.tvHour);
        viewHolder.tvTemperature = (TextView) view
                .findViewById(R.id.tvTemperature);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.imgWeather.setImageResource(new WeatherUtils().getImage(mDatas.get(i).getWeather(),mDatas.get(i).isNight()));
        viewHolder.tvHour.setText(mDatas.get(i).getHour());
        viewHolder.tvTemperature.setText(mDatas.get(i).getTemperature());
    }



}
