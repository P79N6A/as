package com.yaoguang.appcommon.phone.node.noderichtext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.databinding.FragmentOrderNodeRichtextBinding;
import com.yaoguang.datasource.driver.OrderNodeDataSource;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeDetail;
import com.yaoguang.lib.appcommon.widget.textview.MyEditText;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AoHaiUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.widget.WarpLinearLayout;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 节点的相关内容补充
 * Created by zhongjh on 2018/5/4.
 */
public class OrderNodeRichTextFragement extends BaseFragmentDataBind<FragmentOrderNodeRichtextBinding> {

    public static final int REQUEST = 1;

    private OrderNodeRichTextUtils mOrderNodeRichTextUtils1;
    private OrderNodeRichTextUtils mOrderNodeRichTextUtils2;

    private ViewHolder mContainerView1;
    private ViewHolder mContainerView2;

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    OrderNodeDataSource mOrderNodeDataSource = new OrderNodeDataSource();

    boolean mIsEdit;

    /**
     * @param nodeId 节点id
     * @param isEdit 是否可编辑
     * @return
     */
    public static OrderNodeRichTextFragement newInstance(String nodeId, boolean isEdit) {
        OrderNodeRichTextFragement nodeFragment = new OrderNodeRichTextFragement();
        Bundle bundle = new Bundle();
        bundle.putString("nodeId", nodeId);
        bundle.putBoolean("isEdit", isEdit);
        nodeFragment.setArguments(bundle);
        return nodeFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_node_richtext;
    }

