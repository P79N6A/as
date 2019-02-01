package com.yaoguang.company.my.usermanagement.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.user.ViewUser;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by zhongjh on 2018/12/12.
 */

public class UserManagementAdapter extends BaseLoadMoreRecyclerAdapter<ViewUser, RecyclerView.ViewHolder> {

    private Context mContext;

    public UserManagementAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_management, parent, false);
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

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        ViewUser viewUser = getItem(position);

        // 判断两个状态
        switch (viewUser.getUserType()) {
            case 0:
                itemViewHolder.tvType.setBackground(mContext.getResources().getDrawable(R.drawable.background_shape_2_5_violet_violet));
                itemViewHolder.tvType.setText("总公司用户");
                break;
            case 2:
                itemViewHolder.tvType.setBackground(mContext.getResources().getDrawable(R.drawable.background_shape_2_5_green_green));
                itemViewHolder.tvType.setText("管理员");
                break;
            case 3:
                itemViewHolder.tvType.setBackground(mContext.getResources().getDrawable(R.drawable.background_shape_2_5_blue_blue));
                itemViewHolder.tvType.setText("普通用户");
                break;
        }
        switch (viewUser.getUsable()) {
            case 0:
                itemViewHolder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.background_shape_2_5_red_red));
                itemViewHolder.tvStatus.setText("不可用");
                break;
            case 1:
                itemViewHolder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.background_shape_2_5_primary_primary));
                itemViewHolder.tvStatus.setText("可用");
                break;
        }
        itemViewHolder.tvName.setText(viewUser.getName());
        itemViewHolder.tvMechanism.setText(viewUser.getLoginName());
        itemViewHolder.tvRoleName.setText(viewUser.getRoleName());
        itemViewHolder.tvMechanismName.setText(viewUser.getEnterpriseShortName());

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvName;
        public TextView tvStatus;
        public TextView tvType;
        public TextView tvMechanism;
        public TextView tvRoleName;
        public TextView tvMechanismName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvName = (TextView) rootView.findViewById(R.id.tvName);
            this.tvStatus = (TextView) rootView.findViewById(R.id.tvStatus);
            this.tvType = (TextView) rootView.findViewById(R.id.tvType);
            this.tvMechanism = (TextView) rootView.findViewById(R.id.tvMechanism);
            this.tvRoleName = (TextView) rootView.findViewById(R.id.tvRoleName);
            this.tvMechanismName = (TextView) rootView.findViewById(R.id.tvMechanismName);
        }

    }
}
