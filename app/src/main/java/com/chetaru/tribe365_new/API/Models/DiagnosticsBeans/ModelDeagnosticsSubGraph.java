package com.chetaru.tribe365_new.API.Models.DiagnosticsBeans;


import com.google.gson.annotations.SerializedName;


public class ModelDeagnosticsSubGraph {

    @SerializedName("percentage")
    private String mPercentage;
    @SerializedName("score")
    private String mScore;
    @SerializedName("title")
    private String mTitle;

    public String getPercentage() {
        return mPercentage;
    }

    public void setPercentage(String percentage) {
        mPercentage = percentage;
    }

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
