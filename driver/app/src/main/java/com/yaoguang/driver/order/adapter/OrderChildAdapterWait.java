package com.yaoguang.driver.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.Order;

/**
 * 订单列表：待接单
 * Created by wly on 2017/5/9.
 */

public class OrderChildAdapterWait extends OrderChildAdapter {

    private View mView;

    public OrderChildAdapterWait(Context context) {
        super(context);
    }

    public class ItemViewHolderCustom extends ItemViewHolder {
        Button btnAccept;
        Button btnRefuse;

        ItemViewHolderCustom(View view) {
            super(view);
            mView = view;
            btnAccept = view.findViewById(R.id.btnAccept);
            btnRefuse = view.findViewById(R.id.btnRefuse);
        }
    }

    @Override
    public ItemViewHolder onCreateItemViewHolderCustom(View view) {
        return new ItemViewHolderCustom(view);
    }

    @Override
    public View onCreateItemViewHolderCustom(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_wait, parent, false);
    }

    @Override
    public void onItemBtnRegisterClick(View view, ItemViewHolder holder) {

    }

    @Override
    public void onItemBtnAcceptClick(final View view, final ItemViewHolder holder) {
        view.findViewById(R.id.btnAccept).setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
                mOnRecyclerViewItemClickListener.onItemBtnAcceptClick(view, OrderChildAdapterWait.this.getList().get(position), position);
        });
    }

    @Override
    public void onItemBtnRefuseClick(final View view, final ItemViewHolder holder) {
        view.findViewById(R.id.btnRefuse).setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
                mOnRecyclerViewItemClickListener.onItemBtnRefuseClick(view, OrderChildAdapterWait.this.getList().get(position), position);
        });
    }

    @Override
    public void onBindItemViewHolderCustom(ItemViewHolder itemViewHolder, int position) {
        ItemViewHolderCustom itemViewHolderCustom = (ItemViewHolderCustom) itemViewHolder;
        Order order = getItem(position);
        // 能否拒单（0:非可拒 1:可拒）
        if (order.getRefusable() == null || order.getRefusable() == 0)
            itemViewHolderCustom.btnRefuse.setVisibility(View.GONE);
        else itemViewHolderCustom.btnRefuse.setVisibility(View.VISIBLE);
    }

    //按钮点击事件
    public interface OnRecyclerViewItemClickListener<Order> {
        void onItemBtnAcceptClick(View itemView, Order item, int position);

        void onItemBtnRefuseClick(View itemView, Order item, int position);
    }

    private OnRecyclerViewItemClickListener<Order> mOnRecyclerViewItemClickListener = null;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnRecyclerViewItemClickListener = listener;
    }


}
