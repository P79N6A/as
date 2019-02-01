package com.yaoguang.company.businessorder2.common.list.adapter;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.widget.edittext.ClearEditText;

/**
 * 时间条件的适配器
 * Created by zhongjh on 2018/10/31.
 */
public class ConditionStringAdapter extends BaseLoadMoreRecyclerAdapter<SysCondition, RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order_list_string, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final int position = holder.getAdapterPosition();
                list.get(position).setInputValue(s.toString());
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SysCondition myItem = getList().get(position);
        viewHolder.tvTitle.setText(myItem.getDisplayName());
        viewHolder.etSearch.setText(ObjectUtils.parseString(myItem.getInputValue()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public ClearEditText etSearch;
        public RelativeLayout rlShipper;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.etSearch = rootView.findViewById(R.id.etSearch);
            this.rlShipper = rootView.findViewById(R.id.rlShipper);
        }

    }



}
