package com.yaoguang.appcommon.search2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.databinding.FragmentSearch2Binding;
import com.yaoguang.appcommon.search2.adapter.SearchCacheAdapter;
import com.yaoguang.appcommon.search2.adapter.SearchSimpleAdapter;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SharedPrefsStrListUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 搜索窗体 改版2
 * Created by zhongjh on 2017/8/24.
 */
public class SearchFragment extends BaseFragmentListConditionDataBind<AppPublicInfoWrapper, String, SearchSimpleAdapter, FragmentSearch2Binding> implements SearchContact.SearchView {

    SearchContact.SearchPresenter mPresenter;
    int mType;
    String mCodeId;

    List<String> mCaches;
    LayoutInflater mInflater;

    SearchCacheAdapter mSearchCacheAdapter;

    /**
     * @param type     类型
     */
    public static SearchFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param type     类型
     * @param codeId 关联公司Id,托运人Id，都可以传给这个codeId
     */
    public static SearchFragment newInstance(int type, String codeId) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("codeId",codeId);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt("type");
            mCodeId = args.getString("codeId");
        }
        mIsInitialLoad = false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search2;
    }

    @Override
    public void init() {
        mPresenter = new SearchPresenter(this,  mType,mCodeId);
        mPresenter.subscribe();
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        super.initDataBind(view, inflater);

        mInflater = inflater;
        //初始化缓存数据
        mCaches = SharedPrefsStrListUtil.getStrListValue(getContext(), ObjectUtils.parseString(mType));

        // 初始化数据源
        mSearchCacheAdapter = new SearchCacheAdapter(mCaches);
        RecyclerViewUtils.initPage(mDataBinding.rlCache, mSearchCacheAdapter, null, getContext(), false);

        initCacheAdapter();
    }

    @Override
    public void initListener() {
        RxTextView.textChanges(mDataBinding.etSearch)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        if (TextUtils.isEmpty(charSequence)) {
                            // 空的就显示历史
                            mDataBinding.flMain.setVisibility(View.GONE);
                            mDataBinding.flCache.setVisibility(View.VISIBLE);
                        } else {
                            // 非空的就显示当前搜索内容
                            mDataBinding.flMain.setVisibility(View.VISIBLE);
                            mDataBinding.flCache.setVisibility(View.GONE);

                            // 根据查询的内容，立即搜索文本
                            refreshData();

                            addCachs(charSequence.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        //进入焦点的时候
        mDataBinding.etSearch.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                //显示标签
                mDataBinding.tvSearch.setVisibility(View.VISIBLE);
                mDataBinding.flCache.setVisibility(View.VISIBLE);
                mDataBinding.flMain.setVisibility(View.GONE);
            } else {
                mDataBinding.tvSearch.setVisibility(View.GONE);
                mDataBinding.flCache.setVisibility(View.GONE);
                mDataBinding.flMain.setVisibility(View.VISIBLE);
            }
        });

        //点击搜索的时候
        mDataBinding.tvSearch.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String keytag = mDataBinding.etSearch.getText().toString().trim();

                if (TextUtils.isEmpty(keytag)) {
                    //空的就显示历史
                    mDataBinding.flMain.setVisibility(View.GONE);
                    mDataBinding.flCache.setVisibility(View.VISIBLE);
                } else {
                    //非空的就显示当前搜索内容
                    mDataBinding.flMain.setVisibility(View.VISIBLE);
                    mDataBinding.flCache.setVisibility(View.GONE);

                    //根据查询的内容，立即搜索文本
                    refreshDataAnimation();

                    addCachs(keytag);
                }

                SearchFragment.this.hideKeyboard();
            }
        });

        // 回车搜索的时候
        mDataBinding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                String keytag = mDataBinding.etSearch.getText().toString().trim();

                if (TextUtils.isEmpty(keytag)) {
                    //空的就显示历史
                    mDataBinding.flMain.setVisibility(View.GONE);
                    mDataBinding.flCache.setVisibility(View.VISIBLE);
                } else {
                    //非空的就显示当前搜索内容
                    mDataBinding.flMain.setVisibility(View.VISIBLE);
                    mDataBinding.flCache.setVisibility(View.GONE);

                    //根据查询的内容，立即搜索文本
                    refreshDataAnimation();

                    addCachs(keytag);
                }
                return true;
            }
            return false;

        });

        // 点击缓存列表的时候，刷新列表
        mSearchCacheAdapter.setOnItemClickListener((itemView, item, position) -> mDataBinding.etSearch.setText(mCaches.get(position)));

        // 点击删除缓存列表其中的某一项
        mSearchCacheAdapter.setOnItemDeleteClickListener((itemView, position) -> {
            mCaches.remove(position);
            initCacheAdapter();
        });

        //点击列表的时候，添加进缓存
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            AppPublicInfoWrapper appPublicInfoWrapper = (AppPublicInfoWrapper) item;
            comitQuery(appPublicInfoWrapper);
        });

        //点击返回
        mDataBinding.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pop();
            }
        });

        //清除缓存
        mDataBinding.imgDelete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mCaches.clear();
                initCacheAdapter();
            }
        });
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new SearchSimpleAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public void onDestroy() {
        //保存缓存
        SharedPrefsStrListUtil.putStrListValue(getContext(), ObjectUtils.parseString(mType), mCaches);
        super.onDestroy();
    }

    @Override
    public String getCondition(boolean isRegain) {
        return mDataBinding.etSearch.getText().toString().trim();
    }

    @Override
    public void setConditionView(String condition) {

    }

    /**
     * 初始化缓存适配器
     */
    private void initCacheAdapter() {
        //如果缓存没数据，就显示空的提示
        if (mCaches == null || mCaches.size() <= 0) {
            mDataBinding.flCache.setVisibility(View.GONE);
        } else {
            mDataBinding.flCache.setVisibility(View.VISIBLE);
            mSearchCacheAdapter.setList(mCaches);
            mSearchCacheAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 添加到缓存
     * @param remark1 查询的值
     */
    private void addCachs(String remark1){
        //判断有重复,则删除，然后添加
        for (int i = 0; i < mCaches.size(); i++) {
            if (mCaches.get(i).equals(remark1)) {
                mCaches.remove(i);
            }
        }
        mCaches.add(remark1);
        //如果长度超过20个，则删除最后一个
        if (mCaches.size() > 30) {
            mCaches.remove(mCaches.size() - 1);
        }

        // 刷新列表
        mSearchCacheAdapter.setList(mCaches);
        mSearchCacheAdapter.notifyDataSetChanged();
    }

    /**
     * 提交查询的内容
     *
     * @param appPublicInfoWrapper 查询的实体类
     */
    private void comitQuery(AppPublicInfoWrapper appPublicInfoWrapper) {
        Bundle bundle = new Bundle();
        bundle.putString("name", appPublicInfoWrapper.getRemark1());
        bundle.putString("id", appPublicInfoWrapper.getId());
        bundle.putString("shortName", appPublicInfoWrapper.getShortName());
        bundle.putString("fullName", appPublicInfoWrapper.getFullName());
        setFragmentResult(RESULT_OK, bundle);
        pop();
    }




}
