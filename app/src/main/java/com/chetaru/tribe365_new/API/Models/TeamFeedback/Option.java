package com.chetaru.tribe365_new.API.Models.TeamFeedback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {
    boolean answerFlag = false;
    @SerializedName("optionId")
    @Expose
    private Integer optionId;
    @SerializedName("optionName")
    @Expose
    private String optionName;



    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public boolean isAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(boolean answerFlag) {
        this.answerFlag = answerFlag;
    }
}
