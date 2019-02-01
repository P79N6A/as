package com.yaoguang.driver.my.myorder;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.yaoguang.common.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.common.common.DateUtils;
import com.yaoguang.common.common.TimeUtils;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.NavHeaderMainBinding;
import com.yaoguang.driver.my.myorder.adapter.OrderStatisticsMenuAdapter;
import com.yaoguang.greendao.entity.UserOffice;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * 描    述： 我的订单滑动窗口
 * 作    者：韦理英
 * 时    间：2017/8/31 0031.
 * 版    权：
 */
public class MyOrderDialogFragment extends DialogFragment {
    NavHeaderMainBinding mNavHeaderMainBinding;
    private Comeback comeback;
    private HashSet<String> ids = new HashSet<>();  // 选择公司id
    private String selectNum = "1";
    private String mEndData;
    private String mStartData;
    private List<UserOffice> userOffices;
    private RadioButton lastRb;

    public static MyOrderDialogFragment newInstance() {
        Bundle args = new Bundle();
        MyOrderDialogFragment fragment = new MyOrderDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onStart() {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params;
        if (window != null) {
            params = window.getAttributes();
            // 右部显示
            params.gravity = Gravity.RIGHT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.width = ConvertUtils.dp2px(280);
            window.setAttributes(params);
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
        super.onStart();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.nav_header_main, container, false);

        mNavHeaderMainBinding = DataBindingUtil.bind(v);
        // 公司列表
        OrderStatisticsMenuAdapter orderStatisticsMenuAdapter = new OrderStatisticsMenuAdapter();
        orderStatisticsMenuAdapter.getList().clear();
        orderStatisticsMenuAdapter.appendToList(userOffices);
        orderStatisticsMenuAdapter.notifyDataSetChanged();

        mNavHeaderMainBinding.menuRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        mNavHeaderMainBinding.menuRecyclerView.setLayoutManager(layoutManager2);
        mNavHeaderMainBinding.menuRecyclerView.setAdapter(orderStatisticsMenuAdapter);
        orderStatisticsMenuAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (AppClickUtil.isDuplicateClick()) return;

            UserOffice userOffice = (UserOffice) item;
            CheckBox checkBox = itemView.findViewById(R.id.cbTitle);
            if (checkBox.isChecked()) {
                userOffice.setSelect(true);
                ids.add(userOffice.getId());
            } else {
                userOffice.setSelect(false);
                ids.remove(userOffice.getId());
            }
        });
        // 自定义 默认gone
        mNavHeaderMainBinding.llBox.setVisibility(View.GONE);
        initValue();


        // 本月  时间范围类型 0、全部，1、本月，2、上月，3、近三月
        v.findViewById(R.id.rbSelect1).setOnClickListener(v1 -> {
            mEndData = null;
            selectNum = "1";
            mNavHeaderMainBinding.llBox.setVisibility(View.GONE);
        });
        // 上个月
        v.findViewById(R.id.rbSelect2).setOnClickListener(v12 -> {
            mEndData = null;
            selectNum = "2";
            mNavHeaderMainBinding.llBox.setVisibility(View.GONE);
        });
        // 近三个月
        v.findViewById(R.id.rbSelect3).setOnClickListener(v13 -> {
            mEndData = null;
            selectNum = "3";
            mNavHeaderMainBinding.llBox.setVisibility(View.GONE);
        });

        // 自定义
//        view.findViewById(R.id.rbSelectCustom).setOnClickListener(v -> {
//                llBox.setVisibility(View.VISIBLE);
//        });


