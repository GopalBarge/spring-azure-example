package com.sal.prompt.web.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

@UtilityClass
public class CommonUtility {

    String intToStringConvertor(Integer value) {
        String result = Integer.toString(value);
        return result;
    }

    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return formatter.format(date);
    }

   public static String dateToString(Date value) {
        SimpleDateFormat dateformater = new SimpleDateFormat("yyyy/MM/dd");
        String result = dateformater.format(value);
        return result;
    }
    public static String getBatchId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String doubleToString(Double input) {
        Formatter formatter = new Formatter();
        formatter.format("%.2f", input);
        return formatter.toString();
    }
}
