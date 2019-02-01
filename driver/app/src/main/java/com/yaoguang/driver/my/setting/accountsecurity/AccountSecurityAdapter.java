package com.yaoguang.driver.my.setting.accountsecurity;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.driver.R;
import com.yaoguang.driver.my.setting.SettingAdapter;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/23
 * 描    述：
 * 帐户安全adapter
 * =====================================
 */

public class AccountSecurityAdapter extends BaseLoadMoreRecyclerAdapter<String, RecyclerView.ViewHolder> {
    private final String[] mArrayParam;

    public AccountSecurityAdapter(String[] mArrayParam) {
        this.mArrayParam = mArrayParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_account_security_item, null);

        final AccountSecurityAdapter.ViewHolder viewHolder = new AccountSecurityAdapter.ViewHolder(view);
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
        AccountSecurityAdapter.ViewHolder _holder = (AccountSecurityAdapter.ViewHolder) holder;
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
        if (position == 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(50));
            params.setMargins(0, ConvertUtils.dp2px(10), 0, 0);
            _holder.linearLayout.setLayoutParams(params);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        LinearLayout item;
        LinearLayout linearLayout;
        ImageView checkbox;
        TextView alert;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(com.yaoguang.appcommon.R.id.title);
            linearLayout = itemView.findViewById(com.yaoguang.appcommon.R.id.linearLayout);
            item = itemView.findViewById(com.yaoguang.appcommon.R.id.item);
            checkbox = itemView.findViewById(com.yaoguang.appcommon.R.id.image);
            alert = itemView.findViewById(com.yaoguang.appcommon.R.id.text);
        }
    }
}
