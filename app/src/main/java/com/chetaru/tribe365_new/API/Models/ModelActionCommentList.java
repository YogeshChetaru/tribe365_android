package com.chetaru.tribe365_new.API.Models;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ModelActionCommentList {

    @SerializedName("comment")
    private String mComment;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("userId")
    private String mUserId;

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
