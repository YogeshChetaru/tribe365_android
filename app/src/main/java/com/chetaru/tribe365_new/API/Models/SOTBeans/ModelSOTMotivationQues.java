package com.chetaru.tribe365_new.API.Models.SOTBeans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSOTMotivationQues {

    boolean falg = false;
    @SerializedName("option")
    private List<Option> mOption;
    @SerializedName("questionId")
    private Long mQuestionId;
    @SerializedName("questionName")
    private String mQuestionName;

    public boolean isFalg() {
        return falg;
    }

    public void setFalg(boolean falg) {
        this.falg = falg;
    }

    public List<Option> getOption() {
        return mOption;
    }

    public void setOption(List<Option> option) {
        mOption = option;
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
