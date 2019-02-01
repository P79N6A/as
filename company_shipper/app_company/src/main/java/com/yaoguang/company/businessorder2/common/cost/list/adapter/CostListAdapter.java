package com.yaoguang.company.businessorder2.common.cost.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.AccountFee;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 费用适配器
 * Created by zhongjh on 2018/11/10.
 */
public class CostListAdapter extends BaseLoadMoreRecyclerAdapter<AccountFee, RecyclerView.ViewHolder> {

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        AccountFee accountFee = getList().get(position);
        // 应收应付
        itemViewHolder.tvType.setText(accountFee.getAccoAttr()== 0 ? "应收" : "应付");
        if (accountFee.getAccoAttr() == 0){
            itemViewHolder.imgType.setImageResource(R.drawable.ic_yingshou_qingse);
        }else{
            itemViewHolder.imgType.setImageResource(R.drawable.ic_yingfu_red);
        }
        // 核算对象
        itemViewHolder.tvTitle.setText(ObjectUtils.parseString(accountFee.getShipper(),"未知"));
        // 费用项目
        itemViewHolder.tvProject.setText(ObjectUtils.parseString(accountFee.getFeeId(),"未知"));
        // 计量单位
        itemViewHolder.tvUnit.setText(ObjectUtils.parseString(accountFee.getUnit(),"未知"));
        // 数量
        itemViewHolder.tvNumber.setText(ObjectUtils.parseString(accountFee.getQuantity(),"未知"));
        // 单价（元）
        itemViewHolder.tvUnitPrice.setText(ObjectUtils.parseString(accountFee.getPrice(),"未知"));
        // 总金额（元）
        itemViewHolder.tvTotalAmount.setText(ObjectUtils.parseString(accountFee.getTotalup(),"未知"));
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_cost, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, getList().get(position), position);
            }
        });
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgType;
        public TextView tvType;
        public TextView tvTitle;
        public TextView tvProject;
        public TextView tvUnit;
        public TextView tvNumber;
        public TextView tvUnitPrice;
        public TextView tvTotalAmount;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgType = rootView.findViewById(R.id.imgType);
            this.tvType = rootView.findViewById(R.id.tvType);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvProject = rootView.findViewById(R.id.tvProject);
            this.tvUnit = rootView.findViewById(R.id.tvUnit);
            this.tvNumber = rootView.findViewById(R.id.tvNumber);
            this.tvUnitPrice = rootView.findViewById(R.id.tvUnitPrice);
            this.tvTotalAmount = rootView.findViewById(R.id.tvTotalAmount);
        }

    }

}
