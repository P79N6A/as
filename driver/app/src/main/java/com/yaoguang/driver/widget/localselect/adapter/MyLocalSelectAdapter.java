package com.yaoguang.driver.widget.localselect.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.map.location.AMapLocation;
import com.yaoguang.map.location.impl.LocationManager;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：$version
 * 创建日期：2018/02/27
 * 描    述：
 * =====================================
 */

public class MyLocalSelectAdapter extends BaseLoadMoreRecyclerAdapter<String, RecyclerView.ViewHolder> {
    OnAddressClick onAddressClick;

    boolean isShowMyLocation;
    boolean isShowArea;

    String province;
    String city;
    String area;

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.my_localselect_item, null);
        MyLocalSelectHolder viewHolder = new MyLocalSelectHolder(itemView);
        viewHolder.llItem.setOnClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(itemView, getItem(adapterPosition), adapterPosition);
            }
        });
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyLocalSelectHolder itemHolder = (MyLocalSelectHolder) holder;
        String name = getItem(position);

        // 是否显示定位
        if (isShowMyLocation() && position == 0) {
            itemHolder.llBanner.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(province) && !TextUtils.isEmpty(city)) {
                String address = province + " " + city;
                if (isShowArea) {
                    address = address + " " + area;
                }
                itemHolder.tvAddress.setText(address);
                itemHolder.llAddress.setOnClickListener(v -> {
                    if (onAddressClick != null) {
                        onAddressClick.click(province, city, area);
                    }
                });
            } else {
                itemHolder.tvAddress.setText("获取位置");
                itemHolder.llAddress.setOnClickListener(getNewLocal);
            }
        } else {
            itemHolder.llBanner.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(name)) {
            itemHolder.tvName.setText(name);
        }
    }

    private static class MyLocalSelectHolder extends RecyclerView.ViewHolder {
        private TextView tvBannerTitle;
        private TextView tvAddress;
        private View line;
        private LinearLayout llBanner;
        private LinearLayout llAddress;
        private LinearLayout llItem;
        private TextView tvName;
        private ImageView cbFault;

        MyLocalSelectHolder(View view) {
            super(view);
            tvBannerTitle = view.findViewById(R.id.tvBannerTitle);
            tvAddress = view.findViewById(R.id.tvAddress);
            llBanner = view.findViewById(R.id.llBanner);
            llAddress = view.findViewById(R.id.llAddress);
            llItem = view.findViewById(R.id.llItem);
            tvName = view.findViewById(R.id.tvName);
        }
    }

    public interface OnAddressClick {
        void click(String province,
                   String city,
                   String area);
    }

    public boolean isShowMyLocation() {
        return isShowMyLocation;
    }

    public void setShowMyLocation(boolean showMyLocation) {
        isShowMyLocation = showMyLocation;
    }


    public OnAddressClick getOnAddressClick() {
        return onAddressClick;
    }

    public void setOnAddressClick(OnAddressClick onAddressClick) {
        this.onAddressClick = onAddressClick;
    }


    View.OnClickListener getNewLocal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView tv = v.findViewById(R.id.tvAddress);

            LocationManager locationManager = new LocationManager();
            locationManager.setComeback(new AMapLocation.Comeback() {
                @SuppressLint("SetTextI18n")
                @Override
                public void success(Location last) {
                    province = last.getProvince();
                    city = last.getCity();
                    area = last.getDistrict();

                    String address = province + " " + city;
                    if (isShowArea) {
                        address = address + " " + area;
                    }
                    tv.setText(address);
                }
            });
        }
    };

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isShowArea() {
        return isShowArea;
    }

    public void setShowArea(boolean showArea) {
        isShowArea = showArea;
    }
}
