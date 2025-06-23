package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class ModelCOTQuestion {

    boolean flag = false;
    @SerializedName("option")
    private List<Option> mOption;
    @SerializedName("questionId")
    private Long mQuestionId;
    @SerializedName("questionName")
    private String mQuestionName;

    public List<Option> getOption() {
        return mOption;
    }

    public void setOption(List<Option> option) {
        mOption = option;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
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

}
