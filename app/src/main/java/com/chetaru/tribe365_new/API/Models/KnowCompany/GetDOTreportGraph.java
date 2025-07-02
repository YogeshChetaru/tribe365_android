package com.chetaru.tribe365_new.API.Models.KnowCompany;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetDOTreportGraph {

    @SerializedName("beliefId")
    private Long mBeliefId;
    @SerializedName("beliefName")
    private String mBeliefName;
    @SerializedName("beliefRatings")
    private String mBeliefRatings;
    @SerializedName("beliefValues")
    private List<BeliefValue> mBeliefValues;

    public Long getBeliefId() {
        return mBeliefId;
    }

    public void setBeliefId(Long beliefId) {
        mBeliefId = beliefId;
    }

    public String getBeliefName() {
        return mBeliefName;
    }

    public void setBeliefName(String beliefName) {
        mBeliefName = beliefName;
    }

    public String getBeliefRatings() {
        return mBeliefRatings;
    }

    public void setBeliefRatings(String beliefRatings) {
        mBeliefRatings = beliefRatings;
    }

    public List<BeliefValue> getBeliefValues() {
        return mBeliefValues;
    }

    public void setBeliefValues(List<BeliefValue> beliefValues) {
        mBeliefValues = beliefValues;
    }

}
