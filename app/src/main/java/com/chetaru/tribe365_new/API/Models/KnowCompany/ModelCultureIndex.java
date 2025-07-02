package com.chetaru.tribe365_new.API.Models.KnowCompany;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ModelCultureIndex {

    @SerializedName("data")
    private String mIndexCount;
    @SerializedName("monthName")
    private String mTitle;

    public String getIndexCount() {
        return mIndexCount;
    }

    public void setIndexCount(String indexCount) {
        mIndexCount = indexCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
