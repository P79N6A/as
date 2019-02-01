package com.yaoguang.shipper.sonos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.greendao.entity.AppOwnerSonoCondition;
import com.yaoguang.greendao.entity.AppOwnerSonoWrapper;
import com.yaoguang.lib.appcommon.utils.ProgressDialogHelper;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentList;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.view.LoadingLayout;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.sonos.adapter.SonosAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 货柜查询
 * Created by zhongjh on 2017/6/13.
 */
public class SonosFragment extends BaseFragmentList<AppOwnerSonoWrapper> implements SonosContact.SonosView, Toolbar.OnMenuItemClickListener {

    SonosContact.SonosPresenter mPresenter;
    AppOwnerSonoCondition mCondition;
    InitialView mInitialView;


    public static SonosFragment newInstance() {
        return new SonosFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new SonosPresenterImpl(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sonos, container, false);
        mInitialView = new InitialView(view);
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mPresenter.subscribe();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_ORDER_SONS:
                    mInitialView.etValueCount.setText(data.getString("fullName"));
                    break;
                case PublicSearchInteractorImpl.TYPE_ORDER_NUMBER:
                    mInitialView.etValueOrderId.setText(data.getString("fullName"));
                    break;
            }
        }
    }


    @Override
    public void onDestroy() {
        mInitialView.unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_search) {
            if (mInitialView.llTop.getVisibility() == View.GONE) {
                YoYo.with(Techniques.FadeIn).duration(250).playOn(mInitialView.llTop);
                mInitialView.llTop.setVisibility(View.VISIBLE);
                setConditionView();
            } else {
                YoYo.with(Techniques.FadeOut).duration(250).playOn(mInitialView.llTop);
                mInitialView.llTop.setVisibility(View.GONE);
            }
        }
        return true;
    }

    @Override
    public AppOwnerSonoCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mCondition == null)
                mCondition = new AppOwnerSonoCondition();
            mCondition.setContNo(mInitialView.etValueCount.getText().toString());
            mCondition.setmBlno(mInitialView.etValueOrderId.getText().toString());
            mCondition.setOrderSn(mInitialView.tvValueOrderNo.getText().toString());
