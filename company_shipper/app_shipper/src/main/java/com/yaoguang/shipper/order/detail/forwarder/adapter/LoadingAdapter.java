package com.yaoguang.shipper.order.detail.forwarder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightShipper;
import com.yaoguang.shipper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 装货的
 * Created by zhongjh on 2017/6/21.
 */
public class LoadingAdapter extends BaseLoadMoreRecyclerAdapter<FreightShipper, RecyclerView.ViewHolder> {

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTag)
        TextView tvTag;
        @BindView(R.id.tvJHR)
        TextView tvJHR;
        @BindView(R.id.tvCompanyLXR)
        TextView tvCompanyLXR;
        @BindView(R.id.tvShipperTel)
        TextView tvShipperTel;
        @BindView(R.id.tvShipperMP)
        TextView tvShipperMP;
        @BindView(R.id.tvConsignerUnitFax)
        TextView tvConsignerUnitFax;
        @BindView(R.id.tvConsignerUnit)
        TextView tvConsignerUnit;
        @BindView(R.id.tvRemark3)
        TextView tvRemark3;
        @BindView(R.id.tvRemark3Title)
        TextView tvRemark3Title;


        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
        view.findViewById(R.id.tvTag).setBackgroundResource(R.drawable.ic_biaoqian_lan);
        ((TextView) view.findViewById(R.id.tvRemark3Title)).setText("装货说明:");
        if (getList().size() <= 1)
            view.findViewById(R.id.tvTag).setVisibility(View.GONE);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvJHR.setText(getList().get(position).getJhr());
        itemViewHolder.tvCompanyLXR.setText(getList().get(position).getCompanyLxr());
        itemViewHolder.tvShipperTel.setText(getList().get(position).getShipperTel());
        itemViewHolder.tvShipperMP.setText(getList().get(position).getShipperMp());
        itemViewHolder.tvConsignerUnitFax.setText(getList().get(position).getConsignerUnitFax());
        itemViewHolder.tvConsignerUnit.setText(getList().get(position).getConsignerUnit());
        itemViewHolder.tvRemark3.setText(getList().get(position).getRemark3());
        itemViewHolder.tvTag.setText(ObjectUtils.parseString((position + 1)));
    }

}
