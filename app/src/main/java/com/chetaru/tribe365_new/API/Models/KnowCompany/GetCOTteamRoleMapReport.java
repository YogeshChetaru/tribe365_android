package com.chetaru.tribe365_new.API.Models.KnowCompany;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GetCOTteamRoleMapReport {

    @SerializedName("completerFinisher")
    private Long mCompleterFinisher;
    @SerializedName("coordinator")
    private Long mCoordinator;
    @SerializedName("id")
    private Long mId;
    @SerializedName("implementer")
    private Long mImplementer;
    @SerializedName("mapersArray")
    private MapersArray mMapersArray;
    @SerializedName("monitorEvaluator")
    private Long mMonitorEvaluator;
    @SerializedName("name")
    private String mName;
    @SerializedName("orgId")
    private Long mOrgId;
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

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getOrgId() {
        return mOrgId;
    }

    public void setOrgId(Long orgId) {
        mOrgId = orgId;
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
