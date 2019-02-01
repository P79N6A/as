package com.yaoguang.driver.phone.order.child.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.imagepicker.GlideManager;

import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.ACCEPT;
import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.FINISH;
import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.REFUSE;
import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.WAIT;

/**
 * 订单
 * Created by zhongjh on 2018/7/2.
 */

public class OrderChildAdapter extends BaseLoadMoreRecyclerAdapter<DriverOrderWrapper, RecyclerView.ViewHolder> {

    private Context mContext;
    private int mType; // 订单类型，0代表待确认、1代表已确认、2代表已完成、3代表已关闭

    public OrderChildAdapter(Context context, int type) {
        this.mContext = context;
        this.mType = type;
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnRecyclerViewItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_common2, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        // 点击事件
        view.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, getItem(position), position);
            }
        });

        // 编辑地址事件
        viewHolder.imgEditAddless.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final int position = viewHolder.getAdapterPosition();
                if (mOnRecyclerViewItemClickListener != null)
                    mOnRecyclerViewItemClickListener.onItemEditAddressClick(v, getItem(position), position);
            }
        });

        // 判断类型
        switch (mType) {
            // 待确认的订单
            case WAIT:
                // 接受事件
                viewHolder.tvAccept.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = viewHolder.getAdapterPosition();
                        if (mOnRecyclerViewItemClickListener != null)
                            mOnRecyclerViewItemClickListener.onItemReceiptClick(v, getItem(position), position);
                    }
                });
                // 拒绝事件
                viewHolder.tvRefuse.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = viewHolder.getAdapterPosition();
                        if (mOnRecyclerViewItemClickListener != null)
                            mOnRecyclerViewItemClickListener.onItemRefuseClick(v, getItem(position), position);
                    }
                });
                break;
            // 已确认的订单
            case ACCEPT:
                // 隐藏改按钮，只留下一个
                viewHolder.tvRefuse.setVisibility(View.GONE);
                viewHolder.tvAccept.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = viewHolder.getAdapterPosition();
                        if (getItem(position).getVehicleFlag().equals("1")) {
                            if (mOnRecyclerViewItemClickListener != null)
                                // 进度更新事件
                                mOnRecyclerViewItemClickListener.onItemProgressUpdateClick(v, getItem(position), position);
                        } else {
                            if (mOnRecyclerViewItemClickListener != null)
                                // 出车事件
                                mOnRecyclerViewItemClickListener.onItemOutCarClick(v, getItem(position), position);
                        }
                    }
                });
                break;
            case FINISH:
                viewHolder.tvRefuse.setVisibility(View.GONE);
                viewHolder.tvAccept.setVisibility(View.GONE);
                break;
            case REFUSE:
                viewHolder.tvRefuse.setVisibility(View.GONE);
                viewHolder.tvAccept.setVisibility(View.GONE);
                break;
        }
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DriverOrderWrapper driverOrderWrapper = getList().get(position);

        // 订单的绑定
        viewHolder.tvCompanyName.setText(driverOrderWrapper.getEntrustCompany());
        viewHolder.tvDriverOrderType.setText(ObjectUtils.parseString(driverOrderWrapper.getOrderType()));
        viewHolder.tvVehiclePrice.setText("¥" + ObjectUtils.formatNumber2(driverOrderWrapper.getVehiclePrice(), 0));
        viewHolder.tvContQty.setText(driverOrderWrapper.getContQty());
        viewHolder.tvShipCompany.setText(driverOrderWrapper.getShipCompany());
        viewHolder.tvDeliveryPlaces.setText(driverOrderWrapper.getDeliveryTime());
        if (!TextUtils.isEmpty(GlideManager.getImageUrl(driverOrderWrapper.getLogo())))
            GlideManager.getInstance().withRounded(mContext, GlideManager.getImageUrl(driverOrderWrapper.getLogo()), viewHolder.imgLogo, R.drawable.ic_qymrtx);
        viewHolder.tvDeliveryRoute.setText(driverOrderWrapper.getSpannableStringBuilder());

        // 判断类型
        switch (mType) {
            case ACCEPT:
                if (driverOrderWrapper.getVehicleFlag().equals("1")) {
                    viewHolder.tvAccept.setBackgroundResource(R.drawable.background_shape_2_5_null_primary);
                    viewHolder.tvAccept.setText("当前任务进度");
                    viewHolder.tvAccept.setTextColor(mContext.getResources().getColor(R.color.primary));
                } else {
                    viewHolder.tvAccept.setBackgroundResource(R.drawable.background_shape_2_5_green);
                    viewHolder.tvAccept.setText("现在出车");
                    viewHolder.tvAccept.setTextColor(mContext.getResources().getColor(R.color.green_primary));
                }
                break;
        }

    }

    // 按钮点击事件
    public interface OnRecyclerViewItemClickListener {

        /**
         * 编辑地址
         */
        void onItemEditAddressClick(View itemView, DriverOrderWrapper item, int position);

        /**
         * 接单
         */
        void onItemReceiptClick(View itemView, DriverOrderWrapper item, int position);

        /**
         * 拒绝
         */
        void onItemRefuseClick(View itemView, DriverOrderWrapper item, int position);

        /**
         * 进度更新
         */
        void onItemProgressUpdateClick(View itemView, DriverOrderWrapper item, int position);

        /**
         * 出车
         */
        void onItemOutCarClick(View itemView, DriverOrderWrapper item, int position);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgLogo;
        public TextView tvCompanyName;
        public TextView tvStatus;
        public View vLine;
        public TextView tvContQty;
        public View vLine2;
        public TextView tvDriverOrderType;
        public TextView tvShipCompany;
        public TextView tvDeliveryPlaces;
        public TextView tvDeliveryRoute;
        public ImageView imgEditAddless;
        public View vLine3;
        public TextView tvVehiclePrice;
        public TextView tvRefuse;
        public TextView tvAccept;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgLogo = rootView.findViewById(R.id.imgLogo);
            this.tvCompanyName = rootView.findViewById(R.id.tvCompanyName);
            this.tvStatus = rootView.findViewById(R.id.tvStatus);
            this.vLine = rootView.findViewById(R.id.vLine);
            this.tvContQty = rootView.findViewById(R.id.tvContQty);
            this.vLine2 = rootView.findViewById(R.id.vLine2);
            this.tvDriverOrderType = rootView.findViewById(R.id.tvDriverOrderType);
            this.tvShipCompany = rootView.findViewById(R.id.tvShipCompany);
            this.tvDeliveryPlaces = rootView.findViewById(R.id.tvDeliveryPlaces);
            this.tvDeliveryRoute = rootView.findViewById(R.id.tvDeliveryRoute);
            this.imgEditAddless = rootView.findViewById(R.id.imgEditAddless);
            this.vLine3 = rootView.findViewById(R.id.vLine3);
            this.tvVehiclePrice = rootView.findViewById(R.id.tvVehiclePrice);
            this.tvRefuse = rootView.findViewById(R.id.tvRefuse);
            this.tvAccept = rootView.findViewById(R.id.tvAccept);
        }

    }
}
