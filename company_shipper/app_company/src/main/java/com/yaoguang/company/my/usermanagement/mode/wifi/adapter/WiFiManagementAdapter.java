package com.yaoguang.company.my.usermanagement.mode.wifi.adapter;

import android.content.Context;
import android.support.constraint.Guideline;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.company.my.usermanagement.mode.wifi.WiFiManagementFragment;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowWlan;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by zhongjh on 2018/12/6.
 */

public class WiFiManagementAdapter extends BaseLoadMoreRecyclerAdapter<UserLoginAllowWlan, RecyclerView.ViewHolder> {

    private Context mContext;
    private RecyclerView.RecycledViewPool recycledViewPool;// 从MainActivity传递过来的共享池

    public void setRecycledViewPool(Context context, RecyclerView.RecycledViewPool recycledViewPool) {
        this.mContext = context;
        this.recycledViewPool = recycledViewPool;
    }

    @Override
    public int getItemViewTypeCustom(int position) {
        return WiFiManagementFragment.PARENT_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanagement_wifi, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, recycledViewPool);
        viewHolder.rlChild.setOnTouchListener((v, event) -> viewHolder.rootView.onTouchEvent(event));
        viewHolder.rootView.setOnClickListener(v -> {
            final int position = viewHolder.getAdapterPosition();
            if (!list.get(position).isCheck()) {
                list.get(position).setCheck(true);
            } else {
                list.get(position).setCheck(false);
            }
            notifyDataSetChanged();
        });
        return viewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        UserLoginAllowWlan userLoginAllowWlan = getItem(position);
        // 先判断一下是不是已经设置了Adapter
        if (viewHolder.rlChild.getAdapter() == null) {
            WiFiManagementChildAdapter wiFiManagementChildAdapter = new WiFiManagementChildAdapter(mContext, userLoginAllowWlan.getWlanList());
            viewHolder.rlChild.setAdapter(wiFiManagementChildAdapter);
        } else {
            WiFiManagementChildAdapter wiFiManagementChildAdapter = ((WiFiManagementChildAdapter) viewHolder.rlChild.getAdapter());
            wiFiManagementChildAdapter.setData(userLoginAllowWlan.getWlanList());
            viewHolder.rlChild.getAdapter().notifyDataSetChanged();
        }

        if (list.get(position).isCheck()) {
            viewHolder.imgCheck.setImageResource(R.drawable.ic_big_bossyellow);
            viewHolder.imgCheck.setTag("1");
        } else {
            viewHolder.imgCheck.setImageResource(R.drawable.ic_big_bossempty);
            viewHolder.imgCheck.setTag("0");
        }

        viewHolder.ProjectNameA.setText(userLoginAllowWlan.getGroupName());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView ProjectNameA;
        public Guideline guideline1V50;
        public RecyclerView rlChild;
        public ImageView imgCheck;

        public ViewHolder(View rootView, RecyclerView.RecycledViewPool recycledViewPool) {
            super(rootView);
            this.rootView = rootView;
            this.ProjectNameA = (TextView) rootView.findViewById(R.id.ProjectNameA);
            this.guideline1V50 = (Guideline) rootView.findViewById(R.id.guideline1V50);
            this.rlChild = (RecyclerView) rootView.findViewById(R.id.rlChild);
            this.imgCheck = (ImageView) rootView.findViewById(R.id.imgCheck);

            GridLayoutManager manager = new GridLayoutManager(itemView.getContext(), 2);
            // 需要注意：要使用RecycledViewPool的话,如果使用的LayoutManager是LinearLayoutManager或其子类（如GridLayoutManager），需要手动开启这个特性
            manager.setRecycleChildrenOnDetach(true);
            // 嵌套的子RecyclerView,需要将LinearLayoutManager设置setAutoMeasureEnabled(true)成自适应高度
//            manager.setAutoMeasureEnabled(true);
            // 子RecyclerView没必要滚动本身
            rlChild.setNestedScrollingEnabled(false);
            rlChild.setLayoutManager(manager);
            // 子RecyclerView现在和父RecyclerView在同一个共享池了
            rlChild.setRecycledViewPool(recycledViewPool);

        }

    }
}
