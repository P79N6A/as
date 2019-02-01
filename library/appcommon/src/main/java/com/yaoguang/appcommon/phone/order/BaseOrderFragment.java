package com.yaoguang.appcommon.phone.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.databinding.FragmentOrderBinding;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.appcommon.widget.date.TimePickerFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongjh
 * @Package com.yaoguang.company.order
 * @Description: 订单跟踪 窗体
 * @date 2018/01/05
 */
public abstract class BaseOrderFragment<T,A extends BaseLoadMoreRecyclerAdapter,B> extends BaseFragmentListConditionDataBind<T, SysConditionWrapper,A, FragmentOrderBinding> implements OrderContract.View<T> {

    /**
     * 订单类型,0是货代,1是拖车
     */
    public static final String BUNDLE_BILLTYPE = "mBillType";
    protected OrderContract.Presenter mPresenter;
    SysConditionWrapper mSysConditionWrapper = new SysConditionWrapper();
    protected int mBillType;

    public abstract OrderContract.Presenter newPresenter();

    // 子view的自定义view
    protected abstract void customInitView();

    // 子view的自定义事件
    protected abstract void customInitListener();

    // 子view的自定义获取条件
    protected abstract void customGetCondition(List<SysCondition> sysCondition);

    // 子view的自定义设置条件
    protected abstract void customSetConditionView(SysConditionWrapper mCondition);

    // 子view的重置
    protected abstract void customReset();

    /**
     * 日期
     */
    List<SysCondition> mSysConditionDates;
    List<String> mSysConditionDateStrs;
    //日期的索引
    int mSysDatePosition = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mBillType = args.getInt(BUNDLE_BILLTYPE, 0);
            mPresenter = newPresenter();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void init() {
        mPresenter.subscribe();
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "", R.menu.shipschedule, BaseOrderFragment.this);
        }

        customInitView();
        mLayoutRecyclerviewBinding.loading.setEmptyImage(R.drawable.ic_dd_k);

        // 初始化装卸
        initSweetSheets(mDataBinding.tvValueLoadingOrUnLoading.getId(), mDataBinding.flMain, "请选择装/卸", R.menu.sheet_service_type, (position, menuEntity) -> {
            //返回的分别是三个级别的选中位置
            mDataBinding.tvValueLoadingOrUnLoading.setText(menuEntity.title);
            return true;
        });
    }

    @Override
    public void initListener() {
        customInitListener();
        BaseOrderFragment.this.initRecyclerviewListener();
        mDataBinding.cbTrailer.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked && !mDataBinding.cbForwarder.isChecked()) {
                mDataBinding.cbTrailer.setChecked(true);
                return;
            }
            //如果只选择拖车，则显示
            if (!mDataBinding.cbForwarder.isChecked() && isChecked) {
                mDataBinding.llLoadingOrUnLoading.setVisibility(View.VISIBLE);
                mDataBinding.vLoadingOrUnLoading.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.llLoadingOrUnLoading.setVisibility(View.GONE);
                mDataBinding.vLoadingOrUnLoading.setVisibility(View.GONE);
            }
        });
        mDataBinding.cbForwarder.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked && !mDataBinding.cbTrailer.isChecked())
                mDataBinding.cbForwarder.setChecked(true);
            //如果只选择拖车，则显示
            if (mDataBinding.cbTrailer.isChecked() && !isChecked) {
                mDataBinding.llLoadingOrUnLoading.setVisibility(View.VISIBLE);
                mDataBinding.vLoadingOrUnLoading.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.llLoadingOrUnLoading.setVisibility(View.GONE);
                mDataBinding.vLoadingOrUnLoading.setVisibility(View.GONE);
            }
        });
        //清空
        mDataBinding.btnEmpty.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.cbForwarder.setChecked(true);
                mDataBinding.cbTrailer.setChecked(true);
                mDataBinding.etValueCount.setText("");
                mDataBinding.tvValueUser.setText("");
                mDataBinding.tvValueUser.setTag("");
                mDataBinding.tvValueLoadingOrUnLoading.setText("");
                mDataBinding.etValueOrderId.setText("");
                mDataBinding.etValueOrderNo.setText("");
                mDataBinding.tvStartDateType.setText("");
                mDataBinding.tvEndDateType.setText("");
                mDataBinding.tvDateType.setText("");
                mDataBinding.tvStartBusinessDate.setText("");
                mDataBinding.tvEndBusinessDate.setText("");
                mDataBinding.tvStartLoadDate.setText("");
                mDataBinding.tvEndLoadDate.setText("");
                customReset();
            }
        });

        // 选择柜号
