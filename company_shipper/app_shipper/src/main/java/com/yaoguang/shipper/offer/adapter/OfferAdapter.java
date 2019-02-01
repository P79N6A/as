package com.yaoguang.shipper.offer.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRate;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.shipper.R;

/**
 * Created by zhongjh on 2018/9/18.
 */

public class OfferAdapter extends BaseLoadMoreRecyclerAdapter<PriceShipperReceivableRate, RecyclerView.ViewHolder> {

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        itemViewHolder.tvContainer.setText(getList().get(position).getContId());
        itemViewHolder.tvMoney.setText(getList().get(position).getMoney());
        itemViewHolder.tvGoodsName.setText(getList().get(position).getGoodsName());
        itemViewHolder.tvConsigneeId.setText(getList().get(position).getCarriageItem());
        itemViewHolder.tvDate.setText(getList().get(position).getEndDate());
        itemViewHolder.tvAddress.setText(getList().get(position).getLoadingAddress());
        itemViewHolder.tvAddress2.setText(getList().get(position).getUnloadingAddress());
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, getList().get(holder.getAdapterPosition()), holder.getAdapterPosition()));
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvContainer;
        public TextView tvMoney;
        public TextView tvGoodsName;
        public TextView tvConsigneeId;
        public TextView tvDate;
        public TextView tvAddress;
        public TextView tvAddress2;
        public ImageView imgDetail;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvContainer = (TextView) rootView.findViewById(R.id.tvContainer);
            this.tvMoney = (TextView) rootView.findViewById(R.id.tvMoney);
            this.tvGoodsName = (TextView) rootView.findViewById(R.id.tvGoodsName);
            this.tvConsigneeId = (TextView) rootView.findViewById(R.id.tvConsigneeId);
            this.tvDate = (TextView) rootView.findViewById(R.id.tvDate);
            this.tvAddress = (TextView) rootView.findViewById(R.id.tvAddress);
            this.tvAddress2 = (TextView) rootView.findViewById(R.id.tvAddress2);
            this.imgDetail = (ImageView) rootView.findViewById(R.id.imgDetail);
        }

    }
}
