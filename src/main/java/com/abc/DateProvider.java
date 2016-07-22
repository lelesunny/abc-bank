package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    public static boolean isLeap() {
    	int year = getInstance().now().getYear();
    	if ((year%100 == 0) && (year%400 != 0)) return false;
    	if (year%4 == 0) return true;
    	return false;
    }
}
