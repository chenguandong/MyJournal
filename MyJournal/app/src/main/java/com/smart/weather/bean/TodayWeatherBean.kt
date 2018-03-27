package com.smart.weather.bean

import java.io.Serializable

/**
 * @author guandongchen
 * @date 2017/11/28
 */

class TodayWeatherBean : Serializable {


    /**
     * count : 1
     * forecasts : [{"adcode":"410102","casts":[{"date":"2018-01-05","daypower":"≤3","daytemp":"0","dayweather":"晴","daywind":"南","nightpower":"≤3","nighttemp":"-8","nightweather":"阴","nightwind":"南","week":"5"},{"date":"2018-01-06","daypower":"≤3","daytemp":"-2","dayweather":"中雪","daywind":"无风向","nightpower":"≤3","nighttemp":"-6","nightweather":"小雪","nightwind":"西北","week":"6"},{"date":"2018-01-07","daypower":"4","daytemp":"-1","dayweather":"阴","daywind":"西北","nightpower":"4","nighttemp":"-7","nightweather":"多云","nightwind":"西北","week":"7"},{"date":"2018-01-08","daypower":"4","daytemp":"-1","dayweather":"晴","daywind":"西北","nightpower":"≤3","nighttemp":"-5","nightweather":"晴","nightwind":"西北","week":"1"}],"city":"中原区","province":"河南","reporttime":"2018-01-05 18:00:00"}]
     * info : OK
     * infocode : 10000
     * status : 1
     */

    var count: String? = null
    var info: String? = null
    var infocode: String? = null
    var status: String? = null
    var forecasts: List<ForecastsBean>? = null

    class ForecastsBean : Serializable {
        /**
         * adcode : 410102
         * casts : [{"date":"2018-01-05","daypower":"≤3","daytemp":"0","dayweather":"晴","daywind":"南","nightpower":"≤3","nighttemp":"-8","nightweather":"阴","nightwind":"南","week":"5"},{"date":"2018-01-06","daypower":"≤3","daytemp":"-2","dayweather":"中雪","daywind":"无风向","nightpower":"≤3","nighttemp":"-6","nightweather":"小雪","nightwind":"西北","week":"6"},{"date":"2018-01-07","daypower":"4","daytemp":"-1","dayweather":"阴","daywind":"西北","nightpower":"4","nighttemp":"-7","nightweather":"多云","nightwind":"西北","week":"7"},{"date":"2018-01-08","daypower":"4","daytemp":"-1","dayweather":"晴","daywind":"西北","nightpower":"≤3","nighttemp":"-5","nightweather":"晴","nightwind":"西北","week":"1"}]
         * city : 中原区
         * province : 河南
         * reporttime : 2018-01-05 18:00:00
         */

        var adcode: String? = null
        var city: String? = null
        var province: String? = null
        var reporttime: String? = null
        var casts: List<CastsBean>? = null

        class CastsBean : Serializable {
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

            var date: String? = null
            var daypower: String? = null
            var daytemp: String? = null
            var dayweather: String? = null
            var daywind: String? = null
            var nightpower: String? = null
            var nighttemp: String? = null
            var nightweather: String? = null
            var nightwind: String? = null
            var week: String? = null
        }
    }
}
