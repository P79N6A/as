package com.yaoguang.greendao.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhongjh on 2017/9/8.
 */

public class F7Bean {
    /**
     * day_weather : 多云
     * night_weather : 中雨
     * night_weather_code : 08
     * index : {"yh":{"title":"较适宜","desc":"天气较好，适宜约会"},"ls":{"title":"不适宜","desc":"天气阴沉，不太适宜晾晒。"},"clothes":{"title":"热","desc":"适合穿T恤、短薄外套等夏季服装。"},"dy":{"title":"不适宜钓鱼","desc":"天气太热,不适宜垂钓。"},"sports":{"title":"较适宜","desc":"运动请注意防晒。"},"travel":{"title":"较不宜","desc":"天气很热，如外出可选择水上娱乐项目。"},"beauty":{"title":"去油","desc":"请选用露质面霜打底，水质无油粉底霜。"},"xq":{"title":"较差","desc":"天气热，容易烦躁"},"hc":{"title":"适宜","desc":"天气较好，适宜划船及嬉玩水上运动。"},"zs":{"title":"可能中暑","desc":"气温较高，易中暑人群注意阴凉休息。"},"cold":{"title":"少发","desc":"感冒机率较低，避免长期处于空调屋中。"},"gj":{"title":"适宜","desc":"这种好天气去逛街可使身心畅快放松。"},"comfort":{"title":"较差","desc":"热，感觉不舒适"},"uv":{"title":"强","desc":"涂擦SPF大于15、PA+防晒护肤品。"},"cl":{"title":"较适宜","desc":"运动请注意防晒。"},"glass":{"title":"必要","desc":"必要佩戴"},"aqi":{"title":"良好","desc":"可以正常在户外活动，易敏感人群应减少外出"},"ac":{"title":"暂缺","desc":"暂缺"},"wash_car":{"title":"适宜","desc":"无雨且风力较小，易保持清洁度。"},"mf":{"title":"暂缺","desc":"暂缺"},"ag":{"title":"暂缺","desc":"暂缺"},"pj":{"title":"适宜","desc":"炎热干燥，适宜饮用冰镇啤酒，不要贪杯。"},"nl":{"title":"暂缺","desc":"暂缺"},"pk":{"title":"不宜","desc":"天气酷热，不适宜放风筝。"}}
     * air_press : 1009 hPa
     * jiangshui : 11%
     * night_wind_power : 微风 <5.4m/s
     * day_wind_power : 微风 <5.4m/s
     * day_weather_code : 01
     * 3hourForcast : [{"wind_direction":"无持续风向","temperature_max":"30","wind_power":"微风,3","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","temperature_min":"27","hour":"8时-14时","temperature":"28"},{"wind_direction":"无持续风向","temperature_max":"30","wind_power":"微风,3","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","temperature_min":"25","hour":"14时-20时","temperature":"30"},{"wind_direction":"无持续风向","temperature_max":"30","wind_power":"微风,0","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","temperature_min":"25","hour":"20时-2时","temperature":"25"},{"wind_direction":"无持续风向","temperature_max":"25","wind_power":"微风,0","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","temperature_min":"25","hour":"2时-8时","temperature":"25"}]
     * sun_begin_end : 06:13|18:32
     * ziwaixian : 强
     * day_weather_pic : http://app1.showapi.com/weather/icon/day/01.png
     * weekday : 4
     * night_air_temperature : 24
     * day_air_temperature : 35
     * day_wind_direction : 无持续风向
     * day : 20170914
     * night_weather_pic : http://app1.showapi.com/weather/icon/night/08.png
     * night_wind_direction : 无持续风向
     */

    private String day_weather;
    private String night_weather;
    private String night_weather_code;
    private IndexBeanX index;
    private String air_press;
    private String jiangshui;
    private String night_wind_power;
    private String day_wind_power;
    private String day_weather_code;
    private String sun_begin_end;
    private String ziwaixian;
    private String day_weather_pic;
    private int weekday;
    private String night_air_temperature;
    private String day_air_temperature;
    private String day_wind_direction;
    private String day;
    private String night_weather_pic;
    private String night_wind_direction;
    @SerializedName("3hourForcast")
    private List<_$3hourForcastBeanX> _$3hourForcast;

    public String getDay_weather() {
        return day_weather;
    }

    public void setDay_weather(String day_weather) {
        this.day_weather = day_weather;
    }

    public String getNight_weather() {
        return night_weather;
    }