//        mDataBinding.rlCount.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View v) {
//                SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_ORDER_SONS);
//                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_ORDER_SONS));
//            }
//        });

        // 选择运单号
//        mDataBinding.rlOrderId.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View v) {
//                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_ORDER_NUMBER);
//                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_ORDER_NUMBER));
//            }
//        });

        // 选择工作单号
//        mDataBinding.rlOrderNo.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View v) {
//                // 判断物流端或者货主端
//                SearchFragment fragment;
//                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
//                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_ORDER_SNS);
//                } else {
//                    if (TextUtils.isEmpty(mDataBinding.tvValueUser.getText())) {
//                        ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有关联的公司！");
//                        return;
//                    }
//                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_ORDER_SNS, mDataBinding.tvValueUser.getTag().toString());
//                }
//                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_ORDER_SNS));
//            }
//        });

        //货主/公司名称 的选择
        mDataBinding.rlUser.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                setRlUserOnClick();
            }
        });

        // 选择业务员
        mDataBinding.rlEmployeeId.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_EMPLOYEES);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_EMPLOYEES));
            }
        });

        //选择装卸
        mDataBinding.rlLoadingOrUnLoading.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSweetSheets(mDataBinding.tvValueLoadingOrUnLoading.getId());
            }
        });
        //物流端-选择时间类型
        mDataBinding.tvDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSweetSheetDateType();
            }
        });
        mDataBinding.imgDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSweetSheetDateType();
            }
        });
        //物流端-起始日期
        mDataBinding.tvStartDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showLoadingDate();
            }
        });
        mDataBinding.imgStartDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showLoadingDate();
            }
        });
        //物流端-结束日期
        mDataBinding.tvEndDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showEndDate();
            }
        });
        mDataBinding.imgEndDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showEndDate();
            }
        });
        //货主端-业务日期
        mDataBinding.tvStartBusinessDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showBusinessDateStart();
            }
        });
        mDataBinding.imgStartBusinessDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showBusinessDateStart();
            }
        });
        mDataBinding.tvEndBusinessDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showBusinessDateEnd();
            }
        });
        mDataBinding.imgEndBusinessDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showBusinessDateEnd();
            }
        });
        //货主端-装货时间
        mDataBinding.tvStartLoadDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showLoadDateStart();
            }
        });
        mDataBinding.imgStartLoadDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showLoadDateStart();
            }
        });
        mDataBinding.tvEndLoadDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showLoadDateEnd();
            }
        });
        mDataBinding.imgEndLoadDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showLoadDateEnd();
            }
        });

        //清空
        mDataBinding.imgLoadingOrUnLoading.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValueLoadingOrUnLoading.setText("");
            }
        });
        mDataBinding.imgUser.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValueUser.setText("");
                mDataBinding.tvValueUser.setTag("");
            }
        });
        mDataBinding.imgCont.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.etValueCount.setText("");
            }
        });
        mDataBinding.imgOrderId.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.etValueOrderId.setText("");
            }
        });
        mDataBinding.imgOrderNo.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.etValueOrderNo.setText("");
            }
        });
        mDataBinding.imgLoadDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvStartLoadDate.setText("");
                mDataBinding.tvEndLoadDate.setText("");
            }
        });
        mDataBinding.imgBusinessDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvStartBusinessDate.setText("");
                mDataBinding.tvEndBusinessDate.setText("");
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER:
                    mDataBinding.tvValueUser.setText(data.getString("name"));
                    mDataBinding.tvValueUser.setTag(data.getString("id"));
                    break;
                case PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY:
                    mDataBinding.tvValueUser.setText(data.getString("fullName"));
                    mDataBinding.tvValueUser.setTag(data.getString("id"));
                    break;
                case PublicSearchInteractorImpl.TYPE_ORDER_NUMBER:
                    mDataBinding.etValueOrderId.setText(data.getString("fullName"));
                    break;
                case PublicSearchInteractorImpl.TYPE_ORDER_SONS:
                    mDataBinding.etValueCount.setText(data.getString("fullName"));
                    break;
                case PublicSearchInteractorImpl.TYPE_ORDER_SNS:
                    mDataBinding.etValueOrderNo.setText(data.getString("fullName"));
                    break;
                case PublicSearchInteractorImpl.TYPE_EMPLOYEES:
                    mDataBinding.tvValueEmployeeId.setText(data.getString("fullName"));
                    break;
            }
        }
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public SysConditionWrapper getCondition(boolean isRegain) {
        if (isRegain) {
            List<SysCondition> sysConditions = new ArrayList<>();
            //装拆箱
            SysCondition tvValueLoadingOrUnLoading = new SysCondition();
            tvValueLoadingOrUnLoading.setConditionType("ServiceType");
            int valueLoadingOrUnLoading = -1;
            if (mDataBinding.tvValueLoadingOrUnLoading.getText().toString().equals(getResources().getString(R.string.loading_goods))) {
                valueLoadingOrUnLoading = 0;
            } else if (mDataBinding.tvValueLoadingOrUnLoading.getText().toString().equals(getResources().getString(R.string.unloading_goods))) {
                valueLoadingOrUnLoading = 1;
            }
            tvValueLoadingOrUnLoading.setInputValue(ObjectUtils.parseString(valueLoadingOrUnLoading));
            tvValueLoadingOrUnLoading.setTableFieldName("OTHERSERVICE");
            sysConditions.add(tvValueLoadingOrUnLoading);
            //货主
            SysCondition tvValueUser = new SysCondition();
            tvValueUser.setConditionType("string");
            tvValueUser.setInputValue(mDataBinding.tvValueUser.getText().toString());
            tvValueUser.setTableFieldName("SHIPPER");
            sysConditions.add(tvValueUser);
            //柜号
            SysCondition tvValueCount = new SysCondition();
            tvValueCount.setConditionType("string");
            tvValueCount.setInputValue(mDataBinding.etValueCount.getText().toString());
            tvValueCount.setTableFieldName("CONT_NO");
            sysConditions.add(tvValueCount);
            //运单号
            SysCondition tvValueOrderId = new SysCondition();
            tvValueOrderId.setConditionType("string");
            tvValueOrderId.setInputValue(mDataBinding.etValueOrderId.getText().toString());
            tvValueOrderId.setTableFieldName("M_BL_NO");
            sysConditions.add(tvValueOrderId);
            //工作单号
            SysCondition tvValueOrderNo = new SysCondition();
            tvValueOrderNo.setConditionType("string");
            tvValueOrderNo.setInputValue(mDataBinding.etValueOrderNo.getText().toString());
            tvValueOrderNo.setTableFieldName("LADING_ID");
            sysConditions.add(tvValueOrderNo);
            //业务日期
            SysCondition tvStartLoadDate = new SysCondition();
            tvStartLoadDate.setConditionType("date");
            if (!TextUtils.isEmpty(mDataBinding.tvStartLoadDate.getText().toString())){
                tvStartLoadDate.setInputValue(mDataBinding.tvStartLoadDate.getText().toString() + " 00:00:00");
            }else{
                tvStartLoadDate.setInputValue(mDataBinding.tvStartLoadDate.getText().toString());
            }
            if (!TextUtils.isEmpty(mDataBinding.tvEndLoadDate.getText().toString())){
                tvStartLoadDate.setInputValue2(mDataBinding.tvEndLoadDate.getText().toString() + " 23:59:59");
            }else{
                tvStartLoadDate.setInputValue2(mDataBinding.tvEndLoadDate.getText().toString());
            }
            tvStartLoadDate.setTableFieldName("LOAD_DATE");
            sysConditions.add(tvStartLoadDate);
            //装货时间
            SysCondition tvStartBusinessDate = new SysCondition();
            tvStartBusinessDate.setConditionType("date");
            if (!TextUtils.isEmpty(mDataBinding.tvStartBusinessDate.getText().toString())){
                tvStartBusinessDate.setInputValue(mDataBinding.tvStartBusinessDate.getText().toString() + " 00:00:00");
            }else{
                tvStartBusinessDate.setInputValue(mDataBinding.tvStartBusinessDate.getText().toString());
            }
            if (!TextUtils.isEmpty(mDataBinding.tvEndBusinessDate.getText().toString())){
                tvStartBusinessDate.setInputValue2(mDataBinding.tvEndBusinessDate.getText().toString() + " 23:59:59");
            }else{
                tvStartBusinessDate.setInputValue2(mDataBinding.tvEndBusinessDate.getText().toString());
            }
            tvStartBusinessDate.setTableFieldName("MODIFY_DATE");
            sysConditions.add(tvStartBusinessDate);
            // 业务员
            SysCondition tvEmployeeID = new SysCondition();
            tvEmployeeID.setConditionType("string");
            tvEmployeeID.setInputValue(mDataBinding.tvValueEmployeeId.getText().toString());
            tvEmployeeID.setTableFieldName("EMPLOYEE_ID");
            sysConditions.add(tvEmployeeID);

            //时间类型
            if (mSysDatePosition != -1) {
                if (!TextUtils.isEmpty(mDataBinding.tvStartDateType.getText().toString())){
                    mSysConditionDates.get(mSysDatePosition).setInputValue(mDataBinding.tvStartDateType.getText().toString() + " 00:00:00");
                }else{
                    mSysConditionDates.get(mSysDatePosition).setInputValue(mDataBinding.tvStartDateType.getText().toString());
                }
                if (!TextUtils.isEmpty(mDataBinding.tvEndDateType.getText().toString())){
                    mSysConditionDates.get(mSysDatePosition).setInputValue2(mDataBinding.tvEndDateType.getText().toString() + " 23:59:59");
                }else{
                    mSysConditionDates.get(mSysDatePosition).setInputValue2(mDataBinding.tvEndDateType.getText().toString());
                }
                sysConditions.add(mSysConditionDates.get(mSysDatePosition));
            }

            customGetCondition(sysConditions);

            mSysConditionWrapper.setsysConditions(sysConditions);
        }
        return mSysConditionWrapper;
    }

    @Override
    public void setConditionView(SysConditionWrapper condition) {
        for (SysCondition sysCondition : mSysConditionWrapper.getsysConditions()) {
            if (sysCondition.getTableFieldName() == null)
                continue;
            switch (sysCondition.getTableFieldName()) {
                case "OTHERSERVICE":
                    if (sysCondition.getInputValue().equals("-1")) {
                        mDataBinding.tvValueLoadingOrUnLoading.setText("");
                    } else {
                        if (sysCondition.getInputValue().equals("0")) {
                            mDataBinding.tvValueLoadingOrUnLoading.setText(getResources().getString(R.string.loading_goods));
                        } else {
                            mDataBinding.tvValueLoadingOrUnLoading.setText(getResources().getString(R.string.unloading_goods));
                        }
                    }
                    break;
                case "SHIPPER":
                    mDataBinding.tvValueUser.setText(sysCondition.getInputValue());
                    break;
                case "CONT_NO":
                    mDataBinding.etValueCount.setText(sysCondition.getInputValue());
                    break;
                case "M_BL_NO":
                    mDataBinding.etValueOrderId.setText(sysCondition.getInputValue());
                    break;
                case "LADING_ID":
                    mDataBinding.etValueOrderNo.setText(sysCondition.getInputValue());
                    break;
                case "LOAD_DATE":
                    mDataBinding.tvStartLoadDate.setText(sysCondition.getInputValue().replace(" 00:00:00", ""));
                    mDataBinding.tvEndLoadDate.setText(sysCondition.getInputValue2().replace(" 23:59:59", ""));
                    break;
                case "MODIFY_DATE":
                    mDataBinding.tvStartBusinessDate.setText(sysCondition.getInputValue().replace(" 00:00:00", ""));
                    mDataBinding.tvEndBusinessDate.setText(sysCondition.getInputValue2().replace(" 23:59:59", ""));
                    break;
            }
        }
        if (mSysDatePosition != -1) {
            mDataBinding.tvStartDateType.setText(mSysConditionDates.get(mSysDatePosition).getInputValue().replace(" 00:00:00", ""));
            mDataBinding.tvEndDateType.setText(mSysConditionDates.get(mSysDatePosition).getInputValue2().replace(" 23:59:59", ""));
        }
        customSetConditionView(mSysConditionWrapper);
    }

    @Override
    public void setSysConditionDates(List<SysCondition> result) {
        mSysConditionDateStrs = new ArrayList<>();
        mSysConditionDates = result;
        int size = result.size();
        for (int i = 0; i < size; i++) {
            mSysConditionDateStrs.add(result.get(i).getDisplayName());
        }
    }

    @Override
    public void showSweetSheetDateType() {
        if (mSysConditionDateStrs == null || mSysConditionDateStrs.size() <= 0) {
            Toast.makeText(BaseApplication.getInstance(), "时间类型未加载,请重新打开此窗体", Toast.LENGTH_SHORT).show();
            return;
        }

        // 初始化时间类型
        initSweetSheets(mDataBinding.tvDateType.getId(), mDataBinding.flMain, "请选择时间类型", mSysConditionDateStrs, (position, menuEntity) -> {
            //返回的分别是三个级别的选中位置
            if (position == 0) {
                mDataBinding.tvDateType.setText("");
                mSysDatePosition = -1;
                mDataBinding.tvStartDateType.setText("");
                mDataBinding.tvEndDateType.setText("");
            } else {
                mDataBinding.tvDateType.setText(mSysConditionDates.get(position).getDisplayName());
                mSysDatePosition = position;
            }
            return true;
        });
        showSweetSheets(mDataBinding.tvDateType.getId());
    }

    @Override
    public void showLoadingDate() {
        DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
        dateBeginPickerFragment.show(getFragmentManager(), "LoadingDate");
        Bundle args = new Bundle();
        args.putString(DatePickerFragment.MAXDATE, mDataBinding.tvEndDateType.getText().toString());
        dateBeginPickerFragment.setArguments(args);
        dateBeginPickerFragment.setComeBack((data, tag) -> {
            if (tag.equals("LoadingDate")) {
                mDataBinding.tvStartDateType.setText(data);
            }
        });
    }

    @Override
    public void showEndDate() {
        DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
        dateBeginPickerFragment.show(getFragmentManager(), "EndDateType");
        Bundle args = new Bundle();
        args.putString(DatePickerFragment.MINDATE, mDataBinding.tvStartDateType.getText().toString());
        dateBeginPickerFragment.setArguments(args);
        dateBeginPickerFragment.setComeBack((data, tag) -> {
            if (tag.equals("EndDateType")) {
                mDataBinding.tvEndDateType.setText(data);
            }
        });
    }

    @Override
    public void showBusinessDateStart() {
        DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
        dateBeginPickerFragment.show(getFragmentManager(), "BusinessDateStart");
        Bundle args = new Bundle();
        args.putString(DatePickerFragment.MAXDATE, mDataBinding.tvEndBusinessDate.getText().toString());
        dateBeginPickerFragment.setArguments(args);
        dateBeginPickerFragment.setComeBack((data, tag) -> {
            if (tag.equals("BusinessDateStart")) {
                mDataBinding.tvStartBusinessDate.setText(data);
            }
        });
    }

    @Override
    public void showBusinessDateEnd() {
        DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
        dateBeginPickerFragment.show(getFragmentManager(), "BusinessDateEnd");
        Bundle args = new Bundle();
        args.putString(DatePickerFragment.MINDATE, mDataBinding.tvStartBusinessDate.getText().toString());
        dateBeginPickerFragment.setArguments(args);
        dateBeginPickerFragment.setComeBack((data, tag) -> {
            if (tag.equals("BusinessDateEnd")) {
                mDataBinding.tvEndBusinessDate.setText(data);
            }
        });
    }

    @Override
    public void showLoadDateStart() {
        DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
        dateBeginPickerFragment.show(getFragmentManager(), "LoadDateStart");
        Bundle args = new Bundle();
        args.putString(DatePickerFragment.MAXDATE, mDataBinding.tvEndLoadDate.getText().toString());
        args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
        dateBeginPickerFragment.setArguments(args);
        dateBeginPickerFragment.setComeBack((data, tag) -> {
            if (tag.equals("LoadDateStart" + TimePickerFragment.TAGTIME)) {
                mDataBinding.tvStartLoadDate.setText(data);
            }
        });
    }

    @Override
    public void showLoadDateEnd() {
        DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
        dateBeginPickerFragment.show(getFragmentManager(), "LoadDateEnd");
        Bundle args = new Bundle();
        args.putString(DatePickerFragment.MAXDATE, mDataBinding.tvStartLoadDate.getText().toString());
        args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
        dateBeginPickerFragment.setArguments(args);
        dateBeginPickerFragment.setComeBack((data, tag) -> {
            if (tag.equals("LoadDateEnd" + TimePickerFragment.TAGTIME)) {
                mDataBinding.tvEndLoadDate.setText(data);
            }
        });
    }

}
