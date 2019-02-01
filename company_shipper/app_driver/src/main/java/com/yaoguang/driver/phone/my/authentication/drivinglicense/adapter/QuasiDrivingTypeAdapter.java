package com.yaoguang.driver.phone.my.authentication.drivinglicense.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.driver.R;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：$version
 * 创建日期：2018/02/28
 * 描    述：
 * 准驾车型Adapater
 * =====================================
 */

public class QuasiDrivingTypeAdapter extends BaseLoadMoreRecyclerAdapter<String, RecyclerView.ViewHolder> {
    HashSet qDTypeHashSet = new HashSet();

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_quasi_driving_type_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {

                // 选择
                String name = holder.tvName.getText().toString();
                if (!qDTypeHashSet.contains(name)) {
                    view.setBackgroundResource(R.drawable.oval_orange);
                    holder.tvName.setTextColor(UiUtils.getColor(R.color.orange500));
                    holder.ivSelect.setVisibility(View.VISIBLE);
                    qDTypeHashSet.add(name);
                } else {
                    view.setBackgroundResource(R.drawable.oval_gray);
                    holder.tvName.setTextColor(UiUtils.getColor(R.color.black666));
                    holder.ivSelect.setVisibility(View.GONE);

                    qDTypeHashSet.remove(name);
                }
                mOnItemClickListener.onItemClick(view, getItem(adapterPosition), adapterPosition);
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String name = getList().get(position);

        // 选择
        if (qDTypeHashSet.contains(name)) {
            holder.fLFrameLayout.setBackgroundResource(R.drawable.oval_orange);
            holder.tvName.setTextColor(UiUtils.getColor(R.color.orange500));
            holder.ivSelect.setVisibility(View.VISIBLE);

            qDTypeHashSet.add(name);
        } else {
            holder.fLFrameLayout.setBackgroundResource(R.drawable.oval_gray);
            holder.tvName.setTextColor(UiUtils.getColor(R.color.black666));
            holder.ivSelect.setVisibility(View.GONE);

            qDTypeHashSet.remove(name);
        }

        holder.tvName.setText(name);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.ivSelect)
        ImageView ivSelect;
        @BindView(R.id.fLFrameLayout)
        FrameLayout fLFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public HashSet getqDTypeHashSet() {
        return qDTypeHashSet;
    }
}
