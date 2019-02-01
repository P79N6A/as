package com.yaoguang.appcommon.common.utils.adater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseLoadMoreRecyclerVLayoutAdapter;

import java.util.List;


/**
 * 作    者：韦理英
 * 时    间：2017/9/11 0011.
 * 描    述：XXXXX
 */
public class CommonBottomAdapter extends BaseLoadMoreRecyclerVLayoutAdapter<String, RecyclerView.ViewHolder> {
    LayoutHelper layoutHelper;

    public CommonBottomAdapter(LayoutHelper layoutHelper, List<String> strings) {
        this.layoutHelper = layoutHelper;
        appendToList(strings);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = View.inflate(parent.getContext(), R.layout.view_textview, null);
        final ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemViewHolder != null) {
                    int layoutPosition = itemViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(view, getList().get(layoutPosition), layoutPosition);
                }
            }
        });
        return itemViewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder holder1 = (ItemViewHolder) holder;
        holder1.textView.setText(getList().get(position));
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }


}
