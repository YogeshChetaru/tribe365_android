package com.chetaru.tribe365_new.API.Models.MemberHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewMoreUser {
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("userImage")
    @Expose
    public String userImage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
