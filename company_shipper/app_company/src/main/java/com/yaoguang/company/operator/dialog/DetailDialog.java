package com.yaoguang.company.operator.dialog;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanWrapper;
import com.yaoguang.lib.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 办单详情的dialog
 * Created by zhongjh on 2018/7/27.
 */
public class DetailDialog extends Dialog {

    Context mContext;
    AppCompanyBanDanWrapper mAppCompanyBanDanWrapper;
    ViewHolder mViewHolder;

    public DetailDialog(Context context, AppCompanyBanDanWrapper appCompanyBanDanWrapper) {
        super(context, R.style.Dialog);
        mContext = context;
        mAppCompanyBanDanWrapper = appCompanyBanDanWrapper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_operator_detail, null);
        mViewHolder = new ViewHolder(layout);
        initData();

        initListener();
        this.setContentView(layout);
    }

    /**
     * 初始化数据源
     */
    private void initData(){
        mViewHolder.tvValueCompanyCopy.setText(mAppCompanyBanDanWrapper.getShipCompany());
        mViewHolder.tvValueShipName.setText(mAppCompanyBanDanWrapper.getVessel());
        mViewHolder.tvValueVoyage.setText(mAppCompanyBanDanWrapper.getVoyage());
        mViewHolder.tvValueWaybill.setText(mAppCompanyBanDanWrapper.getmBlNo());
        mViewHolder.tvCabinet.setText(mAppCompanyBanDanWrapper.getContId());
        mViewHolder.port1.setText(mAppCompanyBanDanWrapper.getPortLoading());
        mViewHolder.port2.setText(mAppCompanyBanDanWrapper.getPortDelivery());
        mViewHolder.tvGoodsName.setText(mAppCompanyBanDanWrapper.getGoodsName());
    }

    /**
     * 事件
     */
    private void initListener(){
        mViewHolder.imgClose.setOnClickListener(v -> DetailDialog.this.dismiss());
        mViewHolder.btnCompanyCopy.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(mViewHolder.tvValueCompanyCopy.getText());
            Toast.makeText(BaseApplication.getInstance(), "复制成功", Toast.LENGTH_LONG).show();
        });
        mViewHolder.btnShipNameCopy.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(mViewHolder.tvValueShipName.getText());
            Toast.makeText(BaseApplication.getInstance(), "复制成功", Toast.LENGTH_LONG).show();
        });
        mViewHolder.btnVoyageCopy.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(mViewHolder.tvValueVoyage.getText());
            Toast.makeText(BaseApplication.getInstance(), "复制成功", Toast.LENGTH_LONG).show();
        });
        mViewHolder.btnWaybillCopy.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(mViewHolder.tvValueWaybill.getText());
            Toast.makeText(BaseApplication.getInstance(), "复制成功", Toast.LENGTH_LONG).show();
        });

    }


    static class ViewHolder {
        @BindView(R.id.imgClose)
        ImageView imgClose;
        @BindView(R.id.toolbar_title)
        TextView toolbarTitle;
        @BindView(R.id.toolbar)
        Toolbar toolbar;
        @BindView(R.id.tvTitleCompany)
        TextView tvTitleCompany;
        @BindView(R.id.btnCompanyCopy)
        TextView btnCompanyCopy;
        @BindView(R.id.tvValueCompanyCopy)
        TextView tvValueCompanyCopy;
        @BindView(R.id.tvTitleShipName)
        TextView tvTitleShipName;
        @BindView(R.id.btnShipNameCopy)
        TextView btnShipNameCopy;
        @BindView(R.id.tvValueShipName)
        TextView tvValueShipName;
        @BindView(R.id.tvTitleVoyage)
        TextView tvTitleVoyage;
        @BindView(R.id.btnVoyageCopy)
        TextView btnVoyageCopy;
        @BindView(R.id.tvValueVoyage)
        TextView tvValueVoyage;
        @BindView(R.id.tvTitleWaybill)
        TextView tvTitleWaybill;
        @BindView(R.id.btnWaybillCopy)
        TextView btnWaybillCopy;
        @BindView(R.id.tvCabinet)
        TextView tvCabinet;
        @BindView(R.id.flCabinet)
        FrameLayout flCabinet;
        @BindView(R.id.tvValueWaybill)
        TextView tvValueWaybill;
        @BindView(R.id.port1)
        TextView port1;
        @BindView(R.id.vLuXian)
        View vLuXian;
        @BindView(R.id.port2)
        TextView port2;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.cvMain)
        CardView cvMain;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
