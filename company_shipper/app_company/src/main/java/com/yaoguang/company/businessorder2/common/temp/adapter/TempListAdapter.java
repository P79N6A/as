package com.yaoguang.company.businessorder2.common.temp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.WebOrderTemplateWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 费用适配器
 * Created by zhongjh on 2018/11/10.
 */
public class TempListAdapter extends BaseLoadMoreRecyclerAdapter<WebOrderTemplateWrapper, RecyclerView.ViewHolder> {

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        WebOrderTemplateWrapper webOrderTemplateWrapper = getList().get(position);
        itemViewHolder.tvTitle.setText(ObjectUtils.parseString(webOrderTemplateWrapper.getTemplate().getName()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_temp, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, getList().get(position), position);
            }
        });
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvValue;
        public RelativeLayout rlNew;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvValue = rootView.findViewById(R.id.tvValue);
            this.rlNew = rootView.findViewById(R.id.rlNew);
        }

    }

}
