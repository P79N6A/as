package com.yaoguang.boss.testlist.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.boss.R;
import com.yaoguang.boss.databinding.ViewTextviewBinding;
import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class TestAdapter extends BaseLoadMoreRecyclerAdapter<String, RecyclerView.ViewHolder> {


    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_textview, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.binding.tv.setText("ok");
        holder.binding.executePendingBindings();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewTextviewBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
