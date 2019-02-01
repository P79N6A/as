package com.yaoguang.lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by zhongjh on 2018/12/12.
 */
public abstract class BaseExpandRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends BaseLoadMoreRecyclerAdapter<T, VH> {

    /**
     * 收缩或者展开
     */
    public void setContractionExpansion(){
        if (maxLength == -1){
            maxLength = this.initialMaxLength;
        }else{
            maxLength = -1;
        }
        notifyDataSetChanged();
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        this.initialMaxLength = maxLength;
    }
    private int maxLength;  // 设置到这个数量后可以收缩拓展,-1的话就是全部展开
    private int initialMaxLength;   // 用于记录最初设置的值

    //正常数据itemViewHolder 实现
    public abstract void onBindItemExpandViewHolder(final VH holder, int position);

    @Override
    public void onBindItemViewHolder(VH holder, int position) {
        if (position < maxLength || maxLength == -1) {
            ((ViewHolder) holder).setVisibility(true);
        } else {
            ((ViewHolder) holder).setVisibility(false);
        }
        onBindItemExpandViewHolder(holder, position);
    }


    public abstract static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setVisibility(boolean isVisible) {
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (isVisible) {
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }

    }

}
