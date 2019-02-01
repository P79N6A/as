package com.yaoguang.company.businessorder.trailer.list.adapter;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list.adapter.BaseBusinessOrderListAdapter;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.AppTruckOrder;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/9/10.
 */

public class BusinessOrderListAdapter extends BaseBusinessOrderListAdapter<AppTruckOrder> {


    protected Fragment mFragment;

    public BusinessOrderListAdapter(Fragment fragment) {
        mFragment = fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
        AppTruckOrder appTruckOrder = getItem(position);
        //判断装货还是卸货，装货就是地区到港口，卸货就是港口到地区
        if (appTruckOrder.getOtherService().equals("0")) {
            //装货
            itemViewHolder.tvDeparture.setText(appTruckOrder.getAddress());
            itemViewHolder.tvDestination.setText(appTruckOrder.getPort());

            Glide.with(mFragment)
                    .load(R.drawable.ic_bq_z)
                    .into(itemViewHolder.imgType);
        } else {
            itemViewHolder.tvDeparture.setText(appTruckOrder.getPort());
            itemViewHolder.tvDestination.setText(appTruckOrder.getAddress
                    ());
            Glide.with(mFragment)
                    .load(R.drawable.ic_bq_x)
                    .into(itemViewHolder.imgType);
        }
        itemViewHolder.tvOrderID.setText(ObjectUtils.parseString(appTruckOrder.getOrderSn()));
        itemViewHolder.tvPeople.setText(ObjectUtils.parseString(appTruckOrder.getShipper()));
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(appTruckOrder.getGoodsName()));
        itemViewHolder.tvFee.setText("¥" + ObjectUtils.formatNumber2(appTruckOrder.getFee(), 0));
        itemViewHolder.tvSonos.setText(ObjectUtils.parseString(appTruckOrder.getSonos()));
        if (!TextUtils.isEmpty(appTruckOrder.getUpdated()))
            itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appTruckOrder.getUpdated()) + " 提交");
        else
            itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appTruckOrder.getCreated()) + " 提交");
        if (appTruckOrder.getStatus().equals("1")) {
            itemViewHolder.imgStatus.setImageResource(R.drawable.ic_ydr_01);
        } else {
            itemViewHolder.imgStatus.setImageResource(R.drawable.ic_dr_w);
        }
    }

}
