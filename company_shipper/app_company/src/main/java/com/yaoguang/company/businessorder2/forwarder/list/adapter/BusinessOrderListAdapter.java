package com.yaoguang.company.businessorder2.forwarder.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/9/6.
 */
public class BusinessOrderListAdapter extends BaseLoadMoreRecyclerAdapter<FreightBills, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order2, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        FreightBills freightBills = getList().get(position);
        itemViewHolder.tvOrderID.setText(ObjectUtils.parseString(freightBills.getLadingId(), "", "未知"));
        itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(freightBills.getCreated()) + " 制单");
        itemViewHolder.tvPeople.setText(ObjectUtils.parseString(freightBills.getShipper(), "", "未知"));
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(freightBills.getGoodsName(), "", "未知"));
        itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(freightBills.getDockOfLoading(), "", "未知"));
        itemViewHolder.tvDestination.setText(ObjectUtils.parseString(freightBills.getFinalDestination(), "", "未知"));
        itemViewHolder.tvCabinetTypeVolume.setText(ObjectUtils.parseString(freightBills.getConts(), "", "未知"));//柜型柜量
        itemViewHolder.tvOperationClause.setText(ObjectUtils.parseString(freightBills.getCarriageItem(), "", "未知"));//操作条款
        itemViewHolder.tvOwner.setText(ObjectUtils.parseString(freightBills.getConsigneeId(), "", "未知"));
        itemViewHolder.tvMBLNO2.setText(ObjectUtils.parseString(freightBills.getmBlNo(), "", "未知"));


        ((ItemViewHolder) holder).rootView.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, getList().get(position), position);
            }
        });
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
