package com.yaoguang.company.my.usermanagement.mode.address.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.ViewTransferCancelDetail;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 机构条件的适配器
 * Created by zhongjh on 2018/10/31.
 */
public class AddressManagementAdapter extends BaseLoadMoreRecyclerAdapter<UserLoginAllowLocation, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanagement_address, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.vClick.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (!list.get(position).isCheck()) {
                list.get(position).setCheck(true);
            } else {
                list.get(position).setCheck(false);
            }
            notifyDataSetChanged();
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
        UserLoginAllowLocation myItem = getList().get(position);
        viewHolder.tvTitle.setText(myItem.getAddress());
        viewHolder.tvContent.setText("附近" + myItem.getRadius() + "M");
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
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView imgDetail;
        public View vClick;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgCheck = (ImageView) rootView.findViewById(R.id.imgCheck);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
            this.imgDetail = (ImageView) rootView.findViewById(R.id.imgDetail);
            this.vClick = rootView.findViewById(R.id.vClick);
        }

    }
}
