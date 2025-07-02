package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelChat {

    @SerializedName("cr_id")
    private String mCrId;
    @SerializedName("created_date")
    private String mCreatedDate;
    //    @SerializedName("message")
//    private String mMessage;
    @SerializedName("post_type")
    private String mPostType;
    @SerializedName("id")
    private String mId;
    @SerializedName("sendTo")
    private String mSendTo;
    @SerializedName("sendFrom")
    private String mSendFrom;
    @SerializedName("name")
    private String mName;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("created_at")
    private String mCreated_at;
    @SerializedName("userImageUrl")
    private String mUserImageUrl;
    @SerializedName("msgImageUrl")
    private String mMsgImageUrl;
    @SerializedName("userType")
    private String mUserType;
    ;

//    @SerializedName("user_type")
//    private String mUserType;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmSendTo() {
        return mSendTo;
    }

    public void setmSendTo(String mSendTo) {
        this.mSendTo = mSendTo;
    }

    public String getmSendFrom() {
        return mSendFrom;
    }

    public void setmSendFrom(String mSendFrom) {
        this.mSendFrom = mSendFrom;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmCreated_at() {
        return mCreated_at;
    }

    public void setmCreated_at(String mCreated_at) {
        this.mCreated_at = mCreated_at;
    }

    public String getmUserImageUrl() {
        return mUserImageUrl;
    }

    public void setmUserImageUrl(String mUserImageUrl) {
        this.mUserImageUrl = mUserImageUrl;
    }

    public String getmMsgImageUrl() {
        return mMsgImageUrl;
    }

    public void setmMsgImageUrl(String mMsgImageUrl) {
        this.mMsgImageUrl = mMsgImageUrl;
    }

    public String getmUserType() {
        return mUserType;
    }

    public void setmUserType(String mUserType) {
        this.mUserType = mUserType;
    }


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
