package com.yaoguang.lib.common;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.entity.ProvinceBean;
import com.yaoguang.lib.entity.ProvinceBeans;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by 韦理英
 * on 2017/7/20 0020.
 */

public class ProvincesCitysAreasSelectUtils implements Runnable {
    static OptionsPickerView pvOptions;
    public final int PROVINCE_BEANS = 0x001;
    ComeBack comeBack;

    public interface ComeBack {
        void result(ProvinceBeans beans);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROVINCE_BEANS:
                    ProvinceBeans provinceBeans = (ProvinceBeans) msg.obj;
                    comeBack.result(provinceBeans);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 初始化点击监听
     * 在点击 事件运行
     * @param context
     * @param listner
     */
    public void initView(Context context, OptionsPickerView.OnOptionsSelectListener listner) {
        pvOptions = new OptionsPickerView.Builder(context, listner)
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
    }

    /**
     * 数据初始化
     * 在onCreate初始化
     * @param comeBack
     */
    public void init(ComeBack comeBack) {
        this.comeBack = comeBack;
        new Thread(this).start();
    }

    /**
     * 解析数据
     */
    private ProvinceBeans handlerJsonData() {

        ProvinceBeans provinceBeans = new ProvinceBeans();

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String jsonData = new GetJsonDataUtil().getJson(BaseApplication.getInstance().getBaseContext(), "province.json");//获取assets目录下的json文件数据

        ArrayList<ProvinceBean> provinceBean = parseData(jsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        provinceBeans.setProvinces(provinceBean);

        for (int i = 0; i < provinceBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < provinceBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = provinceBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (provinceBean.get(i).getCityList().get(c).getArea() == null
                        || provinceBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < provinceBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = provinceBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            provinceBeans.getCitys().add(CityList);

            /**
             * 添加地区数据
             */
            provinceBeans.getAreas().add(Province_AreaList);
        }
        Message message = new Message();
        message.what = PROVINCE_BEANS;
        message.obj = provinceBeans;
        mHandler.sendMessage(message);
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
    public void run() {
        handlerJsonData();
    }

    /**
     * 显示
     * 在点击 事件运行
     * @param beans
     */
    public void showProvince(ProvinceBeans beans) {
        if (beans != null) {
            pvOptions.setPicker(beans.getProvinces());//三级选择器
        }
        if (pvOptions != null) {
            pvOptions.show();
        }
    }
    /**
     * 显示
     * 在点击 事件运行
     * @param beans
     */
    public void showProvinceCity(ProvinceBeans beans) {
        if (beans != null) {
            pvOptions.setPicker(beans.getProvinces(), beans.getCitys());//三级选择器
        }
        if (pvOptions != null) {
            pvOptions.show();
        }
    }
    /**
     * 显示
     * 在点击 事件运行
     * @param beans
     */
    public void showProvinceCityAreas(ProvinceBeans beans) {
        if (beans != null) {
            pvOptions.setPicker(beans.getProvinces(), beans.getCitys(), beans.getAreas());//三级选择器
        }
        if (pvOptions != null) {
            pvOptions.show();
        }
    }
}
