package com.hongyan.life.bean;

public class WeatherNow {
    /**
     * cityid : 101330103
     * date : 2020-04-10
     * week : 星期五
     * update_time : 2020-04-10 23:24:53
     * city : 路环岛
     * cityEn : luhuandao
     * country : 中国
     * countryEn : China
     * wea : 晴
     * wea_img : qing
     * tem : 21
     * tem1 : 26
     * tem2 : 19
     * win : 东风
     * win_speed : 3级
     * win_meter : 小于12km/h
     * humidity : 88%
     * visibility : 暂缺
     * pressure : 1014
     * air : null
     * air_pm25 : null
     * air_level : 优
     * air_tips : 空气很好，可以外出活动，呼吸新鲜空气，拥抱大自然！
     * alarm : {"alarm_type":"","alarm_level":"","alarm_content":""}
     */

    private String cityid;
    private String date;
    private String week;
    private String update_time;
    private String city;
    private String cityEn;
    private String country;
    private String countryEn;
    private String wea;
    private String wea_img;
    private String tem;
    private String tem1;
    private String tem2;
    private String win;
    private String win_speed;
    private String win_meter;
    private String humidity;
    private String visibility;
    private String pressure;
    private String air;
    private String air_pm25;
    private String air_level;
    private String air_tips;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWea_img() {
        return wea_img;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    public String getWin_meter() {
        return win_meter;
    }

    public void setWin_meter(String win_meter) {
        this.win_meter = win_meter;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    public String getAir_pm25() {
        return air_pm25;
    }

    public void setAir_pm25(String air_pm25) {
        this.air_pm25 = air_pm25;
    }

    public String getAir_level() {
        return air_level;
    }

    public void setAir_level(String air_level) {
        this.air_level = air_level;
    }

    public String getAir_tips() {
        return air_tips;
    }

    public void setAir_tips(String air_tips) {
        this.air_tips = air_tips;
    }


    @Override
    public String toString() {
        return "WeatherNow{" +
                "cityid='" + cityid + '\'' +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", update_time='" + update_time + '\'' +
                ", city='" + city + '\'' +
                ", cityEn='" + cityEn + '\'' +
                ", country='" + country + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", wea='" + wea + '\'' +
                ", wea_img='" + wea_img + '\'' +
                ", tem='" + tem + '\'' +
                ", tem1='" + tem1 + '\'' +
                ", tem2='" + tem2 + '\'' +
                ", win='" + win + '\'' +
                ", win_speed='" + win_speed + '\'' +
                ", win_meter='" + win_meter + '\'' +
                ", humidity='" + humidity + '\'' +
                ", visibility='" + visibility + '\'' +
                ", pressure='" + pressure + '\'' +
                ", air='" + air + '\'' +
                ", air_pm25='" + air_pm25 + '\'' +
                ", air_level='" + air_level + '\'' +
                ", air_tips='" + air_tips + '\'' +
                '}';
    }
}
