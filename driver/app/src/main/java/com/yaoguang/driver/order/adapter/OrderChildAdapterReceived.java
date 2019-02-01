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
 * 订单列表：已接单
 * Created by wly on 2017/5/9.
 */

public class OrderChildAdapterReceived extends OrderChildAdapter {

    private View mView;

    public OrderChildAdapterReceived(Context context) {
        super(context);
    }

    public class ItemViewHolderCustom extends ItemViewHolder {
        private Button btnRegister;

        ItemViewHolderCustom(View view) {
            super(view);
            mView = view;
            btnRegister = view.findViewById(R.id.btnRegister);
        }
    }

    @Override
    public ItemViewHolder onCreateItemViewHolderCustom(View view) {
        return new ItemViewHolderCustom(view);
    }

    @Override
    public View onCreateItemViewHolderCustom(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_received, parent, false);
    }

    @Override
    public void onItemBtnRegisterClick(final View view, final ItemViewHolder holder) {
        view.findViewById(R.id.btnRegister).setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemBtnRegisterClickListener.onItemBtnRegisterClick(view, OrderChildAdapterReceived.this.getList().get(position), position);
            }
        });
    }

    @Override
    public void onItemBtnAcceptClick(View view, ItemViewHolder holder) {

    }

    @Override
    public void onItemBtnRefuseClick(View view, ItemViewHolder holder) {

    }

    @Override
    public void onBindItemViewHolderCustom(ItemViewHolder itemViewHolder, int position) {
        ItemViewHolderCustom itemViewHolderCustom = (ItemViewHolderCustom) itemViewHolder;
        //判断状态
        Order order = getList().get(position);
        if (order.getVehicleFlag() == 1) {
            itemViewHolderCustom.btnRegister.setBackgroundColor(mView.getResources().getColor(R.color.refuse));
            itemViewHolderCustom.btnRegister.setText("进度更新");
        } else {
            itemViewHolderCustom.btnRegister.setBackgroundColor(mView.getResources().getColor(R.color.received));
            itemViewHolderCustom.btnRegister.setText("现在出车");
        }
    }

    //按钮点击事件
    public interface OnRecyclerViewItemClickListener<Order> {
        void onItemBtnRegisterClick(View itemView, Order item, int position);
    }

    private OnRecyclerViewItemClickListener<Order> mOnItemBtnRegisterClickListener = null;

    public void setOnItemBtnRegisterClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemBtnRegisterClickListener = listener;
    }


}
