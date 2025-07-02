package com.chetaru.tribe365_new.API.Models.KnowCompany;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GetCOTpersonalityType {

    @SerializedName("nf")
    private Long mNf;
    @SerializedName("nfValue")
    private String mNfValue;
    @SerializedName("nt")
    private Long mNt;
    @SerializedName("ntValue")
    private String mNtValue;
    @SerializedName("sf")
    private Long mSf;
    @SerializedName("sfValue")
    private String mSfValue;
    @SerializedName("st")
    private Long mSt;
    @SerializedName("stValue")
    private String mStValue;

    public Long getNf() {
        return mNf;
    }

    public void setNf(Long nf) {
        mNf = nf;
    }

    public String getNfValue() {
        return mNfValue;
    }

    public void setNfValue(String nfValue) {
        mNfValue = nfValue;
    }

    public Long getNt() {
        return mNt;
    }

    public void setNt(Long nt) {
        mNt = nt;
    }

    public String getNtValue() {
        return mNtValue;
    }

    public void setNtValue(String ntValue) {
        mNtValue = ntValue;
    }

    public Long getSf() {
        return mSf;
    }

    public void setSf(Long sf) {
        mSf = sf;
    }

    public String getSfValue() {
        return mSfValue;
    }

    public void setSfValue(String sfValue) {
        mSfValue = sfValue;
    }

    public Long getSt() {
        return mSt;
    }

    public void setSt(Long st) {
        mSt = st;
    }

    public String getStValue() {
        return mStValue;
    }

    public void setStValue(String stValue) {
        mStValue = stValue;
    }

}
