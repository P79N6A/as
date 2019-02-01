package com.yaoguang.driver.widget.selectcarno;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.yaoguang.appcommon.common.base.BaseDialogFragment;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ConvertUtils;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.driver.R;
import com.yaoguang.appcommon.common.base.BaseLoadMoreRecyclerVLayoutAdapter;
import com.yaoguang.driver.widget.selectcarno.adapter.SelectCarNoAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 作    者：韦理英
 * 时    间：2017/9/11 0011.
 * 描    述：车牌选择器
 */
public class SelectCarNoDialogFragment extends BaseDialogFragment<String> {

    private String[] carNoArray;

    public static SelectCarNoDialogFragment newInstance() {

        Bundle args = new Bundle();

        SelectCarNoDialogFragment fragment = new SelectCarNoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_recyclerview, null);

        RecyclerView recyclerView = (RecyclerView) view;
        // 设置RecyclerView
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        // 初始化数据
        carNoArray = getResources().getStringArray(R.array.carNoFront);
        // 设置格子参数
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(7);
        gridLayoutHelper.setMargin(ConvertUtils.dp2px(10), ConvertUtils.dp2px(30), ConvertUtils.dp2px(10), ConvertUtils.dp2px(30));// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        recyclerView.setBackgroundResource(R.color.gray100);
        gridLayoutHelper.setBgColor(UiUtils.getColor(R.color.gray100));
        gridLayoutHelper.setVGap(20);
        gridLayoutHelper.setHGap(20);
        gridLayoutHelper.setAutoExpand(false);
        // 创建adapter
        SelectCarNoAdapter gridAdapter = new SelectCarNoAdapter(gridLayoutHelper, Arrays.asList(carNoArray));
        // 点击监听
        gridAdapter.setOnItemClickListener(new BaseLoadMoreRecyclerVLayoutAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int position) {
                if (AppClickUtil.isDuplicateClick()) return;

                notice((String) item);
            }
        });
        // 显示recyclerView
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(gridAdapter);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        recyclerView.setAdapter(delegateAdapter);
        // 底部显示
        showLocation = Gravity.BOTTOM;
        return view;
    }

}
