package com.chetaru.tribe365_new.API.Models.basicVersion;

import com.chetaru.tribe365_new.API.Models.Home.LatestKudoAward;
import com.chetaru.tribe365_new.API.Models.MemberHome.LatestKudosAward;
import com.chetaru.tribe365_new.API.Models.freeVersion.HappyIndexMonthly;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicHomeDetail {

    @SerializedName("userGivenfeedback")
    @Expose
    private  boolean userGivenfeedback;
    @SerializedName("todayEIScore")
    @Expose
    private String todayEIScore;

    @SerializedName("appPaymentVersion")
    @Expose
    private Integer appPaymentVersion;

    @SerializedName("badDayOffload")
    @Expose
    private Boolean badDayOffload;
    @SerializedName("kudoAwardKey")
    @Expose
    private String kudoAwardKey;
    @SerializedName("kudoAwardValue")
    @Expose
    private Integer kudoAwardValue;
    @SerializedName("latestKudoAward")
    @Expose
    public LatestKudoAward latestKudosAward;

    @SerializedName("latestKudosAward")
    @Expose
    public List<LatestKudoAward> latestKAward = null;

    @SerializedName("topKudoAwardArray")
    @Expose
    public List<LatestKudosAward> topKudosAward= null;

    @SerializedName("firstDayOfMonth")
    @Expose
    private String firstDayOfMonth;
    @SerializedName("leaveStatus")
    @Expose
    private Integer leaveStatus;

    @SerializedName("happyIndexMonthly")
    @Expose
    private List<HappyIndexMonthly> happyIndexMonthly = null;

    @SerializedName("notWorkingDays")
    @Expose
    private List<String> notWorkingDays = null;

    @SerializedName("notificationPush")
    @Expose
    public Integer notificationPush;

    @SerializedName("deviceNotificationPush")
    @Expose
    public Integer deviceNotificationPush;

    public boolean isUserGivenfeedback() {
        return userGivenfeedback;
    }

    public void setUserGivenfeedback(boolean userGivenfeedback) {
        this.userGivenfeedback = userGivenfeedback;
    }

    public String getTodayEIScore() {
        return todayEIScore;
    }

    public void setTodayEIScore(String todayEIScore) {
        this.todayEIScore = todayEIScore;
    }

    public Integer getAppPaymentVersion() {
        return appPaymentVersion;
    }

    public void setAppPaymentVersion(Integer appPaymentVersion) {
        this.appPaymentVersion = appPaymentVersion;
    }

    public Boolean getBadDayOffload() {
        return badDayOffload;
    }

    public void setBadDayOffload(Boolean badDayOffload) {
        this.badDayOffload = badDayOffload;
    }

    public String getKudoAwardKey() {
        return kudoAwardKey;
    }

    public void setKudoAwardKey(String kudoAwardKey) {
        this.kudoAwardKey = kudoAwardKey;
    }

    public Integer getKudoAwardValue() {
        return kudoAwardValue;
    }

    public void setKudoAwardValue(Integer kudoAwardValue) {
        this.kudoAwardValue = kudoAwardValue;
    }

    public LatestKudoAward getLatestKudosAward() {
        return latestKudosAward;
    }

    public void setLatestKudosAward(LatestKudoAward latestKudosAward) {
        this.latestKudosAward = latestKudosAward;
    }

    public List<LatestKudoAward> getLatestKAward() {
        return latestKAward;
    }

    public void setLatestKAward(List<LatestKudoAward> latestKAward) {
        this.latestKAward = latestKAward;
    }

    public List<LatestKudosAward> getTopKudosAward() {
        return topKudosAward;
    }

    public void setTopKudosAward(List<LatestKudosAward> topKudosAward) {
        this.topKudosAward = topKudosAward;
    }

    public String getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    public void setFirstDayOfMonth(String firstDayOfMonth) {
        this.firstDayOfMonth = firstDayOfMonth;
    }

    public List<HappyIndexMonthly> getHappyIndexMonthly() {
        return happyIndexMonthly;
    }

    public void setHappyIndexMonthly(List<HappyIndexMonthly> happyIndexMonthly) {
        this.happyIndexMonthly = happyIndexMonthly;
    }

    public Integer getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(Integer leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public List<String> getNotWorkingDays() {
        return notWorkingDays;
    }

    public void setNotWorkingDays(List<String> notWorkingDays) {
        this.notWorkingDays = notWorkingDays;
    }

    public Integer getNotificationPush() {
        return notificationPush;
    }

    public void setNotificationPush(Integer notificationPush) {
        this.notificationPush = notificationPush;
    }

    public Integer getDeviceNotificationPush() {
        return deviceNotificationPush;
    }

    public void setDeviceNotificationPush(Integer deviceNotificationPush) {
        this.deviceNotificationPush = deviceNotificationPush;
    }
}
