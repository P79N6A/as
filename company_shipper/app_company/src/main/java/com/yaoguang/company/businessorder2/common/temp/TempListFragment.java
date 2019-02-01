package com.yaoguang.company.businessorder2.common.temp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.temp.adapter.TempListAdapter;
import com.yaoguang.company.businessorder2.forwarder.businessmain.BusinessMainFragment;
import com.yaoguang.company.databinding.FragmentBusinessTempBinding;
import com.yaoguang.greendao.entity.company.WebOrderTemplateWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 模板列表
 * Created by zhongjh on 2018/11/13.
 */
public class TempListFragment extends BaseFragmentListConditionDataBind<WebOrderTemplateWrapper, String, TempListAdapter, FragmentBusinessTempBinding>
        implements TempListContact.View {

    private String mServiceType;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private TempListContact.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mServiceType = args.getString("serviceType");
        }
        super.onCreate(savedInstanceState);
    }

    /**
     * 通过列表传递id进去
     *
     * @param serviceType 0-货代，1-拖车
     */
    public static TempListFragment newInstance(String serviceType) {
        TempListFragment tempListFragment = new TempListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("serviceType", serviceType);
        tempListFragment.setArguments(bundle);
        return tempListFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_temp;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "选择新建方式", -1, null);
        mPresenter = new TempListPresenter(this);

        // 根据拖车，货代设置显示还是隐藏装卸货
        switch (mServiceType) {
            case "0":
                mDataBinding.vLoadingOrUnLoading.setVisibility(View.GONE);
                mDataBinding.rlLoadingOrUnLoading.setVisibility(View.GONE);
                break;
            case "1":
                mDataBinding.vLoadingOrUnLoading.setVisibility(View.VISIBLE);
                mDataBinding.rlLoadingOrUnLoading.setVisibility(View.VISIBLE);
                break;
        }

        TempListFragment.this.initSweetSheets(mDataBinding.rlLoadingOrUnLoading.getId(), mDataBinding.flMain, "选择装/拆箱", R.menu.sheet_service_type2, (position, menuEntity) -> {
            mDataBinding.tvValueLoadingOrUnLoading.setText(menuEntity.title.toString());
            return true;
        });

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void initListener() {
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            switch (mServiceType) {
                case "0":
                    pop();
                    start(BusinessMainFragment.newInstance(((WebOrderTemplateWrapper) item).getViewForwardOrder(), mServiceType, mDataBinding.tvValueLoadingOrUnLoading.getText().toString()));
                    break;
                case "1":
                    // 根据拖车，货代 跳转业务列表
                    String servicetype = ((WebOrderTemplateWrapper) item).getTruckBills().getOtherservice() == 0 ? "装货" : "拆箱";
                    pop();
                    start(com.yaoguang.company.businessorder2.truck.businessmain.BusinessMainFragment.newInstance(((WebOrderTemplateWrapper) item).getTruckBills(), mServiceType, servicetype));
                    break;
            }
        });

        // 选择装货/拆箱
        mDataBinding.rlLoadingOrUnLoading.setOnClickListener(v -> showSweetSheets(mDataBinding.rlLoadingOrUnLoading.getId()));

        // 新增
        mDataBinding.rlNew.setOnClickListener(v -> {
            switch (mServiceType) {
                case "0":
                    pop();
                    start(BusinessMainFragment.newInstance("", mServiceType, mDataBinding.tvValueLoadingOrUnLoading.getText().toString()));
                    break;
                case "1":
                    pop();
                    // 如果没有选择装货或者拆箱，不能打开
                    if (!TextUtils.isEmpty(mDataBinding.tvValueLoadingOrUnLoading.getText().toString()))
                        start(com.yaoguang.company.businessorder2.truck.businessmain.BusinessMainFragment.newInstance("", mServiceType, mDataBinding.tvValueLoadingOrUnLoading.getText().toString()));
                    else
                        Toast.makeText(BaseApplication.getInstance(), "需要先选择装/拆箱", Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new TempListAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public String getCondition(boolean isRegain) {
        return mServiceType;
    }

    @Override
    public void setConditionView(String condition) {

    }

}
