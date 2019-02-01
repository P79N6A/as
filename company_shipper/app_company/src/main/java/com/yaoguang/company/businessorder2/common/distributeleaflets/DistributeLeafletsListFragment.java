package com.yaoguang.company.businessorder2.common.distributeleaflets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.distributeleaflets.adapter.DistributeLeafletsAdapter;
import com.yaoguang.company.databinding.FragmentBusinessDistributeLeafletsListBinding;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.datasource.company.CompanyForwardDataSource;
import com.yaoguang.datasource.company.CompanyTruckDataSource;
import com.yaoguang.greendao.entity.company.InfoSendOrderTemp;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 派单模板 type 0：货代 1：装货 2：送货 3：拖车
 * Created by zhongjh on 2018/11/18.
 */
public class DistributeLeafletsListFragment extends BaseFragmentDataBind<FragmentBusinessDistributeLeafletsListBinding> {

    private String mType;
    private String mId; // 工作单id
    private String mServiceType;// 0:货代 1：拖车
    private CompanyBaseInfoDataSource mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();
    private CompanyForwardDataSource mCompanyForwardDataSource = new CompanyForwardDataSource();
    private CompanyTruckDataSource mCompanyTruckDataSource = new CompanyTruckDataSource();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private InfoSendOrderTemp mInfoSendOrderTemp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getString("type");
            mId = args.getString("id");
            mServiceType = args.getString("serviceType");
        }
        super.onCreate(savedInstanceState);
    }

    public static DistributeLeafletsListFragment newInstance(String type, String id,String serviceType) {
        DistributeLeafletsListFragment distributeLeafletsListFragment = new DistributeLeafletsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
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
        return R.layout.fragment_business_distribute_leaflets_list;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "派单操作", -1, null);
        mCompanyBaseInfoDataSource.transferTempList(mType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoSendOrderTemp>>>(mCompositeDisposable, DistributeLeafletsListFragment.this) {

                    @Override
                    public void onSuccess(BaseResponse<List<InfoSendOrderTemp>> response) {
                        DistributeLeafletsAdapter distributeLeafletsAdapter = new DistributeLeafletsAdapter();
                        distributeLeafletsAdapter.setList(response.getResult());
                        distributeLeafletsAdapter.setOnItemClickListener((itemView, item, position) -> {
                            // 做记录，后面点确定的时候，可以ok
                            mInfoSendOrderTemp = (InfoSendOrderTemp) item;
                        });
                        RecyclerViewUtils.initPage(mDataBinding.rlView, distributeLeafletsAdapter, null, getContext(), true);
                    }

                });
    }

    @Override
    public void initListener() {
        mDataBinding.btnComit.setOnClickListener(v -> {
            if (mInfoSendOrderTemp != null) {
                if (mServiceType.equals("0")) {
                    mCompanyForwardDataSource.transfer(mId, ObjectUtils.parseString(mInfoSendOrderTemp.getId()), mType)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, DistributeLeafletsListFragment.this) {

                                @Override
                                public void onSuccess(BaseResponse<String> response) {
                                    Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_SHORT).show();
                                    DistributeLeafletsListFragment.this.pop();
                                }

                            });
                }else if(mServiceType.equals("1")){
                    mCompanyTruckDataSource.transfer(mId, ObjectUtils.parseString(mInfoSendOrderTemp.getId()), mType)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, DistributeLeafletsListFragment.this) {

                                @Override
                                public void onSuccess(BaseResponse<String> response) {
                                    Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_SHORT).show();
                                    DistributeLeafletsListFragment.this.pop();
                                }

                            });
                }
            }
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
