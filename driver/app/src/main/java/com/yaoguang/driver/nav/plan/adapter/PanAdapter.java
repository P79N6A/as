package com.yaoguang.driver.nav.plan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.map.R;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class PanAdapter extends BaseLoadMoreRecyclerAdapter<String, PanAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_navi_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, int position) {
        holder.value.setText(getItem(position));
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView value;

        public ViewHolder(View itemView) {
            super(itemView);
            value = (TextView) itemView.findViewById(R.id.value);
        }
    }
}
