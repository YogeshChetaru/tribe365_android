package com.chetaru.tribe365_new.API.Models.SOTBeans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSotans {

    @SerializedName("answer")
    private List<Answer> mAnswer;

    public ModelSotans(String id) {
    }

    public List<Answer> getAnswer() {
        return mAnswer;
    }

    public void setAnswer(List<Answer> answer) {
        mAnswer = answer;
    }
//
//    public ModelSotans(String mAnswer) {
//        this.mAnswer = mAnswer;
//    }
}
