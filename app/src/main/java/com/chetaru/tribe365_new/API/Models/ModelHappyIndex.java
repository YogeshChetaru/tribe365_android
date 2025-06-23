package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelHappyIndex {

    @SerializedName("average")
    private String mAverage;
    @SerializedName("happy")
    private String mHappy;
    @SerializedName("sad")
    private String mSad;
    @SerializedName("week")
    private String mWeek;
    @SerializedName("monthName")
    private String mMonthName;
    @SerializedName("dayName")
    private String mDayName;
    @SerializedName("year")
    private String year;


    @SerializedName("month")
    private String mMonth;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getmMonthName() {
        return mMonthName;
    }

    public void setmMonthName(String mMonthName) {
        this.mMonthName = mMonthName;
    }

    public String getmDayName() {
        return mDayName;
    }

    public void setmDayName(String mDayName) {
        this.mDayName = mDayName;
    }


    public String getAverage() {
        return mAverage;
    }

    public void setAverage(String average) {
        mAverage = average;
    }

    public String getHappy() {
        return mHappy;
    }

    public void setHappy(String happy) {
        mHappy = happy;
    }

    public String getSad() {
        return mSad;
    }

    public void setSad(String sad) {
        mSad = sad;
    }

    public String getWeek() {
        return mWeek;
    }

    public void setWeek(String week) {
        mWeek = week;
    }

    public String getmMonth() {
        return mMonth;
    }

    public void setmMonth(String mMonth) {
        this.mMonth = mMonth;
    }
}
