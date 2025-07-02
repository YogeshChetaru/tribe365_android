package com.chetaru.tribe365_new.API.Models.COTBeans.UpdateBean.UpdateFunctionBean;


import com.google.gson.annotations.SerializedName;


public class Option {

    @SerializedName("answerId")
    private Long mAnswerId;
    @SerializedName("isChecked")
    private Boolean mIsChecked;
    @SerializedName("optionId")
    private Long mOptionId;
    @SerializedName("optionName")
    private String mOptionName;

    public Long getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(Long answerId) {
        mAnswerId = answerId;
    }

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

    public String getOptionName() {
        return mOptionName;
    }

    public void setOptionName(String optionName) {
        mOptionName = optionName;
    }

}
