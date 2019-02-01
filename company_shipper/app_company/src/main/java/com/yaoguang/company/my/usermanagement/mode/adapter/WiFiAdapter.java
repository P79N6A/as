package com.yaoguang.company.my.usermanagement.mode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginWlan;
import com.yaoguang.lib.adapter.BaseExpandRecyclerAdapter;

/**
 * WiFi
 * Created by zhongjh on 2018/12/14.
 */
public class WiFiAdapter extends BaseExpandRecyclerAdapter<UserLoginWlan, WiFiAdapter.ViewHolder> {

    @Override
    public void onBindItemExpandViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(getItem(position).getName());
        holder.tvContent.setText(getItem(position).getAddress());
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanagement_mode_wifi, parent, false);
        return new ViewHolder(view);
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
