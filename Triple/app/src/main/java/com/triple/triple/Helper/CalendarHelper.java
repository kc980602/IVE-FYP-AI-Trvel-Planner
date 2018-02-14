package com.triple.triple.Helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.triple.triple.Presenter.Account.LoginActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Kevin on 2018/2/14.
 */

public class CalendarHelper {

    public static String endDate(String startDate, int duration) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
        }
        c.add(Calendar.DATE, duration -1);
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
        Calendar currDate = Calendar.getInstance();
        Calendar targetDate = Calendar.getInstance();
        try {
            targetDate.setTime(sdf.parse(date));
        } catch (ParseException e) {
        }

        if(targetDate.after(currDate)){
            dateLeft = targetDate.get(Calendar.DAY_OF_MONTH) -(currDate.get(Calendar.DAY_OF_MONTH));
        } else {
            dateLeft = 0;
        }
        return dateLeft;
    }
}
