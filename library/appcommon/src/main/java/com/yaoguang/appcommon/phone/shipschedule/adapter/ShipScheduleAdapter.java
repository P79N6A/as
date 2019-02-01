package com.yaoguang.appcommon.phone.shipschedule.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SpanUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.InfoVoyageTable;

/**
 * Created by zhongjh on 2017/6/13.
 */
public class ShipScheduleAdapter extends BaseLoadMoreRecyclerAdapter<InfoVoyageTable, RecyclerView.ViewHolder> {

    String mCodeType;

    public ShipScheduleAdapter(String codeType) {
        mCodeType = codeType;
    }


    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shipschedule_2, parent, false);
        switch (mCodeType) {
            case Constants.APP_COMPANY:
                view.findViewById(R.id.flDetail).setVisibility(View.VISIBLE);
                break;
            case Constants.APP_SHIPPER:
                view.findViewById(R.id.flDetail).setVisibility(View.GONE);
                break;

        }
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.findViewById(R.id.flDetail).setOnClickListener(new NoDoubleClickListener() {
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
        itemViewHolder.tvContValue.setText(ObjectUtils.parseString(getList().get(position).getContId(),"暂无数据"));
        itemViewHolder.tvTotalFee.setText(ObjectUtils.formatNumber2(getList().get(position).getTotalFee(), 0));

        itemViewHolder.tvPortTimeValue.setText(ObjectUtils.parseStringYYYYMMDDHHMM(getList().get(position).getPortTime()));

        itemViewHolder.tvVesselName.setText(ObjectUtils.parseString(getList().get(position).getVesselName(),"暂无数据"));
        itemViewHolder.tvPlaceLoading.setText(ObjectUtils.parseString(getList().get(position).getPlaceLoading(),"暂无数据"));
        itemViewHolder.tvPlaceDelivery.setText(ObjectUtils.parseString(getList().get(position).getPlaceDelivery(),"暂无数据"));
        itemViewHolder.tvVoyage.setText(ObjectUtils.parseString(getList().get(position).getVoyage(),"暂无数据"));

        String onboardDate = ObjectUtils.parseStringYYYYMMDDHHMM(getList().get(position).getOnboardDate());
        String[] onboardDates = onboardDate.split(" ");
        itemViewHolder.tvOnboardDate.setText(onboardDates[0] + "\n" + onboardDates[1]);

        String arrivaal = ObjectUtils.parseStringYYYYMMDDHHMM(getList().get(position).getArrival());
        String[] arrivaals = arrivaal.split(" ");
        itemViewHolder.tvArrival.setText(arrivaals[0] + "\n" + arrivaals[1]);

        itemViewHolder.tvShipCompany.setText(ObjectUtils.parseString(getList().get(position).getShipCompany(),"暂无数据"));
        itemViewHolder.tvCreatedValue.setText(ObjectUtils.parseStringYYYYMMDDHHMM(getList().get(position).getCreated()));

        String shipLine = getList().get(position).getShipLine().replace("--", "-");
        String[] shipLines = shipLine.split("-");

        int size = shipLines.length;
        SpanUtils spanUtils = new SpanUtils();
        spanUtils.appendImage(R.drawable.ic_gk_05, SpanUtils.ALIGN_CENTER).appendSpace(10);
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                spanUtils.append(shipLines[i]);
            } else {
                spanUtils.append(shipLines[i]).appendSpace(10).appendImage(R.drawable.ic_dc_02, SpanUtils.ALIGN_CENTER).appendSpace(10);
            }
        }
        itemViewHolder.tvShipLine.setText(spanUtils.create());
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvContId;
        public TextView tvContValue;
        public TextView tvShipCompany;
        public RelativeLayout rlPlaceLoading;
        public TextView tvVoyage;
        public View vLine;
        public TextView tvPlaceLoading;
        public TextView tvOnboardDate;
        public TextView tvVesselName;
        public TextView tvPlaceDelivery;
        public TextView tvArrival;
        public ImageView imgPortTime;
        public TextView tvPortTime;
        public TextView tvPortTimeValue;
        public ImageView imgCreated;
        public TextView tvCreated;
        public TextView tvCreatedValue;
        public FrameLayout flDetail;
        public TextView tvTotalFee;
        public TextView tvShipLine;


        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvContId = (TextView) rootView.findViewById(R.id.tvContId);
            this.tvContValue = (TextView) rootView.findViewById(R.id.tvContValue);
            this.tvShipCompany = (TextView) rootView.findViewById(R.id.tvShipCompany);
            this.rlPlaceLoading = (RelativeLayout) rootView.findViewById(R.id.rlPlaceLoading);
            this.tvVoyage = (TextView) rootView.findViewById(R.id.tvVoyage);
            this.vLine = rootView.findViewById(R.id.vLine);
            this.tvPlaceLoading = (TextView) rootView.findViewById(R.id.tvPlaceLoading);
            this.tvOnboardDate = (TextView) rootView.findViewById(R.id.tvOnboardDate);
            this.tvVesselName = (TextView) rootView.findViewById(R.id.tvVesselName);
            this.tvPlaceDelivery = (TextView) rootView.findViewById(R.id.tvPlaceDelivery);
            this.tvArrival = (TextView) rootView.findViewById(R.id.tvArrival);
            this.imgPortTime = (ImageView) rootView.findViewById(R.id.imgPortTime);
            this.tvPortTime = (TextView) rootView.findViewById(R.id.tvPortTime);
            this.tvPortTimeValue = (TextView) rootView.findViewById(R.id.tvPortTimeValue);
            this.imgCreated = (ImageView) rootView.findViewById(R.id.imgCreated);
            this.tvCreated = (TextView) rootView.findViewById(R.id.tvCreated);
            this.tvCreatedValue = (TextView) rootView.findViewById(R.id.tvCreatedValue);
            this.flDetail = (FrameLayout) rootView.findViewById(R.id.flDetail);
            this.tvTotalFee = (TextView) rootView.findViewById(R.id.tvTotalFee);
            this.tvShipLine = (TextView) rootView.findViewById(R.id.tvShipLine);

        }

    }
}
