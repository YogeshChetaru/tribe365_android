package com.chetaru.tribe365_new.API.Models.COTBeans;


import com.google.gson.annotations.SerializedName;


public class ModelCotDescription {

    @SerializedName("long_description")
    private String mLongDescription;
    @SerializedName("short_description")
    private String mShortDescription;
    @SerializedName("title")
    private String mTitle;

    public String getLongDescription() {
        return mLongDescription;
    }

    public void setLongDescription(String longDescription) {
        mLongDescription = longDescription;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
