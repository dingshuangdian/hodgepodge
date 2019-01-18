package com.lsqidsd.hodgepodge.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String formatTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long minute = 0;
        long second = 0;
        try {
            Date begin = sdf.parse(time);
            long thisTime = System.currentTimeMillis();
            long between = (thisTime - begin.getTime()) / 1000;//除以1000是为了转换成秒
            day = between / (24 * 3600);
            hour = between % (24 * 3600) / 3600;
            minute = between % 3600 / 60;
            second = between % 60 / 60;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (hour == 0) {
            return minute + "分钟前";
        } else {
            return hour + "小时前";
        }
    }
}
