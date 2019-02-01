package com.yaoguang.driver.phone.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;

/**
 * 订单列表：待接单
 * Created by wly on 2017/5/9.
 */

public class HomeOrderChildAdapterWait extends OrderChildAdapter<DriverOrderMsgWrapper> {

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    public HomeOrderChildAdapterWait(Context context) {
        super(context);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolderCustom itemViewHolderCustom = (ItemViewHolderCustom) holder;
        DriverOrderMsgWrapper driverOrderMsgWrapper = getItem(position);

        bindItemViewHolder(itemViewHolderCustom,driverOrderMsgWrapper);

        //  语音播报
        if (driverOrderMsgWrapper != null && !TextUtils.isEmpty(driverOrderMsgWrapper.getVoice())) {
            // 有语音播报
            itemViewHolderCustom.tvVoice.setVisibility(View.VISIBLE);
            itemViewHolderCustom.tvLookButton.setVisibility(View.VISIBLE);
            itemViewHolderCustom.tvVoice_line.setVisibility(View.VISIBLE);
        } else {    // 没有语音播报
            itemViewHolderCustom.tvLookButton.setVisibility(View.VISIBLE);
            itemViewHolderCustom.tvVoice.setVisibility(View.GONE);
            itemViewHolderCustom.tvVoice_line.setVisibility(View.GONE);
        }
    }

    @Override
    public ItemViewHolder onCreateItemViewHolderCustom(View view) {
        return new ItemViewHolderCustom(view);
    }

    @Override
    public View onCreateItemViewHolderCustom(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_home, parent, false);
    }

    @Override
    public void onItemBtnRegisterClick(View view, ItemViewHolder holder) {

    }

    @Override
    public void onItemBtnAcceptClick(View view, ItemViewHolder holder) {
    }

    @Override
    public void onItemBtnRefuseClick(final View view, final ItemViewHolder holder) {
        ItemViewHolderCustom itemViewHolderCustom = (ItemViewHolderCustom) holder;

        // 语音播报监听
        itemViewHolderCustom.tvVoice.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnRecyclerViewItemClickListener.onItemBtnVoiceClick(view, getList().get(position), position);
            }
        });
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnRecyclerViewItemClickListener = listener;
    }



    //按钮点击事件
    public interface OnRecyclerViewItemClickListener {
        void onItemBtnVoiceClick(View itemView, DriverOrderMsgWrapper item, int position);
    }

    public class ItemViewHolderCustom extends ItemViewHolder {
        TextView tvLookButton;
        TextView tvVoice;
        View tvVoice_line;

        ItemViewHolderCustom(View view) {
            super(view);
            tvVoice_line = view.findViewById(R.id.tvVoice_line);
            tvVoice = view.findViewById(R.id.tvVoice);
            tvLookButton = view.findViewById(R.id.tvLookButton);
        }
    }
}
