package com.yaoguang.driver.order.submit;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.appcommon.my.upload.BaseNodeUploadFragment;
import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.appcommon.utils.EditTextUtils;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.SpanUtils;
import com.yaoguang.driver.R;
import com.yaoguang.interactor.driver.order.DOrderNodeRichTextPresenterImpl;
import com.yaoguang.interfaces.driver.presenter.order.DOrderNodeRichTextPresenter;
import com.yaoguang.interfaces.driver.view.order.DOrderNodeRichTextView;
import com.yaoguang.widget.WarpLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.yaoguang.driver.order.map.OrderNodeMapFragment.TO_NEXT_NOTE;

/**
 * 节点的富文本数据提交：例如图片,视频,语音...
 * Created by wly on 2017/4/28.
 */
public class OrderNodeRichTextFragment extends BaseNodeUploadFragment implements DOrderNodeRichTextView {
    InitialView mInitialView;
    DOrderNodeRichTextPresenter mDOrderNodeRichTextPresenter;

    private DialogHelper mDialogHelper;
    private String mId;

    public static OrderNodeRichTextFragment newInstance(String id) {
        OrderNodeRichTextFragment fragment = new OrderNodeRichTextFragment();
        Bundle args = new Bundle();
        args.putString(NODESBEAN, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(NODESBEAN))) {
            mId = getArguments().getString(NODESBEAN);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NODESBEAN, mId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_ordernode_richtext, null);
        mDOrderNodeRichTextPresenter = new DOrderNodeRichTextPresenterImpl(this);
        mInitialView = new InitialView(view);
        showAddress();

        if (savedInstanceState != null) {
            mId = savedInstanceState.getString(NODESBEAN);
        }

        // 不显示本地上传功能
        setShowLocalChoice(false);
        mDOrderNodeRichTextPresenter.initEditData(mId);
        return view;
    }

    @Override
    public void onPause() {
        hideKeyboard2();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected FrameLayout getFrameView() {
        return mInitialView.flMain;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        mInitialView.toolbarTitle.setText(getDriverOrderNodeDetail().getNodeName());
        String tips = TextUtils.isEmpty(getDriverOrderNodeDetailWrapper().getTips()) ? "箱体，封号" : getDriverOrderNodeDetailWrapper().getTips();
        mInitialView.tvAlert.setText(new SpanUtils().append("*").setForegroundColor(Color.RED)
                .append("你可以拍摄").append(tips)
                .setForegroundColor(Color.RED).append("的照片，并添加相关的录音和视频").create());
        mInitialView.etContNoFirst.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11), new InputFilter.AllCaps()});
        mInitialView.etContNoFirst.setText(getDriverOrderNodeDetail().getContNoFirst());
        mInitialView.etContNoSecond.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11), new InputFilter.AllCaps()});
        mInitialView.etContNoSecond.setText(getDriverOrderNodeDetail().getContNoSecond());
        mInitialView.etSealNoFirst.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        mInitialView.etSealNoFirst.setText(getDriverOrderNodeDetail().getSealNoFirst());
        mInitialView.etSealNoSecond.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        mInitialView.etSealNoSecond.setText(getDriverOrderNodeDetail().getSealNoSecond());

        // 是否需要强制拍照或视频
        if (!getDriverOrderNodeDetailWrapper().isPhotoRequired()) {
            mInitialView.tvAlertUploadOne.setVisibility(View.GONE);
        }

        // 柜号一是否可填
        if (!getDriverOrderNodeDetailWrapper().isContNoFirstInput()) {
            mInitialView.etContNoFirst.setEnabled(false);
        }
        // 封号一是否可填
        if (!getDriverOrderNodeDetailWrapper().isSealNoFirstInput()) {
            mInitialView.etSealNoFirst.setEnabled(false);
        }

        //  判断是装货还是卸货
        if (getDriverOrderNodeDetail().getNodeName().contains("装货") || getDriverOrderNodeDetail().getNodeName().contains("卸货")) {
            mInitialView.cbSelectFirst.setText(getString(R.string.yi_wan_chen_zhen_gui) + (getDriverOrderNodeDetail().getNodeName().contains("装货") ? "装货" : "卸货"));
            mInitialView.cbSelectSecond.setText(getString(R.string.yi_wan_chen_zhen_gui) + (getDriverOrderNodeDetail().getNodeName().contains("装货") ? "装货" : "卸货"));

            mInitialView.llCbNo1.setVisibility(View.VISIBLE);
            mInitialView.llCbNo2.setVisibility(View.VISIBLE);
        } else {  // 其它状态
            mInitialView.llCbNo1.setVisibility(View.GONE);
            mInitialView.llCbNo2.setVisibility(View.GONE);
        }

        //  判断是柜号一是否是选择状态，或gone（-1:禁用 0:未选 1:已选）
        if (!TextUtils.isEmpty(getDriverOrderNodeDetail().getSonoFirstFinish())) {
            switch (getDriverOrderNodeDetail().getSonoFirstFinish()) {
                case "-1":  // 禁用
//                mInitialView.cbSelectFirst.setChecked(true);
//                mInitialView.cbSelectFirst.setEnabled(false);
//                break;
                case "1" :   // 已选
                    mInitialView.cbSelectFirst.setChecked(true);
                    mInitialView.cbSelectFirst.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        mInitialView.cbSelectFirst.setChecked(true);    //  只显示给用户看，不能改
                    });
                    break;
                case "0":
                    mInitialView.cbSelectFirst.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {    //  选中
                            OrderNodeRichTextFragment.this.getDriverOrderNodeDetail().setSonoFirstFinish("1");
                        } else {    //  未选中
                            OrderNodeRichTextFragment.this.getDriverOrderNodeDetail().setSonoFirstFinish("0");
                        }
                    });
                    break;
