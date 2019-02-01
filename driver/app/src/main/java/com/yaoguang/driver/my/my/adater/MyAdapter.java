package com.yaoguang.driver.my.my.adater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.MyItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 韦理英
 * on 2017/6/12 0012.
 */

public class MyAdapter extends BaseLoadMoreRecyclerAdapter<MyItem, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_item, parent, false);
        MyAdapter.ViewHolder holder = new MyAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(view, getItem(adapterPosition), adapterPosition);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        MyItem myItem = getList().get(position);

        holder.tvName.setText(myItem.getTitle());
        holder.ivIcon.setImageResource(myItem.getIcon());
        if (position == 1 || position == 3 || position == 4) {
            holder.dividers.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.GONE);
        } else if (position != 4) {
            holder.dividers.setVisibility(View.GONE);
            holder.line.setVisibility(View.VISIBLE);
        } else {
            holder.line.setVisibility(View.GONE);
        }

        // 设置红点
        if (myItem.getValue() != null) {
            if (myItem.getTitle().contains("业务消息")) {
                holder.tvUnreadCount.setText(myItem.getValue());
            } else if (myItem.getTitle().contains("平台公告")) {
                holder.tvUnreadCount.setText(myItem.getValue());
            }
            holder.flHongDian.setVisibility(View.VISIBLE);
        } else {
            holder.flHongDian.setVisibility(View.GONE);
        }
        holder.tvValue.setText("");
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_item)
        LinearLayout item;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvValue)
        TextView tvValue;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.dividers)
        View dividers;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.tvUnreadCount)
        TextView tvUnreadCount;
        @BindView(R.id.flHongDian)
        FrameLayout flHongDian;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
