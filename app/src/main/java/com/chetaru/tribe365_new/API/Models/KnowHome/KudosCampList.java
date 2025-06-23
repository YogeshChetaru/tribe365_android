package com.chetaru.tribe365_new.API.Models.KnowHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KudosCampList {

    @SerializedName("id")
    private Long mId;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userImage")
    @Expose
    private String userImage;
    @SerializedName("count")
    @Expose
    private String count;

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

   /* public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }*/

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
