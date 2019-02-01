package com.yaoguang.company.businessorder2.forwarder.container.list;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.forwarder.container.detail.ContainerDetailFragment;
import com.yaoguang.company.businessorder2.forwarder.container.list.adapter.ContainerListAdapter;
import com.yaoguang.company.databinding.FragmentForwarderBusinessContainerListBinding;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightSono;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import java.util.ArrayList;

/**
 * Created by zhongjh on 2018/11/10.
 */
public class ContainerListFragment extends BaseFragmentDataBind<FragmentForwarderBusinessContainerListBinding> implements Toolbar.OnMenuItemClickListener {

    private final static int REQUEST_ADD = 1;
    private final static int REQUEST_UPDATE = 2;
    ContainerListAdapter mContainerListAdapter;

    /**
     * @param freightSonos 数据源
     * @param serviceType  货代拖车
     * @param type         0是编辑，1是禁用编辑
     */
    public static ContainerListFragment newInstance(ArrayList<FreightSono> freightSonos, String serviceType, int type) {
        ContainerListFragment containerListFragment = new ContainerListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("freightSonos", freightSonos);
        bundle.putString("serviceType", serviceType);
        bundle.putInt("type", type);
        containerListFragment.setArguments(bundle);
        return containerListFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forwarder_business_container_list;
    }

    @Override
    public void init() {
        if (getArguments() != null) {
            if (getArguments().getInt("type") == 0) {
                initToolbarNav(mToolbarCommonBinding.toolbar, "货柜", R.menu.menu_container_list, this);
            } else {
                initToolbarNav(mToolbarCommonBinding.toolbar, "货柜", -1, null);
            }
            mContainerListAdapter = new ContainerListAdapter();
            mContainerListAdapter.setOnItemClickListener((itemView, item, position) -> {
                // 跳转详情
                startForResult(ContainerDetailFragment.newInstance((FreightSono) item, position, getArguments().getInt("type")), REQUEST_UPDATE);
            });
            mContainerListAdapter.setList(getArguments().getParcelableArrayList("freightSonos"));
            RecyclerViewUtils.initPage(mDataBinding.rlView, mContainerListAdapter, null, getContext(), false);
        }
    }

    @Override
    public void initListener() {
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> {
            // 返回当前列表
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("freightSonos", (ArrayList<FreightSono>) mContainerListAdapter.getList());
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_add:
                startForResult(ContainerDetailFragment.newInstance(null, -1, getArguments().getInt("type")), REQUEST_ADD);
                break;
        }
        return false;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD:
                    FreightSono freightSono = data.getParcelable("freightSono");
                    mContainerListAdapter.add(mContainerListAdapter.getItemCount(), freightSono);
                    break;
                case REQUEST_UPDATE:
                    FreightSono freightSonoU = data.getParcelable("freightSono");
                    int position = data.getInt("position");
                    if (freightSonoU == null) {
                        // 删除
                        mContainerListAdapter.removeItem(position);
                    } else {
                        mContainerListAdapter.updateItem(position, freightSonoU);
                    }
                    break;
            }
        }
        super.onFragmentResult(requestCode, resultCode, data);

    }


    @Override
    public boolean onBackPressedSupport() {
        // 返回当前列表
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("freightSonos", (ArrayList<FreightSono>) mContainerListAdapter.getList());
        setFragmentResult(RESULT_OK, bundle);
        return super.onBackPressedSupport();
    }


}
