package com.yaoguang.appcommon.publicsearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;

import java.util.ArrayList;

/**
 * 搜索框查询后的内容适配器
 * Created by zhongjh on 2017/6/5.
 */
public class SearchLoadingAndUnloadingAdapter extends BaseLoadMoreRecyclerAdapter<AppPublicInfoWrapper, RecyclerView.ViewHolder> {

    //已选择的ids,逗号分隔
    String mIds;
    //已有的地址，跟上个ids一样的作用，只是这个作用于脱离地址模块的
    ArrayList<AppInfoClientPlace> mAppInfoClientPlaces;

    public SearchLoadingAndUnloadingAdapter(String ids, ArrayList<AppInfoClientPlace> appInfoClientPlaces) {
        mIds = ids;
        mAppInfoClientPlaces = appInfoClientPlaces;
    }

    //回调接口
    private Listener listener = null;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void editListener(View itemView, AppPublicInfoWrapper item, int position);

        void deleteListener(View itemView, AppPublicInfoWrapper item, int position);
    }


    private boolean isItemChecked(int position) {
        if (mAppInfoClientPlaces != null) {
            return getList().get(position).isCheck();
        } else {
            if (mIds.contains(getList().get(position).getId())) {
                return true;
            } else {
                return getList().get(position).isCheck();
            }
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvShortName;
        private CheckBox checkBox;
        private LinearLayout llEdit;
        private LinearLayout llDelete;

        ItemViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvShortName = (TextView) view.findViewById(R.id.tvShortName);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            llEdit = (LinearLayout) view.findViewById(R.id.llEdit);
            llDelete = (LinearLayout) view.findViewById(R.id.llDelete);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_loading, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    getList().get(position).setCheck(isChecked);
                };
            }
        });

        holder.llEdit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (listener != null)
                        listener.editListener(view, getList().get(position), position);
                }
            }
        });
        holder.llDelete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (listener != null)
                        listener.deleteListener(view, getList().get(position), position);
                }
            }
        });

        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (isItemChecked(position)) {
                        holder.checkBox.setChecked(false);
                    } else {
                        holder.checkBox.setChecked(true);
                    }
                    mOnItemClickListener.onItemClick(v, getList().get(position), position);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvName.setText(getList().get(position).getRemark1() + " " + getList().get(position).getRemark2() + " " + getList().get(position).getRemark3());
        itemViewHolder.tvShortName.setText(getList().get(position).getFullName() + getList().get(position).getShortName());
        itemViewHolder.checkBox.setChecked(isItemChecked(position));
    }

}
