package com.yaoguang.driver.phone.my.authentication.result.adater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.driver.AuthenticationResultItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 韦理英
 * on 2017/6/12 0012.
 *
 * Update by zhongjh
 * Data 2018/3/15
 * 认证通过的表格
 *
 */

public class AuthenticationResultAdapter extends BaseLoadMoreRecyclerAdapter<AuthenticationResultItem, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_authentication_result, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, getItem(adapterPosition), adapterPosition);
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        AuthenticationResultItem myItem = getList().get(position);

        holder.tvName.setText(myItem.getTitle());
        holder.ivIcon.setVisibility(View.GONE);
        holder.ivRight.setVisibility(View.GONE);
        holder.tvValue.setText(myItem.getValue());

        if (myItem.getTitle().equals("品牌型号")) {
            holder.dividers.setVisibility(View.VISIBLE);
            holder.dividers.setBackgroundColor(UiUtils.getColor(R.color.window_background));
        } else if (getList().size() - 1 != position) {
            holder.dividers.setVisibility(View.VISIBLE);
            holder.dividers.setBackgroundColor(UiUtils.getColor(R.color.white));
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dividers)
        LinearLayout dividers;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.ivRight)
        ImageView ivRight;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvValue)
        TextView tvValue;
        @BindView(R.id.llRow)
        LinearLayout llRow;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
