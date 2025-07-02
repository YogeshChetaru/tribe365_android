package com.chetaru.tribe365_new.API.Models.HPTM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrincipleResponse {
    @SerializedName("principleData")
    @Expose
    private List<PrincipleFirst> principleData = null;
    @SerializedName("hptmScore")
    @Expose
    private Integer hptmScore;

    public List<PrincipleFirst> getPrincipleData() {
        return principleData;
    }

    public void setPrincipleData(List<PrincipleFirst> principleData) {
        this.principleData = principleData;
    }

    public Integer getHptmScore() {
        return hptmScore;
    }

    public void setHptmScore(Integer hptmScore) {
        this.hptmScore = hptmScore;
    }
}
