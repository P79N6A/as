package com.yaoguang.driver.my.myorder.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import com.yaoguang.common.Glide.impl.GlideManager;
import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.common.Constants;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.Contact;

/**
 * 关联的公司适配器 - 已关联
 * Created by wly on 2017/4/7.
 */
public class CompanyAdapter extends BaseLoadMoreRecyclerAdapter<Contact, RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter<CompanyAdapter.HeaderHolder> {

    private Fragment mFragment;

    public CompanyAdapter(Fragment fragment) {
        mFragment = fragment;
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        HeaderHolder(View view) {
            super(view);
            tvTitle = (TextView) view
                    .findViewById(R.id.tvTitle);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvCompanyName;
        ImageView ivPhotoGraph;

        ItemViewHolder(View view) {
            super(view);
            tvCompanyName = (TextView) view
                    .findViewById(R.id.tvCompanyName);
            ivPhotoGraph = (ImageView) view.findViewById(R.id.ivPhotoGraph);
        }
    }

    @Override
    public long getHeaderId(int position) {
        return getList().get(position).getFirstletterid();
    }

    @Override
    public CompanyAdapter.HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(CompanyAdapter.HeaderHolder holder, int position) {
        if (position < getList().size()) {
            holder.tvTitle.setText(getList().get(position).getFirstletter());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_pass, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(view, CompanyAdapter.this.getList().get(position), position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvCompanyName.setText(getList().get(position).getUserOfficeName());
        GlideManager.getInstance().with(mFragment.getContext(), Constants.HEAD + getList().get(position).getPhoto(), itemViewHolder.ivPhotoGraph);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = getList().get(i).getFirstletter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


}
