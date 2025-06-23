package com.chetaru.tribe365_new.API.Models.MemberHome;

import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatestKudosAward {
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("userImage")
    @Expose
    public String userImage;
    @SerializedName("awardDescription")
    @Expose
    public String awardDescription;
    @SerializedName("awardDate")
    @Expose
    public String awardDate;
    @SerializedName("awardValue")
    @Expose
    public String awardValue;
    @SerializedName("kudoAwardCount")
    @Expose
    public int kudoAwardCount;
    @SerializedName("viewMoreUsers")
    @Expose
    public List<KudosAwardList> viewMoreUsers = null;

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

    public String getAwardDescription() {
        return awardDescription;
    }

    public void setAwardDescription(String awardDescription) {
        this.awardDescription = awardDescription;
    }

    public String getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(String awardDate) {
        this.awardDate = awardDate;
    }

    public String getAwardValue() {
        return awardValue;
    }

    public void setAwardValue(String awardValue) {
        this.awardValue = awardValue;
    }

    public int getKudoAwardCount() {
        return kudoAwardCount;
    }

    public void setKudoAwardCount(int kudoAwardCount) {
        this.kudoAwardCount = kudoAwardCount;
    }

    public List<KudosAwardList> getViewMoreUsers() {
        return viewMoreUsers;
    }

    public void setViewMoreUsers(List<KudosAwardList> viewMoreUsers) {
        this.viewMoreUsers = viewMoreUsers;
    }
}