        // 自定义 按月
        mNavHeaderMainBinding.llMonthValue.setOnClickListener(v14 -> mNavHeaderMainBinding.tvMonthValue.performClick());
        // 自定义 按月
        mNavHeaderMainBinding.tvMonthValue.setOnClickListener(v15 -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                mStartData = data;
                mNavHeaderMainBinding.tvMonthValue.setText(data);
                selectNum = "4";
            });
            dateBeginPickerFragment.show(MyOrderDialogFragment.this.getFragmentManager(), "");
        });

        // 自定义 按日 开始时间选择
        mNavHeaderMainBinding.tvDataStartValue.setOnClickListener(v16 -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                mStartData = data;
                mNavHeaderMainBinding.tvDataStartValue.setText(data);
            });
            dateBeginPickerFragment.show(MyOrderDialogFragment.this.getFragmentManager(), "");
        });
        // 自定义 按日 结束时间选择
        mNavHeaderMainBinding.tvDataEndValue.setOnClickListener(v17 -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                mEndData = data;
                mNavHeaderMainBinding.tvDataEndValue.setText(data);
                selectNum = "5";
            });
            dateBeginPickerFragment.show(MyOrderDialogFragment.this.getFragmentManager(), "");
        });
        // 重置
        mNavHeaderMainBinding.tvReset.setOnClickListener(v18 -> MyOrderDialogFragment.this.initValue());
        // 确定
        mNavHeaderMainBinding.tvSubmit.setOnClickListener(v19 -> {
            if (comeback != null) {
                comeback.success(ids, selectNum, mStartData, mEndData);
            }
        });
        // 选中
        mNavHeaderMainBinding.rgSelect.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbSelectCustom) { // 自定义点击事件
                mNavHeaderMainBinding.llBox.setVisibility(View.VISIBLE);
            }

            //获取变更后的选中项的ID
            int radioButtonId = group.getCheckedRadioButtonId();
            //根据ID获取RadioButton的实例
            RadioButton rb = v.findViewById(radioButtonId);
            if (rb == null) return;
            // 如果上一个rb是空，就默认第一个
            if (lastRb == null) {
                lastRb = mNavHeaderMainBinding.rbSelect1;
            }
            // 设置未选中字体色
            lastRb.setTextColor(UiUtils.getColor(R.color.black));
            // 设置选中颜色
            rb.setTextColor(UiUtils.getColor(R.color.orange500));
            // 记录选中
            lastRb = rb;
            selectNum = rb.getTag().toString();

            mNavHeaderMainBinding.rgSelect2.clearCheck();
        });

        mNavHeaderMainBinding.rbSelectCustom.setOnClickListener(v110 -> mNavHeaderMainBinding.rbSelect4.setChecked(true));
        mNavHeaderMainBinding.rbSelect4.setOnClickListener(v111 -> {
            mNavHeaderMainBinding.rbSelectCustom.setChecked(true);
            mNavHeaderMainBinding.rbSelect4.setChecked(true);
            MyOrderDialogFragment.this.setRgselect2TextColor(mNavHeaderMainBinding.rbSelect4);
        });
        mNavHeaderMainBinding.rbSelect5.setOnClickListener(v112 -> {
            mNavHeaderMainBinding.rbSelectCustom.setChecked(true);
            mNavHeaderMainBinding.rbSelect5.setChecked(true);
            MyOrderDialogFragment.this.setRgselect2TextColor(mNavHeaderMainBinding.rbSelect5);
        });
        return mNavHeaderMainBinding.getRoot();
    }

    /**
     * 设置 RgSelect2 的字体颜色
     *
     * @param view RadioButton
     */
    public void setRgselect2TextColor(RadioButton view) {
        if (view == null) return;
        // 如果上一个rb是空，就默认第一个
        if (lastRb == null) {
            lastRb = mNavHeaderMainBinding.rbSelect1;
        }
        // 设置未选中字体色
        lastRb.setTextColor(UiUtils.getColor(R.color.black));
        // 如果上一个rb是空，就默认第一个
        lastRb = mNavHeaderMainBinding.rbSelectCustom;
        // 设置未选中字体色
        mNavHeaderMainBinding.rbSelectCustom.setTextColor(UiUtils.getColor(R.color.orange500));
        selectNum = view.getTag().toString();
    }

    /**
     * 重置
     *
     */
    private void initValue() {
        // 按月份，默认显示本月
        mNavHeaderMainBinding.tvMonthValue.setText(TimeUtils.date2String(new Date(), TimeUtils.YYYYY_MM_MM_FORMAT));
        // 按日期，默认本月开始与结束
        mNavHeaderMainBinding.tvDataStartValue.setText(DateUtils.dateToString(DateUtils.getMonthFirstDay(new Date()), DateUtils.YYYY_MM_DD));

        mNavHeaderMainBinding.tvDataEndValue.setText(DateUtils.dateToString(DateUtils.getMonthLastDay(new Date()), DateUtils.YYYY_MM_DD));

        mNavHeaderMainBinding.rbSelect1.setChecked(true);
        mStartData = mNavHeaderMainBinding.tvMonthValue.getText().toString();
        mEndData = mNavHeaderMainBinding.tvDataEndValue.getText().toString();

        mNavHeaderMainBinding.llBox.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void setComeback(Comeback comeback) {
        this.comeback = comeback;
    }

    public void setCompanies(List<UserOffice> companies) {
        userOffices = companies;
    }

    interface Comeback {
        void success(HashSet<String> ids, String selectNum, String startData, String endData);
    }


}
