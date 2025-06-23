package com.chetaru.tribe365_new.API.Models.DiagnosticsBeans;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelTribeometerQusList {

    boolean flag = false;
    @SerializedName("options")
    private List<Option> mOptions;
    @SerializedName("question")
    private String mQuestion;
    @SerializedName("answer")
    private String mAnswer = "";
    @SerializedName("questionId")
    private Long mQuestionId;

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
}
