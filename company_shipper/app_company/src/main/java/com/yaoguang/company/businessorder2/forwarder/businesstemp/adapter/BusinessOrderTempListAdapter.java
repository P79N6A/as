package com.yaoguang.company.businessorder2.forwarder.businesstemp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by zhongjh on 2018/10/29.
 */
public class BusinessOrderTempListAdapter extends BaseLoadMoreRecyclerAdapter<AppPriceAnalysisWrapper, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order_temp, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, getItem(adapterPosition), adapterPosition);
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvName;
        public RelativeLayout rlAdd;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvName = rootView.findViewById(R.id.tvName);
            this.rlAdd = rootView.findViewById(R.id.rlAdd);
        }

    }
}
