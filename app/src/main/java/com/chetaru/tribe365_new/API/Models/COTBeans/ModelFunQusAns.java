package com.chetaru.tribe365_new.API.Models.COTBeans;


import com.google.gson.annotations.SerializedName;


public class ModelFunQusAns {

    @SerializedName("optionId")
    private String mOptionId;
    @SerializedName("questionId")
    private String mQuestionId;

    public ModelFunQusAns(String mOptionId, String mQuestionId) {
        this.mOptionId = mOptionId;
        this.mQuestionId = mQuestionId;
    }

    public String getOptionId() {
        return mOptionId;
    }

    public void setOptionId(String optionId) {
        mOptionId = optionId;
    }

    public String getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(String questionId) {
        mQuestionId = questionId;
    }
}
