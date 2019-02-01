package com.yaoguang.company.businessorder2.common.distributeleaflets.transferCancelFragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.company.ViewTransferCancel;
import com.yaoguang.greendao.entity.company.ViewTransferCancelDetail;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 机构条件的适配器
 * Created by zhongjh on 2018/10/31.
 */
public class TransferCancelAdapter extends BaseLoadMoreRecyclerAdapter<ViewTransferCancelDetail, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_transfer_cancel, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (ObjectUtils.parseString(list.get(position).getIsCheck(),"0").equals("0")){
                list.get(position).setIsCheck("1");
            }else{
                list.get(position).setIsCheck("0");
            }
            notifyDataSetChanged();
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ViewTransferCancelDetail myItem = getList().get(position);
        viewHolder.tvTitle.setText(myItem.getToCompanyName());
        viewHolder.tvTitle2.setText(myItem.getServiceType() == 0 ? "装货" : "送货" + "派车");
        if (ObjectUtils.parseString(list.get(position).getIsCheck(), "0").equals("1")) {
            viewHolder.imgCheck.setImageResource(R.drawable.ic_big_bossyellow);
            viewHolder.imgCheck.setTag("1");
        } else {
            viewHolder.imgCheck.setImageResource(R.drawable.ic_big_bossempty);
            viewHolder.imgCheck.setTag("0");
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvTitle2;
        public ImageView imgCheck;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvTitle2 = rootView.findViewById(R.id.tvTitle2);
            this.imgCheck = rootView.findViewById(R.id.imgCheck);
        }

    }
}
