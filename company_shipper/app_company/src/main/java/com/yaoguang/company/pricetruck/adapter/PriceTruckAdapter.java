package com.yaoguang.company.pricetruck.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.AppPriceTruckWrapper;

/**
 * 拖车报价查询适配器
 * Created by zhongjh on 2017/6/14.
 */
public class PriceTruckAdapter extends BaseLoadMoreRecyclerAdapter<AppPriceTruckWrapper, RecyclerView.ViewHolder> {


    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pricetruck2, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvShipCompany.setText(ObjectUtils.parseString(getList().get(position).getPriceTruck().getShipper(),"暂无数据"));
        itemViewHolder.tvGoodsType.setText(ObjectUtils.parseString(getList().get(position).getPriceTruck().getGoodsType(),"暂无数据"));

        itemViewHolder.tvFee.setText("¥" + ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getFee(), 0)));
        itemViewHolder.tvOtherServiceAndCont.setText((getList().get(position).getPriceTruck().getOtherservice() == 0 ? "装箱" : "拆箱") + " " + ObjectUtils.parseString(getList().get(position).getCont()));
        //装箱则是 地点 - 港口。拆箱则是 港口 - 地点
        if (getList().get(position).getPriceTruck().getOtherservice() == 0) {
            itemViewHolder.tvPort.setText(ObjectUtils.parseString(getList().get(position).getPriceTruck().getAddress(),"暂无数据"));
            itemViewHolder.tvAddress.setText(ObjectUtils.parseString(getList().get(position).getPriceTruck().getPort(),"暂无数据"));
            itemViewHolder.imgLoading.setImageResource(R.drawable.ic_z_11);
        } else {
            itemViewHolder.tvPort.setText(ObjectUtils.parseString(getList().get(position).getPriceTruck().getPort(),"暂无数据"));
            itemViewHolder.tvAddress.setText(ObjectUtils.parseString(getList().get(position).getPriceTruck().getAddress(),"暂无数据"));
            itemViewHolder.imgLoading.setImageResource(R.drawable.ic_x_11);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgLoading;
        public TextView tvPort;
        public ImageView imgPort;
        public TextView tvAddress;
        public View vLine;
        public TextView tvFee;
        public TextView tvGoodsType;
        public TextView tvOtherServiceAndCont;
        public TextView tvShipCompany;
        public TableLayout tlWait;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgLoading = rootView.findViewById(R.id.imgLoading);
            this.tvPort = rootView.findViewById(R.id.tvPort);
            this.imgPort = rootView.findViewById(R.id.imgPort);
            this.tvAddress = rootView.findViewById(R.id.tvAddress);
            this.vLine = rootView.findViewById(R.id.vLine);
            this.tvFee = rootView.findViewById(R.id.tvFee);
            this.tvGoodsType = rootView.findViewById(R.id.tvGoodsType);
            this.tvOtherServiceAndCont = rootView.findViewById(R.id.tvOtherServiceAndCont);
            this.tvShipCompany = rootView.findViewById(R.id.tvShipCompany);
            this.tlWait = rootView.findViewById(R.id.tlWait);
        }

    }
}