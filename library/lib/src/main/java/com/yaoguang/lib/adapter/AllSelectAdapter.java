package com.yaoguang.lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.HashMap;

/**
 * 作    者：韦理英
 * 时    间：2017/9/5 0005.
 * 描    述：XXXXX
 */
public abstract class AllSelectAdapter<T, VH extends RecyclerView.ViewHolder> extends BaseLoadMoreRecyclerAdapter<T, VH> {
    public static HashMap<Integer,Boolean> isSelected  = new HashMap<Integer, Boolean>();
    public boolean isSelect = false;
    public boolean isAllSelect;
    public boolean isEdit;
    public ViewGroup parent;

    public void setAllSelect(boolean allSelect) {
        isAllSelect = allSelect;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        return super.onCreateViewHolder(parent, viewType);
    }

    public OnRecyclerViewItemClickSelectListener onRecyclerViewItemClickSelectListener;

    //按钮点击事件
    public interface OnRecyclerViewItemClickSelectListener<T> {
        void onItemBtnSelectClick(CheckBox checkBox, T item, int position);
    }

    public void setOnRecyclerViewItemClickSelectListener(OnRecyclerViewItemClickSelectListener onRecyclerViewItemClickSelectListener) {
        this.onRecyclerViewItemClickSelectListener = onRecyclerViewItemClickSelectListener;
    }
}
