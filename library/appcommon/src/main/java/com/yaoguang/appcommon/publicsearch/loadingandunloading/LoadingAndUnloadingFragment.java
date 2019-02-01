package com.yaoguang.appcommon.publicsearch.loadingandunloading;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.event.DataSynEvent;
import com.yaoguang.appcommon.publicsearch.addaddress.AddAddressFragment;
import com.yaoguang.appcommon.publicsearch.BasePublicSearchFragment;
import com.yaoguang.appcommon.publicsearch.PublicSearchPresenterImpl;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.appcommon.publicsearch.adapter.SearchLoadingAndUnloadingAdapter;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.common.view.bar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * 装卸货地址的多选
 * Created by zhongjh on 2017/6/9.
 */
public class LoadingAndUnloadingFragment extends BasePublicSearchFragment implements LoadingAndUnloadingContact.LoadingAndUnloadingView {

    LoadingAndUnloadingContact.LoadingAndUnloadingPresenter mLoadingAndUnloadingPresenter;

    //条件id之一
    String mCodeId;
    //地区
    String mAreaName;
    //已选择的ids,逗号分隔
    String mIds;
    //已有的地址，跟上个ids一样的作用，只是这个作用于脱离地址模块的
    ArrayList<AppInfoClientPlace> mAppInfoClientPlaces;
    private DialogHelper mDialogHelper;

    /**
     * @param type     类型
     * @param codeId   暂时为托运人id
     * @param areaName 暂时为地区
     * @param ids      已选择的ids
     */
    public static BasePublicSearchFragment newInstance(int type, String codeId, String areaName, String ids, ArrayList<AppInfoClientPlace> appInfoClientPlaces) {
        Bundle args = new Bundle();
        args.putInt("mType", type);
        args.putString("codeId", codeId);
        args.putString("areaName", areaName);
        args.putString("ids", ids);
        args.putParcelableArrayList("appInfoClientPlaces", appInfoClientPlaces);
        BasePublicSearchFragment fragment = new LoadingAndUnloadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateCustom(Bundle args) {
        mCodeId = args.getString("codeId", "");
        mAreaName = args.getString("areaName", "");
        mIds = args.getString("ids", "");
        mAppInfoClientPlaces = args.getParcelableArrayList("appInfoClientPlaces");
        mLoadingAndUnloadingPresenter = new LoadingAndUnloadingPresenterImpl(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_infoclient_manager_mulit, container, false);
        mInitialView = new InitialView(view);
        // 计算toolbar高度，倾入式后高度变化
        View titleBar = view.findViewById(R.id.search_query_section);
        View vInvisible = view.findViewById(R.id.vInvisible);
        if (titleBar != null)
            ImmersionBar.setTitleBar(getActivity(), titleBar);
        if (vInvisible != null)
            ImmersionBar.setTitleBar(getActivity(), vInvisible);
        mPresenter = new PublicSearchPresenterImpl(this, mType, mCodeId, mAreaName, mIds);
        return view;
    }

    @Override
    public void onDestroyViewCustom() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        EventBus.getDefault().unregister(this);//解除订阅
        mLoadingAndUnloadingPresenter.unSubscribe();
    }

    @Override
    public void initAdapter(InitialView initialView) {
        SearchLoadingAndUnloadingAdapter searchLoadingAndUnloadingAdapter = new SearchLoadingAndUnloadingAdapter(mIds, mAppInfoClientPlaces);
        searchLoadingAndUnloadingAdapter.setListener(new SearchLoadingAndUnloadingAdapter.Listener() {
            @Override
            public void editListener(View itemView, AppPublicInfoWrapper item, int position) {
                //打开编辑窗体
                start(AddAddressFragment.newInstance(item, mCodeId, mAreaName, mType, false), SINGLETOP);
            }

            @Override
            public void deleteListener(View itemView, final AppPublicInfoWrapper item, int position) {
                if (mDialogHelper == null)
                    mDialogHelper = new DialogHelper(getContext(), "提示", "确认删除该项", new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            mLoadingAndUnloadingPresenter.deleteAddress(item.getId(), mCodeId);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                mDialogHelper.show();

            }
        });
        initialView.adapter = searchLoadingAndUnloadingAdapter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(DataSynEvent event) {
        //刷新数据
        mInitialView.viewHolder.floating_search_view.clearQuery();
        mInitialView.viewHolder.refreshLayout.autoRefresh();
    }


    @Override
    public boolean setMultiSelect() {
        return true;
    }

    @Override
    public void setOnMenuItemClickListener(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_voice_rec) {//查找
            mPresenter.addQuery(mInitialView.viewHolder.floating_search_view.getQuery());
            mInitialView.name = mInitialView.viewHolder.floating_search_view.getQuery();
        } else if (i == R.id.action_save) {//获取所有被勾选的值
            LinkedList<AppPublicInfoWrapper> appPublicInfoWrappers = (LinkedList<AppPublicInfoWrapper>) mInitialView.adapter.getList();
            ArrayList<AppPublicInfoWrapper> appPublicInfoWrapperArrays = new ArrayList<>();
            for (int k = 0; k < appPublicInfoWrappers.size(); k++) {
                if (appPublicInfoWrappers.get(k).isCheck())
                    appPublicInfoWrapperArrays.add(appPublicInfoWrappers.get(k));
            }

            if (appPublicInfoWrappers.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("appPublicInfoWrappers", appPublicInfoWrapperArrays);
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        } else if (i == R.id.action_add) {
            //跳转到添加界面,传递托运人id
            start(AddAddressFragment.newInstance(mCodeId, mAreaName, mType, false), SINGLETOP);
        }
    }


    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }
}
