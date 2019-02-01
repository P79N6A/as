package com.yaoguang.company.businessorder2.common.businessmain.dynamic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by zhongjh on 2018/11/10.
 */

public class DynamicAdapter extends BaseLoadMoreRecyclerAdapter<String[], RecyclerView.ViewHolder> {

    private Context mContext;

    public DynamicAdapter(Context context){
        mContext = context;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        String[] title = getList().get(position);
        itemViewHolder.tvTitle.setText(title[0]);
        itemViewHolder.tvValue.setText(title[1]);
        if (position == 0) {
            itemViewHolder.vTop.setVisibility(View.GONE);
        } else if (position == (getItemCount() - 1)) {
            itemViewHolder.vBottom.setVisibility(View.GONE);
        } else {
            itemViewHolder.vTop.setVisibility(View.VISIBLE);
            itemViewHolder.vBottom.setVisibility(View.VISIBLE);
        }

        // 判断上一个是否有值
        if ( (position-1) >= 0 && TextUtils.isEmpty(getList().get(position-1)[1])){
            // 没值黑色
            itemViewHolder.vTop.setBackgroundColor(mContext.getResources().getColor(R.color.blackccc));
        }else{
            // 有值主色
            itemViewHolder.vTop.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
        }

        // 判断当前是否有值
        if (TextUtils.isEmpty(itemViewHolder.tvValue.getText())){
            // 没值黑色
            itemViewHolder.vBottom.setBackgroundColor(mContext.getResources().getColor(R.color.blackccc));
            itemViewHolder.imgCicle.setImageResource(R.drawable.ic_cicle_s);
            itemViewHolder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.black333));
        }else{
            // 有值主色
            itemViewHolder.vBottom.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
            itemViewHolder.imgCicle.setImageResource(R.drawable.ic_cicle_b);
            itemViewHolder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.primary));
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_dynamic, parent, false);
        ViewHolder holder = new ViewHolder(view);
//        view.setOnClickListener(v -> {
//            final int position = holder.getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                mOnItemClickListener.onItemClick(v, getList().get(position), position);
//            }
//        });
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public View vTop;
        public ImageView imgCicle;
        public View vBottom;
        public TextView tvTitle;
        public TextView tvValue;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.vTop = rootView.findViewById(R.id.vTop);
            this.imgCicle = rootView.findViewById(R.id.imgCicle);
            this.vBottom = rootView.findViewById(R.id.vBottom);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvValue = rootView.findViewById(R.id.tvValue);
        }

    }
}
