package com.sky.app.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by dyb on 13-12-21.
 */
class YixDateUtils {
    public static final String DATE_FORMAT = 'yyyy-MM-dd'
    public static final String DATE_TIME_FORMAT = 'yyyy-MM-dd HH:mm:ss'

    static String formatDate(Date date, String formatPattern) {
        DateFormat format = new SimpleDateFormat(formatPattern);
        if (date != null) {
            return format.format(date)
        } else {
            return ""
        }
    }

    static Date parseDate(String dateString, String formatPattern) {
        DateFormat format = new SimpleDateFormat(formatPattern);
        if (dateString!=null){
            try{
                return format.parse(dateString)
            }catch (ParseException ignored){
            }
        }
        return null;
    }
}
