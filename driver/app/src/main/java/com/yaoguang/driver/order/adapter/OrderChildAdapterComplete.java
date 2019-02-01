package com.yaoguang.driver.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.driver.R;

/**
 * 订单列表：已完成
 * Created by wly on 2017/5/9.
 */

public class OrderChildAdapterComplete extends OrderChildAdapter {

    public OrderChildAdapterComplete(Context context) {
        super(context);
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

    @Override
    public void onBindItemViewHolderCustom(ItemViewHolder itemViewHolder, int position) {
//        ItemViewHolderCustom itemViewHolderCustom = (ItemViewHolderCustom) itemViewHolder;
        //  已完成
//        Order order = getList().get(position);
//        itemViewHolderCustom.tvCreated.setText(getCreated(itemViewHolder.tvCreated, order.getFinishTime()));
    }

}
