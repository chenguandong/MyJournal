package com.smart.weather.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author guandongchen
 * @date 2017/11/28
 */

public class TodayWeatherBean implements Serializable{


    /**
     * count : 1
     * forecasts : [{"adcode":"410102","casts":[{"date":"2018-01-05","daypower":"≤3","daytemp":"0","dayweather":"晴","daywind":"南","nightpower":"≤3","nighttemp":"-8","nightweather":"阴","nightwind":"南","week":"5"},{"date":"2018-01-06","daypower":"≤3","daytemp":"-2","dayweather":"中雪","daywind":"无风向","nightpower":"≤3","nighttemp":"-6","nightweather":"小雪","nightwind":"西北","week":"6"},{"date":"2018-01-07","daypower":"4","daytemp":"-1","dayweather":"阴","daywind":"西北","nightpower":"4","nighttemp":"-7","nightweather":"多云","nightwind":"西北","week":"7"},{"date":"2018-01-08","daypower":"4","daytemp":"-1","dayweather":"晴","daywind":"西北","nightpower":"≤3","nighttemp":"-5","nightweather":"晴","nightwind":"西北","week":"1"}],"city":"中原区","province":"河南","reporttime":"2018-01-05 18:00:00"}]
     * info : OK
     * infocode : 10000
     * status : 1
     */

    private String count;
    private String info;
    private String infocode;
    private String status;
    private List<ForecastsBean> forecasts;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ForecastsBean> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastsBean> forecasts) {
        this.forecasts = forecasts;
    }

    public static class ForecastsBean implements Serializable{
        /**
         * adcode : 410102
         * casts : [{"date":"2018-01-05","daypower":"≤3","daytemp":"0","dayweather":"晴","daywind":"南","nightpower":"≤3","nighttemp":"-8","nightweather":"阴","nightwind":"南","week":"5"},{"date":"2018-01-06","daypower":"≤3","daytemp":"-2","dayweather":"中雪","daywind":"无风向","nightpower":"≤3","nighttemp":"-6","nightweather":"小雪","nightwind":"西北","week":"6"},{"date":"2018-01-07","daypower":"4","daytemp":"-1","dayweather":"阴","daywind":"西北","nightpower":"4","nighttemp":"-7","nightweather":"多云","nightwind":"西北","week":"7"},{"date":"2018-01-08","daypower":"4","daytemp":"-1","dayweather":"晴","daywind":"西北","nightpower":"≤3","nighttemp":"-5","nightweather":"晴","nightwind":"西北","week":"1"}]
         * city : 中原区
         * province : 河南
         * reporttime : 2018-01-05 18:00:00
         */

        private String adcode;
        private String city;
        private String province;
        private String reporttime;
        private List<CastsBean> casts;

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getReporttime() {
            return reporttime;
        }

        public void setReporttime(String reporttime) {
            this.reporttime = reporttime;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public static class CastsBean {
            /**
             * date : 2018-01-05
             * daypower : ≤3
             * daytemp : 0
             * dayweather : 晴
             * daywind : 南
             * nightpower : ≤3
             * nighttemp : -8
             * nightweather : 阴
             * nightwind : 南
             * week : 5
             */

            private String date;
            private String daypower;
            private String daytemp;
            private String dayweather;
            private String daywind;
            private String nightpower;
            private String nighttemp;
            private String nightweather;
            private String nightwind;
            private String week;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDaypower() {
                return daypower;
            }

            public void setDaypower(String daypower) {
                this.daypower = daypower;
            }

            public String getDaytemp() {
                return daytemp;
            }

            public void setDaytemp(String daytemp) {
                this.daytemp = daytemp;
            }

            public String getDayweather() {
                return dayweather;
            }

            public void setDayweather(String dayweather) {
                this.dayweather = dayweather;
            }

            public String getDaywind() {
                return daywind;
            }

            public void setDaywind(String daywind) {
                this.daywind = daywind;
            }

            public String getNightpower() {
                return nightpower;
            }

            public void setNightpower(String nightpower) {
                this.nightpower = nightpower;
            }

            public String getNighttemp() {
                return nighttemp;
            }

            public void setNighttemp(String nighttemp) {
                this.nighttemp = nighttemp;
            }

            public String getNightweather() {
                return nightweather;
            }

            public void setNightweather(String nightweather) {
                this.nightweather = nightweather;
            }

            public String getNightwind() {
                return nightwind;
            }

            public void setNightwind(String nightwind) {
                this.nightwind = nightwind;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }
        }
    }
}
