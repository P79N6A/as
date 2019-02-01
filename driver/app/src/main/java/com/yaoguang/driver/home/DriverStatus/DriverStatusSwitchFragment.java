package com.yaoguang.driver.home.driverstatus;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.DateUtils;
import com.yaoguang.common.common.VideoUrl;
import com.yaoguang.driver.databinding.ToolbarCommonBinding;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.baseview.BaseFragmentListV2;
import com.yaoguang.driver.databinding.FragmentDriverStatusSwitchBinding;
import com.yaoguang.driver.home.adapter.DriverStatusSwitchAdapter;
import com.yaoguang.driver.home.driverstatus.add.DriverStatusSwitchAddFragment;
import com.yaoguang.driver.home.wiget.Event;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.yaoguang.common.common.DateUtils.YYYY_MM_DD_HH_MM;

/**
 * 司机状态切换
 */
public class DriverStatusSwitchFragment extends BaseFragmentListV2<DriverStatusSwitch, StatusSwitchPresenter, FragmentDriverStatusSwitchBinding> implements DStatusSwitchContact.View {
    TextView toolbarTitle;
    Toolbar toolbar;
    TextView tvSubmit;
    CheckBox cbSwitch;
    ImageView imgReturn;
    ImageView ivAdd;

    public static final String DRIVER_WORK_STATUS = "driver_work_status";
    public static int FLAG_UPDATE_DRIVER_STATUS = DriverRequest.REQUESTCODE_HOME + 1;

    DialogHelper mDialogHelper;
    DriverStatusSwitchAdapter driverStatusSwitchAdapter;

    /**
     * 休假信息
     */
    UserDriverStatusDetail updateEntity;
    ToolbarCommonBinding mToolbarCommonBinding;

    /**
     * 通知主页状态设置
     * 1 休假 2 工作 3 启动定时器 4 取消定时器
     */
    int mHomeLeaveSetting;
    Timer mTimer;

    private List<UserDriverStatusDetail> value;

    /**
     * 可向我派单 1 可以 0 不可以
     */
    private boolean mAllowOrder;
    private boolean timeUpdate;
    /**
     * true 休假  false 接单
     */
    boolean isServerLeaveStatus; // 服务器接单状态

    public static DriverStatusSwitchFragment newInstance() {
        return new DriverStatusSwitchFragment();
    }

    @Override
    protected void initView(View view) {
        mToolbarCommonBinding = DataBindingUtil.bind(view.findViewById(R.id.toolbarCommon));

        toolbar = mToolbarCommonBinding.toolbar;
        imgReturn = mToolbarCommonBinding.imgReturn;
        toolbarTitle = mToolbarCommonBinding.toolbarTitle;
        tvSubmit = mDataBinding.tvSubmit;
        cbSwitch = mDataBinding.cbSwitch;
        ivAdd = mDataBinding.ivAdd;

        tvSubmit.setText("保存");
        driverStatusSwitchAdapter = new DriverStatusSwitchAdapter(getChildFragmentManager());
        initSwipeRecyclerView(view, driverStatusSwitchAdapter);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter.setData(Injection.provideDriverRepository(getContext()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_driver_status_switch;
    }

    @Override
    protected void initListener() {
        //  删除休假
        driverStatusSwitchAdapter.setOnItemClickListener(new DriverStatusSwitchAdapter.OnItemClickListener() {
            @Override
            public void removeLeave() {
                if (mDialogHelper != null) {
                    mDialogHelper.hideDialog();
                }
                mDialogHelper = new DialogHelper();
                mDialogHelper.showConfirmDialog(getContext(), "提示", "确定删除休假计划？", "是的", "我再想想", false, new CommonDialog.Listener() {
                    @Override
                    public void ok() {
                        mDialogHelper.hideDialog();
                        // 执行删除休假
                        deletePlan();
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }

            @Override
            public void updateStatus(UserDriverStatusDetail updateEntity, Boolean update) {
                DriverStatusSwitchFragment.this.updateEntity = updateEntity;

                // null为更新时间
                if (update == null) {
                    timeUpdate = true;
                } else {
                    // 设置是否允许派单值
                    DriverStatusSwitchFragment.this.updateEntity.setSendFlag(mAllowOrder ? "0" : "1");
                }

                // 设置值和原值不一样就可以设置，时间更新是否已更新
                if (timeUpdate || update != mAllowOrder) {
                    tvSubmit.setBackgroundResource(R.drawable.ic_btn);
                    tvSubmit.setEnabled(true);
                } else {
                    tvSubmit.setBackgroundResource(R.drawable.ic_no);
                    tvSubmit.setEnabled(false);
                }
            }
        });
        toolbar.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            pop();
        });
        toolbarTitle.setText(getString(R.string.status_setting));
        // 添加 休假计划
        ivAdd.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            startForResult(DriverStatusSwitchAddFragment.newInstance(), DriverStatusSwitchAddFragment.ADD_DRIVER_STATUS_SWITCH_SUCCESS);
        });

        // 状态切换
        cbSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (value.isEmpty() || !value.get(0).isStatus()) return;

            // 切换 到工作 就隐藏计划反之显示
            if (isChecked) {
                cbSwitch.setBackgroundResource(R.drawable.ic_xsxx);
            } else {
                cbSwitch.setBackgroundResource(R.drawable.ic_ycxx);

                if (mDialogHelper != null) {
                    mDialogHelper.hideDialog();
                }
                mDialogHelper = new DialogHelper();
                mDialogHelper.showConfirmDialog(getContext(), "提示", "确定删除休假计划？", "是的", "我再想想", new CommonDialog.Listener() {
                    @Override
                    public void ok() {
                        cbSwitch.setBackgroundResource(R.drawable.ic_ycxx);
                        mDialogHelper.hideDialog();
                        // 执行删除休假
                        deletePlan();
                    }

                    @Override
                    public void cancel() {
                        cbSwitch.setBackgroundResource(R.drawable.ic_xsxx);
                    }
                });
            }
        });

