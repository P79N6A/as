package com.yaoguang.driver.phone.order.chooseaddress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.yaoguang.appcommon.common.base.basemap.BaseMapDataBindFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.driver.R;

/**
 * 文件名：
 * 描    述：基础地图
 * 作    者：韦理英
 * 时    间：2017/8/22 0022.
 * 版    权：
 */
public class MapFragment extends BaseMapDataBindFragment implements Toolbar.OnMenuItemClickListener {
    private static String IS_SHOW_TOOLBAR = "isShowToolbar";

    // 是否显示顶部导航栏目
    private boolean isShowToolbar;

    /**
     *
     * @param isShowToolbar 是否显示toolbar
     * @return
     */
    public static MapFragment newInstance(boolean isShowToolbar) {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        args.putBoolean(IS_SHOW_TOOLBAR, isShowToolbar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        initView(view);
    }

    @Override
    public void init() {

    }

    @Override
    public void initListener() {

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

    @Override
    public void savedInstanceState(@Nullable Bundle savedInstanceState) {
        super.savedInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            isShowToolbar = savedInstanceState.getBoolean(IS_SHOW_TOOLBAR);
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (AppClickUtil.isDuplicateClick()) return false;
//        start(MapSearchFragment.newInstance());
        // TODO
        return false;
    }

    private void initView(View view) {
        ImageView imgReturn = view.findViewById(R.id.imgReturn);
        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 是否显示toolbar
        if (isShowToolbar) {
            toolbarTitle.setText("导航");
//            toolbar.inflateMenu(R.menu.driver_search);
//            toolbar.setOnMenuItemClickListener(this);
            imgReturn.setOnClickListener(v -> MapFragment.this.pop());
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }


    @Override
    public void initMapFinish() {

    }

    @Override
    public void onCameraChangeFinish(LatLng latLng) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result) {

    }
}
