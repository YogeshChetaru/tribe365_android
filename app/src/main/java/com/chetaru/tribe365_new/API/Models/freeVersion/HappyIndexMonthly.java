package com.chetaru.tribe365_new.API.Models.freeVersion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HappyIndexMonthly {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("score")
    @Expose
    private Double score;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
