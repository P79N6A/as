package com.yaoguang.appcommon.phone.shipschedule;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.databinding.FragmentShipScheduleBinding;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.appcommon.phone.shipschedule.adapter.ShipScheduleAdapter;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;
import com.yaoguang.map.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongjh
 * @Package com.yaoguang.company.shipschedule
 * @Description: 船期查询 窗体
 * @date 2018/01/15
 */
public abstract class BaseShipScheduleFragment<B extends ViewDataBinding> extends BaseFragmentListConditionDataBind<InfoVoyageTable, InfoVoyageTableCondition, ShipScheduleAdapter, FragmentShipScheduleBinding> implements ShipScheduleContract.View {

    protected ShipScheduleContract.Presenter mPresenter = new ShipSchedulePresenter(this);
    InfoVoyageTableCondition mInfoVoyageTableCondition = new InfoVoyageTableCondition();

    List<String> conts = new ArrayList<>(); // 柜型数据
    List<String> mSysConditionDateStrs = new ArrayList<>(); //时间类型
    int mSysDatePosition = -1;  //日期的索引

    public abstract void itemOnClick(View itemView, Object item); // 抽象点击事件

    protected abstract void initListenerCustom(); // 抽象初始化事件

    protected abstract void initViewCustom(); // 抽象初始化

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mSysConditionDateStrs.add("未选择");
        mSysConditionDateStrs.add("预开时间");
        mSysConditionDateStrs.add("发布时间");
        mSysConditionDateStrs.add("预抵时间");

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ship_schedule;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mPresenter.subscribe();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "船期查询", R.menu.shipschedule, BaseShipScheduleFragment.this);
        }

        // 初始化一堆底部选择框

        initSweetSheets(mDataBinding.tvDateType.getId(), mDataBinding.flMain, "请选择时间类型", mSysConditionDateStrs, (position, menuEntity) -> {
            if (position == 0) {
                mDataBinding.tvDateType.setText("");
                mSysDatePosition = -1;
                mDataBinding.tvStartDateType.setText("");
                mDataBinding.tvEndDateType.setText("");
            } else {
                mDataBinding.tvDateType.setText(mSysConditionDateStrs.get(position));
                mSysDatePosition = position;
            }
            return true;
        });


        initViewCustom();
    }

    @Override
    public void initListener() {
        BaseShipScheduleFragment.this.initRecyclerviewListener();
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> itemOnClick(itemView, item));

        mDataBinding.cvTop.setOnTouchListener((view, motionEvent) -> true);
        mDataBinding.llTop.setOnTouchListener((view, motionEvent) -> {
            mDataBinding.llTop.setVisibility(View.GONE);
            return true;
        });

        mDataBinding.btnEmpty.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValueContId.setText("");
                mDataBinding.tvValueShipCompany.setText("");
                mDataBinding.tvValuePlaceDelivery.setText("");
                mDataBinding.tvValuePlaceLoading.setText("");
                mDataBinding.tvStartDateType.setText("");
                mDataBinding.tvEndDateType.setText("");
                mDataBinding.tvDateType.setText("");
            }
        });
        mDataBinding.btnComit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                InputMethodUtil.hideKeyboard(getActivity());
                mDataBinding.llTop.setVisibility(View.GONE);
                mLayoutRecyclerviewBinding.refreshLayout.autoRefresh();
            }
        });

        mDataBinding.rlPlaceLoading.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 判断物流端或者货主端
                SearchFragment fragment;
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT);
                } else {
                    if (TextUtils.isEmpty(mDataBinding.tvValueUser.getText())) {
                        ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有选择货代公司");
                        return;
                    }
                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT, mDataBinding.tvValueUser.getTag().toString());
                }
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT));
            }
        });
        mDataBinding.rlPlaceDelivery.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 判断物流端或者货主端
                SearchFragment fragment;
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION);
                } else {
                    if (TextUtils.isEmpty(mDataBinding.tvValueUser.getText())) {
                        ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有选择货代公司");
                        return;
                    }
                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION, mDataBinding.tvValueUser.getTag().toString());
                }
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION));
            }
        });
        mDataBinding.rlShipCompany.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 判断物流端或者货主端
                SearchFragment fragment;
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY);
                } else {
                    if (TextUtils.isEmpty(mDataBinding.tvValueUser.getText())) {
                        ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有选择货代公司");
                        return;
                    }
                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY, mDataBinding.tvValueUser.getTag().toString());
                }
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY));
            }
        });
        mDataBinding.rlContId.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //sheet
                showSweetSheets(mDataBinding.tvValueContId.getId());
            }
        });
        //选择时间类型
        mDataBinding.tvDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showDataType();
            }
        });
        mDataBinding.imgDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showDataType();
            }
        });
        //起始日期
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
        //结束日期
        mDataBinding.tvEndDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showEndDateType();
            }
        });
        mDataBinding.imgEndDateType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showEndDateType();
            }
        });

        //清除
        mDataBinding.imgPlaceLoading.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValuePlaceLoading.setText("");
            }
        });
        mDataBinding.imgPlaceDelivery.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValuePlaceDelivery.setText("");
            }
        });
        mDataBinding.imgShipCompany.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValueShipCompany.setText("");
            }
        });
        mDataBinding.imgContId.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValueContId.setText("");
            }
        });
        mDataBinding.imgPlaceLoading.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValuePlaceLoading.setText("");
            }
        });

        initListenerCustom();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT:
                    mDataBinding.tvValuePlaceLoading.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION:
                    mDataBinding.tvValuePlaceDelivery.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_SHIP_COMPANY:
                    mDataBinding.tvValueShipCompany.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY:
                    mDataBinding.tvValueUser.setText(data.getString("fullName"));
                    mDataBinding.tvValueUser.setTag(data.getString("id"));
                    break;
            }
        }
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new ShipScheduleAdapter(BaseApplication.getAppType());
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public InfoVoyageTableCondition getCondition(boolean isRegain) {
        if (isRegain) {
            // 起运港
            mInfoVoyageTableCondition.setPlaceLoading(mDataBinding.tvValuePlaceLoading.getText().toString());
            // 目的港
            mInfoVoyageTableCondition.setPlaceDelivery(mDataBinding.tvValuePlaceDelivery.getText().toString());
            // 判断类型
            if (mDataBinding.tvDateType.getText().toString().equals("预开时间")) {
                if (!TextUtils.isEmpty(mDataBinding.tvStartDateType.getText().toString())){
                    mInfoVoyageTableCondition.setStartOnboardDate(mDataBinding.tvStartDateType.getText().toString() + " 00:00:00");
                }else{
                    mInfoVoyageTableCondition.setStartOnboardDate(mDataBinding.tvStartDateType.getText().toString());
                }
                if (!TextUtils.isEmpty(mDataBinding.tvEndDateType.getText().toString())){
                    mInfoVoyageTableCondition.setEndOnboardDate(mDataBinding.tvEndDateType.getText().toString() + " 23:59:59");
                }else{
                    mInfoVoyageTableCondition.setEndOnboardDate(mDataBinding.tvEndDateType.getText().toString());
                }
            } else if (mDataBinding.tvDateType.getText().toString().equals("发布时间")) {
                if (!TextUtils.isEmpty(mDataBinding.tvStartDateType.getText().toString())){
                    mInfoVoyageTableCondition.setStartReleaseDate(mDataBinding.tvStartDateType.getText().toString() + " 00:00:00");
                }else{
                    mInfoVoyageTableCondition.setStartReleaseDate(mDataBinding.tvStartDateType.getText().toString());
                }
                if (!TextUtils.isEmpty(mDataBinding.tvEndDateType.getText().toString())){
                    mInfoVoyageTableCondition.setEndReleaseDate(mDataBinding.tvEndDateType.getText().toString() + " 23:59:59");
                }else{
                    mInfoVoyageTableCondition.setEndReleaseDate(mDataBinding.tvEndDateType.getText().toString());
                }
            } else if (mDataBinding.tvDateType.getText().toString().equals("预抵时间")) {
                if (!TextUtils.isEmpty(mDataBinding.tvStartDateType.getText().toString())){
                    mInfoVoyageTableCondition.setStartArrivalDate(mDataBinding.tvStartDateType.getText().toString() + " 00:00:00");
                }else{
                    mInfoVoyageTableCondition.setStartArrivalDate(mDataBinding.tvStartDateType.getText().toString());
                }
                if (!TextUtils.isEmpty(mDataBinding.tvEndDateType.getText().toString())){
                    mInfoVoyageTableCondition.setEndArrivalDate(mDataBinding.tvEndDateType.getText().toString() + " 23:59:59");
                }else{
                    mInfoVoyageTableCondition.setEndArrivalDate(mDataBinding.tvEndDateType.getText().toString());
                }
            }
            // 柜型
            mInfoVoyageTableCondition.setContId(mDataBinding.tvValueContId.getText().toString());
            // 货代公司
            mInfoVoyageTableCondition.setClientId(ObjectUtils.parseString(mDataBinding.tvValueUser.getTag()));
            mInfoVoyageTableCondition.setStrClient(mDataBinding.tvValueUser.getText().toString());
            // 船公司
            mInfoVoyageTableCondition.setShipCompany(mDataBinding.tvValueShipCompany.getText().toString());
        }
        return mInfoVoyageTableCondition;
    }

    @Override
    public void setConditionView(InfoVoyageTableCondition condition) {
        if (mInfoVoyageTableCondition != null) {
            // 起运港
            mDataBinding.tvValuePlaceLoading.setText(mInfoVoyageTableCondition.getPlaceLoading());
            // 目的港
            mDataBinding.tvValuePlaceDelivery.setText(mInfoVoyageTableCondition.getPlaceDelivery());
            //判断类型
            if (mDataBinding.tvDateType.getText().toString().equals("预开时间")) {
                mDataBinding.tvStartDateType.setText(mInfoVoyageTableCondition.getStartOnboardDate().replace(" 00:00:00", ""));
                mDataBinding.tvEndDateType.setText(mInfoVoyageTableCondition.getEndOnboardDate().replace(" 23:59:59", ""));
            } else if (mDataBinding.tvDateType.getText().toString().equals("发布时间")) {
                mDataBinding.tvStartDateType.setText(mInfoVoyageTableCondition.getStartReleaseDate().replace(" 00:00:00", ""));
                mDataBinding.tvEndDateType.setText(mInfoVoyageTableCondition.getEndReleaseDate().replace(" 23:59:59", ""));
            } else if (mDataBinding.tvDateType.getText().toString().equals("预抵时间")) {
                mDataBinding.tvStartDateType.setText(mInfoVoyageTableCondition.getStartArrivalDate().replace(" 00:00:00", ""));
                mDataBinding.tvEndDateType.setText(mInfoVoyageTableCondition.getEndArrivalDate().replace(" 23:59:59", ""));
            }
            // 柜型
            mDataBinding.tvValueContId.setText(mInfoVoyageTableCondition.getContId());
            // 货代公司
            mDataBinding.tvValueUser.setTag(mInfoVoyageTableCondition.getClientId());
            mDataBinding.tvValueUser.setText(mInfoVoyageTableCondition.getStrClient());
            //船公司
            mDataBinding.tvValueShipCompany.setText(mInfoVoyageTableCondition.getShipCompany());
        }
    }

    @Override
    public void setSonos(BaseResponse<List<InfoContType>> infoContType) {
        if (infoContType != null && infoContType.getResult() != null) {
            for (InfoContType item : infoContType.getResult()) {
                conts.add(item.getContChina());
            }
            // 初始化下拉框的数据
            initSweetSheets(mDataBinding.tvValueContId.getId(), mDataBinding.flMain, "柜型", conts, (position, menuEntity) -> {
                mDataBinding.tvValueContId.setText(menuEntity.title);
                return true;
            });
        }
    }

    @Override
    public void setContactCompany(AppPublicInfoWrapper appPublicInfoWrapper) {
        mDataBinding.tvValueUser.setText(appPublicInfoWrapper.getFullName());
        mDataBinding.tvValueUser.setTag(appPublicInfoWrapper.getId());
        mLayoutRecyclerviewBinding.refreshLayout.autoRefresh();
    }

    @Override
    public void showDataType() {
        if (mSysConditionDateStrs == null || mSysConditionDateStrs.size() <= 0) {
            Toast.makeText(BaseApplication.getInstance(), "时间类型未加载,请重新打开此窗体", Toast.LENGTH_SHORT).show();
            return;
        }

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
    public void showEndDateType() {
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

}