    @SuppressLint("InflateParams")
    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "动态记录", -1, null);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        String nodeId = null;
        if (getArguments() != null) {
            nodeId = getArguments().getString("nodeId");
            mIsEdit = getArguments().getBoolean("isEdit");
        }

        // 获取节点id，根据节点id返回数据源
        if (nodeId != null) {
            String finalNodeId = nodeId;
            mOrderNodeDataSource.detail(nodeId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<ArrayList<DriverOrderNodeDetail>>>(mCompositeDisposable) {


                        @Override
                        public void onSuccess(BaseResponse<ArrayList<DriverOrderNodeDetail>> response) {
                            if (response.getResult() != null && response.getResult().size() > 0) {
                                // 判断是否有2个货柜
                                if (response.getResult().size() > 1) {
                                    // 2个货柜
                                    mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText("货柜1"));
                                    mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText("货柜2"));

                                    mContainerView1 = new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.view_order_node_richtext, null, false));
                                    mContainerView1.imgContNo.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            Toast.makeText(BaseApplication.getInstance(), "不是标准柜号！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    addListener(mContainerView1);
                                    setEdit(mContainerView1);
                                    mContainerView2 = new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.view_order_node_richtext, null, false));
                                    mContainerView2.imgContNo.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            Toast.makeText(BaseApplication.getInstance(), "不是标准柜号！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    addListener(mContainerView2);
                                    setEdit(mContainerView2);
                                    mDataBinding.llContainer1.addView(mContainerView1.rootView);
                                    mDataBinding.llContainer2.addView(mContainerView2.rootView);

                                    // 默认显示第一个
                                    mDataBinding.llContainer1.setVisibility(View.VISIBLE);

                                    mOrderNodeRichTextUtils1 = new OrderNodeRichTextUtils(mContainerView1, getActivity(), OrderNodeRichTextFragement.this, getContext(), mOrderNodeDataSource, mCompositeDisposable, finalNodeId, 1000);
                                    mOrderNodeRichTextUtils1.setMaxLength(400);
                                    mOrderNodeRichTextUtils2 = new OrderNodeRichTextUtils(mContainerView2, getActivity(), OrderNodeRichTextFragement.this, getContext(), mOrderNodeDataSource, mCompositeDisposable, finalNodeId, 2000);
                                    mOrderNodeRichTextUtils2.setMaxLength(400);
                                    if (response.getResult().get(0) != null) {
                                        mOrderNodeRichTextUtils1.initData(response.getResult().get(0));
                                    }
                                    if (response.getResult().get(1) != null) {
                                        mOrderNodeRichTextUtils2.initData(response.getResult().get(1));
                                    }
                                } else {
                                    // 只有1个货柜
                                    mDataBinding.tabLayout.setVisibility(View.GONE);
                                    mContainerView1 = new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.view_order_node_richtext, null, false));
                                    mDataBinding.llContainer1.addView(mContainerView1.rootView);
                                    addListener(mContainerView1);
                                    setEdit(mContainerView1);
                                    mContainerView1.imgContNo.setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onNoDoubleClick(View v) {
                                            Toast.makeText(BaseApplication.getInstance(), "不是标准柜号！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    // 默认显示第一个
                                    mDataBinding.llContainer1.setVisibility(View.VISIBLE);

                                    mOrderNodeRichTextUtils1 = new OrderNodeRichTextUtils(mContainerView1, getActivity(), OrderNodeRichTextFragement.this, getContext(), mOrderNodeDataSource, mCompositeDisposable, finalNodeId, 1000);
                                    mOrderNodeRichTextUtils1.setMaxLength(400);
                                    if (response.getResult().get(0) != null) {
                                        mOrderNodeRichTextUtils1.initData(response.getResult().get(0));
                                    }

                                }

                                if (mOrderNodeRichTextUtils1 != null) {
                                    mOrderNodeRichTextUtils1.initViewHolderListener();
                                    // 添加完成的事件
                                    mOrderNodeRichTextUtils1.setOnListener(() -> {
                                        if (mOrderNodeRichTextUtils2 == null) {
                                            // 如果只有一个货柜，直接退出
                                            pop();
                                        } else {
//                                            // 如果有两个货柜，则提交完第一个弹出提示，是否提交其余货柜数据
//                                            DialogHelper dialogHelper = new DialogHelper();
//                                            dialogHelper.show(this, "提示", "是否提交其余货柜数据", "确认", "取消", new CommonDialog.Listener() {
//
//                                                @Override
//                                                public void ok(String content) {
//                                                    dialogHelper.hideDialog();
//                                                    onBackPressed();
//                                                }
//
//                                                @Override
//                                                public void cancel() {
//                                                    pop();
//                                                }
//                                            });
                                        }
                                    });
                                }
                                if (mOrderNodeRichTextUtils2 != null)
                                    mOrderNodeRichTextUtils2.initViewHolderListener();

                            }
                        }
                    });
        }
    }

    /**
     * 设置界面不可编辑
     *
     * @param containerView 界面
     */
    private void setEdit(ViewHolder containerView) {
        if (!mIsEdit) {
            containerView.etContNo.setEnabled(false);
            containerView.etSealNo.setEnabled(false);
            containerView.etRemark.setEnabled(false);
        }
    }

    @Override
    public void initListener() {
        mDataBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() != null)
                    if (tab.getText().toString().equals("货柜1")) {
                        mDataBinding.llContainer1.setVisibility(View.VISIBLE);
                        mDataBinding.llContainer2.setVisibility(View.GONE);
                    } else {
                        mDataBinding.llContainer1.setVisibility(View.GONE);
                        mDataBinding.llContainer2.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (mOrderNodeRichTextUtils1 != null)
            mOrderNodeRichTextUtils1.onFragmentResult(requestCode, resultCode, data);
        if (mOrderNodeRichTextUtils2 != null)
            mOrderNodeRichTextUtils2.onFragmentResult(requestCode, resultCode, data);
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mOrderNodeRichTextUtils1 != null)
            mOrderNodeRichTextUtils1.onActivityResult(requestCode, resultCode, data);
        if (mOrderNodeRichTextUtils2 != null)
            mOrderNodeRichTextUtils2.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    /**
     * 给新增的view添加
     */
    private void addListener(ViewHolder viewHolder) {
        // 启用了才设置显示那个警告
        if (viewHolder.etContNo.isEnabled())
            viewHolder.etContNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!AoHaiUtils.checkContNo(s.toString())) {
                        viewHolder.imgContNo.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.imgContNo.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
    }

    public static class ViewHolder {

        public View rootView;
        public TextView tvTitleContNo;
        public ImageView imgContNo;
        public EditText etContNo;
        public TextView tvTitleSealNo;
        public EditText etSealNo;
        public MyEditText etRemark;
        public TextView tvNumber;
        public TextView tvNumberSum;
        public LinearLayout llNumber;
        public TextView tvAlert;
        public LinearLayout llAlert;
        public ImageView ivPhotographOne;
        public ImageView tvAlertUploadOne;
        public FrameLayout flCamera1;
        public ImageView ivPhotographTwo;
        public ImageView tvAlertUploadTwo;
        public FrameLayout flCamera2;
        public ImageView ivPhotographThree;
        public ImageView tvAlertUploadThree;
        public FrameLayout flCamera3;
        public ImageView ivVideo;
        public ImageView ivVideoPlayFlag;
        public ImageView ivVideoFlag;
        public FrameLayout flVideo1;
        public ImageView ibAudio;
        public ImageView ivAudioPlayPlag;
        public ImageView ivAudioFlag;
        public FrameLayout flAudio1;
        public WarpLinearLayout llPic;
        public LinearLayout llUpload;
        public CircularProgressButton cpbSubmit;
        public FrameLayout flMain;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvTitleContNo = (TextView) rootView.findViewById(R.id.tvTitleContNo);
            this.imgContNo = (ImageView) rootView.findViewById(R.id.imgContNo);
            this.etContNo = (EditText) rootView.findViewById(R.id.etContNo);
            this.tvTitleSealNo = (TextView) rootView.findViewById(R.id.tvTitleSealNo);
            this.etSealNo = (EditText) rootView.findViewById(R.id.etSealNo);
            this.etRemark = (MyEditText) rootView.findViewById(R.id.etRemark);
            this.tvNumber = (TextView) rootView.findViewById(R.id.tvNumber);
            this.tvNumberSum = (TextView) rootView.findViewById(R.id.tvNumberSum);
            this.llNumber = (LinearLayout) rootView.findViewById(R.id.llNumber);
            this.tvAlert = (TextView) rootView.findViewById(R.id.tvAlert);
            this.llAlert = (LinearLayout) rootView.findViewById(R.id.llAlert);
            this.ivPhotographOne = (ImageView) rootView.findViewById(R.id.ivPhotographOne);
            this.tvAlertUploadOne = (ImageView) rootView.findViewById(R.id.tvAlertUploadOne);
            this.flCamera1 = (FrameLayout) rootView.findViewById(R.id.flCamera1);
            this.ivPhotographTwo = (ImageView) rootView.findViewById(R.id.ivPhotographTwo);
            this.tvAlertUploadTwo = (ImageView) rootView.findViewById(R.id.tvAlertUploadTwo);
            this.flCamera2 = (FrameLayout) rootView.findViewById(R.id.flCamera2);
            this.ivPhotographThree = (ImageView) rootView.findViewById(R.id.ivPhotographThree);
            this.tvAlertUploadThree = (ImageView) rootView.findViewById(R.id.tvAlertUploadThree);
            this.flCamera3 = (FrameLayout) rootView.findViewById(R.id.flCamera3);
            this.ivVideo = (ImageView) rootView.findViewById(R.id.ivVideo);
            this.ivVideoPlayFlag = (ImageView) rootView.findViewById(R.id.ivVideoPlayFlag);
            this.ivVideoFlag = (ImageView) rootView.findViewById(R.id.ivVideoFlag);
            this.flVideo1 = (FrameLayout) rootView.findViewById(R.id.flVideo1);
            this.ibAudio = (ImageView) rootView.findViewById(R.id.ibAudio);
            this.ivAudioPlayPlag = (ImageView) rootView.findViewById(R.id.ivAudioPlayPlag);
            this.ivAudioFlag = (ImageView) rootView.findViewById(R.id.ivAudioFlag);
            this.flAudio1 = (FrameLayout) rootView.findViewById(R.id.flAudio1);
            this.llPic = (WarpLinearLayout) rootView.findViewById(R.id.llPic);
            this.llUpload = (LinearLayout) rootView.findViewById(R.id.llUpload);
            this.cpbSubmit = (CircularProgressButton) rootView.findViewById(R.id.cpbSubmit);
            this.flMain = (FrameLayout) rootView.findViewById(R.id.flMain);
        }

    }

}
