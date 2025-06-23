package com.chetaru.tribe365_new.API.Models.COTBeans;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelFunctionQues {
    boolean flag = false;
    @SerializedName("optionId")
    private String mOptionId = "";
    @SerializedName("options")
    private List<Option> mOptions;
    @SerializedName("questionId")
    private Long mQuestionId;
    @SerializedName("questionName")
    private String mQuestionName;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Option> getOptions() {
        return mOptions;
    }

    public void setOptions(List<Option> options) {
        mOptions = options;
    }

    public Long getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(Long questionId) {
        mQuestionId = questionId;
    }

    public String getQuestionName() {
        return mQuestionName;
    }

    public void setQuestionName(String questionName) {
        mQuestionName = questionName;
    }


    public String getOptionId() {
        return mOptionId;
    }

    public void setOptionId(String optionId) {
        mOptionId = optionId;
    }

}
