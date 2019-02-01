package com.yaoguang.appcommon.search2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

/**
 * 搜索框查询后的内容适配器
 * Created by zhongjh on 2017/6/5.
 */
public class SearchSimpleAdapter extends BaseLoadMoreRecyclerAdapter<AppPublicInfoWrapper, RecyclerView.ViewHolder> {


    private boolean isItemChecked(int position) {
        return getList().get(position).isCheck();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvShortName;

        ItemViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvShortName = (TextView) view.findViewById(R.id.tvShortName);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_simple, parent, false);
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
        itemViewHolder.tvName.setText(getList().get(position).getFullName());

        if (ObjectUtils.parseString(getList().get(position).getShortName()).equals("")) {
            itemViewHolder.tvShortName.setVisibility(View.GONE);
        } else {
            itemViewHolder.tvShortName.setVisibility(View.VISIBLE);
            itemViewHolder.tvShortName.setText(getList().get(position).getShortName());
        }
    }

}
