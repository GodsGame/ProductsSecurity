package com.example.productSecurity.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

    public static Date date(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = dateFormat.parse(date);
        return new Date(date1.getTime());
    }

}
