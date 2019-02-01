package com.yaoguang.company.order.forwarder.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;

/**
 * 货代订单跟踪
 * Created by zhongjh on 2017/9/14.
 */
public class ForwarderOrderAdapter extends BaseLoadMoreRecyclerAdapter<FreightBills, RecyclerView.ViewHolder> {
    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order2, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, getList().get(position), position);
            }
        });
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        FreightBills freightBills = getList().get(position);
        itemViewHolder.tvOrderID.setText(ObjectUtils.parseString(freightBills.getLadingId(), "", "数据暂无"));
        itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(freightBills.getCreated()) + " 制单");
        itemViewHolder.tvPeople.setText(ObjectUtils.parseString(freightBills.getShipper(), "", "数据暂无"));
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(freightBills.getGoodsName(), "", "数据暂无"));
        itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(freightBills.getDockOfLoading(), "", "数据暂无"));
        itemViewHolder.tvDestination.setText(ObjectUtils.parseString(freightBills.getFinalDestination(), "", "数据暂无"));
        itemViewHolder.tvCabinetTypeVolume.setText(ObjectUtils.parseString(freightBills.getConts(), "", "数据暂无"));//柜型柜量
        itemViewHolder.tvOperationClause.setText(ObjectUtils.parseString(freightBills.getCarriageItem(), "", "数据暂无"));//操作条款
        itemViewHolder.tvOwner.setText(ObjectUtils.parseString(freightBills.getConsigneeId(), "", "数据暂无"));
        itemViewHolder.tvMBLNO2.setText(ObjectUtils.parseString(freightBills.getmBlNo(), "", "数据暂无"));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvOrderID;
        public TextView tvDeparture;
        public TextView tvMBLNO2;
        public View vLine;
        public TextView tvPeople;
        public TextView tvDestination;
        public TextView tvGoodsName;
        public TextView tvOwner;
        public TextView tvCabinetTypeVolume;
        public TextView tvOperationClause;
        public TextView tvOrderTime;
        public TableLayout tlWait;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvOrderID = (TextView) rootView.findViewById(R.id.tvOrderID);
            this.tvDeparture = (TextView) rootView.findViewById(R.id.tvDeparture);
            this.tvMBLNO2 = (TextView) rootView.findViewById(R.id.tvMBLNO2);
            this.vLine = (View) rootView.findViewById(R.id.vLine);
            this.tvPeople = (TextView) rootView.findViewById(R.id.tvPeople);
            this.tvDestination = (TextView) rootView.findViewById(R.id.tvDestination);
            this.tvGoodsName = (TextView) rootView.findViewById(R.id.tvGoodsName);
            this.tvOwner = (TextView) rootView.findViewById(R.id.tvOwner);
            this.tvCabinetTypeVolume = (TextView) rootView.findViewById(R.id.tvCabinetTypeVolume);
            this.tvOperationClause = (TextView) rootView.findViewById(R.id.tvOperationClause);
            this.tvOrderTime = (TextView) rootView.findViewById(R.id.tvOrderTime);
            this.tlWait = (TableLayout) rootView.findViewById(R.id.tlWait);
        }

    }
}
