package com.chetaru.tribe365_new.API.Models.Risk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiskCount {
    @SerializedName("statusTitle")
    @Expose
    private String statusTitle;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("count")
    @Expose
    private Integer count;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }
}
