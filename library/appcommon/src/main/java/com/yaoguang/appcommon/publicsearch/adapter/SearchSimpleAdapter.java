package com.yaoguang.appcommon.publicsearch.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;

/**
 * 搜索框查询后的内容适配器
 * Created by zhongjh on 2017/6/5.
 */
public class SearchSimpleAdapter extends BaseLoadMoreRecyclerAdapter<AppPublicInfoWrapper, RecyclerView.ViewHolder> {

    private Fragment mFragment;
    private boolean mIsMultiSelect;

    public SearchSimpleAdapter(Fragment fragment, boolean isMultiSelect) {
        mFragment = fragment;
        mIsMultiSelect = isMultiSelect;
    }

    private boolean isItemChecked(int position) {
        return getList().get(position).isCheck();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvShortName;
        CheckBox checkBox;

        ItemViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvShortName = (TextView) view.findViewById(R.id.tvShortName);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        if (mIsMultiSelect) {
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    final int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        getList().get(position).setCheck(isChecked);
                    };
                }
            });
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (isItemChecked(position)) {
                        getList().get(position).setCheck(false);
                        holder.checkBox.setChecked(false);
                    } else {
                        getList().get(position).setCheck(true);
                        holder.checkBox.setChecked(true);
                    }
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

        itemViewHolder.checkBox.setChecked(isItemChecked(position));
    }

}
