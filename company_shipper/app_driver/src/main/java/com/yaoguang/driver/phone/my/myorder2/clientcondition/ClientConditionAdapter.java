package com.yaoguang.driver.phone.my.myorder2.clientcondition;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.driver.DriverEntrustCompany;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 适配器
 * Created by zhongjh on 2018/4/9.
 */
public class ClientConditionAdapter extends BaseLoadMoreRecyclerAdapter<DriverEntrustCompany, RecyclerView.ViewHolder> {

    Fragment mFragment;

    public HashMap<String, String> getmCompanyNameHas() {
        return mCompanyNameHas;
    }

    public void setmCompanyNameHas(HashMap<String, String> mCompanyNameHas) {
        this.mCompanyNameHas = mCompanyNameHas;
    }

    HashMap<String, String> mCompanyNameHas;


    public ClientConditionAdapter(Fragment fragment, HashMap<String, String> companyNameHas) {
        mFragment = fragment;
        mCompanyNameHas = companyNameHas;
        if (mCompanyNameHas == null)
            mCompanyNameHas = new HashMap<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrust_company, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            DriverEntrustCompany driverEntrustCompany = getItem(position);
            // 判断是否存在,不存在就添加进去，存在就删掉
            if (!mCompanyNameHas.containsKey(driverEntrustCompany.getEntrustCompanyId())) {
                // 不存在，添加
                mCompanyNameHas.put(driverEntrustCompany.getEntrustCompanyId(), driverEntrustCompany.getEntrustCompanyName());
            } else {
                // 存在，删除
                mCompanyNameHas.remove(driverEntrustCompany.getEntrustCompanyId());
            }
            notifyItemChanged(position);
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        itemViewHolder.tvCompanyName.setText(getList().get(position).getEntrustCompanyName());

        String photo = getList().get(position).getEntrustCompanyPhoto();
        if (!TextUtils.isEmpty(GlideManager.getImageUrl(photo, false)) && !photo.equals("null")) {
            GlideManager.getInstance().withRounded(mFragment.getContext(), GlideManager.getImageUrl(photo, false), itemViewHolder.ivPhotoGraph, R.drawable.ic_qymrtx);
        }

        // 判断是否存在
        if (!mCompanyNameHas.containsKey(getItem(position).getEntrustCompanyId())) {
            // 不存在
            itemViewHolder.ivSelect.setVisibility(View.INVISIBLE);
        } else {
            // 存在
            itemViewHolder.ivSelect.setVisibility(View.VISIBLE);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPhotoGraph)
        ImageView ivPhotoGraph;
        @BindView(R.id.tvCompanyName)
        TextView tvCompanyName;
        @BindView(R.id.ivSelect)
        ImageView ivSelect;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
