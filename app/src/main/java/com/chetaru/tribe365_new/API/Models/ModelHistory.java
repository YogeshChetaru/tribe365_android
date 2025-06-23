package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class ModelHistory {

    @SerializedName("changeit_id")
    private String mChangeitId;
    @SerializedName("created_date")
    private String mCreatedDate;
    @SerializedName("images")
    private String mImages;
    @SerializedName("itemchat")
    private List<Itemchat> mItemchat;
    @SerializedName("lastmsg")
    private List<Lastmsg> mLastmsg;
    @SerializedName("location_detail")
    private String mLocationDetail;
    @SerializedName("msg_detail")
    private String mMsgDetail;
    @SerializedName("msg_type")
    private String mMsgType;


    //new IOT
    @SerializedName("id")
    private int mId;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("image")
    private String mImage;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("messages")
    private List<ModelChat> mMessagesList;
    @SerializedName("status")
    private String status;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getMCreatedAt() {
        return mCreatedAt;
    }

    public void setMCreatedAt(String MCreatedAt) {
        this.mCreatedAt = MCreatedAt;
    }

    public List<ModelChat> getMmessages() {
        return mMessagesList;
    }

    public void setMmessages(List<ModelChat> mmessages) {
        mMessagesList = mmessages;
    }


    public String getChangeitId() {
        return mChangeitId;
    }

    public void setChangeitId(String changeitId) {
        mChangeitId = changeitId;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getImages() {
        return mImages;
    }

    public void setImages(String images) {
        mImages = images;
    }

    public List<Itemchat> getItemchat() {
        return mItemchat;
    }

    public void setItemchat(List<Itemchat> itemchat) {
        mItemchat = itemchat;
    }

    public List<Lastmsg> getLastmsg() {
        return mLastmsg;
    }

    public void setLastmsg(List<Lastmsg> lastmsg) {
        mLastmsg = lastmsg;
    }

    public String getLocationDetail() {
        return mLocationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        mLocationDetail = locationDetail;
    }

    public String getMsgDetail() {
        return mMsgDetail;
    }

    public void setMsgDetail(String msgDetail) {
        mMsgDetail = msgDetail;
    }

    public String getMsgType() {
        return mMsgType;
    }

    public void setMsgType(String msgType) {
        mMsgType = msgType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
