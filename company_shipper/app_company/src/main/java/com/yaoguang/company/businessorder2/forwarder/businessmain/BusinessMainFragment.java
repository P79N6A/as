package com.yaoguang.company.businessorder2.forwarder.businessmain;

import android.annotation.SuppressLint;
import android.view.MenuItem;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.businessmain.BaseBusinessMainFragment;
import com.yaoguang.company.businessorder2.common.cost.list.CostListFragment;
import com.yaoguang.company.businessorder2.common.distributeleaflets.DistributeLeafletsListFragment;
import com.yaoguang.company.businessorder2.common.distributeleaflets.transferCancelFragment.TransferCancelListFragment;
import com.yaoguang.greendao.entity.common.ViewForwardOrder;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * 这是包含了business和动态
 * Created by zhongjh on 2018/11/9.
 */
@SuppressLint("ValidFragment")
public class BusinessMainFragment extends BaseBusinessMainFragment<ViewForwardOrder> {


    @Override
    public Observable<BaseResponse<ViewForwardOrder>> detail(String id) {
        return mCompanyOrderDataSource.detail(mID);
    }

    @SuppressLint("ValidFragment")
    public BusinessMainFragment(String id, String serviceType, String otherService) {
        super(id, serviceType, otherService);
    }

    @SuppressLint("ValidFragment")
    public BusinessMainFragment(ViewForwardOrder viewForwardOrder, String serviceType, String otherService) {
        super(viewForwardOrder, serviceType, otherService);
    }

    public static BusinessMainFragment newInstance(String id, String serviceType, String otherService) {
        return new BusinessMainFragment(id, serviceType, otherService);
    }

    public static BusinessMainFragment newInstance(ViewForwardOrder viewForwardOrder, String serviceType, String otherService) {
        return new BusinessMainFragment(viewForwardOrder, serviceType, otherService);
    }

    @Override
    public void init() {
        super.init();
        initSweetSheets(R.id.action_distribute, mDataBinding.flBusinessMain, "派单操作", R.menu.sheet_business_forwarder_distribute, new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity) {
                switch (position) {
                    case 0:
                        start(DistributeLeafletsListFragment.newInstance("0", t.getFreightBills().getId(),mServiceType));
                        dismissSweetSheets(R.id.action_distribute);
                        break;
                    case 1:
                        start(DistributeLeafletsListFragment.newInstance("1", t.getFreightBills().getId(),mServiceType));
                        dismissSweetSheets(R.id.action_distribute);
                        break;
                    case 2:
                        start(DistributeLeafletsListFragment.newInstance("2", t.getFreightBills().getId(),mServiceType));
                        dismissSweetSheets(R.id.action_distribute);
                        break;
                    case 3:
                        start(TransferCancelListFragment.newInstance(t.getFreightBills().getId(),mServiceType));
                        dismissSweetSheets(R.id.action_distribute);
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
                start(CostListFragment.newInstance(t.getFreightBills().getId(), mServiceType, ObjectUtils.parseString(mBaseBusinessMainAdapter.mBusinessOrderFragment.mDataBinding.llContainer.getChildCount())));
                break;
            case R.id.action_distribute:
                // 派单
                showSweetSheets(R.id.action_distribute);
                break;
        }
        return false;
    }

}
