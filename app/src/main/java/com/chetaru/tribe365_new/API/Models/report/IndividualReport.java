package com.chetaru.tribe365_new.API.Models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IndividualReport {

    @SerializedName("userHIPercent")
    @Expose
    private String userHIPercent;
    @SerializedName("teamHIPercent")
    @Expose
    private String teamHIPercent;
    @SerializedName("orgHIPercent")
    @Expose
    private String orgHIPercent;
    @SerializedName("orgDot")
    @Expose
    private List<BeliefCount> orgDot = null;
    @SerializedName("departDot")
    @Expose
    private List<BeliefCount> departDot = null;
    @SerializedName("userDot")
    @Expose
    private List<BeliefCount> userDot = null;

    @SerializedName("offloadCount")
    @Expose
    private Integer offloadCount;





    @SerializedName("orgEngagementIndex")
    @Expose
    private EngagementIndexCount orgEngagementIndex;
    @SerializedName("userEngagementIndex")
    @Expose
    private EngagementIndexCount userEngagementIndex;
    @SerializedName("departEngagementIndex")
    @Expose
    private EngagementIndexCount departEngagementIndex;



    public String getUserHIPercent() {
        return userHIPercent;
    }

    public void setUserHIPercent(String userHIPercent) {
        this.userHIPercent = userHIPercent;
    }

    public String getTeamHIPercent() {
        return teamHIPercent;
    }

    public void setTeamHIPercent(String teamHIPercent) {
        this.teamHIPercent = teamHIPercent;
    }

    public String getOrgHIPercent() {
        return orgHIPercent;
    }

    public void setOrgHIPercent(String orgHIPercent) {
        this.orgHIPercent = orgHIPercent;
    }

    public List<BeliefCount> getOrgDot() {
        return orgDot;
    }

    public void setOrgDot(List<BeliefCount> orgDot) {
        this.orgDot = orgDot;
    }

    public List<BeliefCount> getDepartDot() {
        return departDot;
    }

    public void setDepartDot(List<BeliefCount> departDot) {
        this.departDot = departDot;
    }

    public List<BeliefCount> getUserDot() {
        return userDot;
    }

    public void setUserDot(List<BeliefCount> userDot) {
        this.userDot = userDot;
    }


    public Integer getOffloadCount() {
        return offloadCount;
    }

    public void setOffloadCount(Integer offloadCount) {
        this.offloadCount = offloadCount;
    }


    public EngagementIndexCount getOrgEngagementIndex() {
        return orgEngagementIndex;
    }

    public void setOrgEngagementIndex(EngagementIndexCount orgEngagementIndex) {
        this.orgEngagementIndex = orgEngagementIndex;
    }

    public EngagementIndexCount getUserEngagementIndex() {
        return userEngagementIndex;
    }

    public void setUserEngagementIndex(EngagementIndexCount userEngagementIndex) {
        this.userEngagementIndex = userEngagementIndex;
    }

    public EngagementIndexCount getDepartEngagementIndex() {
        return departEngagementIndex;
    }

    public void setDepartEngagementIndex(EngagementIndexCount departEngagementIndex) {
        this.departEngagementIndex = departEngagementIndex;
    }
}
