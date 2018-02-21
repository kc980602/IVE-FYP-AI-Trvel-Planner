package com.triple.triple.Helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kevin on 2018/2/14.
 */

public class DateTimeHelper {

    public static String endDate(String startDate, int duration) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
        }
        c.add(Calendar.DATE, duration - 1);
        return sdf.format(c.getTime());

    }

    public static String castDateToLocale(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
        }
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        return currentDate;
    }

    public static String castDateToLocaleFull(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
        }
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        return currentDate;
    }

    public static int daysLeft(String date) {
        int dateLeft;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar target = Calendar.getInstance();
        Calendar curr = Calendar.getInstance();
        try {
            target.setTime(sdf.parse(date));
        } catch (ParseException e) {
        }

        if (target.after(curr)) {
            Date targetDate = target.getTime();
            Date currDate = new Date();
            long diff = targetDate.getTime() - currDate.getTime();
            dateLeft = (int) (diff / (1000L * 60L * 60L * 24L));
        } else {
            dateLeft = 0;
        }
        return dateLeft;
    }

    public static Calendar twoYearsLater() {
        String date = "";
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 730);
        return c;

    }

    public static double millisToHourMin(int millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.;
    }


    public static String endTime(String startTime, int duration) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MILLISECOND, duration);
        String newTime = sdf.format(cal.getTime());
        return newTime;

    }
}
