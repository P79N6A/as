package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.mode;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginWlan;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by zhongjh on 2018/10/31.
 */
public class WiFiManagementModeAdapter extends BaseLoadMoreRecyclerAdapter<UserLoginWlan, WiFiManagementModeAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi_management_mode, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.imgDelete.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            remove(position);
            notifyDataSetChanged();
        });
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(getItem(position).getName());
        holder.tvContent.setText(getItem(position).getAddress());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public View vClick;
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView imgDelete;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.vClick = (View) rootView.findViewById(R.id.vClick);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
            this.imgDelete = (ImageView) rootView.findViewById(R.id.imgDelete);
        }

    }
}
