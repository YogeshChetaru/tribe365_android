package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Belief {

    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("dotId")
    private Long mDotId;
    @SerializedName("belief_value")
    private ArrayList<BeliefValue> mBeliefValue;

    @SerializedName("beliefUrl")
    @Expose
    private String beliefUrl;
    @SerializedName("beliefDesc")
    @Expose
    private String beliefDesc;


    public ArrayList<BeliefValue> getBeliefValue() {
        return mBeliefValue;
    }

    public void setBeliefValue(ArrayList<BeliefValue> beliefValue) {
        mBeliefValue = beliefValue;
    }

    public Long getDotId() {
        return mDotId;
    }

    public void setDotId(Long dotId) {
        mDotId = dotId;
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

    public String getBeliefUrl() {
        return beliefUrl;
    }

    public void setBeliefUrl(String beliefUrl) {
        this.beliefUrl = beliefUrl;
    }

    public String getBeliefDesc() {
        return beliefDesc;
    }

    public void setBeliefDesc(String beliefDesc) {
        this.beliefDesc = beliefDesc;
    }



}
