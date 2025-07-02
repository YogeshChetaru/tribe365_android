package com.chetaru.tribe365_new.API.Models.HPTM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HPTM_Principle {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("teamFeedbackScorePercent")
    @Expose
    private Double teamFeedbackScorePercent;
    @SerializedName("completionPercent")
    @Expose
    private Double completionPercent;
    @SerializedName("hptmScore")
    @Expose
    private Integer hptmScore;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTeamFeedbackScorePercent() {
        return teamFeedbackScorePercent;
    }

    public void setTeamFeedbackScorePercent(Double teamFeedbackScorePercent) {
        this.teamFeedbackScorePercent = teamFeedbackScorePercent;
    }

    public Double getCompletionPercent() {
        return completionPercent;
    }

    public void setCompletionPercent(Double completionPercent) {
        this.completionPercent = completionPercent;
    }

    public Integer getHptmScore() {
        return hptmScore;
    }

    public void setHptmScore(Integer hptmScore) {
        this.hptmScore = hptmScore;
    }
}
