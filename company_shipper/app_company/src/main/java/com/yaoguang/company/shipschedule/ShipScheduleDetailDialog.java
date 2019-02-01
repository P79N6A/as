package com.yaoguang.company.shipschedule;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.InfoVoyageTable;

/**
 * Created by zhongjh on 2017/7/12.
 */
public class ShipScheduleDetailDialog extends Dialog {
    Context mContext;
    InfoVoyageTable mInfoVoyageTable;

    public ShipScheduleDetailDialog(Context context, InfoVoyageTable infoVoyageTable) {
        super(context, R.style.Dialog);
        mContext = context;
        mInfoVoyageTable = infoVoyageTable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_ship_schedule, null);
        ViewHolder viewHolder = new ViewHolder(layout);
        viewHolder.toolbar_title.setText("费用明细");
        viewHolder.tvOceanfee.setText("¥" + ObjectUtils.parseString(mInfoVoyageTable.getOceanfee()));
        viewHolder.tvContFee.setText("¥" + ObjectUtils.parseString(mInfoVoyageTable.getContFee()));
        viewHolder.tvOrderFee.setText("¥" + ObjectUtils.parseString(mInfoVoyageTable.getOrderFee()));
        viewHolder.tvFuelFee.setText("¥" + ObjectUtils.parseString(mInfoVoyageTable.getFuelFee()));
        viewHolder.tvPortFee.setText("¥" + ObjectUtils.parseString(mInfoVoyageTable.getPortFee()));
        viewHolder.tvRemark.setText(ObjectUtils.parseString(mInfoVoyageTable.getRemark()));
        viewHolder.btnClose.setOnClickListener(v -> ShipScheduleDetailDialog.this.dismiss());
        this.setContentView(layout);
    }

    public static class ViewHolder {
        public View rootView;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public TextView tvOceanfee;
        public TextView tvContFee;
        public TextView tvOrderFee;
        public TextView tvFuelFee;
        public TextView tvPortFee;
        public TextView tvRemark;
        public Button btnClose;
        public LinearLayout log_in_layout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.tvOceanfee = (TextView) rootView.findViewById(R.id.tvOceanfee);
            this.tvContFee = (TextView) rootView.findViewById(R.id.tvContFee);
            this.tvOrderFee = (TextView) rootView.findViewById(R.id.tvOrderFee);
            this.tvFuelFee = (TextView) rootView.findViewById(R.id.tvFuelFee);
            this.tvPortFee = (TextView) rootView.findViewById(R.id.tvPortFee);
            this.tvRemark = (TextView) rootView.findViewById(R.id.tvRemark);
            this.btnClose = (Button) rootView.findViewById(R.id.btnClose);
            this.log_in_layout = (LinearLayout) rootView.findViewById(R.id.log_in_layout);
        }

    }
}