    public void setNight_weather(String night_weather) {
        this.night_weather = night_weather;
    }

    public String getNight_weather_code() {
        return night_weather_code;
    }

    public void setNight_weather_code(String night_weather_code) {
        this.night_weather_code = night_weather_code;
    }

    public IndexBeanX getIndex() {
        return index;
    }

    public void setIndex(IndexBeanX index) {
        this.index = index;
    }

    public String getAir_press() {
        return air_press;
    }

    public void setAir_press(String air_press) {
        this.air_press = air_press;
    }

    public String getJiangshui() {
        return jiangshui;
    }

    public void setJiangshui(String jiangshui) {
        this.jiangshui = jiangshui;
    }

    public String getNight_wind_power() {
        return night_wind_power;
    }

    public void setNight_wind_power(String night_wind_power) {
        this.night_wind_power = night_wind_power;
    }

    public String getDay_wind_power() {
        return day_wind_power;
    }

    public void setDay_wind_power(String day_wind_power) {
        this.day_wind_power = day_wind_power;
    }

    public String getDay_weather_code() {
        return day_weather_code;
    }

    public void setDay_weather_code(String day_weather_code) {
        this.day_weather_code = day_weather_code;
    }

    public String getSun_begin_end() {
        return sun_begin_end;
    }

    public void setSun_begin_end(String sun_begin_end) {
        this.sun_begin_end = sun_begin_end;
    }

    public String getZiwaixian() {
        return ziwaixian;
    }

    public void setZiwaixian(String ziwaixian) {
        this.ziwaixian = ziwaixian;
    }

    public String getDay_weather_pic() {
        return day_weather_pic;
    }

