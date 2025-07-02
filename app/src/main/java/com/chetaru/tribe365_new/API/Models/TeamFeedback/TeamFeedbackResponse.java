package com.chetaru.tribe365_new.API.Models.TeamFeedback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamFeedbackResponse {
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("questionArr")
    @Expose
    private List<QuestionArr> questionArr = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<QuestionArr> getQuestionArr() {
        return questionArr;
    }

    public void setQuestionArr(List<QuestionArr> questionArr) {
        this.questionArr = questionArr;
    }
}
