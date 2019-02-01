package com.yaoguang.company.order.trailerorder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.greendao.entity.driver.TruckGoodsAddr;

/**
 * 拖车装/卸 货适配器
 * Created by zhongjh on 2017/6/23.
 */
public class LoadingTrailerAdapter extends BaseLoadMoreRecyclerAdapter<TruckGoodsAddr, RecyclerView.ViewHolder> {

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public View vLoading;
        public TextView tvTag;
        public TextView tvTitleJHR;
        public TextView tvJHR;
        public TextView tvCompanyLXR;
        public TextView tvShipperTel;
        public TextView tvShipperMP;
        public TextView tvConsignerUnitFax;
        public TextView tvConsignerUnit;
        public TextView tvGoodsPriority;
        public TextView tvTitleRemark3;
        public TextView tvRemark3;

        ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.vLoading = rootView.findViewById(R.id.vLoading);
            this.tvTag = (TextView) rootView.findViewById(R.id.tvTag);
            this.tvTitleJHR = (TextView) rootView.findViewById(R.id.tvTitleJHR);
            this.tvJHR = (TextView) rootView.findViewById(R.id.tvJHR);
            this.tvCompanyLXR = (TextView) rootView.findViewById(R.id.tvCompanyLXR);
            this.tvShipperTel = (TextView) rootView.findViewById(R.id.tvShipperTel);
            this.tvShipperMP = (TextView) rootView.findViewById(R.id.tvShipperMP);
            this.tvConsignerUnitFax = (TextView) rootView.findViewById(R.id.tvConsignerUnitFax);
            this.tvConsignerUnit = (TextView) rootView.findViewById(R.id.tvConsignerUnit);
            this.tvGoodsPriority = (TextView) rootView.findViewById(R.id.tvGoodsPriority);
            this.tvTitleRemark3 = (TextView) rootView.findViewById(R.id.tvTitleRemark3);
            this.tvRemark3 = (TextView) rootView.findViewById(R.id.tvRemark3);
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_trailer, parent, false);
        if (getList().size() <= 1)
            view.findViewById(R.id.tvTag).setVisibility(View.GONE);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        //判断是装货还是卸货
        if (getList().get(position).getFlag() == 0) {
            //装货
            itemViewHolder.tvTitleJHR.setText("装货地址:");
            itemViewHolder.tvTitleRemark3.setText("装货说明:");
        } else {
            //卸货
            itemViewHolder.tvTitleJHR.setText("卸货地址:");
            itemViewHolder.tvTitleRemark3.setText("卸货说明:");
        }
        itemViewHolder.tvJHR.setText(getList().get(position).getAddress());
        itemViewHolder.tvCompanyLXR.setText(getList().get(position).getContacts());
        itemViewHolder.tvShipperTel.setText(getList().get(position).getTel());
        itemViewHolder.tvShipperMP.setText(getList().get(position).getMobile());
        itemViewHolder.tvConsignerUnitFax.setText(getList().get(position).getUnitFax());
        itemViewHolder.tvConsignerUnit.setText(getList().get(position).getGoodsUnit());
        itemViewHolder.tvRemark3.setText(getList().get(position).getRemarks());
        itemViewHolder.tvTag.setText(ObjectUtils.parseString((position + 1)));
        itemViewHolder.tvGoodsPriority.setText(getList().get(position).getGoodsPriority());
    }


}
