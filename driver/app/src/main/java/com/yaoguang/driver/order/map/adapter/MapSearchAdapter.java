package com.yaoguang.driver.order.map.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.driver.R;
import com.yaoguang.driver.nav.search.model.SearchKeyword;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/8 0008.
 * 版    权：
 */
public class MapSearchAdapter extends BaseLoadMoreRecyclerAdapter<SearchKeyword, RecyclerView.ViewHolder> {

    private Context context;

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_searchkeyword, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, MapSearchAdapter.this.getList().get(position), position);
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder holder1 = (ItemViewHolder) holder;
        SearchKeyword keyword = getList().get(position);
        String name;
        if (keyword.getType() == 0) { // 默认显示
            name = keyword.getId() + "." + keyword.getName();
            holder1.ivIcon.setVisibility(View.GONE);
            holder1.tvKm.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else { // 历史搜索和历史点击
            if (keyword.getType() == 1) {
                holder1.ivIcon.setImageResource(R.drawable.ic_dingwei);
            }
            holder1.ivIcon.setVisibility(View.VISIBLE);
            holder1.tvKm.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_xjt), null, null, null);
            holder1.tvKm.setText("");
            name = keyword.getName();
        }
        holder1.tvTitle.setText(name);
        if (!TextUtils.isEmpty(keyword.getAddress())) {
            holder1.tvAddress.setText(keyword.getAddress());
        } else {
            holder1.tvAddress.setVisibility(View.GONE);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvKm;

        ItemViewHolder(View view) {
            super(view);
            this.tvKm = view.findViewById(R.id.tvKm);
            this.tvAddress = view.findViewById(R.id.tvAddress);
            this.tvTitle = view.findViewById(R.id.tvTitle);
            this.ivIcon = view.findViewById(R.id.ivIcon);
        }
    }
}
