package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.SerializedName;

public class TotalKeyCount {

    @SerializedName("completerFinisher")
    private Long mCompleterFinisher;
    @SerializedName("coordinator")
    private Long mCoordinator;
    @SerializedName("implementer")
    private Long mImplementer;
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

}
