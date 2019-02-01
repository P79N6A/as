package com.yaoguang.shipper.businessorder.trailer.templist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.yaoguang.appcompanyshipper.phone.businessorder.BusinessOrderTempListAppInfoClientPlaceAdapter;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.adapter.BaseBusinessOrderTempListAdapter;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrderTemplate;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.shipper.R;

/**
 * Created by zhongjh on 2018/9/10.
 */
public class BusinessOrderTempListAdapter extends BaseBusinessOrderTempListAdapter<AppOwnerTruckOrderTemplate> {


    public BusinessOrderTempListAdapter(RecyclerView.RecycledViewPool mRecycledViewPool, Context context) {
        super(mRecycledViewPool, context);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppOwnerTruckOrderTemplate appOwnerTruckOrderTemplate = getItem(position);
        itemViewHolder.tvInfoClientManager.setText(ObjectUtils.parseString(appOwnerTruckOrderTemplate.getOfficeName()));
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(appOwnerTruckOrderTemplate.getGoodsName()));
        itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(appOwnerTruckOrderTemplate.getPort()));
        itemViewHolder.tvDestination.setText(ObjectUtils.parseString(appOwnerTruckOrderTemplate.getAddress()));
        itemViewHolder.tvShipCompany.setText(ObjectUtils.parseString(appOwnerTruckOrderTemplate.getShipCompany()));
        itemViewHolder.tvOwner.setText(ObjectUtils.parseString(appOwnerTruckOrderTemplate.getOwner()));

        // 先判断一下是不是已经设置了Adapter
        if (itemViewHolder.rlLoading.getAdapter() == null) {
            BusinessOrderTempListAppInfoClientPlaceAdapter businessOrderTempListAppInfoClientPlaceAdapter = new BusinessOrderTempListAppInfoClientPlaceAdapter(mContext,0);
            businessOrderTempListAppInfoClientPlaceAdapter.setData(appOwnerTruckOrderTemplate.getAppInfoClientPlaces());
            itemViewHolder.rlLoading.setAdapter(businessOrderTempListAppInfoClientPlaceAdapter);
        }else{
            ((BusinessOrderTempListAppInfoClientPlaceAdapter)itemViewHolder.rlLoading.getAdapter()).setData(appOwnerTruckOrderTemplate.getAppInfoClientPlaces());
            itemViewHolder.rlLoading.getAdapter().notifyDataSetChanged();
        }

    }

}
