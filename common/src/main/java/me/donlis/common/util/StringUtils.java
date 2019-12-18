package me.donlis.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    /**
     * 字符串拼接，线程安全
     * @param array
     * @return
     */
    public static String buffer(String... array) {
        StringBuffer s = new StringBuffer();
        for (String str : array) {
            s.append(str);
        }
        return s.toString();
    }

    /**
     * 字符串拼接，线程不安全，效率高
     * @param array
     * @return
     */
    public static String builder(String... array) {
        StringBuilder s = new StringBuilder();
        for (String str : array) {
            s.append(str);
        }
        return s.toString();
    }

    /**
     * 日期转字符串
     * @param date
     * @param pattern 例如: "yyyy-MM-dd hh:mm:ss"
     * @return
     */
    public static String DateToStr(Date date,String pattern){
        if(date == null){
            return "";
        }
        if(pattern == null || pattern.trim().length() == 0){
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 字符串转日期
     * @param date
     * @param pattern 例如: "yyyy-MM-dd hh:mm:ss"
     * @return
     */
    public static Date StrToDate(String date,String pattern){
        if(date == null || date.trim().length() == 0){
            return null;
        }
        if(pattern == null || pattern.trim().length() == 0){
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

}
