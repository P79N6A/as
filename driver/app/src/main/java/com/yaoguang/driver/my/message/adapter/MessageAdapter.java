package com.yaoguang.driver.my.message.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.common.Glide.impl.GlideManager;
import com.yaoguang.common.adapter.AllSelectAdapter;
import com.yaoguang.common.appcommon.utils.AllSelectDelete;
import com.yaoguang.greendao.entity.MessageInfo;

/**
 * Project Name:driver
 * Created by weiliying
 * on 2017 2017/4/21.9:51
 */

public class MessageAdapter extends AllSelectAdapter {
    private OnRecyclerViewItemClickListener onRVItemClickListener;
    private Context mContext;
    private boolean isHomePage;

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        final ItemViewHolder viewHolder = new ItemViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                onRVItemClickListener.onItemClick(view, MessageAdapter.this.getList().get(position), position);
            }
        });

        viewHolder.ivCancelThreeDot.setOnClickListener(v -> {
            final int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                onRVItemClickListener.onItemIgnoreClick(view, MessageAdapter.this.getList().get(position), position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final MessageInfo msg = (MessageInfo) getList().get(position);
        if (msg != null) {
            if (!TextUtils.isEmpty(msg.getTitle()))
                itemViewHolder.messageTitle.setText(msg.getTitle());
            if (!TextUtils.isEmpty(msg.getContent()))
                itemViewHolder.messageContent.setText(msg.getContent());
            if (!TextUtils.isEmpty(msg.getCreated()))
                itemViewHolder.messageTime.setText(msg.getCreated());
            if (!TextUtils.isEmpty(msg.getPlatformName()))
                itemViewHolder.tvTitle2.setText(msg.getPlatformName());
            if (!TextUtils.isEmpty(msg.getPlatformPhone())) {
                itemViewHolder.phone.setOnClickListener(v -> {
                    if (onRVItemClickListener != null) {
                        onRVItemClickListener.callPhone(msg.getPlatformPhone());
                    }
                }
                );
            }
            if (!TextUtils.isEmpty(msg.getPlatformLogo()))
                GlideManager.getInstance().withRounded(mContext, msg.getPlatformLogo(), itemViewHolder.ivLogo);

            if (msg.getFlag() == 0)
                itemViewHolder.messageFlag.setVisibility(View.VISIBLE);
            else
                itemViewHolder.messageFlag.setVisibility(View.GONE);
        }

        if (isHomePage()) {
            itemViewHolder.flSelect.setVisibility(View.GONE);
            itemViewHolder.ivCancelThreeDot.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.ivCancelThreeDot.setVisibility(View.GONE);
            AllSelectDelete.adappterSetting(this, itemViewHolder.flSelect, itemViewHolder.llView, itemViewHolder.cbSelect, holder);
        }
    }

    public void setOnRVItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRVItemClickListener = onRecyclerViewItemClickListener;
    }

    private boolean isHomePage() {
        return isHomePage;
    }

    public void setHomePage(boolean homePage) {
        isHomePage = homePage;
    }

    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View itemView, T item, int position);

        void onItemIgnoreClick(View itemView, T item, int position);

        void callPhone(String phone);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle2;
        private ImageView messageFlag;
        private ImageView ivLogo;
        private ImageView ivCancelThreeDot;
        private TextView messageTime;
        private ImageView phone;
        private TextView messageTitle;
        private TextView messageContent;
        private FrameLayout flSelect;
        private CheckBox cbSelect;
        private LinearLayout llView;

        ItemViewHolder(View view) {
            super(view);
            this.flSelect = view.findViewById(R.id.flSelect);
            llView = view.findViewById(R.id.llView);
            this.cbSelect = view.findViewById(R.id.cbSelect);
            this.messageContent = view.findViewById(R.id.messageContent);
            this.messageTitle = view.findViewById(R.id.messageTitle);
            this.phone = view.findViewById(R.id.phone);
            this.ivCancelThreeDot = view.findViewById(R.id.ivCancelThreeDot);
            this.messageTime = view.findViewById(R.id.messageTime);
            this.messageFlag = view.findViewById(R.id.messageFlag);
            this.ivLogo = view.findViewById(R.id.ivLogo);
            this.tvTitle2 = view.findViewById(R.id.tvTitle2);
        }
    }
}
