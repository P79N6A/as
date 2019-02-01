package com.yaoguang.company.businessorder2.common.list.adapter;

import android.support.constraint.Guideline;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 时间条件的适配器
 * Created by zhongjh on 2018/10/31.
 */
public class ConditionDateAdapter extends BaseLoadMoreRecyclerAdapter<SysCondition, RecyclerView.ViewHolder> {

    FragmentManager mFragmentManager;

    public ConditionDateAdapter(FragmentManager fragmentManager){
        mFragmentManager = fragmentManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order_list_date, parent, false);
        ViewHolder holder = new ViewHolder(view,new MyCustomEditTextListener(),new MyCustomEditTextListener2());
        holder.tvBeginDate.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(mFragmentManager, "itemDateBegin");
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("itemDateBegin")) {
                    holder.tvBeginDate.setText(data);
                    list.get(position).setInputValue(data);
                }
            });
        });
        holder.tvEndDate.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(mFragmentManager, "itemDateEnd");
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("itemDateEnd")) {
                    holder.tvEndDate.setText(data);
                    list.get(position).setInputValue2(data);
                }
            });
        });
        holder.imgDelete.setOnClickListener(v -> {
            holder.tvBeginDate.setText("");
            holder.tvEndDate.setText("");
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SysCondition myItem = getList().get(position);
        viewHolder.tvTitle.setText(myItem.getDisplayName());
        viewHolder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        viewHolder.myCustomEditTextListener2.updatePosition(holder.getAdapterPosition());
        viewHolder.tvBeginDate.setText(ObjectUtils.parseString(myItem.getInputValue()));
        viewHolder.tvEndDate.setText(ObjectUtils.parseString(myItem.getInputValue2()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public ImageView imgEnd;
        public View vDelete;
        public ImageView imgDelete;
        public ImageView imgBegin;
        public TextView tvZhi;
        public Guideline guidelineV43;
        public TextView tvEndDate;
        public TextView tvBeginDate;

        public MyCustomEditTextListener myCustomEditTextListener;
        public MyCustomEditTextListener2 myCustomEditTextListener2;

        public ViewHolder(View rootView,MyCustomEditTextListener myCustomEditTextListener,MyCustomEditTextListener2 myCustomEditTextListener2) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.imgEnd = rootView.findViewById(R.id.imgEnd);
            this.vDelete = rootView.findViewById(R.id.vDelete);
            this.imgDelete = rootView.findViewById(R.id.imgDelete);
            this.imgBegin = rootView.findViewById(R.id.imgBegin);
            this.tvZhi = rootView.findViewById(R.id.tvZhi);
            this.guidelineV43 = rootView.findViewById(R.id.guidelineV43);
            this.tvEndDate = rootView.findViewById(R.id.tvEndDate);
            this.tvBeginDate = rootView.findViewById(R.id.tvBeginDate);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.myCustomEditTextListener2 = myCustomEditTextListener2;
            this.tvBeginDate.addTextChangedListener(myCustomEditTextListener);
            this.tvEndDate.addTextChangedListener(myCustomEditTextListener2);
        }

    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            getItem(position).setInputValue(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class MyCustomEditTextListener2 implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            getItem(position).setInputValue2(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

}