//                default:
//                    mInitialView.cbSelectFirst.setChecked(true);
//                    mInitialView.cbSelectFirst.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                        mInitialView.cbSelectFirst.setChecked(true);    //  只显示给用户看，不能改
//                    });
//                    break;
            }
        } else {
            mInitialView.llCbNo1.setVisibility(View.GONE);
        }


        //  判断是柜号二是否是选择状态，或gone（-1:禁用 0:未选 1:已选）
        if (!TextUtils.isEmpty(getDriverOrderNodeDetail().getSonoSecondFinish())) {
            switch (getDriverOrderNodeDetail().getSonoSecondFinish()) {
                case "-1":  // 禁用
//                mInitialView.cbSelectSecond.setChecked(true);
//                mInitialView.cbSelectSecond.setEnabled(false);
//                break;
                case "1":   // 已选
                    mInitialView.cbSelectSecond.setChecked(true);
                    mInitialView.cbSelectSecond.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        mInitialView.cbSelectSecond.setChecked(true);    //  只显示给用户看，不能改
                    });
                    break;
                case "0":   // 可选
                    mInitialView.cbSelectSecond.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {    //  选中
                            OrderNodeRichTextFragment.this.getDriverOrderNodeDetail().setSonoSecondFinish("1");
                        } else {    //  未选中
                            OrderNodeRichTextFragment.this.getDriverOrderNodeDetail().setSonoSecondFinish("0");
                        }
                    });
                    break;
