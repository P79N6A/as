package com.yaoguang.shipper.businessorder.forwarder.templist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.yaoguang.appcompanyshipper.phone.businessorder.BusinessOrderTempListAppInfoClientPlaceAdapter;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.adapter.BaseBusinessOrderTempListAdapter;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrderTemplate;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/9/7.
 */

public class BusinessOrderTempListAdapter extends BaseBusinessOrderTempListAdapter<AppOwnerForwardOrderTemplate> {

    public BusinessOrderTempListAdapter(RecyclerView.RecycledViewPool mRecycledViewPool, Context context) {
        super(mRecycledViewPool, context);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppOwnerForwardOrderTemplate appOwnerForwardOrderTemplate = getItem(position);
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(appOwnerForwardOrderTemplate.getGoodsName()));
        itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(appOwnerForwardOrderTemplate.getDockOfLoading()));
        itemViewHolder.tvDestination.setText(ObjectUtils.parseString(appOwnerForwardOrderTemplate.getFinalDestination()));
        itemViewHolder.tvPortShipment.setText(ObjectUtils.parseString(appOwnerForwardOrderTemplate.getPortLoading()));
        itemViewHolder.tvPortDestination.setText(ObjectUtils.parseString(appOwnerForwardOrderTemplate.getPortDelivery()));
        itemViewHolder.tvInfoClientManager.setText(ObjectUtils.parseString(appOwnerForwardOrderTemplate.getOfficeName()));
        itemViewHolder.tvTransportationClause.setText(ObjectUtils.parseString(appOwnerForwardOrderTemplate.getCarriageWay()));
        itemViewHolder.tvOperationClause.setText(ObjectUtils.parseString(appOwnerForwardOrderTemplate.getCarriageItem()));
        itemViewHolder.tvConsigneeIdonsigneeId.setText(ObjectUtils.parseString(appOwnerForwardOrderTemplate.getOwner()));
        if (appOwnerForwardOrderTemplate.getIsInsurance().equals("1")) {
            itemViewHolder.tvIsInsurance.setText("是");
        } else {
            itemViewHolder.tvIsInsurance.setText("否");
        }

        // 先判断一下是不是已经设置了Adapter
        if (itemViewHolder.rlLoading.getAdapter() == null) {
            BusinessOrderTempListAppInfoClientPlaceAdapter businessOrderTempListAppInfoClientPlaceAdapter = new BusinessOrderTempListAppInfoClientPlaceAdapter(mContext,0);
            businessOrderTempListAppInfoClientPlaceAdapter.setData(appOwnerForwardOrderTemplate.getLoadClientPlaces());
            itemViewHolder.rlLoading.setAdapter(businessOrderTempListAppInfoClientPlaceAdapter);
        }else{
            ((BusinessOrderTempListAppInfoClientPlaceAdapter)itemViewHolder.rlLoading.getAdapter()).setData(appOwnerForwardOrderTemplate.getLoadClientPlaces());
            itemViewHolder.rlLoading.getAdapter().notifyDataSetChanged();
        }

        if (itemViewHolder.rlUnLoading.getAdapter() == null) {
            BusinessOrderTempListAppInfoClientPlaceAdapter businessOrderTempListAppInfoClientPlaceAdapter = new BusinessOrderTempListAppInfoClientPlaceAdapter(mContext,1);
            businessOrderTempListAppInfoClientPlaceAdapter.setData(appOwnerForwardOrderTemplate.getConsigneeClientPlaces());
            itemViewHolder.rlUnLoading.setAdapter(businessOrderTempListAppInfoClientPlaceAdapter);
        }else{
            ((BusinessOrderTempListAppInfoClientPlaceAdapter)itemViewHolder.rlUnLoading.getAdapter()).setData(appOwnerForwardOrderTemplate.getConsigneeClientPlaces());
            itemViewHolder.rlUnLoading.getAdapter().notifyDataSetChanged();
        }

    }

}
