package com.yaoguang.driver.phone.home.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.bugly.crashreport.CrashReport;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.lib.common.file.MediaUtils;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ConvertUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.widget.WarpLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 作    者：韦理英
 * 时    间：2017/9/14 0014.
 * 描    述：司机状态adapter
 */
public class DriverStatusSwitchAdapter extends BaseLoadMoreRecyclerAdapter<UserDriverStatusDetail, RecyclerView.ViewHolder> {

    private final FragmentManager childFragmentManager;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public DriverStatusSwitchAdapter(FragmentManager childFragmentManager) {
        this.childFragmentManager = childFragmentManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_leave_plan, null);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final UserDriverStatusDetail result = getList().get(position);
        // 有休假计划 初始化
        try {
            //  开始时间
            String startDate = result.getStartDate().substring(0, result.getStartDate().length() - 3);
            viewHolder.startTime.setText(startDate);
            viewHolder.tvWaitStartTime.setText("开始时间：" + startDate);
            //  结束时间
            String endDate = result.getEndDate().substring(0, result.getEndDate().length() - 3);
            viewHolder.endTime.setText(endDate);
            viewHolder.tvWaitEndTime.setText("结束时间：" + endDate);
            viewHolder.endTime.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                dateBeginPickerFragment.setComeBack((data, tag) -> {
                    // 更新实体
                    viewHolder.endTime.setText(ObjectUtils.parseStringYYYYMMDDHHMM(data) + ":00");
                    getList().get(position).setEndDate(data);

                    if (onItemClickListener != null) {
                        onItemClickListener.updateStatus(result, null);
                    }
                });
                Bundle args = new Bundle();
                args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
                args.putInt(DatePickerFragment.MINUTE_ARITHMETIC, 15);
                dateBeginPickerFragment.setArguments(args);
                dateBeginPickerFragment.show(childFragmentManager, "endTime");
            });
            // 可向我派单
            viewHolder.cbAllowOrder.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (onItemClickListener != null) {
                    onItemClickListener.updateStatus(result, isChecked);
                }
            });

            //  休假判断 一些UI的处理
            viewHolder.tvStartTimeLogo.setCompoundDrawablesRelativeWithIntrinsicBounds(UiUtils.getDrawable(R.drawable.ic_kssj_hh), null, null, null); //    开始时间通用灰色
            viewHolder.tvStartTimeLogo.setTextColor(UiUtils.getColor(R.color.text_cccccc));
            viewHolder.startTime.setTextColor(UiUtils.getColor(R.color.text_cccccc));
            //  可见UI处理
            viewHolder.tvReason.setVisibility(View.VISIBLE);
            //  不可见UI处理
            viewHolder.cbMaintain.setVisibility(View.GONE);
            viewHolder.cbVacation.setVisibility(View.GONE);
            viewHolder.cbOther.setVisibility(View.GONE);
            viewHolder.tvAlertUploadOne.setVisibility(View.GONE);
            if (result.isStatus()) {
                // 休假开始
                viewHolder.startTime.setVisibility(View.VISIBLE);
                viewHolder.startTime.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                viewHolder.startTime.setPadding(0, 0, ConvertUtils.dp2px(22), 0);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.cbVacation.getLayoutParams();
                layoutParams.setMargins(-ConvertUtils.dp2px(5), 0, 0, 0);
                viewHolder.cbVacation.setLayoutParams(layoutParams);

                viewHolder.tvStartTimeLogo.setVisibility(View.VISIBLE);
                viewHolder.tvEndTimeLogo.setVisibility(View.VISIBLE);
                viewHolder.endTime.setVisibility(View.VISIBLE);
                viewHolder.tvAllowOrder.setVisibility(View.GONE);
                viewHolder.cbAllowOrder.setVisibility(View.VISIBLE);

                viewHolder.cbMaintain.setVisibility(View.VISIBLE);
                viewHolder.cbVacation.setVisibility(View.VISIBLE);
                viewHolder.cbOther.setVisibility(View.VISIBLE);

                viewHolder.llLeaveRemove.setVisibility(View.GONE);
                viewHolder.llLeaveRemove_line.setVisibility(View.GONE);
                viewHolder.tvWaitStartTime.setVisibility(View.GONE);
                viewHolder.tvWaitEndTime.setVisibility(View.GONE);

            } else {
                //  休假没开始
                viewHolder.startTime.setVisibility(View.GONE);
                viewHolder.tvStartTimeLogo.setVisibility(View.GONE);
                viewHolder.tvEndTimeLogo.setVisibility(View.GONE);
                viewHolder.endTime.setVisibility(View.GONE);
                viewHolder.tvAllowOrder.setVisibility(View.VISIBLE);
                viewHolder.cbAllowOrder.setVisibility(View.GONE);

                viewHolder.llLeaveRemove.setVisibility(View.VISIBLE);
                viewHolder.llLeaveRemove_line.setVisibility(View.VISIBLE);
                viewHolder.vWaitStartTimeLine.setVisibility(View.GONE);
                viewHolder.vWaitEndTimeLine.setVisibility(View.GONE);
                viewHolder.vAllowOrderLine.setVisibility(View.GONE);
                viewHolder.tvWaitStartTime.setVisibility(View.VISIBLE);
                viewHolder.tvWaitEndTime.setVisibility(View.VISIBLE);

                viewHolder.tvReason.setVisibility(View.VISIBLE);
            }

            // 移除休假
            viewHolder.ivRemove.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.removeLeave();
                }
            });
            //  可向我派单
            boolean allowOrder = !TextUtils.isEmpty(result.getSendFlag()) && result.getSendFlag().equals("1");
            viewHolder.cbAllowOrder.setChecked(allowOrder);
            if (allowOrder) {
                viewHolder.tvAllowOrder.setText("可向我派单");
            } else {
                viewHolder.tvAllowOrder.setText("不可向我派单");
            }
            //  休假事项
            if (!TextUtils.isEmpty(result.getFlag())) {
                String reason = "";
                // 休假
                viewHolder.cbVacation.setChecked(result.getFlag().contains("0"));
                if (viewHolder.cbVacation.isChecked()) {
                    reason += " 休假";
                }
                // 车辆维修
                viewHolder.cbMaintain.setChecked(result.getFlag().contains("1"));
                if (viewHolder.cbMaintain.isChecked()) {
                    reason += " 车辆维修";
                }
                // 其它原因
                viewHolder.cbOther.setChecked(result.getFlag().contains("2"));
                if (viewHolder.cbOther.isChecked()) {
                    reason += " 其它原因";
                }
                if (!TextUtils.isEmpty(reason)) {
                    viewHolder.tvReason.setText(reason);
                    viewHolder.llReason_line.setVisibility(View.VISIBLE);
                    viewHolder.llReason.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.llReason.setVisibility(View.GONE);
                    viewHolder.llReason_line.setVisibility(View.GONE);
                }
            } else {
                viewHolder.llReason.setVisibility(View.GONE);
                viewHolder.llReason_line.setVisibility(View.GONE);
            }

            //描述
            if (!TextUtils.isEmpty(result.getFlagRemark())) {
                viewHolder.etRemark.setText(result.getFlagRemark());
                viewHolder.etRemark.setVisibility(View.VISIBLE);
                viewHolder.etRemark_line.setVisibility(View.VISIBLE);
            } else {
                viewHolder.etRemark.setVisibility(View.GONE);
                viewHolder.etRemark_line.setVisibility(View.GONE);
            }

            // 默认hide有图片就show
            viewHolder.llPic.setVisibility(View.GONE);
            viewHolder.ibPhotographOne.setVisibility(View.GONE);
            viewHolder.ibPhotographTwo.setVisibility(View.GONE);
            viewHolder.ibPhotographThree.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(result.getPicture())) {
                // 图片
                String[] pictures = result.getPicture().split(",");
                for (int i = 0; i < pictures.length; i++) {
                    final String picture = pictures[i];
                    if (!TextUtils.isEmpty(picture)) {
                        View.OnClickListener clickPhone = v -> LookPhotoActivity.newInstance((Activity) context, picture);
                        switch (i) {
                            case 0:
                                viewHolder.llPic.setVisibility(View.VISIBLE);
                                viewHolder.flCamera1.setVisibility(View.VISIBLE);
                                viewHolder.ibPhotographOne.setVisibility(View.VISIBLE);
                                viewHolder.ibPhotographOne.setOnClickListener(clickPhone);
                                GlideManager.getInstance().withSquare(context, picture, viewHolder.ibPhotographOne);
                                break;
                            case 1:
                                viewHolder.flCamera2.setVisibility(View.VISIBLE);
                                viewHolder.ibPhotographTwo.setVisibility(View.VISIBLE);
                                viewHolder.ibPhotographTwo.setOnClickListener(clickPhone);
                                GlideManager.getInstance().withSquare(context, picture, viewHolder.ibPhotographTwo);
                                break;
                            case 2:
                                viewHolder.flCamera3.setVisibility(View.VISIBLE);
                                viewHolder.ibPhotographThree.setVisibility(View.VISIBLE);
                                viewHolder.ibPhotographThree.setOnClickListener(clickPhone);
                                GlideManager.getInstance().withSquare(context, picture, viewHolder.ibPhotographThree);
                                break;
                        }
                    }
                }
            }

            // 视频
            if (!TextUtils.isEmpty(result.getVideoUrl()) && result.getVideo() != null) {
                viewHolder.ivVideoPlayFlag.setVisibility(View.VISIBLE);
                viewHolder.ivVideo.setVisibility(View.VISIBLE);
                viewHolder.llPic.setVisibility(View.VISIBLE);

                Glide.with(context)
                        .load(result.getVideo())
                        .into(viewHolder.ivVideo);


                viewHolder.ivVideo.setOnClickListener(v -> {
                    if (AppClickUtil.isDuplicateClick()) return;

                    MediaUtils.openVideo(context, result.getVideoUrl());
                });
            } else {
                viewHolder.ivVideo.setVisibility(View.GONE);
            }
            // 声音
            if (!TextUtils.isEmpty(result.getAudioUrl())) {
                viewHolder.ivAudioPlayPlag.setVisibility(View.VISIBLE);
                viewHolder.ibAudio.setVisibility(View.VISIBLE);
                viewHolder.llPic.setVisibility(View.VISIBLE);

                viewHolder.ibAudio.setBackgroundResource(R.drawable.ic_bfyybg);
                viewHolder.ibAudio.setOnClickListener(v -> {
                    if (AppClickUtil.isDuplicateClick()) return;

                    MediaUtils.openAudio(context, result.getAudioUrl());
                });
            } else {
                viewHolder.ivAudioPlayPlag.setVisibility(View.GONE);
                viewHolder.ibAudio.setVisibility(View.GONE);
            }
            viewHolder.tvTitle.setVisibility(View.GONE);
            viewHolder.llNumber.setVisibility(View.GONE);
            viewHolder.llTvTitle.setVisibility(View.GONE);
            viewHolder.tvAlert.setVisibility(View.GONE);
            viewHolder.rlLocation.setVisibility(View.GONE);
            viewHolder.ivLocation_line.setVisibility(View.GONE);
            viewHolder.etRemark.setEnabled(false);
            viewHolder.etRemark.setClickable(false);

        } catch (Exception e) {
            CrashReport.postCatchedException(e);
            e.printStackTrace();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void removeLeave();

        void updateStatus(UserDriverStatusDetail updateEntity, Boolean update);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivRemove)
        ImageView ivRemove;
        @BindView(R.id.llLeaveRemove)
        LinearLayout llLeaveRemove;
        @BindView(R.id.llLeaveRemove_line)
        View llLeaveRemove_line;
        @BindView(R.id.vWaitStartTimeLine)
        View vWaitStartTimeLine;
        @BindView(R.id.vWaitEndTimeLine)
        View vWaitEndTimeLine;
        @BindView(R.id.vAllowOrderLine)
        View vAllowOrderLine;
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
        @BindView(R.id.llReason_line)
        View llReason_line;
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
        @BindView(R.id.llTvTitle)
        LinearLayout llTvTitle;
        @BindView(R.id.etRemark)
        EditText etRemark;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.tvNumberSum)
        TextView tvNumberSum;
        @BindView(R.id.llNumber)
        LinearLayout llNumber;
        @BindView(R.id.tvAlert)
        TextView tvAlert;
        @BindView(R.id.ivPhotographOne)
        ImageView ibPhotographOne;
        @BindView(R.id.tvAlertUploadOne)
        ImageView tvAlertUploadOne;
        @BindView(R.id.ivPhotographTwo)
        ImageView ibPhotographTwo;
        @BindView(R.id.tvAlertUploadTwo)
        ImageView tvAlertUploadTwo;
        @BindView(R.id.flCamera1)
        FrameLayout flCamera1;
        @BindView(R.id.flCamera2)
        FrameLayout flCamera2;
        @BindView(R.id.flCamera3)
        FrameLayout flCamera3;
        @BindView(R.id.ivPhotographThree)
        ImageView ibPhotographThree;
        @BindView(R.id.tvAlertUploadThree)
        ImageView tvAlertUploadThree;
        @BindView(R.id.llPic)
        WarpLinearLayout llPic;
        @BindView(R.id.ivVideo)
        ImageView ivVideo;
        @BindView(R.id.ivVideoPlayFlag)
        ImageView ivVideoPlayFlag;
        @BindView(R.id.ivVideoFlag)
        ImageView ivVideoFlag;
        @BindView(R.id.ibAudio)
        ImageView ibAudio;
        @BindView(R.id.ivAudioPlayPlag)
        ImageView ivAudioPlayPlag;
        @BindView(R.id.ivAudioFlag)
        ImageView ivAudioFlag;
        @BindView(R.id.ivLocation)
        ImageView ivLocation;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.rlLocation)
        LinearLayout rlLocation;
        @BindView(R.id.llBox)
        LinearLayout llBox;
        @BindView(R.id.etRemark_line)
        View etRemark_line;
        @BindView(R.id.ivLocation_line)
        View ivLocation_line;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
