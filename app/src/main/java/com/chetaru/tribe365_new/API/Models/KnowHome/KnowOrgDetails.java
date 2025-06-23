package com.chetaru.tribe365_new.API.Models.KnowHome;

import com.chetaru.tribe365_new.API.Models.MemberHome.LatestKudosAward;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KnowOrgDetails {
    @SerializedName("todayHIPercent")
    @Expose
    private Double todayHIPercent;
    @SerializedName("todayDay")
    @Expose
    private String todayDay;
    @SerializedName("yesterdayHIPercent")
    @Expose
    private String yesterdayHIPercent;
    @SerializedName("yesterdayDay")
    @Expose
    private String yesterdayDay;
    @SerializedName("dayBeforeYesterdayHIPercent")
    @Expose
    private String dayBeforeYesterdayHIPercent;

    @SerializedName("dayBeforeYesterdayDay")
    @Expose
    private String dayBeforeYesterdayDay;

    @SerializedName("dayBeforeYesterdayDate")
    @Expose
    private String dayBeforeYesterdayDate;

    @SerializedName("thisWeekHIPercent")
    @Expose
    private String thisWeekHIPercent;

    @SerializedName("lastWeekHIPercent")
    @Expose
    private String lastWeekHIPercent;
    @SerializedName("lastToLastWeekHIPercent")
    @Expose
    private String lastToLastWeekHIPercent;
    @SerializedName("thisMonthHIPercent")
    @Expose
    private String thisMonthHIPercent;
    @SerializedName("thisMonthName")
    @Expose
    private String thisMonthName;

    @SerializedName("lastMonthHIPercent")
    @Expose
    private String lastMonthHIPercent;
    @SerializedName("lastMonthName")
    @Expose
    private String lastMonthName;
    @SerializedName("lastToLastMonthHIPercent")
    @Expose
    private String lastToLastMonthHIPercent;
    @SerializedName("lastToLastMonthName")
    @Expose
    private String lastToLastMonthName;

    @SerializedName("cultureIndex")
    @Expose
    private Double cultureIndex;
    @SerializedName("engagementIndex")
    @Expose
    private Double engagementIndex;
    @SerializedName("cultureIndexRank")
    @Expose
    private Integer cultureIndexRank;
    @SerializedName("engagementIndexRank")
    @Expose
    private Integer engagementIndexRank;

    @SerializedName("lastMonthKudosChamp")
    @Expose
    private List<KudosCampList> lastMonthKudosChamp = null;

    @SerializedName("latestKudosAward")
    @Expose
    public List<LatestKudosAward> latestKudosAward = null;

    public Double getTodayHIPercent() {
        return todayHIPercent;
    }

    public void setTodayHIPercent(Double todayHIPercent) {
        this.todayHIPercent = todayHIPercent;
    }

    public String getTodayDay() {
        return todayDay;
    }

    public void setTodayDay(String todayDay) {
        this.todayDay = todayDay;
    }

    public String getYesterdayDay() {
        return yesterdayDay;
    }

    public void setYesterdayDay(String yesterdayDay) {
        this.yesterdayDay = yesterdayDay;
    }

    public String getDayBeforeYesterdayDay() {
        return dayBeforeYesterdayDay;
    }

    public void setDayBeforeYesterdayDay(String dayBeforeYesterdayDay) {
        this.dayBeforeYesterdayDay = dayBeforeYesterdayDay;
    }

    /*public Double getYesterdayHIPercent() {
        return yesterdayHIPercent;
    }

    public void setYesterdayHIPercent(Double yesterdayHIPercent) {
        this.yesterdayHIPercent = yesterdayHIPercent;
    }

    public Double getDayBeforeYesterdayHIPercent() {
        return dayBeforeYesterdayHIPercent;
    }

    public void setDayBeforeYesterdayHIPercent(Double dayBeforeYesterdayHIPercent) {
        this.dayBeforeYesterdayHIPercent = dayBeforeYesterdayHIPercent;
    }

    public String getDayBeforeYesterdayDate() {
        return dayBeforeYesterdayDate;
    }

    public void setDayBeforeYesterdayDate(String dayBeforeYesterdayDate) {
        this.dayBeforeYesterdayDate = dayBeforeYesterdayDate;
    }

    public Double getThisWeekHIPercent() {
        return thisWeekHIPercent;
    }

    public void setThisWeekHIPercent(Double thisWeekHIPercent) {
        this.thisWeekHIPercent = thisWeekHIPercent;
    }

    public Double getLastWeekHIPercent() {
        return lastWeekHIPercent;
    }

    public void setLastWeekHIPercent(Double lastWeekHIPercent) {
        this.lastWeekHIPercent = lastWeekHIPercent;
    }

    public Double getLastToLastWeekHIPercent() {
        return lastToLastWeekHIPercent;
    }

    public void setLastToLastWeekHIPercent(Double lastToLastWeekHIPercent) {
        this.lastToLastWeekHIPercent = lastToLastWeekHIPercent;
    }

    public Double getThisMonthHIPercent() {
        return thisMonthHIPercent;
    }

    public void setThisMonthHIPercent(Double thisMonthHIPercent) {
        this.thisMonthHIPercent = thisMonthHIPercent;
    }

    public Double getLastMonthHIPercent() {
        return lastMonthHIPercent;
    }

    public void setLastMonthHIPercent(Double lastMonthHIPercent) {
        this.lastMonthHIPercent = lastMonthHIPercent;
    }

    public Double getLastToLastMonthHIPercent() {
        return lastToLastMonthHIPercent;
    }

    public void setLastToLastMonthHIPercent(Double lastToLastMonthHIPercent) {
        this.lastToLastMonthHIPercent = lastToLastMonthHIPercent;
    }*/

    public Double getCultureIndex() {
        return cultureIndex;
    }

    public void setCultureIndex(Double cultureIndex) {
        this.cultureIndex = cultureIndex;
    }

    public Double getEngagementIndex() {
        return engagementIndex;
    }

    public void setEngagementIndex(Double engagementIndex) {
        this.engagementIndex = engagementIndex;
    }

    public List<KudosCampList> getLastMonthKudosChamp() {
        return lastMonthKudosChamp;
    }

    public void setLastMonthKudosChamp(List<KudosCampList> lastMonthKudosChamp) {
        this.lastMonthKudosChamp = lastMonthKudosChamp;
    }

    public Integer getCultureIndexRank() {
        return cultureIndexRank;
    }

    public void setCultureIndexRank(Integer cultureIndexRank) {
        this.cultureIndexRank = cultureIndexRank;
    }

    public Integer getEngagementIndexRank() {
        return engagementIndexRank;
    }

    public void setEngagementIndexRank(Integer engagementIndexRank) {
        this.engagementIndexRank = engagementIndexRank;
    }

    public List<LatestKudosAward> getLatestKudosAward() {
        return latestKudosAward;
    }

    public void setLatestKudosAward(List<LatestKudosAward> latestKudosAward) {
        this.latestKudosAward = latestKudosAward;
    }

    public String getYesterdayHIPercent() {
        return yesterdayHIPercent;
    }

    public void setYesterdayHIPercent(String yesterdayHIPercent) {
        this.yesterdayHIPercent = yesterdayHIPercent;
    }

    public String getDayBeforeYesterdayHIPercent() {
        return dayBeforeYesterdayHIPercent;
    }

    public void setDayBeforeYesterdayHIPercent(String dayBeforeYesterdayHIPercent) {
        this.dayBeforeYesterdayHIPercent = dayBeforeYesterdayHIPercent;
    }

    public String getDayBeforeYesterdayDate() {
        return dayBeforeYesterdayDate;
    }

    public void setDayBeforeYesterdayDate(String dayBeforeYesterdayDate) {
        this.dayBeforeYesterdayDate = dayBeforeYesterdayDate;
    }

    public String getThisWeekHIPercent() {
        return thisWeekHIPercent;
    }

    public void setThisWeekHIPercent(String thisWeekHIPercent) {
        this.thisWeekHIPercent = thisWeekHIPercent;
    }

    public String getLastWeekHIPercent() {
        return lastWeekHIPercent;
    }

    public void setLastWeekHIPercent(String lastWeekHIPercent) {
        this.lastWeekHIPercent = lastWeekHIPercent;
    }

    public String getLastToLastWeekHIPercent() {
        return lastToLastWeekHIPercent;
    }

    public void setLastToLastWeekHIPercent(String lastToLastWeekHIPercent) {
        this.lastToLastWeekHIPercent = lastToLastWeekHIPercent;
    }

    public String getThisMonthHIPercent() {
        return thisMonthHIPercent;
    }

    public void setThisMonthHIPercent(String thisMonthHIPercent) {
        this.thisMonthHIPercent = thisMonthHIPercent;
    }

    public String getLastMonthHIPercent() {
        return lastMonthHIPercent;
    }

    public void setLastMonthHIPercent(String lastMonthHIPercent) {
        this.lastMonthHIPercent = lastMonthHIPercent;
    }

    public String getLastToLastMonthHIPercent() {
        return lastToLastMonthHIPercent;
    }

    public void setLastToLastMonthHIPercent(String lastToLastMonthHIPercent) {
        this.lastToLastMonthHIPercent = lastToLastMonthHIPercent;
    }

    public String getThisMonthName() {
        return thisMonthName;
    }

    public void setThisMonthName(String thisMonthName) {
        this.thisMonthName = thisMonthName;
    }

    public String getLastMonthName() {
        return lastMonthName;
    }

    public void setLastMonthName(String lastMonthName) {
        this.lastMonthName = lastMonthName;
    }

    public String getLastToLastMonthName() {
        return lastToLastMonthName;
    }

    public void setLastToLastMonthName(String lastToLastMonthName) {
        this.lastToLastMonthName = lastToLastMonthName;
    }
}
