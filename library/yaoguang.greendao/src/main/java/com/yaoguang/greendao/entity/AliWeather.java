package com.yaoguang.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhongjh on 2017/5/24.
 */
public class AliWeather implements Parcelable {

    //图片 温度
    //"weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weather":"多云","temperature":"34"

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"time":"20170524113000","ret_code":0,"cityInfo":{"c6":"beijing","c5":"北京","c4":"beijing","c3":"昌平","c9":"中国","c8":"china","c7":"北京","c17":"+8","c16":"AZ9010","c1":"101010700","c2":"changping","c11":"010","longitude":116.165,"c10":"3","latitude":40.206,"c12":"102200","c15":"80"},"now":{"aqiDetail":{"so2":"2","o3":"129","area_code":"beijing","pm2_5":"12","primary_pollutant":"","num":"107","co":"0.217","area":"北京","no2":"17","aqi":"41","quality":"优","pm10":"26","o3_8h":"71"},"weather_code":"01","wind_direction":"西风","temperature_time":"16:00","wind_power":"5级","aqi":"41","sd":"13%","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weather":"多云","temperature":"34"},"f1":{"day_weather":"多云","night_weather":"多云","night_weather_code":"01","air_press":"1013 hPa","jiangshui":"7%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"01","sun_begin_end":"04:53|19:32","ziwaixian":"中等","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weekday":3,"night_air_temperature":"17","day_air_temperature":"33","day_wind_direction":"西南风","day":"20170524","night_weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","night_wind_direction":"西南风"},"f3":{"day_weather":"晴","night_weather":"晴","night_weather_code":"00","air_press":"1013 hPa","jiangshui":"0%","night_wind_power":"微风 <5.4m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"00","sun_begin_end":"04:52|19:33","ziwaixian":"中等","day_weather_pic":"http://app1.showapi.com/weather/icon/day/00.png","weekday":5,"night_air_temperature":"18","day_air_temperature":"31","day_wind_direction":"南风","day":"20170526","night_weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","night_wind_direction":"北风"},"f2":{"day_weather":"晴","night_weather":"晴","night_weather_code":"00","air_press":"1013 hPa","jiangshui":"0%","night_wind_power":"微风 <5.4m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"00","sun_begin_end":"04:52|19:33","ziwaixian":"中等","day_weather_pic":"http://app1.showapi.com/weather/icon/day/00.png","weekday":4,"night_air_temperature":"15","day_air_temperature":"31","day_wind_direction":"南风","day":"20170525","night_weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","night_wind_direction":"北风"}}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean implements Parcelable {
        /**
         * time : 20170524113000
         * ret_code : 0
         * cityInfo : {"c6":"beijing","c5":"北京","c4":"beijing","c3":"昌平","c9":"中国","c8":"china","c7":"北京","c17":"+8","c16":"AZ9010","c1":"101010700","c2":"changping","c11":"010","longitude":116.165,"c10":"3","latitude":40.206,"c12":"102200","c15":"80"}
         * now : {"aqiDetail":{"so2":"2","o3":"129","area_code":"beijing","pm2_5":"12","primary_pollutant":"","num":"107","co":"0.217","area":"北京","no2":"17","aqi":"41","quality":"优","pm10":"26","o3_8h":"71"},"weather_code":"01","wind_direction":"西风","temperature_time":"16:00","wind_power":"5级","aqi":"41","sd":"13%","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weather":"多云","temperature":"34"}
         * f1 : {"day_weather":"多云","night_weather":"多云","night_weather_code":"01","air_press":"1013 hPa","jiangshui":"7%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"01","sun_begin_end":"04:53|19:32","ziwaixian":"中等","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weekday":3,"night_air_temperature":"17","day_air_temperature":"33","day_wind_direction":"西南风","day":"20170524","night_weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","night_wind_direction":"西南风"}
         * f3 : {"day_weather":"晴","night_weather":"晴","night_weather_code":"00","air_press":"1013 hPa","jiangshui":"0%","night_wind_power":"微风 <5.4m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"00","sun_begin_end":"04:52|19:33","ziwaixian":"中等","day_weather_pic":"http://app1.showapi.com/weather/icon/day/00.png","weekday":5,"night_air_temperature":"18","day_air_temperature":"31","day_wind_direction":"南风","day":"20170526","night_weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","night_wind_direction":"北风"}
         * f2 : {"day_weather":"晴","night_weather":"晴","night_weather_code":"00","air_press":"1013 hPa","jiangshui":"0%","night_wind_power":"微风 <5.4m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"00","sun_begin_end":"04:52|19:33","ziwaixian":"中等","day_weather_pic":"http://app1.showapi.com/weather/icon/day/00.png","weekday":4,"night_air_temperature":"15","day_air_temperature":"31","day_wind_direction":"南风","day":"20170525","night_weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","night_wind_direction":"北风"}
         */

        private String time;
        private int ret_code;
        private CityInfoBean cityInfo;
        private NowBean now;
        private F1Bean f1;
        private F3Bean f3;
        private F2Bean f2;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public CityInfoBean getCityInfo() {
            return cityInfo;
        }

        public void setCityInfo(CityInfoBean cityInfo) {
            this.cityInfo = cityInfo;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public F1Bean getF1() {
            return f1;
        }

        public void setF1(F1Bean f1) {
            this.f1 = f1;
        }

        public F3Bean getF3() {
            return f3;
        }

        public void setF3(F3Bean f3) {
            this.f3 = f3;
        }

        public F2Bean getF2() {
            return f2;
        }

        public void setF2(F2Bean f2) {
            this.f2 = f2;
        }

        public static class CityInfoBean implements Parcelable {
            /**
             * c6 : beijing
             * c5 : 北京
             * c4 : beijing
             * c3 : 昌平
             * c9 : 中国
             * c8 : china
             * c7 : 北京
             * c17 : +8
             * c16 : AZ9010
             * c1 : 101010700
             * c2 : changping
             * c11 : 010
             * longitude : 116.165
             * c10 : 3
             * latitude : 40.206
             * c12 : 102200
             * c15 : 80
             */

            private String c6;
            private String c5;
            private String c4;
            private String c3;
            private String c9;
            private String c8;
            private String c7;
            private String c17;
            private String c16;
            private String c1;
            private String c2;
            private String c11;
            private double longitude;
            private String c10;
            private double latitude;
            private String c12;
            private String c15;

            public String getC6() {
                return c6;
            }

            public void setC6(String c6) {
                this.c6 = c6;
            }

            public String getC5() {
                return c5;
            }

            public void setC5(String c5) {
                this.c5 = c5;
            }

            public String getC4() {
                return c4;
            }

            public void setC4(String c4) {
                this.c4 = c4;
            }

            public String getC3() {
                return c3;
            }

            public void setC3(String c3) {
                this.c3 = c3;
            }

            public String getC9() {
                return c9;
            }

            public void setC9(String c9) {
                this.c9 = c9;
            }

            public String getC8() {
                return c8;
            }

            public void setC8(String c8) {
                this.c8 = c8;
            }

            public String getC7() {
                return c7;
            }

            public void setC7(String c7) {
                this.c7 = c7;
            }

            public String getC17() {
                return c17;
            }

            public void setC17(String c17) {
                this.c17 = c17;
            }

            public String getC16() {
                return c16;
            }

            public void setC16(String c16) {
                this.c16 = c16;
            }

            public String getC1() {
                return c1;
            }

            public void setC1(String c1) {
                this.c1 = c1;
            }

            public String getC2() {
                return c2;
            }

            public void setC2(String c2) {
                this.c2 = c2;
            }

            public String getC11() {
                return c11;
            }

            public void setC11(String c11) {
                this.c11 = c11;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getC10() {
                return c10;
            }

            public void setC10(String c10) {
                this.c10 = c10;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getC12() {
                return c12;
            }

            public void setC12(String c12) {
                this.c12 = c12;
            }

            public String getC15() {
                return c15;
            }

            public void setC15(String c15) {
                this.c15 = c15;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.c6);
                dest.writeString(this.c5);
                dest.writeString(this.c4);
                dest.writeString(this.c3);
                dest.writeString(this.c9);
                dest.writeString(this.c8);
                dest.writeString(this.c7);
                dest.writeString(this.c17);
                dest.writeString(this.c16);
                dest.writeString(this.c1);
                dest.writeString(this.c2);
                dest.writeString(this.c11);
                dest.writeDouble(this.longitude);
                dest.writeString(this.c10);
                dest.writeDouble(this.latitude);
                dest.writeString(this.c12);
                dest.writeString(this.c15);
            }

            public CityInfoBean() {
            }

            protected CityInfoBean(Parcel in) {
                this.c6 = in.readString();
                this.c5 = in.readString();
                this.c4 = in.readString();
                this.c3 = in.readString();
                this.c9 = in.readString();
                this.c8 = in.readString();
                this.c7 = in.readString();
                this.c17 = in.readString();
                this.c16 = in.readString();
                this.c1 = in.readString();
                this.c2 = in.readString();
                this.c11 = in.readString();
                this.longitude = in.readDouble();
                this.c10 = in.readString();
                this.latitude = in.readDouble();
                this.c12 = in.readString();
                this.c15 = in.readString();
            }

            public static final Creator<CityInfoBean> CREATOR = new Creator<CityInfoBean>() {
                @Override
                public CityInfoBean createFromParcel(Parcel source) {
                    return new CityInfoBean(source);
                }

                @Override
                public CityInfoBean[] newArray(int size) {
                    return new CityInfoBean[size];
                }
            };
        }

        public static class NowBean implements Parcelable {
            /**
             * aqiDetail : {"so2":"2","o3":"129","area_code":"beijing","pm2_5":"12","primary_pollutant":"","num":"107","co":"0.217","area":"北京","no2":"17","aqi":"41","quality":"优","pm10":"26","o3_8h":"71"}
             * weather_code : 01
             * wind_direction : 西风
             * temperature_time : 16:00
             * wind_power : 5级
             * aqi : 41
             * sd : 13%
             * weather_pic : http://app1.showapi.com/weather/icon/day/01.png
             * weather : 多云
             * temperature : 34
             */

            private AqiDetailBean aqiDetail;
            private String weather_code;
            private String wind_direction;
            private String temperature_time;
            private String wind_power;
            private String aqi;
            private String sd;
            private String weather_pic;
            private String weather;
            private String temperature;

            public AqiDetailBean getAqiDetail() {
                return aqiDetail;
            }

            public void setAqiDetail(AqiDetailBean aqiDetail) {
                this.aqiDetail = aqiDetail;
            }

            public String getWeather_code() {
                return weather_code;
            }

            public void setWeather_code(String weather_code) {
                this.weather_code = weather_code;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getTemperature_time() {
                return temperature_time;
            }

            public void setTemperature_time(String temperature_time) {
                this.temperature_time = temperature_time;
            }

            public String getWind_power() {
                return wind_power;
            }

            public void setWind_power(String wind_power) {
                this.wind_power = wind_power;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getSd() {
                return sd;
            }

            public void setSd(String sd) {
                this.sd = sd;
            }

            public String getWeather_pic() {
                return weather_pic;
            }

            public void setWeather_pic(String weather_pic) {
                this.weather_pic = weather_pic;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public static class AqiDetailBean implements Parcelable {
                /**
                 * so2 : 2
                 * o3 : 129
                 * area_code : beijing
                 * pm2_5 : 12
                 * primary_pollutant :
                 * num : 107
                 * co : 0.217
                 * area : 北京
                 * no2 : 17
                 * aqi : 41
                 * quality : 优
                 * pm10 : 26
                 * o3_8h : 71
                 */

                private String so2;
                private String o3;
                private String area_code;
                private String pm2_5;
                private String primary_pollutant;
                private String num;
                private String co;
                private String area;
                private String no2;
                private String aqi;
                private String quality;
                private String pm10;
                private String o3_8h;

                public String getSo2() {
                    return so2;
                }

                public void setSo2(String so2) {
                    this.so2 = so2;
                }

                public String getO3() {
                    return o3;
                }

                public void setO3(String o3) {
                    this.o3 = o3;
                }

                public String getArea_code() {
                    return area_code;
                }

                public void setArea_code(String area_code) {
                    this.area_code = area_code;
                }

                public String getPm2_5() {
                    return pm2_5;
                }

                public void setPm2_5(String pm2_5) {
                    this.pm2_5 = pm2_5;
                }

                public String getPrimary_pollutant() {
                    return primary_pollutant;
                }

                public void setPrimary_pollutant(String primary_pollutant) {
                    this.primary_pollutant = primary_pollutant;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getCo() {
                    return co;
                }

                public void setCo(String co) {
                    this.co = co;
                }

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public String getNo2() {
                    return no2;
                }

                public void setNo2(String no2) {
                    this.no2 = no2;
                }

                public String getAqi() {
                    return aqi;
                }

                public void setAqi(String aqi) {
                    this.aqi = aqi;
                }

                public String getQuality() {
                    return quality;
                }

                public void setQuality(String quality) {
                    this.quality = quality;
                }

                public String getPm10() {
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getO3_8h() {
                    return o3_8h;
                }

                public void setO3_8h(String o3_8h) {
                    this.o3_8h = o3_8h;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.so2);
                    dest.writeString(this.o3);
                    dest.writeString(this.area_code);
                    dest.writeString(this.pm2_5);
                    dest.writeString(this.primary_pollutant);
                    dest.writeString(this.num);
                    dest.writeString(this.co);
                    dest.writeString(this.area);
                    dest.writeString(this.no2);
                    dest.writeString(this.aqi);
                    dest.writeString(this.quality);
                    dest.writeString(this.pm10);
                    dest.writeString(this.o3_8h);
                }

                public AqiDetailBean() {
                }

                protected AqiDetailBean(Parcel in) {
                    this.so2 = in.readString();
                    this.o3 = in.readString();
                    this.area_code = in.readString();
                    this.pm2_5 = in.readString();
                    this.primary_pollutant = in.readString();
                    this.num = in.readString();
                    this.co = in.readString();
                    this.area = in.readString();
                    this.no2 = in.readString();
                    this.aqi = in.readString();
                    this.quality = in.readString();
                    this.pm10 = in.readString();
                    this.o3_8h = in.readString();
                }

                public static final Creator<AqiDetailBean> CREATOR = new Creator<AqiDetailBean>() {
                    @Override
                    public AqiDetailBean createFromParcel(Parcel source) {
                        return new AqiDetailBean(source);
                    }

                    @Override
                    public AqiDetailBean[] newArray(int size) {
                        return new AqiDetailBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.aqiDetail, flags);
                dest.writeString(this.weather_code);
                dest.writeString(this.wind_direction);
                dest.writeString(this.temperature_time);
                dest.writeString(this.wind_power);
                dest.writeString(this.aqi);
                dest.writeString(this.sd);
                dest.writeString(this.weather_pic);
                dest.writeString(this.weather);
                dest.writeString(this.temperature);
            }

            public NowBean() {
            }

            protected NowBean(Parcel in) {
                this.aqiDetail = in.readParcelable(AqiDetailBean.class.getClassLoader());
                this.weather_code = in.readString();
                this.wind_direction = in.readString();
                this.temperature_time = in.readString();
                this.wind_power = in.readString();
                this.aqi = in.readString();
                this.sd = in.readString();
                this.weather_pic = in.readString();
                this.weather = in.readString();
                this.temperature = in.readString();
            }

            public static final Creator<NowBean> CREATOR = new Creator<NowBean>() {
                @Override
                public NowBean createFromParcel(Parcel source) {
                    return new NowBean(source);
                }

                @Override
                public NowBean[] newArray(int size) {
                    return new NowBean[size];
                }
            };
        }

        public static class F1Bean implements Parcelable {
            /**
             * day_weather : 多云
             * night_weather : 多云
             * night_weather_code : 01
             * air_press : 1013 hPa
             * jiangshui : 7%
             * night_wind_power : 3-4级 5.5~7.9m/s
             * day_wind_power : 3-4级 5.5~7.9m/s
             * day_weather_code : 01
             * sun_begin_end : 04:53|19:32
             * ziwaixian : 中等
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/01.png
             * weekday : 3
             * night_air_temperature : 17
             * day_air_temperature : 33
             * day_wind_direction : 西南风
             * day : 20170524
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/01.png
             * night_wind_direction : 西南风
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.day_weather);
                dest.writeString(this.night_weather);
                dest.writeString(this.night_weather_code);
                dest.writeString(this.air_press);
                dest.writeString(this.jiangshui);
                dest.writeString(this.night_wind_power);
                dest.writeString(this.day_wind_power);
                dest.writeString(this.day_weather_code);
                dest.writeString(this.sun_begin_end);
                dest.writeString(this.ziwaixian);
                dest.writeString(this.day_weather_pic);
                dest.writeInt(this.weekday);
                dest.writeString(this.night_air_temperature);
                dest.writeString(this.day_air_temperature);
                dest.writeString(this.day_wind_direction);
                dest.writeString(this.day);
                dest.writeString(this.night_weather_pic);
                dest.writeString(this.night_wind_direction);
            }

            public F1Bean() {
            }

            protected F1Bean(Parcel in) {
                this.day_weather = in.readString();
                this.night_weather = in.readString();
                this.night_weather_code = in.readString();
                this.air_press = in.readString();
                this.jiangshui = in.readString();
                this.night_wind_power = in.readString();
                this.day_wind_power = in.readString();
                this.day_weather_code = in.readString();
                this.sun_begin_end = in.readString();
                this.ziwaixian = in.readString();
                this.day_weather_pic = in.readString();
                this.weekday = in.readInt();
                this.night_air_temperature = in.readString();
                this.day_air_temperature = in.readString();
                this.day_wind_direction = in.readString();
                this.day = in.readString();
                this.night_weather_pic = in.readString();
                this.night_wind_direction = in.readString();
            }

            public static final Creator<F1Bean> CREATOR = new Creator<F1Bean>() {
                @Override
                public F1Bean createFromParcel(Parcel source) {
                    return new F1Bean(source);
                }

                @Override
                public F1Bean[] newArray(int size) {
                    return new F1Bean[size];
                }
            };
        }

        public static class F3Bean implements Parcelable {
            /**
             * day_weather : 晴
             * night_weather : 晴
             * night_weather_code : 00
             * air_press : 1013 hPa
             * jiangshui : 0%
             * night_wind_power : 微风 <5.4m/s
             * day_wind_power : 微风 <5.4m/s
             * day_weather_code : 00
             * sun_begin_end : 04:52|19:33
             * ziwaixian : 中等
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/00.png
             * weekday : 5
             * night_air_temperature : 18
             * day_air_temperature : 31
             * day_wind_direction : 南风
             * day : 20170526
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/00.png
             * night_wind_direction : 北风
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.day_weather);
                dest.writeString(this.night_weather);
                dest.writeString(this.night_weather_code);
                dest.writeString(this.air_press);
                dest.writeString(this.jiangshui);
                dest.writeString(this.night_wind_power);
                dest.writeString(this.day_wind_power);
                dest.writeString(this.day_weather_code);
                dest.writeString(this.sun_begin_end);
                dest.writeString(this.ziwaixian);
                dest.writeString(this.day_weather_pic);
                dest.writeInt(this.weekday);
                dest.writeString(this.night_air_temperature);
                dest.writeString(this.day_air_temperature);
                dest.writeString(this.day_wind_direction);
                dest.writeString(this.day);
                dest.writeString(this.night_weather_pic);
                dest.writeString(this.night_wind_direction);
            }

            public F3Bean() {
            }

            protected F3Bean(Parcel in) {
                this.day_weather = in.readString();
                this.night_weather = in.readString();
                this.night_weather_code = in.readString();
                this.air_press = in.readString();
                this.jiangshui = in.readString();
                this.night_wind_power = in.readString();
                this.day_wind_power = in.readString();
                this.day_weather_code = in.readString();
                this.sun_begin_end = in.readString();
                this.ziwaixian = in.readString();
                this.day_weather_pic = in.readString();
                this.weekday = in.readInt();
                this.night_air_temperature = in.readString();
                this.day_air_temperature = in.readString();
                this.day_wind_direction = in.readString();
                this.day = in.readString();
                this.night_weather_pic = in.readString();
                this.night_wind_direction = in.readString();
            }

            public static final Creator<F3Bean> CREATOR = new Creator<F3Bean>() {
                @Override
                public F3Bean createFromParcel(Parcel source) {
                    return new F3Bean(source);
                }

                @Override
                public F3Bean[] newArray(int size) {
                    return new F3Bean[size];
                }
            };
        }

        public static class F2Bean implements Parcelable {
            /**
             * day_weather : 晴
             * night_weather : 晴
             * night_weather_code : 00
             * air_press : 1013 hPa
             * jiangshui : 0%
             * night_wind_power : 微风 <5.4m/s
             * day_wind_power : 3-4级 5.5~7.9m/s
             * day_weather_code : 00
             * sun_begin_end : 04:52|19:33
             * ziwaixian : 中等
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/00.png
             * weekday : 4
             * night_air_temperature : 15
             * day_air_temperature : 31
             * day_wind_direction : 南风
             * day : 20170525
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/00.png
             * night_wind_direction : 北风
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.day_weather);
                dest.writeString(this.night_weather);
                dest.writeString(this.night_weather_code);
                dest.writeString(this.air_press);
                dest.writeString(this.jiangshui);
                dest.writeString(this.night_wind_power);
                dest.writeString(this.day_wind_power);
                dest.writeString(this.day_weather_code);
                dest.writeString(this.sun_begin_end);
                dest.writeString(this.ziwaixian);
                dest.writeString(this.day_weather_pic);
                dest.writeInt(this.weekday);
                dest.writeString(this.night_air_temperature);
                dest.writeString(this.day_air_temperature);
                dest.writeString(this.day_wind_direction);
                dest.writeString(this.day);
                dest.writeString(this.night_weather_pic);
                dest.writeString(this.night_wind_direction);
            }

            public F2Bean() {
            }

            protected F2Bean(Parcel in) {
                this.day_weather = in.readString();
                this.night_weather = in.readString();
                this.night_weather_code = in.readString();
                this.air_press = in.readString();
                this.jiangshui = in.readString();
                this.night_wind_power = in.readString();
                this.day_wind_power = in.readString();
                this.day_weather_code = in.readString();
                this.sun_begin_end = in.readString();
                this.ziwaixian = in.readString();
                this.day_weather_pic = in.readString();
                this.weekday = in.readInt();
                this.night_air_temperature = in.readString();
                this.day_air_temperature = in.readString();
                this.day_wind_direction = in.readString();
                this.day = in.readString();
                this.night_weather_pic = in.readString();
                this.night_wind_direction = in.readString();
            }

            public static final Creator<F2Bean> CREATOR = new Creator<F2Bean>() {
                @Override
                public F2Bean createFromParcel(Parcel source) {
                    return new F2Bean(source);
                }

                @Override
                public F2Bean[] newArray(int size) {
                    return new F2Bean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.time);
            dest.writeInt(this.ret_code);
            dest.writeParcelable(this.cityInfo, flags);
            dest.writeParcelable(this.now, flags);
            dest.writeParcelable(this.f1, flags);
            dest.writeParcelable(this.f3, flags);
            dest.writeParcelable(this.f2, flags);
        }

        public ShowapiResBodyBean() {
        }

        protected ShowapiResBodyBean(Parcel in) {
            this.time = in.readString();
            this.ret_code = in.readInt();
            this.cityInfo = in.readParcelable(CityInfoBean.class.getClassLoader());
            this.now = in.readParcelable(NowBean.class.getClassLoader());
            this.f1 = in.readParcelable(F1Bean.class.getClassLoader());
            this.f3 = in.readParcelable(F3Bean.class.getClassLoader());
            this.f2 = in.readParcelable(F2Bean.class.getClassLoader());
        }

        public static final Creator<ShowapiResBodyBean> CREATOR = new Creator<ShowapiResBodyBean>() {
            @Override
            public ShowapiResBodyBean createFromParcel(Parcel source) {
                return new ShowapiResBodyBean(source);
            }

            @Override
            public ShowapiResBodyBean[] newArray(int size) {
                return new ShowapiResBodyBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.showapi_res_code);
        dest.writeString(this.showapi_res_error);
        dest.writeParcelable(this.showapi_res_body, flags);
    }

    public AliWeather() {
    }

    protected AliWeather(Parcel in) {
        this.showapi_res_code = in.readInt();
        this.showapi_res_error = in.readString();
        this.showapi_res_body = in.readParcelable(ShowapiResBodyBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AliWeather> CREATOR = new Parcelable.Creator<AliWeather>() {
        @Override
        public AliWeather createFromParcel(Parcel source) {
            return new AliWeather(source);
        }

        @Override
        public AliWeather[] newArray(int size) {
            return new AliWeather[size];
        }
    };
}
