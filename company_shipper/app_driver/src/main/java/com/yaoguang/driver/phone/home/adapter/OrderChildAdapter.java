package com.yaoguang.driver.phone.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.adapter.AllSelectAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.imagepicker.GlideManager;

/**
 * 订单适配器 - 抽象类，让其他类继承他，添加新的赋值com.android.tools.profilers.cpu.BottomUpNode@d566342
 * Created by wly on 2017/4/19.
 * update 2018-05-11
 */
public abstract class OrderChildAdapter<T> extends AllSelectAdapter<T, RecyclerView.ViewHolder> {

    private Context context;

    public OrderChildAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = onCreateItemViewHolderCustom(parent);

        final ItemViewHolder holder = onCreateItemViewHolderCustom(view);
        assert view != null;
        view.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, OrderChildAdapter.this.getList().get(position), position);
            }
        });

        onItemBtnRegisterClick(view, holder);
        onItemBtnAcceptClick(view, holder);
        onItemBtnRefuseClick(view, holder);

        return holder;
    }

    /**
     * 有关订单消息数据的绑定
     *
     * @param itemViewHolder        view界面
     * @param driverOrderMsgWrapper 实体类
     */
    public void bindItemViewHolder(ItemViewHolder itemViewHolder, DriverOrderMsgWrapper driverOrderMsgWrapper) {

        bindItemViewHolder(itemViewHolder, driverOrderMsgWrapper.getDriverOrderWrapper());

        // 处理 派，改，关
        if (driverOrderMsgWrapper.getMsgType() != null) {
            switch (driverOrderMsgWrapper.getMsgType()) {
                case "0":
                    itemViewHolder.imgPai.setVisibility(View.VISIBLE);
                    itemViewHolder.imgGai.setVisibility(View.GONE);
                    itemViewHolder.imgGuan.setVisibility(View.GONE);
                    break;
                case "1":
                    itemViewHolder.imgPai.setVisibility(View.GONE);
                    itemViewHolder.imgGai.setVisibility(View.VISIBLE);
                    itemViewHolder.imgGuan.setVisibility(View.GONE);
                    break;
                case "2":
                    itemViewHolder.imgPai.setVisibility(View.GONE);
                    itemViewHolder.imgGai.setVisibility(View.GONE);
                    itemViewHolder.imgGuan.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            itemViewHolder.imgPai.setVisibility(View.GONE);
            itemViewHolder.imgGai.setVisibility(View.GONE);
            itemViewHolder.imgGuan.setVisibility(View.GONE);
        }
    }

    /**
     * 有关订单数据的绑定
     *
     * @param itemViewHolder     view界面
     * @param driverOrderWrapper 实体类
     */
    public void bindItemViewHolder(ItemViewHolder itemViewHolder, DriverOrderWrapper driverOrderWrapper) {
        // 订单的绑定
        itemViewHolder.tvId.setText(driverOrderWrapper.getOrderId());
        itemViewHolder.tvCompanyName.setText(driverOrderWrapper.getEntrustCompany());
        itemViewHolder.tvDriverOrderType.setText(ObjectUtils.parseString(driverOrderWrapper.getOrderType()));
        itemViewHolder.tvVehiclePrice.setText("¥" + ObjectUtils.formatNumber2(driverOrderWrapper.getVehiclePrice(), 0));
        itemViewHolder.tvContQty.setText(driverOrderWrapper.getContQty());
        itemViewHolder.tvShipCompany.setText(shipCompany(driverOrderWrapper));
        itemViewHolder.tvDeliveryPlaces.setText(driverOrderWrapper.getDeliveryTime());
        itemViewHolder.tvCreated.setText(driverOrderWrapper.getCreateTime());
        if (!TextUtils.isEmpty(GlideManager.getImageUrl(driverOrderWrapper.getLogo())))
            GlideManager.getInstance().withRounded(context, GlideManager.getImageUrl(driverOrderWrapper.getLogo()), itemViewHolder.ivLogo, R.drawable.ic_qymrtx);
        itemViewHolder.ivPhone.setOnClickListener(v -> {
            if (callPhone != null) {
                callPhone.click(driverOrderWrapper);
            }
        });
        itemViewHolder.tvDeliveryRoute.setText(driverOrderWrapper.getSpannableStringBuilder());

        // 套拼
        if (driverOrderWrapper.getMark() != null && !driverOrderWrapper.getMark().equals("0") ) {
            switch (driverOrderWrapper.getMark()) {
                case "2":
                    itemViewHolder.imgTao.setVisibility(View.VISIBLE);
                    itemViewHolder.imgPin.setVisibility(View.GONE);
                    break;
                case "4":
                    itemViewHolder.imgTao.setVisibility(View.GONE);
                    itemViewHolder.imgPin.setVisibility(View.VISIBLE);
                    break;
                case "6":
                    itemViewHolder.imgTao.setVisibility(View.VISIBLE);
                    itemViewHolder.imgPin.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            itemViewHolder.imgTao.setVisibility(View.GONE);
            itemViewHolder.imgPin.setVisibility(View.GONE);
        }

        // 订单状态 0:待接单 1：已接单 2：已完成 3:关闭 4:拒绝
        switch (driverOrderWrapper.getOrderStatus()) {
            case "0":
                itemViewHolder.ivOrderStatus.setImageResource(R.drawable.ic_djdtb_01);
                break;
            case "1":
                itemViewHolder.ivOrderStatus.setImageResource(R.drawable.ic_yjdtb_01);
                break;
            case "2":
                itemViewHolder.ivOrderStatus.setImageResource(R.drawable.ic_finish);
                break;
            case "3":
                itemViewHolder.ivOrderStatus.setImageResource(R.drawable.ic_yiguanbi);
                break;
            case "4":
                itemViewHolder.ivOrderStatus.setImageResource(R.drawable.ic_yiguanbi);
                break;
        }

    }

    private CallPhone callPhone;

    public void setCallPhone(CallPhone callPhone) {
        this.callPhone = callPhone;
    }

    public interface CallPhone {
        void click(DriverOrderWrapper driverOrderWrapper);
    }

    /**
     * 船公司处理
     *
     * @param driverOrderWrapper 订单
     */
    private String shipCompany(DriverOrderWrapper driverOrderWrapper) {
        return driverOrderWrapper.getShipCompany();
    }

    /**
     * 使用自定义的View
     *
     * @param parent 父窗体
     * @return view
     */
    public abstract View onCreateItemViewHolderCustom(ViewGroup parent);

    /**
     * 使用自定义的Holder
     *
     * @param view view
     * @return holder
     */
    public abstract ItemViewHolder onCreateItemViewHolderCustom(View view);

    /**
     * 使用自定义的按钮事件:进度更新，现在出车
     *
     * @param view   view
     * @param holder holder
     */
    public abstract void onItemBtnRegisterClick(View view, ItemViewHolder holder);

    /**
     * 使用自定义的按钮事件:接受
     *
     * @param view   view
     * @param holder holder
     */
    public abstract void onItemBtnAcceptClick(View view, ItemViewHolder holder);

    /**
     * 使用自定义的按钮事件:拒绝
     *
     * @param view   view
     * @param holder holder
     */
    public abstract void onItemBtnRefuseClick(View view, ItemViewHolder holder);

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public ImageView ivPhone;
        public ImageView ivLogo;
        public TextView tvDeliveryRoute;
        public LinearLayout llOrderMark;
        public TextView tvCompanyName;
        public TextView tvDriverOrderType;
        public TextView tvVehiclePrice;
        public TextView tvContQty;
        public TextView tvShipCompany;
        public TextView tvDeliveryPlaces;
        public TextView tvCreated;
        public ImageView ivOrderStatus;
        public ImageView imgPai;
        public ImageView imgGai;
        public ImageView imgGuan;
        public ImageView imgTao;
        public ImageView imgPin;

        public ItemViewHolder(View view) {
            super(view);
            tvId = view
                    .findViewById(R.id.tvId);
            llOrderMark = view
                    .findViewById(R.id.llOrderMark);
            tvCreated = view
                    .findViewById(R.id.tvCreated);
            ivPhone = view
                    .findViewById(R.id.ivPhone);
            ivOrderStatus = view
                    .findViewById(R.id.ivStatus);
            ivLogo = view
                    .findViewById(R.id.ivLogo);
            tvDeliveryRoute = view
                    .findViewById(R.id.tvDeliveryRoute);
            tvCompanyName = view
                    .findViewById(R.id.tvCompanyName);
            tvDriverOrderType = view
                    .findViewById(R.id.tvDriverOrderType);
            tvVehiclePrice = view
                    .findViewById(R.id.tvVehiclePrice);
            tvContQty = view
                    .findViewById(R.id.tvContQty);
            tvShipCompany = view
                    .findViewById(R.id.tvShipCompany);
            tvDeliveryPlaces = view
                    .findViewById(R.id.tvDeliveryPlaces);
            imgPai = view
                    .findViewById(R.id.imgPai);
            imgGai = view
                    .findViewById(R.id.imgGai);
            imgGuan = view
                    .findViewById(R.id.imgGuan);
            imgTao = view
                    .findViewById(R.id.imgTao);
            imgPin = view
                    .findViewById(R.id.imgPin);
        }
    }


}
