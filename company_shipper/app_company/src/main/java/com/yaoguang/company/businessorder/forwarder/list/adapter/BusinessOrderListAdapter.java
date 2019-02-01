package com.yaoguang.company.businessorder.forwarder.list.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.adapter.BaseBusinessOrderListAdapter;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.AppCompanyOrder;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/9/6.
 */
public class BusinessOrderListAdapter extends BaseBusinessOrderListAdapter<AppCompanyOrder> {

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppCompanyOrder appCompanyOrder = getItem(position);
        itemViewHolder.tvOrderID.setText(ObjectUtils.parseString(appCompanyOrder.getOrderSn()));
        itemViewHolder.tvPeople.setText(ObjectUtils.parseString(appCompanyOrder.getShipper()));
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(appCompanyOrder.getGoodsName()));
        itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(appCompanyOrder.getDockOfLoading()));
        itemViewHolder.tvDestination.setText(ObjectUtils.parseString(appCompanyOrder.getFinalDestination()));
        itemViewHolder.tvCarriageItem.setText(ObjectUtils.parseString(appCompanyOrder.getCarriageItem()));
        itemViewHolder.tvFee.setText("¥" + ObjectUtils.formatNumber2(appCompanyOrder.getFee(), 0));
        itemViewHolder.tvSonos.setText(ObjectUtils.parseString(appCompanyOrder.getSonos()));
        if (!TextUtils.isEmpty(appCompanyOrder.getUpdated()))
            itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appCompanyOrder.getUpdated()) + " 提交");
        else
            itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appCompanyOrder.getCreated()) + " 提交");
        if (appCompanyOrder.getStatus().equals("1")) {
            itemViewHolder.imgStatus.setImageResource(R.drawable.ic_ydr_01);
        } else {
            itemViewHolder.imgStatus.setImageResource(R.drawable.ic_dr_w);
        }
    }
}
