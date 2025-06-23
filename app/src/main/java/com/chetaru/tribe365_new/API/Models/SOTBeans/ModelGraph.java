package com.chetaru.tribe365_new.API.Models.SOTBeans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelGraph {

    @SerializedName("sotMotivationValues")
    private List<SotMotivationValue> mSotMotivationValues;
    @SerializedName("userId")
    private Long mUserId;
    @SerializedName("userName")
    private String mUserName;

    public List<SotMotivationValue> getSotMotivationValues() {
        return mSotMotivationValues;
    }

    public void setSotMotivationValues(List<SotMotivationValue> sotMotivationValues) {
        mSotMotivationValues = sotMotivationValues;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

}
