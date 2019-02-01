package com.yaoguang.company.businessorder2.common.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.list.adapter.ConditionConditionAdapter;
import com.yaoguang.company.businessorder2.common.list.adapter.ConditionDateAdapter;
import com.yaoguang.company.businessorder2.common.list.adapter.ConditionOfficeAdapter;
import com.yaoguang.company.businessorder2.common.list.adapter.ConditionStatusAdapter;
import com.yaoguang.company.businessorder2.common.list.adapter.ConditionStringAdapter;
import com.yaoguang.company.businessorder2.common.temp.TempListFragment;
import com.yaoguang.company.databinding.FragmentBusinessOrderList2Binding;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.company.AppBusinessOrderListCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;

import java.util.ArrayList;

/**
 * 公共的列表界面
 * Created by zhongjh on 2018/11/14.
 */
public abstract class BaseBusinessOrderListFragment<T, A extends BaseLoadMoreRecyclerAdapter> extends BaseFragmentListConditionDataBind<T, SysConditionWrapper, A, FragmentBusinessOrderList2Binding>
        implements BaseBusinessOrderListContact.View<T> {

    protected String mBillType;
    private SysConditionWrapper mSysConditionWrapper = new SysConditionWrapper(); // 条件

    ConditionOfficeAdapter mConditionOfficeAdapter;
    ConditionStatusAdapter mConditionStatusAdapter;
    ConditionDateAdapter mConditionDateAdapter;
    ConditionStringAdapter mConditionStringAdapter;
    ConditionConditionAdapter mConditionConditionAdapter;

    ViewHolder mHeader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mBillType = args.getString("billType");
        }
        super.onCreate(savedInstanceState);
    }

    public BaseBusinessOrderListFragment(String billType) {
        Bundle bundle = new Bundle();
        bundle.putString("billType", billType);
        this.setArguments(bundle);
    }

    @Override
    public SysConditionWrapper getCondition(boolean isRegain) {
        mSysConditionWrapper.setsysConditions(getSysConditions());
        return mSysConditionWrapper;
    }

    @Override
    public void setConditionView(SysConditionWrapper condition) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order_list2;
    }

    @Override
    public void initListener() {
        mDataBinding.llConditionTop.setOnClickListener(v -> mDataBinding.llOfficeCondition.setVisibility(View.GONE));

        // 机构
        mDataBinding.llScreen.setOnClickListener(v -> {
            InputMethodUtil.hideKeyboard(getActivity());
            if (mDataBinding.llOfficeCondition.getVisibility() == View.VISIBLE) {
                mDataBinding.llOfficeCondition.setVisibility(View.GONE);
                mDataBinding.vTopLine2.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.llOfficeCondition.setVisibility(View.VISIBLE);
                mDataBinding.vTopLine2.setVisibility(View.GONE);
                // 其他隐藏
                mDataBinding.llDateCondition.setVisibility(View.GONE);
                mDataBinding.llStatusCondition.setVisibility(View.GONE);
                mDataBinding.llStringCondition.setVisibility(View.GONE);
            }

        });

        // 状态
        mDataBinding.llStatus.setOnClickListener(v -> {
            InputMethodUtil.hideKeyboard(getActivity());
            if (mDataBinding.llStatusCondition.getVisibility() == View.VISIBLE) {
                mDataBinding.llStatusCondition.setVisibility(View.GONE);
                mDataBinding.vTopLine2.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.llStatusCondition.setVisibility(View.VISIBLE);
                mDataBinding.vTopLine2.setVisibility(View.GONE);
                // 其他隐藏
                mDataBinding.llDateCondition.setVisibility(View.GONE);
                mDataBinding.llOfficeCondition.setVisibility(View.GONE);
                mDataBinding.llStringCondition.setVisibility(View.GONE);
            }
        });

        mDataBinding.rlStatusCabinetType.setOnClickListener(v -> {
            // 选择柜子
            showSweetSheets(mDataBinding.rlStatusCabinetType.getId());
        });

        // 时间范围
        mDataBinding.llTimeFrame.setOnClickListener(v -> {
            InputMethodUtil.hideKeyboard(getActivity());
            if (mDataBinding.llDateCondition.getVisibility() == View.VISIBLE) {
                mDataBinding.llDateCondition.setVisibility(View.GONE);
                mDataBinding.vTopLine2.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.llDateCondition.setVisibility(View.VISIBLE);
                mDataBinding.vTopLine2.setVisibility(View.GONE);
                // 其他隐藏
                mDataBinding.llStatusCondition.setVisibility(View.GONE);
                mDataBinding.llOfficeCondition.setVisibility(View.GONE);
                mDataBinding.llStringCondition.setVisibility(View.GONE);
            }
        });

        // 关键字
        mDataBinding.llString.setOnClickListener(v -> {
            InputMethodUtil.hideKeyboard(getActivity());
            if (mDataBinding.llStringCondition.getVisibility() == View.VISIBLE) {
                mDataBinding.llStringCondition.setVisibility(View.GONE);
                mDataBinding.vTopLine2.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.llStringCondition.setVisibility(View.VISIBLE);
                mDataBinding.vTopLine2.setVisibility(View.GONE);
                // 其他隐藏
                mDataBinding.llStatusCondition.setVisibility(View.GONE);
                mDataBinding.llOfficeCondition.setVisibility(View.GONE);
                mDataBinding.llDateCondition.setVisibility(View.GONE);
            }
        });

        // 点击确定的时候
        mDataBinding.btnOfficeConditionComit.setOnClickListener(v -> {
            refreshDataAnimation();
            // 隐藏
            mDataBinding.llOfficeCondition.setVisibility(View.GONE);
            mDataBinding.vTopLine2.setVisibility(View.VISIBLE);
        });
        mDataBinding.btnStatusConditionComit.setOnClickListener(v -> {
            refreshDataAnimation();
            // 隐藏
            mDataBinding.llStatusCondition.setVisibility(View.GONE);
            mDataBinding.vTopLine2.setVisibility(View.VISIBLE);
        });
        mDataBinding.btnDateConditionComit.setOnClickListener(v -> {
            refreshDataAnimation();
            // 隐藏
            mDataBinding.llDateCondition.setVisibility(View.GONE);
            mDataBinding.vTopLine2.setVisibility(View.VISIBLE);
        });
        mDataBinding.btnStringConditionComit.setOnClickListener(v -> {
            refreshDataAnimation();
            // 隐藏
            mDataBinding.llStringCondition.setVisibility(View.GONE);
            mDataBinding.vTopLine2.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            start(TempListFragment.newInstance(mBillType));
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void init() {
        mHeader = new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.layout_business_order_list_condition, null, false));
        mBaseLoadMoreRecyclerAdapter.addHeaderView(mHeader.rootView);

        initSweetSheets(mDataBinding.rlStatusCabinetType.getId(), mDataBinding.flMain, "柜型", R.menu.sheet_business_cabinet_type, (position, menuEntity) -> {
            // 如果是
            if (position == 0) {
                mDataBinding.tvStatusCabinetTypeValue.setText(menuEntity.title);
                mDataBinding.llStatusCabinetType2.setVisibility(View.GONE);
            } else if (position == 1) {
                // 20'单柜
                mDataBinding.tvStatusCabinetTypeValue.setText(menuEntity.title);
                mDataBinding.llStatusCabinetType2.setVisibility(View.GONE);
            } else if (position == 2) {
                // 20'组柜
                mDataBinding.tvStatusCabinetTypeValue.setText(menuEntity.title);
                mDataBinding.llStatusCabinetType2.setVisibility(View.GONE);
            } else if (position == 3) {
                // 多柜筛选
                mDataBinding.tvStatusCabinetTypeValue.setText(menuEntity.title);
                // 显示下面的
                mDataBinding.llStatusCabinetType2.setVisibility(View.VISIBLE);
            }
            return true;
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void initCondition(AppBusinessOrderListCondition result) {
        // 机构
        // 第一个添加不限
        SysCondition sysCondition = new SysCondition();
        sysCondition.setDisplayName("不限");
        result.getOffice().set(0, sysCondition);
        mConditionOfficeAdapter = new ConditionOfficeAdapter();
        mConditionOfficeAdapter.setList(result.getOffice());
        RecyclerViewUtils.initPage(mDataBinding.rlOfficeCondition, mConditionOfficeAdapter, null, getContext(), true);

        // 状态
        mConditionStatusAdapter = new ConditionStatusAdapter(mDataBinding.flMain);
        mConditionStatusAdapter.setList(result.getStatus());
        RecyclerViewUtils.initPage(mDataBinding.rlStatusCondition, mConditionStatusAdapter, null, getContext(), true);

        // 时间范围
        mConditionDateAdapter = new ConditionDateAdapter(getFragmentManager());
        mConditionDateAdapter.setList(result.getDate());
        RecyclerViewUtils.initPage(mDataBinding.rlDateCondition, mConditionDateAdapter, null, getContext(), true);

        // 关键字搜索
        mConditionStringAdapter = new ConditionStringAdapter();
        mConditionStringAdapter.setList(result.getString());
        RecyclerViewUtils.initPage(mDataBinding.rlStringCondition, mConditionStringAdapter, null, getContext(), true);

        // 全局条件
        mConditionConditionAdapter = new ConditionConditionAdapter();
        mConditionConditionAdapter.setOnItemClickListener((itemView, item, position) -> {
            // 删除,即是修改筛选框的数据源
            SysCondition myItem = (SysCondition) item;
            switch (myItem.getAppCustomType()) {
                case "office":
                    mConditionOfficeAdapter.getItem(myItem.getAppPosition()).setCheck(false);
                    mConditionOfficeAdapter.notifyDataSetChanged();
                    break;
                case "status":
                    if (myItem.getAppPosition() == -1){
                        // 这个是写死的
                        mDataBinding.tvStatusCabinetTypeValue.setText("不限");
                        mDataBinding.llStatusCabinetType2.setVisibility(View.GONE);
                    }else {
                        mConditionStatusAdapter.getItem(myItem.getAppPosition()).setInputValue("");
                        mConditionStatusAdapter.getItem(myItem.getAppPosition()).setInputValue2("");
                        mConditionStatusAdapter.notifyDataSetChanged();
                    }
                    break;
                case "date":
                    mConditionDateAdapter.getItem(myItem.getAppPosition()).setInputValue("");
                    mConditionDateAdapter.getItem(myItem.getAppPosition()).setInputValue2("");
                    mConditionDateAdapter.notifyDataSetChanged();
                    break;
                case "string":
                    mConditionStringAdapter.getItem(myItem.getAppPosition()).setInputValue("");
                    mConditionStringAdapter.notifyDataSetChanged();
                    break;
            }
            // 刷新
            refreshDataAnimation();
        });
        RecyclerViewUtils.initPageMargin(mHeader.rlCondition, mConditionConditionAdapter, null, getContext(), true, R.dimen.margin11);
    }

    /**
     * 获取目前搜索的数据源
     *
     * @return 目前搜索的数据源
     */
    public ArrayList<SysCondition> getSysConditions() {
        // 查询条件的conditions
        ArrayList<SysCondition> sysConditions = new ArrayList<>();
        // 显示的conditions
        ArrayList<SysCondition> sysConditionShows = new ArrayList<>();
        if (mConditionOfficeAdapter == null || mConditionOfficeAdapter.getList() == null && mConditionOfficeAdapter.getList().get(0) == null)
            return sysConditions;
        // 检查机构
        if (mConditionOfficeAdapter.getList().get(0).isCheck()) {
            // 如果机构选择的带个是不限，则不循环
        } else {
            // 循环机构
            for (int i = 1; i < mConditionOfficeAdapter.getList().size(); i++) {
                if (mConditionOfficeAdapter.getList().get(i).isCheck()) {
                    mConditionOfficeAdapter.getList().get(i).setAppCustomType("office");
                    mConditionOfficeAdapter.getList().get(i).setAppPosition(i);
                    sysConditions.add(mConditionOfficeAdapter.getList().get(i));
                    sysConditionShows.add(mConditionOfficeAdapter.getList().get(i));
                }
            }
        }

        // 获取状态顶部写死的
        if (ObjectUtils.parseString(mDataBinding.tvStatusCabinetTypeValue.getText()).equals("20'单柜")) {
            // 20单柜
            SysCondition sysCondition = new SysCondition();
            sysCondition.setAppPosition(-1);
            sysCondition.setTableFieldName("singleCont");
            sysCondition.setInputValue("1");
            sysCondition.setDisplayName("20'单柜");
            sysCondition.setInputValue2("特殊");
            sysCondition.setAppCustomType("status");
            sysCondition.setConditionType("int");
            sysConditions.add(sysCondition);
            sysConditionShows.add(sysCondition);
        } else if (ObjectUtils.parseString(mDataBinding.tvStatusCabinetTypeValue.getText()).equals("20'组柜")) {
            SysCondition sysCondition = new SysCondition();
            sysCondition.setAppPosition(-1);
            sysCondition.setTableFieldName("groupCont");
            sysCondition.setInputValue("1");
            sysCondition.setDisplayName("20'组柜");
            sysCondition.setInputValue2("特殊");
            sysCondition.setAppCustomType("status");
            sysCondition.setConditionType("int");
            sysConditions.add(sysCondition);
            sysConditionShows.add(sysCondition);
        } else if (ObjectUtils.parseString(mDataBinding.tvStatusCabinetTypeValue.getText()).equals("多柜筛选")) {
            // 判断哪些多选框被选
            SysCondition sysConditionShow = new SysCondition();
            if (mDataBinding.cbStatusCabinetTypeSelect20.isChecked()) {
                SysCondition sysCondition = new SysCondition();
                sysCondition.setTableFieldName("20Cont");
                sysCondition.setInputValue("1");
                sysCondition.setDisplayName("20");
                sysCondition.setInputValue2("特殊");
                sysCondition.setAppCustomType("status");
                sysCondition.setConditionType("int");
                sysConditions.add(sysCondition);
                sysConditionShow.setAppPosition(-1);
                sysConditionShow.setAppCustomType("status");
                sysConditionShow.setInputValue2("特殊合并");
                sysConditionShow.setDisplayName("20");
            }
            if (mDataBinding.cbStatusCabinetTypeSelect40.isChecked()) {
                SysCondition sysCondition = new SysCondition();
                sysCondition.setTableFieldName("40Cont");
                sysCondition.setInputValue("1");
                sysCondition.setDisplayName("40");
                sysCondition.setInputValue2("特殊");
                sysCondition.setAppCustomType("status");
                sysCondition.setConditionType("int");
                sysConditions.add(sysCondition);
                sysConditionShow.setAppPosition(-1);
                sysConditionShow.setAppCustomType("status");
                sysConditionShow.setInputValue2("特殊合并");
                sysConditionShow.setDisplayName(TextUtils.isEmpty(sysConditionShow.getDisplayName()) ? "" : sysConditionShow.getDisplayName() + "," + "40");
            }
            if (mDataBinding.cbStatusCabinetTypeSelect45.isChecked()) {
                SysCondition sysCondition = new SysCondition();
                sysCondition.setTableFieldName("45Cont");
                sysCondition.setInputValue("1");
                sysCondition.setDisplayName("45");
                sysCondition.setInputValue2("特殊");
                sysCondition.setAppCustomType("status");
                sysCondition.setConditionType("int");
                sysConditions.add(sysCondition);
                sysConditionShow.setAppPosition(-1);
                sysConditionShow.setAppCustomType("status");
                sysConditionShow.setInputValue2("特殊合并");
                sysConditionShow.setDisplayName(TextUtils.isEmpty(sysConditionShow.getDisplayName()) ? "" : sysConditionShow.getDisplayName() + "," + "45");
            }
            sysConditionShows.add(sysConditionShow);
        }


        // 循环状态,是和否，1和0
        for (int i = 0; i < mConditionStatusAdapter.getList().size(); i++) {
            if (!TextUtils.isEmpty(mConditionStatusAdapter.getList().get(i).getInputValue()) || !TextUtils.isEmpty(mConditionStatusAdapter.getList().get(i).getInputValue2())) {
                mConditionStatusAdapter.getList().get(i).setAppCustomType("status");
                mConditionStatusAdapter.getList().get(i).setAppPosition(i);
                sysConditions.add(mConditionStatusAdapter.getList().get(i));
                sysConditionShows.add(mConditionStatusAdapter.getList().get(i));
            }
        }

        // 循环时间范围，需要两个日期都选择
        for (int i = 0; i < mConditionDateAdapter.getList().size(); i++) {
            if (!ObjectUtils.parseString(mConditionDateAdapter.getList().get(i).getInputValue()).trim().equals("") && !ObjectUtils.parseString(mConditionDateAdapter.getList().get(i).getInputValue()).trim().equals("")) {
                mConditionDateAdapter.getList().get(i).setAppCustomType("date");
                mConditionDateAdapter.getList().get(i).setAppPosition(i);
                sysConditions.add(mConditionDateAdapter.getList().get(i));
                sysConditionShows.add(mConditionDateAdapter.getList().get(i));
            }
        }

        // 循环关键字搜索
        for (int i = 0; i < mConditionStringAdapter.getList().size(); i++) {
            if (!ObjectUtils.parseString(mConditionStringAdapter.getList().get(i).getInputValue()).trim().equals("")) {
                mConditionStringAdapter.getList().get(i).setAppCustomType("string");
                mConditionStringAdapter.getList().get(i).setAppPosition(i);
                sysConditions.add(mConditionStringAdapter.getList().get(i));
                sysConditionShows.add(mConditionStringAdapter.getList().get(i));
            }
        }

        // 顶部判断是否显示
        mConditionConditionAdapter.setList(sysConditionShows);
        mConditionConditionAdapter.notifyDataSetChanged();
        if (sysConditions.size() > 0) {
            mHeader.flCondition.setVisibility(View.VISIBLE);
        } else {
            mHeader.flCondition.setVisibility(View.GONE);
        }

        return sysConditions;
    }

    @Override
    public void recyclerViewShowEmpty(boolean isShowRecyclerView) {
        super.recyclerViewShowEmpty(true);
    }

    public static class ViewHolder {
        public View rootView;
        public RecyclerView rlCondition;
        public FrameLayout flCondition;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.rlCondition = rootView.findViewById(R.id.rlCondition);
            this.flCondition = rootView.findViewById(R.id.flCondition);
        }

    }

}
