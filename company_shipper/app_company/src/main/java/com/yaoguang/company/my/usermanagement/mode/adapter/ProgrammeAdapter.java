package com.yaoguang.company.my.usermanagement.mode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.lib.adapter.BaseExpandRecyclerAdapter;

/**
 * Created by zhongjh on 2018/12/12.
 */
public class ProgrammeAdapter extends BaseExpandRecyclerAdapter<String,ProgrammeAdapter.ViewHolder> {

    @Override
    public void onBindItemExpandViewHolder(ViewHolder holder, int position) {
        holder.tvOneDay.setText(getItem(position));
        holder.tvOneDate.setText(getItem(position));
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_programme, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends BaseExpandRecyclerAdapter.ViewHolder {

        public View rootView;
        public TextView tvOneDay;
        public TextView tvOneDate;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvOneDay = (TextView) rootView.findViewById(R.id.tvOneDay);
            this.tvOneDate = (TextView) rootView.findViewById(R.id.tvOneDate);
        }

    }
}
