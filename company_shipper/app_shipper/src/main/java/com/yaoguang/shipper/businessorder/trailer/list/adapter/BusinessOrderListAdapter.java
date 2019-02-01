package com.yaoguang.shipper.businessorder.trailer.list.adapter;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list.adapter.BaseBusinessOrderListAdapter;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrder;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.businessorder.trailer.list.BusinessOrderListFragment;

/**
 * Created by zhongjh on 2018/9/10.
 */

public class BusinessOrderListAdapter extends BaseBusinessOrderListAdapter<AppOwnerTruckOrder> {


    protected Fragment mFragment;

    public BusinessOrderListAdapter(Fragment fragment) {
        mFragment = fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppOwnerTruckOrder appOwnerTruckOrder = getItem(position);
        //判断装货还是卸货，装货就是地区到港口，卸货就是港口到地区
        if (appOwnerTruckOrder.getOtherService().equals("0")) {
            //装货
            itemViewHolder.tvDeparture.setText(appOwnerTruckOrder.getAddress());
            itemViewHolder.tvDestination.setText(appOwnerTruckOrder.getPort());

            Glide.with(mFragment)
                    .load(R.drawable.ic_bq_z)
                    .into(itemViewHolder.imgType);
        } else {
            itemViewHolder.tvDeparture.setText(appOwnerTruckOrder.getPort());
            itemViewHolder.tvDestination.setText(appOwnerTruckOrder.getAddress
                    ());
            Glide.with(mFragment)
                    .load(R.drawable.ic_bq_x)
                    .into(itemViewHolder.imgType);
        }
        itemViewHolder.tvOrderID.setText(ObjectUtils.parseString(appOwnerTruckOrder.getOrderSn()));
        itemViewHolder.tvPeople.setText(ObjectUtils.parseString(appOwnerTruckOrder.getCompanyName()));
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(appOwnerTruckOrder.getGoodsName()));
        itemViewHolder.tvFee.setText("¥" + ObjectUtils.formatNumber2(appOwnerTruckOrder.getFee(), 0));
        itemViewHolder.tvSonos.setText(ObjectUtils.parseString(appOwnerTruckOrder.getSonos()));
        if (!TextUtils.isEmpty(appOwnerTruckOrder.getUpdated()))
            itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerTruckOrder.getUpdated()) + " 提交");
        else
            itemViewHolder.tvOrderTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerTruckOrder.getCreated()) + " 提交");
        if (appOwnerTruckOrder.getStatus().equals("1")) {
            itemViewHolder.imgStatus.setImageResource(R.drawable.ic_ydr_01);
        } else {
            itemViewHolder.imgStatus.setImageResource(R.drawable.ic_dr_w);
        }
    }

}
