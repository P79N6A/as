package com.yaoguang.driver.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.common.appcommon.utils.AllSelectDelete;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.widget.WarpLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 订单列表：待接单
 * Created by wly on 2017/5/9.
 */

public class MessageOrderChildAdapterWait extends OrderChildAdapter {
    private final Context mContext;

    public MessageOrderChildAdapterWait(Context context) {
        super(context);
        mContext = context;
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

    @Override
    public void onBindItemViewHolderCustom(ItemViewHolder itemViewHolder, int position) {
        ItemViewHolderCustom holder = (ItemViewHolderCustom) itemViewHolder;
        Order order = getItem(position);

        // 是否已读未读
        if (order.getDriverOrderMsg().getFlag() == 0) { //  是否已读（0：未读 1：已读）
            holder.messageFlag.setVisibility(View.VISIBLE);
        } else {
            holder.messageFlag.setVisibility(View.GONE);
        }
        // 处理 派，套，拼，告，改，关
        if (order.getOrderMarkList() != null)
            Flowable.just(order.getOrderMarkList())
                .flatMap(Flowable::fromIterable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    WarpLinearLayout.LayoutParams params = new WarpLinearLayout.LayoutParams(
                            WarpLinearLayout.LayoutParams.WRAP_CONTENT, WarpLinearLayout.LayoutParams.WRAP_CONTENT);
                    ImageView iv = new ImageView(mContext);
                    iv.setLayoutParams(params);
                    iv.setImageResource(integer);
                    itemViewHolder.llOrderMark.addView(iv);
                });

        AllSelectDelete.adappterSetting(this, holder.flSelect, holder.llView, holder.cbSelect, holder);
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
