package com.yaoguang.driver.order.map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yaoguang.appcommon.common.base.basemap.BaseMapFragment;
import com.yaoguang.appcommon.common.eventbus.SearchRoadEvent;
import com.yaoguang.common.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.SPUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.nav.plan.NaviPlanUtil;
import com.yaoguang.driver.nav.search.MapSearch;
import com.yaoguang.driver.nav.search.impl.MapSearchImpl;
import com.yaoguang.driver.nav.search.model.SearchKeyword;
import com.yaoguang.driver.order.map.adapter.MapSearchAdapter;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.interactor.driver.order.map.MapSearchContact;
import com.yaoguang.map.navi.MapNavi;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描    述： 地图搜索
 * 作    者：韦理英
 * 时    间：2017/8/7 0007.
 * 版    权：
 */
public class MapSearchFragment extends BaseMapFragment implements MapSearchContact.View<SearchKeyword> {
    private static String SEARCH_KEYWORD = "search_keyword";
    public InitialView mInitialView;

    /**
     * 搜索记录
     */
    private List<SearchKeyword> cacheList;

    private NaviPlanUtil mNaviPlanUtil;
    /**
     * 从
     */
    protected MapSearch fromSearch = new MapSearchImpl();
    /**
     * 到
     */
    protected MapSearch toSearch = new MapSearchImpl();
    protected MyHandler myHandler;

    /**
     * 是否选择显示到 哪里搜索
     */
    protected boolean isToSearch;
    /**
     * 规划线路标识 true成功 false失败
     */
    protected boolean planSuccess;
    /**
     * 是否显示搜索框
     */
    boolean isSearchBoxShow;
    /**
     * 是否显示搜索位置列表
     */
    boolean isFlSearchShow;
    /**
     * 切换起点和终点的位置 true 还原回 false 互换
     */
    private boolean isChanger;

    protected Location location;
    /**
     * 没有规划地图的定位信息
     */
    public LatLng mNoPlanLocation;

    private BottomSheetBehavior<FrameLayout> bottomSheetBehavior;
    MapSearchAdapter mapSearchAdapter;

