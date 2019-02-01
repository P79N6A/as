package com.yaoguang.greendao.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhongjh on 2017/9/8.
 */
public class F1Bean {
    /**
     * day_weather : 雷阵雨
     * night_weather : 小雨
     * night_weather_code : 07
     * index : {"yh":{"title":"较不适宜","desc":"建议尽量不要去室外约会。"},"ls":{"title":"不适宜","desc":"有降水会淋湿衣物，不适宜晾晒。"},"clothes":{"title":"热","desc":"适合穿T恤、短薄外套等夏季服装。"},"dy":{"title":"不适宜钓鱼","desc":"天气不好，不适合垂钓"},"sports":{"title":"较不宜","desc":"有降水,推荐您在室内进行休闲运动。"},"travel":{"title":"较不宜","desc":"有降水气温高，注意防暑降温携带雨具。"},"beauty":{"title":"保湿","desc":"请选用保湿型霜类化妆品。"},"xq":{"title":"较差","desc":"天气热，容易烦躁"},"hc":{"title":"不适宜","desc":"天气不好，建议选择别的娱乐方式。"},"zs":{"title":"可能中暑","desc":"气温较高，易中暑人群注意阴凉休息。"},"cold":{"title":"少发","desc":"感冒机率较低，避免长期处于空调屋中。"},"gj":{"title":"不适宜","desc":"有降水，出门带雨具并注意防雷。"},"comfort":{"title":"较差","desc":"偏热或较热，部分人感觉不舒适"},"uv":{"title":"强","desc":"涂擦SPF大于15、PA+防晒护肤品。"},"cl":{"title":"较不宜","desc":"有降水,推荐您在室内进行休闲运动。"},"glass":{"title":"必要","desc":"必要佩戴"},"aqi":{"title":"良好","desc":"可以正常在户外活动，易敏感人群应减少外出"},"ac":{"title":"部分时间开启","desc":"午后天气炎热可适时开启制冷空调。"},"wash_car":{"title":"不适宜","desc":"有雨，雨水和泥水会弄脏爱车。"},"mf":{"title":"一般","desc":"注意头发清洁，选用防晒型护发品。"},"ag":{"title":"易发","desc":"应减少外出，外出需采取防护措施。"},"pj":{"title":"适宜","desc":"天热潮湿，可饮用清凉的啤酒，不要贪杯。"},"nl":{"title":"较适宜","desc":"只要您稍作准备就可以放心外出。"},"pk":{"title":"不宜","desc":"天气酷热，不适宜放风筝。"}}
     * air_press : 1009 hPa
     * jiangshui : 81%
     * night_wind_power : 微风 <5.4m/s
     * day_wind_power : 微风 <5.4m/s
     * day_weather_code : 04
     * 3hourForcast : [{"wind_direction":"无持续风向","temperature_max":"31","wind_power":"微风,3","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/day/07.png","temperature_min":"26","hour":"8时-11时","temperature":"26"},{"wind_direction":"无持续风向","temperature_max":"34","wind_power":"微风,3","weather":"雷阵雨","weather_pic":"http://app1.showapi.com/weather/icon/day/04.png","temperature_min":"26","hour":"11时-14时","temperature":"31"},{"wind_direction":"无持续风向","temperature_max":"34","wind_power":"微风,3","weather":"雷阵雨","weather_pic":"http://app1.showapi.com/weather/icon/day/04.png","temperature_min":"31","hour":"14时-17时","temperature":"34"},{"wind_direction":"无持续风向","temperature_max":"34","wind_power":"微风,3","weather":"雷阵雨","weather_pic":"http://app1.showapi.com/weather/icon/day/04.png","temperature_min":"26","hour":"17时-20时","temperature":"31"},{"wind_direction":"无持续风向","temperature_max":"31","wind_power":"微风,0","weather":"雷阵雨","weather_pic":"http://app1.showapi.com/weather/icon/night/04.png","temperature_min":"26","hour":"20时-23时","temperature":"26"},{"wind_direction":"无持续风向","temperature_max":"26","wind_power":"微风,0","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/night/07.png","temperature_min":"26","hour":"23时-2时","temperature":"26"},{"wind_direction":"无持续风向","temperature_max":"26","wind_power":"微风,0","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/night/07.png","temperature_min":"26","hour":"2时-5时","temperature":"26"},{"wind_direction":"无持续风向","temperature_max":"27","wind_power":"微风,0","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/night/07.png","temperature_min":"26","hour":"5时-8时","temperature":"26"}]
     * sun_begin_end : 06:11|18:38
     * ziwaixian : 强
     * day_weather_pic : http://app1.showapi.com/weather/icon/day/04.png
     * weekday : 5
     * night_air_temperature : 25
     * day_air_temperature : 34
     * day_wind_direction : 无持续风向
     * day : 20170908
     * night_weather_pic : http://app1.showapi.com/weather/icon/night/07.png
     * night_wind_direction : 无持续风向
     */

