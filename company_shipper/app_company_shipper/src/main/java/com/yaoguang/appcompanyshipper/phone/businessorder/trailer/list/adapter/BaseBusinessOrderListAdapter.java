package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by zhongjh on 2018/9/7.
 */
public abstract class BaseBusinessOrderListAdapter<T> extends BaseLoadMoreRecyclerAdapter<T, RecyclerView.ViewHolder> {

    //点击事件
    public interface OnRecyclerViewItemRLClickListener<T> {
        void onEditClick(View itemView, T item, int position);
    }

    private OnRecyclerViewItemRLClickListener mOnRecyclerViewItemRLClickListener = null;

    public void setOnRecyclerViewItemRLClickListener(OnRecyclerViewItemRLClickListener listener) {
        this.mOnRecyclerViewItemRLClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order_trailer, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, getList().get(position), position);
            }
        });
        holder.lbtnStars.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (mOnRecyclerViewItemRLClickListener != null) {
                    final int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mOnRecyclerViewItemRLClickListener.onEditClick(likeButton, getList().get(position), position);
                    }
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (mOnRecyclerViewItemRLClickListener != null) {
                    final int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mOnRecyclerViewItemRLClickListener.onEditClick(likeButton, getList().get(position), position);
                    }
                }
            }
        });
        return holder;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgType;
        public TextView tvOrderID;
        public ImageView imgStatus;
        public TextView tvDeparture;
        public TextView tvGoodsName;
        public View vLine;
        public TextView tvPeople;
        public TextView tvDestination;
        public TextView tvSonos;
        public TextView tvFee;
        public TextView tvOrderTime;
        public TableLayout tlWait;
        public LikeButton lbtnStars;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgType = (ImageView) rootView.findViewById(R.id.imgType);
            this.tvOrderID = (TextView) rootView.findViewById(R.id.tvOrderID);
            this.imgStatus = (ImageView) rootView.findViewById(R.id.imgStatus);
            this.tvDeparture = (TextView) rootView.findViewById(R.id.tvDeparture);
            this.tvGoodsName = (TextView) rootView.findViewById(R.id.tvGoodsName);
            this.vLine = rootView.findViewById(R.id.vLine);
            this.tvPeople = (TextView) rootView.findViewById(R.id.tvPeople);
            this.tvDestination = (TextView) rootView.findViewById(R.id.tvDestination);
            this.tvSonos = (TextView) rootView.findViewById(R.id.tvSonos);
            this.tvFee = (TextView) rootView.findViewById(R.id.tvFee);
            this.tvOrderTime = (TextView) rootView.findViewById(R.id.tvOrderTime);
            this.tlWait = (TableLayout) rootView.findViewById(R.id.tlWait);
            this.lbtnStars = (LikeButton) rootView.findViewById(R.id.lbtnStars);
        }

    }


}
