package com.yaoguang.company.businessorder2.common.list.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

import java.util.HashMap;

/**
 * Created by zhongjh on 2018/11/21.
 */

public class ConditionConditionAdapter extends BaseLoadMoreRecyclerAdapter<SysCondition, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order_list_condition, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SysCondition myItem = getList().get(position);
        // 判断类型进行相应的显示
        switch (myItem.getAppCustomType()) {
            case "office":
                viewHolder.tvTitle.setText("机构");
                viewHolder.tvValue.setText(myItem.getDisplayName());
                break;
            case "status":
                if (myItem.getInputValue2().equals("特殊") || myItem.getInputValue2().equals("特殊合并")){
                    viewHolder.tvTitle.setText("柜型");
                    viewHolder.tvValue.setText(myItem.getDisplayName());
                }else{
                    viewHolder.tvTitle.setText(myItem.getDisplayName());
                    viewHolder.tvValue.setText(myItem.getInputValue().equals("1") ? "是" : "否");
                }
                break;
            case "date":
                viewHolder.tvTitle.setText(myItem.getDisplayName());
                viewHolder.tvValue.setText(myItem.getInputValue() + " ~ " + myItem.getInputValue2());
                break;
            case "string":
                viewHolder.tvTitle.setText(myItem.getDisplayName());
                viewHolder.tvValue.setText(myItem.getInputValue());
                break;
        }
        viewHolder.imgSelect.setOnClickListener(v -> {
            final int position1 = holder.getAdapterPosition();
            if (position1 != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, getList().get(position1), position1);
            }
            remove(position);
            notifyDataSetChanged();

        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvValue;
        public ImageView imgSelect;
        public RelativeLayout rlShipper;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvValue = rootView.findViewById(R.id.tvValue);
            this.imgSelect = rootView.findViewById(R.id.imgSelect);
            this.rlShipper = rootView.findViewById(R.id.rlShipper);
        }

    }
}