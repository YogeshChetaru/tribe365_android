package com.chetaru.tribe365_new.API.Models.Risk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActionList {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("tier")
    @Expose
    private String tier;
    @SerializedName("tierId")
    @Expose
    private Integer tierId;
    @SerializedName("responsibleName")
    @Expose
    private String responsibleName;
    @SerializedName("responsibleUserId")
    @Expose
    private Integer responsibleUserId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("startedDate")
    @Expose
    private String startedDate;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("orgStatus")
    @Expose
    private String orgStatus;
    @SerializedName("orgId")
    @Expose
    private Integer orgId;
    @SerializedName("offDeptName")
    @Expose
    private String offDeptName;
    @SerializedName("offDeptId")
    @Expose
    private String offDeptId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("themes")
    @Expose
    private List<RiskTheme> themes = null;
    @SerializedName("linkedActionOffloads")
    @Expose
    private Integer linkedActionOffloads;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Integer getTierId() {
        return tierId;
    }

    public void setTierId(Integer tierId) {
        this.tierId = tierId;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public Integer getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(Integer responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOffDeptName() {
        return offDeptName;
    }

    public void setOffDeptName(String offDeptName) {
        this.offDeptName = offDeptName;
    }

    public String getOffDeptId() {
        return offDeptId;
    }

    public void setOffDeptId(String offDeptId) {
        this.offDeptId = offDeptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RiskTheme> getThemes() {
        return themes;
    }

    public void setThemes(List<RiskTheme> themes) {
        this.themes = themes;
    }

    public Integer getLinkedActionOffloads() {
        return linkedActionOffloads;
    }

    public void setLinkedActionOffloads(Integer linkedActionOffloads) {
        this.linkedActionOffloads = linkedActionOffloads;
    }
}
