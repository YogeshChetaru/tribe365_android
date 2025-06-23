package com.chetaru.tribe365_new.API.Models.DiagnosticsBeans;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Option {

    boolean answerFlag = false;
    @SerializedName("optionId")
    private Long mOptionId;
    @SerializedName("optionName")
    private String mOptionName;

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

    public boolean isAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(boolean answerFlag) {
        this.answerFlag = answerFlag;
    }
}