    public Comeback comeback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_map_search, null);

        mInitialView = new InitialView(view);
        myHandler = new MyHandler(this);
        location = Injections.getLocationBiz().getLast();

        initSearchCache();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 导航规划显示
        mNaviPlanUtil = new NaviPlanUtil();
        mNaviPlanUtil.init(getContext(), getView(), getChildFragmentManager(), weakReferenceAMap.get());
        // 导航 线路规划成功 回调
        navComeback();
        // 初始化搜索
        searchInputInit();
        // 是否显示定位按钮
        setLocationButton(false);
        //  初始化没有规划线路的地图
        initNoPlanMap(savedInstanceState);
        // 自动导航
        autoNav();
    }

    /**
     * 初始化没有规划线路的地图
     *
     * @param savedInstanceState Bundle
     */
    private void initNoPlanMap(Bundle savedInstanceState) {
        mInitialView.mapNoPlan.onCreate(savedInstanceState);
        mInitialView.mapNoPlan.getMap().moveCamera(CameraUpdateFactory.zoomTo(15f));
        mInitialView.mapNoPlan.getMap().setMyLocationEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 请求间隔
        myLocationStyle.interval(LOCATION_INTERVAL);
        // 请求类型
        mInitialView.mapNoPlan.getMap().setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER));
        mInitialView.mapNoPlan.getMap().setMyLocationStyle(myLocationStyle);
        mInitialView.mapNoPlan.getMap().setOnMyLocationChangeListener(location -> {

            try {
                // 如果缓存位置是空的，就取一个
                if (mNoPlanLocation == null) {
                    mNoPlanLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mInitialView.mapNoPlan.getMap().moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(mNoPlanLocation, 15, 0, 0)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mInitialView.mapNoPlan.getMap().getUiSettings().setMyLocationButtonEnabled(true);
        mInitialView.mapNoPlan.getMap().getUiSettings().setCompassEnabled(false);
        mInitialView.mapNoPlan.getMap().getUiSettings().setZoomControlsEnabled(false);
    }

    @Override
    public void onResume() {
        if (mInitialView == null) return;
        mInitialView.mapNoPlan.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mInitialView == null) return;
        mInitialView.mapNoPlan.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mInitialView == null) return;
        mInitialView.unbinder.unbind();
        mNaviPlanUtil.onDestroy();
        if (mInitialView != null && mInitialView.mapNoPlan != null) {
            mInitialView.mapNoPlan.onDestroy();
        }
    }


    /**
     * 打开搜索地图
     *
     * @param address 自动导航地址
     * @return Fragment
     */
    public static MapSearchFragment newInstance(String address) {
        Bundle args = new Bundle();
        MapSearchFragment fragment = new MapSearchFragment();
        if (address != null) {
            args.putString(SEARCH_KEYWORD, address);
        }
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 打开搜索地图
     *
     * @return Fragment
     */
    public static MapSearchFragment newInstance() {
        return newInstance(null);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    /**
     * 描    述：初始化缓存
     * 作    者：韦理英
     * 时    间：
     * <p>
     * return list 实体
     */

    public void initSearchCache() {
        // 获取缓存
        String cache = SPUtils.getInstance().getString(SEARCH_KEYWORD);
        // 转成json
        if (cache != null && cache.length() > 5) {
            cacheList = new Gson().fromJson(cache, new TypeToken<ArrayList<SearchKeyword>>() {
            }.getType());
            showData(cacheList);
        } else {
            cacheList = new ArrayList<>();
        }
    }


    /**
     * 描    述：过滤重复
     * 作    者：韦理英
     * 时    间：
     *
     * @param word [参数说明]
     *             return
     *             [return说明]
     */

    @Override
    public boolean filterWord(SearchKeyword word) {
        // 空名字不保存
        if (word == null || TextUtils.isEmpty(word.getName())) return true;

        // 保存
        if (cacheList.isEmpty()) return false;

        // 获取列表
        for (SearchKeyword searchKeyword : cacheList) {
            if (searchKeyword.getName().equals(word.getName())) {
                // 不保存
                return true;
            }
        }
        return false;
    }

    /**
     * 保存缓存
     *
     * @param keyword 关键词
     */
    @Override
    public void saveSearchCache(SearchKeyword keyword) {
        if (keyword == null || cacheList == null) return;
        // 过滤重复
        if (!filterWord(keyword)) {
            // 保存
            cacheList.add(keyword);
            // 实体转json
            String json = new Gson().toJson(cacheList);
            // 保存json
            SPUtils.getInstance().put(SEARCH_KEYWORD, json);
        }
    }

    /**
     * 描    述：清除缓存
     * 作    者：韦理英
     * 时    间：
     */

    protected void clearCache() {
        cacheList.clear();
        SPUtils.getInstance().put(SEARCH_KEYWORD, "");
        ArrayList<SearchKeyword> result = new ArrayList<>();
        showData(result);
    }

    /**
     * 搜索兴趣点
     *
     * @param keyword 关键词
     * @param city    城市
     */
    @Override
    public void searchPoi(String keyword, String city) {
        if (isToSearch) {
            toSearch.searchPoi(keyword, false, city, myHandler);
        } else {
            fromSearch.searchPoi(keyword, false, city, myHandler);
        }
    }

    public LatLng getTarget() {
        Location location = Injections.getLocationBiz().getLast();
        LatLng latLng = null;
        if (location != null) {
            latLng = new LatLng(location.getLat(), location.getLon());
        } else {
            XLog.st(3).e("搜索初始化失败，没有获取手机定位信息，请检查是否同意定位权限");
        }

        return latLng;
    }

    /**
     * 自动导航
     */
    @Override
    public void autoNav() {
        String address = null;
        // 获取自动搜索的关键词
        if (getArguments() != null) {
            address = (String) getArguments().get(SEARCH_KEYWORD);
        }
        if (TextUtils.isEmpty(address)) return;
        // 设置 到 搜索
        isToSearch = true;
        GetCity getCity = new GetCity(address).build();
        String keyWork = getCity.getKeyWork();
        String city = getCity.getCity();
        searchPoi(keyWork, city);
    }

    /**
     * 初始化搜索
     */
    @Override
    public void searchInputInit() {
        String city = "广州";
        Location location = Injections.getLocationBiz().getLast();
        if (location != null) {
            city = location.getCity();
        }
        fromSearch.init(getContext(), weakReferenceAMap.get(), MapSearchFragment.this::showData, city);
        fromSearch.setCurrentLocation(getTarget());
        zoom(15f);
        toSearch.init(getContext(), weakReferenceAMap.get(), MapSearchFragment.this::showData, city);
    }

    /**
     * 显示搜索结果
     *
     * @param result 搜索结果
     */
    @Override
    public void showData(List<SearchKeyword> result) {
        mapSearchAdapter.getList().clear();
        mapSearchAdapter.appendToList(result);
        mapSearchAdapter.notifyDataSetChanged();
    }

    /**
     * 显示底部tab面板，导航规划
     */
    @Override
    public void showSearchDialog() {
        isSearchBoxShow = true;
        mInitialView.searchBox.setVisibility(View.VISIBLE);
        mInitialView.mapNoPlan.setVisibility(View.GONE);

        if (planSuccess) {
            // 如果规划成功
            mInitialView.tabLayout.setVisibility(View.VISIBLE);
            mInitialView.llTab.setVisibility(View.VISIBLE);
            mNaviPlanUtil.mPlanFragment.isShowNavUi(true);
            bottomCollapsed();
        } else {
            // 规划失败
            mInitialView.llTab.setVisibility(View.GONE);
            mInitialView.tabLayout.setVisibility(View.GONE);
            mInitialView.llTab.setVisibility(View.GONE);
            mNaviPlanUtil.mPlanFragment.isShowNavUi(false);
        }
    }

    /**
     * 隐藏底部导航面板
     */
    @Override
    public void hideSearchDialog() {
        isSearchBoxShow = false;
        hideKeyboard2();
        mInitialView.searchBox.setVisibility(View.GONE);
        mInitialView.llTab.setVisibility(View.GONE);
        // hide刷新规划
        mInitialView.ivRefresh.setVisibility(View.GONE);
        if (comeback != null) {
            comeback.hideSearchDialog();
        }
    }

    /**
     * 导航 线路规划成功 回调
     */
    @Override
    public void navComeback() {
        MapNavi.OnDrawRoute onDrawRoute = routeOverlays -> {
            try {
                if (mInitialView == null || mInitialView.tabLayout == null)
                    return;
                ULog.i("routeOverlays size:" + routeOverlays.size());
                planSuccess = true;
                MapSearchFragment.this.showSearchDialog();
                // 地图缩小一级
                MapSearchFragment.this.zoomOut();
                // 显示方案
                mNaviPlanUtil.setSchemeUI(routeOverlays);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        mNaviPlanUtil.setOnDrawRoute(onDrawRoute);
    }

    @Subscribe
    public void searchEvent(SearchRoadEvent event) {
        isChanger = true;
        searchPlanLingMap();
    }

    /**
     * 搜索线路地图
     */
    @Override
    public void searchPlanLingMap() {
        if (weakReferenceAMap.get() == null) return;

        ULog.i("searchPlanLingMap");
        LatLng to;
        LatLng from;
        // 是否切换起始位置
        if (isChanger) {
            to = toSearch.getSelected();
            from = fromSearch.getSelected();
        } else {
            to = fromSearch.getSelected();
            from = toSearch.getSelected();
        }
        if (to == null || from == null) {
            return;
        }
        // 设置搜索里面的经纬度
        toSearch.setCurrentLocation(to);
        fromSearch.setCurrentLocation(from);

        weakReferenceAMap.get().clear();
        List<LatLng> list = new ArrayList<>();
        list.add(from);
        list.add(to);
        // 缩放到显示线路
        zoomToSpan(list);
        // 开始规划
        mNaviPlanUtil.startCalculate(from, null, to);
        showSearchDialog();
    }

    /**
     * 描    述：显示一半底部
     * 作    者：韦理英
     * 时    间：
     */

    protected void bottomCollapsed() {
        if (mInitialView == null) return;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void updateLocation() {
        App.locationInit();
    }

    /**
     * 设置搜索框的关键词
     *
     * @param word 关键字
     */
    @Override
    public void setSearchText(String word) {
        if (TextUtils.isEmpty(word) || mInitialView == null
                || mInitialView.tvToKeyWord == null || mInitialView.tvFromKeyWord == null) return;
        if (isToSearch) {
            mInitialView.tvToKeyWord.setText(word);
        } else {
            mInitialView.tvFromKeyWord.setText(word);
        }
    }

    /**
     * 描    述：搜索关键词，显示在列表上
     * 作    者：韦理英
     * 时    间：
     *
     * @param word [关键词]
     */
    @Override
    public void searchKeyword(String word) {
        if (isToSearch) {
            toSearch.searchKeyword(word);
        } else {
            fromSearch.searchKeyword(word);
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    /**
     * 回调
     */
    interface Comeback {
        // hide search dialog
        void hideSearchDialog();
    }

    /**
     * 描    述：initView类
     * 作    者：韦理英
     * 时    间：
     */
    public final class InitialView {
        @BindView(R.id.ivSearchBoxBack)
        ImageView ivSearchBoxBack;
        @BindView(R.id.tvFromKeyWord)
        TextView tvFromKeyWord;
        @BindView(R.id.tvToKeyWord)
        TextView tvToKeyWord;
        @BindView(R.id.ivChanger)
        ImageView ivChanger;
        @BindView(R.id.searchBox)
        LinearLayout searchBox;
        @BindView(R.id.map)
        TextureMapView map;
        @BindView(R.id.mapNoPlan)
        TextureMapView mapNoPlan;
        @BindView(R.id.ivRefresh)
        ImageView ivRefresh;
        @BindView(R.id.ivSetNav)
        ImageView ivSetNav;
        @BindView(R.id.ivLocation)
        ImageView ivLocation;
        @BindView(R.id.llMap)
        LinearLayout llMap;
        @BindView(R.id.tabLayout)
        TabLayout tabLayout;
        @BindView(R.id.llTab)
        LinearLayout llTab;
        @BindView(R.id.viewPager)
        ViewPager viewPager;
        @BindView(R.id.bottom_sheet)
        FrameLayout bottomSheet;
        @BindView(R.id.imReturn)
        ImageView imReturn;
        @BindView(R.id.etKeyword)
        EditText etKeyword;
        @BindView(R.id.tvSearchButton)
        TextView tvSearchButton;
        @BindView(R.id.tvMyLocal)
        TextView tvMyLocal;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.tvClear)
        TextView tvClear;
        @BindView(R.id.flSearch)
        FrameLayout flSearch;
        Unbinder unbinder;

        public InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);
            initView();
            initListener();
        }

        protected void initView() {
            // 导航规划面板
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_SETTLING);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_DRAGGING);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight((int) getResources().getDimension(R.dimen.peekHeight));
            bottomSheetBehavior.setHideable(false);

            recyclerView.setBackgroundColor(UiUtils.getColor(R.color.white));

            // bottom 默认不显示
            llTab.setVisibility(View.GONE);

            ivSearchBoxBack.setImageResource(R.drawable.ic_close);
            mapSearchAdapter = new MapSearchAdapter();

            RecyclerViewUtils.initPage(recyclerView, mapSearchAdapter, null, getContext(), true);
            recyclerView.setNestedScrollingEnabled(false);

            tabLayout.setSmoothScrollingEnabled(true);
            tabLayout.setSelectedTabIndicatorHeight(10);

            // 刷新规划线路
            // initMapCustomView(ivRefresh);
            // 定位
            initMapCustomView(ivLocation);
            // 设置导航信息
//            initMapCustomView(ivSetNav);
        }

        private void initMapCustomView(ImageView iv) {
            iv.setVisibility(View.VISIBLE);
//            iv.getBackground().setAlpha(90);
        }

        protected void initListener() {
            // 导航规划面板滑动监听
            bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    ULog.i("slideOffset=" + slideOffset);
                    // 没有规划成功，不能操作
                    if (!planSuccess) {
                        return;
                    }

                    // 全部显示
                    if (slideOffset >= 1) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        mNaviPlanUtil.mPlanFragment.isShowBottomNav(true);
                    } else if (slideOffset == 0) {  // 折叠状态
                        mNaviPlanUtil.mPlanFragment.isShowBottomNav(false);
                    }
                }
            });

            // 刷新规划线路
            ivRefresh.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                // 如果本地位置未改变，起点位置和搜索起点一样，如果一样就不需要再搜索
                if (!isLocationChange || weakReferenceLatLng.get().latitude == toSearch.getSelected().latitude || weakReferenceLatLng.get().latitude == fromSearch.getSelected().latitude) {
                    showToast("重算线路与当前一致，请稍候再试");
                    return;
                }
                // 如果规划的线路不是和手机位置一样，就允许跳到手机位置，参照高德
                float distance = AMapUtils.calculateLineDistance(weakReferenceLatLng.get(), fromSearch.getSelected());
                // 如果手机起点位置和 从搜索框 多1公里，就不规划手机位置
                if (distance > 1000) {
                    return;
                }
                // 设置搜索经纬度
                fromSearch.setCurrentLocation(weakReferenceLatLng.get());
                searchPlanLingMap();
            });

            // 定位
            ivLocation.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                movePoint(weakReferenceLatLng.get());
            });

            // 设置导航信息
            ivSetNav.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                NavSettingDialogFragment dialogFragmengt = new NavSettingDialogFragment();
                dialogFragmengt.setmNavSetting(mNaviPlanUtil.mNavSetting);
                dialogFragmengt.setmComeback(navSetting -> {
                    // 传递 线路参数
                    mNaviPlanUtil.mNavSetting = navSetting;
                    // 保存缓存
                    mNaviPlanUtil.saveNavCache();
                    // 搜索线路
                    searchPlanLingMap();
                });
                dialogFragmengt.show(getFragmentManager(), "NavSetting");
            });

            // 我的位置
            tvMyLocal.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                if (location != null && !TextUtils.isEmpty(location.getAddress())) {
                    flSearch.setVisibility(View.GONE);
                    GetCity getCity = new GetCity(location.getAddress()).build();
                    String keyWork = getCity.getKeyWork();
                    String city = getCity.getCity();
                    searchPoi(keyWork, city);
                    setSearchText(keyWork);
                    hideKeyboard2();
                } else {
                    App.locationInit();
                    showToast("获取我的位置信息失败,请重试！");
                }
            });

            // 搜索
            tvSearchButton.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                hideKeyboard2();
                if (TextUtils.isEmpty(etKeyword.getText().toString())) {
                    showToast("请输入关键词");
                    return;
                }
                setSearchText(etKeyword.getText().toString());
                searchKeyword(etKeyword.getText().toString());
                SearchKeyword keyword = new SearchKeyword();
                keyword.setType(2);
                keyword.setName(etKeyword.getText().toString());
                saveSearchCache(keyword);
                YoYo.with(Techniques.FadeIn).playOn(mInitialView.recyclerView);
            });

            // 搜索列表返回
            imReturn.setOnClickListener(v -> {
                hideKeyboard2();
                flSearch.setVisibility(View.GONE);
            });

            // 清空搜索历史
            tvClear.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                clearCache();
            });

            // 搜索 点击事件
            mapSearchAdapter.setOnItemClickListener((itemView, item, position) -> {
                hideKeyboard2();
                SearchKeyword searchKeyword = mapSearchAdapter.getItem(position);
                flSearch.setVisibility(View.GONE);
                searchPoi(searchKeyword.getName(), location.getCity());
                setSearchText(searchKeyword.getName());
                searchKeyword.setType(1);
                saveSearchCache(searchKeyword);
            });

            // hide setSearchEditView dialog
            ivSearchBoxBack.setOnClickListener(v -> pop());

            // 从
            tvFromKeyWord.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                if (isToSearch) {
                    etKeyword.setText("");
                    clearSearchResult();
                }
                isToSearch = false;
                toSearch.setCity(location.getCity());
                etKeyword.setHint("输入地点");
                etKeyword.requestFocus();
                flSearch.setVisibility(View.VISIBLE);
                showData(cacheList);
                hideKeyboard2();
                isFlSearchShow = true;
                listenerToAndFromComeback();
            });

            // 到
            tvToKeyWord.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                if (!isToSearch) {
                    etKeyword.setText("");
                    clearSearchResult();
                }
                isToSearch = true;
                etKeyword.setHint("输入地点");
                etKeyword.requestFocus();
                flSearch.setVisibility(View.VISIBLE);
                showData(cacheList);
                hideKeyboard2();
                isFlSearchShow = true;
                listenerToAndFromComeback();
                toSearch.setCity(location.getCity());
            });

            // 搜索文字返回
            etKeyword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ULog.i("搜索=" + s);
                    if (!TextUtils.isEmpty(s)) {
                        searchKeyword(s.toString());
                    } else {
                        clearSearchResult();
                        showData(cacheList);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            // 切换
            ivChanger.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                String toStr = tvToKeyWord.getText().toString();
                String fromStr = tvFromKeyWord.getText().toString();
                if (toStr.startsWith(UiUtils.getString(R.string.destination))) {
                    showToast("请先搜索目的地，才能切换");
                    return;
                }
                if (isChanger) { // 还原
                    isChanger = false;
                    tvToKeyWord.setText(fromStr);
                    tvFromKeyWord.setText(toStr);
                } else { // 切换
                    isChanger = true;
                    tvToKeyWord.setText(fromStr);
                    tvFromKeyWord.setText(toStr);
                }
                searchPlanLingMap();
            });
        }

        /**
         * 监听返回键，按返回键就隐藏 搜索关键字面板
         */
        private void listenerToAndFromComeback() {
            UiUtils.listenerBackKey(flSearch, () -> flSearch.setVisibility(View.GONE));
        }

        /**
         * 清除搜索结果
         */
        private void clearSearchResult() {
            mapSearchAdapter.getList().clear();
            mapSearchAdapter.notifyDataSetChanged();
        }
    }

    protected static class MyHandler extends Handler {
        final WeakReference<MapSearchFragment> mapSearchFragmentWeakReference;

        MyHandler(MapSearchFragment mapSearchFragmentWeakReference) {
            this.mapSearchFragmentWeakReference = new WeakReference<>(mapSearchFragmentWeakReference);
        }

        @Override
        public void dispatchMessage(Message msg) {
            MapSearchFragment mapSearchFragment = mapSearchFragmentWeakReference.get();

            if (msg.what == 0 && msg.obj != null && mapSearchFragment != null) {
                mapSearchFragment.setSearchText((String) msg.obj);
            }

            super.dispatchMessage(msg);
        }
    }

    /**
     * 描    述：[说明]  获取详细地址关键字
     * 作    者：韦理英
     * 时    间：
     */
    private class GetCity {
        private String address;
        private String city;
        private String keyWork;

        GetCity(String address) {
            this.address = address;
        }

        String getCity() {
            return city;
        }

        String getKeyWork() {
            return keyWork;
        }

        public GetCity build() {
            if ((address.contains("省") || address.contains("自治区")) && !address.contains("市")) {  // 只有省，取省
                city = address.substring(0, 2);
            } else if ((address.contains("省") || address.contains("自治区")) && address.contains("市")) { // 有省，有市，取市
                if (!address.contains("自治区")) {
                    city = address.substring(3, 5);
                } else {
                    city = address.substring(address.indexOf("自治区"), address.indexOf("自治区") + 5).replace("自治区", "");
                }
            } else if (address.contains("市")) { // 只有市
                city = address.substring(0, 2);
            } else {
                city = "";
            }

            if (address.contains("市")) {
                keyWork = address.substring(address.indexOf("市"), address.length());
                keyWork = keyWork.substring(1, keyWork.length());
            } else {
                keyWork = address;
            }
            return this;
        }
    }
}