    public void setDay_weather_pic(String day_weather_pic) {
        this.day_weather_pic = day_weather_pic;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getNight_air_temperature() {
        return night_air_temperature;
    }

    public void setNight_air_temperature(String night_air_temperature) {
        this.night_air_temperature = night_air_temperature;
    }

    public String getDay_air_temperature() {
        return day_air_temperature;
    }

    public void setDay_air_temperature(String day_air_temperature) {
        this.day_air_temperature = day_air_temperature;
    }

    public String getDay_wind_direction() {
        return day_wind_direction;
    }

    public void setDay_wind_direction(String day_wind_direction) {
        this.day_wind_direction = day_wind_direction;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNight_weather_pic() {
        return night_weather_pic;
    }

    public void setNight_weather_pic(String night_weather_pic) {
        this.night_weather_pic = night_weather_pic;
    }

    public String getNight_wind_direction() {
        return night_wind_direction;
    }

    public void setNight_wind_direction(String night_wind_direction) {
        this.night_wind_direction = night_wind_direction;
    }

    public List<_$3hourForcastBeanX> get_$3hourForcast() {
        return _$3hourForcast;
    }

    public void set_$3hourForcast(List<_$3hourForcastBeanX> _$3hourForcast) {
        this._$3hourForcast = _$3hourForcast;
    }

    public static class IndexBeanX {
        /**
         * yh : {"title":"较适宜","desc":"天气较好，适宜约会"}
         * ls : {"title":"不适宜","desc":"天气阴沉，不太适宜晾晒。"}
         * clothes : {"title":"热","desc":"适合穿T恤、短薄外套等夏季服装。"}
         * dy : {"title":"不适宜钓鱼","desc":"天气太热,不适宜垂钓。"}
         * sports : {"title":"较适宜","desc":"运动请注意防晒。"}
         * travel : {"title":"较不宜","desc":"天气很热，如外出可选择水上娱乐项目。"}
         * beauty : {"title":"去油","desc":"请选用露质面霜打底，水质无油粉底霜。"}
         * xq : {"title":"较差","desc":"天气热，容易烦躁"}
         * hc : {"title":"适宜","desc":"天气较好，适宜划船及嬉玩水上运动。"}
         * zs : {"title":"可能中暑","desc":"气温较高，易中暑人群注意阴凉休息。"}
         * cold : {"title":"少发","desc":"感冒机率较低，避免长期处于空调屋中。"}
         * gj : {"title":"适宜","desc":"这种好天气去逛街可使身心畅快放松。"}
         * comfort : {"title":"较差","desc":"热，感觉不舒适"}
         * uv : {"title":"强","desc":"涂擦SPF大于15、PA+防晒护肤品。"}
         * cl : {"title":"较适宜","desc":"运动请注意防晒。"}
         * glass : {"title":"必要","desc":"必要佩戴"}
         * aqi : {"title":"良好","desc":"可以正常在户外活动，易敏感人群应减少外出"}
         * ac : {"title":"暂缺","desc":"暂缺"}
         * wash_car : {"title":"适宜","desc":"无雨且风力较小，易保持清洁度。"}
         * mf : {"title":"暂缺","desc":"暂缺"}
         * ag : {"title":"暂缺","desc":"暂缺"}
         * pj : {"title":"适宜","desc":"炎热干燥，适宜饮用冰镇啤酒，不要贪杯。"}
         * nl : {"title":"暂缺","desc":"暂缺"}
         * pk : {"title":"不宜","desc":"天气酷热，不适宜放风筝。"}
         */

        private YhBeanX yh;
        private LsBeanX ls;
        private ClothesBeanX clothes;
        private DyBeanX dy;
        private SportsBeanX sports;
        private TravelBeanX travel;
        private BeautyBeanX beauty;
        private XqBeanX xq;
        private HcBeanX hc;
        private ZsBeanX zs;
        private ColdBeanX cold;
        private GjBeanX gj;
        private ComfortBeanX comfort;
        private UvBeanX uv;
        private ClBeanX cl;
        private GlassBeanX glass;
        private AqiBeanX aqi;
        private AcBeanX ac;
        private WashCarBeanX wash_car;
        private MfBeanX mf;
        private AgBeanX ag;
        private PjBeanX pj;
        private NlBeanX nl;
        private PkBeanX pk;

        public YhBeanX getYh() {
            return yh;
        }

        public void setYh(YhBeanX yh) {
            this.yh = yh;
        }

        public LsBeanX getLs() {
            return ls;
        }

        public void setLs(LsBeanX ls) {
            this.ls = ls;
        }

        public ClothesBeanX getClothes() {
            return clothes;
        }

        public void setClothes(ClothesBeanX clothes) {
            this.clothes = clothes;
        }

        public DyBeanX getDy() {
            return dy;
        }

        public void setDy(DyBeanX dy) {
            this.dy = dy;
        }

        public SportsBeanX getSports() {
            return sports;
        }

        public void setSports(SportsBeanX sports) {
            this.sports = sports;
        }

        public TravelBeanX getTravel() {
            return travel;
        }

        public void setTravel(TravelBeanX travel) {
            this.travel = travel;
        }

        public BeautyBeanX getBeauty() {
            return beauty;
        }

        public void setBeauty(BeautyBeanX beauty) {
            this.beauty = beauty;
        }

        public XqBeanX getXq() {
            return xq;
        }

        public void setXq(XqBeanX xq) {
            this.xq = xq;
        }

        public HcBeanX getHc() {
            return hc;
        }

        public void setHc(HcBeanX hc) {
            this.hc = hc;
        }

        public ZsBeanX getZs() {
            return zs;
        }

        public void setZs(ZsBeanX zs) {
            this.zs = zs;
        }

        public ColdBeanX getCold() {
            return cold;
        }

        public void setCold(ColdBeanX cold) {
            this.cold = cold;
        }

        public GjBeanX getGj() {
            return gj;
        }

        public void setGj(GjBeanX gj) {
            this.gj = gj;
        }

        public ComfortBeanX getComfort() {
            return comfort;
        }

        public void setComfort(ComfortBeanX comfort) {
            this.comfort = comfort;
        }

        public UvBeanX getUv() {
            return uv;
        }

        public void setUv(UvBeanX uv) {
            this.uv = uv;
        }

        public ClBeanX getCl() {
            return cl;
        }

        public void setCl(ClBeanX cl) {
            this.cl = cl;
        }

        public GlassBeanX getGlass() {
            return glass;
        }

        public void setGlass(GlassBeanX glass) {
            this.glass = glass;
        }

        public AqiBeanX getAqi() {
            return aqi;
        }

        public void setAqi(AqiBeanX aqi) {
            this.aqi = aqi;
        }

        public AcBeanX getAc() {
            return ac;
        }

        public void setAc(AcBeanX ac) {
            this.ac = ac;
        }

        public WashCarBeanX getWash_car() {
            return wash_car;
        }

        public void setWash_car(WashCarBeanX wash_car) {
            this.wash_car = wash_car;
        }

        public MfBeanX getMf() {
            return mf;
        }

        public void setMf(MfBeanX mf) {
            this.mf = mf;
        }

        public AgBeanX getAg() {
            return ag;
        }

        public void setAg(AgBeanX ag) {
            this.ag = ag;
        }

        public PjBeanX getPj() {
            return pj;
        }

        public void setPj(PjBeanX pj) {
            this.pj = pj;
        }

        public NlBeanX getNl() {
            return nl;
        }

        public void setNl(NlBeanX nl) {
            this.nl = nl;
        }

        public PkBeanX getPk() {
            return pk;
        }

        public void setPk(PkBeanX pk) {
            this.pk = pk;
        }

        public static class YhBeanX {
            /**
             * title : 较适宜
             * desc : 天气较好，适宜约会
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class LsBeanX {
            /**
             * title : 不适宜
             * desc : 天气阴沉，不太适宜晾晒。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class ClothesBeanX {
            /**
             * title : 热
             * desc : 适合穿T恤、短薄外套等夏季服装。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class DyBeanX {
            /**
             * title : 不适宜钓鱼
             * desc : 天气太热,不适宜垂钓。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class SportsBeanX {
            /**
             * title : 较适宜
             * desc : 运动请注意防晒。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class TravelBeanX {
            /**
             * title : 较不宜
             * desc : 天气很热，如外出可选择水上娱乐项目。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class BeautyBeanX {
            /**
             * title : 去油
             * desc : 请选用露质面霜打底，水质无油粉底霜。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class XqBeanX {
            /**
             * title : 较差
             * desc : 天气热，容易烦躁
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class HcBeanX {
            /**
             * title : 适宜
             * desc : 天气较好，适宜划船及嬉玩水上运动。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class ZsBeanX {
            /**
             * title : 可能中暑
             * desc : 气温较高，易中暑人群注意阴凉休息。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class ColdBeanX {
            /**
             * title : 少发
             * desc : 感冒机率较低，避免长期处于空调屋中。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class GjBeanX {
            /**
             * title : 适宜
             * desc : 这种好天气去逛街可使身心畅快放松。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class ComfortBeanX {
            /**
             * title : 较差
             * desc : 热，感觉不舒适
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class UvBeanX {
            /**
             * title : 强
             * desc : 涂擦SPF大于15、PA+防晒护肤品。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class ClBeanX {
            /**
             * title : 较适宜
             * desc : 运动请注意防晒。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class GlassBeanX {
            /**
             * title : 必要
             * desc : 必要佩戴
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class AqiBeanX {
            /**
             * title : 良好
             * desc : 可以正常在户外活动，易敏感人群应减少外出
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class AcBeanX {
            /**
             * title : 暂缺
             * desc : 暂缺
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class WashCarBeanX {
            /**
             * title : 适宜
             * desc : 无雨且风力较小，易保持清洁度。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class MfBeanX {
            /**
             * title : 暂缺
             * desc : 暂缺
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class AgBeanX {
            /**
             * title : 暂缺
             * desc : 暂缺
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class PjBeanX {
            /**
             * title : 适宜
             * desc : 炎热干燥，适宜饮用冰镇啤酒，不要贪杯。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class NlBeanX {
            /**
             * title : 暂缺
             * desc : 暂缺
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class PkBeanX {
            /**
             * title : 不宜
             * desc : 天气酷热，不适宜放风筝。
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }

    public static class _$3hourForcastBeanX {
        /**
         * wind_direction : 无持续风向
         * temperature_max : 30
         * wind_power : 微风,3
         * weather : 多云
         * weather_pic : http://app1.showapi.com/weather/icon/day/01.png
         * temperature_min : 27
         * hour : 8时-14时
         * temperature : 28
         */

        private String wind_direction;
        private String temperature_max;
        private String wind_power;
        private String weather;
        private String weather_pic;
        private String temperature_min;
        private String hour;
        private String temperature;

        public String getWind_direction() {
            return wind_direction;
        }

        public void setWind_direction(String wind_direction) {
            this.wind_direction = wind_direction;
        }

        public String getTemperature_max() {
            return temperature_max;
        }

        public void setTemperature_max(String temperature_max) {
            this.temperature_max = temperature_max;
        }

        public String getWind_power() {
            return wind_power;
        }

        public void setWind_power(String wind_power) {
            this.wind_power = wind_power;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeather_pic() {
            return weather_pic;
        }

        public void setWeather_pic(String weather_pic) {
            this.weather_pic = weather_pic;
        }

        public String getTemperature_min() {
            return temperature_min;
        }

        public void setTemperature_min(String temperature_min) {
            this.temperature_min = temperature_min;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }
    }
}