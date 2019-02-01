package com.yaoguang.company.my.usermanagement.mode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.lib.adapter.BaseExpandRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 授权
 * Created by zhongjh on 2018/12/14.
 */
public class AddressAdapter extends BaseExpandRecyclerAdapter<UserLoginAllowLocation, AddressAdapter.ViewHolder> {

    @Override
    public void onBindItemExpandViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(getItem(position).getAddress());
        holder.tvContent.setText("附近" + ObjectUtils.parseString(getItem(position).getRadius()) + "m");
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanagement_mode_address, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rootView.setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(viewHolder.rootView, getItem(adapterPosition), adapterPosition);
            }
        });
        viewHolder.tvDelete.setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            remove(adapterPosition);
            notifyDataSetChanged();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mOnItemLongClickListener.onItemClick(viewHolder.rootView, getItem(adapterPosition), adapterPosition);
            }
        });
        return viewHolder;
    }


    public static class ViewHolder extends BaseExpandRecyclerAdapter.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvDelete;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
            this.tvDelete = (TextView) rootView.findViewById(R.id.tvDelete);
        }

    }
}
