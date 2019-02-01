package com.yaoguang.driver.my.my.adater;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.yaoguang.common.adapter.BaseRecyclerHeaderAndFooterAdapter;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.MyItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 韦理英
 * on 2017/6/12 0012.
 */

public class PersonalInformationAndFooterAdapter extends BaseRecyclerHeaderAndFooterAdapter<MyItem, RecyclerView.ViewHolder> {


    @Override
    protected RecyclerView.ViewHolder getFooterHolder(ViewGroup parent, View mHeaderView) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(ViewGroup parent, View mHeaderView) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_personalinfomation_head, parent, false);
        HeaderHolder headerHolder = new HeaderHolder(view);
        view.setOnClickListener(v -> {
            int adapterPosition = headerHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, getItem(adapterPosition));
            }
        });
        return headerHolder;
    }

    @Override
    protected RecyclerView.ViewHolder getNormalHolder(ViewGroup parent, View mHeaderView) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personalinformation_item, parent, false);
        ViewNormalHolder holder = new ViewNormalHolder(view);
        view.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(view, getItem(adapterPosition));
            }
        });
        return holder;
    }

    @Override
    protected void onBindItemFooterViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    protected void onBindItemHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindItemNormalViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewNormalHolder) {
            ViewNormalHolder holder = (ViewNormalHolder) viewHolder;
            MyItem myItem = getList().get(position - 1);

            if (myItem.getType() == 0) {
                holder.tvName.setText(myItem.getTitle());
                holder.llRow.setVisibility(View.VISIBLE);
                if (myItem.getValue() != null && !myItem.getValue().startsWith("http")) {
                    String value = myItem.getValue();

                    // 手机号中间加*
                    if (myItem.getTitle().equals("更换手机号码") && myItem.getValue().length() > 10) {
                        value = value.substring(0, 3) + "******" + value.substring(9, 11);
                    }
//                    else if (myItem.getTitle().equals("个人简介") && value.length() > 10) {
//                        value = value.substring(0, 10) + "...";
//                    }
                    holder.tvValue.setText(value);
                }
            } else {
                holder.llRow.setVisibility(View.GONE);
            }

            //  分隔线
            if (myItem.getTitle().equals("重型半挂牵引车") || myItem.getTitle().equals("电子邮箱")) {
                holder.line.setVisibility(View.GONE);
            } else {
                holder.line.setVisibility(View.VISIBLE);
            }

            //  banner
            if (myItem.getType() == 1) {
                int res = 0;
                if (myItem.getTitle().contains("基本信息")) {
                    res = R.drawable.ic_js_yellow;
                } else if (myItem.getTitle().contains("我的车辆")) {
                    res = R.drawable.ic_gc_blue;
                } else if (myItem.getTitle().contains("其它信息")) {
                    res = R.drawable.ic_qy_green;
                }

                holder.llBanner.setVisibility(View.VISIBLE);
                holder.llRow.setVisibility(View.GONE);
                holder.line.setVisibility(View.GONE);
                holder.tvBannerTitle.setText(myItem.getTitle());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    holder.tvBannerTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(UiUtils.getDrawable(res), null, null, null);
                }
            } else {
                holder.llBanner.setVisibility(View.GONE);
            }
        }
    }

    public class ViewNormalHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_item)
        LinearLayout item;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvValue)
        TextView tvValue;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.dividers)
        View dividers;
        @BindView(R.id.llBanner)
        View llBanner;
        @BindView(R.id.tvBannerTitle)
        TextView tvBannerTitle;
        @BindView(R.id.llRow)
        View llRow;
        @BindView(R.id.line)
        View line;

        ViewNormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        HeaderHolder(View itemView) {
            super(itemView);
        }
    }
}
