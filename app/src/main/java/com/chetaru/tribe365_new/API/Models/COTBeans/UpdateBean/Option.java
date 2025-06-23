package com.chetaru.tribe365_new.API.Models.COTBeans.UpdateBean;


import com.google.gson.annotations.SerializedName;


public class Option {

    String oldValue = "";
    String newValue = "";
    boolean flag = false;
    @SerializedName("answerId")
    private Long mAnswerId;
    @SerializedName("optionId")
    private Long mOptionId;
    @SerializedName("optionName")
    private String mOptionName;
    @SerializedName("point")
    private String mPoint;
    @SerializedName("answer")
    private String mAnswer = "";
    @SerializedName("alphabate")
    private String mAlphabate = "";

    public Option(String mOptionName, Long mOptionId, String mPoint, String mAlphabate) {
        this.mOptionName = mOptionName;
        this.mOptionId = mOptionId;
        this.mPoint = mPoint;
        this.mAlphabate = mAlphabate;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }


    public Long getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(Long answerId) {
        mAnswerId = answerId;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public Long getOptionId() {
        return mOptionId;
    }

    public void setOptionId(Long optionId) {
        mOptionId = optionId;
    }

    public String getOptionName() {
        return mOptionName;
    }

    public void setOptionName(String optionName) {
        mOptionName = optionName;
    }

    public String getPoint() {
        return mPoint;
    }

    public void setPoint(String point) {
        mPoint = point;
    }

    public String getmAlphabate() {
        return mAlphabate;
    }

    public void setmAlphabate(String mAlphabate) {
        this.mAlphabate = mAlphabate;
    }
}
