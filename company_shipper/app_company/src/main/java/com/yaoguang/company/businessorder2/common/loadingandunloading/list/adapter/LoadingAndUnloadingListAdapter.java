package com.yaoguang.company.businessorder2.common.loadingandunloading.list.adapter;

import android.support.constraint.Barrier;
import android.support.constraint.Guideline;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/10/29.
 */
public class LoadingAndUnloadingListAdapter extends BaseLoadMoreRecyclerAdapter<InfoClientPlace, RecyclerView.ViewHolder> {

    //点击事件
    public interface OnRecyclerViewItemListener {

        // 编辑
        void onItemEditClick(View itemView, InfoClientPlace item, int position);
        // 删除
        void onItemDeleteClick(View itemView, InfoClientPlace item, int position);
        // 选择
        void onItemSubmitClick(View itemView, InfoClientPlace item, int position);
    }

    protected OnRecyclerViewItemListener mOnRecyclerViewItemListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemListener listener) {
        this.mOnRecyclerViewItemListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_and_unloading, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && mOnRecyclerViewItemListener != null) {
                mOnRecyclerViewItemListener.onItemEditClick(view, getItem(adapterPosition), adapterPosition);
            }
        });
        holder.tvEdit.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && mOnRecyclerViewItemListener != null) {
                mOnRecyclerViewItemListener.onItemEditClick(view, getItem(adapterPosition), adapterPosition);
            }
        });
        holder.tvDelete.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && mOnRecyclerViewItemListener != null) {
                mOnRecyclerViewItemListener.onItemDeleteClick(view, getItem(adapterPosition), adapterPosition);
            }
        });
        holder.tvCheck.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && mOnRecyclerViewItemListener != null) {
                mOnRecyclerViewItemListener.onItemSubmitClick(view, getItem(adapterPosition), adapterPosition);
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        itemViewHolder.tvUnitValue.setText(ObjectUtils.parseString(getList().get(position).getConsigneeCompany(),"未知"));
        itemViewHolder.tvAddressValue.setText(ObjectUtils.parseString(getList().get(position).getAddr(),"未知"));
        itemViewHolder.tvRegionValue.setText(ObjectUtils.parseString(getList().get(position).getRegionid(),"未知"));
        itemViewHolder.tvCargoOwnerValue.setText(ObjectUtils.parseString(getList().get(position).getConsigneeId(),"未知"));
        itemViewHolder.tvLinkmanValue.setText(ObjectUtils.parseString(getList().get(position).getLinkman(),"未知"));
        itemViewHolder.tvMobileValue.setText(ObjectUtils.parseString(getList().get(position).getLinkmanMp(),"未知"));
        itemViewHolder.tvPhoneValue.setText(ObjectUtils.parseString(getList().get(position).getLinkmanTel(),"未知"));
        itemViewHolder.tvFaxValue.setText(ObjectUtils.parseString(getList().get(position).getConsigneeCompanyFax(),"未知"));
        itemViewHolder.tvRemarkValue.setText(ObjectUtils.parseString(getList().get(position).getZxRemark(),"未知"));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public View rootView;
        public TextView tvUnitTitle;
        public TextView tvUnitValue;
        public TextView tvAddressTitle;
        public TextView tvAddressValue;
        public TextView tvRegionTitle;
        public TextView tvRegionValue;
        public TextView tvCargoOwnerTitle;
        public TextView tvCargoOwnerValue;
        public Barrier barrierRegionAndCargoOwner;
        public TextView tvLinkmanTitle;
        public TextView tvLinkmanValue;
        public TextView tvMobileTitle;
        public TextView tvMobileValue;
        public Barrier barrierLinkmanAndMobile;
        public TextView tvPhoneTitle;
        public TextView tvPhoneValue;
        public TextView tvFaxTitle;
        public TextView tvFaxValue;
        public TextView tvRemarkTitle;
        public TextView tvRemarkValue;
        public View vLineBottom;
        public TextView tvDelete;
        public TextView tvEdit;
        public TextView tvCheck;
        public Guideline guidelineV50;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvUnitTitle = rootView.findViewById(R.id.tvUnitTitle);
            this.tvUnitValue = rootView.findViewById(R.id.tvUnitValue);
            this.tvAddressTitle = rootView.findViewById(R.id.tvAddressTitle);
            this.tvAddressValue = rootView.findViewById(R.id.tvAddressValue);
            this.tvRegionTitle = rootView.findViewById(R.id.tvRegionTitle);
            this.tvRegionValue = rootView.findViewById(R.id.tvRegionValue);
            this.tvCargoOwnerTitle = rootView.findViewById(R.id.tvCargoOwnerTitle);
            this.tvCargoOwnerValue = rootView.findViewById(R.id.tvCargoOwnerValue);
            this.barrierRegionAndCargoOwner = rootView.findViewById(R.id.barrierRegionAndCargoOwner);
            this.tvLinkmanTitle = rootView.findViewById(R.id.tvLinkmanTitle);
            this.tvLinkmanValue = rootView.findViewById(R.id.tvLinkmanValue);
            this.tvMobileTitle = rootView.findViewById(R.id.tvMobileTitle);
            this.tvMobileValue = rootView.findViewById(R.id.tvMobileValue);
            this.barrierLinkmanAndMobile = rootView.findViewById(R.id.barrierLinkmanAndMobile);
            this.tvPhoneTitle = rootView.findViewById(R.id.tvPhoneTitle);
            this.tvPhoneValue = rootView.findViewById(R.id.tvPhoneValue);
            this.tvFaxTitle = rootView.findViewById(R.id.tvFaxTitle);
            this.tvFaxValue = rootView.findViewById(R.id.tvFaxValue);
            this.tvRemarkTitle = rootView.findViewById(R.id.tvRemarkTitle);
            this.tvRemarkValue = rootView.findViewById(R.id.tvRemarkValue);
            this.vLineBottom = rootView.findViewById(R.id.vLineBottom);
            this.tvDelete = rootView.findViewById(R.id.tvDelete);
            this.tvEdit = rootView.findViewById(R.id.tvEdit);
            this.tvCheck = rootView.findViewById(R.id.tvCheck);
            this.guidelineV50 = rootView.findViewById(R.id.guidelineV50);
        }

    }
}
