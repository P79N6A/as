package com.yaoguang.appcommon.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.databinding.FragmentSearchBinding;
import com.yaoguang.appcommon.search.adapter.SearchSimpleAdapter;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SharedPrefsStrListUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * 搜索窗体
 * Created by zhongjh on 2017/8/24.
 */
public class SearchFragment extends BaseFragmentListConditionDataBind<AppPublicInfoWrapper, String, SearchSimpleAdapter, FragmentSearchBinding> implements SearchContact.SearchView {

    SearchContact.SearchPresenter mPresenter;
    int mType;
    String mCodeId;
    boolean isShowFlMain = true;//是true的话就要先显示查询出来的表格，是false的话，则直接查出来数据返回。用于点击标签快速跳转

    List<String> mCaches;
    LayoutInflater mInflater;



    /**
     * @param type     类型
     */
    public static SearchFragment newInstance( int type) {
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
    public static SearchFragment newInstance( int type,String codeId) {
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
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
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
        initCacheAdapter(inflater);

        // 缓存只能选择一个
        mDataBinding.tflCache.setMaxSelectCount(1);

        mOnRecyclerViewCallback = (list, isHas) -> {
//            if (!isShowFlMain && list.size() > 0) {
//                comitQuery((AppPublicInfoWrapper) list.get(0), inflater);
//                if (!isShowFlMain) {
//                    isShowFlMain = true;
//                }
//                return true;
//            }
//            if (!isShowFlMain) {
//                isShowFlMain = true;
//            }
            return false;
        };
    }

    @Override
    public void initListener() {
        //进入焦点的时候
        mDataBinding.etSearch.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                //显示标签
                mDataBinding.tvCancel.setVisibility(View.VISIBLE);
                mDataBinding.flCache.setVisibility(View.VISIBLE);
                mDataBinding.flMain.setVisibility(View.GONE);
            } else {
                mDataBinding.tvCancel.setVisibility(View.GONE);
                mDataBinding.flCache.setVisibility(View.GONE);
                mDataBinding.flMain.setVisibility(View.VISIBLE);
            }
        });
        //点击取消的时候
        mDataBinding.tvCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.etSearch.setText("");
                mDataBinding.tvCancel.setVisibility(View.GONE);
                mDataBinding.flCache.setVisibility(View.GONE);
                mDataBinding.flMain.setVisibility(View.VISIBLE);
                mDataBinding.etSearch.clearFocus();
                mDataBinding.etFocus.setFocusable(true);
                mDataBinding.etFocus.requestFocus();
                SearchFragment.this.hideKeyboard();
            }
        });
        // 输入文本的时候，时时搜索
        mDataBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
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
                    mPresenter.startSearch(s.toString());
                }
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
                }
                return true;
            }
            return false;

        });

        //点击缓存列表的时候，刷新列表
        mDataBinding.tflCache.setOnTagClickListener((view, position, parent) -> {
            mDataBinding.etSearch.setText(mCaches.get(position));
//            isShowFlMain = false;
            return true;
        });

        //点击列表的时候，添加进缓存
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            AppPublicInfoWrapper appPublicInfoWrapper = (AppPublicInfoWrapper) item;
            comitQuery(appPublicInfoWrapper, mInflater);
        });

        //点击返回
        mDataBinding.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pop();
            }
        });

        //清除缓存
        mDataBinding.tvClear.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mCaches.clear();
                initCacheAdapter(mInflater);
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
    private void initCacheAdapter(final LayoutInflater inflater) {
        //如果缓存没数据，就显示空的提示
        if (mCaches == null || mCaches.size() <= 0) {
            mDataBinding.ivCacheEmpty.setVisibility(View.VISIBLE);
            mDataBinding.llCache.setVisibility(View.GONE);
        } else {
            mDataBinding.ivCacheEmpty.setVisibility(View.GONE);
            mDataBinding.llCache.setVisibility(View.VISIBLE);
            mDataBinding.tflCache.setAdapter(new TagAdapter<String>(mCaches) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) inflater.inflate(R.layout.item_search_cache,
                            mDataBinding.tflCache, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }
    }

    /**
     * 提交查询的内容
     *
     * @param appPublicInfoWrapper 查询的实体类
     * @param inflater             样式
     */
    private void comitQuery(AppPublicInfoWrapper appPublicInfoWrapper, LayoutInflater inflater) {
        //判断有重复,则删除，然后添加
        for (int i = 0; i < mCaches.size(); i++) {
            if (mCaches.get(i).equals(appPublicInfoWrapper.getRemark1())) {
                mCaches.remove(i);
            }
        }
        mCaches.add(appPublicInfoWrapper.getRemark1());
        //如果长度超过20个，则删除最后一个
        if (mCaches.size() > 30) {
            mCaches.remove(mCaches.size() - 1);
        }

//        if (isShowFlMain) {
//            initCacheAdapter(inflater);
//        }

        Bundle bundle = new Bundle();
        bundle.putString("name", appPublicInfoWrapper.getRemark1());
        bundle.putString("id", appPublicInfoWrapper.getId());
        bundle.putString("shortName", appPublicInfoWrapper.getShortName());
        bundle.putString("fullName", appPublicInfoWrapper.getFullName());
        setFragmentResult(RESULT_OK, bundle);
        pop();
    }




}
