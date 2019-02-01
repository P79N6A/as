package com.yaoguang.company.businessorder2.common.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 机构条件的适配器
 * Created by zhongjh on 2018/10/31.
 */
public class ConditionOfficeAdapter extends BaseLoadMoreRecyclerAdapter<SysCondition, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order_list_office, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();

            if (position == 0) {
                // 如果是第一个，那么其他就清空
                for (int i = 1; i < list.size(); i++) {
                    list.get(i).setCheck(false);
                }
                this.notifyDataSetChanged();
            }
            if (position != RecyclerView.NO_POSITION) {
                if (ObjectUtils.parseString(holder.imgCheck.getTag(), "0").equals("0")) {
                    holder.imgCheck.setImageResource(R.drawable.ic_ymr_yellow);
                    holder.imgCheck.setTag("1");
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setCheck(false);
                    }
                    list.get(position).setCheck(true);
                    this.notifyDataSetChanged();
                } else {
                    holder.imgCheck.setImageResource(R.drawable.ic_ymr_empty);
                    holder.imgCheck.setTag("0");
                    list.get(position).setCheck(false);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SysCondition myItem = getList().get(position);
        viewHolder.tvTitle.setText(myItem.getDisplayName());
        if (list.get(position).isCheck()) {
            viewHolder.imgCheck.setImageResource(R.drawable.ic_ymr_yellow);
            viewHolder.imgCheck.setTag("1");
        } else {
            viewHolder.imgCheck.setImageResource(R.drawable.ic_ymr_empty);
            viewHolder.imgCheck.setTag("0");
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public ImageView imgCheck;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.imgCheck = (ImageView) rootView.findViewById(R.id.imgCheck);
        }

    }
}
