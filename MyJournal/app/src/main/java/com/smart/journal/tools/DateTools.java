package com.smart.journal.tools;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author guandongchen
 * @date 2018/1/12
 */
public class DateTools {
    private static Locale locale = Locale.getDefault();

    /**
     * 格式化时间格式
     */
    public static String formatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", locale);
        String birthStr = sdf.format(time);
        return birthStr;
    }

    public static String getChineseWeek(Date date) {

        return TimeUtils.getChineseWeek(date);
    }

    /**
     * 获取时间的年、月、日
     */
    public static int[] getYMd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        return new int[] { year, month + 1, day };
    }

    public static String coverNumToWeekChina(String num) {
        String numName = num;
        switch (num) {
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
