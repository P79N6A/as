package com.yaoguang.driver.my.myorder.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;


import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.UserOffice;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 韦理英
 * on 2017/7/7 0007.
 */

public class OrderStatisticsMenuAdapter extends BaseLoadMoreRecyclerAdapter<UserOffice, RecyclerView.ViewHolder> {

    public OrderStatisticsMenuAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_checkbox, null);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        UserOffice userOffice = getList().get(position);
        if (userOffice == null) return;
        itemViewHolder.cbTitle.setText(getName(position));
        itemViewHolder.cbTitle.setChecked(userOffice.isSelect());

        itemViewHolder.cbTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(itemViewHolder.cbTitle, getItem(position), position);
            }
        });
    }

    private String getName(int position) {
        String name = null;
        if (!TextUtils.isEmpty(getList().get(position).getName())) {
            name = getList().get(position).getName();
        } else {
            name = getList().get(position).getShortName();
        }
        return name;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cbTitle)
        CheckBox cbTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
