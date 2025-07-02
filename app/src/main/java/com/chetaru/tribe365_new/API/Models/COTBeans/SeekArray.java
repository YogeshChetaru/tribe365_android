package com.chetaru.tribe365_new.API.Models.COTBeans;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SeekArray {

    @SerializedName("value")
    private String mValue;
    @SerializedName("valueType")
    private String mValueType;

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public String getValueType() {
        return mValueType;
    }

    public void setValueType(String valueType) {
        mValueType = valueType;
    }

}
