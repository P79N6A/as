package com.yaoguang.company.businessorder2.truck.container.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.TruckSono;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/11/10.
 */

public class ContainerListAdapter extends BaseLoadMoreRecyclerAdapter<TruckSono, RecyclerView.ViewHolder> {

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        TruckSono truckSono = getList().get(position);
        // 规格
        itemViewHolder.tvTitle.setText(ObjectUtils.parseString(truckSono.getContId(),"未知"));
        // 柜号
        itemViewHolder.tvValueCabinetType.setText(ObjectUtils.parseString(truckSono.getContNo(),"未知"));
        // 封号
        itemViewHolder.tvValueNumber.setText(ObjectUtils.parseString(truckSono.getSealNo(),"未知"));
        // 提柜码头
        itemViewHolder.tvValueCupboardWharf.setText(ObjectUtils.parseString(truckSono.getCarryPort(),"未知"));
        // 还柜码头
        itemViewHolder.tvValueCounterWharf.setText(ObjectUtils.parseString(truckSono.getGetPort(),"未知"));
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_truck_business_container_detail, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, getList().get(position), position);
            }
        });
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgLoading;
        public TextView tvTitle;
        public TextView tvValueCabinetType;
        public TextView tvValueNumber;
        public TextView tvValueCupboardWharf;
        public TextView tvValueCounterWharf;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgLoading = (ImageView) rootView.findViewById(R.id.imgLoading);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvValueCabinetType = (TextView) rootView.findViewById(R.id.tvValueCabinetType);
            this.tvValueNumber = (TextView) rootView.findViewById(R.id.tvValueNumber);
            this.tvValueCupboardWharf = (TextView) rootView.findViewById(R.id.tvValueCupboardWharf);
            this.tvValueCounterWharf = (TextView) rootView.findViewById(R.id.tvValueCounterWharf);
        }

    }

}
