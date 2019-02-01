package com.yaoguang.company.businessorder2.common.loadingandunloading.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.loadingandunloading.list.adapter.LoadingAndUnloadingListAdapter;
import com.yaoguang.company.businessorder2.common.loadingandunloading.model.LoadingAndUnloadingModelFragment;
import com.yaoguang.company.databinding.FragmentLoadingAndUnloadingListBinding;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.greendao.entity.company.LoadingAndUnloadingCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

/**
 * Created by zhongjh on 2018/10/30.
 */

public class LoadingAndUnloadingListFragment extends BaseFragmentListConditionDataBind<InfoClientPlace, LoadingAndUnloadingCondition, LoadingAndUnloadingListAdapter, FragmentLoadingAndUnloadingListBinding>
        implements LoadingAndUnloadingListContact.View {

    private final static int MODE = 1;

    private LoadingAndUnloadingCondition mLoadingAndUnloadingCondition = new LoadingAndUnloadingCondition(); // 条件
    private LoadingAndUnloadingListContact.Presenter mPresenter = new LoadingAndUnloadingListPresenter(this);

    private int mType;      // 0装货1卸货
    private String mCodeId; // 托运人id，必须有这个
    private String mAreaName;// 装卸货地址
    private String mConsigneeId;// 货主

    DialogHelper dialogHelper;

    public static LoadingAndUnloadingListFragment newInstance(int type, String codeId, String areName,String consigneeId) {
        LoadingAndUnloadingListFragment loadingAndUnloadingListFragment = new LoadingAndUnloadingListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("codeId", codeId);
        bundle.putString("areName", areName);
        bundle.putString("consigneeId",consigneeId);
        loadingAndUnloadingListFragment.setArguments(bundle);
        return loadingAndUnloadingListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mCodeId = args.getString("codeId");
            mType = args.getInt("type");
            mAreaName = args.getString("areName");
            mConsigneeId = args.getString("consigneeId");
        }
        mLoadingAndUnloadingCondition.setCodeId(mCodeId);
        mLoadingAndUnloadingCondition.setAreaName(mAreaName);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new LoadingAndUnloadingListAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public LoadingAndUnloadingCondition getCondition(boolean isRegain) {
        if (isRegain)
            mLoadingAndUnloadingCondition.setName(mDataBinding.etQuery.getText().toString());
        return mLoadingAndUnloadingCondition;
    }

    @Override
    public void setConditionView(LoadingAndUnloadingCondition condition) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_loading_and_unloading_list;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            String title;
            if (mType == 0)
                title = "装货";
            else
                title = "卸货";

            initToolbarNav(mToolbarCommonBinding.toolbar, "管理" + title + "信息", R.menu.menu_loading_and_unloading_list, LoadingAndUnloadingListFragment.this);
        }
    }

    @Override
    public void initListener() {
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener(new LoadingAndUnloadingListAdapter.OnRecyclerViewItemListener() {
            @Override
            public void onItemEditClick(View itemView, InfoClientPlace item, int position) {
                startForResult(LoadingAndUnloadingModelFragment.newInstance("", item, mCodeId, 0, mAreaName,mConsigneeId), MODE);
            }

            @Override
            public void onItemDeleteClick(View itemView, InfoClientPlace item, int position) {
                // 弹出提示确认是否删除
                if (dialogHelper == null)
                    dialogHelper = new DialogHelper(getContext(), "提示", "是否删除该信息~", "确定", "取消", false, new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            mPresenter.batchDeletePlace(item.getId(), mCodeId);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                dialogHelper.show();
            }

            @Override
            public void onItemSubmitClick(View itemView, InfoClientPlace item, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("infoClientPlace", item);
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });

        mDataBinding.etQuery.setOnKeyListener((v, keyCode, event) -> {
            //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
            if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                //处理事件
                refreshDataAnimation();
                return true;
            }
            return false;
        });

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startForResult(LoadingAndUnloadingModelFragment.newInstance("", null, mCodeId, mType, mAreaName,mConsigneeId), MODE);
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MODE:
                    refreshDataAnimation();
                    break;
            }
        }
    }
}
