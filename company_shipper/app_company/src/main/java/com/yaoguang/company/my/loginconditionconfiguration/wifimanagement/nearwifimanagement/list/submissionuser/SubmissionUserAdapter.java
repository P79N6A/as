package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.nearwifimanagement.list.submissionuser;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlanLog;
import com.yaoguang.lib.adapter.BaseExpandRecyclerAdapter;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by zhongjh on 2018/10/31.
 */
public class SubmissionUserAdapter extends BaseLoadMoreRecyclerAdapter<UserRecentlyUsedWlanLog, SubmissionUserAdapter.ViewHolder> {

    @Override
    public SubmissionUserAdapter.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_submission_user, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(SubmissionUserAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(getItem(position).getUsername() +" 最后提交于" + getItem(position).getLastUsedTime());

    }


    public static class ViewHolder extends BaseExpandRecyclerAdapter.ViewHolder {
        public View rootView;
        public TextView tvTitle;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        }

    }
}
