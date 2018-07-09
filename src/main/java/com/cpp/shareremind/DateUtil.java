package com.cpp.shareremind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public static Date toDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toDateStr(Date dateStr) {
        return dateFormat.format(dateStr);
    }
}
