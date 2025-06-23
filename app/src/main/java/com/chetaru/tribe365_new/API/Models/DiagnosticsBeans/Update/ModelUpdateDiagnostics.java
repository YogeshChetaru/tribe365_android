package com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.Update;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelUpdateDiagnostics {

    @SerializedName("options")
    private List<Option> mOptions;
    @SerializedName("question")
    private String mQuestion;
    @SerializedName("questionId")
    private Long mQuestionId;
    @SerializedName("answer")
    private String mAnswer = "";
    @SerializedName("answerId")
    private Long mAnswerId;

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

    public String getmAnswer() {
        return mAnswer;
    }

    public void setmAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }

    public Long getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(Long answerId) {
        mAnswerId = answerId;
    }
}
