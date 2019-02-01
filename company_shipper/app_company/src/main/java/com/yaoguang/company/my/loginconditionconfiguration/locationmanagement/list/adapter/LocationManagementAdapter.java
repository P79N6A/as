package com.yaoguang.company.my.loginconditionconfiguration.locationmanagement.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 *
 * Created by zhongjh on 2018/12/6.
 */
public class LocationManagementAdapter extends BaseLoadMoreRecyclerAdapter<UserLoginAllowLocation, RecyclerView.ViewHolder> {

    //点击事件
    public interface OnItemDeleteClick {
        void onItemDeleteClick(View itemView, UserLoginAllowLocation item, int position);
    }

    protected OnItemDeleteClick mOnItemDeleteClickListener = null;

    public void setOnItemDeleteClick(OnItemDeleteClick listener) {
        this.mOnItemDeleteClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location_management, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.llEdit.setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(viewHolder.rootView, getItem(adapterPosition), adapterPosition);
            }
        });
        viewHolder.llDelete.setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mOnItemDeleteClickListener.onItemDeleteClick(viewHolder.rootView, getItem(adapterPosition), adapterPosition);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        UserLoginAllowLocation userLoginAllowLocation = getItem(position);
        itemViewHolder.tvAddress.setText(userLoginAllowLocation.getAddress());
        itemViewHolder.tvRadius.setText("附近" + userLoginAllowLocation.getRadius() + "M");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView tvAddress;
        public TextView tvRadius;
        public LinearLayout llEdit;
        public LinearLayout llDelete;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvAddress = (TextView) rootView.findViewById(R.id.tvAddress);
            this.tvRadius = (TextView) rootView.findViewById(R.id.tvRadius);
            this.llEdit = (LinearLayout) rootView.findViewById(R.id.llEdit);
            this.llDelete = (LinearLayout) rootView.findViewById(R.id.llDelete);
        }

    }
}
