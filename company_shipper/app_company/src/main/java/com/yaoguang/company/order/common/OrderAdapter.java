package com.yaoguang.company.order.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.company.R;

import java.util.List;

/**
 * Created by zhongjh on 2017/6/15.
 */
public abstract class OrderAdapter<T, VH extends RecyclerView.ViewHolder> extends BaseLoadMoreRecyclerAdapter {

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderID;
        public TextView tvOrderTime;
        public TextView tvPeople;
        public TextView tvGoodsName;
        public TextView tvDeparture;
        public TextView tvDestination;
        public TableRow tbPort;
        public TextView tvPortDestination;
        public TextView tvPortShip;
        public TextView tvBusinessType;
        public TableRow tbBusinessType;
        public TextView tvOperationClause;
        public TableRow tbOperationClause;
        public TextView tvOwner;
        public TextView tvCabinetTypeVolume;
        public TextView tvMBLNO1;
        public TextView tvMBLNO2;

        public ItemViewHolder(View view) {
            super(view);
            tvOrderID = (TextView) view
                    .findViewById(R.id.tvOrderID);
            tvOrderTime = (TextView) view.findViewById(R.id.tvOrderTime);
            tvPeople = (TextView) view.findViewById(R.id.tvPeople);
            tvGoodsName = (TextView) view.findViewById(R.id.tvGoodsName);
            tvDeparture = (TextView) view.findViewById(R.id.tvDeparture);
            tvDestination = (TextView) view.findViewById(R.id.tvDestination);
            tbPort = (TableRow) view.findViewById(R.id.tbPort);
            tvPortShip = (TextView) view.findViewById(R.id.tvPortShip);
            tvPortDestination = (TextView) view.findViewById(R.id.tvPortDestination);
            tvBusinessType = (TextView) view.findViewById(R.id.tvBusinessType);
            tbBusinessType = (TableRow) view.findViewById(R.id.tbBusinessType);
            tvOperationClause = (TextView) view.findViewById(R.id.tvOperationClause);
            tbOperationClause = (TableRow) view.findViewById(R.id.tbOperationClause);
            tvOwner = (TextView) view.findViewById(R.id.tvOwner);
            tvCabinetTypeVolume = (TextView) view.findViewById(R.id.tvCabinetTypeVolume);
            tvMBLNO1 = (TextView) view.findViewById(R.id.tvMBLNO1);
            tvMBLNO2 = (TextView) view.findViewById(R.id.tvMBLNO2);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(v -> {
//            finalrequest int position = holder.getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, getList().get(holder.getAdapterPosition()),holder.getAdapterPosition());
//            }
        });
        initView(view);
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        List<T> list = getList();
        setItemViewHolder(itemViewHolder, list.get(position));
    }

    protected abstract void initView(View view);

    protected abstract void setItemViewHolder(ItemViewHolder itemViewHolder, T t);

}
