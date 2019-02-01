package com.yaoguang.company.pricetruck;

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
import android.widget.Toast;

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
import com.yaoguang.company.pricetruck.adapter.PriceTruckAdapter;
import com.yaoguang.greendao.entity.PriceTruckCondition;
import com.yaoguang.interactor.company.pricetruck.PriceTruckContact;
import com.yaoguang.interactor.company.pricetruck.PriceTruckPresenterImpl;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentList;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.common.view.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 拖车报价查询
 * Created by zhongjh on 2017/6/13.
 */
public class PriceTruckFragment extends BaseFragmentList implements PriceTruckContact.PriceTruckView, Toolbar.OnMenuItemClickListener {

    PriceTruckContact.PriceTruckPresenter mPresenter;
    PriceTruckCondition mPriceTruckCondition;
    InitialView mInitialView;


    public static PriceTruckFragment newInstance() {
        return new PriceTruckFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new PriceTruckPresenterImpl(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_price_truck, container, false);
        mInitialView = new InitialView(view);
        mPresenter.subscribe();
        mInitialView.refreshLayout.autoRefresh();
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mPresenter.subscribe();
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
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT:
                    mInitialView.tvValuePort.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION:
                    mInitialView.tvValuePort.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_DEPARTURE:
                    mInitialView.tvValueAddress.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER:
                    mInitialView.tvValueShipper.setText(data.getString("name"));
                    break;
            }
        }
    }

    @Override
    public PriceTruckCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mPriceTruckCondition == null)
                mPriceTruckCondition = new PriceTruckCondition();
            mPriceTruckCondition.setPort(mInitialView.tvValuePort.getText().toString());
            mPriceTruckCondition.setAddress(mInitialView.tvValueAddress.getText().toString());
            mPriceTruckCondition.setShipper(mInitialView.tvValueShipper.getText().toString());
            mPriceTruckCondition.setCont(mInitialView.tvValueCont.getText().toString());
            if (mInitialView.tvValueServiceType.getText().toString().equals(getResources().getString(R.string.loading_goods))) {
                mPriceTruckCondition.setServiceType(0);
            } else if (mInitialView.tvValueServiceType.getText().toString().equals(getResources().getString(R.string.unloading_goods))) {
                mPriceTruckCondition.setServiceType(1);
            } else {
                mPriceTruckCondition.setServiceType(null);
            }
        }
        return mPriceTruckCondition;
    }

    @Override
    public void setConditionView() {
        if (mPriceTruckCondition != null) {
            mInitialView.tvValuePort.setText(mPriceTruckCondition.getPort());
            mInitialView.tvValueAddress.setText(mPriceTruckCondition.getAddress());
            mInitialView.tvValueShipper.setText(mPriceTruckCondition.getShipper());
            mInitialView.tvValueCont.setText(mPriceTruckCondition.getCont());

            if (ObjectUtils.parseString(mPriceTruckCondition.getServiceType()).equals("0")) {
                mInitialView.tvValueServiceType.setText(getResources().getString(R.string.loading_goods));
            } else if (ObjectUtils.parseString(mPriceTruckCondition.getServiceType()).equals("1")) {
                mInitialView.tvValueServiceType.setText(getResources().getString(R.string.unloading_goods));
            } else {
                mPriceTruckCondition.setServiceType(null);
            }
        }
    }

    @Override
    public void fail(String strMessage) {
        Toast.makeText(BaseApplication.getInstance(), strMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSonos(List<String> values) {
        // 初始化柜型
        List<MenuEntity> menuEntities = new ArrayList<>();
        for (String item : values) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.title = item;
            menuEntities.add(menuEntity);
        }

        mInitialView.mSweetSheetSonos = new SweetSheet(mInitialView.flMain);
        mInitialView.mSweetSheetSonos.setMenuList(menuEntities);
        mInitialView.mSweetSheetSonos.setTitle("请选择柜型");
        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
        recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
        mInitialView.mSweetSheetSonos.setDelegate(recyclerViewDelegate);
        mInitialView.mSweetSheetSonos.setBackgroundEffect(new DimEffect(4));
        mInitialView.mSweetSheetSonos.setOnMenuItemClickListener((position, menuEntity1) -> {
            //返回的分别是三个级别的选中位置
            mInitialView.tvValueCont.setText(menuEntity1.title);
            return true;
        });

    }


    @Override
    public void refreshData() {
        mPresenter.refreshData(getCondition(true));
    }

    @Override
    public void loadMoreData() {
        mPresenter.loadMoreData(getCondition(false), mInitialView.priceTruckAdapter.getList().size());
    }

    public class InitialView {

        PriceTruckAdapter priceTruckAdapter;

        SweetSheet mSweetSheetSonos;
        SweetSheet mSweetSheetServiceType;

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
        @BindView(R.id.tvServiceType)
        TextView tvServiceType;
        @BindView(R.id.tvValueServiceType)
        TextView tvValueServiceType;
        @BindView(R.id.rlServiceType)
        RelativeLayout rlServiceType;
        @BindView(R.id.imgServiceType)
        ImageView imgServiceType;
        @BindView(R.id.tvCont)
        TextView tvCont;
        @BindView(R.id.tvValueCont)
        TextView tvValueCont;
        @BindView(R.id.rlCont)
        RelativeLayout rlCont;
        @BindView(R.id.imgCont)
        ImageView imgCont;
        @BindView(R.id.tvPort)
        TextView tvPort;
        @BindView(R.id.tvValuePort)
        TextView tvValuePort;
        @BindView(R.id.rlPort)
        RelativeLayout rlPort;
        @BindView(R.id.imgPort)
        ImageView imgPort;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvValueAddress)
        TextView tvValueAddress;
        @BindView(R.id.rlAddress)
        RelativeLayout rlAddress;
        @BindView(R.id.imgAddress)
        ImageView imgAddress;
        @BindView(R.id.tvShipper)
        TextView tvShipper;
        @BindView(R.id.tvValueShipper)
        TextView tvValueShipper;
        @BindView(R.id.rlShipper)
        RelativeLayout rlShipper;
        @BindView(R.id.imgShipper)
        ImageView imgShipper;
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
        Unbinder unbinder;

        InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);
            initView();
            PriceTruckFragment.this.setRecyclerview(rlView, refreshLayout, loading, priceTruckAdapter);
            initListener();
        }

        void initView() {
            initToolbarNav(toolbar, "拖车报价查询", R.menu.shipschedule, PriceTruckFragment.this);

            //列表初始化
            priceTruckAdapter = new PriceTruckAdapter();
            RecyclerViewUtils.initPage(rlView, priceTruckAdapter, null, getContext(), false);

            //初始化装/卸
            mSweetSheetServiceType = new SweetSheet(flMain);
            mSweetSheetServiceType.setMenuList(R.menu.sheet_service_type);
            mSweetSheetServiceType.setTitle("请选择装/卸");
            RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
            recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(200));
            mSweetSheetServiceType.setDelegate(recyclerViewDelegate);
            mSweetSheetServiceType.setBackgroundEffect(new DimEffect(4));
            mSweetSheetServiceType.setOnMenuItemClickListener((position, menuEntity1) -> {
                //返回的分别是三个级别的选中位置
                mInitialView.tvValueServiceType.setText(menuEntity1.title);
                return true;
            });
        }

        void initListener() {
            PriceTruckFragment.this.initRecyclerviewListener();
            cvTop.setOnTouchListener((v, event) -> true);
            llTop.setOnTouchListener((v, event) -> {
                llTop.setVisibility(View.GONE);
                return false;
            });
            loading.setOnEmptyInflateListener(inflated -> mPresenter.refreshData(getCondition(true)));
            btnComit.setOnClickListener(v -> {
                InputMethodUtil.hideKeyboard(getActivity());
                llTop.setVisibility(View.GONE);
                mInitialView.refreshLayout.autoRefresh();
            });
            btnEmpty.setOnClickListener(v -> {
                tvValueServiceType.setText("");
                tvValueCont.setText("");
                tvValuePort.setText("");
                tvValueAddress.setText("");
                tvValueShipper.setText("");
            });

            // 选择装卸
            rlServiceType.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    InputMethodUtil.hideKeyboard(getActivity());
                    mSweetSheetServiceType.show();
                }
            });

            // 柜型柜量
            rlCont.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    InputMethodUtil.hideKeyboard(getActivity());
                    mSweetSheetSonos.show();
                }
            });

            //码头,就是起运港或者目的港之类的
            rlPort.setOnClickListener(v -> {
                //判断如果是装货，就起运港，如果是卸货，就目的港
                if (tvValueServiceType.getText().equals("")) {
                    Toast.makeText(BaseApplication.getInstance(), "必须先选择装卸类型才可以选择卸货地区！", Toast.LENGTH_LONG).show();
                } else if (tvValueServiceType.getText().equals("装货")) {
                    SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT);
                    startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT));
                } else {
                    SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION);
                    startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION));
                }

            });

            //地点,就是起运地
            rlAddress.setOnClickListener(v -> {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DEPARTURE);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE));
            });
            //托运人
            rlShipper.setOnClickListener(v -> {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER));
            });

            //清空
            imgServiceType.setOnClickListener(v -> tvValueServiceType.setText(""));
            imgCont.setOnClickListener(v -> tvValueCont.setText(""));
            imgPort.setOnClickListener(v -> tvValuePort.setText(""));
            imgAddress.setOnClickListener(v -> tvValueAddress.setText(""));
            imgShipper.setOnClickListener(v -> tvValueShipper.setText(""));

        }
    }


}
