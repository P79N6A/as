package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.widget.recyclerview.LinearLayoutManagerAutoMeasure;

import java.util.ArrayList;

import static com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.BaseBusinessOrderTempListFragment.PARENT_VIEW_TYPE;

/**
 * 预订单模版适配器
 * Created by zhongjh on 2017/6/13.
 */
public abstract class BaseBusinessOrderTempListAdapter<T> extends BaseLoadMoreRecyclerAdapter<T, RecyclerView.ViewHolder> {

    protected RecyclerView.RecycledViewPool mRecycledViewPool;// 从窗体传递过来的共享池
    protected Context mContext;

    protected ViewGroup parent;


    public BaseBusinessOrderTempListAdapter(RecyclerView.RecycledViewPool mRecycledViewPool,Context context) {
        this.mRecycledViewPool = mRecycledViewPool;
        this.mContext = context;
    }

    @Override
    public int getItemViewTypeCustom(int position) {
        return PARENT_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order_temp_trailer, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view, mRecycledViewPool);
        view.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, getList().get(holder.getAdapterPosition()), holder.getAdapterPosition()));
        return holder;
    }

    /**
     * @param appInfoClientPlace 装货模型
     * @param view               view
     */
    protected void viewSetValue(View view, AppInfoClientPlace appInfoClientPlace, int drawable) {
        ((TextView) view.findViewById(R.id.tvName)).setText(appInfoClientPlace.getLinkman() + " " + appInfoClientPlace.getLinkmanMp() + " " + appInfoClientPlace.getLinkmanTel());
        ((TextView) view.findViewById(R.id.tvShortName)).setText((appInfoClientPlace.getRegionid() + appInfoClientPlace.getAddress()));
        Glide.with(parent.getContext())
                .load(drawable)
                .into((ImageView) view.findViewById(R.id.imgLoadingOrUnLoading));

    }


    public static class ItemViewHolder  extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvDeparture;
        public TextView tvDestination;
        public TextView tvInfoClientManager;
        public View vLuXian;
        public TextView tvGoodsName;
        public View vLuXian2;
        public TextView tvOwner;
        public TextView tvShipCompany;
        public TableLayout tlWait;
        public View vLuXian3;
        public RecyclerView rlLoading;

        public ItemViewHolder(View rootView,RecyclerView.RecycledViewPool recycledViewPool) {
            super(rootView);
            this.rootView = rootView;
            this.tvDeparture = (TextView) rootView.findViewById(R.id.tvDeparture);
            this.tvDestination = (TextView) rootView.findViewById(R.id.tvDestination);
            this.tvInfoClientManager = (TextView) rootView.findViewById(R.id.tvInfoClientManager);
            this.vLuXian = (View) rootView.findViewById(R.id.vLuXian);
            this.tvGoodsName = (TextView) rootView.findViewById(R.id.tvGoodsName);
            this.vLuXian2 = (View) rootView.findViewById(R.id.vLuXian2);
            this.tvOwner = (TextView) rootView.findViewById(R.id.tvOwner);
            this.tvShipCompany = (TextView) rootView.findViewById(R.id.tvShipCompany);
            this.tlWait = (TableLayout) rootView.findViewById(R.id.tlWait);
            this.vLuXian3 = (View) rootView.findViewById(R.id.vLuXian3);
            this.rlLoading = (RecyclerView) rootView.findViewById(R.id.rlLoading);

            LinearLayoutManagerAutoMeasure manager = new LinearLayoutManagerAutoMeasure(itemView.getContext());
            // 需要注意：要使用RecycledViewPool的话,如果使用的LayoutManager是LinearLayoutManager或其子类（如GridLayoutManager），需要手动开启这个特性
            manager.setRecycleChildrenOnDetach(true);
            // 嵌套的子RecyclerView,需要将LinearLayoutManager设置setAutoMeasureEnabled(true)成自适应高度
//            manager.setAutoMeasureEnabled(true);
            // 子RecyclerView没必要滚动本身
            rlLoading.setNestedScrollingEnabled(false);
            rlLoading.setLayoutManager(manager);
            // 子RecyclerView现在和父RecyclerView在同一个共享池了
            rlLoading.setRecycledViewPool(recycledViewPool);

        }

    }
}
