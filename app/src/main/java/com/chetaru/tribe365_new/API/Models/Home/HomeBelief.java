package com.chetaru.tribe365_new.API.Models.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeBelief {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("valueUrl")
    @Expose
    private String valueUrl;
    @SerializedName("valueDesc")
    @Expose
    private String valueDesc;
    @SerializedName("valueId")
    @Expose
    private String valueId;
    @SerializedName("beliefId")
    @Expose
    private Integer beliefId;
    @SerializedName("dotId")
    @Expose
    private Integer dotId;
    @SerializedName("todayKudosCount")
    @Expose
    private Integer todayKudosCount;
    @SerializedName("yesterdayKudosCount")
    @Expose
    private Integer yesterdayKudosCount;
    @SerializedName("thisWeekKudosCount")
    @Expose
    private Integer thisWeekKudosCount;
    @SerializedName("lastWeekKudosCount")
    @Expose
    private Integer lastWeekKudosCount;
    @SerializedName("thisMonthKudosCount")
    @Expose
    private Integer thisMonthKudosCount;
    @SerializedName("lastMonthKudosCount")
    @Expose
    private Integer lastMonthKudosCount;
    @SerializedName("totalKudosCount")
    @Expose
    private Integer totalKudosCount;

    @SerializedName("todayDotValueKudoAwardCount")
    @Expose
    private Integer todayDotValueKudoAwardCount;
    @SerializedName("yesterdayDotValueKudoAwardCount")
    @Expose
    private Integer yesterdayDotValueKudoAwardCount;
    @SerializedName("thisWeekDotValueKudoAwardCount")
    @Expose
    private Integer thisWeekDotValueKudoAwardCount;
    @SerializedName("lastWeekDotValueKudoAwardCount")
    @Expose
    private Integer lastWeekDotValueKudoAwardCount;
    @SerializedName("thisMonthDotValueKudoAwardCount")
    @Expose
    private Integer thisMonthDotValueKudoAwardCount;
    @SerializedName("lastMonthDotValueKudoAwardCount")
    @Expose
    private Integer lastMonthDotValueKudoAwardCount;
    @SerializedName("totalDotValueKudoAwardCount")
    @Expose
    private Integer totalDotValueKudoAwardCount;

    private Integer amazingValue;

    boolean isValue=false;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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

    public String getValueUrl() {
        return valueUrl;
    }

    public void setValueUrl(String valueUrl) {
        this.valueUrl = valueUrl;
    }

    public String getValueDesc() {
        return valueDesc;
    }

    public void setValueDesc(String valueDesc) {
        this.valueDesc = valueDesc;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public Integer getBeliefId() {
        return beliefId;
    }

    public void setBeliefId(Integer beliefId) {
        this.beliefId = beliefId;
    }

    public Integer getDotId() {
        return dotId;
    }

    public void setDotId(Integer dotId) {
        this.dotId = dotId;
    }

    public Integer getTodayKudosCount() {
        return todayKudosCount;
    }

    public void setTodayKudosCount(Integer todayKudosCount) {
        this.todayKudosCount = todayKudosCount;
    }

    public Integer getYesterdayKudosCount() {
        return yesterdayKudosCount;
    }

    public void setYesterdayKudosCount(Integer yesterdayKudosCount) {
        this.yesterdayKudosCount = yesterdayKudosCount;
    }

    public Integer getThisWeekKudosCount() {
        return thisWeekKudosCount;
    }

    public void setThisWeekKudosCount(Integer thisWeekKudosCount) {
        this.thisWeekKudosCount = thisWeekKudosCount;
    }

    public Integer getLastWeekKudosCount() {
        return lastWeekKudosCount;
    }

    public void setLastWeekKudosCount(Integer lastWeekKudosCount) {
        this.lastWeekKudosCount = lastWeekKudosCount;
    }

    public Integer getThisMonthKudosCount() {
        return thisMonthKudosCount;
    }

    public void setThisMonthKudosCount(Integer thisMonthKudosCount) {
        this.thisMonthKudosCount = thisMonthKudosCount;
    }

    public Integer getLastMonthKudosCount() {
        return lastMonthKudosCount;
    }

    public void setLastMonthKudosCount(Integer lastMonthKudosCount) {
        this.lastMonthKudosCount = lastMonthKudosCount;
    }

    public Integer getTotalKudosCount() {
        return totalKudosCount;
    }

    public void setTotalKudosCount(Integer totalKudosCount) {
        this.totalKudosCount = totalKudosCount;
    }

    public boolean isValue() {
        return isValue;
    }

    public void setValue(boolean value) {
        isValue = value;
    }


    public Integer getTodayDotValueKudoAwardCount() {
        return todayDotValueKudoAwardCount;
    }

    public void setTodayDotValueKudoAwardCount(Integer todayDotValueKudoAwardCount) {
        this.todayDotValueKudoAwardCount = todayDotValueKudoAwardCount;
    }

    public Integer getYesterdayDotValueKudoAwardCount() {
        return yesterdayDotValueKudoAwardCount;
    }

    public void setYesterdayDotValueKudoAwardCount(Integer yesterdayDotValueKudoAwardCount) {
        this.yesterdayDotValueKudoAwardCount = yesterdayDotValueKudoAwardCount;
    }

    public Integer getThisWeekDotValueKudoAwardCount() {
        return thisWeekDotValueKudoAwardCount;
    }

    public void setThisWeekDotValueKudoAwardCount(Integer thisWeekDotValueKudoAwardCount) {
        this.thisWeekDotValueKudoAwardCount = thisWeekDotValueKudoAwardCount;
    }

    public Integer getLastWeekDotValueKudoAwardCount() {
        return lastWeekDotValueKudoAwardCount;
    }

    public void setLastWeekDotValueKudoAwardCount(Integer lastWeekDotValueKudoAwardCount) {
        this.lastWeekDotValueKudoAwardCount = lastWeekDotValueKudoAwardCount;
    }

    public Integer getThisMonthDotValueKudoAwardCount() {
        return thisMonthDotValueKudoAwardCount;
    }

    public void setThisMonthDotValueKudoAwardCount(Integer thisMonthDotValueKudoAwardCount) {
        this.thisMonthDotValueKudoAwardCount = thisMonthDotValueKudoAwardCount;
    }

    public Integer getLastMonthDotValueKudoAwardCount() {
        return lastMonthDotValueKudoAwardCount;
    }

    public void setLastMonthDotValueKudoAwardCount(Integer lastMonthDotValueKudoAwardCount) {
        this.lastMonthDotValueKudoAwardCount = lastMonthDotValueKudoAwardCount;
    }

    public Integer getTotalDotValueKudoAwardCount() {
        return totalDotValueKudoAwardCount;
    }

    public void setTotalDotValueKudoAwardCount(Integer totalDotValueKudoAwardCount) {
        this.totalDotValueKudoAwardCount = totalDotValueKudoAwardCount;
    }

    public Integer getAmazingValue() {
        return amazingValue;
    }

    public void setAmazingValue(Integer amazingValue) {
        this.amazingValue = amazingValue;
    }
}
