package com.chetaru.tribe365_new.API.Models.COTBeans;


import com.google.gson.annotations.SerializedName;


public class ModelCOTTEAMNEW {

    @SerializedName("completerFinisher")
    private Long mCompleterFinisher;
    @SerializedName("coordinator")
    private Long mCoordinator;
    @SerializedName("implementer")
    private Long mImplementer;
    @SerializedName("mapersArray")
    private MapersArray mMapersArray;
    @SerializedName("monitorEvaluator")
    private Long mMonitorEvaluator;
    @SerializedName("plant")
    private Long mPlant;
    @SerializedName("resourceInvestigator")
    private Long mResourceInvestigator;
    @SerializedName("shaper")
    private Long mShaper;
    @SerializedName("teamworker")
    private Long mTeamworker;
    @SerializedName("totalKeyCount")
    private TotalKeyCount mTotalKeyCount;
    @SerializedName("userId")
    private Long mUserId;
    @SerializedName("userName")
    private String mUserName;

    public Long getCompleterFinisher() {
        return mCompleterFinisher;
    }

    public void setCompleterFinisher(Long completerFinisher) {
        mCompleterFinisher = completerFinisher;
    }

    public Long getCoordinator() {
        return mCoordinator;
    }

    public void setCoordinator(Long coordinator) {
        mCoordinator = coordinator;
    }

    public Long getImplementer() {
        return mImplementer;
    }

    public void setImplementer(Long implementer) {
        mImplementer = implementer;
    }

    public MapersArray getMapersArray() {
        return mMapersArray;
    }

    public void setMapersArray(MapersArray mapersArray) {
        mMapersArray = mapersArray;
    }

    public Long getMonitorEvaluator() {
        return mMonitorEvaluator;
    }

    public void setMonitorEvaluator(Long monitorEvaluator) {
        mMonitorEvaluator = monitorEvaluator;
    }

    public Long getPlant() {
        return mPlant;
    }

    public void setPlant(Long plant) {
        mPlant = plant;
    }

    public Long getResourceInvestigator() {
        return mResourceInvestigator;
    }

    public void setResourceInvestigator(Long resourceInvestigator) {
        mResourceInvestigator = resourceInvestigator;
    }

    public Long getShaper() {
        return mShaper;
    }

    public void setShaper(Long shaper) {
        mShaper = shaper;
    }

    public Long getTeamworker() {
        return mTeamworker;
    }

    public void setTeamworker(Long teamworker) {
        mTeamworker = teamworker;
    }

    public TotalKeyCount getTotalKeyCount() {
        return mTotalKeyCount;
    }

    public void setTotalKeyCount(TotalKeyCount totalKeyCount) {
        mTotalKeyCount = totalKeyCount;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

}
