package com.chetaru.tribe365_new.API.Models.KnowCompany;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class BeliefValue {

    @SerializedName("valueId")
    private Long mValueId;
    @SerializedName("valueName")
    private String mValueName;
    @SerializedName("valueRatings")
    private String mValueRatings;

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

    public String getValueRatings() {
        return mValueRatings;
    }

    public void setValueRatings(String valueRatings) {
        mValueRatings = valueRatings;
    }

}
