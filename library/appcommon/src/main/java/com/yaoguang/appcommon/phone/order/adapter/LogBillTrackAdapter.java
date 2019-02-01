package com.yaoguang.appcommon.phone.order.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.greendao.entity.AppLogBillTrackWrapper;

/**
 * 货柜动态 - 右侧的
 * Created by zhongjh on 2017/6/21.
 */
public class LogBillTrackAdapter extends BaseLoadMoreRecyclerAdapter<AppLogBillTrackWrapper, RecyclerView.ViewHolder> {

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public View vTop;
        public View vBottom;
        public TextView tvTitle;
        public TextView textView4;
        public ImageView imgHeidian;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.vTop = rootView.findViewById(R.id.vTop);
            this.vBottom = rootView.findViewById(R.id.vBottom);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.textView4 = rootView.findViewById(R.id.textView4);
            this.imgHeidian = rootView.findViewById(R.id.imgHeidian);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logbilltrack, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppLogBillTrackWrapper appLogBillTrackWrapperPrevious = null;
        if (position > 0)
            appLogBillTrackWrapperPrevious = getList().get(position - 1);
        AppLogBillTrackWrapper appLogBillTrackWrapper = getList().get(position);
        if (position == 0) {
            itemViewHolder.vTop.setVisibility(View.INVISIBLE);
        } else if (position == getList().size() - 1) {
            itemViewHolder.vBottom.setVisibility(View.INVISIBLE);
        } else {
            itemViewHolder.vTop.setVisibility(View.VISIBLE);
            itemViewHolder.vBottom.setVisibility(View.VISIBLE);
        }
        if (appLogBillTrackWrapper.getStatus() == 1) {
            itemViewHolder.vBottom.setBackgroundColor(ContextCompat.getColor(itemViewHolder.vBottom.getContext(), R.color.primary));
        } else {
            itemViewHolder.vBottom.setBackgroundColor(ContextCompat.getColor(itemViewHolder.vBottom.getContext(), R.color.grey900));
        }
        if (appLogBillTrackWrapperPrevious != null && appLogBillTrackWrapperPrevious.getStatus() == 1) {
            itemViewHolder.vTop.setBackgroundColor(ContextCompat.getColor(itemViewHolder.vBottom.getContext(), R.color.primary));
        } else {
            itemViewHolder.vTop.setBackgroundColor(ContextCompat.getColor(itemViewHolder.vBottom.getContext(), R.color.grey900));
        }
        if (appLogBillTrackWrapper.getStatus() == 1) {
            itemViewHolder.imgHeidian.setImageResource(R.drawable.ic_huangdian);
        } else {
            itemViewHolder.imgHeidian.setImageResource(R.drawable.ic_huidian);
        }

        itemViewHolder.tvTitle.setText(appLogBillTrackWrapper.getName());
        itemViewHolder.textView4.setText(appLogBillTrackWrapper.getTime());
    }

}
