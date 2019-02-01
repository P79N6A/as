package com.yaoguang.company.businessorder.trailer.templist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.yaoguang.appcompanyshipper.phone.businessorder.BusinessOrderTempListAppInfoClientPlaceAdapter;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.adapter.BaseBusinessOrderTempListAdapter;
import com.yaoguang.greendao.entity.company.AppTruckOrderTemplate;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/9/10.
 */
public class BusinessOrderTempListAdapter extends BaseBusinessOrderTempListAdapter<AppTruckOrderTemplate> {


    public BusinessOrderTempListAdapter(RecyclerView.RecycledViewPool mRecycledViewPool, Context context) {
        super(mRecycledViewPool, context);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppTruckOrderTemplate appTruckOrderTemplate = getItem(position);
        itemViewHolder.tvInfoClientManager.setText(ObjectUtils.parseString(appTruckOrderTemplate.getShipCompany()));
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(appTruckOrderTemplate.getGoodsName()));
        itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(appTruckOrderTemplate.getPort()));
        itemViewHolder.tvDestination.setText(ObjectUtils.parseString(appTruckOrderTemplate.getAddress()));
//        itemViewHolder.tvShipCompany.setText(ObjectUtils.parseString(appTruckOrderTemplate.getShipCompany()));
        itemViewHolder.tvOwner.setText(ObjectUtils.parseString(appTruckOrderTemplate.getOwner()));

        // 先判断一下是不是已经设置了Adapter
        if (itemViewHolder.rlLoading.getAdapter() == null) {
            BusinessOrderTempListAppInfoClientPlaceAdapter businessOrderTempListAppInfoClientPlaceAdapter = new BusinessOrderTempListAppInfoClientPlaceAdapter(mContext,0);
            businessOrderTempListAppInfoClientPlaceAdapter.setData(appTruckOrderTemplate.getAppInfoClientPlaces());
            itemViewHolder.rlLoading.setAdapter(businessOrderTempListAppInfoClientPlaceAdapter);
        }else{
            ((BusinessOrderTempListAppInfoClientPlaceAdapter)itemViewHolder.rlLoading.getAdapter()).setData(appTruckOrderTemplate.getAppInfoClientPlaces());
            itemViewHolder.rlLoading.getAdapter().notifyDataSetChanged();
        }

    }

}
