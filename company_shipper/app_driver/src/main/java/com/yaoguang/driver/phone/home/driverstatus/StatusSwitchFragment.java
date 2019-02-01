package com.yaoguang.driver.phone.home.driverstatus;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.elvishew.xlog.XLog;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentDriverStatusSwitchBinding;
import com.yaoguang.driver.phone.home.adapter.DriverStatusSwitchAdapter;
import com.yaoguang.driver.phone.home.driverstatus.add.DriverStatusSwitchAddFragment;
import com.yaoguang.driver.phone.home.wiget.Event;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.common.file.VideoUrl;
import com.yaoguang.lib.common.rxjava.RxTimerUtil;
import com.yaoguang.lib.databinding.LayoutRecyclerviewBinding;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.yaoguang.lib.common.DateUtils.YYYY_MM_DD_HH_MM;

/**
 * 司机状态切换
 */
public class StatusSwitchFragment extends BaseFragmentDataBind<FragmentDriverStatusSwitchBinding> implements StatusSwitchContact.View {

    StatusSwitchContact.Presenter mPresenter;

    public LayoutRecyclerviewBinding mLayoutRecyclerviewBinding;

    public static final String DRIVER_WORK_STATUS = "driver_work_status";
    public static int FLAG_UPDATE_DRIVER_STATUS = DriverRequest.REQUESTCODE_HOME + 1;

    DialogHelper mDialogHelper;
    DialogHelper mDialogHelper2;
    DriverStatusSwitchAdapter driverStatusSwitchAdapter;

    // 休假信息
    UserDriverStatusDetail updateEntity;

    // 计算秒时计算器
    RxTimerUtil mTimeRxTimerUtil = new RxTimerUtil();
    // ui上的池
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 通知主页状态设置
     * 1 休假 2 工作 3 启动定时器 4 取消定时器
     */
    int mHomeLeaveSetting;

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

    public static StatusSwitchFragment newInstance() {
        return new StatusSwitchFragment();
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void init() {
        mPresenter = new StatusSwitchPresenter(this, getContext());
        driverStatusSwitchAdapter = new DriverStatusSwitchAdapter(getChildFragmentManager());
        mLayoutRecyclerviewBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.layoutRecyclerview));
        RecyclerViewUtils.initPage(mLayoutRecyclerviewBinding.rlView, driverStatusSwitchAdapter, null, getContext(), true);

