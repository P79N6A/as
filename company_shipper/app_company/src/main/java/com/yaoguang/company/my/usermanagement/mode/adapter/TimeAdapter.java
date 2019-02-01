package com.yaoguang.company.my.usermanagement.mode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.lib.adapter.BaseExpandRecyclerAdapter;

/**
 * 登录时间方案
 * Created by zhongjh on 2018/12/12.
 */
public class TimeAdapter extends BaseExpandRecyclerAdapter<String[], TimeAdapter.ViewHolder> {

    @Override
    public void onBindItemExpandViewHolder(ViewHolder holder, int position) {
        holder.tvDay.setText(getItem(position)[0]);
        holder.tvDate.setText(getItem(position)[1]);
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanagement_mode_time, parent, false);
        return new ViewHolder(view);
    }


    public static class ViewHolder extends BaseExpandRecyclerAdapter.ViewHolder {
        public View rootView;
        public TextView tvDay;
        public TextView tvDate;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvDay = (TextView) rootView.findViewById(R.id.tvDay);
            this.tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        }

    }
}
