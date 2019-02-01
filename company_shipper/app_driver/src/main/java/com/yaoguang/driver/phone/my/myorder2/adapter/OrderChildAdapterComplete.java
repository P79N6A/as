package com.yaoguang.driver.phone.my.myorder2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.driver.R;
import com.yaoguang.driver.phone.home.adapter.OrderChildAdapter;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;

/**
 * 订单列表：已完成
 * Created by wly on 2017/5/9.
 */
public class OrderChildAdapterComplete extends OrderChildAdapter<DriverOrderWrapper> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = onCreateItemViewHolderCustom(parent);

        final ItemViewHolder holder = onCreateItemViewHolderCustom(view);
        assert view != null;
        view.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            position = position - 1;
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, getList().get(position), position);
            }
        });

        onItemBtnRegisterClick(view, holder);
        onItemBtnAcceptClick(view, holder);
        onItemBtnRefuseClick(view, holder);

        return holder;
    }

    public OrderChildAdapterComplete(Context context) {
        super(context);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindItemViewHolder((ItemViewHolderCustom) holder, getItem(position));
    }

    public class ItemViewHolderCustom extends ItemViewHolder {

        ItemViewHolderCustom(View view) {
            super(view);
        }
    }

    @Override
    public ItemViewHolder onCreateItemViewHolderCustom(View view) {
        return new ItemViewHolderCustom(view);
    }

    @Override
    public View onCreateItemViewHolderCustom(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_end, parent, false);
    }

    @Override
    public void onItemBtnRegisterClick(View view, ItemViewHolder holder) {

    }

    @Override
    public void onItemBtnAcceptClick(View view, ItemViewHolder holder) {

    }

    @Override
    public void onItemBtnRefuseClick(View view, ItemViewHolder holder) {

    }

}
