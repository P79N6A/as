package com.yaoguang.lib.adapter;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.lib.R;

import java.util.LinkedList;
import java.util.List;


/**
 * 底部加载更多
 * Created by zhongjh on 2016/6/15.
 */
public abstract class BaseLoadMoreRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    private RecyclerView mRecyclerView;

    private boolean hasFooter;
    private boolean hasFooterPriority = true;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;
    public static final int TYPE_HEADER = 2; // 头部
    public static final int TYPE_FOOTER = 1; // 脚步
    public static final int TYPE_ITEM = 0; // item
    public static final int TYPE_FOOTER_NO_LOAD = 3; // 已经全部加载的脚步

    protected List<T> list = new LinkedList<>();

    /**
     * 设置成无数据加载（这个优先级更高点）
     */
    public void setHasFooterPriority(boolean hasFooterPriority) {
        this.hasFooterPriority = hasFooterPriority;
    }

    /**
     * 这是由数据来决定是否 设置成无数据加载了
     */
    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    public boolean isHasFooter() {
        return hasFooter && hasFooterPriority;
    }

    private static class FooterViewHolder extends RecyclerView.ViewHolder {
        final TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            tvFooter = view.findViewById(R.id.tvFooter);
        }
    }

    /**
     * 添加头部view
     *
     * @param headerView 头部view
     */
    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    /**
     * 添加尾部view
     *
     * @param footerView 尾部view
     */
    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断该position是否头部view
     *
     * @param position 索引
     * @return 是否
     */
    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    /**
     * 判断该position是否尾部view
     *
     * @param position 索引
     * @return 是否
     */
    private boolean isFooterView(int position) {
        // 判断isHasFooter()，如果要显示无加载数据，那就要倒数第二个
        int i = 1;
        if (isHasFooter())
            i++;
        return haveFooterView() && position == getItemCount() - i;
    }

    /**
     * 判断该position是否是无加载数据
     *
     * @param position 索引
     * @return 是否
     */
    private boolean isHasFooterView(int position) {
        return isHasFooter() && position == getItemCount() - 1;
    }


    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }

    /**
     * 是否存在头部view
     */
    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    /**
     * 是否存在底部view
     */
    private boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    /**
     * 计算数据源长度
     *
     * @return 长度
     */
    @Override
    public int getItemCount() {
        int count = list.size() + (isHasFooter() ? 1 : 0);
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    /**
     * 不再重写getItemViewType，而是getItemViewTypeCustom
     * @param position
     * @return
     */
    public int getItemViewTypeCustom(int position) {
        return TYPE_ITEM;
    }

    /**
     * 判断类型
     *
     * @param position 索引
     * @return 类型
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else if (isHasFooterView(position)) {
            return TYPE_FOOTER_NO_LOAD;
        } else {
            return getItemViewTypeCustom(position);
        }
    }

    /**
     * 根据类型创建view
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER_NO_LOAD) {//底部 加载view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_foot, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            return new MyHolder(VIEW_HEADER);
        } else if (viewType == TYPE_FOOTER) {
            return new MyHolder(VIEW_FOOTER);
        } else {
            //数据itemViewHolder
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).tvFooter.setText("已经全部加载完毕");
        } else if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) {
                // 如果有头部，索引就减一
                position = position - 1;
                onBindItemViewHolder((VH) holder, position);
            } else {
                // 没有头部，索引照常
                onBindItemViewHolder((VH) holder, position);
            }
        }
    }


    //正常数据itemViewHolder 实现
    public abstract void onBindItemViewHolder(final VH holder, int position);

    //数据itemViewHolder 实现
    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    public static class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 赋值数据源
     * @param list 数据源
     */
    public void setList(List<T> list){
        this.list = list;
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
        if (position <= list.size() - 1 && position >= 0) {
            list.remove(position);
        }
    }

    public void clear() {
        list.clear();
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

    //点击事件
    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View itemView, T item, int position);
    }

    protected OnRecyclerViewItemClickListener<T> mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // 长按事件
    public interface OnRecyclerViewItemLongClickListener<T> {
        void onItemClick(View itemView, T item, int position);
    }

    protected OnRecyclerViewItemLongClickListener<T> mOnItemLongClickListener = null;

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }


    //添加删除更新事件

    /**
     * 添加
     *
     * @param position 索引
     * @param object   对象
     */
    public void add(int position, T object) {
        list.add(position, object);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, list.size());
    }

    /**
     * 更新
     *
     * @param position 索引
     * @param object   对象
     */
    public void updateItem(int position, T object) {
        list.set(position, object);
        notifyItemChanged(position);
    }

    /**
     * 删除
     *
     * @param position 索引
     */
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

}
