package com.chetaru.tribe365_new.API.Models.freeVersion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FreeVersionHome {
    @SerializedName("userGivenfeedback")
    @Expose
    private Boolean userGivenfeedback;
    @SerializedName("happyIndexMonthly")
    @Expose
    private List<HappyIndexMonthly> happyIndexMonthly = null;
    @SerializedName("firstDayOfMonth")
    @Expose
    private String firstDayOfMonth;
    @SerializedName("orgYearList")
    @Expose
    private List<Integer> orgYearList = null;
    @SerializedName("notWorkingDays")
    @Expose
    private List<String> notWorkingDays = null;


    @SerializedName("appPaymentVersion")
    @Expose
    private Integer appPaymentVersion;

    @SerializedName("leaveStatus")
    @Expose
    private Integer leaveStatus;

    public Boolean getUserGivenfeedback() {
        return userGivenfeedback;
    }

    public void setUserGivenfeedback(Boolean userGivenfeedback) {
        this.userGivenfeedback = userGivenfeedback;
    }

    public List<HappyIndexMonthly> getHappyIndexMonthly() {
        return happyIndexMonthly;
    }

    public void setHappyIndexMonthly(List<HappyIndexMonthly> happyIndexMonthly) {
        this.happyIndexMonthly = happyIndexMonthly;
    }

    public String getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    public void setFirstDayOfMonth(String firstDayOfMonth) {
        this.firstDayOfMonth = firstDayOfMonth;
    }

    public List<Integer> getOrgYearList() {
        return orgYearList;
    }

    public void setOrgYearList(List<Integer> orgYearList) {
        this.orgYearList = orgYearList;
    }

    public List<String> getNotWorkingDays() {
        return notWorkingDays;
    }

    public void setNotWorkingDays(List<String> notWorkingDays) {
        this.notWorkingDays = notWorkingDays;
    }

    public Integer getAppPaymentVersion() {
        return appPaymentVersion;
    }

    public void setAppPaymentVersion(Integer appPaymentVersion) {
        this.appPaymentVersion = appPaymentVersion;
    }

    public Integer getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(Integer leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
}
