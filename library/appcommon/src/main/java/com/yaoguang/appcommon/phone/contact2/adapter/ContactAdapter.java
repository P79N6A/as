package com.yaoguang.appcommon.phone.contact2.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by zhongjh on 2018/4/12.
 */
public class ContactAdapter extends BaseLoadMoreRecyclerAdapter<DriverContactCompany, RecyclerView.ViewHolder> {

    private Fragment mFragment;

    public ContactAdapter(Fragment fragment) {
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
        itemViewHolder.tvCompanyName.setText(getList().get(position).getUserOffice().getName());

        String photo = getList().get(position).getUserOffice().getShopLogo();
        if (!TextUtils.isEmpty(GlideManager.getImageUrl(photo)) && !photo.equals("null")) {
            GlideManager.getInstance().withRounded(mFragment.getContext(), GlideManager.getImageUrl(photo), itemViewHolder.ivPhotoGraph, R.drawable.ic_qymrtx);
        }

        // 判断字段是否变灰
        if (!getList().get(position).getStatus().equals("0")) {
            // 字体变灰色
            itemViewHolder.tvCompanyName.setTextColor(mFragment.getResources().getColor(R.color.gray));
        }else{
            itemViewHolder.tvCompanyName.setTextColor(mFragment.getResources().getColor(R.color.black));
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
