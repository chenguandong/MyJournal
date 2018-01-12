package com.smart.weather.tools;

/**
 * @author guandongchen
 * @date 2018/1/12
 */

public class DateTools {

    public  static String coverNumToWeekChina(String num){
        String numName = num;
        switch (num){
            case "1":
                numName = "一";
                break;
            case "2":
                numName = "二";
                break;
            case "3":
                numName = "三";
                break;
            case "4":
                numName = "四";
                break;
            case "5":
                numName = "五";
                break;
            case "6":
                numName = "六";
                break;
            case "7":
                numName = "日";
                break;
            default:

                break;
        }
        return numName;
    }
}
