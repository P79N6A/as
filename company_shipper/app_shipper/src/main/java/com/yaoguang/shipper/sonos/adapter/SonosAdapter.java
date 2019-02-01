package com.yaoguang.shipper.sonos.adapter;

import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.greendao.entity.AppOwnerSonoWrapper;
import com.yaoguang.shipper.App;
import com.yaoguang.shipper.R;

import static com.yaoguang.lib.common.DateUtils.YYYY_MM_DD;

/**
 * 货柜适配器
 * Created by zhongjh on 2017/6/15.
 */
public class SonosAdapter extends BaseLoadMoreRecyclerAdapter<AppOwnerSonoWrapper, RecyclerView.ViewHolder> {

    int mColorPrimary = 0;


    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sonos2, parent, false);
        if (mColorPrimary == 0) {
            TypedArray array = parent.getContext().getTheme().obtainStyledAttributes(new int[]{com.yaoguang.lib.R.attr.colorPrimary});
            mColorPrimary = array.getColor(0, 0);
            array.recycle();
        }
//        view.setOnClickListener(v -> {
//            final int position = holder.getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                mOnItemClickListener.onItemClick(v, getList().get(position));
//            }
//        });
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AppOwnerSonoWrapper appOwnerSonoWrapper = getList().get(position);

        itemViewHolder.tvContNo.setText(ObjectUtils.parseString(appOwnerSonoWrapper.getContNo()));
        itemViewHolder.tvCreated.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerSonoWrapper.getCreated()));

        itemViewHolder.tvMBlno.setText(ObjectUtils.parseString(appOwnerSonoWrapper.getmBlno()));
        if (appOwnerSonoWrapper != null) {
            //判断如果有值，黑点就变亮点

            itemViewHolder.tvTruckPlanTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerSonoWrapper.getTruckPlanTime()));
            itemViewHolder.tvLoadOverDate.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerSonoWrapper.getLoadOverDate()));
            itemViewHolder.tvFirstEtd.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerSonoWrapper.getFirstEtd()));
            itemViewHolder.tvFirstEta.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerSonoWrapper.getFirstEta()));
            itemViewHolder.tvDestTruckPlanTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerSonoWrapper.getDestTruckPlanTime()));
            itemViewHolder.tvDeliveryOverDate.setText(ObjectUtils.parseStringYYYYMMDDHHMM(appOwnerSonoWrapper.getDeliveryOverDate()));

            if (!itemViewHolder.tvTruckPlanTime.getText().toString().equals("")) {
                itemViewHolder.imgIsTruckPlanTime.setImageResource(R.drawable.ic_huangdian);
                itemViewHolder.tvTruckPlanTime.setTextColor(mColorPrimary);
            } else {
                itemViewHolder.imgIsTruckPlanTime.setImageResource(R.drawable.ic_huidian);
            }

            if (!itemViewHolder.tvLoadOverDate.getText().toString().equals("")) {
                itemViewHolder.imgIsLoadOverDate.setImageResource(R.drawable.ic_huangdian);
                itemViewHolder.tvLoadOverDate.setTextColor(mColorPrimary);
            } else {
                itemViewHolder.imgIsLoadOverDate.setImageResource(R.drawable.ic_huidian);
            }

            if (!itemViewHolder.tvFirstEtd.getText().toString().equals("")) {
                itemViewHolder.imgIsFirstEtd.setImageResource(R.drawable.ic_huangdian);
                itemViewHolder.tvFirstEtd.setTextColor(mColorPrimary);
            } else {
                itemViewHolder.imgIsFirstEtd.setImageResource(R.drawable.ic_huidian);
            }

            if (!itemViewHolder.tvFirstEta.getText().toString().equals("")) {
                itemViewHolder.imgIsFirstEta.setImageResource(R.drawable.ic_huangdian);
                itemViewHolder.tvFirstEta.setTextColor(mColorPrimary);
            } else {
                itemViewHolder.imgIsFirstEta.setImageResource(R.drawable.ic_huidian);
            }

            if (!itemViewHolder.tvDestTruckPlanTime.getText().toString().equals("")) {
                itemViewHolder.imgIsDestTruckPlanTime.setImageResource(R.drawable.ic_huangdian);
                itemViewHolder.tvDestTruckPlanTime.setTextColor(mColorPrimary);
            } else {
                itemViewHolder.imgIsDestTruckPlanTime.setImageResource(R.drawable.ic_huidian);
            }

            if (!itemViewHolder.tvDeliveryOverDate.getText().toString().equals("")) {
                itemViewHolder.imgIsDeliveryOver.setImageResource(R.drawable.ic_huangdian);
                itemViewHolder.tvDeliveryOverDate.setTextColor(mColorPrimary);
            } else {
                itemViewHolder.imgIsDeliveryOver.setImageResource(R.drawable.ic_huidian);
            }
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvMBlno;
        public TextView tvContNo;
        public TextView tvCreated;
        public View vIsTruckPlanTime;
        public View vIsLoadOverDate;
        public View vIsFirstEtd;
        public View vIsFirstEta;
        public View vIsDestTruckPlanTime;
        public ImageView imgIsTruckPlanTime;
        public ImageView imgIsLoadOverDate;
        public ImageView imgIsFirstEtd;
        public ImageView imgIsFirstEta;
        public ImageView imgIsDestTruckPlanTime;
        public ImageView imgIsDeliveryOver;
        public TextView tvIsTruckPlanTime;
        public TextView tvIsLoadOverDate;
        public TextView tvIsFirstEtd;
        public TextView tvIsFirstEta;
        public TextView tvIsDestTruckPlanTime;
        public TextView tvIsDeliveryOver;
        public TextView tvTruckPlanTime;
        public TextView tvLoadOverDate;
        public TextView tvFirstEtd;
        public TextView tvFirstEta;
        public TextView tvDestTruckPlanTime;
        public TextView tvDeliveryOverDate;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvMBlno = (TextView) rootView.findViewById(R.id.tvMBlno);
            this.tvContNo = (TextView) rootView.findViewById(R.id.tvContNo);
            this.tvCreated = (TextView) rootView.findViewById(R.id.tvCreated);
            this.vIsTruckPlanTime = (View) rootView.findViewById(R.id.vIsTruckPlanTime);
            this.vIsLoadOverDate = (View) rootView.findViewById(R.id.vIsLoadOverDate);
            this.vIsFirstEtd = (View) rootView.findViewById(R.id.vIsFirstEtd);
            this.vIsFirstEta = (View) rootView.findViewById(R.id.vIsFirstEta);
            this.vIsDestTruckPlanTime = (View) rootView.findViewById(R.id.vIsDestTruckPlanTime);
            this.imgIsTruckPlanTime = (ImageView) rootView.findViewById(R.id.imgIsTruckPlanTime);
            this.imgIsLoadOverDate = (ImageView) rootView.findViewById(R.id.imgIsLoadOverDate);
            this.imgIsFirstEtd = (ImageView) rootView.findViewById(R.id.imgIsFirstEtd);
            this.imgIsFirstEta = (ImageView) rootView.findViewById(R.id.imgIsFirstEta);
            this.imgIsDestTruckPlanTime = (ImageView) rootView.findViewById(R.id.imgIsDestTruckPlanTime);
            this.imgIsDeliveryOver = (ImageView) rootView.findViewById(R.id.imgIsDeliveryOver);
            this.tvIsTruckPlanTime = (TextView) rootView.findViewById(R.id.tvIsTruckPlanTime);
            this.tvIsLoadOverDate = (TextView) rootView.findViewById(R.id.tvIsLoadOverDate);
            this.tvIsFirstEtd = (TextView) rootView.findViewById(R.id.tvIsFirstEtd);
            this.tvIsFirstEta = (TextView) rootView.findViewById(R.id.tvIsFirstEta);
            this.tvIsDestTruckPlanTime = (TextView) rootView.findViewById(R.id.tvIsDestTruckPlanTime);
            this.tvIsDeliveryOver = (TextView) rootView.findViewById(R.id.tvIsDeliveryOver);
            this.tvTruckPlanTime = (TextView) rootView.findViewById(R.id.tvTruckPlanTime);
            this.tvLoadOverDate = (TextView) rootView.findViewById(R.id.tvLoadOverDate);
            this.tvFirstEtd = (TextView) rootView.findViewById(R.id.tvFirstEtd);
            this.tvFirstEta = (TextView) rootView.findViewById(R.id.tvFirstEta);
            this.tvDestTruckPlanTime = (TextView) rootView.findViewById(R.id.tvDestTruckPlanTime);
            this.tvDeliveryOverDate = (TextView) rootView.findViewById(R.id.tvDeliveryOverDate);
        }

    }
}
