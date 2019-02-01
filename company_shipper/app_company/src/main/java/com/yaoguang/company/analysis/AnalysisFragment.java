package com.yaoguang.company.analysis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.analysis.adapter.PriceAnalysisAdapter;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.interactor.company.analysis.AnalysisContact;
import com.yaoguang.interactor.company.analysis.AnalysisPresenterImpl;
import com.yaoguang.lib.appcommon.utils.ProgressDialogHelper;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentList;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.common.view.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 货代报价查询
 * Created by zhongjh on 2017/6/13.
 */
public class AnalysisFragment extends BaseFragmentList<AppPriceAnalysisWrapper> implements AnalysisContact.AnalysisView, Toolbar.OnMenuItemClickListener {

    AnalysisContact.AnalysisPresenter mAnalysisPresenter;
    PriceAnalysisCondition mPriceAnalysisCondition;
    InitialView mInitialView;

    public static AnalysisFragment newInstance() {
        return new AnalysisFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mAnalysisPresenter = new AnalysisPresenterImpl(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter getPresenter() {
        return mAnalysisPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        mInitialView = new InitialView(view);
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mAnalysisPresenter.subscribe();
        mInitialView.refreshLayout.autoRefresh();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInitialView.unbinder.unbind();
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
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_DEPARTURE:
                    mInitialView.tvValueDockOfLoading.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT:
                    mInitialView.tvValuePlaceLoading.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION:
                    mInitialView.tvValuePlaceDelivery.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_DESTINATION:
                    mInitialView.tvValueFinalDestination.setText(data.getString("name"));
                    break;
            }
        }
    }

