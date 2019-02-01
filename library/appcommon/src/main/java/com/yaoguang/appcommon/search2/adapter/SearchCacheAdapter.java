package com.yaoguang.appcommon.search2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索历史适配器
 * Created by zhongjh on 2017/6/5.
 */
public class SearchCacheAdapter extends BaseLoadMoreRecyclerAdapter<String, RecyclerView.ViewHolder> {

    public SearchCacheAdapter(List<String> datas){
        this.list = datas;
    }

    //点击事件
    public interface OnRecyclerViewItemDeleteClickListener {
        void onItemClick(View itemView, int position);
    }

    protected OnRecyclerViewItemDeleteClickListener mOnItemDeleteClickListener = null;

    public void setOnItemDeleteClickListener(OnRecyclerViewItemDeleteClickListener listener) {
        this.mOnItemDeleteClickListener = listener;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgIsCheck;

        ItemViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            imgIsCheck = view.findViewById(R.id.imgIsCheck);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_cache2, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        // 点击
        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(v, getList().get(position), position);
                }
            }
        });
        // 删除
        view.findViewById(R.id.imgIsCheck).setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemDeleteClickListener.onItemClick(v,  position);
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvName.setText(getList().get(position));
    }

}
