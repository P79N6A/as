package com.yaoguang.driver.my.setting;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;


/**
 * 作者：韦理英
 * 时间： 2017/5/18 0018.
 */

public class SettingAdapter extends BaseLoadMoreRecyclerAdapter<String, RecyclerView.ViewHolder> {
    private final String[] mArrayParam;

    public SettingAdapter(String[] arrayParam) {
        mArrayParam = arrayParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item,
                parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mOnItemClickListener != null) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        mOnItemClickListener.onItemClick(view, getItem(adapterPosition), adapterPosition);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder _holder = (ViewHolder) holder;
        String title = getList().get(position);
        //  标题
        _holder.title.setText(title);
        //  右边的值
        String value = mArrayParam[position];
        if (!TextUtils.isEmpty(value)) {
            _holder.alert.setText(value);
            _holder.alert.setVisibility(View.VISIBLE);
        } else {
            _holder.alert.setVisibility(View.GONE);
        }

        // 间隔
        if (title.equals("清除缓存") || title.equals("帐户安全")) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(50));
            params.setMargins(0, 0, 0, ConvertUtils.dp2px(10));
            _holder.linearLayout.setLayoutParams(params);
            _holder.line.setVisibility(View.GONE);
        } else {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(50));
            params.setMargins(0, 0, 0, 0);
            _holder.linearLayout.setLayoutParams(params);
            _holder.line.setVisibility(View.VISIBLE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        LinearLayout item;
        LinearLayout linearLayout;
        ImageView checkbox;
        TextView alert;
        View line;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            item = itemView.findViewById(R.id.item);
            checkbox = itemView.findViewById(R.id.image);
            alert = itemView.findViewById(R.id.text);
            line = itemView.findViewById(R.id.line);
        }
    }
}
