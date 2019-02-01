package com.yaoguang.appcompanyshipper.phone.businessorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.lib.common.ObjectUtils;

import java.util.List;

import static com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.BaseBusinessOrderTempListFragment.CHILD_VIEW_TYPE;

/**
 * Created by zhongjh on 2018/9/14.
 */

public class BusinessOrderTempListAppInfoClientPlaceAdapter extends RecyclerView.Adapter<BusinessOrderTempListAppInfoClientPlaceAdapter.ViewHolder> {

    private List<AppInfoClientPlace> list; // 数据源
    private Context mContext;
    private int mType;

    /**
     *
     * @param context 上下文
     * @param type 0是装货，1是卸货
     */
    public BusinessOrderTempListAppInfoClientPlaceAdapter(Context context,int type) {
        super();
        mContext = context;
        mType = type;
    }

    @Override
    public int getItemViewType(int position) {
        return CHILD_VIEW_TYPE;
    }

    public void setData(List<AppInfoClientPlace> data) {
        this.list = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order_temp_loading, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (mType== 0){
            holder.imgLoadingOrUnLoading.setImageResource(R.drawable.ic_zhaung_14);
        }else{
            holder.imgLoadingOrUnLoading.setImageResource(R.drawable.ic_xie_14);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfoClientPlace appInfoClientPlace = list.get(position);
        holder.tvName.setText(ObjectUtils.parseString(appInfoClientPlace.getLinkman()) + " " + ObjectUtils.parseString(appInfoClientPlace.getLinkmanMp()) + " " + ObjectUtils.parseString(appInfoClientPlace.getLinkmanTel()));
        holder.tvShortName.setText((appInfoClientPlace.getRegionid() + appInfoClientPlace.getAddress()));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgLoadingOrUnLoading;
        public TextView tvName;
        public TextView tvShortName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgLoadingOrUnLoading = rootView.findViewById(R.id.imgLoadingOrUnLoading);
            this.tvName = rootView.findViewById(R.id.tvName);
            this.tvShortName = rootView.findViewById(R.id.tvShortName);
        }

    }
}
