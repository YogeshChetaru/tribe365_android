package com.chetaru.tribe365_new.API.Models.MemberHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KnowMemberDetails {
    @SerializedName("personaliseData")
    @Expose
    private Boolean personaliseData;
    @SerializedName("engagementIndexScore")
    @Expose
    private String engagementIndexScore;
    @SerializedName("kudosCount")
    @Expose
    private List<KudosCount> kudosCount = null;
    @SerializedName("personalityType")
    @Expose
    private List<PersonalityType> personalityType = null;
    @SerializedName("teamRole")
    @Expose
    private List<String> teamRole = null;
    @SerializedName("motivation")
    @Expose
    private List<String> motivation = null;
    @SerializedName("perTypeDetails")
    @Expose
    private String perTypeDetails;
    @SerializedName("teamRoleDetails")
    @Expose
    private String teamRoleDetails;
    @SerializedName("motivationDetails")
    @Expose
    private String motivationDetails;

    public Boolean getPersonaliseData() {
        return personaliseData;
    }

    public void setPersonaliseData(Boolean personaliseData) {
        this.personaliseData = personaliseData;
    }

    public String getEngagementIndexScore() {
        return engagementIndexScore;
    }

    public void setEngagementIndexScore(String engagementIndexScore) {
        this.engagementIndexScore = engagementIndexScore;
    }

    public List<KudosCount> getKudosCount() {
        return kudosCount;
    }

    public void setKudosCount(List<KudosCount> kudosCount) {
        this.kudosCount = kudosCount;
    }

    public List<PersonalityType> getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(List<PersonalityType> personalityType) {
        this.personalityType = personalityType;
    }

    public List<String> getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(List<String> teamRole) {
        this.teamRole = teamRole;
    }

    public List<String> getMotivation() {
        return motivation;
    }

    public void setMotivation(List<String> motivation) {
        this.motivation = motivation;
    }

    public String getPerTypeDetails() {
        return perTypeDetails;
    }

    public void setPerTypeDetails(String perTypeDetails) {
        this.perTypeDetails = perTypeDetails;
    }

    public String getTeamRoleDetails() {
        return teamRoleDetails;
    }

    public void setTeamRoleDetails(String teamRoleDetails) {
        this.teamRoleDetails = teamRoleDetails;
    }

    public String getMotivationDetails() {
        return motivationDetails;
    }

    public void setMotivationDetails(String motivationDetails) {
        this.motivationDetails = motivationDetails;
    }
}
