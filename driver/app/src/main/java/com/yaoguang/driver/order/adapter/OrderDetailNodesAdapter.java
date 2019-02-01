package com.yaoguang.driver.order.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.SpanUtils;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.NodesBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 订单详情的节点
 * Created by wly on 2017/4/21.
 */
public class OrderDetailNodesAdapter extends BaseLoadMoreRecyclerAdapter<NodesBean, RecyclerView.ViewHolder> {

    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.ivRemark)
        ImageView ivRemark;
        @BindView(R.id.ivDian)
        ImageView ivDian;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        Unbinder unbinder;

        ItemViewHolder(View view) {
            super(view);
            unbinder = ButterKnife.bind(this, view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_node, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final NodesBean nodesBean = getList().get(position);

        itemViewHolder.tvTitle.setText(nodesBean.getNodeName().trim());
        if (TextUtils.isEmpty(nodesBean.getAddress())) {
            itemViewHolder.tvAddress.setVisibility(View.GONE);
        } else {
            itemViewHolder.tvAddress.setVisibility(View.VISIBLE);
            itemViewHolder.tvAddress.setText(nodesBean.getAddress());
        }

        //判断是否完成
        if (nodesBean.isHuangDian()) {
            itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), R.color.orange500));
            itemViewHolder.ivDian.setImageResource(R.drawable.ic_huangdian);
        } else {
            itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), R.color.dark_gray));
            itemViewHolder.ivDian.setImageResource(R.drawable.ic_huidain);
        }

        if (nodesBean.getDetailFlag() != null && nodesBean.getDetailFlag() == 1 && nodesBean.getDetailSuccess() != null && nodesBean.getDetailSuccess() == 1) {
            itemViewHolder.ivRemark.setVisibility(View.VISIBLE);
            itemViewHolder.ivRemark.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, nodesBean, position));
        } else {
            itemViewHolder.ivRemark.setVisibility(View.GONE);
        }

        // 设置虚线
//        if (position == 0) {
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ConvertUtils.px2dp(1), FrameLayout.LayoutParams.MATCH_PARENT);
//            params.setMargins(0,ConvertUtils.dp2px(10),0,0);
//            itemViewHolder.line.setLayoutParams(params);
//            itemViewHolder.line.setVisibility(View.INVISIBLE);
//        }
//
//        if (position == (getItemCount() - 1)) {
//            itemViewHolder.line.setVisibility(View.INVISIBLE);
//        }

        handlerTime(itemViewHolder.tvTime, itemViewHolder.tvDate, nodesBean);
    }

    /**
     * 处理节点时间
     * @param tvTime 时间
     * @param tvDate 日期
     * @param nodesBean bean
     */
    private void handlerTime(TextView tvTime, TextView tvDate, NodesBean nodesBean) {
        if (TextUtils.isEmpty(nodesBean.getUpdated())) {
            tvTime.setText("");
            tvDate.setText("");
            return;
        }
        String[] time = nodesBean.getUpdated().split(" ");
        SpanUtils a = new SpanUtils();
        time[1] = time[1].substring(0,5);
        if (nodesBean.isHuangDian()) {
            a.append(time[1]);
            a.setForegroundColor(ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), R.color.black));
        } else {
            a.append(time[1]);
            a.setForegroundColor(ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), R.color.dark_gray));
        }
        tvTime.setText(a.create());
        tvDate.setText(time[0]);

    }
}
