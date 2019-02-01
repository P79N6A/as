package com.yaoguang.lib.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * 头部底部加载更多
 * Created by wly on 2016/6/15.
 */
public abstract class BaseRecyclerHeaderAndFooterAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    private static final int TYPE_HEADER = 0;  //说明是带有Header的
    private static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    private static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //获取从Activity中传递过来每个item的数据集合
    private List<T> list = new ArrayList<>();
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }


    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (mFooterView != null && position == getItemCount() - 1) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return getHeaderView(parent, mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return getFooterHolder(parent, mHeaderView);
        }
        return getNormalHolder(parent, mHeaderView);
    }

    /**
     * 获取底部的view
     *
     * @param parent      parent
     * @param mHeaderView head view
     * @return 底部Holder
     */
    protected abstract RecyclerView.ViewHolder getFooterHolder(ViewGroup parent, View mHeaderView);

    /**
     * 获取head view
     *
     * @param parent      parent
     * @param mHeaderView head view
     * @return Header Holder
     */
    protected abstract RecyclerView.ViewHolder getHeaderView(ViewGroup parent, View mHeaderView);

    /**
     * 获取正常的列表
     *
     * @param parent      view
     * @param mHeaderView head view
     * @return 正常的holder
     */
    protected abstract VH getNormalHolder(ViewGroup parent, View mHeaderView);

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    //这里加载数据的时候要注意，是从position-1开始，否则会数组越界，因为position==0已经被header占用了
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            onBindItemNormalViewHolder((VH) holder, position);
        } else if (getItemViewType(position) == TYPE_HEADER) {
            onBindItemHeaderViewHolder((VH) holder, position);
        } else if (getItemViewType(position) == TYPE_FOOTER) {
            onBindItemFooterViewHolder((VH) holder, position);
        }
    }

    /**
     * 这里是底部ui处理
     *
     * @param holder   holder
     * @param position position
     */
    protected abstract void onBindItemFooterViewHolder(VH holder, int position);

    /**
     * 这里做head ui处理
     *
     * @param holder   holder
     * @param position pos
     */
    protected abstract void onBindItemHeaderViewHolder(VH holder, int position);

    /**
     * 这里做UI数据处理
     *
     * @param holder   holder
     * @param position position
     */
    protected abstract void onBindItemNormalViewHolder(VH holder, int position);

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return list.size();
        }else if(mHeaderView == null && mFooterView != null){
            return list.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return list.size() + 1;
        }else {
            return list.size() + 2;
        }
    }


    /**
     * 数据源
     */
    public List<T> getList() {
        return list;
    }

    public void appendToList(List<T> list) {
        if (list == null) {
            return;
        }
        this.list.addAll(list);
    }

    public void append(T t) {
        if (t == null) {
            return;
        }
        list.add(t);
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
        void onItemClick(View itemView, T item);
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
