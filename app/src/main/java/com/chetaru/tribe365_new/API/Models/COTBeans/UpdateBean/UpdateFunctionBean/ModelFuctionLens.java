package com.chetaru.tribe365_new.API.Models.COTBeans.UpdateBean.UpdateFunctionBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelFuctionLens {

    @SerializedName("optionId")
    private String mOptionId = "";
    @SerializedName("options")
    private List<Option> mOptions;
    @SerializedName("questionId")
    private Long mQuestionId;
    @SerializedName("question")
    private String mQuestion;
    @SerializedName("answerId")
    private Long mAnswerId;

    public List<Option> getOptions() {
        return mOptions;
    }

    public void setOptions(List<Option> options) {
        mOptions = options;
    }

    public Long getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(Long answerId) {
        mAnswerId = answerId;
    }

    public Long getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(Long questionId) {
        mQuestionId = questionId;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }


    public String getOptionId() {
        return mOptionId;
    }

    public void setOptionId(String optionId) {
        mOptionId = optionId;
    }


}
