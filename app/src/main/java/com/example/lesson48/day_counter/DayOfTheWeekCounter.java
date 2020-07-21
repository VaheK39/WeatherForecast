package com.example.lesson48.day_counter;

import android.content.Context;

import com.example.lesson48.R;
import com.example.lesson48.utils.Constants;

public class DayOfTheWeekCounter {
    private static final String TAG = "DayOfTheWeekCounter";

    private int[] date;
    private int monthCode;
    private int centuryCode;
    private int yearCode;
    private int leapYearAdjustment;
    private Context context;

    public DayOfTheWeekCounter(Context context, String date) {
        this.context = context;
        this.date = convertDateToIntArray(date);

        this.monthCode = getMonthCode();
        this.centuryCode = getCenturyCode();
        this.yearCode = getYearCode(this.date[3]);
        this.leapYearAdjustment = leapYearAdjustment(this.date[0], this.date[1]);

    }


    public String getDayOfTheWeek() {
        int dayNum = (yearCode + monthCode + centuryCode + date[2] - leapYearAdjustment) % 7;
        switch (dayNum) {
            case 0:
                return context.getString(R.string.sunday);
            case 1:
                return context.getString(R.string.monday);

            case 2:
                return context.getString(R.string.tuesday);

            case 3:
                return context.getString(R.string.wednesday);

            case 4:
                return context.getString(R.string.thursday);

            case 5:
                return context.getString(R.string.friday);

            case 6:
                return context.getString(R.string.saturday);
            default: return  context.getString(R.string.no_data);

        }
    }



    private int [] convertDateToIntArray(String date) {
        String[] strArray = date.split("-");
        int[] arr = new int[strArray.length + 1];
        for (int i = 0; i < strArray.length; i++) {
            arr[i] = Integer.parseInt(strArray[i]);
        }
        arr[strArray.length] = Integer.parseInt(strArray[0].substring(2));
        //arr[0] - year
        //arr[1] - month
        //arr[2] - day
        //arr[3] - the last two digits of year
        return arr;
    }

    private int getYearCode(int lastTwoDigitsOfYear) {
        return (lastTwoDigitsOfYear + (lastTwoDigitsOfYear/4)) % 7;
    }

    private int leapYearAdjustment(int year, int month) {
       if (month <= 2) {
           if (year % 4 == 0) {
               if (year % 100 == 0) {
                   if (year % 400 == 0) {
                       return 1;
                   }
                   return 0;
               }
               return 1;
           }
           return 0;
       }
       return 0;
    }

    private int getMonthCode() {
        switch (date[1]) {
            case 1:
                return monthCode = Constants.MonthCodes.JANUARY;

            case 2:
                return monthCode = Constants.MonthCodes.FEBRUARY;

            case 3:
                return monthCode = Constants.MonthCodes.MARCH;

            case 4:
                return monthCode = Constants.MonthCodes.APRIL;

            case 5:
                return monthCode = Constants.MonthCodes.MAY;

            case 6:
                return monthCode = Constants.MonthCodes.JUNE;

            case 7:
                return monthCode = Constants.MonthCodes.JULY;

            case 8:
                return monthCode = Constants.MonthCodes.AUGUST;

            case 9:
                return monthCode = Constants.MonthCodes.SEPTEMBER;

            case 10:
                return monthCode = Constants.MonthCodes.OCTOBER;

            case 11:
                return monthCode = Constants.MonthCodes.NOVEMBER;

            case 12:
                return monthCode = Constants.MonthCodes.DECEMBER;

            default: return -1;

        }
    }

    private int getCenturyCode() {
        if (date[0] >= 1900 && date[0] < 2000) {
            return Constants.CenturyCodes.CE_1900;
        }
        else if (date[0] >= 2000 && date[0] < 2100) {
            return Constants.CenturyCodes.CE_2000;
        }else if (date[0] >= 2100 && date[0] < 2200) {
            return Constants.CenturyCodes.CE_2200;
        }
        return -1;
    }



}
