package com.chetaru.tribe365_new.API.Models.Risk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RiskHome {
    @SerializedName("riskCount")
    @Expose
    private List<RiskCount> riskCount = null;
    @SerializedName("riskArr")
    @Expose
    private List<RiskArr> riskArr = null;
    @SerializedName("swotList")
    @Expose
    private List<Swot> swotList = null;

    public List<RiskCount> getRiskCount() {
        return riskCount;
    }

    public void setRiskCount(List<RiskCount> riskCount) {
        this.riskCount = riskCount;
    }

    public List<RiskArr> getRiskArr() {
        return riskArr;
    }

    public void setRiskArr(List<RiskArr> riskArr) {
        this.riskArr = riskArr;
    }

    public List<Swot> getSwotList() {
        return swotList;
    }

    public void setSwotList(List<Swot> swotList) {
        this.swotList = swotList;
    }


}
