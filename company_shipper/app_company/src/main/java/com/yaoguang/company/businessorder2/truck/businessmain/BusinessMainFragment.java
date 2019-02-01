package com.yaoguang.company.businessorder2.truck.businessmain;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.widget.Toast;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.businessmain.BaseBusinessMainFragment;
import com.yaoguang.company.businessorder2.common.cost.list.CostListFragment;
import com.yaoguang.company.businessorder2.common.distributeleaflets.DistributeLeafletsListFragment;
import com.yaoguang.company.businessorder2.common.distributeleaflets.transferCancelFragment.TransferCancelListFragment;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 这是包含了business和动态
 * Created by zhongjh on 2018/11/9.
 */
@SuppressLint("ValidFragment")
public class BusinessMainFragment extends BaseBusinessMainFragment<TruckBills> {

    @Override
    public Observable<BaseResponse<TruckBills>> detail(String id) {
        return mCompanyOrderDataSource.truckDetail(mID);
    }

    @SuppressLint("ValidFragment")
    public BusinessMainFragment(String id, String serviceType, String otherService) {
        super(id, serviceType, otherService);
    }

    @SuppressLint("ValidFragment")
    public BusinessMainFragment(TruckBills truckBills, String serviceType, String otherService) {
        super(truckBills, serviceType, otherService);
    }

    public static BusinessMainFragment newInstance(String id, String serviceType, String otherService) {
        return new BusinessMainFragment(id, serviceType, otherService);
    }

    public static BusinessMainFragment newInstance(TruckBills truckBills, String serviceType, String otherService) {
        return new BusinessMainFragment(truckBills, serviceType, otherService);
    }

    @Override
    public void init() {
        super.init();
        initSweetSheets(R.id.action_distribute, mDataBinding.flBusinessMain, "派单操作", R.menu.sheet_business_truck_distribute, new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity) {
                switch (position) {
                    case 0:
                        start(DistributeLeafletsListFragment.newInstance("3", t.getId(), mServiceType));
                        break;
                    case 1:
                        start(TransferCancelListFragment.newInstance(t.getId(), mServiceType));
                        break;
                    case 2:
                        // 司机app直接派单
                        mCompanyOrderDataSource.sendToDriver(t.getId())
                         .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, BusinessMainFragment.this) {

                                @Override
                                public void onSuccess(BaseResponse<String> response) {
                                    Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_SHORT).show();
                                    pop();
                                    // 重新打开
                                    start(BusinessMainFragment.newInstance(mID, "1", mOtherService));
                                }

                            });
                        break;
                    case 3:
                        // 取消司机app派单
                        mCompanyOrderDataSource.cancelDriverOrder(t.getDriverOrderId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, BusinessMainFragment.this) {

                                    @Override
                                    public void onSuccess(BaseResponse<String> response) {
                                        Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_SHORT).show();
                                    }

                                });
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_copy:
                // 拷加该订单
                pop();
                start(BusinessMainFragment.newInstance(t, mServiceType, mOtherService));
                break;
            case R.id.action_cost:
                // 费用
                start(CostListFragment.newInstance(t.getOrderSn(), mServiceType, ObjectUtils.parseString(mBaseBusinessMainAdapter.mBusinessOrderFragmentTruck.mDataBinding.llContainer.getChildCount())));
                break;
            case R.id.action_distribute:
                // 派单
                showSweetSheets(R.id.action_distribute);
                break;
        }
        return false;
    }


}
