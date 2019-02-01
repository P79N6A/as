package com.yaoguang.lib.treerecyclerview.item;

import com.yaoguang.lib.treerecyclerview.base.ViewHolder;
import com.yaoguang.lib.treerecyclerview.widget.swipe.SwipeLayout;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public interface SwipeItem {

    int getSwipeLayoutId();

    SwipeLayout.DragEdge getDragEdge();

    void onBindSwipeView(ViewHolder viewHolder, int position);
}
