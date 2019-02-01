package com.yaoguang.shipper.offer.logistics.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.imagepicker.GlideManager;

/**
 * 物流公司
 * Created by zhongjh on 2018/4/12.
 */
public class LogisticsAdapter extends BaseLoadMoreRecyclerAdapter<AppPublicInfoWrapper, RecyclerView.ViewHolder> {

    private Fragment mFragment;

    public LogisticsAdapter(Fragment fragment) {
        mFragment = fragment;
    }


    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, getList().get(holder.getAdapterPosition()), holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        itemViewHolder.tvCompanyName.setText(getList().get(position).getFullName());

        String photo = getList().get(position).getRemark1();
        if (!TextUtils.isEmpty(GlideManager.getImageUrl(photo)) && !photo.equals("null")) {
            GlideManager.getInstance().withRounded(mFragment.getContext(), GlideManager.getImageUrl(photo), itemViewHolder.ivPhotoGraph, R.drawable.ic_qymrtx);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView ivPhotoGraph;
        public TextView tvCompanyName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.ivPhotoGraph = rootView.findViewById(R.id.ivPhotoGraph);
            this.tvCompanyName = rootView.findViewById(R.id.tvCompanyName);
        }

    }
}
