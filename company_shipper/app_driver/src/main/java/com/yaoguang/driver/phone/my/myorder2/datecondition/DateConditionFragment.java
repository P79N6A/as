package com.yaoguang.driver.phone.my.myorder2.datecondition;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentMyOrderDataConditionBinding;
import com.yaoguang.greendao.entity.driver.DriverOrderCondition;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * 时间条件筛选
 * Created by zhongjh on 2018/4/8.
 */
public class DateConditionFragment extends BaseFragmentDataBind<FragmentMyOrderDataConditionBinding> implements Toolbar.OnMenuItemClickListener {

    DriverOrderCondition mDriverOrderCondition = new DriverOrderCondition();

    public static DateConditionFragment newInstance(DriverOrderCondition mDriverOrderCondition) {
        DateConditionFragment fragment = new DateConditionFragment();
        Bundle args = new Bundle();
        args.putParcelable("DriverOrderCondition", mDriverOrderCondition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_order_data_condition;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_sure) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("DriverOrderCondition", mDriverOrderCondition);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        }
        return false;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "选择时间", R.menu.menu_order_data_type, this);
        // 获取数据
        Bundle bundle = getArguments();
        if (bundle != null)
            mDriverOrderCondition = getArguments().getParcelable("DriverOrderCondition");

    }

    @Override
    public void initListener() {
        // (0、自定义，1、近1周，2、近1月，3、近3月，4、近6月 5、近1年)
        mDataBinding.tvWeek.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDriverOrderCondition.setDateScopeType("1");
                mDataBinding.tvMonth.setChecked(false);
                mDataBinding.tvThreeMonth.setChecked(false);
                mDataBinding.tvSixMonth.setChecked(false);
                mDataBinding.tvYear.setChecked(false);
                mDataBinding.tvCustom.setChecked(false);

                mDataBinding.tvDateBegin.setOnClickListener(null);
                mDataBinding.tvDateEnd.setOnClickListener(null);
            }
        });
        mDataBinding.tvMonth.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDriverOrderCondition.setDateScopeType("2");
                mDataBinding.tvWeek.setChecked(false);
                mDataBinding.tvThreeMonth.setChecked(false);
                mDataBinding.tvSixMonth.setChecked(false);
                mDataBinding.tvYear.setChecked(false);
                mDataBinding.tvCustom.setChecked(false);

                mDataBinding.tvDateBegin.setOnClickListener(null);
                mDataBinding.tvDateEnd.setOnClickListener(null);
            }
        });
        mDataBinding.tvThreeMonth.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDriverOrderCondition.setDateScopeType("3");
                mDataBinding.tvWeek.setChecked(false);
                mDataBinding.tvMonth.setChecked(false);
                mDataBinding.tvSixMonth.setChecked(false);
                mDataBinding.tvYear.setChecked(false);
                mDataBinding.tvCustom.setChecked(false);

                mDataBinding.tvDateBegin.setOnClickListener(null);
                mDataBinding.tvDateEnd.setOnClickListener(null);
            }
        });
        mDataBinding.tvSixMonth.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDriverOrderCondition.setDateScopeType("4");
                mDataBinding.tvWeek.setChecked(false);
                mDataBinding.tvMonth.setChecked(false);
                mDataBinding.tvThreeMonth.setChecked(false);
                mDataBinding.tvYear.setChecked(false);
                mDataBinding.tvCustom.setChecked(false);

                mDataBinding.tvDateBegin.setOnClickListener(null);
                mDataBinding.tvDateEnd.setOnClickListener(null);
            }
        });
        mDataBinding.tvYear.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDriverOrderCondition.setDateScopeType("5");
                mDataBinding.tvWeek.setChecked(false);
                mDataBinding.tvMonth.setChecked(false);
                mDataBinding.tvThreeMonth.setChecked(false);
                mDataBinding.tvSixMonth.setChecked(false);
                mDataBinding.tvCustom.setChecked(false);

                mDataBinding.tvDateBegin.setOnClickListener(null);
                mDataBinding.tvDateEnd.setOnClickListener(null);
            }
        });
        mDataBinding.tvCustom.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDriverOrderCondition.setDateScopeType("0");
                mDataBinding.tvWeek.setChecked(false);
                mDataBinding.tvMonth.setChecked(false);
                mDataBinding.tvThreeMonth.setChecked(false);
                mDataBinding.tvSixMonth.setChecked(false);
                mDataBinding.tvYear.setChecked(false);

                // 选择了自定义才可以时间选择
                mDataBinding.tvDateBegin.setOnClickListener(v -> {

                    Toast.makeText(BaseApplication.getInstance(), "要选择自定义才可以选择时间", Toast.LENGTH_SHORT).show();

                    DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                    dateBeginPickerFragment.show(getFragmentManager(), "tvDateBegin");
                    Bundle args = new Bundle();
                    if (!mDataBinding.tvDateEnd.getText().toString().equals(getString(R.string.unlimited))) {
                        args.putString(DatePickerFragment.MAXDATE, mDataBinding.tvDateEnd.getText().toString());
                    }
                    dateBeginPickerFragment.setArguments(args);
                    dateBeginPickerFragment.setComeBack((data, tag) -> {
                        if (tag.equals("tvDateBegin")) {
                            mDataBinding.tvDateBegin.setText(data);
                            mDriverOrderCondition.setStartDate(data);
                        }
                    });
                });

                mDataBinding.tvDateEnd.setOnClickListener(v -> {

                    Toast.makeText(BaseApplication.getInstance(), "要选择自定义才可以选择时间", Toast.LENGTH_SHORT).show();

                    DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                    dateBeginPickerFragment.show(getFragmentManager(), "tvDateEnd");
                    Bundle args = new Bundle();
                    if (!mDataBinding.tvDateBegin.getText().toString().equals(getString(R.string.unlimited))) {
                        args.putString(DatePickerFragment.MINDATE, mDataBinding.tvDateBegin.getText().toString());
                    }
                    dateBeginPickerFragment.setArguments(args);
                    dateBeginPickerFragment.setComeBack((data, tag) -> {
                        if (tag.equals("tvDateEnd")) {
                            mDataBinding.tvDateEnd.setText(data);
                            mDriverOrderCondition.setEndDate(data);
                        }
                    });
                });


            }
        });


        initListenerInit();
    }

    /**
     * 第二个初始化，需要在设置完事件后再赋值
     */
    private void initListenerInit(){
        // 初始化数据
        if (mDriverOrderCondition != null) {
            // (0、自定义，1、近1周，2、近1月，3、近3月，4、近6月 5、近1年)
            switch (mDriverOrderCondition.getDateScopeType()) {
                case "0":
                    mDataBinding.tvWeek.setChecked(false);
                    mDataBinding.tvMonth.setChecked(false);
                    mDataBinding.tvThreeMonth.setChecked(false);
                    mDataBinding.tvSixMonth.setChecked(false);
                    mDataBinding.tvYear.setChecked(false);
                    mDataBinding.tvCustom.setChecked(true);
                    break;
                case "1":
                    mDataBinding.tvWeek.setChecked(true);
                    mDataBinding.tvMonth.setChecked(false);
                    mDataBinding.tvThreeMonth.setChecked(false);
                    mDataBinding.tvSixMonth.setChecked(false);
                    mDataBinding.tvYear.setChecked(false);
                    mDataBinding.tvCustom.setChecked(false);
                    break;
                case "2":
                    mDataBinding.tvWeek.setChecked(false);
                    mDataBinding.tvMonth.setChecked(true);
                    mDataBinding.tvThreeMonth.setChecked(false);
                    mDataBinding.tvSixMonth.setChecked(false);
                    mDataBinding.tvYear.setChecked(false);
                    mDataBinding.tvCustom.setChecked(false);
                    break;
                case "3":
                    mDataBinding.tvWeek.setChecked(false);
                    mDataBinding.tvMonth.setChecked(false);
                    mDataBinding.tvThreeMonth.setChecked(true);
                    mDataBinding.tvSixMonth.setChecked(false);
                    mDataBinding.tvYear.setChecked(false);
                    mDataBinding.tvCustom.setChecked(false);
                    break;
                case "4":
                    mDataBinding.tvWeek.setChecked(false);
                    mDataBinding.tvMonth.setChecked(false);
                    mDataBinding.tvThreeMonth.setChecked(false);
                    mDataBinding.tvSixMonth.setChecked(true);
                    mDataBinding.tvYear.setChecked(false);
                    mDataBinding.tvCustom.setChecked(false);
                    break;
                case "5":
                    mDataBinding.tvWeek.setChecked(false);
                    mDataBinding.tvMonth.setChecked(false);
                    mDataBinding.tvThreeMonth.setChecked(false);
                    mDataBinding.tvSixMonth.setChecked(false);
                    mDataBinding.tvYear.setChecked(true);
                    mDataBinding.tvCustom.setChecked(false);
                    break;
            }
            if (mDriverOrderCondition.getStartDate() != null) {
                mDataBinding.tvDateBegin.setText(mDriverOrderCondition.getStartDate());
            } else {
                mDataBinding.tvDateBegin.setText(getString(R.string.unlimited));
            }
            if (mDriverOrderCondition.getStartDate() != null) {
                mDataBinding.tvDateEnd.setText(mDriverOrderCondition.getEndDate());
            } else {
                mDataBinding.tvDateEnd.setText(getString(R.string.unlimited));
            }
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
