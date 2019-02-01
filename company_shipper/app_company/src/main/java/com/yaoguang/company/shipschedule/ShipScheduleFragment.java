package com.yaoguang.company.shipschedule;

import android.view.View;

import com.yaoguang.appcommon.databinding.FragmentShipScheduleBinding;
import com.yaoguang.appcommon.phone.shipschedule.BaseShipScheduleFragment;
import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;

/**
 * 船期查询
 * Created by zhongjh on 2017/6/29.
 */
public class ShipScheduleFragment extends BaseShipScheduleFragment<FragmentShipScheduleBinding> {

    public static ShipScheduleFragment newInstance() {
        return new ShipScheduleFragment();
    }

    @Override
    public void itemOnClick(View itemView, Object item) {
        InfoVoyageTable infoVoyageTable = (InfoVoyageTable) item;
        ShipScheduleDetailDialog dialog = new ShipScheduleDetailDialog(ShipScheduleFragment.this.getContext(), infoVoyageTable);
        dialog.show();
    }

    @Override
    protected void initListenerCustom() {

    }

    @Override
    protected void initViewCustom() {
        mPresenter.subscribe();
    }



}