    private String day_weather;
    private String night_weather;
    private String night_weather_code;
    private IndexBeanXX index;
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
    private List<_$3hourForcastBeanXX> _$3hourForcast;

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

    public IndexBeanXX getIndex() {
        return index;
    }

    public void setIndex(IndexBeanXX index) {
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

    public List<_$3hourForcastBeanXX> get_$3hourForcast() {
        return _$3hourForcast;
    }

    public void set_$3hourForcast(List<_$3hourForcastBeanXX> _$3hourForcast) {
        this._$3hourForcast = _$3hourForcast;
    }

    public static class IndexBeanXX {
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
         * ac : {"title":"部分时间开启","desc":"午后天气炎热可适时开启制冷空调。"}
         * wash_car : {"title":"不适宜","desc":"有雨，雨水和泥水会弄脏爱车。"}
         * mf : {"title":"一般","desc":"注意头发清洁，选用防晒型护发品。"}
         * ag : {"title":"易发","desc":"应减少外出，外出需采取防护措施。"}
         * pj : {"title":"适宜","desc":"天热潮湿，可饮用清凉的啤酒，不要贪杯。"}
         * nl : {"title":"较适宜","desc":"只要您稍作准备就可以放心外出。"}
         * pk : {"title":"不宜","desc":"天气酷热，不适宜放风筝。"}
         */

        private YhBeanXX yh;
        private LsBeanXX ls;
        private ClothesBeanXX clothes;
        private DyBeanXX dy;
        private SportsBeanXX sports;
        private TravelBeanXX travel;
        private BeautyBeanXX beauty;
        private XqBeanXX xq;
        private HcBeanXX hc;
        private ZsBeanXX zs;
        private ColdBeanXX cold;
        private GjBeanXX gj;
        private ComfortBeanXX comfort;
        private UvBeanXX uv;
        private ClBeanXX cl;
        private GlassBeanXX glass;
        private AqiBeanXX aqi;
        private AcBeanXX ac;
        private WashCarBeanXX wash_car;
        private MfBeanXX mf;
        private AgBeanXX ag;
        private PjBeanXX pj;
        private NlBeanXX nl;
        private PkBeanXX pk;

        public YhBeanXX getYh() {
            return yh;
        }

        public void setYh(YhBeanXX yh) {
            this.yh = yh;
        }

        public LsBeanXX getLs() {
            return ls;
        }

        public void setLs(LsBeanXX ls) {
            this.ls = ls;
        }

        public ClothesBeanXX getClothes() {
            return clothes;
        }

        public void setClothes(ClothesBeanXX clothes) {
            this.clothes = clothes;
        }

        public DyBeanXX getDy() {
            return dy;
        }

        public void setDy(DyBeanXX dy) {
            this.dy = dy;
        }

        public SportsBeanXX getSports() {
            return sports;
        }

        public void setSports(SportsBeanXX sports) {
            this.sports = sports;
        }

        public TravelBeanXX getTravel() {
            return travel;
        }

        public void setTravel(TravelBeanXX travel) {
            this.travel = travel;
        }

        public BeautyBeanXX getBeauty() {
            return beauty;
        }

        public void setBeauty(BeautyBeanXX beauty) {
            this.beauty = beauty;
        }

        public XqBeanXX getXq() {
            return xq;
        }

        public void setXq(XqBeanXX xq) {
            this.xq = xq;
        }

        public HcBeanXX getHc() {
            return hc;
        }

        public void setHc(HcBeanXX hc) {
            this.hc = hc;
        }

        public ZsBeanXX getZs() {
            return zs;
        }

        public void setZs(ZsBeanXX zs) {
            this.zs = zs;
        }

        public ColdBeanXX getCold() {
            return cold;
        }

        public void setCold(ColdBeanXX cold) {
            this.cold = cold;
        }

        public GjBeanXX getGj() {
            return gj;
        }

        public void setGj(GjBeanXX gj) {
            this.gj = gj;
        }

        public ComfortBeanXX getComfort() {
            return comfort;
        }

        public void setComfort(ComfortBeanXX comfort) {
            this.comfort = comfort;
        }

        public UvBeanXX getUv() {
            return uv;
        }

        public void setUv(UvBeanXX uv) {
            this.uv = uv;
        }

        public ClBeanXX getCl() {
            return cl;
        }

        public void setCl(ClBeanXX cl) {
            this.cl = cl;
        }

        public GlassBeanXX getGlass() {
            return glass;
        }

        public void setGlass(GlassBeanXX glass) {
            this.glass = glass;
        }

        public AqiBeanXX getAqi() {
            return aqi;
        }

        public void setAqi(AqiBeanXX aqi) {
            this.aqi = aqi;
        }

        public AcBeanXX getAc() {
            return ac;
        }

        public void setAc(AcBeanXX ac) {
            this.ac = ac;
        }

        public WashCarBeanXX getWash_car() {
            return wash_car;
        }

        public void setWash_car(WashCarBeanXX wash_car) {
            this.wash_car = wash_car;
        }

        public MfBeanXX getMf() {
            return mf;
        }

        public void setMf(MfBeanXX mf) {
            this.mf = mf;
        }

        public AgBeanXX getAg() {
            return ag;
        }

        public void setAg(AgBeanXX ag) {
            this.ag = ag;
        }

        public PjBeanXX getPj() {
            return pj;
        }

        public void setPj(PjBeanXX pj) {
            this.pj = pj;
        }

        public NlBeanXX getNl() {
            return nl;
        }

        public void setNl(NlBeanXX nl) {
            this.nl = nl;
        }

        public PkBeanXX getPk() {
            return pk;
        }

        public void setPk(PkBeanXX pk) {
            this.pk = pk;
        }

        public static class YhBeanXX {
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

        public static class LsBeanXX {
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

        public static class ClothesBeanXX {
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

        public static class DyBeanXX {
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

        public static class SportsBeanXX {
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

        public static class TravelBeanXX {
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

        public static class BeautyBeanXX {
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

        public static class XqBeanXX {
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

        public static class HcBeanXX {
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

        public static class ZsBeanXX {
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

        public static class ColdBeanXX {
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

        public static class GjBeanXX {
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

        public static class ComfortBeanXX {
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

        public static class UvBeanXX {
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

        public static class ClBeanXX {
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

        public static class GlassBeanXX {
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

        public static class AqiBeanXX {
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

        public static class AcBeanXX {
            /**
             * title : 部分时间开启
             * desc : 午后天气炎热可适时开启制冷空调。
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

        public static class WashCarBeanXX {
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

        public static class MfBeanXX {
            /**
             * title : 一般
             * desc : 注意头发清洁，选用防晒型护发品。
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

        public static class AgBeanXX {
            /**
             * title : 易发
             * desc : 应减少外出，外出需采取防护措施。
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

        public static class PjBeanXX {
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

        public static class NlBeanXX {
            /**
             * title : 较适宜
             * desc : 只要您稍作准备就可以放心外出。
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

        public static class PkBeanXX {
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

    public static class _$3hourForcastBeanXX {
        /**
         * wind_direction : 无持续风向
         * temperature_max : 31
         * wind_power : 微风,3
         * weather : 小雨
         * weather_pic : http://app1.showapi.com/weather/icon/day/07.png
         * temperature_min : 26
         * hour : 8时-11时
         * temperature : 26
         */

        private String wind_direction;
        private String temperature_max;
        private String wind_power;
        private String weather;
        private String weather_pic;
        private String temperature_min;
        private String hour;
        private String temperature;
        private boolean isNight;

        public boolean isNight() {
            return isNight;
        }

        public void setNight(boolean night) {
            isNight = night;
        }

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
