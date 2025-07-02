package com.chetaru.tribe365_new.API.Models.Risk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RiskDetail {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("actions")
    @Expose
    private List<ActionList> actions = null;
    @SerializedName("linkedOffloads")
    @Expose
    private Integer offloads;

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ActionList> getActions() {
        return actions;
    }

    public void setActions(List<ActionList> actions) {
        this.actions = actions;
    }

    public Integer getOffloads() {
        return offloads;
    }

    public void setOffloads(Integer offloads) {
        this.offloads = offloads;
    }
}
