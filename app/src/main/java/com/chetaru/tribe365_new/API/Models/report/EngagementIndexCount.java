package com.chetaru.tribe365_new.API.Models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EngagementIndexCount {

    @SerializedName("current")
    @Expose
    private Double current;
    @SerializedName("last")
    @Expose
    private Double last;

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }
}
