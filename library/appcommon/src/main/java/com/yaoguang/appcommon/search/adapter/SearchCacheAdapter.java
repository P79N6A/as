package com.yaoguang.appcommon.search.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;

/**
 * 搜索历史适配器
 * Created by zhongjh on 2017/6/5.
 */
public class SearchCacheAdapter extends BaseLoadMoreRecyclerAdapter<String, RecyclerView.ViewHolder> {


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        ItemViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_cache, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(v, getList().get(position), position);
                }
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
