package com.yaoguang.shipper.shipschedule;

import android.view.View;

import com.yaoguang.appcommon.databinding.FragmentShipScheduleBinding;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.appcommon.phone.shipschedule.BaseShipScheduleFragment;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

/**
 * 船期查询
 * Created by zhongjh on 2017/6/29.
 */
public class ShipScheduleFragment extends BaseShipScheduleFragment<FragmentShipScheduleBinding>  {

    @Override
    public void itemOnClick(View itemView, Object item) {

    }

    @Override
    protected void initViewCustom() {
        mDataBinding.llUser.setVisibility(View.VISIBLE);
        //默认获取第一个
        mPresenter.analysisContactCompany();
    }

    @Override
    protected void initListenerCustom() {
        mDataBinding.llUser.setOnClickListener(
                new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY);
                        startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY));
                    }
                });
    }

    public static ShipScheduleFragment newInstance() {
        return new ShipScheduleFragment();
    }
}
