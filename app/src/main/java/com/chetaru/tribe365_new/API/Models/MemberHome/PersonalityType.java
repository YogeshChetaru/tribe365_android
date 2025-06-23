package com.chetaru.tribe365_new.API.Models.MemberHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalityType {
    @SerializedName("cateName")
    @Expose
    private String cateName;
    @SerializedName("score")
    @Expose
    private Integer score;

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
