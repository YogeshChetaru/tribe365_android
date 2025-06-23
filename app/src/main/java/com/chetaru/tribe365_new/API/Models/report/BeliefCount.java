package com.chetaru.tribe365_new.API.Models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeliefCount {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("kudosCount")
    @Expose
    private Integer kudosCount;
    @SerializedName("lastKudosCount")
    @Expose
    private Integer lastKudosCount;
    @SerializedName("kudosAwardCount")
    @Expose
    private Integer kudosAwardCount;
    @SerializedName("lastKudosAwardCount")
    @Expose
    private Integer lastKudosAwardCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getLastKudosCount() {
        return lastKudosCount;
    }

    public void setLastKudosCount(Integer lastKudosCount) {
        this.lastKudosCount = lastKudosCount;
    }

    public Integer getKudosAwardCount() {
        return kudosAwardCount;
    }

    public void setKudosAwardCount(Integer kudosAwardCount) {
        this.kudosAwardCount = kudosAwardCount;
    }

    public Integer getLastKudosAwardCount() {
        return lastKudosAwardCount;
    }

    public void setLastKudosAwardCount(Integer lastKudosAwardCount) {
        this.lastKudosAwardCount = lastKudosAwardCount;
    }
}
