package com.chetaru.tribe365_new.API.Models.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AwardList {

    @SerializedName("awardId")
    @Expose
    public Integer awardId;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
