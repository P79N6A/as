package com.yaoguang.appcommon.phone.contact2.contactSearch;


import android.databinding.ViewDataBinding;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.phone.contact2.contactSearch.adapter.ContactSearchAdapter;
import com.yaoguang.appcommon.databinding.FragmentContact2SearchBinding;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SharedPrefsStrListUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.view.bar.ImmersionBar;
import com.yaoguang.lib.net.bean.PageList;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.contactsearchfragment
 * @Description: 关注查找 窗体
 * @date 2018/04/12
 */
public abstract class BaseContactSearchFragment<B extends ViewDataBinding> extends BaseFragmentListConditionDataBind<UserOfficeWrapper, String, ContactSearchAdapter, FragmentContact2SearchBinding> implements ContactSearchContract.View {

    public static final String TYPE = "CONTACT_SEARCH_FRAGMENT_CACHES_KEY";
    ContactSearchContract.Presenter mPresenter = new ContactSearchPresenter(this);
    protected LayoutInflater mInflater;
    // 缓存的数据
    List<String> caches;

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact2_search;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new ContactSearchAdapter(BaseContactSearchFragment.this);
    }

    @Override
    public String getCondition(boolean isRegain) {
        return mDataBinding.etSearch.getText().toString();
    }

    @Override
    public void setConditionView(String condition) {

    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        super.initDataBind(view, inflater);
        mInflater = inflater;
        // 计算toolbar高度，倾入式后高度变化
        View toolbarSearch = view.findViewById(R.id.toolbarSearch);
        if (toolbarSearch != null)
            ImmersionBar.setTitleBar(getActivity(), toolbarSearch);

        //初始化缓存数据
        caches = SharedPrefsStrListUtil.getStrListValue(getContext(), ObjectUtils.parseString(TYPE));
        initCacheAdapter(inflater);
    }

    @Override
    public void init() {
        mPresenter.subscribe();
    }

    @Override
    public void initListener() {
        // 点击搜索的时候
        mDataBinding.etSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                // 先隐藏键盘
                hideKeyboard();
                // 显示dialog
                showProgressDialog();
                //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                mPresenter.refreshData(getCondition(true));
            }
            return false;
        });
        //点击缓存列表的时候，直接跳转
        mDataBinding.tflCache.setOnTagClickListener((view, position, parent) -> {
            mDataBinding.etSearch.setText(caches.get(position));
            // 先隐藏键盘
            hideKeyboard();
            // 显示dialog
            showProgressDialog();
            //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
            mPresenter.refreshData(getCondition(true));
            return true;
        });


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
                BaseContactSearchFragment.this.hideKeyboard();
            }
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
                caches.clear();
                initCacheAdapter(mInflater);
            }
        });

    }

    @Override
    public void onDestroy() {
        //保存缓存
        SharedPrefsStrListUtil.putStrListValue(getContext(), ObjectUtils.parseString(TYPE), caches);
        super.onDestroy();
    }

    protected void comitQuery(String name, LayoutInflater inflater) {
        //判断有重复,则删除，然后添加
        for (int i = 0; i < caches.size(); i++) {
            if (caches.get(i).equals(name)) {
                caches.remove(i);
            }
        }
        caches.add(name);
        //如果长度超过20个，则删除最后一个
        if (caches.size() > 30) {
            caches.remove(caches.size() - 1);
        }

        initCacheAdapter(inflater);

    }

    /**
     * 初始化缓存适配器
     *
     * @param inflater 布局
     */
    private void initCacheAdapter(final LayoutInflater inflater) {
        //如果缓存没数据，就显示空的提示
        if (caches == null || caches.size() <= 0) {
            mDataBinding.ivCacheEmpty.setVisibility(View.VISIBLE);
            mDataBinding.llCache.setVisibility(View.GONE);
        } else {
            mDataBinding.ivCacheEmpty.setVisibility(View.GONE);
            mDataBinding.llCache.setVisibility(View.VISIBLE);
            mDataBinding.tflCache.setAdapter(new TagAdapter<String>(caches) {
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

    @Override
    public void isOneStartFragment(PageList<UserOfficeWrapper> result) {

    }

    @Override
    public void recyclerViewShowEmpty(boolean isShowRecyclerView) {
        super.recyclerViewShowEmpty(false);
        hideProgressDialog();
    }

    @Override
    public void nextAdapter(List<UserOfficeWrapper> list, boolean isHas) {
        super.nextAdapter(list, isHas);
        hideProgressDialog();
    }

    @Override
    public void refreshAdapter(List<UserOfficeWrapper> list, boolean isHas) {
        super.refreshAdapter(list, isHas);
        hideProgressDialog();
    }
}
