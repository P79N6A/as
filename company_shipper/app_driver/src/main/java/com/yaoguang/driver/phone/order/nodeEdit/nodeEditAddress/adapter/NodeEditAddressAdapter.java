package com.yaoguang.driver.phone.order.nodeEdit.nodeEditAddress.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索框查询后的内容适配器
 * Created by zhongjh on 2017/6/5.
 */
public class NodeEditAddressAdapter extends BaseLoadMoreRecyclerAdapter<Tip, RecyclerView.ViewHolder> {

    public List<Boolean> isChecks = new ArrayList<>();

    public void appendToList(List<Tip> list, boolean isCheck) {
        super.appendToList(list);
        if (isCheck) {
            isChecks.clear();
            for (Tip tip : list) {
                isChecks.add(false);
            }
            isChecks.set(0, true);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvShortName;
        ImageView imgIsCheck;

        ItemViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvShortName = view.findViewById(R.id.tvShortName);
            imgIsCheck = view.findViewById(R.id.imgIsCheck);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_simple, parent, false);
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
        itemViewHolder.tvName.setText(getList().get(position).getName());

        if (ObjectUtils.parseString(getList().get(position).getDistrict()).equals("")) {
            itemViewHolder.tvShortName.setVisibility(View.GONE);
        } else {
            itemViewHolder.tvShortName.setVisibility(View.VISIBLE);
            itemViewHolder.tvShortName.setText(getList().get(position).getDistrict());
        }

        if (isChecks != null && isChecks.size() > 0 && isChecks.get(position) != null && isChecks.get(position)) {
            itemViewHolder.imgIsCheck.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.imgIsCheck.setVisibility(View.GONE);
        }

    }

}
