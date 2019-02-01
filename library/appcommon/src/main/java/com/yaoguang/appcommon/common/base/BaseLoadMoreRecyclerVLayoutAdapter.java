package com.yaoguang.appcommon.common.base;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.LinkedList;
import java.util.List;


/**
 * 底部加载更多
 * Created by zhongjh on 2016/6/15.
 */
public abstract class BaseLoadMoreRecyclerVLayoutAdapter<T, VH extends RecyclerView.ViewHolder> extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
//    private static finalrequest int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
//    private boolean mHasFooter;//设置是否显示Footer
//    private boolean mHasMoreData;//设置是否可以继续加载数据

    private final List<T> list = new LinkedList<>();


//    private static class FooterViewHolder extends RecyclerView.ViewHolder {
//        finalrequest ProgressBar pbLoading;
//        finalrequest TextView tvMessage;
//
//        FooterViewHolder(View view) {
//            super(view);
//            pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
//            tvMessage = (TextView) view.findViewById(R.id.tvMessage);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + tvMessage.getText();
//        }
//    }

    //数据itemViewHolder 实现
    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_FOOTER) {//底部 加载view
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_footer, parent, false);
//            return new FooterViewHolder(view);
//        } else {
            //数据itemViewHolder
            return onCreateItemViewHolder(parent, viewType);
//        }
    }

    //正常数据itemViewHolder 实现
    public abstract void onBindItemViewHolder(final VH holder, int position);


    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof FooterViewHolder) {
//            //没有更多数据
//            if (mHasMoreData) {
//                ((FooterViewHolder) holder).pbLoading.setVisibility(View.VISIBLE);
//                ((FooterViewHolder) holder).tvMessage.setText("正在加载......");
//            } else {
//                ((FooterViewHolder) holder).pbLoading.setVisibility(View.GONE);
//                ((FooterViewHolder) holder).tvMessage.setText("没有更多数据了......");
//            }
//        } else {
            onBindItemViewHolder((VH) holder, position);
//        }
    }


    @Override
    public int getItemViewType(int position) {
//        if (position == getBasicItemCount() && mHasFooter) {
//            return TYPE_FOOTER;
//        }
        return TYPE_ITEM;//0
    }

    /**
     * 数据源
     */
    public List<T> getList() {
        return list;
    }

    public void append(T t) {
        if (t == null) {
            return;
        }
        list.add(t);
    }

    public void appendToList(List<T> list) {
        if (list == null) {
            return;
        }
        this.list.addAll(list);
    }

    public void appendToTop(T item) {
        if (item == null) {
            return;
        }
        list.add(0, item);
    }

    public void appendToTopList(List<T> list) {
        if (list == null) {
            return;
        }
        this.list.addAll(0, list);
    }


    public void remove(int position) {
        if (position < list.size() - 1 && position >= 0) {
            list.remove(position);
        }
    }

    public void clear() {
        list.clear();
    }

    private int getBasicItemCount() {
        return list.size();
    }

    @Override
    public int getItemCount() {
//        return getBasicItemCount() + (mHasFooter ? 1 : 0);
        return getBasicItemCount();
    }

    public T getItem(int position) {
        if (position > list.size() - 1) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    public boolean mHasFooter() {
//        return mHasFooter;
//    }
//
//    public void setmHasFooter(boolean mHasFooter) {
//        if (this.mHasFooter != mHasFooter) {
//            this.mHasFooter = mHasFooter;
//        }
//    }
//
//
//    public boolean hasMoreData() {
//        return mHasMoreData;
//    }
//
//    public void setmHasMoreData(boolean isMoreData) {
//        if (this.mHasMoreData != isMoreData) {
//            this.mHasMoreData = isMoreData;
//        }
//    }
//
//    public void setHasMoreDataAndFooter(boolean hasMoreData, boolean mHasFooter) {
//        if (this.mHasMoreData != hasMoreData || this.mHasFooter != mHasFooter) {
//            this.mHasMoreData = hasMoreData;
//            this.mHasFooter = mHasFooter;
//        }
//    }


    //点击事件
    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View itemView, T item, int position);
    }

    protected OnRecyclerViewItemClickListener<T> mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //添加删除更新事件
    /* *
     * 添加
     * @param position 索引
     * @param object 对象
     */
    public void add(int position, T object) {
        list.add(position, object);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, list.size());
    }

    /* *
     * 更新
     * @param position 索引
     * @param object 对象
     */
    public void updateItem(int position, T object) {
        list.set(position, object);
        notifyItemChanged(position);
    }

    /* *
     * 删除
     * @param position 索引
     */
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

}