        mDataBinding.tvSubmit.setText("保存");
    }


    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mPresenter.getDriverLeaveStatus();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_driver_status_switch;
    }

    @Override
    public void initListener() {
        //  删除休假
        driverStatusSwitchAdapter.setOnItemClickListener(new DriverStatusSwitchAdapter.OnItemClickListener() {
            @Override
            public void removeLeave() {
                if (mDialogHelper == null)
                    mDialogHelper = new DialogHelper(getContext(), "提示", "确定删除休假计划？", "是的", "我再想想", false, new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            // 执行删除休假
                            deletePlan();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                mDialogHelper.show();
            }

            @Override
            public void updateStatus(UserDriverStatusDetail updateEntity, Boolean update) {
                StatusSwitchFragment.this.updateEntity = updateEntity;

                // null为更新时间
                if (update == null) {
                    timeUpdate = true;
                } else {
                    // 设置是否允许派单值
                    StatusSwitchFragment.this.updateEntity.setSendFlag(mAllowOrder ? "0" : "1");
                }

                // 设置值和原值不一样就可以设置，时间更新是否已更新
                if (timeUpdate || update != mAllowOrder) {
                    mDataBinding.tvSubmit.setBackgroundResource(R.drawable.ic_btn);
                    mDataBinding.tvSubmit.setEnabled(true);
                } else {
                    mDataBinding.tvSubmit.setBackgroundResource(R.drawable.ic_no);
                    mDataBinding.tvSubmit.setEnabled(false);
                }
            }
        });
        mToolbarCommonBinding.toolbar.setOnClickListener(v ->

        {
            if (AppClickUtil.isDuplicateClick()) return;

            pop();
        });
        mToolbarCommonBinding.toolbarTitle.setText(

                getString(R.string.status_setting));
        // 添加 休假计划
        mDataBinding.ivAdd.setOnClickListener(v ->

        {
            if (AppClickUtil.isDuplicateClick()) return;

            startForResult(DriverStatusSwitchAddFragment.newInstance(), DriverStatusSwitchAddFragment.ADD_DRIVER_STATUS_SWITCH_SUCCESS);
        });

        // 状态切换
        mDataBinding.cbSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->

        {

            if (value.isEmpty() || !value.get(0).isStatus()) return;

            // 切换 到工作 就隐藏计划反之显示
            if (isChecked) {
                mDataBinding.cbSwitch.setBackgroundResource(R.drawable.ic_xsxx);
            } else {
                mDataBinding.cbSwitch.setBackgroundResource(R.drawable.ic_ycxx);

                if (mDialogHelper2 == null)
                    mDialogHelper2 = new DialogHelper(getContext(), "提示", "确定删除休假计划？", "是的", "我再想想", new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            mDataBinding.cbSwitch.setBackgroundResource(R.drawable.ic_ycxx);
                            // 执行删除休假
                            deletePlan();
                        }

                        @Override
                        public void cancel() {
                            mDataBinding.cbSwitch.setBackgroundResource(R.drawable.ic_xsxx);
                        }
                    });
                mDialogHelper2.show();
            }
        });

        mDataBinding.tvSubmit.setOnClickListener(v ->

        {
            if (AppClickUtil.isDuplicateClick()) return;

            if (updateEntity != null) { // 更新休假
                mPresenter.updateLeavePlan(updateEntity.getId(), updateEntity.getEndDate() + ":00", updateEntity.getSendFlag());
            }
        });
        mToolbarCommonBinding.imgReturn.setOnClickListener(view ->

                pop());
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (DriverStatusSwitchAddFragment.ADD_DRIVER_STATUS_SWITCH_SUCCESS == requestCode) {
            mDataBinding.ivAdd.setVisibility(View.GONE);
            // 重新刷新
            mPresenter.getDriverLeaveStatus();
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }


    @Override
    public void refreshAdapter(List list) {
        value = list;

        if (!value.isEmpty() && value.get(0) != null && !TextUtils.isEmpty(value.get(0).getStartDate())) {
            // 休假
            isServerLeaveStatus = true;

            if (value.get(0).isStatus()) {
                // 休假时间开始
                mDataBinding.cbSwitch.setChecked(true);
                mDataBinding.cbSwitch.setEnabled(true);
                mDataBinding.cbSwitch.setVisibility(View.VISIBLE);
                mDataBinding.tvSubmit.setVisibility(View.VISIBLE);
                mDataBinding.cbSwitch.setBackgroundResource(R.drawable.ic_xsxx);

                mAllowOrder = !TextUtils.isEmpty(value.get(0).getSendFlag()) && value.get(0).getSendFlag().equals("1");
                mDataBinding.tvSubmit.setBackgroundResource(R.drawable.ic_no);
                mDataBinding.tvSubmit.setEnabled(false);
            } else {
                // 休假开始时间还没开始
                mDataBinding.cbSwitch.setChecked(false);
                mDataBinding.cbSwitch.setEnabled(false);
                mDataBinding.cbSwitch.setBackgroundResource(R.drawable.ic_zh);
                mDataBinding.tvSubmit.setVisibility(View.GONE);
            }

            // 一些UI处理
            mDataBinding.tvSubmit.setBackgroundResource(R.drawable.ic_no);
            mDataBinding.ivAdd.setVisibility(View.GONE);

            refreshAdapter(list);

            mHomeLeaveSetting = 2;
        } else {
            //  清空数据
            driverStatusSwitchAdapter.getList().clear();
            driverStatusSwitchAdapter.notifyDataSetChanged();

            isServerLeaveStatus = false;
            // 一些UI处理
            mDataBinding.cbSwitch.setChecked(false);
            mDataBinding.cbSwitch.setEnabled(false);
            mDataBinding.ivAdd.setVisibility(View.VISIBLE);
            mDataBinding.tvSubmit.setVisibility(View.GONE);
            mDataBinding.cbSwitch.setVisibility(View.VISIBLE);
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
        mTimeRxTimerUtil.cancel();

        // 刷新adapter
        BaseResponse<DriverStatusSwitch> value = data;
        List<UserDriverStatusDetail> list = new ArrayList<>();
        list.add(value.getResult().getDriverRestPlan());
        refreshAdapter(list);
        // 启动定时器
        if (value.getResult() != null && value.getResult().getDriverRestPlan() != null && !TextUtils.isEmpty(value.getResult().getDriverRestPlan().getStartDate())) {
            // 计算秒数
            String currentDate = value.getResult().getCurrentDate();
            Date serverDate = DateUtils.stringToDate(currentDate, YYYY_MM_DD_HH_MM);
            long serverTime = serverDate.getTime();
            Date leaveDate = DateUtils.stringToDate(value.getResult().getDriverRestPlan().getStartDate(), YYYY_MM_DD_HH_MM);
            long leaveTime = leaveDate.getTime();
            long miao = (leaveTime - serverTime) + 5000;
            XLog.i("多少秒后显示" + miao);
            // 如果大于0，就启动定时刷新，加5秒确保刷新安全
            if (miao > 0) {
                // 定时前，先是工作状态，定时结束后才是休假状态
                mTimeRxTimerUtil.timer(miao, number -> {
                    if (mDataBinding.cbSwitch == null || driverStatusSwitchAdapter == null) {
                        return;
                    }
                    mDataBinding.cbSwitch.setVisibility(View.VISIBLE);
                    mDataBinding.cbSwitch.setChecked(true);
                    if (!driverStatusSwitchAdapter.getList().isEmpty()) {
                        driverStatusSwitchAdapter.getList().get(0).setStatus(0);
                        driverStatusSwitchAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                // 如果小于0秒，就马上显示
                mDataBinding.cbSwitch.setVisibility(View.VISIBLE);
                if (!driverStatusSwitchAdapter.getList().isEmpty()) {
                    driverStatusSwitchAdapter.getList().get(0).setStatus(0);
                    driverStatusSwitchAdapter.notifyDataSetChanged();
                }
            }
        }
        // 启动首页定时器
        mHomeLeaveSetting = 3;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
            mDialogHelper = null;
        }

        mTimeRxTimerUtil.cancel();

        mCompositeDisposable.clear();
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
    public void setAdapter(List list) {
        // 处理视频缩略图
        //定义被观察者
        Observable.create((ObservableOnSubscribe<Boolean>) e -> {
            for (UserDriverStatusDetail bean : (List<UserDriverStatusDetail>) list) {
                if (!TextUtils.isEmpty(bean.getVideoUrl())) {
                    Bitmap mBitmapVideo = VideoUrl.createVideoThumbnail(bean.getVideoUrl(), MediaStore.Images.Thumbnails.MICRO_KIND);
                    if (mBitmapVideo == null) break;
                    final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mBitmapVideo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    final byte[] bytes = stream.toByteArray();
                    bean.setVideo(bytes);
                }
            }
            e.onNext(true);
        }).subscribe(new Observer<Boolean>() {        //观察者
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Boolean value) {
                if (value) {
                    setAdapter(list);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
