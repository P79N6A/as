package com.yaoguang.appcommon.publicsearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.publicsearch.adapter.SearchSimpleAdapter;
import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * @deprecated 弃用，如果要重新使用，记得加上顶部的隔离
 * 单选，只有条件类型的
 * Created by zhongjh on 2017/6/9.
 */
public class PublicSearchSimpleFragment extends BasePublicSearchFragment {

    /**
     * @param codeType 用户类型
     * @param type     类型
     */
    public static BasePublicSearchFragment newInstance(String codeType, int type) {
        Bundle args = new Bundle();
        args.putInt("mType", type);
        args.putString("codeType", codeType);
        BasePublicSearchFragment fragment = new PublicSearchSimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateCustom(Bundle args) {

    }

    @Override
    public boolean setMultiSelect() {
        return false;
    }

    @Override
    public void setOnMenuItemClickListener(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_voice_rec) {//查找
            mPresenter.addQuery(mInitialView.viewHolder.floating_search_view.getQuery());
            mInitialView.name = mInitialView.viewHolder.floating_search_view.getQuery();
        }
    }

    @Override
    public void onDestroyViewCustom() {

    }

    @Override
    public void initAdapter(InitialView initialView) {
        initialView.adapter = new SearchSimpleAdapter(PublicSearchSimpleFragment.this, mMultiSelect);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_infoclient_manager, container, false);
        mInitialView = new InitialView(view);
        mPresenter = new PublicSearchPresenterImpl(this,  mType, null, null, null);
        return view;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }
}
