package com.yaoguang.driver.phone.order.nodeEdit.nodeEditPort;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentNodeEditPortBinding;
import com.yaoguang.driver.phone.order.nodeEdit.nodeEditPort.adapter.NodeEditPortAdapter;
import com.yaoguang.greendao.entity.common.InfoDock;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SharedPrefsStrListUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * 修改码头
 * Created by zhongjh on 2018/7/9.
 */
public class NodeEditPortFragment extends BaseFragmentListConditionDataBind<InfoDock, HashMap<String, String>, NodeEditPortAdapter, FragmentNodeEditPortBinding> implements NodeEditPortContract.View {

    NodeEditPortPresenter mNodeEditPortPresenter = new NodeEditPortPresenter(this);
    static final String TYPE = "NODE_EDIT_PORT_TYPE"; // 缓存的类型
    List<String> caches; // 缓存的数据
    HashMap<String, String> mCondition = new HashMap<>();
    boolean isShowFlMain = true;//是true的话就要先显示查询出来的表格，是false的话，则直接查出来数据返回。用于点击标签快速跳转

    /**
     * @param clientId 公司id
     * @param nodeId   节点id
     */
    public static NodeEditPortFragment newInstance(String clientId, String nodeId) {
        NodeEditPortFragment nodeEditPortFragment = new NodeEditPortFragment();
        if (clientId != null) {
            Bundle bundle = new Bundle();
            bundle.putString("clientId", clientId);
            bundle.putString("nodeId", nodeId);
            nodeEditPortFragment.setArguments(bundle);
        }
        return nodeEditPortFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_node_edit_port;
    }

    @Override
    public void init() {
        //初始化缓存数据
        caches = SharedPrefsStrListUtil.getStrListValue(getContext(), ObjectUtils.parseString(TYPE));

        initCacheAdapter(mLayoutInflater);

        mOnRecyclerViewCallback = (list, isHas) -> {
            if (!isShowFlMain && list.size() > 0) {
                comitQuery((InfoDock) list.get(0), mLayoutInflater);
                if (!isShowFlMain) {
                    isShowFlMain = true;
                }
                return true;
            }
            if (!isShowFlMain) {
                isShowFlMain = true;
            }
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
                NodeEditPortFragment.this.hideKeyboard();
            }
        });

        //搜索
        mDataBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //根据查询的内容，立即搜索文本
                refreshData();
                if (TextUtils.isEmpty(s.toString())) {
                    //空的就显示历史
                    mDataBinding.flMain.setVisibility(View.GONE);
                    mDataBinding.flCache.setVisibility(View.VISIBLE);
                } else {
                    //非空的就显示当前搜索内容
                    mDataBinding.flMain.setVisibility(View.VISIBLE);
                    mDataBinding.flCache.setVisibility(View.GONE);
                }
            }
        });

        //点击缓存列表的时候，刷新列表
        mDataBinding.tflCache.setOnTagClickListener((view, position, parent) -> {
            mDataBinding.etSearch.setText(caches.get(position));
            isShowFlMain = false;
            return true;
        });

        //点击列表的时候，添加进缓存
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            comitQuery((InfoDock) item, mLayoutInflater);
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
                initCacheAdapter(mLayoutInflater);
            }
        });

    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new NodeEditPortAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mNodeEditPortPresenter;
    }

    @Override
    public HashMap<String, String> getCondition(boolean isRegain) {
        if (isRegain) {
            if (getArguments() != null)
                mCondition.put("clientId", getArguments().getString("clientId"));
            mCondition.put("name", mDataBinding.etSearch.getText().toString());
        }
        return mCondition;
    }

    @Override
    public void setConditionView(HashMap<String, String> condition) {

    }

    @Override
    public void onDestroy() {
        //保存缓存
        SharedPrefsStrListUtil.putStrListValue(getContext(), ObjectUtils.parseString(TYPE), caches);
        super.onDestroy();
    }

    /**
     * 提交查询的内容
     *
     * @param infoDock 查询的实体类
     * @param inflater 样式
     */
    private void comitQuery(InfoDock infoDock, LayoutInflater inflater) {
        //判断有重复,则删除，然后添加
        for (int i = 0; i < caches.size(); i++) {
            if (caches.get(i).equals(infoDock.getDockId())) {
                caches.remove(i);
            }
        }
        caches.add(infoDock.getDockId());
        //如果长度超过20个，则删除最后一个
        if (caches.size() > 30) {
            caches.remove(caches.size() - 1);
        }

        if (isShowFlMain) {
            initCacheAdapter(inflater);
        }

        Bundle bundle = new Bundle();
        bundle.putString("dockId", infoDock.getDockId());
        if (getArguments() != null)
            bundle.putString("nodeId", getArguments().getString("nodeId"));
        setFragmentResult(RESULT_OK, bundle);
        pop();
    }

    /**
     * 初始化缓存适配器
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

}