//            if (mInitialView.cbForwarder.isChecked()) {
//                mCondition.setType(0);
//            } else if (mInitialView.cbTrailer.isChecked()) {
//                mCondition.setType(1);
//            }
        }
        return mCondition;
    }

    @Override
    public void setConditionView() {
        if (mCondition != null) {
            mInitialView.etValueCount.setText(mCondition.getContNo());
            mInitialView.etValueOrderId.setText(mCondition.getmBlno());
            mInitialView.tvValueOrderNo.setText(mCondition.getOrderSn());
//            if (mCondition.getType() == 0) {
//                mInitialView.cbForwarder.setChecked(true);
//            } else if (mCondition.getType() == 1) {
//                mInitialView.cbTrailer.setChecked(true);
//            }
        }
    }

    @Override
    public void fail(String strMessage) {
        Toast.makeText(BaseApplication.getInstance(), strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mInitialView.progressDialogHelper.showProgressDialog();
    }

    @Override
    public void hideProgress() {
        mInitialView.progressDialogHelper.dismiss();
    }

    @Override
    public void showProgressMBlNos() {
        mInitialView.pbOrderId.setVisibility(View.VISIBLE);
        mInitialView.imgOrderId.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressMBlNos() {
        mInitialView.pbOrderId.setVisibility(View.GONE);
        mInitialView.imgOrderId.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressContNos() {
        mInitialView.pbCont.setVisibility(View.VISIBLE);
        mInitialView.imgCont.setVisibility(View.GONE);
    }


    @Override
    public void hideProgressContNos() {
        mInitialView.pbCont.setVisibility(View.GONE);
        mInitialView.imgCont.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshData() {
        mPresenter.refreshData(getCondition(true));
    }

    @Override
    public void loadMoreData() {
        mPresenter.loadMoreData(getCondition(false), mInitialView.sonosAdapter.getList().size());
    }

    public class InitialView {

        SonosAdapter sonosAdapter;

        ProgressDialogHelper progressDialogHelper;

        @BindView(R.id.imgReturn)
        ImageView imgReturn;
        @BindView(R.id.toolbar_title)
        TextView toolbarTitle;
        @BindView(R.id.toolbar)
        Toolbar toolbar;
        @BindView(R.id.rlView)
        RecyclerView rlView;
        @BindView(R.id.loading)
        LoadingLayout loading;
        @BindView(R.id.refreshLayout)
        SmartRefreshLayout refreshLayout;
        @BindView(R.id.tvForwarder)
        TextView tvForwarder;
        @BindView(R.id.cbForwarder)
        CheckBox cbForwarder;
        @BindView(R.id.tvTrailer)
        TextView tvTrailer;
        @BindView(R.id.cbTrailer)
        CheckBox cbTrailer;
        @BindView(R.id.rlMulitCheck)
        RelativeLayout rlMulitCheck;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.imgXCount)
        ImageView imgXCount;
        @BindView(R.id.etValueCount)
        EditText etValueCount;
        @BindView(R.id.rlCount)
        RelativeLayout rlCount;
        @BindView(R.id.imgCont)
        ImageView imgCont;
        @BindView(R.id.pbCont)
        ProgressBar pbCont;
        @BindView(R.id.tvOrderId)
        TextView tvOrderId;
        @BindView(R.id.imgXOrderId)
        ImageView imgXOrderId;
        @BindView(R.id.etValueOrderId)
        EditText etValueOrderId;
        @BindView(R.id.rlOrderId)
        RelativeLayout rlOrderId;
        @BindView(R.id.imgOrderId)
        ImageView imgOrderId;
        @BindView(R.id.pbOrderId)
        ProgressBar pbOrderId;
        @BindView(R.id.tvOrderNo)
        TextView tvOrderNo;
        @BindView(R.id.tvValueOrderNo)
        EditText tvValueOrderNo;
        @BindView(R.id.rlOrderNo)
        RelativeLayout rlOrderNo;
        @BindView(R.id.imgOrderNo)
        ImageView imgOrderNo;
        @BindView(R.id.btnEmpty)
        Button btnEmpty;
        @BindView(R.id.btnComit)
        Button btnComit;
        @BindView(R.id.cvTop)
        CardView cvTop;
        @BindView(R.id.llTop)
        LinearLayout llTop;
        @BindView(R.id.flMain)
        FrameLayout flMain;
        Unbinder unbinder;


        InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);
            progressDialogHelper = new ProgressDialogHelper(getContext());
            initView();
            SonosFragment.this.setRecyclerview(rlView, refreshLayout, loading, sonosAdapter);
            initListener();
        }

        void initView() {
            initToolbarNav(toolbar, "货物跟踪", R.menu.shipschedule, SonosFragment.this);

            //列表初始化
            sonosAdapter = new SonosAdapter();
            RecyclerViewUtils.initPage(rlView, sonosAdapter, null, getContext(), false);
        }

        void initListener() {
            SonosFragment.this.initRecyclerviewListener();
            cvTop.setOnTouchListener((v, event) -> true);
            llTop.setOnTouchListener((v, event) -> {
                llTop.setVisibility(View.GONE);
                return false;
            });
            btnComit.setOnClickListener(v -> {
                InputMethodUtil.hideKeyboard(getActivity());
                llTop.setVisibility(View.GONE);

//                // 检测是否为空
//                if (TextUtils.isEmpty(etValueCount.getText().toString())) {
//                    Toast.makeText(BaseApplication.getInstance(), "柜号不能为空", Toast.LENGTH_SHORT).show();
//                }
//                if (TextUtils.isEmpty(etValueOrderId.getText().toString())) {
//                    Toast.makeText(BaseApplication.getInstance(), "运单号不能为空", Toast.LENGTH_SHORT).show();
//                }

                refreshLayout.autoRefresh();
            });
            btnEmpty.setOnClickListener(v -> {
                etValueCount.setText("");
                etValueOrderId.setText("");
                tvValueOrderNo.setText("");
//                cbForwarder.setChecked(true);
//                cbTrailer.setChecked(false);
            });

//            // 选择柜号
//            rlCount.setOnClickListener(v -> {
//                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_ORDER_SONS);
//                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_ORDER_SONS));
//            });

//            // 选择运单号
//            rlOrderId.setOnClickListener(v -> {
//                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_ORDER_NUMBER);
//                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_ORDER_NUMBER));
//            });


            //清空
            imgCont.setOnClickListener(v -> etValueCount.setText(""));
            imgOrderId.setOnClickListener(v -> etValueOrderId.setText(""));
            imgOrderNo.setOnClickListener(v -> tvValueOrderNo.setText(""));

        }
    }


}
