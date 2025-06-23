package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelEvidence {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("fileURL")
    private String mFileURL;
    @SerializedName("userId")
    private String mUserId;
    @SerializedName("id")
    private String mId;
    @SerializedName("section")
    private String mSection;


    public String getSection() {
        return mSection;
    }

    public void setSection(String mSection) {
        this.mSection = mSection;
    }


    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
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

    public String getFileURL() {
        return mFileURL;
    }

    public void setFileURL(String fileURL) {
        mFileURL = fileURL;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
