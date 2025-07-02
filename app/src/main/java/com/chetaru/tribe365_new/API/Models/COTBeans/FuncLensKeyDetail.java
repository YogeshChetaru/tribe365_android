package com.chetaru.tribe365_new.API.Models.COTBeans;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class FuncLensKeyDetail {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("value")
    private String mValue;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

}
