package com.yaoguang.appcommon.phone.order.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.ConvertUtils;
import com.yaoguang.lib.common.SpanUtils;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.NodesBean;


/**
 * 订单详情的节点
 * Created by zhongjh on 2017/4/21.
 */
public class OrderDetailNodesAdapter extends BaseLoadMoreRecyclerAdapter<NodesBean, RecyclerView.ViewHolder> {

    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvDate;
        private View line;
        private LinearLayout llRemark;
        private FrameLayout flDian;
        private ImageView ivDian;
        private TextView tvTitle;
        private ImageView ivRemark;
        private TextView tvAddress;

        ItemViewHolder(View view) {
            super(view);
            this.tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            this.ivRemark = (ImageView) view.findViewById(R.id.ivRemark);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.ivDian = (ImageView) view.findViewById(R.id.ivDian);
            this.line = (View) view.findViewById(R.id.line);
            this.llRemark = (LinearLayout) view.findViewById(R.id.llRemark);
            this.flDian = (FrameLayout) view.findViewById(R.id.flDian);
            this.tvDate = (TextView) view.findViewById(R.id.tvDate);
            this.tvTime = (TextView) view.findViewById(R.id.tvTime);
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

        if (!TextUtils.isEmpty(nodesBean.getNodeName()))
            itemViewHolder.tvTitle.setText(nodesBean.getNodeName().trim());
        if (TextUtils.isEmpty(nodesBean.getAddress())) {
            itemViewHolder.tvAddress.setVisibility(View.GONE);
        } else {
            itemViewHolder.tvAddress.setVisibility(View.VISIBLE);
            itemViewHolder.tvAddress.setText(nodesBean.getAddress());
        }

        // 控制线高度
        FrameLayout.LayoutParams params;
        if (position == 0) {
            //  如果是第一个，去掉间隔
            params = new FrameLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(ConvertUtils.dp2px(6), ConvertUtils.dp2px(8), 0, 0);
        } else if (position == (getList().size() - 1)) {
            // 如果是最后一个
            params = new FrameLayout.LayoutParams(1, ConvertUtils.dp2px(5));
            params.setMargins(ConvertUtils.dp2px(6), 0, 0, 0);
        } else {
            // 中间
            params = new FrameLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(ConvertUtils.dp2px(6), 0, 0, 0);
        }
        itemViewHolder.line.setLayoutParams(params);

        // 标红
        //判断是否完成
        if (nodesBean.isHuangDian()) {
            itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), R.color.orange500));
            // 如果下一个有记录
            NodesBean item = getItem(position + 1);
            if (item != null && item.isHuangDian() || getList().size() - 1 == position) {
                itemViewHolder.line.setBackgroundColor(UiUtils.getColor(R.color.orange500));
            } else {
                itemViewHolder.line.setBackgroundColor(UiUtils.getColor(R.color.line_grey));
            }
            itemViewHolder.ivDian.setImageResource(R.drawable.ic_huangdian);
        } else {
            itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), R.color.dark_gray));
            itemViewHolder.line.setBackgroundColor(UiUtils.getColor(R.color.line_grey));
            itemViewHolder.ivDian.setImageResource(R.drawable.ic_huidain);
        }

        if (nodesBean.getDetailFlag() != null && nodesBean.getDetailFlag() == 1 && nodesBean.getDetailSuccess() != null && nodesBean.getDetailSuccess() == 1) {
            itemViewHolder.ivRemark.setVisibility(View.VISIBLE);
            itemViewHolder.llRemark.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    mOnItemClickListener.onItemClick(v, nodesBean, position);
                }
            });
        } else {
            itemViewHolder.llRemark.setOnClickListener(null);
            itemViewHolder.ivRemark.setVisibility(View.GONE);
        }

        handlerTime(itemViewHolder.tvTime, itemViewHolder.tvDate, nodesBean);
    }

    /**
     * 处理节点时间
     *
     * @param tvTime
     * @param tvDate
     * @param nodesBean
     */
    private boolean handlerTime(TextView tvTime, TextView tvDate, NodesBean nodesBean) {
        if (TextUtils.isEmpty(nodesBean.getUpdated())) {
            tvTime.setText("");
            tvDate.setText("");
            return false;
        }
        String[] time = nodesBean.getUpdated().split(" ");
        SpanUtils a = new SpanUtils();
        time[1] = time[1].substring(0, 5);
        if (nodesBean.isHuangDian()) {
            a.append(time[1]);
            a.setForegroundColor(ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), R.color.black));
        } else {
            a.append(time[1]);
            a.setForegroundColor(ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), R.color.dark_gray));
        }
        tvTime.setText(a.create());
        tvDate.setText(time[0]);

        return true;
    }
}
