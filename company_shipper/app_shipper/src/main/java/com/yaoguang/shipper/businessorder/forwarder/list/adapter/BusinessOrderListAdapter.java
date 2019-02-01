package com.yaoguang.shipper.businessorder.forwarder.list.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.adapter.BaseBusinessOrderListAdapter;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrder;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.shipper.R;

/**
 * Created by zhongjh on 2018/9/6.
 */
public class BusinessOrderListAdapter extends BaseBusinessOrderListAdapter<AppOwnerForwardOrder> {

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppOwnerForwardOrder appOwnerForwardOrder = getItem(position);
        itemViewHolder.tvOrderID.setText(ObjectUtils.parseString(appOwnerForwardOrder.getOrderSn()));
        itemViewHolder.tvPeople.setText(ObjectUtils.parseString(appOwnerForwardOrder.getCompanyName()));
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(appOwnerForwardOrder.getGoodsName()));
        itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(appOwnerForwardOrder.getDockOfLoading()));
        itemViewHolder.tvDestination.setText(ObjectUtils.parseString(appOwnerForwardOrder.getFinalDestination()));
        itemViewHolder.tvCarriageItem.setText(ObjectUtils.parseString(appOwnerForwardOrder.getCarriageItem()));
        itemViewHolder.tvFee.setText("¥" + ObjectUtils.formatNumber2(appOwnerForwardOrder.getFee(), 0));
        itemViewHolder.tvSonos.setText(ObjectUtils.parseString(appOwnerForwardOrder.getSonos()));
        if (!TextUtils.isEmpty(appOwnerForwardOrder.getUpdated()))
            itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerForwardOrder.getUpdated()) + " 提交");
        else
            itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerForwardOrder.getCreated()) + " 提交");
        if (appOwnerForwardOrder.getStatus().equals("1")) {
            itemViewHolder.imgStatus.setImageResource(R.drawable.ic_ydr_01);
        } else {
            itemViewHolder.imgStatus.setImageResource(R.drawable.ic_dr_w);
        }
    }
}
