package com.chetaru.tribe365_new.API.Models.SOTBeans;

import com.google.gson.annotations.SerializedName;


public class Option {

    @SerializedName("categoryId")
    private Long mCategoryId;
    @SerializedName("option")
    private String mOption;
    @SerializedName("OptionId")
    private Long mOptionId;

    @SerializedName("rating")
    private String mRating = "";

    public Long getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(Long categoryId) {
        mCategoryId = categoryId;
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

    public void setOptionId(Long OptionId) {
        mOptionId = OptionId;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

}
