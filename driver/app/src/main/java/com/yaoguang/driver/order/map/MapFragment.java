package com.yaoguang.driver.order.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.common.base.basemap.BaseMapFragment;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ULog;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/22 0022.
 * 版    权：
 */
public class MapFragment extends BaseMapFragment implements Toolbar.OnMenuItemClickListener {
    private static String IS_SHOW_TOOLBAR = "isShowToolbar";
    private boolean isShowToolbar;

    public static MapFragment newInstance(boolean isShowToolbar) {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        args.putBoolean(IS_SHOW_TOOLBAR, isShowToolbar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isShowToolbar = getArguments().getBoolean(IS_SHOW_TOOLBAR);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_SHOW_TOOLBAR, isShowToolbar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_map, null);
        if (savedInstanceState != null) {
            isShowToolbar = savedInstanceState.getBoolean(IS_SHOW_TOOLBAR);
        }

        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        App.handler.postDelayed(() -> {
            zoom(15f);
            ULog.i("zoom(15f);");
        }, 2000);
    }

    @Override
    protected void updateLocation() {
        App.locationInit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (AppClickUtil.isDuplicateClick()) return false;
        start(MapSearchFragment.newInstance());
        return false;
    }

    private void initView(View view) {
        ImageView imgReturn = view.findViewById(R.id.imgReturn);
        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 是否显示toolbar
        if (isShowToolbar) {
            toolbarTitle.setText("导航");
            toolbar.inflateMenu(R.menu.driver_search);
            toolbar.setOnMenuItemClickListener(this);
            imgReturn.setOnClickListener(v -> MapFragment.this.pop());
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }
}
