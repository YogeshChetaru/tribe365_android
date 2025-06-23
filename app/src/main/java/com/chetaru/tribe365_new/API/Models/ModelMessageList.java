package com.chetaru.tribe365_new.API.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("unused")
public class ModelMessageList implements Serializable {

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
    @SerializedName("msg_detail")
    private String mMsgDetail;
    @SerializedName("msg_type")
    private String mMsgType;
    @SerializedName("id")
    private String mId;
    @SerializedName("feedback_msg")
    private String mFeedback_msg;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("date")
    private String mDate;
    @SerializedName("image")
    private Boolean mImage;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmFeedback_msg() {
        return mFeedback_msg;
    }

    public void setmFeedback_msg(String mFeedback_msg) {
        this.mFeedback_msg = mFeedback_msg;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public Boolean getmImage() {
        return mImage;
    }

    public void setmImage(Boolean mImage) {
        this.mImage = mImage;
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

}
