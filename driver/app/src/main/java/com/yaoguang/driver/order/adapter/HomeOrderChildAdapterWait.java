package com.yaoguang.driver.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.widget.WarpLinearLayout;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 订单列表：待接单
 * Created by wly on 2017/5/9.
 */

public class HomeOrderChildAdapterWait extends OrderChildAdapter {
    private final Context mContext;
    private OnRecyclerViewItemClickListener<Order> mOnRecyclerViewItemClickListener = null;
    private View mView;

    public HomeOrderChildAdapterWait(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolderCustom(View view) {
        return new ItemViewHolderCustom(view);
    }

    @Override
    public View onCreateItemViewHolderCustom(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_home, parent, false);
    }

    @Override
    public void onItemBtnRegisterClick(View view, ItemViewHolder holder) {

    }

    @Override
    public void onItemBtnAcceptClick(View view, ItemViewHolder holder) {
    }

    @Override
    public void onItemBtnRefuseClick(final View view, final ItemViewHolder holder) {
        ItemViewHolderCustom itemViewHolderCustom = (ItemViewHolderCustom) holder;

        // 语音播报监听
        itemViewHolderCustom.tvVoice.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnRecyclerViewItemClickListener.onItemBtnVoiceClick(view, getList().get(position), position);
            }
        });
    }

    @Override
    public void onBindItemViewHolderCustom(ItemViewHolder itemViewHolder, int position) {
        ItemViewHolderCustom holder = (ItemViewHolderCustom) itemViewHolder;
        Order order = getItem(position);

        //orderMark(itemViewHolder.llOrderMark, order, true);
        itemViewHolder.llOrderMark.removeAllViews();
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

        //  语音播报
        if (order.getDriverOrderMsg() != null && !TextUtils.isEmpty(order.getDriverOrderMsg().getVoice())) {    // 有语音播报
            holder.tvVoice.setVisibility(View.VISIBLE);
            holder.tvLookButton.setVisibility(View.VISIBLE);
            holder.tvVoice_line.setVisibility(View.VISIBLE);
        } else {    // 没有语音播报
            holder.tvLookButton.setVisibility(View.VISIBLE);

            holder.tvVoice.setVisibility(View.GONE);
            holder.tvVoice_line.setVisibility(View.GONE);
        }

    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnRecyclerViewItemClickListener = listener;
    }

    //按钮点击事件
    public interface OnRecyclerViewItemClickListener<Order> {
        void onItemBtnVoiceClick(View itemView, Order item, int position);
    }

    public class ItemViewHolderCustom extends ItemViewHolder {
        TextView tvLookButton;
        TextView tvVoice;
        View tvVoice_line;

        ItemViewHolderCustom(View view) {
            super(view);
            mView = view;
            tvVoice_line = view.findViewById(R.id.tvVoice_line);
            tvVoice = view.findViewById(R.id.tvVoice);
            tvLookButton = view.findViewById(R.id.tvLookButton);
        }
    }
}
