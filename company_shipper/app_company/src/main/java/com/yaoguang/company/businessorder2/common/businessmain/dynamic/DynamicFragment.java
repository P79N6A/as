package com.yaoguang.company.businessorder2.common.businessmain.dynamic;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.businessmain.dynamic.adapter.DynamicAdapter;
import com.yaoguang.company.businessorder2.common.businessmain.dynamic.event.InitEvent;
import com.yaoguang.company.databinding.FragmentBusinessDynamicBinding;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by zhongjh on 2018/11/10.
 */
public class DynamicFragment extends BaseFragmentDataBind<FragmentBusinessDynamicBinding> {

    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_dynamic;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onInitEvent(InitEvent initEvent) {
        if (initEvent.getLogBillsTrack() != null) {
            DynamicAdapter dynamicAdapter = new DynamicAdapter(getContext());
            ArrayList<String[]> arrayList = new ArrayList<>();
            // 判断是货代还是拖车
            if (initEvent.getBillType().equals("0")) {
                arrayList.add(new String[]{"装货办单", initEvent.getLogBillsTrack().getBdDate()});
                arrayList.add(new String[]{"转货完成", initEvent.getLogBillsTrack().getLoadOverDate()});
                arrayList.add(new String[]{"核柜", initEvent.getLogBillsTrack().getComDate()});
                arrayList.add(new String[]{"报柜号/配船", initEvent.getLogBillsTrack().getMarketCheckDate()});
                arrayList.add(new String[]{"开船", initEvent.getLogBillsTrack().getFirstEtd()});
                arrayList.add(new String[]{"运单回传", initEvent.getLogBillsTrack().getBillRegisterDate()});
                arrayList.add(new String[]{"船到", initEvent.getLogBillsTrack().getFirstEta()});
                arrayList.add(new String[]{"送货办单", initEvent.getLogBillsTrack().getBdDestDate()});
                arrayList.add(new String[]{"装货完成", initEvent.getLogBillsTrack().getDeliveryOverDate()});
            }else if (initEvent.getBillType().equals("1")){
                // 判断是装货还是卸货
                if (initEvent.getOtherService().equals("0")){
                }else if(initEvent.getOtherService().equals("1")){
                    arrayList.add(new String[]{"船到", initEvent.getLogBillsTrack().getFirstEta()});
                }
                arrayList.add(new String[]{"办单", initEvent.getLogBillsTrack().getBdDate()});
                arrayList.add(new String[]{"码头打单", initEvent.getLogBillsTrack().getMtddDate()});
                arrayList.add(new String[]{"派车", initEvent.getLogBillsTrack().getTruckPlanTime()});
                arrayList.add(new String[]{"司机确认", initEvent.getLogBillsTrack().getConfirmDate()});
                arrayList.add(new String[]{"提柜时间", initEvent.getLogBillsTrack().getCarryDate()});
                arrayList.add(new String[]{"装送货", initEvent.getLogBillsTrack().getRelivePlanTime()});
                arrayList.add(new String[]{"开船", initEvent.getLogBillsTrack().getFirstEtd()});
                arrayList.add(new String[]{"回单", initEvent.getLogBillsTrack().getLastLoadDate()});
                arrayList.add(new String[]{"核柜", initEvent.getLogBillsTrack().getComDate()});
                arrayList.add(new String[]{"提交调度", initEvent.getLogBillsTrack().getAssembleDate()});
                arrayList.add(new String[]{"报柜号", initEvent.getLogBillsTrack().getMarketCheckDate()});
            }


            dynamicAdapter.setList(arrayList);
            RecyclerViewUtils.initPage(mDataBinding.rlView, dynamicAdapter, null, getContext(), false);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }


}
