package com.chetaru.tribe365_new.API.Models.MemberHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KudosCount {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("KudosCount")
    @Expose
    private Integer kudosCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKudosCount() {
        return kudosCount;
    }

    public void setKudosCount(Integer kudosCount) {
        this.kudosCount = kudosCount;
    }
}
