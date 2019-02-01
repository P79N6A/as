package com.yaoguang.driver.phone.order.feedback.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.appcommon.multimedia.upload.BaseNodeUploadFragment;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.DriverOrderNodeDetailWrapper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ConvertUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SpanUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.map.location.impl.LocationManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 故障反馈 反馈添加
 * Created by wly on 2017/5/17.
 * <p>
 * update zhongjh
 */
public class OrderFeedBackAddFragment extends BaseNodeUploadFragment implements OrderFeedBackAddContacts.View {
    private static final String ORDERID = "orderId";
    private static final String ORDERNAME = "orderName";
    private static final String NODEID = "nodeid";

    InitialView mInitialView;
    private String mOrderId;
    private String mOrderName;
    OrderFeedBackAddPresenter mOrderFeedBackAddPresenter;
    private String mNodeId;
    private DialogHelper mDialogHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrderId = getArguments().getString(ORDERID);
            mNodeId = getArguments().getString(NODEID);
            mOrderName = getArguments().getString(ORDERNAME);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ORDERID, mOrderId);
        outState.putString(NODEID, mNodeId);
        outState.putString(ORDERNAME, mOrderName);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_feedbacl;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        super.initDataBind(view, inflater);
        mInitialView = new InitialView(view);
    }

    @Override
    public void savedInstanceState(@Nullable Bundle savedInstanceState) {
        super.savedInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mOrderId = savedInstanceState.getString(ORDERID);
            mNodeId = savedInstanceState.getString(NODEID);
            mOrderName = savedInstanceState.getString(ORDERNAME);
        }
    }

    @Override
    public void init() {
        super.init();
        mOrderFeedBackAddPresenter = new OrderFeedBackAddPresenter(this, mOrderId, mOrderName, mNodeId);
        showAddress();
        // 初始化保存的图片视频语音的实体
        setDriverOrderNodeDetailWrapper(new DriverOrderNodeDetailWrapper());
        getDriverOrderNodeDetailWrapper().setDriverOrderNodeDetail(new DriverOrderNodeDetail());

        // 不显示本地上传功能
        setShowLocalChoice(false);
    }

    @Override
    public void onPause() {
        hideKeyboard();
        super.onPause();
    }

    @Override
    protected FrameLayout getFrameView() {
        return mInitialView.flMain;
    }

    /**
     * 实例化
     *
     * @param orderSn   订单id
     * @param nodeId    去掉
     * @param orderName 订单名称
     * @return 实例化
     */
    public static OrderFeedBackAddFragment newInstance(String orderSn, String nodeId, String orderName) {
        OrderFeedBackAddFragment fragment = new OrderFeedBackAddFragment();
        Bundle args = new Bundle();
        args.putString(ORDERID, orderSn);
        args.putString(NODEID, nodeId);
        args.putString(ORDERNAME, orderName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void success(BaseResponse<String> value) {
        Toast.makeText(BaseApplication.getInstance(), value.getResult(), Toast.LENGTH_SHORT).show();
        pop();
    }

    @Override
    public void fail(String message) {
        Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mInitialView == null) return;
        mInitialView.unbinder.unbind();
        mOrderFeedBackAddPresenter.unSubscribe();

        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return mOrderFeedBackAddPresenter;
    }

    @Override
    public EditText getEtRemark() {
        return mInitialView.etRemark;
    }

    @Override
    protected TextView getTvNumber() {
        return mInitialView.tvNumber;
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
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.llTvTitle)
        LinearLayout llTvTitle;
        @BindView(R.id.etRemark)
        EditText etRemark;
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
        @BindView(R.id.btnComplete)
        Button btnComplete;
        @BindView(R.id.flMain)
        FrameLayout flMain;
        Unbinder unbinder;

        InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);

            initView();
            initListener();
        }

        void initView() {
            toolbarTitle.setText("故障反馈");
            toolbarLeft.setOnClickListener(v -> {
                hideKeyboard();
                onBackPressedSupport();
            });
            llTvTitle.setVisibility(View.GONE);
            tvAlert.setText(new SpanUtils().append("您可以拍摄").append("有关故障").setForegroundColor(ContextCompat.getColor(_mActivity, R.color.accent)).append("照片、录音和视频").create());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(150));
            etRemark.setHint("请在此处描述该故障的详细情况");
            etRemark.setLayoutParams(params);
        }

        void initListener() {
            btnComplete.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;
                btnComplete.setEnabled(false);
                // 获取当前地址
                LocationManager locationManager = new LocationManager();
                locationManager.init(BaseApplication.getInstance().getBaseContext());
                locationManager.setComeback(location -> {
                    btnComplete.setEnabled(true);
                    locationManager.destroyLocation();

                    getDriverOrderNodeDetail().setLat(ObjectUtils.parseString(location.getLat()));
                    getDriverOrderNodeDetail().setLon(ObjectUtils.parseString(location.getLon()));
                    getDriverOrderNodeDetail().setAddress(location.getAddress());
                    getDriverOrderNodeDetail().setProvince(location.getProvince());
                    getDriverOrderNodeDetail().setCity(location.getCity());
                    getDriverOrderNodeDetail().setDistrict(location.getDistrict());
                    getDriverOrderNodeDetail().setStreet(location.getStreet());

                    showAddress();
                    setSuperNodesBean();
                    mOrderFeedBackAddPresenter.add(getDriverOrderNodeDetail(), etRemark.getText().toString());
                });


            });
        }
    }


    @Override
    public boolean onBackPressedSupport() {
        if (mDialogHelper == null)
            mDialogHelper = new DialogHelper(getContext(), "提示", "确定放弃编辑", "是的", "我再想想", new CommonDialog.Listener() {
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
