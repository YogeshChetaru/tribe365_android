package com.chetaru.tribe365_new.API.Models.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EngagementScore {
    @SerializedName("todayEIScore")
    @Expose
    public String todayEIScore;

    public String getTodayEIScore() {
        return todayEIScore;
    }

    public void setTodayEIScore(String todayEIScore) {
        this.todayEIScore = todayEIScore;
    }
}