    @Override
    public PriceAnalysisCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mPriceAnalysisCondition == null)
                mPriceAnalysisCondition = new PriceAnalysisCondition();
            if (mInitialView != null && mInitialView.tvValueDockOfLoading != null && mInitialView.tvValuePlaceLoading != null && mInitialView.tvValuePlaceDelivery != null &&
                    mInitialView.tvValueFinalDestination != null && mInitialView.tvValueContId != null
                    ) {
                mPriceAnalysisCondition.setDockLoading(mInitialView.tvValueDockOfLoading.getText().toString());
                mPriceAnalysisCondition.setPortLoading(mInitialView.tvValuePlaceLoading.getText().toString());
                mPriceAnalysisCondition.setPortDelivery(mInitialView.tvValuePlaceDelivery.getText().toString());
                mPriceAnalysisCondition.setFinalDestination(mInitialView.tvValueFinalDestination.getText().toString());
                mPriceAnalysisCondition.setContId(mInitialView.tvValueContId.getText().toString());
            }
        }
        return mPriceAnalysisCondition;
    }

    @Override
    public void setConditionView() {
        if (mPriceAnalysisCondition != null) {
            mInitialView.tvValueDockOfLoading.setText(ObjectUtils.parseString(mPriceAnalysisCondition.getDockLoading()));
            mInitialView.tvValuePlaceLoading.setText(ObjectUtils.parseString(mPriceAnalysisCondition.getPortLoading()));
            mInitialView.tvValuePlaceDelivery.setText(ObjectUtils.parseString(mPriceAnalysisCondition.getPortDelivery()));
            mInitialView.tvValueFinalDestination.setText(ObjectUtils.parseString(mPriceAnalysisCondition.getFinalDestination()));
            mInitialView.tvValueContId.setText(ObjectUtils.parseString(mPriceAnalysisCondition.getContId()));
        }
    }

    @Override
    public void setSonos(List<String> values) {
        List<MenuEntity> menuEntities = new ArrayList<>();
        for (String item : values) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.title = item;
            menuEntities.add(menuEntity);
        }

        mInitialView.mSweetSheet = new SweetSheet(mInitialView.flMain);
        mInitialView.mSweetSheet.setMenuList(menuEntities);
        mInitialView.mSweetSheet.setTitle("请选择柜型");
        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
        recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
        mInitialView.mSweetSheet.setDelegate(recyclerViewDelegate);
        mInitialView.mSweetSheet.setBackgroundEffect(new DimEffect(4));
        mInitialView.mSweetSheet.setOnMenuItemClickListener((position, menuEntity1) -> {
            //返回的分别是三个级别的选中位置
            mInitialView.tvValueContId.setText(menuEntity1.title);
            return true;
        });
        InputMethodUtil.hideKeyboard(getActivity());
    }


    @Override
    public void refreshData() {
        mAnalysisPresenter.refreshData(getCondition(true));
    }

    @Override
    public void loadMoreData() {
        mAnalysisPresenter.loadMoreData(getCondition(false), mInitialView.analysisAdapter.getList().size());
    }

    public class InitialView {

        PriceAnalysisAdapter analysisAdapter;

        ProgressDialogHelper progressDialogHelper;

        SweetSheet mSweetSheet;

        Unbinder unbinder;

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
        @BindView(R.id.tvDockOfLoading)
        TextView tvDockOfLoading;
        @BindView(R.id.tvValueDockOfLoading)
        TextView tvValueDockOfLoading;
        @BindView(R.id.rlDockOfLoading)
        RelativeLayout rlDockOfLoading;
        @BindView(R.id.imgDockOfLoading)
        ImageView imgDockOfLoading;
        @BindView(R.id.tvPlaceLoading)
        TextView tvPlaceLoading;
        @BindView(R.id.tvValuePlaceLoading)
        TextView tvValuePlaceLoading;
        @BindView(R.id.rlPlaceLoading)
        RelativeLayout rlPlaceLoading;
        @BindView(R.id.imgPlaceLoading)
        ImageView imgPlaceLoading;
        @BindView(R.id.tvPlaceDelivery)
        TextView tvPlaceDelivery;
        @BindView(R.id.tvValuePlaceDelivery)
        TextView tvValuePlaceDelivery;
        @BindView(R.id.rlPlaceDelivery)
        RelativeLayout rlPlaceDelivery;
        @BindView(R.id.imgPlaceDelivery)
        ImageView imgPlaceDelivery;
        @BindView(R.id.tvFinalDestination)
        TextView tvFinalDestination;
        @BindView(R.id.tvValueFinalDestination)
        TextView tvValueFinalDestination;
        @BindView(R.id.rlFinalDestination)
        RelativeLayout rlFinalDestination;
        @BindView(R.id.imgFinalDestination)
        ImageView imgFinalDestination;
        @BindView(R.id.tvContId)
        TextView tvContId;
        @BindView(R.id.tvValueContId)
        TextView tvValueContId;
        @BindView(R.id.rlContId)
        RelativeLayout rlContId;
        @BindView(R.id.imgContId)
        ImageView imgContId;
        @BindView(R.id.btnEmpty)
        Button btnEmpty;
        @BindView(R.id.btnComit)
        Button btnComit;
        @BindView(R.id.cvTop)
        CardView cvTop;
        @BindView(R.id.llTop)
        LinearLayout llTop;
        @BindView(R.id.flRecyclerView)
        FrameLayout flRecyclerView;
        @BindView(R.id.flMain)
        FrameLayout flMain;

        InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);
            progressDialogHelper = new ProgressDialogHelper(getContext());
            initView();
            AnalysisFragment.this.setRecyclerview(rlView, refreshLayout, loading, analysisAdapter);
            initListener();
        }

        void initView() {
            initToolbarNav(toolbar, "货代报价查询", R.menu.shipschedule, AnalysisFragment.this);
            //列表初始化
            analysisAdapter = new PriceAnalysisAdapter();
            RecyclerViewUtils.initPage(rlView, analysisAdapter, null, getContext(), false);
        }

        void initListener() {
            AnalysisFragment.this.initRecyclerviewListener();
            cvTop.setOnTouchListener((v, event) -> true);
            llTop.setOnTouchListener((v, event) -> {
                llTop.setVisibility(View.GONE);
                return true;
            });
            btnComit.setOnClickListener(v -> {
                InputMethodUtil.hideKeyboard(getActivity());
                llTop.setVisibility(View.GONE);
                mInitialView.refreshLayout.autoRefresh();
            });
            btnEmpty.setOnClickListener(v -> {
                tvValueDockOfLoading.setText("");
                tvValuePlaceLoading.setText("");
                tvValuePlaceDelivery.setText("");
                tvValueFinalDestination.setText("");
                tvValueContId.setText("");
            });

            rlDockOfLoading.setOnClickListener(v -> {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DEPARTURE);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE));
            });
            rlPlaceLoading.setOnClickListener(v -> {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT));
            });
            rlPlaceDelivery.setOnClickListener(v -> {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION));
            });
            rlFinalDestination.setOnClickListener(v -> {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DESTINATION);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DESTINATION));
            });
            rlContId.setOnClickListener(v -> mSweetSheet.show());

            imgDockOfLoading.setOnClickListener(v -> tvValueDockOfLoading.setText(""));
            imgPlaceLoading.setOnClickListener(v -> tvValuePlaceLoading.setText(""));
            imgPlaceDelivery.setOnClickListener(v -> tvValuePlaceDelivery.setText(""));
            imgContId.setOnClickListener(v -> tvValueContId.setText(""));
            imgFinalDestination.setOnClickListener(v -> tvValueFinalDestination.setText(""));
        }
    }

}
