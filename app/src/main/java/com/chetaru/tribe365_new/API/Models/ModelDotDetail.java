package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class ModelDotDetail {

    @SerializedName("id")
    @Expose
    private Long mId;

    @SerializedName("vision")
    private String mVision;

    @SerializedName("mission")
    private String mMission;

    @SerializedName("focus")
    @Expose
    private String mFocus;

    @SerializedName("orgId")
    private long mOrgId;

    @SerializedName("belief")
    @Expose
    private List<Belief> mBelief;

    //new Add Data
    @SerializedName("visionUrl")
    @Expose
    private String visionUrl;
    @SerializedName("visionDesc")
    @Expose
    private String visionDesc;

    @SerializedName("missionUrl")
    @Expose
    private String missionUrl;
    @SerializedName("missionDesc")
    @Expose
    private String missionDesc;

    @SerializedName("focusUrl")
    @Expose
    private String focusUrl;
    @SerializedName("focusDesc")
    @Expose
    private String focusDesc;


    public long getOrgId() {
        return mOrgId;
    }

    public void setOrgId(long orgId) {
        mOrgId = orgId;
    }

    public List<Belief> getBelief() {
        return mBelief;
    }

    public void setBelief(List<Belief> belief) {
        mBelief = belief;
    }

    public String getFocus() {
        return mFocus;
    }

    public void setFocus(String focus) {
        mFocus = focus;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getMission() {
        return mMission;
    }

    public void setMission(String mission) {
        mMission = mission;
    }


    public String getVision() {
        return mVision;
    }

    public void setVision(String vision) {
        mVision = vision;
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

    public String getMissionUrl() {
        return missionUrl;
    }

    public void setMissionUrl(String missionUrl) {
        this.missionUrl = missionUrl;
    }

    public String getMissionDesc() {
        return missionDesc;
    }

    public void setMissionDesc(String missionDesc) {
        this.missionDesc = missionDesc;
    }

    public String getFocusUrl() {
        return focusUrl;
    }

    public void setFocusUrl(String focusUrl) {
        this.focusUrl = focusUrl;
    }

    public String getFocusDesc() {
        return focusDesc;
    }

    public void setFocusDesc(String focusDesc) {
        this.focusDesc = focusDesc;
    }
}
