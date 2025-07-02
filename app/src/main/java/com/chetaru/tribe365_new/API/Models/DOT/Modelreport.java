package com.chetaru.tribe365_new.API.Models.DOT;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Modelreport {

    @SerializedName("beliefValueArr")
    private List<BeliefValueArr> mBeliefValueArr;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;

    public List<BeliefValueArr> getBeliefValueArr() {
        return mBeliefValueArr;
    }

    public void setBeliefValueArr(List<BeliefValueArr> beliefValueArr) {
        mBeliefValueArr = beliefValueArr;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
