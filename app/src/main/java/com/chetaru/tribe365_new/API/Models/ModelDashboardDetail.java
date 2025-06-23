package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDashboardDetail {


    @SerializedName("vision")
    @Expose
    private String vision;
    @SerializedName("visionUrl")
    @Expose
    private String visionUrl;
    @SerializedName("visionDesc")
    @Expose
    private String visionDesc;

    @SerializedName("userGivenfeedback")
    @Expose
    private Boolean userGivenfeedback;
    @SerializedName("indexEngagement")
    @Expose
    private String indexEngagement;
    @SerializedName("happyIndex")
    @Expose
    private String happyIndex;
    @SerializedName("happyIndexDD")
    @Expose
    private String happyIndexDD;
    @SerializedName("happyIndexMA")
    @Expose
    private String happyIndexMA;


    @SerializedName("indexOrgDD")
    @Expose
    private String indexOrgDD;
    @SerializedName("indexOrgMA")
    @Expose
    private String indesOrgMA;
    @SerializedName("dotId")
    @Expose
    private Integer dotId;

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public Boolean getUserGivenfeedback() {
        return userGivenfeedback;
    }

    public void setUserGivenfeedback(Boolean userGivenfeedback) {
        this.userGivenfeedback = userGivenfeedback;
    }

    public String getIndexEngagement() {
        return indexEngagement;
    }

    public void setIndexEngagement(String indexEngagement) {
        this.indexEngagement = indexEngagement;
    }

    public String getHappyIndex() {
        return happyIndex;
    }

    public void setHappyIndex(String happyIndex) {
        this.happyIndex = happyIndex;
    }

    public String getHappyIndexDD() {
        return happyIndexDD;
    }

    public void setHappyIndexDD(String happyIndexDD) {
        this.happyIndexDD = happyIndexDD;
    }

    public String getHappyIndexMA() {
        return happyIndexMA;
    }

    public void setHappyIndexMA(String happyIndexMA) {
        this.happyIndexMA = happyIndexMA;
    }

    public Integer getDotId() {
        return dotId;
    }

    public void setDotId(Integer dotId) {
        this.dotId = dotId;
    }

    public String getIndexOrgDD() {
        return indexOrgDD;
    }

    public void setIndexOrgDD(String indexOrgDD) {
        this.indexOrgDD = indexOrgDD;
    }

    public String getIndesOrgMA() {
        return indesOrgMA;
    }

    public void setIndesOrgMA(String indesOrgMA) {
        this.indesOrgMA = indesOrgMA;
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

}
