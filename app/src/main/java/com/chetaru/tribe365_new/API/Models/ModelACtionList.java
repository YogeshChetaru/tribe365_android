package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class ModelACtionList implements Serializable {


    @SerializedName("id")
    private String mId;
    @SerializedName("userId")
    private String mUserId;
    @SerializedName("tier")
    private String mTier;
    @SerializedName("tierId")
    private String mTierId;

    @SerializedName("responsibleName")
    private String mResponsibleName;
    @SerializedName("responsibleUserId")
    private String mResponsibleUserId;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("startedDate")
    private String mStartedDate;
    @SerializedName("dueDate")
    private String mDueDate;
    @SerializedName("orgStatus")
    private String mOrgStatus;
    @SerializedName("orgId")
    private String mOrgId;
    @SerializedName("offDeptName")
    private String mOffDeptName;
    @SerializedName("offDeptId")
    private String mOffDeptId;
    @SerializedName("name")
    private String mName;
    @SerializedName("themes")
    private List<ModelTheme> mThemes;

    @SerializedName("linkedActionOffloads")
    private Integer linkedActionOffloads;

    private String currentStatus = "";

    public List<ModelTheme> getmThemes() {
        return mThemes;
    }

    public void setmThemes(List<ModelTheme> mThemes) {
        this.mThemes = mThemes;
    }

    public String getTierId() {
        return mTierId;
    }

    public void setTierId(String mTierId) {
        this.mTierId = mTierId;
    }

    public String getOffDeptId() {
        return mOffDeptId;
    }

    public void setOffDeptId(String mOffDeptId) {
        this.mOffDeptId = mOffDeptId;
    }

    public String getResponsibleUserId() {
        return mResponsibleUserId;
    }

    public void setResponsibleUserId(String mResponsibleUserId) {
        this.mResponsibleUserId = mResponsibleUserId;
    }

    public String getOffDeptName() {
        return mOffDeptName;
    }

    public void setOffDeptName(String mOffDeptName) {
        this.mOffDeptName = mOffDeptName;
    }

    public String getTier() {
        return mTier;
    }

    public void setTier(String mTier) {
        this.mTier = mTier;
    }

    public String getResponsibleName() {
        return mResponsibleName;
    }

    public void setResponsibleName(String mResponsibleName) {
        this.mResponsibleName = mResponsibleName;
    }


    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }


    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDueDate() {
        return mDueDate;
    }

    public void setDueDate(String dueDate) {
        mDueDate = dueDate;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOrgId() {
        return mOrgId;
    }

    public void setOrgId(String orgId) {
        mOrgId = orgId;
    }

    public String getOrgStatus() {
        return mOrgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        mOrgStatus = orgStatus;
    }

    public String getStartedDate() {
        return mStartedDate;
    }

    public void setStartedDate(String startedDate) {
        mStartedDate = startedDate;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public Integer getLinkedActionOffloads() {
        return linkedActionOffloads;
    }

    public void setLinkedActionOffloads(Integer linkedActionOffloads) {
        this.linkedActionOffloads = linkedActionOffloads;
    }
}