        tvSubmit.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            if (updateEntity != null) { // 更新休假
                mPresenter.updateLeavePlan(updateEntity.getId(), updateEntity.getEndDate() + ":00", updateEntity.getSendFlag());
            }
        });
        imgReturn.setOnClickListener(view -> pop());
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (DriverStatusSwitchAddFragment.ADD_DRIVER_STATUS_SWITCH_SUCCESS == requestCode) {
            ivAdd.setVisibility(View.GONE);
            refreshData();
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    /**
     * 刷新adapter
     *
     * @param list  数据
     * @param isHas 是否有无下一页
     */
    @Override
    public void refreshAdapter(List list, boolean isHas) {
        value = list;

        if (!value.isEmpty() && value.get(0) != null && !TextUtils.isEmpty(value.get(0).getStartDate())) { // 休假
            isServerLeaveStatus = true;
            if (value.get(0).isStatus()) {
                // 休假时间开始
                cbSwitch.setChecked(true);
                cbSwitch.setEnabled(true);
                cbSwitch.setVisibility(View.VISIBLE);
                tvSubmit.setVisibility(View.VISIBLE);
                cbSwitch.setBackgroundResource(R.drawable.ic_xsxx);

                mAllowOrder = !TextUtils.isEmpty(value.get(0).getSendFlag()) && value.get(0).getSendFlag().equals("1") ? true : false;
                tvSubmit.setBackgroundResource(R.drawable.ic_no);
                tvSubmit.setEnabled(false);
            } else {
                // 休假开始时间还没开始
                cbSwitch.setChecked(false);
                cbSwitch.setEnabled(false);
                cbSwitch.setBackgroundResource(R.drawable.ic_zh);
                tvSubmit.setVisibility(View.GONE);
            }

            // 一些UI处理
            tvSubmit.setBackgroundResource(R.drawable.ic_no);
            ivAdd.setVisibility(View.GONE);
            super.refreshAdapter(list, false);
            mHomeLeaveSetting = 2;
        } else { // 接单
            //  清空数据
            driverStatusSwitchAdapter.getList().clear();
            driverStatusSwitchAdapter.notifyDataSetChanged();
            //  显示无数据页
            recyclerViewShowEmpty();
            isServerLeaveStatus = false;
            // 一些UI处理
            cbSwitch.setChecked(false);
            cbSwitch.setEnabled(false);
            ivAdd.setVisibility(View.VISIBLE);
            tvSubmit.setVisibility(View.GONE);
            cbSwitch.setVisibility(View.VISIBLE);
            mHomeLeaveSetting = 1;
        }
        Event.updateDriverStatus();
    }


    /**
     * 删除休假计划成功
     */
    @Override
    public void deleteLeavePlanSuccess() {
        showToast("删除休假计划成功");
        mHomeLeaveSetting = 4;
    }


    /**
     * 更新休假计划成功
     */
    @Override
    public void updateSuccess() {
        showToast("更新休假计划成功");
        updateEntity = null;
    }


    /**
     * 设置数据
     *
     * @param data 数据
     */
    @Override
    public void setRefreshAdapter(@NonNull BaseResponse data) {
        // 取消上一个定时器
        if (mTimer != null) {
            XLog.i("取消上一个定时器");
            mTimer.cancel();
        }

        // 刷新adapter
        BaseResponse<DriverStatusSwitch> value = data;
        List<UserDriverStatusDetail> list = new ArrayList<>();
        list.add(value.getResult().getDriverRestPlan());
        refreshAdapter(list, false);
        // 启动定时器
        if (value.getResult() != null && value.getResult().getDriverRestPlan() != null && !TextUtils.isEmpty(value.getResult().getDriverRestPlan().getStartDate())) {
            // 计算秒数
            String currentDate = value.getResult().getCurrentDate();
            Date serverDate = DateUtils.stringToDate(currentDate, YYYY_MM_DD_HH_MM);
            long serverTime = serverDate.getTime();
            Date leaveDate = DateUtils.stringToDate(value.getResult().getDriverRestPlan().getStartDate(), YYYY_MM_DD_HH_MM);
            long leaveTime = leaveDate.getTime();
//            long miao = (leaveTime - serverTime) / 1000;
            long miao = (leaveTime - serverTime) + 5000;
            XLog.i("多少秒后显示" + miao);
            // 如果大于0，就启动定时刷新，加5秒确保刷新安全
            if (miao > 0) {
                // 定时前，先是工作状态，定时结束后才是休假状态
                mTimer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        App.handler.post(() -> {
                            if (cbSwitch == null || driverStatusSwitchAdapter == null) {
                                return;
                            }
                            cbSwitch.setVisibility(View.VISIBLE);
                            cbSwitch.setChecked(true);
                            if (!driverStatusSwitchAdapter.getList().isEmpty()) {
                                driverStatusSwitchAdapter.getList().get(0).setStatus(0);
                                driverStatusSwitchAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                };
                mTimer.schedule(task, miao);
            } else {
                // 如果小于0秒，就马上显示
                cbSwitch.setVisibility(View.VISIBLE);
                if (!driverStatusSwitchAdapter.getList().isEmpty()) {
                    driverStatusSwitchAdapter.getList().get(0).setStatus(0);
                    driverStatusSwitchAdapter.notifyDataSetChanged();
                }
            }
        }
        // 启动首页定时器
        mHomeLeaveSetting = 3;
    }

    /**
     * 刷新数据
     */
    @Override
    protected void refreshData() {
        mPresenter.getDriverLeaveStatus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
            mDialogHelper = null;
        }

        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    /**
     * 删除计划
     */
    private void deletePlan() {
        // 获取删除id
        if (!driverStatusSwitchAdapter.getList().isEmpty() && driverStatusSwitchAdapter.getList().get(0) != null) {
            String id = driverStatusSwitchAdapter.getList().get(0).getId();
            if (!TextUtils.isEmpty(id)) {
                // 休假转工作,保存到服务器
                mPresenter.deleteLeavePlan(id);
            }
        } else {
            XLog.e("你没有休假计划要删除");
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        Bundle bundle = new Bundle();
        bundle.putInt(DRIVER_WORK_STATUS, mHomeLeaveSetting);
        setFragmentResult(FLAG_UPDATE_DRIVER_STATUS, bundle);
        return super.onBackPressedSupport();
    }

    @Override
    public void setAdapter(List list, boolean isHas) {
        List<UserDriverStatusDetail> _list = list;
        // 处理视频缩略图
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (UserDriverStatusDetail bean : _list) {
                    if (!TextUtils.isEmpty(bean.getVideoUrl())) {
                        Bitmap mBitmapVideo = VideoUrl.createVideoThumbnail(bean.getVideoUrl(), MediaStore.Images.Thumbnails.MICRO_KIND);
                        if (mBitmapVideo == null) break;
                        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmapVideo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        final byte[] bytes = stream.toByteArray();
                        bean.setVideo(bytes);
                    }
                }

                App.handler.post(() -> {
                    DriverStatusSwitchFragment.super.setAdapter(list, isHas);
                    DriverStatusSwitchFragment.super.finishRefreshing();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0);
    }

    @Override
    public void recyclerViewShowError(String strMessage) {
        DriverStatusSwitchFragment.super.finishRefreshing();
        super.recyclerViewShowError(strMessage);
    }

    public void recyclerViewShowEmpty() {
        DriverStatusSwitchFragment.super.finishRefreshing();
    }

}