//                default:
//                    mInitialView.cbSelectSecond.setChecked(true);
//                    mInitialView.cbSelectSecond.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                        mInitialView.cbSelectSecond.setChecked(true);    //  只显示给用户看，不能改
//                    });
//                    break;
            }
        } else {
            mInitialView.llCbNo2.setVisibility(View.GONE);
        }

        // 柜号二是否可填
        if (!getDriverOrderNodeDetailWrapper().isContNoSecondInput()) {
            mInitialView.etContNoSecond.setEnabled(false);
        }
        // 封号二是否可填
        if (!getDriverOrderNodeDetailWrapper().isSealNoSecondInput()) {
            mInitialView.etSealNoSecond.setEnabled(false);
        }

        // Amount 1, 2 1个柜子或者2个柜子来判断可见
        if (getDriverOrderNodeDetailWrapper().getAmount() == 1) {
            mInitialView.llSecondNo2.setVisibility(View.GONE);
            mInitialView.rlContNoSecond.setVisibility(View.GONE);
            mInitialView.rlSealNoSecond.setVisibility(View.GONE);
        }

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
    public EditText getEtRemark() {
        return mInitialView.etRemark;
    }

    @Override
    protected TextView getTvNumber() {
        return mInitialView.tvNumber;
    }


    @Override
    public void success(String result) {
        if (!TextUtils.isEmpty(result)) {
            showToast(result);
            setFragmentResult(TO_NEXT_NOTE, null);
            pop();
        }
    }

    /**
     * 节点提交错误提示
     * @param strError  提示错误内容
     */
    @Override
    public void showContErrorDialog(String strError) {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        mDialogHelper = new DialogHelper();
        mDialogHelper.showConfirmDialog(getContext(), strError, "按确定提交", "确定", "取消", new CommonDialog.Listener() {
            @Override
            public void ok() {
                toNextNode(true);
            }

            @Override
            public void cancel() {

            }
        });
    }

    /**
     *  提交节点
     * @param allowNext 是否允许提交
     */
    private void toNextNode(boolean allowNext) {
        setSuperNodesBean();
        //备注
        getDriverOrderNodeDetail().setRemark(mInitialView.etRemark.getText().toString());
        //柜号柜号
        getDriverOrderNodeDetail().setContNoFirst(mInitialView.etContNoFirst.getText().toString().toUpperCase());
        getDriverOrderNodeDetail().setContNoSecond(mInitialView.etContNoSecond.getText().toString().toUpperCase());
        getDriverOrderNodeDetail().setSealNoFirst(mInitialView.etSealNoFirst.getText().toString().toUpperCase());
        getDriverOrderNodeDetail().setSealNoSecond(mInitialView.etSealNoSecond.getText().toString().toUpperCase());
        // 设置 已完成整柜装货/卸货
        getDriverOrderNodeDetail().setContNoFirst(mInitialView.etContNoFirst.getText().toString());
        // 用于 判断柜号1封号1是否有改变
        String tmpContFirst = getDriverOrderNodeDetail().getContNoFirst();
        String tmpSealFirst = getDriverOrderNodeDetail().getSealNoFirst();
        String tmpContTwo = getDriverOrderNodeDetail().getContNoFirst();
        String tmpSealTwo = getDriverOrderNodeDetail().getSealNoFirst();

        // 检测非空
        if (!checkCommit()) return;

        // 是否必须上传一项
        if (getDriverOrderNodeDetailWrapper().isPhotoRequired() &&
                TextUtils.isEmpty(getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getPicture())
                && TextUtils.isEmpty(getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getVideoUrl())) {
            Toast.makeText(getContext(), "拍照或视频，必须选一样", Toast.LENGTH_SHORT).show();
            return;
        }

        if (allowNext) {    // 是否允许提交,如果允许，就直接提交
            mDOrderNodeRichTextPresenter.commitNode(getDriverOrderNodeDetailWrapper(), tmpContFirst, tmpSealFirst, tmpContTwo, tmpSealTwo);
        } else if (getDriverOrderNodeDetail().getNodeName().contains("提柜完成")) {   // 只有提柜完成只验证柜号一，柜号二的正确性
            mDOrderNodeRichTextPresenter.setNodeNext(getDriverOrderNodeDetailWrapper(), tmpContFirst, tmpSealFirst, tmpContTwo, tmpSealTwo);
        } else {    // 直接提交
            mDOrderNodeRichTextPresenter.commitNode(getDriverOrderNodeDetailWrapper(), tmpContFirst, tmpSealFirst, tmpContTwo, tmpSealTwo);
        }
    }

    private boolean checkCommit() {
        //判断是否能为空
        if (getDriverOrderNodeDetailWrapper().isContNoFirstRequired()
                && TextUtils.isEmpty(getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getContNoFirst())) {
            showToast("柜号一不能为空");
            return false;
        }
        if (getDriverOrderNodeDetailWrapper().getAmount() > 1
                && getDriverOrderNodeDetailWrapper().isContNoSecondRequired()
                && TextUtils.isEmpty(getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getContNoSecond())) {
            showToast( "柜号二不能为空");
            return false;
        }
        if (getDriverOrderNodeDetailWrapper().isSealNoFirstRequired()
                && TextUtils.isEmpty(getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getSealNoFirst())) {
            showToast( "封号一不能为空");
            return false;
        }
        if (getDriverOrderNodeDetailWrapper().getAmount() > 1
                && getDriverOrderNodeDetailWrapper().isSealNoSecondRequired()
                && TextUtils.isEmpty(getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getSealNoSecond())) {
            showToast( "封号二不能为空");
            return false;
        }

        //不能重复
        if (getDriverOrderNodeDetailWrapper().getAmount() > 1
                && !getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getContNoSecond().trim().equals("")
                && !getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getContNoFirst().trim().equals("")
                && getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getContNoSecond().trim().
                equals(getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getContNoFirst().trim())) {
            showToast( "柜号1跟柜号2不能一样");
            return false;
        }

        // 是否必须上传拍照或视频一项
        if (getDriverOrderNodeDetailWrapper().isPhotoRequired() &&
                TextUtils.isEmpty(getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getPicture())
                && TextUtils.isEmpty(getDriverOrderNodeDetailWrapper().getDriverOrderNodeDetail().getVideoUrl())) {
            showToast("拍照或视频，必须选一样");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        if (mInitialView == null) return;
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        super.onDestroyView();
        mDOrderNodeRichTextPresenter.unSubscribe();
        mInitialView.unbinder.unbind();
    }

    @Override
    public BasePresenter getPresenter() {
        return mDOrderNodeRichTextPresenter;
    }

    public class InitialView {

        @BindView(R.id.toolbar_title)
        TextView toolbarTitle;
        @BindView(R.id.toolbar)
        Toolbar toolbar;
        @BindView(R.id.textView3)
        TextView textView3;
        @BindView(R.id.etContNoFirst)
        EditText etContNoFirst;
        @BindView(R.id.rlContNoFirst)
        LinearLayout rlContNoFirst;
        @BindView(R.id.etSealNoFirst)
        EditText etSealNoFirst;
        @BindView(R.id.rlSealNoFirst)
        LinearLayout rlSealNoFirst;
        @BindView(R.id.flSelectFirstLine)
        FrameLayout flSelectFirstLine;
        @BindView(R.id.cbSelectFirst)
        CheckBox cbSelectFirst;
        @BindView(R.id.llCbNo1)
        LinearLayout llCbNo1;
        @BindView(R.id.flLineSecond)
        FrameLayout flLineSecond;
        @BindView(R.id.textView2)
        TextView textView2;
        @BindView(R.id.etContNoSecond)
        EditText etContNoSecond;
        @BindView(R.id.rlContNoSecond)
        LinearLayout rlContNoSecond;
        @BindView(R.id.flSealNoSecondLine)
        FrameLayout flSealNoSecondLine;
        @BindView(R.id.etSealNoSecond)
        EditText etSealNoSecond;
        @BindView(R.id.rlSealNoSecond)
        LinearLayout rlSealNoSecond;
        @BindView(R.id.flSelectSecondLine)
        FrameLayout flSelectSecondLine;
        @BindView(R.id.cbSelectSecond)
        CheckBox cbSelectSecond;
        @BindView(R.id.llCbNo2)
        LinearLayout llCbNo2;
        @BindView(R.id.llSecondNo2)
        LinearLayout llSecondNo2;
        @BindView(R.id.llContSeal)
        LinearLayout llContSeal;
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
        @BindView(R.id.etRemark_line)
        FrameLayout etRemarkLine;
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
        @BindView(R.id.ivPhotographThree)
        ImageView ibPhotographThree;
        @BindView(R.id.tvAlertUploadThree)
        ImageView tvAlertUploadThree;
        @BindView(R.id.llPic)
        WarpLinearLayout llPic;
        @BindView(R.id.ivVideo)
        ImageView ivVideo;
        @BindView(R.id.ivVideoFlag)
        ImageView ivVideoFlag;
        @BindView(R.id.ibAudio)
        ImageView ivAudio;
        @BindView(R.id.ivAudioFlag)
        ImageView ivAudioFlag;
        @BindView(R.id.ivLocation_line)
        FrameLayout ivLocationLine;
        @BindView(R.id.ivLocation)
        ImageView ivLocation;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.rlLocation)
        LinearLayout rlLocation;
        @BindView(R.id.btnComplete)
        Button btnComplete;
        @BindView(R.id.imgReturn)
        ImageView imgReturn;
        @BindView(R.id.flMain)
        FrameLayout flMain;
        Unbinder unbinder;

        InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);
            initView();
            initListener();
        }

        void initView() {
            etContNoFirst.setTransformationMethod(EditTextUtils.replacementTransformationMethod);
            etSealNoFirst.setTransformationMethod(EditTextUtils.replacementTransformationMethod);
            etContNoSecond.setTransformationMethod(EditTextUtils.replacementTransformationMethod);
            etSealNoSecond.setTransformationMethod(EditTextUtils.replacementTransformationMethod);
            etRemark.setHint("请输入描述一下当前情况...");
        }

        void initListener() {
            btnComplete.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                toNextNode(false);
            });

            imgReturn.setOnClickListener(view -> pop());
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        mDialogHelper = new DialogHelper();
        mDialogHelper.showConfirmDialog(getContext(), "提示", "确定放弃编辑", "是的","我再想想", new CommonDialog.Listener() {
            @Override
            public void ok() {
                mDialogHelper.hideDialog();
                pop();
            }

            @Override
            public void cancel() {

            }
        });
        return true;
    }
}
