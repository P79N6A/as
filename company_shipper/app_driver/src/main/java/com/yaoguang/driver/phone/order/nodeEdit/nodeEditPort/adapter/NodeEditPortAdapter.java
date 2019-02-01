package com.yaoguang.driver.phone.order.nodeEdit.nodeEditPort.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.common.InfoDock;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhongjh on 2018/7/9.
 */
public class NodeEditPortAdapter extends BaseLoadMoreRecyclerAdapter<InfoDock, RecyclerView.ViewHolder> {

    public List<Boolean> isChecks = new ArrayList<>();

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        ItemViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_node_edit_port, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final int position = holder.getAdapterPosition();
                if (isChecks != null && isChecks.size() > 0) {
                    for (int i = 0; i < isChecks.size(); i++) {
                        isChecks.set(i, false);
                    }
                    isChecks.set(position, true);
                }
                if (position != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(v, getList().get(position), position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvName.setText(getList().get(position).getDockId());
    }


}
