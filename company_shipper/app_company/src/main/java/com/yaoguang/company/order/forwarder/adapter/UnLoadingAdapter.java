package com.yaoguang.company.order.forwarder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightConsignee;

/**
 * 卸货的
 * Created by zhongjh on 2017/6/21.
 */
public class UnLoadingAdapter extends BaseLoadMoreRecyclerAdapter<FreightConsignee, RecyclerView.ViewHolder> {

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private TextView tvJHR;
        private TextView tvCompanyLXR;
        private TextView tvShipperTel;
        private TextView tvShipperMP;
        private TextView tvConsignerUnitFax;
        private TextView tvConsignerUnit;
        private TextView tvRemark3;
        private TextView tvTag;

        ItemViewHolder(View view) {
            super(view);
            this.rootView = view;
            this.tvJHR = (TextView) rootView.findViewById(R.id.tvJHR);
            this.tvCompanyLXR = (TextView) rootView.findViewById(R.id.tvCompanyLXR);
            this.tvShipperTel = (TextView) rootView.findViewById(R.id.tvShipperTel);
            this.tvShipperMP = (TextView) rootView.findViewById(R.id.tvShipperMP);
            this.tvConsignerUnitFax = (TextView) rootView.findViewById(R.id.tvConsignerUnitFax);
            this.tvConsignerUnit = (TextView) rootView.findViewById(R.id.tvConsignerUnit);
            this.tvRemark3 = (TextView) rootView.findViewById(R.id.tvRemark3);
            this.tvTag = (TextView) rootView.findViewById(R.id.tvTag);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
        view.findViewById(R.id.tvTag).setBackgroundResource(R.drawable.ic_biaoqian_fen);
        ((TextView)view.findViewById(R.id.tvRemark3Title)).setText("卸货说明:");
        if (getList().size() <= 1)
            view.findViewById(R.id.tvTag).setVisibility(View.GONE);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvJHR.setText(getList().get(position).getConsigneePlace());
        itemViewHolder.tvCompanyLXR.setText(getList().get(position).getConsigneeLinkman());
        itemViewHolder.tvShipperTel.setText(getList().get(position).getConsigneeTel());
        itemViewHolder.tvShipperMP.setText(getList().get(position).getConsigneeMp());
        itemViewHolder.tvConsignerUnitFax.setText(getList().get(position).getConsigneeUnitFax());
        itemViewHolder.tvConsignerUnit.setText(getList().get(position).getConsigneeUnit());
        itemViewHolder.tvRemark3.setText(getList().get(position).getRemark9());
        itemViewHolder.tvTag.setText(ObjectUtils.parseString((position + 1)));
    }

}
