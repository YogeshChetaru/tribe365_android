package com.chetaru.tribe365_new.API.Models.SOTBeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSOTQuesListResponse {
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("option")
    @Expose
    private List<ModelSOTOPTIONLIST> option = null;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ModelSOTOPTIONLIST> getOption() {
        return option;
    }

    public void setOption(List<ModelSOTOPTIONLIST> option) {
        this.option = option;
    }
}
