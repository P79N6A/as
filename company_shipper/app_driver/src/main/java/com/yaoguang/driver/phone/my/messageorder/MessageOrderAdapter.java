package com.yaoguang.driver.phone.my.messageorder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.driver.R;
import com.yaoguang.driver.phone.home.adapter.OrderChildAdapter;
import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.lib.appcommon.utils.AllSelectDelete;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单列表：待接单
 * Created by wly on 2017/5/9.
 */

public class MessageOrderAdapter extends OrderChildAdapter<DriverOrderMsgWrapper> {

    private final Context mContext;

    public MessageOrderAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolderCustom itemViewHolderCustom = (ItemViewHolderCustom) holder;
        DriverOrderMsgWrapper driverOrderMsgWrapper = getItem(position);

        bindItemViewHolder(itemViewHolderCustom, driverOrderMsgWrapper);

        // 是否已读未读
        if (driverOrderMsgWrapper.getFlag().equals("0")) {
            //  是否已读（0：未读 1：已读）
            itemViewHolderCustom.messageFlag.setVisibility(View.VISIBLE);
        } else {
            itemViewHolderCustom.messageFlag.setVisibility(View.GONE);
        }

        AllSelectDelete.adappterSetting(this, itemViewHolderCustom.flSelect, itemViewHolderCustom.llView, itemViewHolderCustom.cbSelect, itemViewHolderCustom);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolderCustom(View view) {
        return new ItemViewHolderCustom(view);
    }

    @Override
    public View onCreateItemViewHolderCustom(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_message, parent, false);
    }

    @Override
    public void onItemBtnRegisterClick(View view, ItemViewHolder holder) {

    }

    @Override
    public void onItemBtnAcceptClick(View view, ItemViewHolder holder) {
    }

    @Override
    public void onItemBtnRefuseClick(View view, ItemViewHolder holder) {
    }

    public void setAllSelect(boolean allSelect) {
        isAllSelect = allSelect;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }


    public class ItemViewHolderCustom extends ItemViewHolder {
        @BindView(R.id.tvId)
        TextView tvId;
        @BindView(R.id.llOrderMark)
        LinearLayout llOrderMark;
        @BindView(R.id.ivLogo)
        ImageView ivLogo;
        @BindView(R.id.messageFlag)
        ImageView messageFlag;
        @BindView(R.id.tvCompanyName)
        TextView tvCompanyName;
        @BindView(R.id.tvCreated)
        TextView tvCreated;
        @BindView(R.id.ivPhone)
        ImageView ivPhone;
        @BindView(R.id.tvDeliveryRoute)
        TextView wllDeliveryRoute;
        @BindView(R.id.tvDeliveryPlaces)
        TextView tvDeliveryPlaces;
        @BindView(R.id.tvVehiclePrice)
        TextView tvVehiclePrice;
        @BindView(R.id.tvContQty)
        TextView tvContQty;
        @BindView(R.id.tvDriverOrderType)
        TextView tvDriverOrderType;
        @BindView(R.id.tvShipCompany)
        TextView tvShipCompany;
        @BindView(R.id.ivStatus)
        ImageView ivStatus;
        @BindView(R.id.tvVoice)
        TextView tvVoice;
        @BindView(R.id.tvVoice_line)
        View tvVoiceLine;
        @BindView(R.id.tvLookButton)
        TextView tvLookButton;
        @BindView(R.id.llView)
        LinearLayout llView;
        @BindView(R.id.cbSelect)
        CheckBox cbSelect;
        @BindView(R.id.flSelect)
        FrameLayout flSelect;

        ItemViewHolderCustom(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
