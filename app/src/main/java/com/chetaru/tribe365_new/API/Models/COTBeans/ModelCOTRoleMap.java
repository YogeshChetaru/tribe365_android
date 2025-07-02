package com.chetaru.tribe365_new.API.Models.COTBeans;


import com.google.gson.annotations.SerializedName;


public class ModelCOTRoleMap {

    @SerializedName("id")
    private Long mId;
    @SerializedName("maper")


    private String mMaper;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getMaper() {
        return mMaper;
    }

    public void setMaper(String maper) {
        mMaper = maper;
    }

}
