package com.chetaru.tribe365_new.API.Models.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KudosAwardTitleList {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("dotName")
    @Expose
    public String dotName;
    @SerializedName("awardCount")
    @Expose
    public Integer awardCount;
    @SerializedName("dotValAward")
    @Expose
    public List<AwardList> dotAwardDes = null;
    @SerializedName("countStatus")
    @Expose
    public Boolean countStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDotName() {
        return dotName;
    }

    public void setDotName(String dotName) {
        this.dotName = dotName;
    }

    public Integer getAwardCount() {
        return awardCount;
    }

    public void setAwardCount(Integer awardCount) {
        this.awardCount = awardCount;
    }

    public List<AwardList> getDotAwardDes() {
        return dotAwardDes;
    }

    public void setDotAwardDes(List<AwardList> dotAwardDes) {
        this.dotAwardDes = dotAwardDes;
    }

    public Boolean getCountStatus() {
        return countStatus;
    }

    public void setCountStatus(Boolean countStatus) {
        this.countStatus = countStatus;
    }
}
