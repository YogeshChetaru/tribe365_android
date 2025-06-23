package com.chetaru.tribe365_new.API.Models.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeKudosResponse {
    @SerializedName("belief")
    @Expose
    private List<HomeBelief> belief = null;

    public List<HomeBelief> getBelief() {
        return belief;
    }

    public void setBelief(List<HomeBelief> belief) {
        this.belief = belief;
    }
    @SerializedName("kudoAwardKey")
    @Expose
    public String kudoAwardKey;
    @SerializedName("todayKudosAwardCount")
    @Expose
    public Integer todayKudosAwardCount;
    @SerializedName("yesterdayKudosAwardCount")
    @Expose
    public Integer yesterdayKudosAwardCount;
    @SerializedName("thisWeekKudosAwardCount")
    @Expose
    public Integer thisWeekKudosAwardCount;
    @SerializedName("lastWeekKudosAwardCount")
    @Expose
    public Integer lastWeekKudosAwardCount;
    @SerializedName("thisMonthKudosAwardCount")
    @Expose
    public Integer thisMonthKudosAwardCount;
    @SerializedName("lastMonthKudosAwardCount")
    @Expose
    public Integer lastMonthKudosAwardCount;
    @SerializedName("totalKudosAwardCount")
    @Expose
    public Integer totalKudosAwardCount;

    public String getKudoAwardKey() {
        return kudoAwardKey;
    }

    public void setKudoAwardKey(String kudoAwardKey) {
        this.kudoAwardKey = kudoAwardKey;
    }

    public Integer getTodayKudosAwardCount() {
        return todayKudosAwardCount;
    }

    public void setTodayKudosAwardCount(Integer todayKudosAwardCount) {
        this.todayKudosAwardCount = todayKudosAwardCount;
    }

    public Integer getYesterdayKudosAwardCount() {
        return yesterdayKudosAwardCount;
    }

    public void setYesterdayKudosAwardCount(Integer yesterdayKudosAwardCount) {
        this.yesterdayKudosAwardCount = yesterdayKudosAwardCount;
    }

    public Integer getThisWeekKudosAwardCount() {
        return thisWeekKudosAwardCount;
    }

    public void setThisWeekKudosAwardCount(Integer thisWeekKudosAwardCount) {
        this.thisWeekKudosAwardCount = thisWeekKudosAwardCount;
    }

    public Integer getLastWeekKudosAwardCount() {
        return lastWeekKudosAwardCount;
    }

    public void setLastWeekKudosAwardCount(Integer lastWeekKudosAwardCount) {
        this.lastWeekKudosAwardCount = lastWeekKudosAwardCount;
    }

    public Integer getThisMonthKudosAwardCount() {
        return thisMonthKudosAwardCount;
    }

    public void setThisMonthKudosAwardCount(Integer thisMonthKudosAwardCount) {
        this.thisMonthKudosAwardCount = thisMonthKudosAwardCount;
    }

    public Integer getLastMonthKudosAwardCount() {
        return lastMonthKudosAwardCount;
    }

    public void setLastMonthKudosAwardCount(Integer lastMonthKudosAwardCount) {
        this.lastMonthKudosAwardCount = lastMonthKudosAwardCount;
    }

    public Integer getTotalKudosAwardCount() {
        return totalKudosAwardCount;
    }

    public void setTotalKudosAwardCount(Integer totalKudosAwardCount) {
        this.totalKudosAwardCount = totalKudosAwardCount;
    }
}
