package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelNotificationList {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("notificationType")
    private String mNotificationType = "";
    @SerializedName("title")
    private String mTitle;
    @SerializedName("feedbackId")
    private String mFeedbackId;
    @SerializedName("supportId")
    private String mSupportId;
    @SerializedName("reflectionId")
    private String mReflectionId;
    @SerializedName("teamFeedbackId")
    private String mTeamFeedbackId;
    @SerializedName("id")
    private String mId;
    @SerializedName("lastMessage")
    private String mLastMessage;
    @SerializedName("file")
    private Boolean mFile;
    @SerializedName("isRead")
    private Boolean isRead;
    @SerializedName("isMultiple")
    private Boolean isMultiple;
    @SerializedName("userImage")
    private String mUserImage;
    @SerializedName("userName")
    private String mUserName;
    @SerializedName("userEmail")
    private String mUserEmail;
    @SerializedName("reminderList")
    private Boolean reminderList;
    @SerializedName("fromUserId")
    private String fromUserId;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmFeedbackId() {
        return mFeedbackId;
    }

    public void setmFeedbackId(String mFeedbackId) {
        this.mFeedbackId = mFeedbackId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getmCreatedAt() {
        return mCreatedAt;
    }

    public void setmCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }







    public String getmLastMessage() {
        return mLastMessage;
    }

    public void setmLastMessage(String mLastMessage) {
        this.mLastMessage = mLastMessage;
    }



    public String getNotificationType() {
        return mNotificationType;
    }

    public void setNotificationType(String notificationType) {
        mNotificationType = notificationType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Boolean getMultiple() {
        return isMultiple;
    }

    public void setMultiple(Boolean multiple) {
        isMultiple = multiple;
    }

    public String getmUserImage() {
        return mUserImage;
    }

    public void setmUserImage(String mUserImage) {
        this.mUserImage = mUserImage;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserEmail() {
        return mUserEmail;
    }

    public void setmUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public Boolean getReminderList() {
        return reminderList;
    }

    public void setReminderList(Boolean reminderList) {
        this.reminderList = reminderList;
    }

    public String getmSupportId() {
        return mSupportId;
    }

    public void setmSupportId(String mSupportId) {
        this.mSupportId = mSupportId;
    }

    public String getmReflectionId() {
        return mReflectionId;
    }

    public void setmReflectionId(String mReflectionId) {
        this.mReflectionId = mReflectionId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getmTeamFeedbackId() {
        return mTeamFeedbackId;
    }

    public void setmTeamFeedbackId(String mTeamFeedbackId) {
        this.mTeamFeedbackId = mTeamFeedbackId;
    }
}
