package com.yaoguang.company.businessorder2.forwarder.container.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightSono;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/11/10.
 */

public class ContainerListAdapter extends BaseLoadMoreRecyclerAdapter<FreightSono, RecyclerView.ViewHolder> {

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        FreightSono freightSono = getList().get(position);
        // 规格
        itemViewHolder.tvTitle.setText(ObjectUtils.parseString(freightSono.getContId(),"未知"));
        // 柜号
        itemViewHolder.tvValueCabinetType.setText(ObjectUtils.parseString(freightSono.getContNo(),"未知"));
        // 封号
        itemViewHolder.tvValueNumber.setText(ObjectUtils.parseString(freightSono.getSealNo(),"未知"));
        // 是否允许套箱
        itemViewHolder.cbTx.setChecked(ObjectUtils.parseDouble(freightSono.getTx()) == 1);
        // 维修
        itemViewHolder.cbIsRepair.setChecked(ObjectUtils.parseDouble(freightSono.getIsRepair()) == 1);
        // 扣货
        itemViewHolder.cbTruckGoodsOr.setChecked(ObjectUtils.parseDouble(freightSono.getTruckGoodsOr()) == 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forwarder_business_container_detail, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, getList().get(position), position);
            }
        });
        holder.cbTx.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgLoading;
        public TextView tvTitle;
        public TextView tvValueCabinetType;
        public TextView tvValueNumber;
        public CheckBox cbTx;
        public CheckBox cbTruckGoodsOr;
        public CheckBox cbIsRepair;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgLoading = rootView.findViewById(R.id.imgLoading);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvValueCabinetType = rootView.findViewById(R.id.tvValueCabinetType);
            this.tvValueNumber = rootView.findViewById(R.id.tvValueNumber);
            this.cbTx = rootView.findViewById(R.id.cbTx);
            this.cbTruckGoodsOr = rootView.findViewById(R.id.cbTruckGoodsOr);
            this.cbIsRepair = rootView.findViewById(R.id.cbIsRepair);
        }

    }

}
