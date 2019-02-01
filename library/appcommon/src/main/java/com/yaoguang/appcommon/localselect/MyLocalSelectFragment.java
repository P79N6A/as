package com.yaoguang.appcommon.localselect;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.localselect.adapter.MyLocalSelectAdapter;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.GetJsonDataUtil;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.entity.ProvinceBean;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.map.location.impl.LocationManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.REQUESTCODE_PERSONAL_INFORMATION;


/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/27
 * 描    述：
 * 位置选择(省市区)
 * <p>
 * update zhongjh
 * data 2018-03-27
 * <p>
 * =====================================
 */

public class MyLocalSelectFragment extends BaseFragment2 {

    public final static String SELECT_LOCAL_RESULT = "selectLocalResult";
    private final static String IS_SHOW_AREA = "isShowArea";

    ProvinceBeans mProvinceBeans;

    boolean isShowArea;

    String mProvince;
    String mCity;
    String mArea;
    int mIntProvince;
    int mIntCity;
    int mIntArea;

    List<String> mProvinceList = new ArrayList<>();
    List<String> mCityList = new ArrayList<>();
    List<String> mAreaList = new ArrayList<>();

    private ImageView imgReturn;
    private TextView toolbar_title;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyLocalSelectAdapter mMyLocalSelectAdapter;

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public static MyLocalSelectFragment newInstance(boolean isShowArea) {

        Bundle args = new Bundle();

        MyLocalSelectFragment fragment = new MyLocalSelectFragment();
        args.putBoolean(IS_SHOW_AREA, isShowArea);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_local_select;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        imgReturn = view.findViewById(R.id.imgReturn);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recyclerView);

        if (getArguments() != null) {
            isShowArea = getArguments().getBoolean(IS_SHOW_AREA);
        }

        toolbar_title.setText("显示地区");
        recyclerView.setBackgroundColor(UiUtils.getColor(R.color.window_background));

        mMyLocalSelectAdapter = new MyLocalSelectAdapter();
        RecyclerViewUtils.initPage(recyclerView, mMyLocalSelectAdapter, null, getContext(), false);

        // 设置当前地址
        LocationManager locationManager = new LocationManager();
        locationManager.init(BaseApplication.getInstance().getBaseContext());
        locationManager.setComeback(location -> {
            locationManager.destroyLocation();
            String province = location.getProvince();
            String city = location.getCity();
            String district = location.getDistrict();
            mMyLocalSelectAdapter.setProvince(province);
            mMyLocalSelectAdapter.setCity(city);
            mMyLocalSelectAdapter.setArea(district);
        });


