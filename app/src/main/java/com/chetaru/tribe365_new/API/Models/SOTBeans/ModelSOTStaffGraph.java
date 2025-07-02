package com.chetaru.tribe365_new.API.Models.SOTBeans;


import com.google.gson.annotations.SerializedName;


public class ModelSOTStaffGraph {

    @SerializedName("score")
    private String mScore;
    @SerializedName("title")
    private String mTitle;

    public String getScore() {
        return mScore;
    }

    public void setScore(String score) {
        mScore = score;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
