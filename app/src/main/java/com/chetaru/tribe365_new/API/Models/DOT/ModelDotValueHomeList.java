package com.chetaru.tribe365_new.API.Models.DOT;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelDotValueHomeList {

    @SerializedName("dotBeliefId")
    private String mDotBeliefId;
    @SerializedName("dotValueId")
    private String mDotValueId;
    @SerializedName("dotValueNameId")
    private String mDotValueNameId;

    public String getDotBeliefId() {
        return mDotBeliefId;
    }

    public void setDotBeliefId(String dotBeliefId) {
        mDotBeliefId = dotBeliefId;
    }

    public String getDotValueId() {
        return mDotValueId;
    }

    public void setDotValueId(String dotValueId) {
        mDotValueId = dotValueId;
    }

    public String getDotValueNameId() {
        return mDotValueNameId;
    }

    public void setDotValueNameId(String dotValueNameId) {
        mDotValueNameId = dotValueNameId;
    }

}
