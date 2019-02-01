package com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.greendao.entity.Container;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 箱子成型成量
 * Created by zhongjh on 2017/5/31.
 */
public class ContainerAdapter extends BaseLoadMoreRecyclerAdapter<Container, RecyclerView.ViewHolder> {

    boolean enable = true;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private void setCount(int position, int count) {
        getList().get(position).setCount(count);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvContainer;
        public EditText etContainer;
        public ImageView imgAddFu;
        public ImageView imgRemoveTitle;
        public TextView tvAddTitle;
        public LinearLayout llAddTitle;
        public TextView tvCabinetType0;
        public LinearLayout llContainerTitle;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvContainer = (TextView) rootView.findViewById(R.id.tvContainer);
            this.etContainer = (EditText) rootView.findViewById(R.id.etContainer);
            this.imgAddFu = (ImageView) rootView.findViewById(R.id.imgAddFu);
            this.imgRemoveTitle = (ImageView) rootView.findViewById(R.id.imgRemoveTitle);
            this.tvAddTitle = (TextView) rootView.findViewById(R.id.tvAddTitle);
            this.llAddTitle = (LinearLayout) rootView.findViewById(R.id.llAddTitle);
            this.tvCabinetType0 = (TextView) rootView.findViewById(R.id.tvCabinetType0);
            this.llContainerTitle = (LinearLayout) rootView.findViewById(R.id.llContainerTitle);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        if (enable) {
            //删除
            holder.llAddTitle.setOnClickListener(v -> {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                        mOnRecyclerViewItemRLClickListener.onRlRemoveTitleClick(v, getList().get(position), position);
                }
            });
            holder.llContainerTitle.setOnClickListener(v -> {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                        mOnRecyclerViewItemRLClickListener.onRlContainerTitleClick(v, holder.tvCabinetType0, getList().get(position), position);
                }
            });
            holder.etContainer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    final int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        setCount(position, ObjectUtils.parseInt(s.toString()));
                    }

                }
            });
        } else {
            holder.llAddTitle.setOnClickListener(null);
            holder.llContainerTitle.setOnClickListener(null);
            holder.etContainer.setEnabled(false);
            holder.tvCabinetType0.setTextColor(view.getResources().getColor(R.color.hint_color));
        }
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvCabinetType0.setText(getList().get(position).getTitle().split("\\*")[0]);
        itemViewHolder.etContainer.setText(ObjectUtils.parseString(getList().get(position).getCount()));
    }

    //点击事件
    public interface OnRecyclerViewItemRLClickListener<Container> {

        void onRlRemoveTitleClick(View itemView, Container item, int position);

        void onRlContainerTitleClick(View itemView, TextView tvCabinetType0, Container item, int position);

    }

    private OnRecyclerViewItemRLClickListener<Container> mOnRecyclerViewItemRLClickListener = null;

    public void setOnRecyclerViewItemRLClickListener(OnRecyclerViewItemRLClickListener listener) {
        this.mOnRecyclerViewItemRLClickListener = listener;
    }

}
