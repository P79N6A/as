package com.yaoguang.greendao.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhongjh on 2017/9/8.
 */
public class F4Bean {
    /**
     * day_weather : 雷阵雨
     * night_weather : 雷阵雨
     * night_weather_code : 04
     * index : {"yh":{"title":"较不适宜","desc":"建议尽量不要去室外约会。"},"ls":{"title":"不适宜","desc":"有降水会淋湿衣物，不适宜晾晒。"},"clothes":{"title":"热","desc":"适合穿T恤、短薄外套等夏季服装。"},"dy":{"title":"不适宜钓鱼","desc":"天气不好，不适合垂钓"},"sports":{"title":"较不宜","desc":"有降水,推荐您在室内进行休闲运动。"},"travel":{"title":"较不宜","desc":"有降水气温高，注意防暑降温携带雨具。"},"beauty":{"title":"保湿","desc":"请选用保湿型霜类化妆品。"},"xq":{"title":"较差","desc":"天气热，容易烦躁"},"hc":{"title":"不适宜","desc":"天气不好，建议选择别的娱乐方式。"},"zs":{"title":"可能中暑","desc":"气温较高，易中暑人群注意阴凉休息。"},"cold":{"title":"少发","desc":"感冒机率较低，避免长期处于空调屋中。"},"gj":{"title":"不适宜","desc":"有降水，出门带雨具并注意防雷。"},"comfort":{"title":"较差","desc":"偏热或较热，部分人感觉不舒适"},"uv":{"title":"强","desc":"涂擦SPF大于15、PA+防晒护肤品。"},"cl":{"title":"较不宜","desc":"有降水,推荐您在室内进行休闲运动。"},"glass":{"title":"必要","desc":"必要佩戴"},"aqi":{"title":"良好","desc":"可以正常在户外活动，易敏感人群应减少外出"},"ac":{"title":"暂缺","desc":"暂缺"},"wash_car":{"title":"不适宜","desc":"有雨，雨水和泥水会弄脏爱车。"},"mf":{"title":"暂缺","desc":"暂缺"},"ag":{"title":"暂缺","desc":"暂缺"},"pj":{"title":"适宜","desc":"天热潮湿，可饮用清凉的啤酒，不要贪杯。"},"nl":{"title":"暂缺","desc":"暂缺"},"pk":{"title":"不宜","desc":"天气酷热，不适宜放风筝。"}}
     * air_press : 1009 hPa
     * jiangshui : 85%
     * night_wind_power : 微风 <5.4m/s
     * day_wind_power : 微风 <5.4m/s
     * day_weather_code : 04
     * 3hourForcast : [{"wind_direction":"无持续风向","temperature_max":"33","wind_power":"微风,1","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","temperature_min":"26","hour":"8时-14时","temperature":"28"},{"wind_direction":"无持续风向","temperature_max":"33","wind_power":"微风,3","weather":"雷阵雨","weather_pic":"http://app1.showapi.com/weather/icon/day/04.png","temperature_min":"28","hour":"14时-20时","temperature":"33"},{"wind_direction":"无持续风向","temperature_max":"33","wind_power":"微风,0","weather":"雷阵雨","weather_pic":"http://app1.showapi.com/weather/icon/night/04.png","temperature_min":"26","hour":"20时-2时","temperature":"28"},{"wind_direction":"无持续风向","temperature_max":"28","wind_power":"微风,0","weather":"雷阵雨","weather_pic":"http://app1.showapi.com/weather/icon/night/04.png","temperature_min":"26","hour":"2时-8时","temperature":"26"}]
     * sun_begin_end : 06:12|18:35
     * ziwaixian : 强
     * day_weather_pic : http://app1.showapi.com/weather/icon/day/04.png
     * weekday : 1
     * night_air_temperature : 26
     * day_air_temperature : 34
     * day_wind_direction : 无持续风向
     * day : 20170911
     * night_weather_pic : http://app1.showapi.com/weather/icon/night/04.png
     * night_wind_direction : 无持续风向
     */

    private String day_weather;
    private String night_weather;
    private String night_weather_code;
    private IndexBeanXXXXXX index;
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
    private List<_$3hourForcastBeanXXXXXX> _$3hourForcast;

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

    public IndexBeanXXXXXX getIndex() {
        return index;
    }

