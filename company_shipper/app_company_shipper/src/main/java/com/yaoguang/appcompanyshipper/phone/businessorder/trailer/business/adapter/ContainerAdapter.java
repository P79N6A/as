package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.greendao.entity.AppTruckSono;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 箱子成型成量
 * Created by zhongjh on 2017/5/31.
 */
public class ContainerAdapter extends BaseLoadMoreRecyclerAdapter<AppTruckSono, RecyclerView.ViewHolder> {

    boolean enable = true;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_trailer, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        if (enable) {
            //删除
            holder.imgRemove.setOnClickListener(v -> {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mOnRecyclerViewItemRLClickListener.onRlRemoveClick(v, getList().get(position), position);
                }
            });
            //添加
            holder.imgAdd.setOnClickListener(v -> {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mOnRecyclerViewItemRLClickListener.onRlAddClick(v, getList().get(position), position);
                }
            });
            //编辑
            view.setOnClickListener(v -> {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mOnRecyclerViewItemRLClickListener.onEditAddClick(v, getList().get(position), position);
                }
            });

        } else {
            holder.imgRemove.setOnClickListener(null);
            holder.imgAdd.setOnClickListener(null);
            view.setOnClickListener(null);
        }
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        if (getList().get(position) != null) {
            itemViewHolder.tvontId.setText(ObjectUtils.parseString(getList().get(position).getContId()));
            itemViewHolder.tvContNo.setText(ObjectUtils.parseString(getList().get(position).getContNo()));
            itemViewHolder.tvSealNo.setText(ObjectUtils.parseString(getList().get(position).getSealNo()));
            itemViewHolder.tvCarryPort.setText(ObjectUtils.parseString(getList().get(position).getCarryPort()));
            itemViewHolder.tvGetPort.setText(ObjectUtils.parseString(getList().get(position).getGetPort()));
        }
    }

    //点击事件
    public interface OnRecyclerViewItemRLClickListener {

        void onRlRemoveClick(View itemView, AppTruckSono item, int position);

        void onRlAddClick(View itemView, AppTruckSono item, int position);

        void onEditAddClick(View itemView, AppTruckSono item, int position);
    }

    private OnRecyclerViewItemRLClickListener mOnRecyclerViewItemRLClickListener = null;

    public void setOnRecyclerViewItemRLClickListener(OnRecyclerViewItemRLClickListener listener) {
        this.mOnRecyclerViewItemRLClickListener = listener;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        TextView tvontId;
        TextView tvContNo;
        TextView tvSealNo;
        TextView tvCarryPort;
        TextView tvGetPort;
        TableLayout tlWait;
        ImageView imgAdd;
        ImageView imgRemove;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvontId = (TextView) rootView.findViewById(R.id.tvontId);
            this.tvContNo = (TextView) rootView.findViewById(R.id.tvContNo);
            this.tvSealNo = (TextView) rootView.findViewById(R.id.tvSealNo);
            this.tvCarryPort = (TextView) rootView.findViewById(R.id.tvCarryPort);
            this.tvGetPort = (TextView) rootView.findViewById(R.id.tvGetPort);
            this.tlWait = (TableLayout) rootView.findViewById(R.id.tlWait);
            this.imgAdd = (ImageView) rootView.findViewById(R.id.imgAdd);
            this.imgRemove = (ImageView) rootView.findViewById(R.id.imgRemove);
        }

    }
}
