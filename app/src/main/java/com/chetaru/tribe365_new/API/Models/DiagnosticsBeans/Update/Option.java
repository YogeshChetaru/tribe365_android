package com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.Update;

import com.google.gson.annotations.SerializedName;


public class Option {

    @SerializedName("isChecked")
    private Boolean mIsChecked;
    @SerializedName("optionId")
    private Long mOptionId;
    @SerializedName("answerId")
    private Long mAnswerId;
    @SerializedName("optionName")
    private String mOptionName;

    public Boolean getIsChecked() {
        return mIsChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        mIsChecked = isChecked;
    }

    public Long getOptionId() {
        return mOptionId;
    }

    public void setOptionId(Long optionId) {
        mOptionId = optionId;
    }

    public Long getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(Long answerId) {
        mAnswerId = answerId;
    }

    public String getOptionName() {
        return mOptionName;
    }

    public void setOptionName(String optionName) {
        mOptionName = optionName;
    }

}
