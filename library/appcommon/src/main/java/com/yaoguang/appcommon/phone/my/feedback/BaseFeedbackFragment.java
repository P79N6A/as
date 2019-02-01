package com.yaoguang.appcommon.phone.my.feedback;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.multimedia.upload.BaseNodeUploadFragment;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.driver.FeedbackApp;
import com.yaoguang.widget.WarpLinearLayout;

/**
 * Created by 韦理英
 * on 2017/4/27 0027.
 *
 * update zhongjh
 * data 2018-03-24
 * 意见反馈
 */

public abstract class BaseFeedbackFragment extends BaseNodeUploadFragment implements FeedbackContacts.View {
    /**
     * 控制层代码
     */
    FeedbackContacts.Presenter mDFeedbackPresenter;

    InitialView mInitialView;

    protected abstract String getCodeType();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_feedback;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        super.initDataBind(view, inflater);
        mInitialView = new InitialView(view);
    }

    @Override
    public void init() {
        setMaxLength(400);
        super.init();
        mDFeedbackPresenter = new FeedbackPresenter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInitialView.ibPhotographOne.setBackgroundResource(R.drawable.ic_camera_l);
        mInitialView.ibPhotographTwo.setBackgroundResource(R.drawable.ic_camera_l);
        mInitialView.ibPhotographThree.setBackgroundResource(R.drawable.ic_camera_l);
        mInitialView.etRemark.setHint("请输入不少于10个字的描述");
    }

    @Override
    public void onDestroyView() {
        mDFeedbackPresenter.unSubscribe();
        super.onDestroyView();
    }

    protected final class InitialView {
        private ImageView imgReturn;
        private TextView toolbarTitle;
        private Toolbar toolbar;
        private TextView tvNumber;
        private TextView tvNumberSum;
        private LinearLayout llNumber;
        private LinearLayout llAlert;
        private TextView tvTitle;
        private LinearLayout llTvTitle;
        private EditText etRemark;
        private FrameLayout etRemarkLine;
        private TextView tvAlert;
        private ImageView ibPhotographOne;
        private ImageView tvAlertUploadOne;
        private FrameLayout flCamera1;
        private ImageView ibPhotographTwo;
        private ImageView tvAlertUploadTwo;
        private FrameLayout flCamera2;
        private ImageView ibPhotographThree;
        private ImageView tvAlertUploadThree;
        private FrameLayout flCamera3;
        private ImageView ivVideoPlayFlag;
        private ImageView ivVideoFlag;
        private ImageView ivVideo;
        private FrameLayout flVideo1;
        private ImageView ivAudio;
        private ImageView ivAudioPlayPlag;
        private ImageView ivAudioFlag;
        private FrameLayout flAudio1;
        private WarpLinearLayout llPic;
        private LinearLayout llUpload;
        private FrameLayout ivLocationLine;
        private ImageView ivLocation;
        private TextView tvAddress;
        private LinearLayout rlLocation;
        private LinearLayout linearLayout;
        private FrameLayout flMain;
        private CheckBox cbFault;
        private CheckBox cbAdvice;
        private CheckBox cbOther;

        public InitialView(View view) {

            initView(view);
            initListener();
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        private void initView(View view) {
            imgReturn = view.findViewById(R.id.imgReturn);
            toolbarTitle = view.findViewById(R.id.toolbar_title);
            toolbar = view.findViewById(R.id.toolbar);
            tvNumber = view.findViewById(R.id.tvNumber);
            tvNumberSum = view.findViewById(R.id.tvNumberSum);
            llNumber = view.findViewById(R.id.llNumber);
            llAlert = view.findViewById(R.id.llAlert);
            tvTitle = view.findViewById(R.id.tvTitle);
            llTvTitle = view.findViewById(R.id.llTvTitle);
            linearLayout = view.findViewById(R.id.linearLayout);
            etRemark = view.findViewById(R.id.etRemark);
            etRemarkLine = view.findViewById(R.id.etRemark_line);
            tvAlert = view.findViewById(R.id.tvAlert);
            ibPhotographOne = view.findViewById(R.id.ivPhotographOne);
            tvAlertUploadOne = view.findViewById(R.id.tvAlertUploadOne);
            flCamera1 = view.findViewById(R.id.flCamera1);
            ibPhotographTwo = view.findViewById(R.id.ivPhotographTwo);
            tvAlertUploadTwo = view.findViewById(R.id.tvAlertUploadTwo);
            flCamera2 = view.findViewById(R.id.flCamera2);
            ibPhotographThree = view.findViewById(R.id.ivPhotographThree);
            tvAlertUploadThree = view.findViewById(R.id.tvAlertUploadThree);
            flCamera3 = view.findViewById(R.id.flCamera3);
            ivVideoPlayFlag = view.findViewById(R.id.ivVideoPlayFlag);
            ivVideoFlag = view.findViewById(R.id.ivVideoFlag);
            ivVideo = view.findViewById(R.id.ivVideo);
            flVideo1 = view.findViewById(R.id.flVideo1);
            ivAudio = view.findViewById(R.id.ibAudio);
            ivAudioPlayPlag = view.findViewById(R.id.ivAudioPlayPlag);
            ivAudioFlag = view.findViewById(R.id.ivAudioFlag);
            flAudio1 = view.findViewById(R.id.flAudio1);
            llPic = view.findViewById(R.id.llPic);
            llUpload = view.findViewById(R.id.llUpload);
            ivLocationLine = view.findViewById(R.id.ivLocation_line);
            ivLocation = view.findViewById(R.id.ivLocation);
            tvAddress = view.findViewById(R.id.tvAddress);
            rlLocation = view.findViewById(R.id.rlLocation);
            flMain = view.findViewById(R.id.flMain);
            cbFault = view.findViewById(R.id.cbFault);
            cbAdvice = view.findViewById(R.id.cbAdvice);
            cbOther = view.findViewById(R.id.cbOther);

            toolbarTitle.setText(R.string.feedback);
            tvTitle.setText("详情描述（必填）");
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

            // 添加菜单
            toolbar.inflateMenu(R.menu.submit);

            rlLocation.setVisibility(View.GONE);
            flVideo1.setVisibility(View.GONE);
            flAudio1.setVisibility(View.GONE);
            llTvTitle.setVisibility(View.GONE);
            tvAlertUploadOne.setVisibility(View.GONE);

            linearLayout.setBackgroundColor(UiUtils.getColor(R.color.white));


            tvAlert.setText("请提供上述问题的截图或照片");
            tvAlert.setTextColor(UiUtils.getColor(R.color.black666));
            tvAlert.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_qy_green, 0, 0, 0);

            llAlert.setBackgroundColor(UiUtils.getColor(R.color.window_background));
        }

        private void initListener() {

            // 返回
            imgReturn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    pop();
                }
            });
            //
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    String feedbackString = mInitialView.etRemark.getText().toString().trim();
                    if (TextUtils.isEmpty(feedbackString)) {
                        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "请输入不少于10个字的描述", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    FeedbackApp feedbackApp = new FeedbackApp();
                    feedbackApp.setAppType(1);
                    feedbackApp.setPlatformType(0);
                    feedbackApp.setUserId(DataStatic.getInstance().getId());
                    feedbackApp.setAdvice(feedbackString);
                    feedbackApp.setFeedbackPic(getImageUrl());

                    int type = 0;
                    //  功能故障或不可用
                    if (cbFault.isChecked()) {
                        type = type | FeedbackApp.FEEDBACK_TYPE_BUG;
                    }
                    //  产品建议
                    if (cbAdvice.isChecked()) {
                        type = type | FeedbackApp.FEEDBACK_TYPE_ADVICE;
                    }
                    //  其它
                    if (cbOther.isChecked()) {
                        type = type | FeedbackApp.FEEDBACK_TYPE_OTHER;
                    }
                    if (type == 0) {
                        showToast("请选择您要反馈的问题类型");
                        return false;
                    }

                    feedbackApp.setFeedbackType(type);

                    mDFeedbackPresenter.submitFeedback(feedbackApp);
                    return false;
                }
            });
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return mDFeedbackPresenter;
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
    protected ImageView getIvVideo() {
        return mInitialView.ivVideo;
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
    protected ImageView getImageFlagThree() {
        return mInitialView.tvAlertUploadThree;
    }

    @Override
    protected ImageView getImageFlagTwo() {
        return mInitialView.tvAlertUploadTwo;
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
}
