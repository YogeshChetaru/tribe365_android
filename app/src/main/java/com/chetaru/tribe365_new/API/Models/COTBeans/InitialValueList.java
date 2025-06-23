package com.chetaru.tribe365_new.API.Models.COTBeans;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class InitialValueList {

    @SerializedName("allowableWeaknesses")
    private String mAllowableWeaknesses;
    @SerializedName("positives")
    private String mPositives;
    @SerializedName("score")
    private Long mScore;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("value")
    private String mValue;

    public String getAllowableWeaknesses() {
        return mAllowableWeaknesses;
    }

    public void setAllowableWeaknesses(String allowableWeaknesses) {
        mAllowableWeaknesses = allowableWeaknesses;
    }

    public String getPositives() {
        return mPositives;
    }

    public void setPositives(String positives) {
        mPositives = positives;
    }

    public Long getScore() {
        return mScore;
    }

    public void setScore(Long score) {
        mScore = score;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

}
