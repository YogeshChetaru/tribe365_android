package com.chetaru.tribe365_new.API.Models.COTBeans.UpdateBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelCOTTeamUpdate {

    @SerializedName("answerId")
    private Long mAnswerId;
    @SerializedName("options")
    private List<Option> mOptions;
    @SerializedName("question")
    private String mQuestion;
    @SerializedName("questionId")
    private Long mQuestionId;

    public Long getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(Long answerId) {
        mAnswerId = answerId;
    }

    public List<Option> getOptions() {
        return mOptions;
    }

    public void setOptions(List<Option> options) {
        mOptions = options;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public Long getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(Long questionId) {
        mQuestionId = questionId;
    }

}
