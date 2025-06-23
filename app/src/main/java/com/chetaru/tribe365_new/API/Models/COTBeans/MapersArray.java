package com.chetaru.tribe365_new.API.Models.COTBeans;


import com.google.gson.annotations.SerializedName;


public class MapersArray {

    @SerializedName("completerFinisher")
    private String mCompleterFinisher;
    @SerializedName("coordinator")
    private String mCoordinator;
    @SerializedName("implementer")
    private String mImplementer;
    @SerializedName("monitorEvaluator")
    private String mMonitorEvaluator;
    @SerializedName("plant")
    private String mPlant;
    @SerializedName("resourceInvestigator")
    private String mResourceInvestigator;
    @SerializedName("shaper")
    private String mShaper;
    @SerializedName("teamworker")
    private String mTeamworker;

    public String getCompleterFinisher() {
        return mCompleterFinisher;
    }

    public void setCompleterFinisher(String completerFinisher) {
        mCompleterFinisher = completerFinisher;
    }

    public String getCoordinator() {
        return mCoordinator;
    }

    public void setCoordinator(String coordinator) {
        mCoordinator = coordinator;
    }

    public String getImplementer() {
        return mImplementer;
    }

    public void setImplementer(String implementer) {
        mImplementer = implementer;
    }

    public String getMonitorEvaluator() {
        return mMonitorEvaluator;
    }

    public void setMonitorEvaluator(String monitorEvaluator) {
        mMonitorEvaluator = monitorEvaluator;
    }

    public String getPlant() {
        return mPlant;
    }

    public void setPlant(String plant) {
        mPlant = plant;
    }

    public String getResourceInvestigator() {
        return mResourceInvestigator;
    }

    public void setResourceInvestigator(String resourceInvestigator) {
        mResourceInvestigator = resourceInvestigator;
    }

    public String getShaper() {
        return mShaper;
    }

    public void setShaper(String shaper) {
        mShaper = shaper;
    }

    public String getTeamworker() {
        return mTeamworker;
    }

    public void setTeamworker(String teamworker) {
        mTeamworker = teamworker;
    }

}
