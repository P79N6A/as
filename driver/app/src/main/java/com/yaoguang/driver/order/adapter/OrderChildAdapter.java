package com.yaoguang.driver.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.common.Glide.impl.GlideManager;
import com.yaoguang.common.adapter.AllSelectAdapter;
import com.yaoguang.common.common.DateUtils;
import com.yaoguang.common.common.ImagesUtil;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.SpanUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.widget.WarpLinearLayout;

import org.reactivestreams.Publisher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 订单适配器 - 抽象类，让其他类继承他，添加新的赋值com.android.tools.profilers.cpu.BottomUpNode@d566342
 * Created by wly on 2017/4/19.
 */
public abstract class OrderChildAdapter extends AllSelectAdapter<Order, RecyclerView.ViewHolder> {

    private Context context;

    OrderChildAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = onCreateItemViewHolderCustom(parent);

        final ItemViewHolder holder = onCreateItemViewHolderCustom(view);
        assert view != null;
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, OrderChildAdapter.this.getList().get(position), position);
            }
        });

        onItemBtnRegisterClick(view, holder);
        onItemBtnAcceptClick(view, holder);
        onItemBtnRefuseClick(view, holder);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final Order order = getList().get(position);

        itemViewHolder.tvId.setText(order.getOrderId());
        itemViewHolder.tvCompanyName.setText(order.getEntrustCompany());
        itemViewHolder.tvDriverOrderType.setText(ObjectUtils.parseString(order.getOrderType()));
        itemViewHolder.tvVehiclePrice.setText("¥" + ObjectUtils.formatNumber2(order.getVehiclePrice(), 0));
        itemViewHolder.tvContQty.setText(order.getContQty());
        itemViewHolder.tvShipCompany.setText(shipCompany(order));
        itemViewHolder.tvDeliveryPlaces.setText(order.getDeliveryTime());
        itemViewHolder.tvCreated.setText(order.getCreateTime());
        if (!TextUtils.isEmpty(ImagesUtil.getImageUrl(order.getLogo())))
            GlideManager.getInstance().withRounded(context, ImagesUtil.getImageUrl(order.getLogo()), itemViewHolder.ivLogo, R.drawable.ic_qymrtx);
        itemViewHolder.ivPhone.setOnClickListener(v -> {
            if (callPhone != null) {
                callPhone.click(order);
            }
        });
        itemViewHolder.tvDeliveryRoute.setText(order.getSpannableStringBuilder());
//        wllDeliveryRoute(itemViewHolder.tvDeliveryRoute, order.getDeliveryRoute(), context);

        itemViewHolder.llOrderMark.removeAllViews();
        // 处理派告关
        if (order.getDriverOrderMsg() != null && order.getDriverOrderMsg().getMsgTypeRes() != null) {
            WarpLinearLayout.LayoutParams params = new WarpLinearLayout.LayoutParams(
                    WarpLinearLayout.LayoutParams.WRAP_CONTENT, WarpLinearLayout.LayoutParams.WRAP_CONTENT);
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(params);
            iv.setImageResource(order.getDriverOrderMsg().getMsgTypeRes());
            itemViewHolder.llOrderMark.addView(iv);
        }

        onBindItemViewHolderCustom(itemViewHolder, position);

        // 订单状态 0:待接单 1：已接单 2：已完成 3:关闭
        switch (order.getOrderStatus()) {
            case 0:
                itemViewHolder.ivOrderStatus.setImageResource(R.drawable.ic_djdtb_01);
                break;
            case 1:
                itemViewHolder.ivOrderStatus.setImageResource(R.drawable.ic_yjdtb_01);
                break;
            case 2:
                itemViewHolder.ivOrderStatus.setImageResource(R.drawable.ic_finish);
                break;
            case 3:
            case 4:
                itemViewHolder.ivOrderStatus.setImageResource(R.drawable.ic_yiguanbi);
                break;
        }
        itemViewHolder.ivOrderStatus.setVisibility(View.VISIBLE);
    }

    private CallPhone callPhone;

    public void setCallPhone(CallPhone callPhone) {
        this.callPhone = callPhone;
    }

    public interface CallPhone {
        void click(Order order);
    }

    /**
     * 船公司处理
     *
     * @param order 订单
     */
    private String shipCompany(Order order) {
        return order.getShipCompany();
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

    /**
     * 使用自定义的赋值
     *
     * @param itemViewHolder holder
     * @param position       索引
     */
    public abstract void onBindItemViewHolderCustom(ItemViewHolder itemViewHolder, int position);

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        ImageView ivPhone;
        ImageView ivLogo;
        TextView tvDeliveryRoute;
        LinearLayout llOrderMark;
        TextView tvCompanyName;
        TextView tvDriverOrderType;
        TextView tvVehiclePrice;
        TextView tvContQty;
        TextView tvShipCompany;
        TextView tvDeliveryPlaces;
        TextView tvCreated;
        ImageView ivOrderStatus;

        ItemViewHolder(View view) {
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
        }
    }


}
