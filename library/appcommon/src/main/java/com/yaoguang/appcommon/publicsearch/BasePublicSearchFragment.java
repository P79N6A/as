package com.yaoguang.appcommon.publicsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentList;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.PublicSearchData;

import java.util.List;

import ezy.ui.layout.LoadingLayout;

/**
 * 公共的名称,并且可以用于缓存
 * Created by zhongjh on 2017/6/2.
 */
public abstract class BasePublicSearchFragment extends BaseFragmentList implements PublicSearchContact.CPublicSearchView {

    protected InitialView mInitialView;
    protected PublicSearchContact.CPublicSearchPresenter mPresenter;
    protected int mType;
    boolean mMultiSelect;

    public abstract void onCreateCustom(Bundle args);

    public abstract boolean setMultiSelect();

    public abstract void setOnMenuItemClickListener(MenuItem item);

    public abstract void onDestroyViewCustom();

    public abstract void initAdapter(InitialView initialView);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt("mType");
            onCreateCustom(args);
        }
        mMultiSelect = setMultiSelect();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateView(inflater, container);
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mInitialView.viewHolder.refreshLayout.autoRefresh();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onDestroy() {
        onDestroyViewCustom();
        mPresenter.unSubscribe();
        super.onDestroy();
    }

    @Override
    public String getName() {
        if (mInitialView != null)
            return mInitialView.name;
        return null;
    }

    @Override
    public void fail(String strMessage) {
        Toast.makeText(BaseApplication.getInstance(),strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swapSuggestions(List<PublicSearchData> publicSearchDatas) {
        mInitialView.viewHolder.floating_search_view.swapSuggestions(publicSearchDatas);
    }

    @Override
    public void showProgress() {
        mInitialView.viewHolder.floating_search_view.showProgress();
    }

    @Override
    public void hideProgress() {
        mInitialView.viewHolder.floating_search_view.hideProgress();
    }

    @Override
    public void refreshData() {
        mPresenter.refreshData(getName());
    }

    @Override
    public void loadMoreData() {
        mPresenter.loadMoreData(getName(), mInitialView.adapter.getList().size());
    }

    public class InitialView {
        public BaseLoadMoreRecyclerAdapter adapter;
        public ViewHolder viewHolder;

        public String name;

        public InitialView(View view) {
            viewHolder = new ViewHolder(view);
            initView(view);
            BasePublicSearchFragment.this.setRecyclerview(viewHolder.rlView, viewHolder.refreshLayout, viewHolder.loading, adapter);
            initListener();
        }

        void initView(View view) {
            //列表初始化
            initAdapter(this);
            RecyclerViewUtils.initPage(viewHolder.rlView, adapter, null, getContext(), true);
        }

        void initListener() {
            BasePublicSearchFragment.this.initRecyclerviewListener();
            //点击事件 <AppPublicInfoWrapper>
            adapter.setOnItemClickListener((itemView, item, position) -> {
                if (!mMultiSelect) {
                    AppPublicInfoWrapper appPublicInfoWrapper = (AppPublicInfoWrapper) item;
                    mPresenter.addQuery(appPublicInfoWrapper.getFullName());
                    name = appPublicInfoWrapper.getFullName();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", appPublicInfoWrapper.getRemark1());
                    bundle.putString("id", appPublicInfoWrapper.getId());
                    bundle.putString("shortName", appPublicInfoWrapper.getShortName());
                    bundle.putString("fullName", appPublicInfoWrapper.getFullName());
                    popChild();
                    setFragmentResult(RESULT_OK, bundle);
                }
            });
            //编辑文本的时候
            viewHolder.floating_search_view.setOnQueryChangeListener((oldQuery, newQuery) -> {
                mPresenter.onSearchAction(newQuery);
                mPresenter.refreshData(newQuery);
            });
//            viewHolder.floating_search_view.setOnQueryChangeListener((oldQuery, newQuery) -> mPresenter.onSearchAction(newQuery));
            //确认查询某个文本的时候
            viewHolder.floating_search_view.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
                @Override
                public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                    //item选择
                    mPresenter.addQuery(searchSuggestion.getBody());
                    name = searchSuggestion.getBody();
                }

                @Override
                public void onSearchAction(String query) {
                    //文本查询
                    mPresenter.addQuery(query);
                    name = query;
                }
            });
            viewHolder.floating_search_view.setOnMenuItemClickListener(item -> BasePublicSearchFragment.this.setOnMenuItemClickListener(item));
            viewHolder.floating_search_view.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
                @Override
                public void onMenuOpened() {
                    pop();
                }

                @Override
                public void onMenuClosed() {

                }
            });
            viewHolder.floating_search_view.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
                @Override
                public void onFocus() {
                    //进入焦点的时候
                    mPresenter.onSearchAction(viewHolder.floating_search_view.getQuery());
                }

                @Override
                public void onFocusCleared() {
                    //离开焦点的时候
                }
            });

        }

    }

    public static class ViewHolder {
        public View rootView;
        public RecyclerView rlView;
        public LoadingLayout loading;
        public SmartRefreshLayout refreshLayout;
        public FrameLayout flMain;
        public FloatingSearchView floating_search_view;
        public RelativeLayout parent_view;
        public CoordinatorLayout main_content;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.rlView = (RecyclerView) rootView.findViewById(R.id.rlView);
            this.loading = (LoadingLayout) rootView.findViewById(R.id.loading);
            this.refreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refreshLayout);
            this.flMain = (FrameLayout) rootView.findViewById(R.id.flMain);
            this.floating_search_view = (FloatingSearchView) rootView.findViewById(R.id.floating_search_view);
            this.parent_view = (RelativeLayout) rootView.findViewById(R.id.parent_view);
            this.main_content = (CoordinatorLayout) rootView.findViewById(R.id.main_content);
        }

    }

}
