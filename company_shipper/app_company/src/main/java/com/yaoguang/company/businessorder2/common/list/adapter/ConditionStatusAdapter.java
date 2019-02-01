package com.yaoguang.company.businessorder2.common.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.Option;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 时间条件的适配器
 * Created by zhongjh on 2018/10/31.
 */
public class ConditionStatusAdapter extends BaseLoadMoreRecyclerAdapter<SysCondition, RecyclerView.ViewHolder> {

    FrameLayout mFrameLayout;
    TextView mTextView;// 当前选择后改变的值
    int mPosition;// 当前选择的position
    LinkedHashMap<Integer, SweetSheet> sheetHashMap = new LinkedHashMap<>();

    public ConditionStatusAdapter(FrameLayout flMain) {
        mFrameLayout = flMain;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_order_list_status, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();

            // 弹出选择框
            mTextView = holder.tvValue;
            mPosition = position;
            sheetHashMap.get(position).show();
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SysCondition myItem = getList().get(position);
        viewHolder.tvTitle.setText(myItem.getDisplayName());
        if (!TextUtils.isEmpty(myItem.getInputValue2())) {
            viewHolder.tvValue.setText(myItem.getInputValue2());
        } else {
            if (ObjectUtils.parseString(myItem.getInputValue(), "").equals("1")) {
                viewHolder.tvValue.setText("是");
            } else if (ObjectUtils.parseString(myItem.getInputValue(), "").equals("0")) {
                viewHolder.tvValue.setText("否");
            } else if (ObjectUtils.parseString(myItem.getInputValue(), "").equals("")) {
                viewHolder.tvValue.setText("不限");
            }
        }
        // 生成sweet
        if (sheetHashMap.get(position) == null) {
            initSweet(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvValue;
        public ImageView imgSelect;
        public RelativeLayout rlShipper;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvValue = rootView.findViewById(R.id.tvValue);
            this.imgSelect = rootView.findViewById(R.id.imgSelect);
            this.rlShipper = rootView.findViewById(R.id.rlShipper);
        }

    }

    /**
     * 初始化选择框
     */
    private void initSweet(int intPosition) {
        SweetSheet sweetSheet2 = new SweetSheet(mFrameLayout);
        if (getItem(intPosition).getOptions() != null && getItem(intPosition).getOptions().size() > 0) {
            List<MenuEntity> menuEntities = new ArrayList<>();
            for (Option option : getItem(intPosition).getOptions()) {
                MenuEntity menuEntity = new MenuEntity();
                menuEntity.title = option.getShowValue();
                menuEntity.value = option.getValue();
                menuEntities.add(menuEntity);
            }
            sweetSheet2.setMenuList(menuEntities);
        } else {
            sweetSheet2.setMenuList(R.menu.sheet_business_status);
        }

        sweetSheet2.setTitle("请选择");
        RecyclerViewDelegate recyclerViewDelegate2 = new RecyclerViewDelegate(true);
        recyclerViewDelegate2.setContentHeight(DisplayMetricsUtils.dip2px(250));
        sweetSheet2.setDelegate(recyclerViewDelegate2);
        sweetSheet2.setBackgroundEffect(new DimEffect(4));
        sweetSheet2.setOnMenuItemClickListener((position, menuEntity1) -> {
            mTextView.setText(menuEntity1.title.toString());
            if (menuEntity1.title.equals("是")) {
                list.get(mPosition).setInputValue("1");
            } else if (menuEntity1.title.equals("否")) {
                list.get(mPosition).setInputValue("0");
            } else if (menuEntity1.title.equals("不限")) {
                list.get(mPosition).setInputValue("");
            } else {
                list.get(mPosition).setInputValue(menuEntity1.value);
                list.get(mPosition).setInputValue2(menuEntity1.title.toString());
            }
            return true;
        });

        sheetHashMap.put(intPosition, sweetSheet2);

    }

}