    public void setIndex(IndexBeanXXXXXX index) {
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

    public List<_$3hourForcastBeanXXXXXX> get_$3hourForcast() {
        return _$3hourForcast;
    }

    public void set_$3hourForcast(List<_$3hourForcastBeanXXXXXX> _$3hourForcast) {
        this._$3hourForcast = _$3hourForcast;
    }

    public static class IndexBeanXXXXXX {
        /**
         * yh : {"title":"较不适宜","desc":"建议尽量不要去室外约会。"}
         * ls : {"title":"不适宜","desc":"有降水会淋湿衣物，不适宜晾晒。"}
         * clothes : {"title":"热","desc":"适合穿T恤、短薄外套等夏季服装。"}
         * dy : {"title":"不适宜钓鱼","desc":"天气不好，不适合垂钓"}
         * sports : {"title":"较不宜","desc":"有降水,推荐您在室内进行休闲运动。"}
         * travel : {"title":"较不宜","desc":"有降水气温高，注意防暑降温携带雨具。"}
         * beauty : {"title":"保湿","desc":"请选用保湿型霜类化妆品。"}
         * xq : {"title":"较差","desc":"天气热，容易烦躁"}
         * hc : {"title":"不适宜","desc":"天气不好，建议选择别的娱乐方式。"}
         * zs : {"title":"可能中暑","desc":"气温较高，易中暑人群注意阴凉休息。"}
         * cold : {"title":"少发","desc":"感冒机率较低，避免长期处于空调屋中。"}
         * gj : {"title":"不适宜","desc":"有降水，出门带雨具并注意防雷。"}
         * comfort : {"title":"较差","desc":"偏热或较热，部分人感觉不舒适"}
         * uv : {"title":"强","desc":"涂擦SPF大于15、PA+防晒护肤品。"}
         * cl : {"title":"较不宜","desc":"有降水,推荐您在室内进行休闲运动。"}
         * glass : {"title":"必要","desc":"必要佩戴"}
         * aqi : {"title":"良好","desc":"可以正常在户外活动，易敏感人群应减少外出"}
         * ac : {"title":"暂缺","desc":"暂缺"}
         * wash_car : {"title":"不适宜","desc":"有雨，雨水和泥水会弄脏爱车。"}
         * mf : {"title":"暂缺","desc":"暂缺"}
         * ag : {"title":"暂缺","desc":"暂缺"}
         * pj : {"title":"适宜","desc":"天热潮湿，可饮用清凉的啤酒，不要贪杯。"}
         * nl : {"title":"暂缺","desc":"暂缺"}
         * pk : {"title":"不宜","desc":"天气酷热，不适宜放风筝。"}
         */

        private YhBeanXXXXXX yh;
        private LsBeanXXXXXX ls;
        private ClothesBeanXXXXXX clothes;
        private DyBeanXXXXXX dy;
        private SportsBeanXXXXXX sports;
        private TravelBeanXXXXXX travel;
        private BeautyBeanXXXXXX beauty;
        private XqBeanXXXXXX xq;
        private HcBeanXXXXXX hc;
        private ZsBeanXXXXXX zs;
        private ColdBeanXXXXXX cold;
        private GjBeanXXXXXX gj;
        private ComfortBeanXXXXXX comfort;
        private UvBeanXXXXXX uv;
        private ClBeanXXXXXX cl;
        private GlassBeanXXXXXX glass;
        private AqiBeanXXXXXX aqi;
        private AcBeanXXXXXX ac;
        private WashCarBeanXXXXXX wash_car;
        private MfBeanXXXXXX mf;
        private AgBeanXXXXXX ag;
        private PjBeanXXXXXX pj;
        private NlBeanXXXXXX nl;
        private PkBeanXXXXXX pk;

        public YhBeanXXXXXX getYh() {
            return yh;
        }

        public void setYh(YhBeanXXXXXX yh) {
            this.yh = yh;
        }

        public LsBeanXXXXXX getLs() {
            return ls;
        }

        public void setLs(LsBeanXXXXXX ls) {
            this.ls = ls;
        }

        public ClothesBeanXXXXXX getClothes() {
            return clothes;
        }

        public void setClothes(ClothesBeanXXXXXX clothes) {
            this.clothes = clothes;
        }

        public DyBeanXXXXXX getDy() {
            return dy;
        }

        public void setDy(DyBeanXXXXXX dy) {
            this.dy = dy;
        }

        public SportsBeanXXXXXX getSports() {
            return sports;
        }

        public void setSports(SportsBeanXXXXXX sports) {
            this.sports = sports;
        }

        public TravelBeanXXXXXX getTravel() {
            return travel;
        }

        public void setTravel(TravelBeanXXXXXX travel) {
            this.travel = travel;
        }

        public BeautyBeanXXXXXX getBeauty() {
            return beauty;
        }

        public void setBeauty(BeautyBeanXXXXXX beauty) {
            this.beauty = beauty;
        }

        public XqBeanXXXXXX getXq() {
            return xq;
        }

        public void setXq(XqBeanXXXXXX xq) {
            this.xq = xq;
        }

        public HcBeanXXXXXX getHc() {
            return hc;
        }

        public void setHc(HcBeanXXXXXX hc) {
            this.hc = hc;
        }

        public ZsBeanXXXXXX getZs() {
            return zs;
        }

        public void setZs(ZsBeanXXXXXX zs) {
            this.zs = zs;
        }

        public ColdBeanXXXXXX getCold() {
            return cold;
        }

        public void setCold(ColdBeanXXXXXX cold) {
            this.cold = cold;
        }

        public GjBeanXXXXXX getGj() {
            return gj;
        }

        public void setGj(GjBeanXXXXXX gj) {
            this.gj = gj;
        }

        public ComfortBeanXXXXXX getComfort() {
            return comfort;
        }

        public void setComfort(ComfortBeanXXXXXX comfort) {
            this.comfort = comfort;
        }

        public UvBeanXXXXXX getUv() {
            return uv;
        }

        public void setUv(UvBeanXXXXXX uv) {
            this.uv = uv;
        }

        public ClBeanXXXXXX getCl() {
            return cl;
        }

        public void setCl(ClBeanXXXXXX cl) {
            this.cl = cl;
        }

        public GlassBeanXXXXXX getGlass() {
            return glass;
        }

        public void setGlass(GlassBeanXXXXXX glass) {
            this.glass = glass;
        }

        public AqiBeanXXXXXX getAqi() {
            return aqi;
        }

        public void setAqi(AqiBeanXXXXXX aqi) {
            this.aqi = aqi;
        }

        public AcBeanXXXXXX getAc() {
            return ac;
        }

        public void setAc(AcBeanXXXXXX ac) {
            this.ac = ac;
        }

        public WashCarBeanXXXXXX getWash_car() {
            return wash_car;
        }

        public void setWash_car(WashCarBeanXXXXXX wash_car) {
            this.wash_car = wash_car;
        }

        public MfBeanXXXXXX getMf() {
            return mf;
        }

        public void setMf(MfBeanXXXXXX mf) {
            this.mf = mf;
        }

        public AgBeanXXXXXX getAg() {
            return ag;
        }

        public void setAg(AgBeanXXXXXX ag) {
            this.ag = ag;
        }

        public PjBeanXXXXXX getPj() {
            return pj;
        }

        public void setPj(PjBeanXXXXXX pj) {
            this.pj = pj;
        }

        public NlBeanXXXXXX getNl() {
            return nl;
        }

        public void setNl(NlBeanXXXXXX nl) {
            this.nl = nl;
        }

        public PkBeanXXXXXX getPk() {
            return pk;
        }

        public void setPk(PkBeanXXXXXX pk) {
            this.pk = pk;
        }

        public static class YhBeanXXXXXX {
            /**
             * title : 较不适宜
             * desc : 建议尽量不要去室外约会。
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

        public static class LsBeanXXXXXX {
            /**
             * title : 不适宜
             * desc : 有降水会淋湿衣物，不适宜晾晒。
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

        public static class ClothesBeanXXXXXX {
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

        public static class DyBeanXXXXXX {
            /**
             * title : 不适宜钓鱼
             * desc : 天气不好，不适合垂钓
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

        public static class SportsBeanXXXXXX {
            /**
             * title : 较不宜
             * desc : 有降水,推荐您在室内进行休闲运动。
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

        public static class TravelBeanXXXXXX {
            /**
             * title : 较不宜
             * desc : 有降水气温高，注意防暑降温携带雨具。
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

        public static class BeautyBeanXXXXXX {
            /**
             * title : 保湿
             * desc : 请选用保湿型霜类化妆品。
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

        public static class XqBeanXXXXXX {
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

        public static class HcBeanXXXXXX {
            /**
             * title : 不适宜
             * desc : 天气不好，建议选择别的娱乐方式。
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

        public static class ZsBeanXXXXXX {
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

        public static class ColdBeanXXXXXX {
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

        public static class GjBeanXXXXXX {
            /**
             * title : 不适宜
             * desc : 有降水，出门带雨具并注意防雷。
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

        public static class ComfortBeanXXXXXX {
            /**
             * title : 较差
             * desc : 偏热或较热，部分人感觉不舒适
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

        public static class UvBeanXXXXXX {
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

        public static class ClBeanXXXXXX {
            /**
             * title : 较不宜
             * desc : 有降水,推荐您在室内进行休闲运动。
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

        public static class GlassBeanXXXXXX {
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

        public static class AqiBeanXXXXXX {
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

        public static class AcBeanXXXXXX {
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

        public static class WashCarBeanXXXXXX {
            /**
             * title : 不适宜
             * desc : 有雨，雨水和泥水会弄脏爱车。
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

        public static class MfBeanXXXXXX {
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

        public static class AgBeanXXXXXX {
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

        public static class PjBeanXXXXXX {
            /**
             * title : 适宜
             * desc : 天热潮湿，可饮用清凉的啤酒，不要贪杯。
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

        public static class NlBeanXXXXXX {
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

        public static class PkBeanXXXXXX {
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

    public static class _$3hourForcastBeanXXXXXX {
        /**
         * wind_direction : 无持续风向
         * temperature_max : 33
         * wind_power : 微风,1
         * weather : 多云
         * weather_pic : http://app1.showapi.com/weather/icon/day/01.png
         * temperature_min : 26
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