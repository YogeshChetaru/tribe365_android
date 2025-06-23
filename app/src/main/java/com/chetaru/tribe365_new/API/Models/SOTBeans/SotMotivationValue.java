package com.chetaru.tribe365_new.API.Models.SOTBeans;


import com.google.gson.annotations.SerializedName;


public class SotMotivationValue {

    @SerializedName("autonomyVarietyScore")
    private Long mAutonomyVarietyScore;
    @SerializedName("avoidWorkingAloneScore")
    private Long mAvoidWorkingAloneScore;
    @SerializedName("identifyWithTeamScore")
    private Long mIdentifyWithTeamScore;
    @SerializedName("jobStructureScore")
    private Long mJobStructureScore;
    @SerializedName("moneyScore")
    private Long mMoneyScore;
    @SerializedName("motId")
    private Long mMotId;
    @SerializedName("personalGrowthScore")
    private Long mPersonalGrowthScore;
    @SerializedName("powerScore")
    private Long mPowerScore;
    @SerializedName("recognitionScore")
    private Long mRecognitionScore;
    @SerializedName("riskAvoidanceScore")
    private Long mRiskAvoidanceScore;
    @SerializedName("stressAvoidScore")
    private Long mStressAvoidScore;

    public Long getAutonomyVarietyScore() {
        return mAutonomyVarietyScore;
    }

    public void setAutonomyVarietyScore(Long autonomyVarietyScore) {
        mAutonomyVarietyScore = autonomyVarietyScore;
    }

    public Long getAvoidWorkingAloneScore() {
        return mAvoidWorkingAloneScore;
    }

    public void setAvoidWorkingAloneScore(Long avoidWorkingAloneScore) {
        mAvoidWorkingAloneScore = avoidWorkingAloneScore;
    }

    public Long getIdentifyWithTeamScore() {
        return mIdentifyWithTeamScore;
    }

    public void setIdentifyWithTeamScore(Long identifyWithTeamScore) {
        mIdentifyWithTeamScore = identifyWithTeamScore;
    }

    public Long getJobStructureScore() {
        return mJobStructureScore;
    }

    public void setJobStructureScore(Long jobStructureScore) {
        mJobStructureScore = jobStructureScore;
    }

    public Long getMoneyScore() {
        return mMoneyScore;
    }

    public void setMoneyScore(Long moneyScore) {
        mMoneyScore = moneyScore;
    }

    public Long getMotId() {
        return mMotId;
    }

    public void setMotId(Long motId) {
        mMotId = motId;
    }

    public Long getPersonalGrowthScore() {
        return mPersonalGrowthScore;
    }

    public void setPersonalGrowthScore(Long personalGrowthScore) {
        mPersonalGrowthScore = personalGrowthScore;
    }

    public Long getPowerScore() {
        return mPowerScore;
    }

    public void setPowerScore(Long powerScore) {
        mPowerScore = powerScore;
    }

    public Long getRecognitionScore() {
        return mRecognitionScore;
    }

    public void setRecognitionScore(Long recognitionScore) {
        mRecognitionScore = recognitionScore;
    }

    public Long getRiskAvoidanceScore() {
        return mRiskAvoidanceScore;
    }

    public void setRiskAvoidanceScore(Long riskAvoidanceScore) {
        mRiskAvoidanceScore = riskAvoidanceScore;
    }

    public Long getStressAvoidScore() {
        return mStressAvoidScore;
    }

    public void setStressAvoidScore(Long stressAvoidScore) {
        mStressAvoidScore = stressAvoidScore;
    }

}
