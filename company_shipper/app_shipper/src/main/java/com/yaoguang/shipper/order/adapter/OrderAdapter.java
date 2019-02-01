package com.yaoguang.shipper.order.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.AppOrderWrapper;
import com.yaoguang.shipper.R;

/**
 * Created by zhongjh on 2017/6/15.
 */
public class OrderAdapter extends BaseLoadMoreRecyclerAdapter<AppOrderWrapper, RecyclerView.ViewHolder> {

    Fragment mFragment;

    public OrderAdapter(Fragment fragment) {
        mFragment = fragment;
    }


    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order2, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(v, getList().get(position), position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppOrderWrapper appOrderWrapper = getList().get(position);
        itemViewHolder.tvShipCompany.setText(ObjectUtils.parseString(appOrderWrapper.getOrderSn(), "", "数据暂无"));
        itemViewHolder.tvGoodsType.setText(ObjectUtils.parseString(appOrderWrapper.getCompanyName(), "", "数据暂无"));


        //装货是从起运地到起运港，卸货是从目的地到目的港
        if (appOrderWrapper.getType() == 0) {
            itemViewHolder.tvPort.setText(ObjectUtils.parseString(appOrderWrapper.getDockLoading(), "", "数据暂无"));
            itemViewHolder.tvAddress.setText(ObjectUtils.parseString(appOrderWrapper.getPortDelivery(), "", "数据暂无"));
            itemViewHolder.imgOtherservice.setVisibility(View.GONE);
        } else {
            if (appOrderWrapper.getOtherservice() != null)
                itemViewHolder.imgOtherservice.setVisibility(View.VISIBLE);
            if (appOrderWrapper.getOtherservice() == 0) {
                itemViewHolder.tvPort.setText(ObjectUtils.parseString(appOrderWrapper.getDockLoading(), "", "数据暂无"));
                itemViewHolder.tvAddress.setText(ObjectUtils.parseString(appOrderWrapper.getPortLoading(), "", "数据暂无"));
                GlideManager.getInstance().with(mFragment.getContext(), R.drawable.ic_zhuang, itemViewHolder.imgOtherservice);
            } else {
                itemViewHolder.tvPort.setText(ObjectUtils.parseString(appOrderWrapper.getPortDelivery(), "", "数据暂无"));
                itemViewHolder.tvAddress.setText(ObjectUtils.parseString(appOrderWrapper.getFinalDestination(), "", "数据暂无"));
                GlideManager.getInstance().with(mFragment.getContext(), R.drawable.ic_xie, itemViewHolder.imgOtherservice);
            }
        }

        itemViewHolder.tvCont.setText(ObjectUtils.parseString(appOrderWrapper.getContQty(), "", "数据暂无"));
        itemViewHolder.tvFee.setText(ObjectUtils.parseString(appOrderWrapper.getGoodsName(), "", "数据暂无"));
        if (appOrderWrapper.getCreated() != null || !appOrderWrapper.getCreated().equals(""))
            itemViewHolder.tvCreated.setText("业务时间:  " + ObjectUtils.parseStringYYYYMMDDHHMM(appOrderWrapper.getCreated()));
        itemViewHolder.tvmBlNo.setText("运单号:  " + appOrderWrapper.getmBlNo());
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvShipCompany;
        public ImageView imgOtherservice;
        public TextView tvGoodsType;
        public RelativeLayout rlPlaceLoading;
        public TextView tvPort;

        public TextView tvFee;
        public View vLine;
        public TextView tvCont;
        public TextView tvAddress;
        public TextView tvmBlNo;
        public TextView tvCreated;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvShipCompany = (TextView) rootView.findViewById(R.id.tvShipCompany);
            this.imgOtherservice = (ImageView) rootView.findViewById(R.id.imgOtherservice);
            this.tvGoodsType = (TextView) rootView.findViewById(R.id.tvGoodsType);
            this.rlPlaceLoading = (RelativeLayout) rootView.findViewById(R.id.rlPlaceLoading);
            this.tvPort = (TextView) rootView.findViewById(R.id.tvPort);
            this.tvFee = (TextView) rootView.findViewById(R.id.tvFee);
            this.vLine = (View) rootView.findViewById(R.id.vLine);
            this.tvCont = (TextView) rootView.findViewById(R.id.tvCont);
            this.tvAddress = (TextView) rootView.findViewById(R.id.tvAddress);
            this.tvmBlNo = (TextView) rootView.findViewById(R.id.tvmBlNo);
            this.tvCreated = (TextView) rootView.findViewById(R.id.tvCreated);
        }

    }
}
