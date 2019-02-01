package com.yaoguang.company.businessorder.forwarder.templist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.yaoguang.appcompanyshipper.phone.businessorder.BusinessOrderTempListAppInfoClientPlaceAdapter;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.adapter.BaseBusinessOrderTempListAdapter;
import com.yaoguang.greendao.entity.company.AppCompanyOrderTemplate;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/9/7.
 */

public class BusinessOrderTempListAdapter extends BaseBusinessOrderTempListAdapter<AppCompanyOrderTemplate> {

    public BusinessOrderTempListAdapter(RecyclerView.RecycledViewPool mRecycledViewPool, Context context) {
        super(mRecycledViewPool, context);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppCompanyOrderTemplate appCompanyOrderTemplate = getItem(position);
        itemViewHolder.tvGoodsName.setText(ObjectUtils.parseString(appCompanyOrderTemplate.getGoodsName()));
        itemViewHolder.tvDeparture.setText(ObjectUtils.parseString(appCompanyOrderTemplate.getDockOfLoading()));
        itemViewHolder.tvDestination.setText(ObjectUtils.parseString(appCompanyOrderTemplate.getFinalDestination()));
        itemViewHolder.tvPortShipment.setText(ObjectUtils.parseString(appCompanyOrderTemplate.getPortLoading()));
        itemViewHolder.tvPortDestination.setText(ObjectUtils.parseString(appCompanyOrderTemplate.getPortDelivery()));
        itemViewHolder.tvInfoClientManager.setText(ObjectUtils.parseString(appCompanyOrderTemplate.getShipper()));
        itemViewHolder.tvTransportationClause.setText(ObjectUtils.parseString(appCompanyOrderTemplate.getCarriageWay()));
        itemViewHolder.tvOperationClause.setText(ObjectUtils.parseString(appCompanyOrderTemplate.getCarriageItem()));
        itemViewHolder.tvConsigneeIdonsigneeId.setText(ObjectUtils.parseString(appCompanyOrderTemplate.getOwner()));
        if (appCompanyOrderTemplate.getIsInsurance().equals("1")) {
            itemViewHolder.tvIsInsurance.setText("是");
        } else {
            itemViewHolder.tvIsInsurance.setText("否");
        }

        // 先判断一下是不是已经设置了Adapter
        if (itemViewHolder.rlLoading.getAdapter() == null) {
            BusinessOrderTempListAppInfoClientPlaceAdapter businessOrderTempListAppInfoClientPlaceAdapter = new BusinessOrderTempListAppInfoClientPlaceAdapter(mContext,0);
            businessOrderTempListAppInfoClientPlaceAdapter.setData(appCompanyOrderTemplate.getLoadClientPlaces());
            itemViewHolder.rlLoading.setAdapter(businessOrderTempListAppInfoClientPlaceAdapter);
        }else{
            ((BusinessOrderTempListAppInfoClientPlaceAdapter)itemViewHolder.rlLoading.getAdapter()).setData(appCompanyOrderTemplate.getLoadClientPlaces());
            itemViewHolder.rlLoading.getAdapter().notifyDataSetChanged();
        }

        if (itemViewHolder.rlUnLoading.getAdapter() == null) {
            BusinessOrderTempListAppInfoClientPlaceAdapter businessOrderTempListAppInfoClientPlaceAdapter = new BusinessOrderTempListAppInfoClientPlaceAdapter(mContext,1);
            businessOrderTempListAppInfoClientPlaceAdapter.setData(appCompanyOrderTemplate.getConsigneeClientPlaces());
            itemViewHolder.rlUnLoading.setAdapter(businessOrderTempListAppInfoClientPlaceAdapter);
        }else{
            ((BusinessOrderTempListAppInfoClientPlaceAdapter)itemViewHolder.rlUnLoading.getAdapter()).setData(appCompanyOrderTemplate.getConsigneeClientPlaces());
            itemViewHolder.rlUnLoading.getAdapter().notifyDataSetChanged();
        }

    }

}
