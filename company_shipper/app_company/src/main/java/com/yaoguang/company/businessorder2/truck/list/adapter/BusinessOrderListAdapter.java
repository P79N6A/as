package com.yaoguang.company.businessorder2.truck.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/9/6.
 */
public class BusinessOrderListAdapter extends BaseLoadMoreRecyclerAdapter<TruckBills, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order2, parent, false);
        view.findViewById(R.id.tvOperationClause).setVisibility(View.GONE);
        view.findViewById(R.id.imgOperationClause).setVisibility(View.GONE);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        TruckBills truckBills = getList().get(position);
        itemViewHolder.tvOrderID.setText(ObjectUtils.parseString(truckBills.getOrderSn(), "", "未知"));
        itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(truckBills.getCreated()) + " 制单");
        itemViewHolder.tvPeople.setText(ObjectUtils.parseString(truckBills.getShipper(), "", "未知"));
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(truckBills.getGoodsName(), "", "未知"));
        // 装箱是0，拆箱是1 //4种运货类型 1.起运地DockOfLoading，起运港PortLoading，目的港PortDelivery，目的地FinalDestination
        if (truckBills.getOtherservice().equals(0)) {
            itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(truckBills.getDockOfLoading(), "", "未知"));
            itemViewHolder.tvDestination.setText(ObjectUtils.parseString(truckBills.getPortLoading(), "", "未知"));
        } else if (truckBills.getOtherservice().equals(1)) {
            itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(truckBills.getPortDelivery(), "", "未知"));
            itemViewHolder.tvDestination.setText(ObjectUtils.parseString(truckBills.getFinalDestination(), "", "未知"));
        }

        itemViewHolder.tvCabinetTypeVolume.setText(ObjectUtils.parseString(truckBills.getConQty(), "", "未知"));//柜型柜量
        itemViewHolder.tvOwner.setText(ObjectUtils.parseString(truckBills.getConsigneeId(), "", "未知"));
        itemViewHolder.tvMBLNO2.setText(ObjectUtils.parseString(truckBills.getmBlNo(), "", "未知"));


        // 判断装卸
        if (truckBills.getOtherservice() != null && truckBills.getOtherservice() == 0) {
            itemViewHolder.imgLoadingOrUnLoading.setImageResource(R.drawable.ic_z_11);
            itemViewHolder.imgLoadingOrUnLoading.setVisibility(View.VISIBLE);
        } else if (truckBills.getOtherservice() != null && truckBills.getOtherservice() == 1) {
            itemViewHolder.imgLoadingOrUnLoading.setImageResource(R.drawable.ic_x_11);
            itemViewHolder.imgLoadingOrUnLoading.setVisibility(View.VISIBLE);
        }else{
            itemViewHolder.imgLoadingOrUnLoading.setVisibility(View.GONE);
        }

        // 判断整柜拼箱
        if (truckBills.getSonoGroup() != null && truckBills.getSonoGroup() == 0) {
            itemViewHolder.imgZheng.setImageResource(R.drawable.ic_zheng_11);
            itemViewHolder.imgZheng.setVisibility(View.VISIBLE);
        } else if (truckBills.getSonoGroup() != null && truckBills.getSonoGroup() == 1) {
            itemViewHolder.imgZheng.setImageResource(R.drawable.ic_pin11);
            itemViewHolder.imgZheng.setVisibility(View.VISIBLE);
        }else{
            itemViewHolder.imgZheng.setVisibility(View.GONE);
        }

        itemViewHolder.rootView.setOnClickListener(v -> {
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
        public ImageView imgLoadingOrUnLoading;
        public ImageView imgZheng;
        public ImageView imgOperationClause;

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
            this.imgLoadingOrUnLoading = rootView.findViewById(R.id.imgLoadingOrUnLoading);
            this.imgZheng = rootView.findViewById(R.id.imgZheng);
            this.imgOperationClause = rootView.findViewById(R.id.imgOperationClause);
        }

    }


}
