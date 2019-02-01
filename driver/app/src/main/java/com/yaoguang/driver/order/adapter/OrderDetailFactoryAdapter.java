package com.yaoguang.driver.order.adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.DriverGoodsAddr;


/**
 * 订单详情的厂节点（卸货或者装货）
 * Created by wly on 2017/4/24.
 */
public class OrderDetailFactoryAdapter extends BaseLoadMoreRecyclerAdapter<DriverGoodsAddr, RecyclerView.ViewHolder> {

    private final String goodsType;

    public OrderDetailFactoryAdapter(String goodsType) {
        this.goodsType = goodsType;
    }

    private String getMobile(DriverGoodsAddr goodsAddrs) {
        if (!TextUtils.isEmpty(goodsAddrs.getMobile())) {
            return goodsAddrs.getMobile();
        }
        return goodsAddrs.getTel();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView ivRemark;
        TextView tvGoodsUnit;
        TextView tvAddress;
        TextView tvContacts;
        TextView tvMobile;
        TextView tvRemarks;
        TextView tvOrderType;
        TextView tvTitle;
        TextView tvTmp1;

        ItemViewHolder(View view) {
            super(view);
            tvTmp1 = view
                    .findViewById(R.id.tvTmp1);
            tvTitle = view
                    .findViewById(R.id.tvTitle);
            ivRemark = view
                    .findViewById(R.id.ivRemark);
            tvGoodsUnit = view
                    .findViewById(R.id.tvGoodsUnit);
            tvAddress = view
                    .findViewById(R.id.tvAddress);
            tvContacts = view
                    .findViewById(R.id.tvContacts);
            tvMobile = view
                    .findViewById(R.id.tvMobile);
            tvRemarks = view
                    .findViewById(R.id.tvRemarks);
            tvOrderType = view
                    .findViewById(R.id.tvOrderType);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_factory, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        DriverGoodsAddr goodsAddrs = getList().get(position);
        //
        if (goodsAddrs.getFlag() == 0) {
            itemViewHolder.tvTitle.setText("装货门点");
            itemViewHolder.tvTmp1.setText("装货说明:");
            itemViewHolder.ivRemark.setText("" + (position + 1));
            itemViewHolder.ivRemark.setBackgroundResource(R.drawable.ic_biaoqian_01);
        } else {
            itemViewHolder.tvTitle.setText("卸货门点");
            itemViewHolder.tvTmp1.setText("卸货说明:");
            itemViewHolder.ivRemark.setText("" + (position + 1));
            itemViewHolder.ivRemark.setBackgroundResource(R.drawable.ic_biaoqian_02);
        }

        itemViewHolder.tvContacts.setText(" "+ObjectUtils.parseString(goodsAddrs.getContacts()));
        itemViewHolder.tvGoodsUnit.setText(" "+ObjectUtils.parseString(goodsAddrs.getGoodsUnit()));
        itemViewHolder.tvAddress.setText(" "+ObjectUtils.parseString(goodsAddrs.getAddress()));
        itemViewHolder.tvMobile.setText(""+getMobile(goodsAddrs));
        itemViewHolder.tvMobile.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        itemViewHolder.tvRemarks.setText(" "+ObjectUtils.parseString(goodsAddrs.getRemarks()));
        itemViewHolder.tvOrderType.setText(" "+ObjectUtils.parseString(goodsType));

        itemViewHolder.tvMobile.setOnClickListener(v -> {
            if (callPhone != null) {
                callPhone.call(itemViewHolder.tvMobile.getText().toString());
            }
        });
    }

    private CallPhone callPhone;

    public void setCallPhone(CallPhone callPhone) {
        this.callPhone = callPhone;
    }

    public interface CallPhone {
        void call(String phone);
    }
}
