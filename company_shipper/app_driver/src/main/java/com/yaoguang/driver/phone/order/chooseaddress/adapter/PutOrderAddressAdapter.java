package com.yaoguang.driver.phone.order.chooseaddress.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.InfoPutorderPlace;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择订单地址List配置
 * Created by Administrator on 2017/12/28 0028.
 */

public class PutOrderAddressAdapter extends BaseLoadMoreRecyclerAdapter<InfoPutorderPlace, RecyclerView.ViewHolder> {
    private String lastSelectId;
    private Comeback mComeback;

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_choose_order_address, null);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    viewHolder.checkBox.performClick();
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        InfoPutorderPlace item = getItem(position);
        // 存放点名称
        if (!TextUtils.isEmpty(item.getName())) {
            viewHolder.tvName.setText(item.getName());
        }
        // 存放点地址
        if (!TextUtils.isEmpty(item.getAddress())) {
            viewHolder.tvAddress.setText(item.getAddress());
        }
        // 存放点电话
        if (!TextUtils.isEmpty(item.getPhone())) {
            viewHolder.tvTel.setText(item.getPhone());
        }
        // 设置选择
        if (item.isSelect) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }

        // 思明说暂时不用监听电话 电话click
//        viewHolder.tvTel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (mComeback != null) {
//                    mComeback.callMobile(item);
//                }
//            }
//        });


        // 选择
        viewHolder.checkBox.setOnClickListener(v -> {
            // 初始化全为没选择
            List<InfoPutorderPlace> list = getList();
            for (InfoPutorderPlace place : list) {
                place.setSelect(false);
            }

            InfoPutorderPlace place = list.get(position);
            // 如果一样设置
            if (place.getId().equals(lastSelectId)) {
                // 设置已选的
                if (place.isSelect) {
                    place.setSelect(true);
                } else lastSelectId = null;

                if (mComeback != null) {
                    mComeback.myLocation(item);
                }
            } else {
                // 不一样的设置
                place.setSelect(true);
                // 记录下上次的选择，刷新数据时还在
                lastSelectId = list.get(position).getId();

                if (mComeback != null && place.getLatitude() != 0 && place.getLongitude() != 0) {
                    mComeback.showLocation(new LatLng(place.getLatitude(), place.getLongitude()), place.getName(), place.getAddress());
                }
            }

            notifyDataSetChanged();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, item, position);
            }
        });


    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.tvTel)
        TextView tvTel;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAddress)
        TextView tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String getLastSelectId() {
        return lastSelectId;
    }

    public interface Comeback {
        void showLocation(LatLng latLng, String name, String address);

        void myLocation(InfoPutorderPlace item);

        void callMobile(InfoPutorderPlace item);
    }

    public void setComeback(Comeback mComeback) {
        this.mComeback = mComeback;
    }
}
