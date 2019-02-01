package com.yaoguang.company.operator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yaoguang.appcommon.BaseMainActivity;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentOperatorBinding;
import com.yaoguang.company.operator.adapter.OperatorAdapter;
import com.yaoguang.company.operator.dialog.DetailDialog;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanCondition;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.appcommon.widget.popupwindow.SpinnerPopWindow;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 因为拖车办单员和货代办单员极其相似，这边就做了父类来共同点
 * Created by zhongjh on 2018/7/25.
 */
public class OperatorFragment extends BaseFragmentListConditionDataBind<AppCompanyBanDanWrapper, AppCompanyBanDanCondition, OperatorAdapter, FragmentOperatorBinding>
        implements OperatorContract.View, Toolbar.OnMenuItemClickListener, View.OnClickListener, SpinnerPopWindow.OnDismissListener {

    private AppCompanyBanDanCondition mAppCompanyBanDanCondition = new AppCompanyBanDanCondition(); // 条件
    private OperatorContract.Presenter mPresenter = new OperatorPresenter(this);
    private SpinnerPopWindow mSpinnerPopWindow;

    // 其实RecycledViewPool的内部维护了一个Map，里面以不同的viewType为Key存储了各自对应的ViewHolder集合，所以这边设置了常量防止父适配器和子适配器的ViewType冲突
    public static final int PARENT_VIEW_TYPE = 0;
    public static final int CHILD_VIEW_TYPE = 1;

    // 触摸事件
    BaseMainActivity.MyDispatchTouchEvent myDispatchTouchEvent;

    private String mBizType; // 0是货代，1是拖车
    private String mSonoIds;// 货柜id，逗号分隔

    /**
     * @param bizType 业务类型(0:货代 1:拖车)
     */
    public static OperatorFragment newInstance(String bizType) {
        OperatorFragment baseOperatorFragment = new OperatorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("bizType", bizType);
        baseOperatorFragment.setArguments(bundle);
        return baseOperatorFragment;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new OperatorAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public AppCompanyBanDanCondition getCondition(boolean isRegain) {
        if (isRegain) {
            // 船到情况
            if (mDataBinding.cbShipToCondition.isChecked()) {
                mAppCompanyBanDanCondition.setCdStatus("");
            } else if (mDataBinding.cbShipToConditionNotReached.isChecked()) {
                mAppCompanyBanDanCondition.setCdStatus("0");
            } else if (mDataBinding.cbShipToConditionAlreadyArrived.isChecked()) {
                mAppCompanyBanDanCondition.setCdStatus("1");
            } else {
                mAppCompanyBanDanCondition.setCdStatus("");
            }

            // 办单进度
            if (mDataBinding.cbSchedule.isChecked()) {
                mAppCompanyBanDanCondition.setBdStatus("");
                mAppCompanyBanDanCondition.setMtddStatus("");
            } else if (mDataBinding.cbScheduleNotReached.isChecked()) {
                mAppCompanyBanDanCondition.setBdStatus("0");
                mAppCompanyBanDanCondition.setMtddStatus("");
            } else if (mDataBinding.cbScheduleAlreadyArrived.isChecked()) {
                mAppCompanyBanDanCondition.setBdStatus("1");
                mAppCompanyBanDanCondition.setMtddStatus("");
            } else if (mDataBinding.cbScheduleAlreadyBeaten.isChecked()) {
                mAppCompanyBanDanCondition.setBdStatus("");
                mAppCompanyBanDanCondition.setMtddStatus("1");
            }

            // 类型
            if (mDataBinding.cbType.isChecked()) {
                mAppCompanyBanDanCondition.setOtherservice("");
            } else if (mDataBinding.cbLoading.isChecked()) {
                mAppCompanyBanDanCondition.setOtherservice("0");
            } else if (mDataBinding.cbUnLoading.isChecked()) {
                mAppCompanyBanDanCondition.setOtherservice("1");
            }

            // 业务时间
            if (mDataBinding.cbCustomDay.isChecked()) {
                mAppCompanyBanDanCondition.setDateScopeType("0");
            } else if (mDataBinding.cbToday.isChecked()) {
                mAppCompanyBanDanCondition.setDateScopeType("1");
            } else if (mDataBinding.cb15Day.isChecked()) {
                mAppCompanyBanDanCondition.setDateScopeType("2");
            } else if (mDataBinding.cb30Day.isChecked()) {
                mAppCompanyBanDanCondition.setDateScopeType("3");
            } else if (mDataBinding.cb90Day.isChecked()) {
                mAppCompanyBanDanCondition.setDateScopeType("4");
            } else if (mDataBinding.cb180Day.isChecked()) {
                mAppCompanyBanDanCondition.setDateScopeType("5");
            } else {
                mAppCompanyBanDanCondition.setDateScopeType("1");
            }

            // 开始 - 结束 时间
            mAppCompanyBanDanCondition.setStartModifyDate(mDataBinding.tvValueBeginDate.getText().toString() + " 00:00:00");
            mAppCompanyBanDanCondition.setEndModifyDate(mDataBinding.tvValueEndDate.getText().toString() + " 23:59:59");

            // 关键字搜索
            if (mDataBinding.tvSpinner.getText().equals("运单号")) {
                mAppCompanyBanDanCondition.setmBlNo(mDataBinding.etSpinnerValue.getText().toString());
                mAppCompanyBanDanCondition.setVessel("");
                mAppCompanyBanDanCondition.setVoyage("");
                mAppCompanyBanDanCondition.setShipCompany("");
                mAppCompanyBanDanCondition.setContNo("");
            } else if (mDataBinding.tvSpinner.getText().equals("船名")) {
                mAppCompanyBanDanCondition.setmBlNo("");
                mAppCompanyBanDanCondition.setVessel(mDataBinding.etSpinnerValue.getText().toString());
                mAppCompanyBanDanCondition.setVoyage("");
                mAppCompanyBanDanCondition.setShipCompany("");
                mAppCompanyBanDanCondition.setContNo("");
            } else if (mDataBinding.tvSpinner.getText().equals("航次")) {
                mAppCompanyBanDanCondition.setmBlNo("");
                mAppCompanyBanDanCondition.setVessel("");
                mAppCompanyBanDanCondition.setVoyage(mDataBinding.etSpinnerValue.getText().toString());
                mAppCompanyBanDanCondition.setShipCompany("");
                mAppCompanyBanDanCondition.setContNo("");
            } else if (mDataBinding.tvSpinner.getText().equals("船公司")) {
                mAppCompanyBanDanCondition.setmBlNo("");
                mAppCompanyBanDanCondition.setVessel("");
                mAppCompanyBanDanCondition.setVoyage("");
                mAppCompanyBanDanCondition.setShipCompany(mDataBinding.etSpinnerValue.getText().toString());
                mAppCompanyBanDanCondition.setContNo("");
            } else if (mDataBinding.tvSpinner.getText().equals("柜号")) {
                mAppCompanyBanDanCondition.setmBlNo("");
                mAppCompanyBanDanCondition.setVessel("");
                mAppCompanyBanDanCondition.setVoyage("");
                mAppCompanyBanDanCondition.setShipCompany("");
                mAppCompanyBanDanCondition.setContNo(mDataBinding.etSpinnerValue.getText().toString());
            }
        }
        return mAppCompanyBanDanCondition;
    }

    @Override
    public void setConditionView(AppCompanyBanDanCondition condition) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_operator;
    }

    @Override
    public void init() {
        // 判断类型
        if (getArguments() != null)
            mAppCompanyBanDanCondition.setBizType(getArguments().getString("bizType"));
        if (mAppCompanyBanDanCondition.getBizType().equals("0")) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "货代办单员任务", -1, null);
            mBizType = "0";
            mDataBinding.flCompletionIntimidate.setVisibility(View.GONE);
            mDataBinding.tvType.setVisibility(View.GONE);
            mDataBinding.llType.setVisibility(View.GONE);
        } else {
            initToolbarNav(mToolbarCommonBinding.toolbar, "拖车办单员任务", -1, null);
            mBizType = "1";
            mDataBinding.flCompletionIntimidate.setVisibility(View.VISIBLE);
            mDataBinding.tvType.setVisibility(View.VISIBLE);
            mDataBinding.llType.setVisibility(View.VISIBLE);
        }


        //关键字搜索的 数据
        List<String> data = new ArrayList<>();
        data.add("运单号");
        data.add("船名");
        data.add("航次");
        data.add("船公司");
        data.add("柜号");
        mSpinnerPopWindow = new SpinnerPopWindow(_mActivity.getApplicationContext(), this, this, data);

        // 创建 ViewHolder的缓存共享池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        LinearLayoutManager layoutManager = (LinearLayoutManager) mLayoutRecyclerviewBinding.rlView.getLayoutManager();
        // 需要注意：要使用RecycledViewPool的话,如果使用的LayoutManager是LinearLayoutManager或其子类（如GridLayoutManager），需要手动开启这个特性
        layoutManager.setRecycleChildrenOnDetach(true);
        mLayoutRecyclerviewBinding.rlView.setRecycledViewPool(recycledViewPool);
        // 传递RecycledViewPool共享池进父适配器，让父适配器里面的子适配器也共用同一个共享池
        mBaseLoadMoreRecyclerAdapter.setmRecycledViewPool(_mActivity.getApplicationContext(), recycledViewPool, isChecked -> {
            // 回调是否全选
            mDataBinding.checkBoxAll.setOnCheckedChangeListener(null);
            mDataBinding.checkBoxAll.setChecked(isChecked);
            mDataBinding.checkBoxAll.setOnCheckedChangeListener((buttonView, isChecked1) -> setOnCheckBoxAll(isChecked1));
        },mBizType);
    }

    /**
     * 返回事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (super.onBackPressedSupport())
            return true;
        if (mDataBinding.flScreenContent.getVisibility() == View.VISIBLE) {
            setGoneScreenContent(true);
            return true;
        }
        if (mDataBinding.flDateContent.getVisibility() == View.VISIBLE) {
            setGoneDateContent(true);
            return true;
        }
        if (mDataBinding.rlBottomAction.getVisibility() == View.VISIBLE) {
            mDataBinding.rlBottomAction.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    @Override
    public void initListener() {

        //触摸事件
        myDispatchTouchEvent = ev -> {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {

                // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
                View v = getActivity().getCurrentFocus();
                if (BaseMainActivity.isShouldHideInput(v, ev)) {
                    hideKeyboard();
                }
            }
            return false;
        };
        if (getActivity() instanceof BaseMainActivity)
            ((BaseMainActivity) getActivity()).registerMyOnTouchListener(myDispatchTouchEvent);

        // region 筛选
        mDataBinding.llScreen.setOnClickListener(v -> {
            if (mDataBinding.flScreenContent.getVisibility() == View.GONE) {
                mDataBinding.imgScreen.setImageResource(R.drawable.ic_shouqi_yellow);
                // 显示
                YoYo.with(Techniques.FadeIn).duration(250).playOn(mDataBinding.flScreenContent);
                mDataBinding.flScreenContent.setVisibility(View.VISIBLE);
                // 隐藏另外一个布局
                setGoneDateContent(true);
            } else {
                // 隐藏
                setGoneScreenContent(true);
            }
        });

        // 重置
        mDataBinding.btnEmpty.setOnClickListener(v -> {
            mDataBinding.cbShipToCondition.performClick();
            mDataBinding.cbSchedule.performClick();
            mDataBinding.cbType.performClick();
        });

        // 确定
        mDataBinding.btnComit.setOnClickListener(v -> {
            // 隐藏
            setGoneScreenContent(false);
            // 刷新
            refreshDataAnimation();
        });

        // endregion 筛选

        // region 时间
        mDataBinding.llDate.setOnClickListener(v -> {
            if (mDataBinding.flDateContent.getVisibility() == View.GONE) {
                mDataBinding.imgDate.setImageResource(R.drawable.ic_shouqi_yellow);
                // 显示
                YoYo.with(Techniques.FadeIn).duration(250).playOn(mDataBinding.flDateContent);
                mDataBinding.flDateContent.setVisibility(View.VISIBLE);
                // 隐藏另外一个布局
                setGoneScreenContent(true);
            } else {
                setGoneDateContent(true);
            }
        });

        // 时间筛选
        mDataBinding.rlBeginDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                dateBeginPickerFragment.show(getFragmentManager(), "LoadingDate");
                Bundle args = new Bundle();
                args.putString(DatePickerFragment.MAXDATE, mDataBinding.tvValueEndDate.getText().toString());
                dateBeginPickerFragment.setArguments(args);
                dateBeginPickerFragment.setComeBack((data, tag) -> {
                    if (tag.equals("LoadingDate")) {
                        mDataBinding.tvValueBeginDate.setText(data);
                    }
                });
            }
        });
        mDataBinding.rlEndDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                dateBeginPickerFragment.show(getFragmentManager(), "EndDateType");
                Bundle args = new Bundle();
                args.putString(DatePickerFragment.MINDATE, mDataBinding.tvValueBeginDate.getText().toString());
                dateBeginPickerFragment.setArguments(args);
                dateBeginPickerFragment.setComeBack((data, tag) -> {
                    if (tag.equals("EndDateType")) {
                        mDataBinding.tvValueEndDate.setText(data);
                    }
                });
            }
        });

        // 重置
        mDataBinding.btnDateEmpty.setOnClickListener(v -> {
            mDataBinding.cbToday.performClick();
            mDataBinding.tvValueBeginDate.setText("");
            mDataBinding.tvValueEndDate.setText("");
        });

        // 确定
        mDataBinding.btnDateComit.setOnClickListener(v -> {
            // 隐藏
            setGoneDateContent(false);
            // 刷新
            refreshDataAnimation();
        });

        // endregion 时间

        // region 关键字搜索 的 筛选
        mDataBinding.llSpinner.setOnClickListener(v -> {
            mSpinnerPopWindow.showSpPop(mDataBinding.llSpinner);
            mDataBinding.imgSpinner.setImageResource(R.drawable.ic_shouqi_yellow);
        });

        // 回车查询
        mDataBinding.etSpinnerValue.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO回车键按下时要执行的操作
                refreshDataAnimation();
            }
            // 隐藏软键盘
            hideKeyboard();
            return false;
        });

        // endregion 关键字的搜索

        // 设置一些条件布局的体验优化
        mDataBinding.llScreenContent.setOnTouchListener((v, event) -> true);
        mDataBinding.flScreenContent.setOnTouchListener((v, event) -> {
            setGoneScreenContent(true);
            return true;
        });
        mDataBinding.llDateContent.setOnTouchListener((v, event) -> true);
        mDataBinding.flDateContent.setOnTouchListener((v, event) -> {
            setGoneDateContent(true);
            return true;
        });
        mDataBinding.llBottomAction.setOnTouchListener((v, event) -> true);
        mDataBinding.rlBottomAction.setOnTouchListener((v, event) -> {
            mDataBinding.rlBottomAction.setVisibility(View.GONE);
            return true;
        });

        // 单选事件
        cbTodayOnCheckedChangeListener();
        cbShipToConditionOnCheckedChangeListener();
        cbScheduleOnCheckedChangeListener();
        cbTypeOnCheckedChangeListener();
        // 时间筛选
        mDataBinding.cbToday.setChecked(true);

        // region 底部
        // 显示底部的功能
        mDataBinding.rlAction.setOnClickListener(v -> {
            if (mDataBinding.rlBottomAction.getVisibility() == View.GONE) {
                // 显示
                YoYo.with(Techniques.FadeIn).duration(250).playOn(mDataBinding.rlBottomAction);
                mDataBinding.rlBottomAction.setVisibility(View.VISIBLE);

                // 存储 20GP*200
                HashMap<String, Integer> hasValue = new HashMap<>();
                HashMap<String, String> sonoIds = new HashMap<>();

                // 获取当前所有选择的
                for (int i = 0; i < mBaseLoadMoreRecyclerAdapter.getmIsCheckedChilds().size(); i++) {
                    for (int k = 0; k < mBaseLoadMoreRecyclerAdapter.getmIsCheckedChilds().get(i).size(); k++) {
                        if (mBaseLoadMoreRecyclerAdapter.getmIsCheckedChilds().get(i).get(k)) {
                            // 获取id，获取数据
                            sonoIds.put(mBaseLoadMoreRecyclerAdapter.getList().get(i).getSonoList().get(k).getId(),mBaseLoadMoreRecyclerAdapter.getList().get(i).getSonoList().get(k).getId());
                            hasValue.put(mBaseLoadMoreRecyclerAdapter.getList().get(i).getContId(), (ObjectUtils.parseInt(ObjectUtils.parseString(hasValue.get(mBaseLoadMoreRecyclerAdapter.getList().get(i).getContId())),0) + 1));
                        }
                    }
                }

                mDataBinding.tvValueCabinet.setText("");
                int doubleValue = 1;// 用于第二个的时候换行
                for (Map.Entry<String, Integer> entry : hasValue.entrySet()) {
                    if (doubleValue == 1) {
                        mDataBinding.tvValueCabinet.append(entry.getKey() + "*" + entry.getValue() + "     ");
                        doubleValue = doubleValue + 1;
                        continue;
                    }
                    if (doubleValue == 2) {
                        mDataBinding.tvValueCabinet.append(entry.getKey() + "*" + entry.getValue() + "\n");
                        doubleValue = 1;
                    }
                }

                StringBuilder sonoIdsBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : sonoIds.entrySet()) {
                    sonoIdsBuilder.append(entry.getValue()).append(",");
                }
                mSonoIds = sonoIdsBuilder.toString();

            } else {
                // 隐藏
                YoYo.with(Techniques.FadeOut).duration(250).playOn(mDataBinding.rlBottomAction);
                mDataBinding.rlBottomAction.setVisibility(View.GONE);
            }
        });

        // 全选功能
        mDataBinding.checkBoxAll.setOnCheckedChangeListener((buttonView, isChecked) -> setOnCheckBoxAll(isChecked));

        mDataBinding.llBottom.setOnClickListener(v -> mDataBinding.checkBoxAll.performClick());

        // 操作功能 - 船到
        mDataBinding.flShipTo.setOnClickListener(v -> {
            // 判断货代还是拖车
            if (mBizType.equals("0")){
                mPresenter.freightUpdate(mSonoIds,"0");
            }else if(mBizType.equals("1")){
                mPresenter.truckUpdate(mSonoIds,"0");
            }
        });

        // 操作功能 - 完成办单
        mDataBinding.flCompletionOrder.setOnClickListener(v -> {
            // 判断货代还是拖车
            if (mBizType.equals("0")){
                mPresenter.freightUpdate(mSonoIds,"1");
            }else if(mBizType.equals("1")){
                mPresenter.truckUpdate(mSonoIds,"1");
            }
        });

        // 操作功能 - 完成打单
        mDataBinding.flCompletionIntimidate.setOnClickListener(v -> {
            // 判断货代还是拖车
            if (mBizType.equals("0")){
                mPresenter.freightUpdate(mSonoIds,"2");
            }else if(mBizType.equals("1")){
                mPresenter.truckUpdate(mSonoIds,"2");
            }
        });

        // endregion

        // 点击更多事件
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            DetailDialog dialog = new DetailDialog(getContext(), (AppCompanyBanDanWrapper) item);
            dialog.show();
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() instanceof BaseMainActivity)
            ((BaseMainActivity) getActivity()).unregisterMyOnTouchListener(myDispatchTouchEvent);
    }

    /**
     * 全选功能的事件
     *
     * @param isChecked 是否选择
     */
    private void setOnCheckBoxAll(Boolean isChecked) {
        // 设置列表是全选还是未全选
        mBaseLoadMoreRecyclerAdapter.setChecked(isChecked);
        mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * 隐藏筛选布局，重新赋值
     *
     * @param isSynchro 是否需要让控件同步当前条件实体
     */
    private void setGoneScreenContent(boolean isSynchro) {
        mDataBinding.imgScreen.setImageResource(R.drawable.ic_zhankai_grey);
        // 隐藏
        YoYo.with(Techniques.FadeOut).duration(250).playOn(mDataBinding.flScreenContent);
        mDataBinding.flScreenContent.setVisibility(View.GONE);

        if (isSynchro) {
            // 重新赋值 船到情况
            if (TextUtils.isEmpty(mAppCompanyBanDanCondition.getCdStatus())) {
                mDataBinding.cbShipToCondition.performClick();
            } else if (mAppCompanyBanDanCondition.getCdStatus().equals("0")) {
                mDataBinding.cbShipToConditionNotReached.performClick();
            } else if (mAppCompanyBanDanCondition.getCdStatus().equals("1")) {
                mDataBinding.cbShipToConditionAlreadyArrived.performClick();
            }

            // 办单进度
            if (TextUtils.isEmpty(mAppCompanyBanDanCondition.getBdStatus()) && TextUtils.isEmpty(mAppCompanyBanDanCondition.getMtddStatus())) {
                mDataBinding.cbSchedule.performClick();
            } else if (mAppCompanyBanDanCondition.getBdStatus().equals("0")) {
                mDataBinding.cbScheduleNotReached.performClick();
            } else if (mAppCompanyBanDanCondition.getBdStatus().equals("1")) {
                mDataBinding.cbScheduleAlreadyArrived.performClick();
            } else if (mAppCompanyBanDanCondition.getMtddStatus().equals("1")) {
                mDataBinding.cbScheduleAlreadyBeaten.performClick();
            } else {
                mDataBinding.cbSchedule.performClick();
            }

            // 类型
            if (TextUtils.isEmpty(mAppCompanyBanDanCondition.getOtherservice())) {
                mDataBinding.cbType.performClick();
            } else if (mAppCompanyBanDanCondition.getOtherservice().equals("0")) {
                mDataBinding.cbLoading.performClick();
            } else if (mAppCompanyBanDanCondition.getOtherservice().equals("1")) {
                mDataBinding.cbUnLoading.performClick();
            }
        }
    }

    /**
     * 隐藏时间布局，重新赋值
     *
     * @param isSynchro 是否需要让控件同步当前条件实体
     */
    @SuppressLint("SetTextI18n")
    private void setGoneDateContent(boolean isSynchro) {
        mDataBinding.imgDate.setImageResource(R.drawable.ic_zhankai_grey);
        // 隐藏
        YoYo.with(Techniques.FadeOut).duration(250).playOn(mDataBinding.flDateContent);
        mDataBinding.flDateContent.setVisibility(View.GONE);

        if (isSynchro) {
            // 重新赋值 业务时间
            if (TextUtils.isEmpty(mAppCompanyBanDanCondition.getDateScopeType())) {
                mDataBinding.cbToday.performClick();
            } else {
                if (mAppCompanyBanDanCondition.getDateScopeType().equals("0")) {
                    mDataBinding.cbCustomDay.performClick();
                } else if (mAppCompanyBanDanCondition.getDateScopeType().equals("1")) {
                    mDataBinding.cbToday.performClick();
                } else if (mAppCompanyBanDanCondition.getDateScopeType().equals("2")) {
                    mDataBinding.cb15Day.performClick();
                } else if (mAppCompanyBanDanCondition.getDateScopeType().equals("3")) {
                    mDataBinding.cb30Day.performClick();
                } else if (mAppCompanyBanDanCondition.getDateScopeType().equals("4")) {
                    mDataBinding.cb90Day.performClick();
                } else if (mAppCompanyBanDanCondition.getDateScopeType().equals("5")) {
                    mDataBinding.cb180Day.performClick();
                } else {
                    mDataBinding.cbToday.performClick();
                }
            }

            // 开始-结束 时间,去掉00:00:00
            mDataBinding.tvValueBeginDate.setText(mAppCompanyBanDanCondition.getStartModifyDate().replace(" 00:00:00", ""));
            mDataBinding.tvValueEndDate.setText(mAppCompanyBanDanCondition.getEndModifyDate().replace(" 23:59:59", ""));
        }

        if (mDataBinding.cbCustomDay.isChecked()){
            String[] dates = mDataBinding.tvValueBeginDate.getText().toString().split("-");
            mDataBinding.tvDateTitle.setText(dates[1] + "-" + dates[2] + "...");
        }else if(mDataBinding.cbToday.isChecked()){
            mDataBinding.tvDateTitle.setText(mDataBinding.cbToday.getText().toString());
        }else if(mDataBinding.cb15Day.isChecked()){
            mDataBinding.tvDateTitle.setText(mDataBinding.cb15Day.getText().toString());
        }else if(mDataBinding.cb30Day.isChecked()){
            mDataBinding.tvDateTitle.setText(mDataBinding.cb30Day.getText().toString());
        }else if(mDataBinding.cb90Day.isChecked()){
            mDataBinding.tvDateTitle.setText(mDataBinding.cb90Day.getText().toString());
        }else if(mDataBinding.cb180Day.isChecked()){
            mDataBinding.tvDateTitle.setText(mDataBinding.cb180Day.getText().toString());
        }



    }

    /**
     * 业务时间的事件
     */
    private void cbTodayOnCheckedChangeListener() {
        mDataBinding.cbToday.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxBusinessTime(buttonView));
        mDataBinding.cb15Day.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxBusinessTime(buttonView));
        mDataBinding.cb30Day.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxBusinessTime(buttonView));
        mDataBinding.cb90Day.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxBusinessTime(buttonView));
        mDataBinding.cb180Day.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxBusinessTime(buttonView));
        mDataBinding.cbCustomDay.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxBusinessTime(buttonView));
    }

    /**
     * 船到情况 单选事件
     */
    private void cbShipToConditionOnCheckedChangeListener() {
        mDataBinding.cbShipToCondition.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxShipToCondition(buttonView));
        mDataBinding.cbShipToConditionNotReached.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxShipToCondition(buttonView));
        mDataBinding.cbShipToConditionAlreadyArrived.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxShipToCondition(buttonView));
    }

    /**
     * 办单进度 单选事件
     */
    private void cbScheduleOnCheckedChangeListener() {
        mDataBinding.cbSchedule.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxSchedule(buttonView));
        mDataBinding.cbScheduleNotReached.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxSchedule(buttonView));
        mDataBinding.cbScheduleAlreadyArrived.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxSchedule(buttonView));
        mDataBinding.cbScheduleAlreadyBeaten.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxSchedule(buttonView));
    }

    /**
     * 类型 单选事件
     */
    private void cbTypeOnCheckedChangeListener() {
        mDataBinding.cbType.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxType(buttonView));
        mDataBinding.cbLoading.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxType(buttonView));
        mDataBinding.cbUnLoading.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxType(buttonView));
    }

    /**
     * 设置单选 （时间）
     *
     * @param buttonView 选中的控件
     */
    private void checkBoxBusinessTime(CompoundButton buttonView) {
        mDataBinding.cbToday.setOnCheckedChangeListener(null);
        mDataBinding.cb15Day.setOnCheckedChangeListener(null);
        mDataBinding.cb30Day.setOnCheckedChangeListener(null);
        mDataBinding.cb90Day.setOnCheckedChangeListener(null);
        mDataBinding.cb180Day.setOnCheckedChangeListener(null);
        mDataBinding.cbCustomDay.setOnCheckedChangeListener(null);
        mDataBinding.cbToday.setChecked(false);
        mDataBinding.cb15Day.setChecked(false);
        mDataBinding.cb30Day.setChecked(false);
        mDataBinding.cb90Day.setChecked(false);
        mDataBinding.cb180Day.setChecked(false);
        mDataBinding.cbCustomDay.setChecked(false);
        buttonView.setChecked(true);
        cbTodayOnCheckedChangeListener();

        if (buttonView.getId() == mDataBinding.cbToday.getId()) {
            mDataBinding.tvValueBeginDate.setText(DateUtils.dateToString(new Date(), DateUtils.YYYY_MM_DD));
            mDataBinding.tvValueEndDate.setText(DateUtils.dateToString(new Date(), DateUtils.YYYY_MM_DD));
        } else if (buttonView.getId() == mDataBinding.cb15Day.getId()) {
            mDataBinding.tvValueBeginDate.setText(DateUtils.dateToString(DateUtils.getPre15Day(new Date()), DateUtils.YYYY_MM_DD));
            mDataBinding.tvValueEndDate.setText(DateUtils.dateToString(new Date(), DateUtils.YYYY_MM_DD));
        } else if (buttonView.getId() == mDataBinding.cb30Day.getId()) {
            mDataBinding.tvValueBeginDate.setText(DateUtils.dateToString(DateUtils.getPreMonth(new Date()), DateUtils.YYYY_MM_DD));
            mDataBinding.tvValueEndDate.setText(DateUtils.dateToString(new Date(), DateUtils.YYYY_MM_DD));
        } else if (buttonView.getId() == mDataBinding.cb90Day.getId()) {
            mDataBinding.tvValueBeginDate.setText(DateUtils.dateToString(DateUtils.getPreThreeMonth(new Date()), DateUtils.YYYY_MM_DD));
            mDataBinding.tvValueEndDate.setText(DateUtils.dateToString(new Date(), DateUtils.YYYY_MM_DD));
        } else if (buttonView.getId() == mDataBinding.cb180Day.getId()) {
            mDataBinding.tvValueBeginDate.setText(DateUtils.dateToString(DateUtils.getPreSixMonth(new Date()), DateUtils.YYYY_MM_DD));
            mDataBinding.tvValueEndDate.setText(DateUtils.dateToString(new Date(), DateUtils.YYYY_MM_DD));
        }


    }

    /**
     * 设置单选（船到情况）
     *
     * @param buttonView 选中的控件
     */
    private void checkBoxShipToCondition(CompoundButton buttonView) {
        mDataBinding.cbShipToCondition.setOnCheckedChangeListener(null);
        mDataBinding.cbShipToConditionNotReached.setOnCheckedChangeListener(null);
        mDataBinding.cbShipToConditionAlreadyArrived.setOnCheckedChangeListener(null);
        mDataBinding.cbShipToCondition.setChecked(false);
        mDataBinding.cbShipToConditionNotReached.setChecked(false);
        mDataBinding.cbShipToConditionAlreadyArrived.setChecked(false);
        buttonView.setChecked(true);
        cbShipToConditionOnCheckedChangeListener();
    }

    /***
     * 设置单选(办单进度)
     * @param buttonView 选中的控件
     */
    private void checkBoxSchedule(CompoundButton buttonView) {
        mDataBinding.cbSchedule.setOnCheckedChangeListener(null);
        mDataBinding.cbScheduleNotReached.setOnCheckedChangeListener(null);
        mDataBinding.cbScheduleAlreadyArrived.setOnCheckedChangeListener(null);
        mDataBinding.cbScheduleAlreadyBeaten.setOnCheckedChangeListener(null);
        mDataBinding.cbSchedule.setChecked(false);
        mDataBinding.cbScheduleNotReached.setChecked(false);
        mDataBinding.cbScheduleAlreadyArrived.setChecked(false);
        mDataBinding.cbScheduleAlreadyBeaten.setChecked(false);
        buttonView.setChecked(true);
        cbScheduleOnCheckedChangeListener();
    }

    /**
     * 设置单选(类型)
     *
     * @param buttonView 选中的控件
     */
    private void checkBoxType(CompoundButton buttonView) {
        mDataBinding.cbType.setOnCheckedChangeListener(null);
        mDataBinding.cbLoading.setOnCheckedChangeListener(null);
        mDataBinding.cbUnLoading.setOnCheckedChangeListener(null);
        mDataBinding.cbType.setChecked(false);
        mDataBinding.cbLoading.setChecked(false);
        mDataBinding.cbUnLoading.setChecked(false);
        buttonView.setChecked(true);
        cbTypeOnCheckedChangeListener();
    }

    /**
     * 搜索下拉框的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        // 关闭本身
        mSpinnerPopWindow.dismiss();
        // 赋值文本
        mDataBinding.tvSpinner.setText(((TextView) v.findViewById(android.R.id.text1)).getText());
    }

    /**
     * 关闭事件
     */
    @Override
    public void onDismiss() {
        // 修改图标颜色
        mDataBinding.imgSpinner.setImageResource(R.drawable.ic_zhankai_grey);
    }

}
