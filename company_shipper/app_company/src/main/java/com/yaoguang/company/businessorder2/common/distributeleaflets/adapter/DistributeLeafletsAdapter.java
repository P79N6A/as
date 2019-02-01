package com.yaoguang.company.businessorder2.common.distributeleaflets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.company.InfoSendOrderTemp;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 机构条件的适配器
 * Created by zhongjh on 2018/10/31.
 */
public class DistributeLeafletsAdapter extends BaseLoadMoreRecyclerAdapter<InfoSendOrderTemp, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_transfer_cancel, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();

            if (position == 0) {
                // 如果是第一个，那么其他就清空
                for (int i = 1; i < list.size(); i++) {
                    list.get(i).setIsCheck("0");
                }
                this.notifyDataSetChanged();
            }
            if (position != RecyclerView.NO_POSITION) {
                if (ObjectUtils.parseString(holder.imgCheck.getTag(), "0").equals("0")) {
                    holder.imgCheck.setImageResource(R.drawable.ic_ymr_yellow);
                    holder.imgCheck.setTag("1");
                    list.get(position).setIsCheck("1");
                    if (position != 0) {
                        // 取消无限
                        list.get(0).setIsCheck("0");
                        this.notifyItemChanged(0);
                    }
                } else {
                    holder.imgCheck.setImageResource(R.drawable.ic_ymr_empty);
                    holder.imgCheck.setTag("0");
                    list.get(position).setIsCheck("0");
                }
            }
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, getItem(position), position);
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        InfoSendOrderTemp myItem = getList().get(position);
        viewHolder.tvTitle.setText(myItem.getName());
        if (ObjectUtils.parseString(list.get(position).getIsCheck(), "0").equals("1")) {
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
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.imgCheck = rootView.findViewById(R.id.imgCheck);
        }

    }
}
