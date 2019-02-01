package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.list.WiFiManagementFragment;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginWlan;

import java.util.List;

/**
 * Created by zhongjh on 2018/12/19.
 */

public class WiFiManagementChildAdapter extends RecyclerView.Adapter<WiFiManagementChildAdapter.ViewHolder> {

    private Context context;
    private List<UserLoginWlan> list; // 数据源

    //点击事件
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View itemView);
    }

    protected OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    WiFiManagementChildAdapter(Context context, List<UserLoginWlan> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return WiFiManagementFragment.CHILD_VIEW_TYPE;
    }

    public void setData(List<UserLoginWlan> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wifi_management_child, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.rootView.setOnClickListener(v -> {
//            int adapterPosition = viewHolder.getAdapterPosition();
//            if (adapterPosition != RecyclerView.NO_POSITION) {
//                mOnItemClickListener.onItemClick(viewHolder.rootView);
//            }
//        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserLoginWlan info = list.get(position);
        holder.tvOneTitle.setText(info.getName());
        holder.tvOneContent.setText(info.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvOneTitle;
        public TextView tvOneContent;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvOneTitle = (TextView) rootView.findViewById(R.id.tvOneTitle);
            this.tvOneContent = (TextView) rootView.findViewById(R.id.tvOneContent);
        }

    }
}