        mMyLocalSelectAdapter.setShowArea(isShowArea);
    }

    @Override
    public void init() {
        // 处理数据，显示省
        Observable.create((ObservableOnSubscribe<List<String>>) e -> {
            ProvinceBeans provinceBeans = handlerJsonData();
            mProvinceBeans = provinceBeans;
            List<String> provinceList = new ArrayList<>();
            for (ProvinceBean bean : provinceBeans.getProvinces()) {
                if (!TextUtils.isEmpty(bean.getName())) {
                    provinceList.add(bean.getName());
                }
            }
            e.onNext(provinceList);
        }).subscribe(new Observer<List<String>>() {        //观察者
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<String> value) {
                mProvinceList = value;
                mMyLocalSelectAdapter.setShowMyLocation(true);
                mMyLocalSelectAdapter.getList().clear();
                mMyLocalSelectAdapter.appendToList(value);
                mMyLocalSelectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void initListener() {
        mMyLocalSelectAdapter.setOnItemClickListener((itemView, item, position) -> {
            ULog.i(item.toString());

            // 显示市
            if (mProvince == null) {
                mProvince = item.toString();
                mIntProvince = position;
                // 显示市
                List<ProvinceBean.CityBean> cityList = mProvinceBeans.getProvinces().get(mIntProvince).getCityList();
                Flowable.just(cityList).map(provinceBean -> {
                    List<String> list = new ArrayList<>();
                    for (ProvinceBean.CityBean cityBean : provinceBean) {
                        if (!TextUtils.isEmpty(cityBean.getName())) {
                            list.add(cityBean.getName());
                        }
                    }
                    return list;
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(strings -> {
                            mCityList = strings;

                            mMyLocalSelectAdapter.setShowMyLocation(false);
                            mMyLocalSelectAdapter.getList().clear();
                            mMyLocalSelectAdapter.appendToList(strings);
                            mMyLocalSelectAdapter.notifyDataSetChanged();
                        });
                return;
            }

            // 显示区
            if (mCity == null) {
                mCity = item.toString();
                mIntCity = position;

                if (isShowArea) {
                    List<String> areaList = mProvinceBeans.getProvinces().get(mIntProvince).getCityList().get(mIntCity).getArea();
                    mMyLocalSelectAdapter.setShowMyLocation(false);
                    mMyLocalSelectAdapter.getList().clear();
                    mMyLocalSelectAdapter.appendToList(areaList);
                    mMyLocalSelectAdapter.notifyDataSetChanged();
                } else {
                    // 返回省市结果
                    String result = mProvince + "," + mCity;
                    Bundle bundle = new Bundle();
                    bundle.putString(SELECT_LOCAL_RESULT, result);
                    setFragmentResult(RESULT_OK, bundle);
                    pop();
                }
                return;
            }

            // 返回省市区结果
            if (mArea == null) {
                mArea = item.toString();

                String result = mProvince + "," + mCity + "," + mArea;
                Bundle bundle = new Bundle();
                bundle.putString(SELECT_LOCAL_RESULT, result);
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });


        // 直接选择定位结果
        mMyLocalSelectAdapter.setOnAddressClick((province, city, area) -> {
            String result = province + "," + city + "," + area;
            Bundle bundle = new Bundle();
            bundle.putString(SELECT_LOCAL_RESULT, result);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });

        imgReturn.setOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
    }

    private ProvinceBeans handlerJsonData() {

        ProvinceBeans provinceBeans = new ProvinceBeans();

        /*
          注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
          关键逻辑在于循环体
          */
        String jsonData = new GetJsonDataUtil().getJson(BaseApplication.getInstance().getBaseContext(), "province.json");//获取assets目录下的json文件数据

        ArrayList<ProvinceBean> provinceBean = parseData(jsonData);//用Gson 转成实体

        /*
          添加省份数据

          注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
          PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        provinceBeans.setProvinces(provinceBean);

        for (int i = 0; i < provinceBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < provinceBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = provinceBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> cityAreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (provinceBean.get(i).getCityList().get(c).getArea() == null
                        || provinceBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    cityAreaList.add("");
                } else {
                    for (int d = 0; d < provinceBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = provinceBean.get(i).getCityList().get(c).getArea().get(d);

                        cityAreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(cityAreaList);//添加该省所有地区数据
            }

            /*
              添加城市数据
             */
            provinceBeans.getCitys().add(CityList);

            /*
              添加地区数据
             */
            provinceBeans.getAreas().add(Province_AreaList);
        }
        return provinceBeans;
    }

    /**
     * Gson 解析
     *
     * @param result 字符串数据
     * @return 返回实体类集合
     */
    private ArrayList<ProvinceBean> parseData(String result) {
        ArrayList<ProvinceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    public boolean onBackPressedSupport() {

        // 从区返回显示市
        if (mCity != null) {
            mMyLocalSelectAdapter.setShowMyLocation(false);
            mMyLocalSelectAdapter.getList().clear();
            mMyLocalSelectAdapter.appendToList(mCityList);
            mMyLocalSelectAdapter.notifyDataSetChanged();

            mArea = null;
            mCity = null;

            return true;
        }
        // 从市返回省
        if (mProvince != null) {
            mMyLocalSelectAdapter.setShowMyLocation(true);
            mMyLocalSelectAdapter.getList().clear();
            mMyLocalSelectAdapter.appendToList(mProvinceList);
            mMyLocalSelectAdapter.notifyDataSetChanged();

            mArea = null;
            mCity = null;
            mProvince = null;
            return true;
        }

        pop();
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
