package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelChatMsg {

    @SerializedName("cr_id")
    private String mCrId;
    @SerializedName("created_date")
    private String mCreatedDate;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("post_type")
    private String mPostType;
    @SerializedName("user_type")
    private String mUserType;

    public String getCrId() {
        return mCrId;
    }

    public void setCrId(String crId) {
        mCrId = crId;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getPostType() {
        return mPostType;
    }

    public void setPostType(String postType) {
        mPostType = postType;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}
