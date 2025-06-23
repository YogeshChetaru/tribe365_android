package com.chetaru.tribe365_new.API.Models.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeDetailResponse {
    @SerializedName("dotId")
    @Expose
    private String dotId;
    @SerializedName("vision")
    @Expose
    private String vision;
    @SerializedName("visionUrl")
    @Expose
    private String visionUrl;
    @SerializedName("visionDesc")
    @Expose
    private String visionDesc;
    @SerializedName("userGivenfeedback")
    @Expose
    private  boolean userGivenfeedback;
    @SerializedName("todayEIScore")
    @Expose
    private String todayEIScore;
    @SerializedName("belief")
    @Expose
    private List<HomeBelief> belief = null;

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
    @SerializedName("leaveStatus")
    @Expose
    private Integer leaveStatus;

    @SerializedName("latestKudosAward")
    @Expose
    public List<LatestKudoAward> latestKAward = null;

    @SerializedName("notificationPush")
    @Expose
    public Integer notificationPush;

    @SerializedName("deviceNotificationPush")
    @Expose
    public Integer deviceNotificationPush;

    public String getDotId() {
        return dotId;
    }

    public void setDotId(String dotId) {
        this.dotId = dotId;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getVisionUrl() {
        return visionUrl;
    }

    public void setVisionUrl(String visionUrl) {
        this.visionUrl = visionUrl;
    }

    public String getVisionDesc() {
        return visionDesc;
    }

    public void setVisionDesc(String visionDesc) {
        this.visionDesc = visionDesc;
    }

    public String getTodayEIScore() {
        return todayEIScore;
    }

    public void setTodayEIScore(String todayEIScore) {
        this.todayEIScore = todayEIScore;
    }

    public List<HomeBelief> getBelief() {
        return belief;
    }

    public void setBelief(List<HomeBelief> belief) {
        this.belief = belief;
    }

    public boolean isUserGivenfeedback() {
        return userGivenfeedback;
    }

    public void setUserGivenfeedback(boolean userGivenfeedback) {
        this.userGivenfeedback = userGivenfeedback;
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

    public Integer getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(Integer leaveStatus) {
        this.leaveStatus = leaveStatus;
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
