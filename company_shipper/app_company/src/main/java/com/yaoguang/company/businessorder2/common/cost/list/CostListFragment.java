package com.yaoguang.company.businessorder2.common.cost.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.cost.detail.CostDetailFragment;
import com.yaoguang.company.businessorder2.common.cost.list.adapter.CostListAdapter;
import com.yaoguang.company.databinding.FragmentBusinessCostListBinding;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.AccountFee;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.OldNewAccountFeeWrapper;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/11/13.
 */
public class CostListFragment extends BaseFragmentDataBind<FragmentBusinessCostListBinding> implements Toolbar.OnMenuItemClickListener {

    private final static int REQUEST_ADD = 1;
    private final static int REQUEST_UPDATE = 2;
    CostListAdapter mCostListAdapter;
    CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    ArrayList<AccountFee> oldFees = new ArrayList<>();// 旧数据，用于更新的

    private String mID;

    private String mNumberValue; // 数量

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mID = args.getString("id");
            mNumberValue = args.getString("numberValue");
        }
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    /**
     * @param id          工作单id
     * @param serviceType 0-货代，1-拖车
     */
    public static CostListFragment newInstance(String id, String serviceType, String numberValue) {
        CostListFragment costListFragment = new CostListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("serviceType", serviceType);
        bundle.putString("numberValue", numberValue);
        costListFragment.setArguments(bundle);
        return costListFragment;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_add:
                startForResult(CostDetailFragment.newInstance(null, -1, getArguments().getString("serviceType"), mNumberValue), REQUEST_ADD);
                break;
        }
        return false;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD:
                    AccountFee accountFee = data.getParcelable("accountFee");
                    mCostListAdapter.add(mCostListAdapter.getItemCount(), accountFee);
                    break;
                case REQUEST_UPDATE:
                    AccountFee accountFeeU = data.getParcelable("accountFee");
                    int position = data.getInt("position");
                    if (accountFeeU == null) {
                        // 删除
                        mCostListAdapter.removeItem(position);
                    } else {
                        mCostListAdapter.updateItem(position, accountFeeU);
                    }
                    break;
            }
            // 提交服务器
            OldNewAccountFeeWrapper oldNewAccountFeeWrapper = new OldNewAccountFeeWrapper();
            oldNewAccountFeeWrapper.setOldFees(oldFees);
            oldNewAccountFeeWrapper.setNewFees(mCostListAdapter.getList());
            oldNewAccountFeeWrapper.setBillsId(mID);
            for (AccountFee accountFee : oldFees) {
                accountFee.setBillsId(mID);
            }
            for (AccountFee accountFee : mCostListAdapter.getList()) {
                accountFee.setBillsId(mID);
            }
            mCompanyOrderDataSource.feeSave(oldNewAccountFeeWrapper)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, this) {

                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_SHORT).show();
                            // 旧数据进行更新
                            oldFees.clear();
                            Gson gson = new Gson();
                            for (AccountFee accountFee : mCostListAdapter.getList()) {
                                String json = gson.toJson(accountFee);
                                oldFees.add(gson.fromJson(json, AccountFee.class));
                            }
                            initData();
                        }

                    });
        }
        super.onFragmentResult(requestCode, resultCode, data);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_cost_list;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "费用", R.menu.menu_cost_list, this);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        initData();
    }

    @Override
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mCompanyOrderDataSource.feeList(getArguments().getString("id"), getArguments().getString("serviceType"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<AccountFee>>>(mCompositeDisposable, this) {

                    @Override
                    public void onSuccess(BaseResponse<List<AccountFee>> response) {
                        mCostListAdapter = new CostListAdapter();
                        mCostListAdapter.setOnItemClickListener((itemView, item, position) -> {
                            // 跳转详情
                            startForResult(CostDetailFragment.newInstance((AccountFee) item, position, getArguments().getString("serviceType"), mNumberValue), REQUEST_UPDATE);
                        });
                        mCostListAdapter.setList(response.getResult());

                        // 应付合计
                        double accoAttrTotalup1 = 0;
                        // 应收合计
                        double accoAttrTotalup0 = 0;
                        Gson gson = new Gson();
                        for (AccountFee accountFee : response.getResult()) {
                            if (accountFee.getAccoAttr() == 0) {
                                // 应收
                                accoAttrTotalup0 += accountFee.getTotalup();
                            } else {
                                // 应付
                                accoAttrTotalup1 += accountFee.getTotalup();
                            }
                            String json = gson.toJson(accountFee);
                            oldFees.add(gson.fromJson(json, AccountFee.class));
                        }

                        mDataBinding.tvTitle1.setText("应付合计  ￥"+accoAttrTotalup1 + "       应收合计  ￥" + accoAttrTotalup0 );
                        mDataBinding.tvTitle2.setText("毛利合计  ￥" + (accoAttrTotalup0 - accoAttrTotalup1));
                        RecyclerViewUtils.initPage(mDataBinding.rlView, mCostListAdapter, null, getContext(), false);
                    }

                });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
