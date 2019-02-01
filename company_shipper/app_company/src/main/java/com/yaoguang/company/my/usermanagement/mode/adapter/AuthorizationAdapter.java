package com.yaoguang.company.my.usermanagement.mode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAuthDevice;
import com.yaoguang.lib.adapter.BaseExpandRecyclerAdapter;

/**
 * 授权
 * Created by zhongjh on 2018/12/14.
 */
public class AuthorizationAdapter extends BaseExpandRecyclerAdapter<UserLoginAuthDevice, AuthorizationAdapter.ViewHolder> {

    @Override
    public void onBindItemExpandViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(getItem(position).getDevice());
        holder.tvContent.setText(getItem(position).getOperator() + "于" + getItem(position).getCreated() + "授权");
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanagement_mode_authorization, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        // 点击事件
        viewHolder.rootView.setOnClickListener(v -> {
            final int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, getItem(position), position);
            }
        });
        return viewHolder;

    }

    public static class ViewHolder extends BaseExpandRecyclerAdapter.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvContent;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
        }

    }
}
