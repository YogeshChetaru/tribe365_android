package com.chetaru.tribe365_new.API.Models.SOTBeans.UpdateBean;


import com.google.gson.annotations.SerializedName;


public class Option {

    @SerializedName("answerId")
    private Long mAnswerId;
    @SerializedName("option")
    private String mOption;
    @SerializedName("optionId")
    private Long mOptionId;
    @SerializedName("points")
    private Long mPoints;
    @SerializedName("rating")
    private String mRating = "";

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public Long getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(Long answerId) {
        mAnswerId = answerId;
    }

    public String getOption() {
        return mOption;
    }

    public void setOption(String option) {
        mOption = option;
    }

    public Long getOptionId() {
        return mOptionId;
    }

    public void setOptionId(Long optionId) {
        mOptionId = optionId;
    }

    public Long getPoints() {
        return mPoints;
    }

    public void setPoints(Long points) {
        mPoints = points;
    }

}
