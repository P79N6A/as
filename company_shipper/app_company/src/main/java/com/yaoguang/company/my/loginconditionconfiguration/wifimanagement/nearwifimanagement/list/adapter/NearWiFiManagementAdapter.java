package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.nearwifimanagement.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlan;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/12/6.
 */

public class NearWiFiManagementAdapter extends BaseLoadMoreRecyclerAdapter<UserRecentlyUsedWlan, RecyclerView.ViewHolder> {

    //点击事件
    public interface OnItemCheckClick {
        void onItemCheckClick(View itemView, UserRecentlyUsedWlan item, int position);
    }

    protected OnItemCheckClick mOnItemCheckClickListener = null;

    public void setOnItemCheckClick(OnItemCheckClick listener) {
        this.mOnItemCheckClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_near_wifi_usermanagement, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.vClick.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (!list.get(position).isCheck()) {
                list.get(position).setCheck(true);
            } else {
                list.get(position).setCheck(false);
            }
            notifyDataSetChanged();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemCheckClickListener.onItemCheckClick(holder.rootView, getItem(position), position);
            }
        });
        // 点击事件
        holder.rootView.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, getItem(position), position);
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        UserRecentlyUsedWlan myItem = getList().get(position);

        viewHolder.tvTitle.setText(myItem.getName());
        viewHolder.tvMac.setText(myItem.getAddress());
        viewHolder.tvContent.setText("最后提交时间:" + myItem.getLastUsedTime() );

        if (list.get(position).isCheck()) {
            viewHolder.imgCheck.setImageResource(R.drawable.ic_big_bossyellow);
            viewHolder.imgCheck.setTag("1");
        } else {
            viewHolder.imgCheck.setImageResource(R.drawable.ic_big_bossempty);
            viewHolder.imgCheck.setTag("0");
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgCheck;
        public View vClick;
        public TextView tvTitle;
        public TextView tvMac;
        public TextView tvContent;
        public ImageView imgDetail;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgCheck = (ImageView) rootView.findViewById(R.id.imgCheck);
            this.vClick = (View) rootView.findViewById(R.id.vClick);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvMac = (TextView) rootView.findViewById(R.id.tvMac);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
            this.imgDetail = (ImageView) rootView.findViewById(R.id.imgDetail);
        }

    }
}
