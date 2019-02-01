package com.yaoguang.driver.nav.search.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yaoguang.appcommon.common.eventbus.SearchRoadEvent;
import com.yaoguang.common.common.ULog;
import com.yaoguang.driver.R;
import com.yaoguang.driver.nav.search.MapSearch;
import com.yaoguang.driver.nav.search.model.SearchKeyword;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.map.common.AMapUtil;
import com.yaoguang.map.common.PoiOverlay;
import com.yaoguang.map.common.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：韦理英
 * 时间： 2017/5/15 0015.
 */
public class MapSearchImpl implements MapSearch {
    private Comeback comeback;
    private AMap aMap;
    //    private String keyWord = "";// 要输入的poi搜索关键字
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private Context mContext;
    private LatLng selectedLatLng;
    private String keyword;
    private String city;

    /**
     * 初始化AMap对象
     */

    public void init(final Context context, AMap aMap, Comeback comeback, String city) {
        mContext = context;
        this.comeback = comeback;
        this.aMap = aMap;
        this.city = city;
        aMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            MapSearchImpl.this.setSelected(marker.getPosition());
            return false;
        });// 添加点击marker监听事件
        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View view = View.inflate(context, R.layout.poikeywordsearch_uri,
                        null);
                TextView title = view.findViewById(R.id.title);
                title.setText(marker.getTitle());

                TextView snippet = view.findViewById(R.id.snippet);
                snippet.setText(marker.getSnippet());

                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
    }

    /**
     * 描    述：输入关键词获取结束
     * 作    者：韦理英
     * 时    间：
     *
     * @param newText 关键字
     */
    @Override
    public void searchKeyword(String newText) {
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, city);
            Inputtips inputTips = new Inputtips(mContext, inputquery);
            inputTips.setInputtipsListener((tipList, rCode) -> {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
                    List<SearchKeyword> list = new ArrayList<>();
                    Location latLonPoint = MapSearchImpl.this.getLatLon();
                    for (int i = 0; i < tipList.size(); i++) {
                        SearchKeyword searchKeyword = new SearchKeyword();
                        Tip tip = tipList.get(i);
                        searchKeyword.setId(i + 1 + "");
                        searchKeyword.setName(tip.getName());
                        searchKeyword.setAddress(tip.getAddress());
                        if (latLonPoint != null && tip.getPoint() != null) {
                            try {
                                searchKeyword.setKm(latLonPoint, tip.getPoint());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        list.add(searchKeyword);
                    }
                    if (comeback != null) {
                        comeback.searchResult(list);
                    }
//                    aAdapter = new ArrayAdapter<String>(
//                            context,
//                            R.layout.route_inputs, listString);
//                    searchText.setAdapter(aAdapter);
//                    aAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast(mContext, rCode);
                }
            });
            inputTips.requestInputtipsAsyn();
        }
    }

    private Location getLatLon() {
        Location location = Injections.getLocationBiz().getLast();
        if (location != null && location.getLat() != 0) {
            return location;
        }
        return null;
    }

    /**
     * 获取搜索位置 经纬度
     */
    @Override
    public LatLng getSelected() {
        return selectedLatLng;
    }

    /**
     * 设置搜索位置 经纬度
     *
     * @param object 列表或经纬度对象
     */
    private void setSelected(Object object) {
        if (object instanceof List) {
            List<PoiItem> poiItems = (List<PoiItem>) object;
            LatLonPoint point = poiItems.get(0).getLatLonPoint();
            selectedLatLng = new LatLng(point.getLatitude(), point.getLongitude());
        } else {
            selectedLatLng = (LatLng) object;
        }
    }

    /**
     * 设置搜索位置 经纬度
     *
     * @param latLng LatLng
     */
    @Override
    public void setCurrentLocation(LatLng latLng) {
        selectedLatLng = latLng;
    }


    /**
     * 点击搜索按钮
     *
     * @param keyword  关键词
     * @param quiet    是否显示对话框
     * @param province 省份城市
     * @param handler  handler
     */
    @Override
    public void searchPoi(String keyword, boolean quiet, String province, Handler handler) {
        this.keyword = keyword;
        if (!TextUtils.isEmpty(province)) {
            city = province;
        }
        ULog.i("搜索城市=" + city + "搜索关键字=" + keyword);
//        keyWord = AMapUtil.checkEditText(searchText);
        if ("".equals(keyword)) {
            ToastUtil.show(mContext, "请输入搜索关键字");
        } else {
            doSearchQuery(keyword, city, quiet, handler);
        }
    }

    @Override
    public String getKeyword() {
        return keyword;
    }

    /**
     * 点击下一页按钮
     */
    public void nextButton() {
        if (query != null && poiSearch != null && poiResult != null) {
            if (poiResult.getPageCount() - 1 > currentPage) {
                currentPage++;
                query.setPageNum(currentPage);// 设置查后一页
                poiSearch.searchPOIAsyn();
            } else {
                ToastUtil.show(mContext,
                        R.string.no_result);
            }
        }
    }

    /**
     * 显示进度框
     *
     * @param keyWord 关键词
     */
    private void showProgressDialog(String keyWord) {
//        DialogHelper.showSimpleProgressDialog(mContext, "正在搜索:\n" + keyWord);
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
//        DialogHelper.onDestroy();
    }

    /**
     * 开始进行poi搜索
     *
     * @param keyWord 关键词
     * @param handler handler
     */
    private void doSearchQuery(final String keyWord, String city, final boolean quiet, final Handler handler) {
        if (!quiet)
            showProgressDialog(keyWord);// 显示进度框
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(mContext, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int rCode) {
                dissmissProgressDialog();// 隐藏对话框
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        if (result.getQuery().equals(query)) {// 是否是同一条
                            poiResult = result;
                            // 取得搜索到的poiitems有多少页
                            List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                            List<SuggestionCity> suggestionCities = poiResult
                                    .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                            if (poiItems != null && poiItems.size() > 0) {
                                aMap.clear();// 清理之前的图标
                                PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                                poiOverlay.removeFromMap();
                                poiOverlay.addToMap();
                                poiOverlay.zoomToSpan();

                                try {
                                    setSelected(poiItems);
                                    notifyEvent();
                                    if (handler != null) {
                                        Message message = new Message();
                                        message.what = 0;
                                        message.obj = keyWord;
                                        handler.sendMessage(message);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (suggestionCities != null
                                    && suggestionCities.size() > 0) {
                                showSuggestCity(suggestionCities);
                            } else {
                                if (!quiet)
                                    ToastUtil.show(mContext,
                                            R.string.no_result);
                            }
                        }
                    } else {
                        if (!quiet)
                            ToastUtil.show(mContext,
                                    R.string.no_result);
                    }
                } else {
                    ToastUtil.showToast(mContext, rCode);
                }

            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }
        });
        poiSearch.searchPOIAsyn();
    }

    private void notifyEvent() {
        EventBus.getDefault().post(new SearchRoadEvent());
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        StringBuilder infomation = new StringBuilder("推荐城市\n");
        for (int i = 0; i < cities.size(); i++) {
            infomation.append("城市名称:").append(cities.get(i).getCityName()).append("城市区号:").append(cities.get(i).getCityCode()).append("城市编码:").append(cities.get(i).getAdCode()).append("\n");
        }
        ToastUtil.show(mContext, infomation.toString());

    }

    @Override
    public void setCity(String city) {
        if (!TextUtils.isEmpty(city)) {
            this.city = city;
        }
    }
}
