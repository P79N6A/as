package com.yaoguang.appcommon.phone.contact2.contactnew.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.greendao.entity.common.DriverFollowCompany;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * 新的关联
 * Created by zhongjh on 2018/4/12.
 */
public class ContactNewAdapter extends BaseLoadMoreRecyclerAdapter<DriverFollowCompany, RecyclerView.ViewHolder> {

    private Fragment mFragment;

    //点击事件
    public interface OnRecyclerViewAgreenItemClickListener<T> {
        void onItemClick(View itemView, T item, int position);
    }

    protected OnRecyclerViewAgreenItemClickListener mOnAgreenItemClickListener = null;

    public void setOnAgreenItemClickListener(OnRecyclerViewAgreenItemClickListener listener) {
        this.mOnAgreenItemClickListener = listener;
    }

    public ContactNewAdapter(Fragment fragment) {
        mFragment = fragment;
    }


    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_new, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, getList().get(holder.getAdapterPosition()), holder.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
            mOnItemLongClickListener.onItemClick(v, getList().get(holder.getAdapterPosition()), holder.getAdapterPosition());
            return false;
        });
        // 同意
        view.findViewById(R.id.tvAgreen).setOnClickListener(v -> {
            if (mOnAgreenItemClickListener !=null)
                mOnAgreenItemClickListener.onItemClick(v, getList().get(holder.getAdapterPosition()), holder.getAdapterPosition());
        });

        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        String photo = "";
        if (getList().get(position).getUserOffice() != null){
            itemViewHolder.tvCompanyName.setText(getList().get(position).getUserOffice().getName());
            photo = getList().get(position).getUserOffice().getShopLogo();

        }
        if (!TextUtils.isEmpty(GlideManager.getImageUrl(photo)) && !photo.equals("null")) {
            GlideManager.getInstance().withRounded(mFragment.getContext(), GlideManager.getImageUrl(photo), itemViewHolder.ivPhotoGraph, R.drawable.ic_qymrtx);
        }
        // 判断状态.ApplyType=1是被关注方,0是关注发起方。companyFlag=0是待审核，=1是已审核通过

        //(1) 先判断是否已关联
        if (getList().get(position).getCompanyFlag().equals("1")){
            itemViewHolder.tvMessage.setVisibility(View.VISIBLE);
            itemViewHolder.tvMessage.setText("已关联");
            itemViewHolder.tvAgreen.setVisibility(View.GONE);
        }else{
            // (2) 待审核旧判断是等待通过还是自己要同意
            if (getList().get(position).getApplyType().equals("1")){
                itemViewHolder.tvMessage.setVisibility(View.GONE);
                itemViewHolder.tvAgreen.setVisibility(View.VISIBLE);
            }else{
                itemViewHolder.tvMessage.setVisibility(View.VISIBLE);
                itemViewHolder.tvMessage.setText("等待通过");
                itemViewHolder.tvAgreen.setVisibility(View.GONE);
            }
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView ivPhotoGraph;
        public TextView tvCompanyName;
        public FrameLayout flRight;
        public TextView tvMessage;
        public TextView tvAgreen;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.ivPhotoGraph = rootView.findViewById(R.id.ivPhotoGraph);
            this.tvCompanyName = rootView.findViewById(R.id.tvCompanyName);
            this.flRight = rootView.findViewById(R.id.flRight);
            this.tvMessage = rootView.findViewById(R.id.tvMessage);
            this.tvAgreen = rootView.findViewById(R.id.tvAgreen);
        }

    }
}
