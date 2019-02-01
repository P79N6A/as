package com.yaoguang.company.businessorder2.common.distributeleaflets.transferCancelFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.distributeleaflets.transferCancelFragment.adapter.TransferCancelAdapter;
import com.yaoguang.company.databinding.FragmentBusinessTransferCancelListBinding;
import com.yaoguang.datasource.company.CompanyForwardDataSource;
import com.yaoguang.datasource.company.CompanyTruckDataSource;
import com.yaoguang.greendao.entity.company.ViewTransferCancel;
import com.yaoguang.greendao.entity.company.ViewTransferCancelDetail;
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
 * 订单派单管理 - 取消派单
 * Created by zhongjh on 2018/11/18.
 */
public class TransferCancelListFragment extends BaseFragmentDataBind<FragmentBusinessTransferCancelListBinding> {

    private String mId; // 工作单id
    private String mServiceType;// 0:货代 1：拖车
    private CompanyForwardDataSource mCompanyForwardDataSource = new CompanyForwardDataSource();
    private CompanyTruckDataSource mCompanyTruckDataSource = new CompanyTruckDataSource();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private TransferCancelAdapter mTransferCancelAdapter = new TransferCancelAdapter();
    List<ViewTransferCancel> mViewTransferCacels;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mId = args.getString("id");
            mServiceType = args.getString("serviceType");
        }
        super.onCreate(savedInstanceState);
    }

    public static TransferCancelListFragment newInstance(String id,String serviceType) {
        TransferCancelListFragment distributeLeafletsListFragment = new TransferCancelListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("serviceType",serviceType);
        distributeLeafletsListFragment.setArguments(bundle);
        return distributeLeafletsListFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_transfer_cancel_list;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "选择取消派单的对象", -1, null);
        if (mServiceType.equals("0")) {
            mCompanyForwardDataSource.transferCancelView(mId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<List<ViewTransferCancel>>>(mCompositeDisposable, TransferCancelListFragment.this) {

                        @Override
                        public void onSuccess(BaseResponse<List<ViewTransferCancel>> response) {
                            mViewTransferCacels = response.getResult();
                            mTransferCancelAdapter.setList(response.getResult().get(0).getDetails());
                            RecyclerViewUtils.initPage(mDataBinding.rlView, mTransferCancelAdapter, null, getContext(), true);
                        }

                    });
        }else if(mServiceType.equals("1")){
            mCompanyTruckDataSource.transferCancelView(mId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<List<ViewTransferCancel>>>(mCompositeDisposable, TransferCancelListFragment.this) {

                        @Override
                        public void onSuccess(BaseResponse<List<ViewTransferCancel>> response) {
                            mViewTransferCacels = response.getResult();
                            mTransferCancelAdapter.setList(response.getResult().get(0).getDetails());
                            RecyclerViewUtils.initPage(mDataBinding.rlView, mTransferCancelAdapter, null, getContext(), true);
                        }

                    });
        }
    }

    @Override
    public void initListener() {
        mDataBinding.btnComit.setOnClickListener(v -> {
            if (mViewTransferCacels != null) {
                ArrayList<ViewTransferCancelDetail> viewTransferCancelss = new ArrayList<>();
                for (ViewTransferCancelDetail viewTransferCancel : mTransferCancelAdapter.getList()) {
                    if (viewTransferCancel.getIsCheck().equals("1")) {
                        viewTransferCancelss.add(viewTransferCancel);
                    }
                }
                mViewTransferCacels.get(0).setDetails(viewTransferCancelss);
                if (viewTransferCancelss.size() > 0) {
                    if (mServiceType.equals("0")) {
                        mCompanyForwardDataSource.transferCancel((ArrayList<ViewTransferCancel>) mViewTransferCacels)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, TransferCancelListFragment.this) {

                                    @Override
                                    public void onSuccess(BaseResponse<String> response) {
                                        Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_SHORT).show();
                                        TransferCancelListFragment.this.pop();
                                    }

                                });
                    }else if(mServiceType.equals("1")){
                        mCompanyTruckDataSource.transferCancel((ArrayList<ViewTransferCancel>) mViewTransferCacels)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, TransferCancelListFragment.this) {

                                    @Override
                                    public void onSuccess(BaseResponse<String> response) {
                                        Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_SHORT).show();
                                        TransferCancelListFragment.this.pop();
                                    }

                                });
                    }
                }
            }
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
