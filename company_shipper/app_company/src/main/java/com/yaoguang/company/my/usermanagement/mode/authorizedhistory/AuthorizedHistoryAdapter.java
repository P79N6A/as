package com.yaoguang.company.my.usermanagement.mode.authorizedhistory;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.InfoSendOrderTemp;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAuthHistory;
import com.yaoguang.lib.adapter.BaseExpandRecyclerAdapter;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 机构条件的适配器
 * Created by zhongjh on 2018/10/31.
 */
public class AuthorizedHistoryAdapter extends BaseLoadMoreRecyclerAdapter<UserLoginAuthHistory, AuthorizedHistoryAdapter.ViewHolder> {

    @Override
    public AuthorizedHistoryAdapter.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanagement_authorization, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(AuthorizedHistoryAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(getItem(position).getDevice());
        if (getItem(position).getOperation() == 0) {
            holder.tvContent.setText(getItem(position).getOperator() + "于" + getItem(position).getCreated() + "取消授权");
        } else {
            holder.tvContent.setText(getItem(position).getOperator() + "于" + getItem(position).getCreated() + "授权成功");
        }

    }


    public static class ViewHolder extends BaseExpandRecyclerAdapter.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvContent;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
        }

    }
}
