package com.yaoguang.driver.phone.home.driverstatus.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.driver.R;
import com.yaoguang.appcommon.multimedia.upload.BaseNodeUploadFragment;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.DriverOrderNodeDetailWrapper;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.widget.WarpLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_TOOLBAR;
import static com.yaoguang.lib.common.DateUtils.YYYY_MM_DD_HH_MM;
import static com.yaoguang.lib.common.DateUtils.dateToString;

/**
 * 添加休假计划
 *
 * @author wly
 */

public class DriverStatusSwitchAddFragment extends BaseNodeUploadFragment implements DStatusSwitchAddContact.View {

    public final static int ADD_DRIVER_STATUS_SWITCH_SUCCESS = 1;
    DStatusSwitchAddContact.Presenter presenter = null;
    private InitialView mInitialView;
    private DialogHelper mDialogHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_status_switch_add;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        super.initDataBind(view, inflater);
        mInitialView = new InitialView(view);
        presenter = new DStatusSwitchAddPresenter(this);
    }

    @Override
    public void init() {
        super.init();
        setDriverOrderNodeDetailWrapper(new DriverOrderNodeDetailWrapper());
        getDriverOrderNodeDetailWrapper().setDriverOrderNodeDetail(new DriverOrderNodeDetail());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mInitialView == null) return;
        mInitialView.unbinder.unbind();
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
            mDialogHelper = null;
        }
    }

    public static DriverStatusSwitchAddFragment newInstance() {
        return new DriverStatusSwitchAddFragment();
    }

    @Override
    protected FrameLayout getFrameView() {
        return mInitialView.flMain;
    }

    @Override
    public EditText getEtRemark() {
        return mInitialView.etRemark;
    }

    @Override
    protected TextView getTvNumber() {
        return mInitialView.tvNumber;
    }

    @Override
    public void setStatusSuccess(@NonNull UserDriverStatusDetail result) {
        showToast("计划已保存");
        EventBus.getDefault().post(new HomeEvent(FLAG_REFRESH_TOOLBAR));
        setFragmentResult(ADD_DRIVER_STATUS_SWITCH_SUCCESS, null);
        pop();
    }

    public class InitialView {
        @BindView(R.id.toolbar_left)
        ImageView toolbarLeft;
        @BindView(R.id.toolbar_title)
        TextView toolbarTitle;
        @BindView(R.id.toolbar_right)
        ImageView toolbarRight;
        @BindView(R.id.toolbar)
        Toolbar toolbar;
        @BindView(R.id.llTvTitle)
        View llTvTitle;
        @BindView(R.id.ivRemove)
        ImageView ivRemove;
        @BindView(R.id.llLeaveRemove)
        LinearLayout llLeaveRemove;
        @BindView(R.id.tvWaitStartTime)
        TextView tvWaitStartTime;
        @BindView(R.id.tvStartTimeLogo)
        TextView tvStartTimeLogo;
        @BindView(R.id.startTime)
        TextView startTime;
        @BindView(R.id.tvWaitEndTime)
        TextView tvWaitEndTime;
        @BindView(R.id.tvEndTimeLogo)
        TextView tvEndTimeLogo;
        @BindView(R.id.endTime)
        TextView endTime;
        @BindView(R.id.tvAllowOrder)
        TextView tvAllowOrder;
        @BindView(R.id.cbAllowOrder)
        CheckBox cbAllowOrder;
        @BindView(R.id.tvReason)
        TextView tvReason;
        @BindView(R.id.cbVacation)
        CheckBox cbVacation;
        @BindView(R.id.cbMaintain)
        CheckBox cbMaintain;
        @BindView(R.id.cbOther)
        CheckBox cbOther;
        @BindView(R.id.llReason)
        LinearLayout llReason;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.etRemark)
        EditText etRemark;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.tvNumberSum)
        TextView tvNumberSum;
        @BindView(R.id.llNumber)
        LinearLayout llNumber;
        @BindView(R.id.ivPhotographOne)
        ImageView ibPhotographOne;
        @BindView(R.id.tvAlertUploadOne)
        ImageView tvAlertUploadOne;
        @BindView(R.id.ivPhotographTwo)
        ImageView ibPhotographTwo;
        @BindView(R.id.tvAlertUploadTwo)
        ImageView tvAlertUploadTwo;
        @BindView(R.id.ivPhotographThree)
        ImageView ibPhotographThree;
        @BindView(R.id.tvAlertUploadThree)
        ImageView tvAlertUploadThree;
        @BindView(R.id.llPic)
        WarpLinearLayout llPic;
        @BindView(R.id.ivVideo)
        ImageView ibVideo;
        @BindView(R.id.ivVideoFlag)
        ImageView ivVideoFlag;
        @BindView(R.id.ibAudio)
        ImageView ivAudio;
        @BindView(R.id.ivAudioFlag)
        ImageView ivAudioFlag;
        @BindView(R.id.tvAlert)
        TextView tvAlert;
        @BindView(R.id.ivLocation)
        ImageView ivLocation;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.rlLocation)
        LinearLayout rlLocation;
        @BindView(R.id.llBox)
        LinearLayout llBox;
        @BindView(R.id.tvSubmit)
        TextView tvSubmit;
        @BindView(R.id.scrollView)
        ScrollView scrollView;
        @BindView(R.id.flMain)
        FrameLayout flMain;
        Unbinder unbinder;

        public InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);

            toolbarTitle.setText(getString(R.string.leave_plan));

            tvTitle.setVisibility(View.GONE);
            llTvTitle.setVisibility(View.GONE);
            tvAlert.setVisibility(View.GONE);
            rlLocation.setVisibility(View.GONE);
            tvAlertUploadOne.setVisibility(View.GONE);

            startTime.setText(dateToString(DateUtils.rollDateByField(new Date(), Calendar.MINUTE, 5), YYYY_MM_DD_HH_MM));

            Date endDate = DateUtils.rollDateByField(new Date(), Calendar.DATE, 1);
            endDate = DateUtils.rollDateByField(endDate, Calendar.MINUTE, 15);
            endTime.setText(dateToString(endDate, YYYY_MM_DD_HH_MM));
            etRemark.setHint("休假原因说明...");

            initListener();
        }

        public void initListener() {
            // 开始时间
            startTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppClickUtil.isDuplicateClick()) return;

                    // 选择时间对话框
                    DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                    dateBeginPickerFragment.setComeBack(new DatePickerFragment.ComeBack() {
                        @Override
                        public void getData(String data, String tag) {
                            if (isCurrentTime(4, null, data)) {
                                // 判断时间大于现在时间且5分钟之后
                                mInitialView.startTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(data));
                            } else {
                                showToast("开始时间，必须是当前时间5分钟之后");
                            }
                        }
                    });
                    Bundle args = new Bundle();
                    args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
                    args.putInt(DatePickerFragment.MINUTE_ARITHMETIC, 5);
                    args.putString(DatePickerFragment.MINDATE,
                            dateToString(DateUtils.rollDateByField(new Date(),
                                    Calendar.MINUTE, 5), YYYY_MM_DD_HH_MM));
                    dateBeginPickerFragment.setArguments(args);
                    dateBeginPickerFragment.show(getFragmentManager(), "startTime");
                }
            });
            // 结束时间
            endTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppClickUtil.isDuplicateClick()) return;

                    // 选择时间对话框
                    DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                    dateBeginPickerFragment.setComeBack(new DatePickerFragment.ComeBack() {
                        @Override
                        public void getData(String data, String tag) {
                            if (isCurrentTime(9, mInitialView.startTime.getText().toString(), data)) {
                                // 判断时间大于现在时间且10分钟之后
                                mInitialView.endTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(data));
                            } else {
                                showToast("结束时间，必须是开始时间10分钟之后");
                            }
                        }
                    });
                    Bundle args = new Bundle();
                    args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
                    args.putInt(DatePickerFragment.MINUTE_ARITHMETIC, 15);
                    args.putString(DatePickerFragment.MINDATE,
                            dateToString(DateUtils.rollDateByField(new Date(),
                                    Calendar.MINUTE, 15), YYYY_MM_DD_HH_MM));
                    dateBeginPickerFragment.setArguments(args);
                    dateBeginPickerFragment.show(getFragmentManager(), "endTime");
                }
            });
            // 提交
            tvSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppClickUtil.isDuplicateClick() || startTime == null) return;

                    UserDriverStatusDetail entity = new UserDriverStatusDetail();
                    //  开始时间
                    entity.setStartDate(startTime.getText().toString() + ":00");
                    //  结束时间
                    entity.setEndDate(endTime.getText().toString() + ":00");
                    // 可向我派单(0:否 1:是)
                    entity.setSendFlag(cbAllowOrder.isChecked() ? "1" : "0");
                    // 司机工作状态（0:休假 1：正常）
                    entity.setStatus(0);
                    // 设置图片，视频，录音
                    setSuperNodesBean();
                    // 备注
                    entity.setFlagRemark(etRemark.getText().toString());
                    // 事件选项
                    String flag = "";
                    if (cbVacation.isChecked()) {   // 休假
                        flag += "0,";
                    }
                    if (cbMaintain.isChecked()) {   // 维修
                        flag += "1,";
                    }
                    if (cbOther.isChecked()) {      // 其它
                        flag += "2,";
                    }
                    if (flag.length() > 1) {
                        flag = flag.substring(0, flag.length() - 1);
                        XLog.i("flag" + flag);
                        entity.setFlag(flag);
                    }

                    if (getDriverOrderNodeDetail() != null) {
                        if (!TextUtils.isEmpty(getDriverOrderNodeDetail().getAudioUrl()))
                            entity.setAudioUrl(getDriverOrderNodeDetail().getAudioUrl());
                        if (!TextUtils.isEmpty(getDriverOrderNodeDetail().getVideoUrl()))
                            entity.setVideoUrl(getDriverOrderNodeDetail().getVideoUrl());
                        if (!TextUtils.isEmpty(getDriverOrderNodeDetail().getPicture()))
                            entity.setPicture(getDriverOrderNodeDetail().getPicture());
                    }

                    // 保存到服务器
                    presenter.saveLeaveStatus(getDriverOrderNodeDetail(), entity);
                }
            });
            // 返回
            toolbarLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppClickUtil.isDuplicateClick()) return;

                    DriverStatusSwitchAddFragment.this.setFragmentResult(-1, null);
                    DriverStatusSwitchAddFragment.this.onBackPressedSupport();
                }
            });
        }
    }

    @Override
    protected ImageView getIvVideo() {
        return mInitialView.ibVideo;
    }

    @Override
    protected ImageView getIvPhotographThree() {
        return mInitialView.ibPhotographThree;
    }

    @Override
    protected ImageView getIvPhotographTwo() {
        return mInitialView.ibPhotographTwo;
    }

    @Override
    protected ImageView getIvPhotographOne() {
        return mInitialView.ibPhotographOne;
    }

    @Override
    protected ImageView getIvAudio() {
        return mInitialView.ivAudio;
    }

    @Override
    protected TextView getTvAddress() {
        return mInitialView.tvAddress;
    }

    @Override
    protected ImageView getImageFlagOne() {
        return mInitialView.tvAlertUploadOne;
    }

    @Override
    protected ImageView getImageFlagThree() {
        return mInitialView.tvAlertUploadThree;
    }

    @Override
    protected ImageView getImageFlagTwo() {
        return mInitialView.tvAlertUploadTwo;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    /**
     * 判断时间大于现在时间且10分钟之后
     *
     * @return
     */
    public boolean isCurrentTime(int timeCal, String currentTime, String selectTimeStr) {
        //  选择时间
        SimpleDateFormat format = new java.text.SimpleDateFormat(YYYY_MM_DD_HH_MM);
        Date date;
        try {
            date = format.parse(selectTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        Calendar selectTime = Calendar.getInstance();    // 选择的时间

        selectTime.set(Calendar.YEAR, selectTime.get(Calendar.YEAR));
        selectTime.set(Calendar.MONTH, selectTime.get(Calendar.MONTH));
        selectTime.set(Calendar.DAY_OF_MONTH, selectTime.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        selectTime.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY);
        selectTime.set(Calendar.SECOND, Calendar.SECOND);
        selectTime.setTime(date);

        //  当前时间
        Date currentDate;
        if (!TextUtils.isEmpty(currentTime)) {  // 如果有当前时间就传进入的值，没有就用系统时间
            SimpleDateFormat currentFormat = new java.text.SimpleDateFormat(YYYY_MM_DD_HH_MM);
            try {
                currentDate = currentFormat.parse(currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            currentDate = new Date();
        }
        Calendar current = Calendar.getInstance();    //选择的时间
        current.setTime(currentDate);
        current.set(Calendar.MINUTE, current.get(Calendar.MINUTE) + timeCal);

        if (selectTime.after(current)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onBackPressedSupport() {
        if (mDialogHelper == null)
            mDialogHelper = new DialogHelper(getContext(), "提示", "确定放弃编辑", "是的", "我再想想", false, new CommonDialog.Listener() {
                @Override
                public void ok(String content) {
                    pop();
                }

                @Override
                public void cancel() {

                }
            });
        mDialogHelper.show();
        return true;
    }
}
