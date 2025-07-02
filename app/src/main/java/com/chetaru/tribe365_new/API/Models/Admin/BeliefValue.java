package com.chetaru.tribe365_new.API.Models.Admin;


import com.google.gson.annotations.SerializedName;


public class BeliefValue {

    @SerializedName("valueId")
    private Long mValueId;
    @SerializedName("valueName")
    private String mValueName;
    @SerializedName("valueRatings")
    private double mValueRatings;

    public Long getValueId() {
        return mValueId;
    }

    public void setValueId(Long valueId) {
        mValueId = valueId;
    }

    public String getValueName() {
        return mValueName;
    }

    public void setValueName(String valueName) {
        mValueName = valueName;
    }

    public double getValueRatings() {
        return mValueRatings;
    }

    public void setValueRatings(double valueRatings) {
        mValueRatings = valueRatings;
    }

}
