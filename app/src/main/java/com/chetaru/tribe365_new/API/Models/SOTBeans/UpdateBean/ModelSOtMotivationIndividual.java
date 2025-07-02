package com.chetaru.tribe365_new.API.Models.SOTBeans.UpdateBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelSOtMotivationIndividual {

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
